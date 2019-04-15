package seedu.address.model.course;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Wraps all data at the Course List level
 */
public class CourseList {
    private final List<Course> courseList;

    {
        courseList = new ArrayList<>();
    }

    public CourseList() { }

    /** Adds Course to the List of all the courses
    * @param course
    */
    public void addCourse(Course course) {
        this.courseList.add(course);
    }

    /**
     * Returns Course which has the same course name
     * @param courseName
     * @return course
     */
    public Course getCourse(CourseName courseName) {
        Course course = null;
        for (int i = 0; i < courseList.size(); i++) {
            if (courseList.get(i).getCourseName().equals(courseName)) {
                course = courseList.get(i);
            }
        }
        return course;
    }

    /**
     * Returns an unmodifiable observable list of course list
     * @return
     */
    public ObservableList<Course> getObservableList() {
        ObservableList<Course> observableList = FXCollections
                .unmodifiableObservableList(FXCollections.observableArrayList(courseList));
        return observableList;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof CourseList)) {
            return false;
        }

        CourseList other = (CourseList) obj;
        return this.courseList.equals(other.courseList);
    }

}
