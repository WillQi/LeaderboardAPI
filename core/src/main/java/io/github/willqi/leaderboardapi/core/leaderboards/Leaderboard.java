package io.github.willqi.leaderboardapi.core.leaderboards;

import io.github.willqi.leaderboardapi.core.datasources.DataSource;
import io.github.willqi.leaderboardapi.core.datasources.exceptions.DataSourceException;

import java.util.List;

/**
 * Represents a leaderboard
 * @param <K> Identifier of the leaderboard entry (e.g. UUID)
 * @param <V> Value associated with each leaderboard entry. (e.g. points)
 * @param <D> DataSource type that should be used with this leaderboard
 */
public abstract class Leaderboard<K, V, D extends DataSource<?, ?>> {

    protected final D dataSource;

    public Leaderboard(D dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Retrieve the entries that are at the highest of this leaderboard
     * @param placings Amount of entries you want returned
     * @return List of leaderboard entries from first place to last place
     */
    public abstract List<Entry<K, V>> getTop(int placings) throws DataSourceException;
    public abstract V get(K key) throws DataSourceException;
    public abstract void add(K key, V amount) throws DataSourceException;
    public abstract void set(K key, V value) throws DataSourceException;
    public abstract void remove(K key) throws DataSourceException;
    /**
     * Delete all entries in a leaderboard resetting it to a blank state
     */
    public abstract void reset() throws DataSourceException;

    /**
     * Represents each entry of a leaderboard
     * @param <K> Identifier of the leaderboard entry (e.g. UUID)
     * @param <V> Value associated with each leaderboard entry. (e.g. points)
     */
    public static class Entry<K, V> {

        private final K identifier;
        private final V value;

        public Entry(K identifier, V value) {
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