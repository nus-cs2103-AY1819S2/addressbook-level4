package seedu.address.testutil;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Phone;
import seedu.address.model.person.patient.Patient;
import seedu.address.model.tag.ConditionTag;
import seedu.address.model.tag.Conditions;

/**
 * Utility Class for building Patient objects.
 */
public class PatientBuilder extends PersonBuilder {

    public static final Set<ConditionTag> DEFAULT_CONDITIONS_SET = new HashSet<>(
        Arrays.asList(new ConditionTag("Physiotherapy"),
            new ConditionTag("Dialysis")));
    private Conditions conditions;

    public PatientBuilder() {
        super();
        this.conditions = new Conditions(DEFAULT_CONDITIONS_SET);
    }

    /**
     * Initializes the PatientBuilder with the data of {@code
     * healthWorkerToCopy}
     */
    public PatientBuilder(Patient patientToCopy) {
        super(patientToCopy);
        this.conditions = patientToCopy.getConditions();
    }

    /**
     * Sets the {@code Conditions} of the patient that we are building.
     */
    public PatientBuilder withConditionTags(String... conditions) {
        HashSet<ConditionTag> set = new HashSet<>();
        Arrays.stream(conditions).forEach(cond -> set.add(new ConditionTag(cond)));
        this.conditions = new Conditions(set);
        return this;
    }

    /**
     * Specifies the nric of the patient object that we are building.
     */
    public PatientBuilder withNric(String nric) {
        this.nric = new Nric(nric);
        return this;
    }

    /**
     * Sets the email of the {@code Patient} in the {@code Request} object that we are building.
     */
    public PatientBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Initializes the PatientBuilder with the data of {@code address}
     */
    @Override
    public PatientBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Initialises the PatientBuilder with the data of {@code name}
     */
    public PatientBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Initialises the PatientBuilder with the data of {@code phone}
     */
    public PatientBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code conditions} of the {@code Patient} that we are building.
     */
    public PatientBuilder withConditions(Conditions conditions) {
        this.conditions = new Conditions(conditions);
        return this;
    }

    /**
     * Builds a new Patient object for testing.
     *
     * @return a Patient object with the parameters specified in this
     * object.
     */
    @Override
    public Patient build() {
        return new Patient(name, phone, email, nric, address, tags, conditions);
    }
}
