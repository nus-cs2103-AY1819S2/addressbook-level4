package seedu.pdf.model;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code PdfBook} that keeps track of its own history.
 */
public class VersionedPdfBook extends PdfBook {

    private final List<ReadOnlyPdfBook> pdfBookStateList;
    private int currentStatePointer;

    VersionedPdfBook(ReadOnlyPdfBook initialState) {
        super(initialState);

        pdfBookStateList = new ArrayList<>();
        pdfBookStateList.add(new PdfBook(initialState));
        currentStatePointer = 0;
    }

    /**
     * Saves a copy of the current {@code PdfBook} state at the end of the state list.
     * Undone states are removed from the state list.
     */
    void commit() {
        removeStatesAfterCurrentPointer();
        pdfBookStateList.add(new PdfBook(this));
        currentStatePointer++;
        indicateModified();
    }

    private void removeStatesAfterCurrentPointer() {
        pdfBookStateList.subList(currentStatePointer + 1, pdfBookStateList.size()).clear();
    }

    /**
     * Restores the pdf book to its previous state.
     */
    public void undo() {
        if (!canUndo()) {
            throw new NoUndoableStateException();
        }

        currentStatePointer--;
        resetData(pdfBookStateList.get(currentStatePointer));
    }

    /**
     * Restores the pdf book to its previously undone state.
     */
    public void redo() {
        if (!canRedo()) {
            throw new NoRedoableStateException();
        }
        currentStatePointer++;
        resetData(pdfBookStateList.get(currentStatePointer));
    }

    /**
     * Returns true if {@code undo()} has pdf book states to undo.
     */
    boolean canUndo() {
        return currentStatePointer > 0;
    }

    /**
     * Returns true if {@code redo()} has pdf book states to redo.
     */
    boolean canRedo() {
        return currentStatePointer < pdfBookStateList.size() - 1;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VersionedPdfBook)) {
            return false;
        }

        VersionedPdfBook otherVersionedPdfBook = (VersionedPdfBook) other;

        // state check
        return super.equals(otherVersionedPdfBook)
                && pdfBookStateList.equals(otherVersionedPdfBook.pdfBookStateList)
                && currentStatePointer == otherVersionedPdfBook.currentStatePointer;
    }

    /**
     * Thrown when trying to {@code undo()} but can't.
     */
    static class NoUndoableStateException extends RuntimeException {
        private NoUndoableStateException() {
            super("Current state pointer at start of pdfBookState list, unable to undo.");
        }
    }

    /**
     * Thrown when trying to {@code redo()} but can't.
     */
    static class NoRedoableStateException extends RuntimeException {
        private NoRedoableStateException() {
            super("Current state pointer at end of pdfBookState list, unable to redo.");
        }
    }
}
