package seedu.address.model;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import seedu.address.commons.util.InvalidationListenerManager;
import seedu.address.model.course.Course;
import seedu.address.model.util.SampleCourse;

/**
 * Represents User's information.
 */
public class UserInfo implements Observable {

    private Course course;
    private final InvalidationListenerManager invalidationListenerManager = new InvalidationListenerManager();

    /**
     * Creates a UserInfo with default values
     */
    public UserInfo() {
        course = SampleCourse.COMPUTER_SCIENCE_ALGORITHMS;
    }

    /**
     * Constructs UserInfo class with given course
     */
    public UserInfo(Course course) {
        requireAllNonNull(course);
        this.course = course;
    }


    public void setCourse(Course course) {
        this.course = course;
        indicateModified();
    }

    public Course getCourse() {
        return course;
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

        return this.course.equals(other.course);
    }
}
