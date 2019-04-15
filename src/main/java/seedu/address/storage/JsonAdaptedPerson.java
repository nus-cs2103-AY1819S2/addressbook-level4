package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.datetime.DateOfBirth;
import seedu.address.model.description.Description;
import seedu.address.model.nextofkin.NextOfKin;
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
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    @JsonProperty("index") private int index;
    private final String name;
    private final String sex;
    private final String nric;
    private final String dateOfBirth;
    private final String phone;
    private final String email;
    private final String address;
    private final String drugAllergy;
    private final String description;
    private final String teeth;
    private final JsonAdaptedNextOfKin nextOfKin;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();
    private final List<JsonAdaptedRecord> records = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("name") String name, @JsonProperty("nric") String nric,
            @JsonProperty("dateOfBirth") String dateOfBirth, @JsonProperty("sex") String sex,
            @JsonProperty("phone") String phone,
            @JsonProperty("email") String email, @JsonProperty("address") String address,
            @JsonProperty("drugAllergy") String drugAllergy,
            @JsonProperty("teeth") String teeth,
            @JsonProperty("tagged") List<JsonAdaptedTag> tagged,
            @JsonProperty("records") List<JsonAdaptedRecord> records,
            @JsonProperty("nextOfKin") JsonAdaptedNextOfKin nextOfKin,
            @JsonProperty("description") String description) {

        this.name = name;
        this.sex = sex;
        this.nric = nric;
        this.dateOfBirth = dateOfBirth;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.teeth = teeth;
        this.drugAllergy = drugAllergy;
        this.description = description;
        this.nextOfKin = nextOfKin;

        if (tagged != null) {
            this.tagged.addAll(tagged);
        }

        if (records != null) {
            this.records.addAll(records);
        }
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Person source) {
        if (source instanceof Patient) {
            name = source.getName().fullName;
            sex = ((Patient) source).getSex().getSex();
            nric = ((Patient) source).getNric().getNric();
            dateOfBirth = ((Patient) source).getDateOfBirth().getRawFormat();
            phone = source.getPhone().value;
            email = source.getEmail().value;
            address = source.getAddress().value;
            drugAllergy = ((Patient) source).getDrugAllergy().toString();
            description = ((Patient) source).getPatientDesc().toString();
            teeth = new JsonAdaptedTeeth(((Patient) source).getTeeth()).getTeethName();
            nextOfKin = new JsonAdaptedNextOfKin(((Patient) source).getNextOfKin());
            tagged.addAll(source.getTags().stream()
                    .map(JsonAdaptedTag::new)
                    .collect(Collectors.toList()));
            records.addAll(((Patient) source).getRecords().stream()
                    .map(JsonAdaptedRecord::new)
                    .collect(Collectors.toList()));
        } else {
            throw new PersonIsNotPatient();
        }
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Person toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            personTags.add(tag.toModelType());
        }

        final List<Record> patientRecords = new ArrayList<>();
        for (JsonAdaptedRecord record : records) {
            patientRecords.add(record.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (sex == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Sex.class.getSimpleName()));
        }
        if (!Sex.isValidSex(sex)) {
            throw new IllegalValueException(Sex.MESSAGE_CONSTRAINTS);
        }
        final Sex modelSex = new Sex(sex);

        if (nric == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Nric.class.getSimpleName()));
        }
        if (!Nric.isValidNric(nric)) {
            throw new IllegalValueException(Nric.MESSAGE_CONSTRAINTS);
        }
        final Nric modelNric = new Nric(nric);

        if (dateOfBirth == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    DateOfBirth.class.getSimpleName()));
        }
        if (!DateOfBirth.isValidDate(dateOfBirth)) {
            throw new IllegalValueException(DateOfBirth.MESSAGE_CONSTRAINTS);
        } else if (!DateOfBirth.isNotFutureDay(dateOfBirth)) {
            throw new IllegalValueException(DateOfBirth.MESSAGE_CONSTRAINTS_FUTURE_DAY);
        }
        final DateOfBirth modelDob = new DateOfBirth(dateOfBirth);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);

        if (drugAllergy == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                DrugAllergy.class.getSimpleName()));
        }
        if (!DrugAllergy.isValidDrugAllergy(drugAllergy)) {
            throw new IllegalValueException(DrugAllergy.MESSAGE_CONSTRAINTS);
        }
        final DrugAllergy modelDrugAllergy = new DrugAllergy(drugAllergy);

        if (description == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                Description.class.getSimpleName()));
        }
        if (!Description.isValidDescription(description)) {
            throw new IllegalValueException(Description.MESSAGE_CONSTRAINTS);
        }
        final Description modelDescription = new Description(description);

        if (teeth == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Teeth.class.getSimpleName()));
        }

        String[] rawLayout = teeth.split(StorageConstants.DIVIDER);
        int[] layout = new int[Teeth.PERMANENTTEETHCOUNT];

        for (int i = 0; i < Teeth.PERMANENTTEETHCOUNT; i++) {
            layout[i] = Integer.parseInt(rawLayout[i]);
        }

        final Teeth modelTeeth = new Teeth(layout);

        final NextOfKin modelNextOfKin = nextOfKin.toModelType();

        final Set<Tag> modelTags = new HashSet<>(personTags);

        final List<Record> modelRecords = patientRecords;

        return new Patient(modelName, modelPhone, modelEmail, modelAddress, modelTags, modelNric,
                modelDob, modelRecords, modelTeeth, modelSex, modelDrugAllergy, modelNextOfKin, modelDescription);
    }

    /**
     * Sets the index of a JsonAdaptedPerson for exporting.
     */
    public void setIndex(int index) {
        this.index = index;
    }

}
