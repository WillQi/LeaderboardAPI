package io.github.willqi.leaderboardapi.core.examples;

/**
 * This example DataSource does not use any database connection and it's sole purpose
 * is to provide a datasource for those who do not want to use any sort of database
 * BUT are using numerical values.
 * @param <K> Type of the identifier the value is associated with
 */
public class MemoryNumericalDoubleDataSource<K> extends MemoryDataSource<K, Double> {

    @Override
    public void add(K key, Double value) {
        this.scores.compute(key, (k, currentValue) -> {
           if (currentValue == null) {
               return value;
           } else {
               return currentValue + value;
           }
        });
    }

}
