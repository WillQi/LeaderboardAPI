package io.github.willqi.leaderboardapi.core.datasources.exceptions;

public class DataSourceException extends Exception {

    public DataSourceException(Throwable throwable) {
        super(throwable);
    }

    public DataSourceException(String message) {
        super(message);
    }

}
