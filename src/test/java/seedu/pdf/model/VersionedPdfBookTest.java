package seedu.pdf.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.pdf.testutil.Assert.assertThrows;
import static seedu.pdf.testutil.TypicalPdfs.SAMPLE_PDF_5;
import static seedu.pdf.testutil.TypicalPdfs.SAMPLE_PDF_6;
import static seedu.pdf.testutil.TypicalPdfs.SAMPLE_PDF_7;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.pdf.testutil.PdfBookBuilder;

public class VersionedPdfBookTest {

    private final ReadOnlyPdfBook pdfBookWithSample7 = new PdfBookBuilder().withPdf(SAMPLE_PDF_7).build();
    private final ReadOnlyPdfBook pdfBookWithSample6 = new PdfBookBuilder().withPdf(SAMPLE_PDF_6).build();
    private final ReadOnlyPdfBook pdfBookWithSample5 = new PdfBookBuilder().withPdf(SAMPLE_PDF_5).build();
    private final ReadOnlyPdfBook emptyPdfBook = new PdfBookBuilder().build();

    @Test
    public void commit_singlePdfBook_noStatesRemovedCurrentStateSaved() {
        VersionedPdfBook versionedPdfBook = preparePdfBookList(emptyPdfBook);

        versionedPdfBook.commit();
        assertPdfBookListStatus(versionedPdfBook,
                Collections.singletonList(emptyPdfBook),
                emptyPdfBook,
                Collections.emptyList());
    }

    @Test
    public void commit_multiplePdfBookPointerAtEndOfStateList_noStatesRemovedCurrentStateSaved() {
        VersionedPdfBook versionedPdfBook = preparePdfBookList(
                emptyPdfBook, pdfBookWithSample7, pdfBookWithSample6);

        versionedPdfBook.commit();
        assertPdfBookListStatus(versionedPdfBook,
                Arrays.asList(emptyPdfBook, pdfBookWithSample7, pdfBookWithSample6),
                pdfBookWithSample6,
                Collections.emptyList());
    }

    @Test
    public void commit_multiplePdfBookPointerNotAtEndOfStateList_statesAfterPointerRemovedCurrentStateSaved() {
        VersionedPdfBook versionedPdfBook = preparePdfBookList(
                emptyPdfBook, pdfBookWithSample7, pdfBookWithSample6);
        shiftCurrentStatePointerLeftwards(versionedPdfBook, 2);

        versionedPdfBook.commit();
        assertPdfBookListStatus(versionedPdfBook,
                Collections.singletonList(emptyPdfBook),
                emptyPdfBook,
                Collections.emptyList());
    }

    @Test
    public void canUndo_multiplePdfBookPointerAtEndOfStateList_returnsTrue() {
        VersionedPdfBook versionedPdfBook = preparePdfBookList(
                emptyPdfBook, pdfBookWithSample7, pdfBookWithSample6);

        assertTrue(versionedPdfBook.canUndo());
    }

    @Test
    public void canUndo_multiplePdfBookPointerAtStartOfStateList_returnsTrue() {
        VersionedPdfBook versionedPdfBook = preparePdfBookList(
                emptyPdfBook, pdfBookWithSample7, pdfBookWithSample6);
        shiftCurrentStatePointerLeftwards(versionedPdfBook, 1);

        assertTrue(versionedPdfBook.canUndo());
    }

    @Test
    public void canUndo_singlePdfBook_returnsFalse() {
        VersionedPdfBook versionedPdfBook = preparePdfBookList(emptyPdfBook);

        assertFalse(versionedPdfBook.canUndo());
    }

    @Test
    public void canUndo_multiplePdfBookPointerAtStartOfStateList_returnsFalse() {
        VersionedPdfBook versionedPdfBook = preparePdfBookList(
                emptyPdfBook, pdfBookWithSample7, pdfBookWithSample6);
        shiftCurrentStatePointerLeftwards(versionedPdfBook, 2);

        assertFalse(versionedPdfBook.canUndo());
    }

    @Test
    public void canRedo_multiplePdfBookPointerNotAtEndOfStateList_returnsTrue() {
        VersionedPdfBook versionedPdfBook = preparePdfBookList(
                emptyPdfBook, pdfBookWithSample7, pdfBookWithSample6);
        shiftCurrentStatePointerLeftwards(versionedPdfBook, 1);

        assertTrue(versionedPdfBook.canRedo());
    }

