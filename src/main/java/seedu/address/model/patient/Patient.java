package seedu.address.model.patient;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.record.Record;
import seedu.address.model.tag.Tag;

/**
 * Represents a patient which is extend from the Person class.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Patient extends Person {
    private static final String NONE = "none";
    private static final String CHILD = "child";
    private static final String ADULT = "adult";
    private Nric nric;
    private Date dateOfBirth;
    private Teeth teeth = null;
    private boolean buildSpecified = false;
    private ArrayList<Record> records = new ArrayList<>();

    /**
     * Every field must be present and not null.
     */
    public Patient(Name name, Phone phone, Email email, Address address, Set<Tag> tags, Nric nric, Date dateOfBirth) {
        super(name, phone, email, address, tags);
        requireAllNonNull(nric, dateOfBirth);
        this.nric = nric;
        this.dateOfBirth = dateOfBirth;
        inferTeethBuild();
    }

    private Patient(Name name, Phone phone, Email email, Address address, Set<Tag> tags,
                   Person personToCopy, int copyCount, Nric nric, Date dateOfBirth) {
        super(name, phone, email, address, tags, personToCopy, copyCount);
        requireAllNonNull(nric, dateOfBirth);
        this.nric = nric;
        this.dateOfBirth = dateOfBirth;
        inferTeethBuild();
    }

    /**
     * Adds a new medical record to a patient.
     * @param record the medical record to be added.
     */
    public void addRecord(Record record) {
        this.records.add(record);
    }

    /**
     * Takes in the age of the patient and infers the teeth build he/she has.
     */
    private void inferTeethBuild() {
        int age = getPatientAge();
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
        buildSpecified = true;
        teeth = new Teeth(teethLayout);
    }

    /**
     * Using the patient's year of birth and the current year, derive his or her age.
     * @return the age of the patient.
     */
    private int getPatientAge() {
        Date today = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        int currentYear = cal.get(Calendar.DAY_OF_YEAR);
        cal.setTime(dateOfBirth);
        int birthYear = cal.get(Calendar.DAY_OF_YEAR);
        return currentYear - birthYear;
    }

    /**
     * @return another instance of the same person
     * {@code Tag} Copy is added
     */
    @Override
    public Patient copy() {
        if (isCopy()) {
            super.copy();
        }
        copyCount++;
        return new Patient(name, phone, email, address, tags, this, copyCount, nric, dateOfBirth);
    }

    public void setRecords(ArrayList<Record> records) {
        this.records = records;
    }

    public Teeth getTeeth() {
        return teeth;
    }

    public Nric getNric() {
        return nric;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public boolean isBuildSpecified() {
        return buildSpecified;
    }

    public ArrayList<Record> getRecords() {
        return records;
    }

    /**
     * Returns true if both patients of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two patients.
     */
    public boolean isSamePatient(Person otherPerson) {
        return super.isSamePerson(otherPerson);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, tags, nric, dateOfBirth, records);
    }

}
