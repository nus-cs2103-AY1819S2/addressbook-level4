package seedu.address.model;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code HealthWorkerBook} that keeps track of its own history.
 */
public class VersionedPatientBook extends PatientBook implements VersionedBook {

    private final List<ReadOnlyPatientBook> patientBookStateList;
    private int currStatePointer;

    public VersionedPatientBook(ReadOnlyPatientBook initialState) {
        super(initialState);

        this.patientBookStateList = new ArrayList<>();
        this.patientBookStateList.add(new PatientBook(initialState));
        this.currStatePointer = 0;
    }

    private void removeStatesAfterCurrentPointer() {
        this.patientBookStateList.subList(this.currStatePointer + 1,
            this.patientBookStateList.size()).clear();
    }

    @Override
    public void commit() {
        removeStatesAfterCurrentPointer();
        this.patientBookStateList.add(new PatientBook(this));
        this.currStatePointer++;
        indicateModified();
    }

    @Override
    public void undo() {
        if (!canUndo()) {
            throw new NoUndoableStateException();
        }
        this.currStatePointer--;
        resetData(this.patientBookStateList.get(this.currStatePointer));
    }

    @Override
    public void redo() {
        if (!canRedo()) {
            throw new NoRedoableStateException();
        }

        this.currStatePointer++;
        resetData(this.patientBookStateList.get(this.currStatePointer));
    }

    @Override
    public boolean canUndo() {
        return this.currStatePointer > 0;
    }

    @Override
    public boolean canRedo() {
        return this.currStatePointer < this.patientBookStateList.size() - 1;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof VersionedPatientBook)) {
            return false;
        }

        VersionedPatientBook otherPatientBook = (VersionedPatientBook) other;

        return super.equals(otherPatientBook)
            && this.patientBookStateList.equals(otherPatientBook.patientBookStateList)
            && this.currStatePointer == otherPatientBook.currStatePointer;
    }
}
