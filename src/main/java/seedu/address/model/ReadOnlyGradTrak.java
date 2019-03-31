package seedu.address.model;

import javafx.beans.Observable;
import javafx.collections.ObservableList;
import seedu.address.model.moduletaken.ModuleTaken;
import seedu.address.model.moduletaken.Semester;

/**
 * Unmodifiable view of an Graduation Tracker/GradTrak
 */
public interface ReadOnlyGradTrak extends Observable {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<ModuleTaken> getModulesTakenList();

    /**
     * Returns an unmodifiable view of the sem limit list.
     */
    ObservableList<SemLimit> getSemesterLimitList();

    /**
     * Returns the current semester.
     */
    Semester getCurrentSemester();

}
