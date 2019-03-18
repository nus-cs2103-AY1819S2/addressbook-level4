package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;
    private final Nric nric;

    // Data fields
    private final Gender gender;
    private final Race race;
    private final Address address;
    private final School school;
    private final Major major;
    private final Grade grade;
    private final InterviewScores interviewScores;
    private final Set<KnownProgLang> knownProgLangs = new HashSet<>();
    private final Set<PastJob> pastjobs = new HashSet<>();
    private final Set<JobsApply> jobsApply = new HashSet<>();
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Nric nric, Gender gender, Race race, Address address,
            School school, Major major, Grade grade, Set<KnownProgLang> knownProgLangs, Set<PastJob> pastjobs,
                  Set<JobsApply> jobsApply, InterviewScores interviewScores, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, nric, gender, race, address, school, major, grade,
                knownProgLangs, pastjobs, jobsApply, interviewScores, tags);

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
        this.knownProgLangs.addAll(knownProgLangs);
        this.pastjobs.addAll(pastjobs);
        this.jobsApply.addAll(jobsApply);
        this.interviewScores = interviewScores;
        this.tags.addAll(tags);
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Nric getNric() {
        return nric;
    }

    public Gender getGender() {
        return gender;
    }

    public Race getRace() {
        return race;
    }

    public Address getAddress() {
        return address;
    }

    public School getSchool() {
        return school;
    }

    public Major getMajor() {
        return major;
    }

    public Grade getGrade() {
        return grade;
    }

    public InterviewScores getInterviewScores() {
        return interviewScores;
    }

    /**
     * Returns an immutable known programming language set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public final Set<KnownProgLang> getKnownProgLangs() {
        return Collections.unmodifiableSet(knownProgLangs);
    }

    /**
     * Returns an immutable past job set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public final Set<PastJob> getPastJobs() {
        return Collections.unmodifiableSet(pastjobs);
    }

    /**
     * Returns an immutable jobs applying for set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public final Set<JobsApply> getJobsApply() {
        return Collections.unmodifiableSet(jobsApply);
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both persons of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getNric().equals(getNric());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return otherPerson.getName().equals(getName())
                && otherPerson.getPhone().equals(getPhone())
                && otherPerson.getEmail().equals(getEmail())
                && otherPerson.getNric().equals(getNric())
                && otherPerson.getGender().equals(getGender())
                && otherPerson.getRace().equals(getRace())
                && otherPerson.getAddress().equals(getAddress())
                && otherPerson.getSchool().equals(getSchool())
                && otherPerson.getPastJobs().equals(getPastJobs())
                && otherPerson.getKnownProgLangs().equals(getKnownProgLangs())
                && otherPerson.getJobsApply().equals(getJobsApply())
                && otherPerson.getInterviewScores().equals(getInterviewScores())
                && otherPerson.getMajor().equals(getMajor())
                && otherPerson.getGrade().equals(getGrade())
                && otherPerson.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, nric, gender, race, address, school, major, grade,
                knownProgLangs, pastjobs, jobsApply, interviewScores, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Email: ")
                .append(getEmail())
                .append(" Nric: ")
                .append(getNric())
                .append(" Gender: ")
                .append(getGender())
                .append(" Race: ")
                .append(getRace())
                .append(" Address: ")
                .append(getAddress())
                .append(" School: ")
                .append(getSchool())
                .append(" Major: ")
                .append(getMajor())
                .append(" Grade: ")
                .append(getGrade())
                .append(" Interview Scores: ")
                .append(getInterviewScores());
        builder.append(" Past jobs: ");
        getPastJobs().forEach(builder::append);
        builder.append(" Known Programming Language(s): ");
        getKnownProgLangs().forEach(builder::append);
        builder.append(" Job(s) Applying For: ");
        getJobsApply().forEach(builder::append);
        builder.append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
