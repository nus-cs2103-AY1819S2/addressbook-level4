package seedu.address.model.patient;

import java.util.Set;

import seedu.address.model.patient.exceptions.TeethLayoutException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Represents a patient which is extend from the Person class.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Patient extends Person {
    private static final String NONE = "none";
    private static final String CHILD = "child";
    private static final String ADULT = "adult";
    private Teeth teeth = null;
    private boolean buildSpecified = false;

    /**
     * Every field must be present and not null.
     */
    public Patient(Name name, Phone phone, Email email, Address address, Set<Tag> tags) {
        super(name, phone, email, address, tags);
    }

    public Patient(Name name, Phone phone, Email email, Address address, Set<Tag> tags,
                   Person personToCopy, int copyCount) {
        super(name, phone, email, address, tags, personToCopy, copyCount);
    }

    /**
     * Takes in the age of the patient and infers the teeth build he/she has.
     * @param age the age of the patient.
     */
    private void inferTeethBuild(int age) {
        if (age < 2) {
            specifyBuild(NONE);
        } else if (age < 13) {
            specifyBuild(CHILD);
        } else {
            specifyBuild(ADULT);
        }
    }

    /**
     * Builds a default no teeth layout.
     * Which represents a baby with no teeth.
     */
    public void specifyNone() {
        specifyBuild(NONE);
    }

    /**
     * Builds a default child teeth layout.
     * Which represents a child with child teeth.
     */
    public void specifyChild() {
        specifyBuild(CHILD);
    }

    /**
     * Builds a default adult teeth layout.
     * Which represents a teenager/adult with permanent teeth.
     */
    public void specifyAdult() {
        specifyBuild(ADULT);
    }

    /**
     * Build a default none/child/adult teeth layout, according to the parameters.
     */
    private void specifyBuild(String teethLayout) {
        switch (teethLayout) {
        case NONE:
            teeth = null; // Stub
            buildSpecified = true;
            break;
        case CHILD:
            teeth = null; // Stub
            buildSpecified = true;
            break;
        case ADULT:
            teeth = null; // Stub
            buildSpecified = true;
            break;
        default:
            throw new TeethLayoutException();
        }
    }

    public Teeth getTeeth() {
        return teeth;
    }

    public boolean isBuildSpecified() {
        return buildSpecified;
    }
}
