package seedu.address.model;

import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertTrue;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
//import static seedu.address.testutil.TypicalPdfs.ALICE;
import static seedu.address.testutil.TypicalPdfs.getTypicalPdfBook;

import java.util.Collection;
//import java.util.Arrays;
import java.util.Collections;
//import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.beans.InvalidationListener;
//import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.pdf.Pdf;
//import seedu.address.model.pdf.exceptions.DuplicatePdfException;
//import seedu.address.testutil.PdfBuilder;

public class PdfBookTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final PdfBook pdfBook = new PdfBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), pdfBook.getPdfList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        pdfBook.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyPdfBook_replacesData() {
        PdfBook newData = getTypicalPdfBook();
        pdfBook.resetData(newData);
        assertEquals(newData, pdfBook);
    }
    /*
    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two pdfs with the same identity fields
        Pdf editedAlice = new PdfBuilder(ALICE).withDirectory(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Pdf> newPdfs = Arrays.asList(ALICE, editedAlice);
        PdfBookStub newData = new PdfBookStub(newPdfs);

        thrown.expect(DuplicatePdfException.class);
        pdfBook.resetData(newData);
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        pdfBook.hasPdf(null);
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(pdfBook.hasPdf(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        pdfBook.addPdf(ALICE);
        assertTrue(pdfBook.hasPdf(ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
        pdfBook.addPdf(ALICE);
        Pdf editedAlice = new PdfBuilder(ALICE).withDirectory(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(pdfBook.hasPdf(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        pdfBook.getPdfList().remove(0);
    }

    @Test
    public void addListener_withInvalidationListener_listenerAdded() {
        SimpleIntegerProperty counter = new SimpleIntegerProperty();
        InvalidationListener listener = observable -> counter.set(counter.get() + 1);
        pdfBook.addListener(listener);
        pdfBook.addPdf(ALICE);
        assertEquals(1, counter.get());
    }

    @Test
    public void removeListener_withInvalidationListener_listenerRemoved() {
        SimpleIntegerProperty counter = new SimpleIntegerProperty();
        InvalidationListener listener = observable -> counter.set(counter.get() + 1);
        pdfBook.addListener(listener);
        pdfBook.removeListener(listener);
        pdfBook.addPdf(ALICE);
        assertEquals(0, counter.get());
    }*/

    /**
     * A stub ReadOnlyPdfBook whose pdfs list can violate interface constraints.
     */
    private static class PdfBookStub implements ReadOnlyPdfBook {
        private final ObservableList<Pdf> pdfs = FXCollections.observableArrayList();

        PdfBookStub(Collection<Pdf> pdfs) {
            this.pdfs.setAll(pdfs);
        }

        @Override
        public ObservableList<Pdf> getPdfList() {
            return pdfs;
        }

        @Override
        public void addListener(InvalidationListener listener) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void removeListener(InvalidationListener listener) {
            throw new AssertionError("This method should not be called.");
        }
    }

}
