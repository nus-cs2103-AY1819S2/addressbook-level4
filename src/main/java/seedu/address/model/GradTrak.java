package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.InvalidationListener;
import javafx.collections.ObservableList;
import seedu.address.commons.util.InvalidationListenerManager;
import seedu.address.model.moduleinfo.ModuleInfoCode;
import seedu.address.model.moduletaken.ModuleTaken;
import seedu.address.model.moduletaken.Semester;
import seedu.address.model.moduletaken.SemesterLimitList;
import seedu.address.model.moduletaken.UniqueModuleTakenList;

/**
 * Todo: modify Address book to store module taken
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameModuleTaken comparison)
 */
public class GradTrak implements ReadOnlyGradTrak {

    private Semester currentSemester;
    private final SemesterLimitList semesterLimitList;
    private final UniqueModuleTakenList modulesTaken;
    private final InvalidationListenerManager invalidationListenerManager = new InvalidationListenerManager();

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        modulesTaken = new UniqueModuleTakenList();
        semesterLimitList = new SemesterLimitList();
        currentSemester = Semester.Y1S1; //TODO get from storage
    }

    public GradTrak() {}

    /**
     * Creates an GradTrak using the Persons in the {@code toBeCopied}
     */
    public GradTrak(ReadOnlyGradTrak toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    public Semester getCurrentSemester() {
        return currentSemester;
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the moduleTaken list with {@code moduleTakens}.
     * {@code moduleTakens} must not contain duplicate moduleTakens.
     */
    public void setModulesTaken(List<ModuleTaken> modulesTaken) {
        this.modulesTaken.setPersons(modulesTaken);
        indicateModified();
    }

    /**
     * Resets the existing data of this {@code GradTrak} with {@code newData}.
     */
    public void resetData(ReadOnlyGradTrak newData) {
        requireNonNull(newData);

        setModulesTaken(newData.getModulesTakenList());
    }

    //// moduleTaken-level operations

    /**
     * Returns true if a moduleTaken with the same identity as {@code moduleTaken} exists in the address book.
     */
    public boolean hasModuleTaken(ModuleTaken moduleTaken) {
        requireNonNull(moduleTaken);
        return modulesTaken.contains(moduleTaken);
    }

    /**
     * Adds a moduleTaken to the address book.
     * The moduleTaken must not already exist in the address book.
     */
    public void addModuleTaken(ModuleTaken p) {
        modulesTaken.add(p);
        indicateModified();
    }

    /**
     * Replaces the given moduleTaken {@code target} in the list with {@code editedModuleTaken}.
     * {@code target} must exist in the address book.
     * The moduleTaken identity of {@code editedModuleTaken} must not be the same as another
     * existing moduleTaken in the address book.
     */
    public void setModuleTaken(ModuleTaken target, ModuleTaken editedModuleTaken) {
        requireNonNull(editedModuleTaken);

        modulesTaken.setPerson(target, editedModuleTaken);
        indicateModified();
    }

    /**
     * Replaces the given index of semester limit with {@code editedSemesterLimit}.
     */
    public void setSemesterLimit(int index, SemLimit editedSemesterLimit) {
        requireNonNull(editedSemesterLimit);

        semesterLimitList.setSemesterLimit(index, editedSemesterLimit);
        indicateModified();
    }

    /**
     * Replaces the given index of semester limit with {@code editedSemesterLimit}.
     */
    public void setCurrentSemester(Semester semester) {
        requireNonNull(semester);

        this.currentSemester = semester;
        indicateModified();
    }

    /**
     * Removes {@code key} from this {@code GradTrak}.
     * {@code key} must exist in the address book.
     */
    public void removeModuleTaken(ModuleTaken key) {
        modulesTaken.remove(key);
        indicateModified();
    }

    /**
     * Returns a List of ModuleInfoCode representing passed ModuleTaken.
     */
    public List<ModuleInfoCode> getPassedModuleList() {
        List<ModuleInfoCode> codeList = new ArrayList<>();

        for (ModuleTaken moduleTaken : getModulesTakenList()) {
            if (moduleTaken.isPassed(currentSemester)) {
                codeList.add(moduleTaken.getModuleInfoCode());
            }
        }

        return codeList;
    }

    /**
     * Returns true if the module represented by the given ModuleInfoCode has been passed.
     */
    public boolean hasPassedModule(ModuleInfoCode moduleInfoCode) {
        return getPassedModuleList().contains(moduleInfoCode);
    }

    /**
     * Returns true if modulesTaken contains an unfinished ModuleTaken represented by the given ModuleInfoCode.
     */
    public boolean hasUnfinishedModule(ModuleInfoCode moduleInfoCode) {
        requireNonNull(moduleInfoCode);
        for (ModuleTaken moduleTaken : getModulesTakenList()) {
            if (moduleTaken.getModuleInfoCode().equals(moduleInfoCode)) {
                return !moduleTaken.isFinished(currentSemester);
            }
        }

        return false;
    }

    @Override
    public void addListener(InvalidationListener listener) {
        invalidationListenerManager.addListener(listener);
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        invalidationListenerManager.removeListener(listener);
    }

    /**
     * Notifies listeners that the address book has been modified.
     */
    protected void indicateModified() {
        invalidationListenerManager.callListeners(this);
    }

    //// util methods

    @Override
    public String toString() {
        return modulesTaken.asUnmodifiableObservableList().size() + " modulesTaken";
        // TODO: refine later
    }

    @Override
    public ObservableList<ModuleTaken> getModulesTakenList() {
        return modulesTaken.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<SemLimit> getSemesterLimitList() {
        return semesterLimitList.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GradTrak // instanceof handles nulls
                && modulesTaken.equals(((GradTrak) other).modulesTaken));
    }

    @Override
    public int hashCode() {
        return modulesTaken.hashCode();
    }
}
