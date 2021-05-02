package io.github.willqi.leaderboardapi.core.examples;

import io.github.willqi.leaderboardapi.core.DataSource;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * This example DataSource does not use any database connection and it's sole purpose
 * is to provide a datasource for those who do not want to use any sort of database
 * Note: add cannot be used with this DataSource because there is no guarantee that V is a number.
 * @param <K> Type of the identifier the value is associated with
 * @param <V> Values must implement the Comparable interface and will be sorted using the compareTo method
 */
public class MemoryDataSource<K, V extends Comparable<V>> implements DataSource<K, V> {

    protected final Map<K, V> scores;

    public MemoryDataSource() {
        this.scores = new ConcurrentHashMap<>();
    }

    @Override
    public List<Record<K, V>> getTop(int placings) {
        return this.scores.entrySet()
                .stream()
                .sorted((entryA, entryB) -> entryB.getValue().compareTo(entryA.getValue()))
                .map(entry -> new Record<>(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    @Override
    public V get(K key) {
        return this.scores.getOrDefault(key, null);
    }

    @Override
    public void set(K key, V value) {
        this.scores.put(key, value);
    }

    @Override
    public void add(K key, V value) {
        throw new UnsupportedOperationException("MemoryDataSource does not support add.");
    }

    @Override
    public void remove(K key) {
        this.scores.remove(key);
    }

    @Override
    public void reset() {
        this.scores.clear();
    }

    @Override
    public void close() {
        this.scores.clear();
    }

}
