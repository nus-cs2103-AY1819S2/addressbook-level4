package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.person.*;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * Utility Class for building Health Worker objects.
 */
public class HealthWorkerBuilder {

    public static final String DEFAULT_NAME = "Default Health Worker";
    public static final String DEFAULT_PHONE = "98765432";
    public static final String DEFAULT_EMAIL = "default@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_ORGANIZATION = "NUS";

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Organization organization;
    private Set<Tag> tags;

    public HealthWorkerBuilder() {
        this.name = new Name(DEFAULT_NAME);
        this.phone = new Phone(DEFAULT_PHONE);
        this.email = new Email(DEFAULT_EMAIL);
        this.address = new Address(DEFAULT_ADDRESS);
        this.organization = new Organization(DEFAULT_ORGANIZATION);
        this.tags = new HashSet<>();
    }

    /**
     * Initializes the HealthWorkerBuilder with the data of {@code
     * healthWorkerToCopy}
     */
    public HealthWorkerBuilder(HealthWorker healthWorkerToCopy) {
        this.name = healthWorkerToCopy.getName();
        this.phone = healthWorkerToCopy.getPhone();
        this.email = healthWorkerToCopy.getEmail();
        this.address = healthWorkerToCopy.getAddress();
        this.organization = healthWorkerToCopy.getOrganization();
        this.tags = new HashSet<>(healthWorkerToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code HealthWorker} that we are building.
     */
    public HealthWorkerBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the
     * {@code HealthWorker} that we are building.
     */
    public HealthWorkerBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code HealthWorker} that we are
     * building.
     */
    public HealthWorkerBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code HealthWorker} that we are building.
     */
    public HealthWorkerBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code HealthWorker} that we are building.
     */
    public HealthWorkerBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Organization} of the {@code HealthWorker} that we are
     * building.
     */
    public HealthWorkerBuilder withOrganization(String organization) {
        this.organization = new Organization(organization);
        return this;
    }

    public HealthWorker build() {
        return new HealthWorker(name, phone, email, address, tags, organization);
    }

}
