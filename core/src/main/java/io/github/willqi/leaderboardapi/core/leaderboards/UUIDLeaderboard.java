package io.github.willqi.leaderboardapi.core.leaderboards;

import io.github.willqi.leaderboardapi.core.datasources.DataSource;
import io.github.willqi.leaderboardapi.core.datasources.exceptions.DataSourceException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public class UUIDLeaderboard<V> extends Leaderboard<UUID, V, DataSource<String, V>> {

    public UUIDLeaderboard(DataSource<String, V> dataSource) {
        super(dataSource);
    }

    @Override
    public CompletableFuture<List<Entry<UUID, V>>> getTop(int placings) {
        return CompletableFuture.supplyAsync(() -> {
            List<Entry<UUID, V>> results = new ArrayList<>();
            try {
                this.dataSource.getTop(placings).forEach(record ->
                    results.add(new Entry<>(UUID.fromString(record.getIdentifier()), record.getValue())));

            } catch (DataSourceException exception) {
                throw new CompletionException(exception);
            }
            return Collections.unmodifiableList(results);
        });
    }

    @Override
    public CompletableFuture<V> get(UUID key) {
        return CompletableFuture.supplyAsync(() -> {
            V result;
            try {
                result = this.dataSource.get(key.toString());
            } catch (DataSourceException exception) {
                throw new CompletionException(exception);
            }
            return result;
        });
    }

    @Override
    public CompletableFuture<Void> add(UUID key, V amount) {
        return CompletableFuture.runAsync(() -> {
            try {
                this.dataSource.add(key.toString(), amount);
            } catch (DataSourceException exception) {
                throw new CompletionException(exception);
            }
        });
    }

    @Override
    public CompletableFuture<Void> set(UUID key, V value) {
        return CompletableFuture.runAsync(() -> {
            try {
                this.dataSource.set(key.toString(), value);
            } catch (DataSourceException exception) {
                throw new CompletionException(exception);
            }
        });
    }

    @Override
    public CompletableFuture<Void> remove(UUID key) {
        return CompletableFuture.runAsync(() -> {
            try {
                this.dataSource.remove(key.toString());
            } catch (DataSourceException exception) {
                throw new CompletionException(exception);
            }
        });
    }

    @Override
    public CompletableFuture<Void> reset() {
        return CompletableFuture.runAsync(() -> {
            try {
                this.dataSource.reset();
            } catch (DataSourceException exception) {
                throw new CompletionException(exception);
            }
        });
    }

}
