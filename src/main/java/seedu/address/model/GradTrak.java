package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import javafx.beans.InvalidationListener;
import javafx.collections.ObservableList;
import seedu.address.commons.util.InvalidationListenerManager;
import seedu.address.model.moduleinfo.ModuleInfoCode;
import seedu.address.model.moduletaken.ModuleTaken;
import seedu.address.model.moduletaken.Semester;
import seedu.address.model.moduletaken.SemesterLimitList;
import seedu.address.model.moduletaken.UniqueModuleTakenList;

/**
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
    }

    public GradTrak() {
        currentSemester = Semester.Y1S1; // default
    }

    /**
     * Creates an GradTrak using the ModulesTaken in the {@code toBeCopied}
     */
    public GradTrak(ReadOnlyGradTrak toBeCopied) {
        this();
        resetData(toBeCopied);
        if (toBeCopied instanceof GradTrak) {
            currentSemester = ((GradTrak) toBeCopied).getCurrentSemester();
        }
    }

    public Semester getCurrentSemester() {
        return currentSemester;
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the moduleTaken list with {@code ModuleTaken}.
     * {@code ModuleTaken} must not contain duplicate modulesTaken.
     */
    public void setModulesTaken(List<ModuleTaken> modulesTaken) {
        this.modulesTaken.setPersons(modulesTaken);
        indicateModified();
    }

    /**
     * Replaces the contents of the Semester Limit list with {@code semLimits}.
     */
    public void setSemesterLimits(List<SemLimit> semLimits) {
        this.semesterLimitList.setSemesterLimits(semLimits);
        indicateModified();
    }

    /**
     * Resets the existing data of this {@code GradTrak} with {@code newData}.
     */
    public void resetData(ReadOnlyGradTrak newData) {
        requireNonNull(newData);

        setModulesTaken(newData.getModulesTakenList());
        setSemesterLimits(newData.getSemesterLimitList());
        setCurrentSemester(newData.getCurrentSemester());
    }

    //// moduleTaken-level operations

    /**
     * Returns true if a {@code ModuleTaken} in GradTrak has the same identity as the given {@code ModuleTaken}.
     */
    public boolean hasModuleTaken(ModuleTaken moduleTaken) {
        requireNonNull(moduleTaken);
        return modulesTaken.contains(moduleTaken);
    }

    /**
     * Adds a moduleTaken to GradTrak.
     * The moduleTaken must not already exist in the GradTrak.
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
     * Adds  all the semester limits in the list to the SemesterLimitList.
     */
    public void addSemesterLimitList(ObservableList<SemLimit> s) {
        for (int i = 0; i < s.size(); i++) {
            this.semesterLimitList.add(s.get(i));
        }
        indicateModified();
    }

    /**
     * Adds a moduleTaken to the address book.
     * The moduleTaken must not already exist in the address book.
     */
    public void addSemesterLimit(SemLimit s) {
        semesterLimitList.add(s);
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
     * Returns a {@code List} of {@code ModuleInfoCode} representing non-failed {@code ModuleTaken}.
     * @return a {@code List} of {@code ModuleInfoCode} representing non-failed {@code ModuleTaken}.
     */
    public List<ModuleInfoCode> getNonFailedCodeList() {
        List<ModuleInfoCode> codeList = new ArrayList<>();
        for (ModuleTaken moduleTaken : getModulesTakenList()) {
            if (!moduleTaken.isFailed(currentSemester)) {
                codeList.add(moduleTaken.getModuleInfoCode());
            }
        }

        return codeList;
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