    @Test
    public void canRedo_multiplePdfBookPointerAtStartOfStateList_returnsTrue() {
        VersionedPdfBook versionedPdfBook = preparePdfBookList(
                emptyPdfBook, pdfBookWithSample7, pdfBookWithSample6);
        shiftCurrentStatePointerLeftwards(versionedPdfBook, 2);

        assertTrue(versionedPdfBook.canRedo());
    }

    @Test
    public void canRedo_singlePdfBook_returnsFalse() {
        VersionedPdfBook versionedPdfBook = preparePdfBookList(emptyPdfBook);

        assertFalse(versionedPdfBook.canRedo());
    }

    @Test
    public void canRedo_multiplePdfBookPointerAtEndOfStateList_returnsFalse() {
        VersionedPdfBook versionedPdfBook = preparePdfBookList(
                emptyPdfBook, pdfBookWithSample7, pdfBookWithSample6);

        assertFalse(versionedPdfBook.canRedo());
    }

    @Test
    public void undo_multiplePdfBookPointerAtEndOfStateList_success() {
        VersionedPdfBook versionedPdfBook = preparePdfBookList(
                emptyPdfBook, pdfBookWithSample7, pdfBookWithSample6);

        versionedPdfBook.undo();
        assertPdfBookListStatus(versionedPdfBook,
                Collections.singletonList(emptyPdfBook),
                pdfBookWithSample7,
                Collections.singletonList(pdfBookWithSample6));
    }

    @Test
    public void undo_multiplePdfBookPointerNotAtStartOfStateList_success() {
        VersionedPdfBook versionedPdfBook = preparePdfBookList(
                emptyPdfBook, pdfBookWithSample7, pdfBookWithSample6);
        shiftCurrentStatePointerLeftwards(versionedPdfBook, 1);

        versionedPdfBook.undo();
        assertPdfBookListStatus(versionedPdfBook,
                Collections.emptyList(),
                emptyPdfBook,
                Arrays.asList(pdfBookWithSample7, pdfBookWithSample6));
    }

    @Test
    public void undo_singlePdfBook_throwsNoUndoableStateException() {
        VersionedPdfBook versionedPdfBook = preparePdfBookList(emptyPdfBook);

        assertThrows(VersionedPdfBook.NoUndoableStateException.class, versionedPdfBook::undo);
    }

    @Test
    public void undo_multiplePdfBookPointerAtStartOfStateList_throwsNoUndoableStateException() {
        VersionedPdfBook versionedPdfBook = preparePdfBookList(
                emptyPdfBook, pdfBookWithSample7, pdfBookWithSample6);
        shiftCurrentStatePointerLeftwards(versionedPdfBook, 2);

        assertThrows(VersionedPdfBook.NoUndoableStateException.class, versionedPdfBook::undo);
    }

    @Test
    public void redo_multiplePdfBookPointerNotAtEndOfStateList_success() {
        VersionedPdfBook versionedPdfBook = preparePdfBookList(
                emptyPdfBook, pdfBookWithSample7, pdfBookWithSample6);
        shiftCurrentStatePointerLeftwards(versionedPdfBook, 1);

        versionedPdfBook.redo();
        assertPdfBookListStatus(versionedPdfBook,
                Arrays.asList(emptyPdfBook, pdfBookWithSample7),
                pdfBookWithSample6,
                Collections.emptyList());
    }

    @Test
    public void redo_multiplePdfBookPointerAtStartOfStateList_success() {
        VersionedPdfBook versionedPdfBook = preparePdfBookList(
                emptyPdfBook, pdfBookWithSample7, pdfBookWithSample6);
        shiftCurrentStatePointerLeftwards(versionedPdfBook, 2);

        versionedPdfBook.redo();
        assertPdfBookListStatus(versionedPdfBook,
                Collections.singletonList(emptyPdfBook),
                pdfBookWithSample7,
                Collections.singletonList(pdfBookWithSample6));
    }

    @Test
    public void redo_singlePdfBook_throwsNoRedoableStateException() {
        VersionedPdfBook versionedPdfBook = preparePdfBookList(emptyPdfBook);

        assertThrows(VersionedPdfBook.NoRedoableStateException.class, versionedPdfBook::redo);
    }

