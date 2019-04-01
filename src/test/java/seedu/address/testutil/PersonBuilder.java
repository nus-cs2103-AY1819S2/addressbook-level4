package seedu.address.testutil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.model.datetime.DateOfBirth;
import seedu.address.model.description.Description;
import seedu.address.model.nextofkin.NextOfKin;
import seedu.address.model.nextofkin.NextOfKinRelation;
import seedu.address.model.patient.DrugAllergy;
import seedu.address.model.patient.Nric;
import seedu.address.model.patient.Patient;
import seedu.address.model.patient.Sex;
import seedu.address.model.patient.Teeth;
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
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_NRIC = "S9512345A";
    public static final String DEFAULT_DOB = "09-06-1995";
    public static final String DEFAULT_SEX = "M";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_DRUG_ALLERGY = "NIL";
    public static final String DEFAULT_DESC = "Just another patient.";

    private Name name;
    private Nric nric;
    private DateOfBirth dateOfBirth;
    private Sex sex;
    private Phone phone;
    private Email email;
    private Address address;
    private Set<Tag> tags;
    private List<Record> records;
    private Teeth teeth;
    private NextOfKin nextOfKin;
    private DrugAllergy drugAllergy;
    private Description description;

    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        nric = new Nric(DEFAULT_NRIC);
        dateOfBirth = new DateOfBirth(DEFAULT_DOB);
        sex = new Sex(DEFAULT_SEX);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        drugAllergy = new DrugAllergy(DEFAULT_DRUG_ALLERGY);
        description = new Description(DEFAULT_DESC);

        nextOfKin = new NextOfKinBuilder().build();

        tags = new HashSet<>();
        tags.add(new TeethTag(TemplateTags.ADULT));
        tags.add(new StatusTag(TemplateTags.HEALTHY));
        records = new ArrayList<>();

        int[] teethLayout = new int[Teeth.PERMANENTTEETHCOUNT];
        teeth = new Teeth(teethLayout);
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        if (personToCopy instanceof Patient) {
            name = personToCopy.getName();
            nric = ((Patient) personToCopy).getNric();
            dateOfBirth = ((Patient) personToCopy).getDateOfBirth();
            sex = ((Patient) personToCopy).getSex();
            phone = personToCopy.getPhone();
            email = personToCopy.getEmail();
            address = personToCopy.getAddress();
            tags = new HashSet<>(personToCopy.getTags());
            records = new ArrayList<>();
        } else {
            throw new PersonIsNotPatient();
        }
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Nric} of the {@code Person} that we are building.
     */
    public PersonBuilder withNric(String nric) {
        this.nric = new Nric(nric);
        return this;
    }

    /**
     * Sets the {@code DateOfBirth} of the {@code Person} that we are building.
     */
    public PersonBuilder withDob(String dateOfBirth) {
        this.dateOfBirth = new DateOfBirth(dateOfBirth);
        return this;
    }

    /**
     * Sets the {@code Sex} of the {@code Person} that we are building.
     */
    public PersonBuilder withSex(String sex) {
        this.sex = new Sex(sex);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
    public PersonBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code DrugAllergy} of the {@code Person} that we are building.
     */
    public PersonBuilder withDrugAllergy(String drugAllergy) {
        this.drugAllergy = new DrugAllergy(drugAllergy);
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code Person} that we are building.
     */
    public PersonBuilder withDescription(String description) {
        this.description = new Description(description);
        return this;
    }

    /**
     * Sets the {@code NextOfKin} of the {@code Person} that we are building.
     */
    public PersonBuilder withNextOfKin(NextOfKin kin) {
        this.nextOfKin = kin;
        return this;
    }

    /**
     * Builds a new Person based on the given parameters.
     */
    public Person build() {
        return new Patient(name, phone, email, address, tags, nric, dateOfBirth, records, teeth, sex, drugAllergy,
            nextOfKin, description);
    }

    /**
     * A utility class to help with building NextOfKin objects.
     */
    public static class NextOfKinBuilder {
        private static final String DEFAULT_KIN_NAME = "Benny Pauline";
        private static final String DEFAULT_KIN_RELATION = "Father";
        private static final String DEFAULT_KIN_PHONE = "95121347";
        private static final String DEFAULT_KIN_ADDRESS = "123, Jurong West Ave 6, #08-111";
        private static final String DEFAULT_KIN_EMAIL = "No email specified";

        private Name kinName;
        private NextOfKinRelation kinRelation;
        private Phone kinPhone;
        private Address kinAddress;
        private Email kinEmail;

        public NextOfKinBuilder() {
            this.kinName = new Name(DEFAULT_KIN_NAME);
            this.kinRelation = new NextOfKinRelation(DEFAULT_KIN_RELATION);
            this.kinPhone = new Phone(DEFAULT_KIN_PHONE);
            this.kinAddress = new Address(DEFAULT_KIN_ADDRESS);
            this.kinEmail = new Email(DEFAULT_KIN_EMAIL);
        }

        /**
         * Sets the {@code Name} of the {@code NextOfKin} that we are building.
         */
        public NextOfKinBuilder withKinName(String kinName) {
            this.kinName = new Name(kinName);
            return this;
        }

        /**
         * Sets the {@code NextOfKinRelation} of the {@code NextOfKin} that we are building.
         */
        public NextOfKinBuilder withKinRelation(String kinRelation) {
            this.kinRelation = new NextOfKinRelation(kinRelation);
            return this;
        }

        /**
         * Sets the {@code Phone} of the {@code NextOfKin} that we are building.
         */
        public NextOfKinBuilder withKinPhone(String kinPhone) {
            this.kinPhone = new Phone(kinPhone);
            return this;
        }

        /**
         * Sets the {@code Address} of the {@code NextOfKin} that we are building.
         */
        public NextOfKinBuilder withKinAddress(String kinAddress) {
            this.kinAddress = new Address(kinAddress);
            return this;
        }

        public NextOfKin build() {
            return new NextOfKin(kinName, kinPhone, kinEmail, kinAddress, null, kinRelation);
        }
    }

}
