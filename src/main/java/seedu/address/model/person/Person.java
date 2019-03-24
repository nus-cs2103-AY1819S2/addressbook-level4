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
    //TODO: change Name to ModuleInfoCode
    private final Name moduleInfo;
    private final Semester semester;

    // Data fields
    private final GradeRange gradeRange;
    private final Workload workload;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Person(Name moduleInfo, Semester semester, Grade expectedMinGrade, Grade expectedMaxGrade,
                  Hour lectureHour, Set<Tag> tags) {
        requireAllNonNull(moduleInfo, semester, expectedMinGrade, expectedMaxGrade, tags);
        this.moduleInfo = moduleInfo;
        this.semester = semester;
        this.gradeRange = new GradeRange(expectedMinGrade, expectedMaxGrade);
        this.workload = new Workload(lectureHour, new Hour("0"),
                new Hour("0"), new Hour("0"), new Hour("0")); //to be populated based on module info
        this.tags.addAll(tags);
    }

    public Name getModuleInfo() {
        return moduleInfo;
    }

    public Semester getSemester() {
        return semester;
    }

    GradeRange getGradeRange() {
        return gradeRange;
    }

    public Grade getExpectedMinGrade() {
        return gradeRange.getMin();
    }

    public Grade getExpectedMaxGrade() {
        return gradeRange.getMax();
    }

    public Hour getLectureHour() {
        return workload.getLectureHour();
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Checks if this module has been finished.
     * @return true if the module has been finished, false otherwise.
     */
    public boolean isFinished() {
        // TODO: compare semester taken and current semester
        return false;
    }

    /**
     * Checks if this module has been passed.
     * @return true if this module has been passed, false otherwise.
     */
    public boolean isPassed() {
        return isFinished() && getGradeRange().getMax().isPassingGrade();
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
                && otherPerson.getModuleInfo().equals(getModuleInfo())
                && (otherPerson.getSemester().equals(getSemester()));
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
        return otherPerson.getModuleInfo().equals(getModuleInfo())
                && otherPerson.getSemester().equals(getSemester())
                && otherPerson.getExpectedMinGrade().equals(getExpectedMinGrade())
                && otherPerson.getExpectedMaxGrade().equals(getExpectedMaxGrade())
                && otherPerson.getLectureHour().equals(getLectureHour())
                && otherPerson.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(moduleInfo, semester, gradeRange, workload, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getModuleInfo())
                .append(" Semester: ")
                .append(getSemester())
                .append(" Expected Min Grade: ")
                .append(getExpectedMinGrade())
                .append(" Expected Max Grade: ")
                .append(getExpectedMaxGrade())
                .append(" Lecture Hour: ")
                .append(getLectureHour())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }
}
