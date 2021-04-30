package io.github.willqi.leaderboardapi.core;

import io.github.willqi.leaderboardapi.core.datasources.DataSource;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Represents a leaderboard
 * @param <K> Identifier of the leaderboard entry (e.g. UUID)
 * @param <V> Value associated with each leaderboard entry. (e.g. points)
 */
public abstract class Leaderboard<K, V> {

    protected final DataSource<?, ?> dataSource;

    public Leaderboard(DataSource<?, ?> dataSource) {
        this.dataSource = dataSource;
    }

    public Leaderboard() {
        this.dataSource = null;
    }

    /**
     * Retrieve the entries that are at the highest of this leaderboard
     * @param placings Amount of entries you want returned
     * @return List of leaderboard entries from first place to last place
     */
    public abstract CompletableFuture<List<Entry<K, V>>> getTop(int placings);

    public abstract CompletableFuture<V> get(K key);
    public abstract CompletableFuture<Void> add(K key, V amount);
    public abstract CompletableFuture<Void> set(K key, V value);
    public abstract CompletableFuture<Void> remove(K key);
    /**
     * Delete all entries in a leaderboard resetting it to a blank state
     */
    public abstract CompletableFuture<Void> reset();

    /**
     * Represents each entry of a leaderboard
     * @param <K> Identifier of the leaderboard entry (e.g. UUID)
     * @param <V> Value associated with each leaderboard entry. (e.g. points)
     */
    public static class Entry<K, V> {

        private final K identifier;
        private final V value;

        protected Entry(K identifier, V value) {
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