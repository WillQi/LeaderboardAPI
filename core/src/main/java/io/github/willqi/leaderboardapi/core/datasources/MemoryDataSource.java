package io.github.willqi.leaderboardapi.core.datasources;

import io.github.willqi.leaderboardapi.core.datasources.exceptions.DataSourceException;

import java.util.List;

public class MemoryDataSource implements DataSource {

    @Override
    public List<Record> getTop() throws DataSourceException {
        return null;
    }

    @Override
    public void set() throws DataSourceException {

    }

    @Override
    public void add() throws DataSourceException {

    }

    @Override
    public void close() {

    }

}
