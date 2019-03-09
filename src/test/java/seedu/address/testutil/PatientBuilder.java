package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.patient.Patient;
import seedu.address.model.person.Phone;

import seedu.address.model.tag.Tag;

/**
 * Utility Class for building Health Worker objects.
 */
public class PatientBuilder extends PersonBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_NRIC = "S1234567A";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";

    private Name name;
    private Nric nric;
    private Phone phone;
    private Email email;
    private Address address;
    private Set<Tag> tags;

    public PatientBuilder() {
        this.name = new Name(DEFAULT_NAME);
        this.nric = new Nric(DEFAULT_NRIC);
        this.phone = new Phone(DEFAULT_PHONE);
        this.email = new Email(DEFAULT_EMAIL);
        this.address = new Address(DEFAULT_ADDRESS);
        this.tags = new HashSet<>();
    }

    /**
     * Initializes the HealthWorkerBuilder with the data of {@code
     * healthWorkerToCopy}
     */
    public PatientBuilder(Patient patientToCopy) {
        this.name = patientToCopy.getName();
        this.nric = patientToCopy.getNric();
        this.phone = patientToCopy.getPhone();
        this.email = patientToCopy.getEmail();
        this.address = patientToCopy.getAddress();
        this.tags = new HashSet<>(patientToCopy.getTags());
    }

    /**
     * Sets the {@code Nric} of the {@code Patient} that we are building.
     */
    public PatientBuilder withNric(String nric) {
        this.nric = new Nric(nric);
        return this;
    }

    /**
     * Builds a new Patient object for testing.
     * @return a Patient object with the parameters specified in this
     * object.
     */
    @Override
    public Patient build() {
        return new Patient(name, phone, email, nric, address, tags);
    }
}
