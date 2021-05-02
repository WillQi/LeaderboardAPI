package io.github.willqi.leaderboardapi.core.leaderboards;

import io.github.willqi.leaderboardapi.core.datasources.DataSource;
import io.github.willqi.leaderboardapi.core.datasources.exceptions.DataSourceException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UUIDLeaderboard<V> extends Leaderboard<UUID, V, DataSource<String, V>> {

    public UUIDLeaderboard(DataSource<String, V> dataSource) {
        super(dataSource);
    }

    @Override
    public List<Entry<UUID, V>> getTop(int placings) throws DataSourceException {
        List<Entry<UUID, V>> results = new ArrayList<>();
        this.dataSource.getTop(placings).forEach(record ->
                results.add(new Entry<>(UUID.fromString(record.getIdentifier()), record.getValue())));
        return results;
    }

    @Override
    public V get(UUID key) throws DataSourceException {
        return this.dataSource.get(key.toString());
    }

    @Override
    public void add(UUID key, V amount) throws DataSourceException {
        this.dataSource.add(key.toString(), amount);
    }

    @Override
    public void set(UUID key, V value) throws DataSourceException {
        this.dataSource.set(key.toString(), value);
    }

    @Override
    public void remove(UUID key) throws DataSourceException {
        this.dataSource.remove(key.toString());
    }

    @Override
    public void reset() throws DataSourceException {
        this.dataSource.reset();
    }

}
