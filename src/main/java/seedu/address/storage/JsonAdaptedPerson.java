package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.KnownProgLang;
import seedu.address.model.person.Major;
import seedu.address.model.person.Name;
import seedu.address.model.person.PastJob;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Race;
import seedu.address.model.person.School;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private final String name;
    private final String phone;
    private final String email;
    private final String race;
    private final String address;
    private final String school;
    private final String major;
    private final List<JsonAdaptedPastJob> pastjobed = new ArrayList<>();
    private final List<JsonAdaptedKnownProgLang> knownProgLang = new ArrayList<>();
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
                             @JsonProperty("email") String email, @JsonProperty("race") String race,
                             @JsonProperty("address") String address, @JsonProperty("school") String school,
                             @JsonProperty("major") String major,
                             @JsonProperty("knownProgLang") List<JsonAdaptedKnownProgLang> knownProgLang,
                             @JsonProperty("pastjobed") List<JsonAdaptedPastJob> pastjobed,
                             @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {

        this.name = name;
        this.phone = phone;
        this.email = email;
        this.race = race;
        this.address = address;
        this.school = school;
        this.major = major;
        if (knownProgLang != null) {
            this.knownProgLang.addAll(knownProgLang);
        }
        if (pastjobed != null) {
            this.pastjobed.addAll(pastjobed);
        }
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Person source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        race = source.getRace().value;
        address = source.getAddress().value;
        school = source.getSchool().value;
        major = source.getMajor().value;
        knownProgLang.addAll(source.getKnownProgLangs().stream()
                .map(JsonAdaptedKnownProgLang::new)
                .collect(Collectors.toList()));
        pastjobed.addAll(source.getPastJobs().stream()
                .map(JsonAdaptedPastJob::new)
                .collect(Collectors.toList()));
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Person toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        final List<PastJob> personPastJobs = new ArrayList<>();
        final List<KnownProgLang> personKnownProgLang = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            personTags.add(tag.toModelType());
        }
        for (JsonAdaptedPastJob pastjob : pastjobed) {
            personPastJobs.add(pastjob.toModelType());
        }
        for (JsonAdaptedKnownProgLang knownProgLang : knownProgLang) {
            personKnownProgLang.add(knownProgLang.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

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

        if (race == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Race.class.getSimpleName()));
        }
        if (!Race.isValidRace(race)) {
            throw new IllegalValueException(Race.MESSAGE_CONSTRAINTS);
        }
        final Race modelRace = new Race(race);

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);

        if (school == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, School.class.getSimpleName()));
        }
        if (!School.isValidSchool(school)) {
            throw new IllegalValueException(School.MESSAGE_CONSTRAINTS);
        }
        final School modelSchool = new School(school);

        if (major == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Major.class.getSimpleName()));
        }
        if (!Major.isValidMajor(major)) {
            throw new IllegalValueException(Major.MESSAGE_CONSTRAINTS);
        }
        final Major modelMajor = new Major(major);
        final Set<KnownProgLang> modelKnownProgLang = new HashSet<>(personKnownProgLang);
        final Set<Tag> modelTags = new HashSet<>(personTags);
        final Set<PastJob> modelPastJobs = new HashSet<>(personPastJobs);
        return new Person(modelName, modelPhone, modelEmail, modelRace, modelAddress,
            modelSchool, modelMajor, modelKnownProgLang, modelPastJobs, modelTags);
    }

}
