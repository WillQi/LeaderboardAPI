package io.github.willqi.leaderboardapi.redis.datasource;

import io.github.willqi.leaderboardapi.core.datasources.DataSource;
import io.github.willqi.leaderboardapi.core.datasources.exceptions.DataSourceException;

import java.util.List;

public class RedisDataSource<K, V extends Number> implements DataSource<K, V> {

    @Override
    public List<Record<K, V>> getTop(int placings) throws DataSourceException {
        return null;
    }

    @Override
    public void set(K key, V value) throws DataSourceException {

    }

    @Override
    public void add(K key, V value) throws DataSourceException {

    }

    @Override
    public void close() {

    }

}