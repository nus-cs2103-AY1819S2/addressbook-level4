package seedu.pdf.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.pdf.testutil.TypicalPdfs.SAMPLE_PDF_7;
import static seedu.pdf.testutil.TypicalPdfs.getTypicalPdfBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.pdf.model.pdf.Pdf;
import seedu.pdf.model.pdf.exceptions.DuplicatePdfException;
import seedu.pdf.testutil.PdfBuilder;

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

    @Test
    public void resetData_withDuplicatePdfs_throwsDuplicatePdfException() {
        // Two pdfs with the same identity fields
        Pdf editedPdf = new PdfBuilder(SAMPLE_PDF_7).build();
        List<Pdf> newPdfs = Arrays.asList(SAMPLE_PDF_7, editedPdf);
        PdfBookStub newData = new PdfBookStub(newPdfs);

        thrown.expect(DuplicatePdfException.class);
        pdfBook.resetData(newData);
    }

    @Test
    public void hasPdf_nullPdf_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        pdfBook.hasPdf(null);
    }

    @Test
    public void hasPdf_pdfNotInPdfBook_returnsFalse() {
        assertFalse(pdfBook.hasPdf(SAMPLE_PDF_7));
    }

    @Test
    public void hasPdf_pdfInPdfBook_returnsTrue() {
        pdfBook.addPdf(SAMPLE_PDF_7);
        assertTrue(pdfBook.hasPdf(SAMPLE_PDF_7));
    }

    @Test
    public void hasPdf_pdfWithSameIdentityFieldsInPdfBook_returnsTrue() {
        pdfBook.addPdf(SAMPLE_PDF_7);
        Pdf editedPdf = new PdfBuilder(SAMPLE_PDF_7).build();
        assertTrue(pdfBook.hasPdf(editedPdf));
    }

    @Test
    public void getPdfList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        pdfBook.getPdfList().remove(0);
    }


    @Test
    public void addListener_withInvalidationListener_listenerAdded() {
        SimpleIntegerProperty counter = new SimpleIntegerProperty();
        InvalidationListener listener = observable -> counter.set(counter.get() + 1);
        pdfBook.addListener(listener);
        pdfBook.addPdf(SAMPLE_PDF_7);
        assertEquals(1, counter.get());
    }

    @Test
    public void removeListener_withInvalidationListener_listenerRemoved() {
        SimpleIntegerProperty counter = new SimpleIntegerProperty();
        InvalidationListener listener = observable -> counter.set(counter.get() + 1);
        pdfBook.addListener(listener);
        pdfBook.removeListener(listener);
        pdfBook.addPdf(SAMPLE_PDF_7);
        assertEquals(0, counter.get());
    }

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
