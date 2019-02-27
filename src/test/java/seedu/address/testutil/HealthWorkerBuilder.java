package seedu.address.testutil;

import seedu.address.model.person.HealthWorker;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Organization;

/**
 * Utility Class for building Health Worker objects.
 */
public class HealthWorkerBuilder extends PersonBuilder {

    public static final String DEFAULT_ORGANIZATION = "NUS";

    private Organization organization;

    public HealthWorkerBuilder() {
        super();
        this.organization = new Organization(DEFAULT_ORGANIZATION);
        this.nric = new Nric(DEFAULT_NRIC);
    }

    /**
     * Initializes the HealthWorkerBuilder with the data of {@code
     * healthWorkerToCopy}
     */
    public HealthWorkerBuilder(HealthWorker healthWorkerToCopy) {
        super(healthWorkerToCopy);
        this.nric = healthWorkerToCopy.getNric();
        this.organization = healthWorkerToCopy.getOrganization();
    }

    /**
     * Sets the {@code Organization} of the {@code HealthWorker} that we are
     * building.
     */
    public HealthWorkerBuilder withOrganization(String organization) {
        this.organization = new Organization(organization);
        return this;
    }

    /**
     * Builds a new HealthWorker object for testing.
     * @return a HealthWorker object with the parameters specified in this
     * object.
     */
    public HealthWorker build() {
        return new HealthWorker(name, phone, email, nric, address, tags,
                organization);
    }

}
