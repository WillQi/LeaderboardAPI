package io.github.willqi.leaderboardapi.core.datasources;

import io.github.willqi.leaderboardapi.core.datasources.exceptions.DataSourceException;

import java.util.List;

public interface DataSource<K, V extends Number> {

    List<Record<K, V>> getTop() throws DataSourceException;

    void set() throws DataSourceException;

    void add() throws DataSourceException;

    void close() throws DataSourceException;


    class Record<K, V extends Number> {

        private final K identifier;
        private final V value;

        protected Record(K identifier, V value) {
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
