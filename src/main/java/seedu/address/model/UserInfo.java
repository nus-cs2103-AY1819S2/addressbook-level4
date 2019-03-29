package seedu.address.model;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import seedu.address.commons.util.InvalidationListenerManager;
import seedu.address.model.course.Course;
import seedu.address.model.moduletaken.Grade;
import seedu.address.model.moduletaken.Hour;
import seedu.address.model.moduletaken.Semester;
import seedu.address.model.util.SampleCourse;

/**
 * Represents User's information.
 */
public class UserInfo implements Observable {

    private Course course;
    private Semester currentSemester;
    private SemLimit semLimit;
    private final InvalidationListenerManager invalidationListenerManager = new InvalidationListenerManager();

    /**
     * Creates a UserInfo with default values
     */
    public UserInfo() {
        course = SampleCourse.COMPUTER_SCIENCE_ALGORITHMS;
        currentSemester = Semester.valueOf("Y1S1");
        semLimit = new SemLimit(Grade.F, Grade.A, new Hour("0"), new Hour("0"));
    }

    /**
     * Constructs UserInfo class with given course, current semester, and semester limit
     */
    public UserInfo(Course course, Semester currentSemester, SemLimit semLimit) {
        requireAllNonNull(course, currentSemester, semLimit);
        this.course = course;
        this.currentSemester = currentSemester;
        this.semLimit = semLimit;
    }


    public void setCourse(Course course) {
        this.course = course;
    }

    public void setCurrentSemester(Semester currentSemester) {
        this.currentSemester = currentSemester;
    }

    public void setSemLimit(SemLimit semLimit) {
        this.semLimit = semLimit;
    }

    public Course getCourse() {
        return course;
    }

    public Semester getCurrentSemester() {
        return currentSemester;
    }

    public SemLimit getSemLimit() {
        return semLimit;
    }

    public void addListener(InvalidationListener listener) {
        invalidationListenerManager.addListener(listener);
    }

    public void removeListener(InvalidationListener listener) {
        invalidationListenerManager.removeListener(listener);
    }

    /**
     * Notifies listeners that the address book has been modified.
     */
    private void indicateModified() {
        invalidationListenerManager.callListeners(this);
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof UserInfo)) {
            return false;
        }

        UserInfo other = (UserInfo) obj;

        return this.course.equals(other.course)
                && this.currentSemester.equals(other.currentSemester)
                && this.semLimit.equals(other.semLimit);
    }
}
