package seedu.address.testutil;

import seedu.address.model.job.Job;
import seedu.address.model.job.JobName;

/**
 * A utility class to help with building Person objects.
 */
public class JobBuilder {

    public static final String DEFAULT_JOBNAME = "HELPER";


    private JobName name;

    public JobBuilder() {
        name = new JobName(DEFAULT_JOBNAME);
    }

    /**
     * Initializes the JobBuilder with the data of {@code jobToCopy}.
     */
    public JobBuilder(Job jobToCopy) {
        name = jobToCopy.getName();
    }

    /**
     * Sets the {@code JobName} of the {@code Job} that we are building.
     */
    public JobBuilder withName(String name) {
        this.name = new JobName(name);
        return this;
    }

    /***/
    public Job build() {
        return new Job(name);
    }

}
