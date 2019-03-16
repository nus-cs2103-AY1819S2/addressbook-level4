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
import seedu.address.model.person.Gender;
import seedu.address.model.person.Grade;
import seedu.address.model.person.InterviewScores;
import seedu.address.model.person.JobsApply;
import seedu.address.model.person.KnownProgLang;
import seedu.address.model.person.Major;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
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
    private final String nric;
    private final String gender;
    private final String race;
    private final String address;
    private final String school;
    private final String major;
    private final String grade;
    private final String interviewScores;
    private final List<JsonAdaptedPastJob> pastjobed = new ArrayList<>();
    private final List<JsonAdaptedKnownProgLang> knownProgLang = new ArrayList<>();
    private final List<JsonAdaptedJobsApply> jobsApply = new ArrayList<>();
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
                             @JsonProperty("email") String email, @JsonProperty("nric") String nric,
                             @JsonProperty("gender") String gender, @JsonProperty("race") String race,
                             @JsonProperty("address") String address, @JsonProperty("school") String school,
                             @JsonProperty("major") String major, @JsonProperty("grade") String grade,
                             @JsonProperty("knownProgLang") List<JsonAdaptedKnownProgLang> knownProgLang,
                             @JsonProperty("pastjobed") List<JsonAdaptedPastJob> pastjobed,
                             @JsonProperty("jobsApply") List<JsonAdaptedJobsApply> jobsApply,
                             @JsonProperty("interviewScores") String interviewScores,
                             @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {

        this.name = name;
        this.phone = phone;
        this.email = email;
        this.nric = nric;
        this.gender = gender;
        this.race = race;
        this.address = address;
        this.school = school;
        this.major = major;
        this.grade = grade;
        this.interviewScores = interviewScores;
        if (knownProgLang != null) {
            this.knownProgLang.addAll(knownProgLang);
        }
        if (pastjobed != null) {
            this.pastjobed.addAll(pastjobed);
        }
        if (jobsApply != null) {
            this.jobsApply.addAll(jobsApply);
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
        nric = source.getNric().value;
        gender = source.getGender().value;
        race = source.getRace().value;
        address = source.getAddress().value;
        school = source.getSchool().value;
        major = source.getMajor().value;
        grade = source.getGrade().value;
        interviewScores = source.getInterviewScores().value;
        knownProgLang.addAll(source.getKnownProgLangs().stream()
                .map(JsonAdaptedKnownProgLang::new)
                .collect(Collectors.toList()));
        pastjobed.addAll(source.getPastJobs().stream()
                .map(JsonAdaptedPastJob::new)
                .collect(Collectors.toList()));
        jobsApply.addAll(source.getJobsApply().stream()
                .map(JsonAdaptedJobsApply::new)
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
        final List<JobsApply> personJobsApply = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            personTags.add(tag.toModelType());
        }
        for (JsonAdaptedPastJob pastjob : pastjobed) {
            personPastJobs.add(pastjob.toModelType());
        }
        for (JsonAdaptedKnownProgLang knownProgLang : knownProgLang) {
            personKnownProgLang.add(knownProgLang.toModelType());
        }

        for (JsonAdaptedJobsApply jobapply : jobsApply) {
            personJobsApply.add(jobapply.toModelType());
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

        if (nric == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Nric.class.getSimpleName()));
        }
        if (!Nric.isValidNric(nric)) {
            throw new IllegalValueException(Nric.MESSAGE_CONSTRAINTS);
        }
        final Nric modelNric = new Nric(nric);

        if (gender == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Gender.class.getSimpleName()));
        }
        if (!Gender.isValidGender(gender)) {
            throw new IllegalValueException(Gender.MESSAGE_CONSTRAINTS);
        }
        final Gender modelGender = new Gender(gender);

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

        if (grade == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Grade.class.getSimpleName()));
        }
        if (!Grade.isValidGrade(grade)) {
            throw new IllegalValueException(Grade.MESSAGE_CONSTRAINTS);
        }
        final Grade modelGrade = new Grade(grade);

        if (interviewScores == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, InterviewScores
                    .class.getSimpleName()));
        }
        if (!InterviewScores.isValidInterviewScores(interviewScores)) {
            throw new IllegalValueException(InterviewScores.MESSAGE_CONSTRAINTS);
        }
        final InterviewScores modelInterviewScores = new InterviewScores(interviewScores);

        final Set<KnownProgLang> modelKnownProgLang = new HashSet<>(personKnownProgLang);
        final Set<Tag> modelTags = new HashSet<>(personTags);
        final Set<PastJob> modelPastJobs = new HashSet<>(personPastJobs);
        final Set<JobsApply> modelJobsApply = new HashSet<>(personJobsApply);
        return new Person(modelName, modelPhone, modelEmail, modelNric, modelGender, modelRace, modelAddress,
                modelSchool, modelMajor, modelGrade, modelKnownProgLang, modelPastJobs, modelJobsApply,
                modelInterviewScores, modelTags);
    }

}
