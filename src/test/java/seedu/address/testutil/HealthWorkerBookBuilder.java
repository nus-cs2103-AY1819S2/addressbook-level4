package seedu.address.testutil;

import seedu.address.model.HealthWorkerBook;
import seedu.address.model.person.healthworker.HealthWorker;

/**
 * Utility class for building HealthWorkerBook objects for testing.
 */
public class HealthWorkerBookBuilder {

    private HealthWorkerBook healthWorkerBook;

    public HealthWorkerBookBuilder() {
        this.healthWorkerBook = new HealthWorkerBook();
    }

    public HealthWorkerBookBuilder(HealthWorkerBook healthWorkerBook) {
        this.healthWorkerBook = healthWorkerBook;
    }

    /**
     * Adds a new {@code HealthWorker} to the {@code HealthWorkerBook} that we are building.
     */
    public HealthWorkerBookBuilder withHealthWorker(HealthWorker healthWorker) {
        this.healthWorkerBook.addHealthWorker(healthWorker);
        return this;
    }

    public HealthWorkerBook build() {
        return this.healthWorkerBook;
    }
}
