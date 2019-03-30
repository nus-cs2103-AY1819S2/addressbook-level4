package seedu.address.model.patient;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.datetime.DateOfBirth;
import seedu.address.model.description.Description;
import seedu.address.model.nextofkin.NextOfKin;
import seedu.address.model.patient.exceptions.PersonIsNotPatient;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.record.Record;
import seedu.address.model.tag.StatusTag;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TeethTag;
import seedu.address.model.tag.TemplateTags;

/**
 * Represents a patient which is extend from the Person class.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Patient extends Person {
    private static final String NONE = "none";
    private static final String CHILD = "child";
    private static final String ADULT = "adult";

    private Sex sex;
    private Nric nric;
    private DateOfBirth dateOfBirth;
    private Teeth teeth = null;
    private List<Record> records = new ArrayList<>();
    private NextOfKin nextOfKin;
    private DrugAllergy drugAllergy;
    private Description patientDesc;

    /**
     * Every field must be present and not null.
     * Used by add command.
     */
    public Patient(Name name, Phone phone, Email email, Address address, Set<Tag> tags, Nric nric,
                   DateOfBirth dateOfBirth, Sex sex, DrugAllergy drugAllergy, NextOfKin nextOfKin,
                   Description describe) {
        super(name, phone, email, address, tags);
        requireAllNonNull(nric, dateOfBirth, sex);
        this.sex = sex;
        this.nric = nric;
        this.dateOfBirth = dateOfBirth;
        this.nextOfKin = nextOfKin;
        buildAdultTeeth();
        this.drugAllergy = drugAllergy;
        this.patientDesc = describe;
        records.add(new Record());
    }

    /**
     * Used by JSON.
     * Every field must be present and not null.
     */
    public Patient(Name name, Phone phone, Email email, Address address, Set<Tag> tags, Nric nric,
                   DateOfBirth dateOfBirth, List<Record> records, Teeth teeth, Sex sex, DrugAllergy drugAllergy,
                   NextOfKin kin, Description describe) {
        super(name, phone, email, address, tags);
        requireAllNonNull(nric, dateOfBirth, records, sex);
        this.sex = sex;
        this.nric = nric;
        this.dateOfBirth = dateOfBirth;
        this.records = records;
        this.records.sort(Comparator.comparing(Record::getRecordDate));
        this.teeth = teeth;
        this.drugAllergy = drugAllergy;
        this.nextOfKin = kin;
        this.patientDesc = describe;
    }

    public Patient(Name name, Phone phone, Email email, Address address, Set<Tag> tags, Nric nric,
                   DateOfBirth dateOfBirth, Person personToCopy, int copyCount, Sex sex) {
        super(name, phone, email, address, tags, personToCopy, copyCount);
        requireAllNonNull(nric, dateOfBirth, sex);
        this.sex = sex;
        this.nric = nric;
        this.dateOfBirth = dateOfBirth;
        buildAdultTeeth();
        updateTags();
    }

    /**
     * Adds a new medical record to a patient.
     * @param record the medical record to be added.
     */
    public void addRecord(Record record) {
        this.records.add(0, record);
        this.records.sort(Comparator.comparing(Record::getRecordDate));
    }

    /**
     * Adds a new medical record to a patient.
     * @param record the medical record to be added.
     */
    public void removeRecord(Record record) {
        this.records.remove(record);
    }

    /**
     * Build a default none/child/adult teeth layout, according to the parameters.
     * Adds relevant tags to patient if not initialised.
     * Should be run only when a new Patient is created, not when it is retrieved from storage.
     */
    private void buildAdultTeeth() {
        teeth = new Teeth();
        addRelevantTags();
    }

    /**
     * Add relevant teeth type and health tags to a patient.
     */
    private void addRelevantTags() {
        addTag(new TeethTag(TemplateTags.ADULT));
        addTag(new StatusTag(TemplateTags.HEALTHY));
    }

    /**
     * Looks at the current status of the teeth,
     * and updates the status tag of the teeth appropriately.
     */
    public void updateTags() {
        boolean absent = teeth.checkFor(Teeth.ABSENT);
        boolean problematic = teeth.checkFor(Teeth.PROBLEMATIC);

        if (absent) {
            addTag(new StatusTag(TemplateTags.ABSENTTOOTH));
        } else if (problematic) {
            addTag(new StatusTag(TemplateTags.STATUSTOOTH));
        } else {
            addTag(new StatusTag(TemplateTags.HEALTHY));
        }

        // Only one option at the moment.
        addTag(new TeethTag(TemplateTags.ADULT));
    }

    /**
     * Adds or replace similar tags of the patient.
     * @param tag the tag to be added or overwrite the existing.
     */
    private void addTag(Tag tag) {
        if (tag instanceof TeethTag) {
            for (Tag t : tags) {
                if (t instanceof TeethTag) {
                    tags.remove(t);
                    break;
                }
            }
        } else if (tag instanceof StatusTag) {
            for (Tag t : tags) {
                if (t instanceof StatusTag) {
                    tags.remove(t);
                    break;
                }
            }
        }
        tags.add(tag);
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

    public DateOfBirth getDateOfBirth() {
        return dateOfBirth;
    }

    public List<Record> getRecords() {
        return records;
    }

    public Sex getSex() {
        return sex;
    }

    public NextOfKin getNextOfKin() {
        return nextOfKin;
    }

    public DrugAllergy getDrugAllergy() {
        return drugAllergy;
    }

    public Description getPatientDesc() {
        return patientDesc;
    }

    /**
     * Return a Patient with changed tags
     * @return a new Patient instance.
     */
    public Patient copy() {
        return new Patient(this.name, this.phone, this.email, this.address, tags, this.nric, this.getDateOfBirth(),
                this.records, this.teeth, this.sex, this.drugAllergy, this.nextOfKin, this.patientDesc);
    }

    /**
     * Returns true if both patients has the same NRIC.
     */
    @Override
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == null) {
            return false;
        } else if (otherPerson instanceof Patient) {
            if (isCopy() || otherPerson.isCopy()) {
                return false;
            }
            return nric.equals(((Patient) otherPerson).getNric());
        } else {
            System.out.println(otherPerson);
            throw new PersonIsNotPatient();
        }
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, tags, nric, dateOfBirth, records);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(this.sex)
                .append(" NRIC: ")
                .append(nric.getNric())
                .append(" Date of Birth: ")
                .append(dateOfBirth.getDate())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Email: ")
                .append(getEmail())
                .append(" Address: ")
                .append(getAddress())
                .append("Drug Allergy: ")
                .append(getDrugAllergy())
                .append(" Tags: ");
        getTags().forEach(builder::append);

        //Next Of Kin fields
        builder.append(" Next Of Kin Name: ")
                .append(this.nextOfKin.getName())
            .append(" Next Of Kin Relation: ")
            .append(this.nextOfKin.getKinRelation())
            .append(" Next Of Kin Phone: ")
            .append(this.nextOfKin.getPhone())
            .append(" Next Of Kin Address: ")
            .append(this.nextOfKin.getAddress());

        builder.append("Desciption: ")
            .append("[ " + getPatientDesc() + "]");

        return builder.toString();
    }
}