    @Test
    public void redo_multiplePdfBookPointerAtEndOfStateList_throwsNoRedoableStateException() {
        VersionedPdfBook versionedPdfBook = preparePdfBookList(
                emptyPdfBook, pdfBookWithSample7, pdfBookWithSample6);

        assertThrows(VersionedPdfBook.NoRedoableStateException.class, versionedPdfBook::redo);
    }

    @Test
    public void equals() {
        VersionedPdfBook versionedPdfBook = preparePdfBookList(pdfBookWithSample7, pdfBookWithSample6);

        // same values -> returns true
        VersionedPdfBook copy = preparePdfBookList(pdfBookWithSample7, pdfBookWithSample6);
        assertTrue(versionedPdfBook.equals(copy));

        // same object -> returns true
        assertTrue(versionedPdfBook.equals(versionedPdfBook));

        // null -> returns false
        assertFalse(versionedPdfBook.equals(null));

        // different types -> returns false
        assertFalse(versionedPdfBook.equals(1));

        // different state list -> returns false
        VersionedPdfBook differentPdfBookList = preparePdfBookList(pdfBookWithSample6, pdfBookWithSample5);
        assertFalse(versionedPdfBook.equals(differentPdfBookList));

        // different current pointer index -> returns false
        VersionedPdfBook differentCurrentStatePointer = preparePdfBookList(
                pdfBookWithSample7, pdfBookWithSample6);
        shiftCurrentStatePointerLeftwards(versionedPdfBook, 1);
        assertFalse(versionedPdfBook.equals(differentCurrentStatePointer));
    }

    /**
     * Asserts that {@code versionedPdfBook} is currently pointing at {@code expectedCurrentState},
     * states before {@code versionedPdfBook#currentStatePointer} is equal to {@code expectedStatesBeforePointer},
     * and states after {@code versionedPdfBook#currentStatePointer} is equal to {@code expectedStatesAfterPointer}.
     */
    private void assertPdfBookListStatus(VersionedPdfBook versionedPdfBook,
                                         List<ReadOnlyPdfBook> expectedStatesBeforePointer,
                                         ReadOnlyPdfBook expectedCurrentState,
                                         List<ReadOnlyPdfBook> expectedStatesAfterPointer) {
        // check state currently pointing at is correct
        assertEquals(new PdfBook(versionedPdfBook), expectedCurrentState);

        // shift pointer to start of state list
        while (versionedPdfBook.canUndo()) {
            versionedPdfBook.undo();
        }

        // check states before pointer are correct
        for (ReadOnlyPdfBook expectedPdfBook : expectedStatesBeforePointer) {
            assertEquals(expectedPdfBook, new PdfBook(versionedPdfBook));
            versionedPdfBook.redo();
        }

        // check states after pointer are correct
        for (ReadOnlyPdfBook expectedPdfBook : expectedStatesAfterPointer) {
            versionedPdfBook.redo();
            assertEquals(expectedPdfBook, new PdfBook(versionedPdfBook));
        }

        // check that there are no more states after pointer
        assertFalse(versionedPdfBook.canRedo());

        // revert pointer to original position
        expectedStatesAfterPointer.forEach(unused -> versionedPdfBook.undo());
    }

    /**
     * Creates and returns a {@code VersionedPdfBook} with the {@code pdfBookStates} added into it, and the
     * {@code VersionedPdfBook#currentStatePointer} at the end of list.
     */
    private VersionedPdfBook preparePdfBookList(ReadOnlyPdfBook... pdfBookStates) {
        assertFalse(pdfBookStates.length == 0);

        VersionedPdfBook versionedPdfBook = new VersionedPdfBook(pdfBookStates[0]);
        for (int i = 1; i < pdfBookStates.length; i++) {
            versionedPdfBook.resetData(pdfBookStates[i]);
            versionedPdfBook.commit();
        }

        return versionedPdfBook;
    }

    /**
     * Shifts the {@code versionedPdfBook#currentStatePointer} by {@code count} to the left of its list.
     */
    private void shiftCurrentStatePointerLeftwards(VersionedPdfBook versionedPdfBook, int count) {
        for (int i = 0; i < count; i++) {
            versionedPdfBook.undo();
        }
    }
}
