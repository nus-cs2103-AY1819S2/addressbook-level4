package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

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
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final String DEFAULT_NRIC = "S1245756B";
    public static final String DEFAULT_GENDER = "Female";
    public static final String DEFAULT_RACE = "Chinese";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_SCHOOL = "NUS";
    public static final String DEFAULT_MAJOR = "CS";
    public static final String DEFAULT_GRADE = "3.00";
    public static final String DEFAULT_INTERVIEWSCORES = "1,2,3,4,5";
    public static final String DEFAULT_JOBSAPPLY = "Software Engineer";


    private Name name;
    private Phone phone;
    private Email email;
    private Nric nric;
    private Gender gender;
    private Race race;
    private Address address;
    private School school;
    private Major major;
    private Grade grade;
    private InterviewScores interviewScores;
    private Set<KnownProgLang> knownProgLangs;
    private Set<PastJob> pastjobs;
    private Set<JobsApply> jobsApply;
    private Set<Tag> tags;

    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        nric = new Nric(DEFAULT_NRIC);
        gender = new Gender(DEFAULT_GENDER);
        race = new Race(DEFAULT_RACE);
        address = new Address(DEFAULT_ADDRESS);
        school = new School(DEFAULT_SCHOOL);
        major = new Major(DEFAULT_MAJOR);
        grade = new Grade(DEFAULT_GRADE);
        knownProgLangs = new HashSet<>();
        pastjobs = new HashSet<>();
        jobsApply = new HashSet<>();
        jobsApply.add(new JobsApply(DEFAULT_JOBSAPPLY));
        interviewScores = new InterviewScores(DEFAULT_INTERVIEWSCORES);
        tags = new HashSet<>();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        nric = personToCopy.getNric();
        gender = personToCopy.getGender();
        race = personToCopy.getRace();
        address = personToCopy.getAddress();
        school = personToCopy.getSchool();
        major = personToCopy.getMajor();
        grade = personToCopy.getGrade();
        knownProgLangs = new HashSet<>(personToCopy.getKnownProgLangs());
        pastjobs = new HashSet<>(personToCopy.getPastJobs());
        jobsApply = new HashSet<>(personToCopy.getJobsApply());
        interviewScores = personToCopy.getInterviewScores();
        tags = new HashSet<>(personToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code knownProgLangs} into a {@code Set<KnowProgLang>} and set it to the {@code Person}
     * that we are building.
     */
    public PersonBuilder withKnownProgLangs(String ... knownProgLangs) {
        this.knownProgLangs = SampleDataUtil.getKnownProgLangSet(knownProgLangs);
        return this;
    }

    /**
     * Parses the {@code jobsApply} into a {@code Set<JobsApply>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withJobsApply(String ... jobsApply) {
        this.jobsApply = SampleDataUtil.getJobsApplySet(jobsApply);
        return this;
    }

    /**
     * Parses the {@code pastjobs} into a {@code Set<PastJob>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withPastJobs(String ... pastjobs) {
        this.pastjobs = SampleDataUtil.getPastJobSet(pastjobs);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
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
     * Sets the {@code Gender} of the {@code Person} that we are building.
     */
    public PersonBuilder withGender(String gender) {
        this.gender = new Gender(gender);
        return this;
    }

    /**
     * Sets the {@code Grade} of the {@code Person} that we are building.
     */
    public PersonBuilder withGrade(String grade) {
        this.grade = new Grade(grade);
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
     * Sets the {@code Nric} of the {@code Person} that we are building.
     */
    public PersonBuilder withNric(String nric) {
        this.nric = new Nric(nric);
        return this;
    }

    /**
     * Sets the {@code InterviewScores} of the {@code Person} that we are building.
     */
    public PersonBuilder withInterviewScores(String interviewScores) {
        this.interviewScores = new InterviewScores(interviewScores);
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
     * Sets the {@code Race} of the {@code Person} that we are building.
     */
    public PersonBuilder withRace(String race) {
        this.race = new Race(race);
        return this;
    }

    /**
     * Sets the {@code School} of the {@code Person} that we are building.
     */
    public PersonBuilder withSchool(String school) {
        this.school = new School(school);
        return this;
    }

    /**
     * Sets the {@code Major} of the {@code Person} that we are building.
     */
    public PersonBuilder withMajor(String major) {
        this.major = new Major(major);
        return this;
    }

    /***/
    public Person build() {
        return new Person(name, phone, email, nric, gender, race, address, school, major, grade, knownProgLangs,
                pastjobs, jobsApply, interviewScores, tags);
    }

}
