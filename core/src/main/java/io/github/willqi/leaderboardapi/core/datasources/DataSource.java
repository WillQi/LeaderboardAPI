package io.github.willqi.leaderboardapi.core.datasources;

import io.github.willqi.leaderboardapi.core.datasources.exceptions.DataSourceException;

import java.util.List;

/**
 * Handles interacting with the database
 * @param <K> Type associated with the identifier of each entry (e.g. id/username)
 * @param <V> Type associated with the value of each entry (e.g. integers, doubles, etc etc)
 */
public interface DataSource<K, V> {

    List<Record<K, V>> getTop(int placings) throws DataSourceException;

    V get(K key) throws DataSourceException;

    void set(K key, V value) throws DataSourceException;

    void add(K key, V value) throws DataSourceException;

    void remove(K key) throws DataSourceException;

    /**
     * Delete all entries in the datasource
     */
    void reset() throws DataSourceException;

    /**
     * Cleanup operation if you do not want to use this datasource anymore
     */
    void close() throws DataSourceException;

    /**
     * Represents each record retrieved by the DataSource
     * @param <K> Type associated with the identifier of each entry (e.g. id/username)
     * @param <V> Type associated with the value of each entry (e.g. integers, doubles, etc etc)
     */
    class Record<K, V> {

        private final K identifier;
        private final V value;

        public Record(K identifier, V value) {
            this.identifier = identifier;
            this.value = value;
        }

        public K getIdentifier() {
            return this.identifier;
        }

        public V getValue() {
            return this.value;
        }

    }

}
