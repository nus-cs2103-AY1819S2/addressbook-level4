package seedu.address.testutil;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import seedu.address.model.person.healthworker.HealthWorker;
import seedu.address.model.person.healthworker.Organization;
import seedu.address.model.tag.Skills;
import seedu.address.model.tag.Specialisation;

/**
 * Utility Class for building Health Worker objects.
 */
public class HealthWorkerBuilder extends PersonBuilder {

    public static final String DEFAULT_ORGANIZATION = "NUS";
    public static final Set<Specialisation> DEFAULT_SKILL_SET = new HashSet<>(
            Arrays.asList(Specialisation.GENERAL_PRACTICE));

    private Organization organization;
    private Skills skills;

    public HealthWorkerBuilder() {
        super();
        this.organization = new Organization(DEFAULT_ORGANIZATION);
        this.skills = new Skills(DEFAULT_SKILL_SET);
    }

    /**
     * Initializes the HealthWorkerBuilder with the data of {@code
     * healthWorkerToCopy}
     */
    public HealthWorkerBuilder(HealthWorker healthWorkerToCopy) {
        super(healthWorkerToCopy);
        this.organization = healthWorkerToCopy.getOrganization();
        this.skills = healthWorkerToCopy.getSkills();
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
     * Sets the {@code skills} of the {@code HealthWorker} that we are
     * building.
     */
    public HealthWorkerBuilder withSkills(Skills skills) {
        this.skills = new Skills(skills);
        return this;
    }

    /**
     * Builds a new HealthWorker object for testing.
     * @return a HealthWorker object with the parameters specified in this
     * object.
     */
    public HealthWorker build() {
        return new HealthWorker(name, nric, phone, organization, skills);
    }

}
