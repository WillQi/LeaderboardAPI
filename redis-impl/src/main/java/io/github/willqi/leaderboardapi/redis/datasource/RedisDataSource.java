package io.github.willqi.leaderboardapi.redis.datasource;

import io.github.willqi.leaderboardapi.core.datasources.DataSource;
import io.github.willqi.leaderboardapi.core.datasources.exceptions.DataSourceException;
import redis.clients.jedis.*;
import redis.clients.jedis.exceptions.JedisException;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class RedisDataSource implements DataSource<String, Double> {

    private final JedisPool pool;
    private final byte[] redisDataKey;

    public RedisDataSource(String keyName) {
        this.redisDataKey = keyName.getBytes(StandardCharsets.UTF_8);
        this.pool = new JedisPool();
    }

    public RedisDataSource(String keyName, JedisPool pool) {
        this.redisDataKey = keyName.getBytes(StandardCharsets.UTF_8);
        this.pool = pool;
    }

    public RedisDataSource(String keyName, String host) {
        this.redisDataKey = keyName.getBytes(StandardCharsets.UTF_8);
        this.pool = new JedisPool(host);
    }

    public RedisDataSource(String keyName, String host, int port) {
        this.redisDataKey = keyName.getBytes(StandardCharsets.UTF_8);
        this.pool = new JedisPool(host, port);
    }

    public RedisDataSource(String keyName, String host, int port, String user, String password) {
        this.redisDataKey = keyName.getBytes(StandardCharsets.UTF_8);
        this.pool = new JedisPool(host, port, user, password);
    }


    @Override
    public List<Record<String, Double>> getTop(int placings) throws DataSourceException {
        Set<Tuple> results;
        try (Jedis jedis = this.getJedis()) {
            results = jedis.zrangeByScoreWithScores(this.redisDataKey, Double.MIN_VALUE, Double.MAX_VALUE);
        } catch (JedisException exception) {
            throw new DataSourceException(exception);
        }
        return results.stream()
                .sorted((tupleA, tupleB) -> (int)(tupleB.getScore() - tupleA.getScore()))
                .map(tuple -> new Record<>(tuple.getElement(), tuple.getScore()))
                .collect(Collectors.toList());
    }

    @Override
    public Double get(String key) throws DataSourceException {
        Double score;
        try (Jedis jedis = this.getJedis()) {
            score = jedis.zscore(this.redisDataKey, key.getBytes(StandardCharsets.UTF_8));
        } catch (JedisException exception) {
            throw new DataSourceException(exception);
        }
        if (score != null) {
            return score;
        } else {
            return 0D;
        }
    }

    @Override
    public void set(String key, Double value) throws DataSourceException {
        try (Jedis jedis = this.getJedis()) {
            jedis.zadd(this.redisDataKey, value, key.getBytes(StandardCharsets.UTF_8));
        } catch (JedisException exception) {
            throw new DataSourceException(exception);
        }
    }

    @Override
    public void add(String key, Double value) throws DataSourceException {
        try (Jedis jedis = this.getJedis()) {
            jedis.zincrby(this.redisDataKey, value, key.getBytes(StandardCharsets.UTF_8));
        } catch (JedisException exception) {
            throw new DataSourceException(exception);
        }
    }

    @Override
    public void remove(String key) throws DataSourceException {
        try (Jedis jedis = this.getJedis()) {
            jedis.zrem(this.redisDataKey, key.getBytes(StandardCharsets.UTF_8));
        } catch (JedisException exception) {
            throw new DataSourceException(exception);
        }
    }

    @Override
    public void reset() throws DataSourceException {
        try (Jedis jedis = this.getJedis()) {
            jedis.del(this.redisDataKey);
        } catch (JedisException exception) {
            throw new DataSourceException(exception);
        }
    }

    @Override
    public void close() throws DataSourceException {
        try {
            this.pool.close();
        } catch (JedisException exception) {
            throw new DataSourceException(exception);
        }
    }

    private Jedis getJedis() {
        return this.pool.getResource();
    }

}