package io.github.willqi.leaderboardapi.core.datasources;

import io.github.willqi.leaderboardapi.core.datasources.exceptions.DataSourceException;

import java.util.List;

/**
 * This DataSource does not use any database connection and it's sole purpose
 * is to provide a datasource for those who do not want to use any sort of database
 * @param <K> Type of the identifier the value is associated with
 * @param <V> Values must implement the Comparable interface and will be sorted using the compareTo method
 */
public class MemoryDataSource<K, V extends Comparable<V>> implements DataSource<K, V> {

    @Override
    public List<Record<K, V>> getTop(int placings) throws DataSourceException {
        return null;
    }

    @Override
    public V get(K key) throws DataSourceException {
        return null;
    }

    @Override
    public void set(K key, V value) throws DataSourceException {

    }

    @Override
    public void add(K key, V value) throws DataSourceException {

    }

    @Override
    public void remove(K key) throws DataSourceException {

    }

    @Override
    public void reset() throws DataSourceException {

    }

    @Override
    public void close() {

    }

}
