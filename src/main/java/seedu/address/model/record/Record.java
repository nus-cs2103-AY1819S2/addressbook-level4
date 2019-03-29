package seedu.address.model.record;

/**
 * Abstract class for more specific record class to extend from.
 * Ensures that subclasses can be recorded by implementing record().
 */
public abstract class Record {
    public abstract Statistics toStatistics(StatisticsManager statisticsManager);
}
