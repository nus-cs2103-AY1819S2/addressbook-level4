package seedu.address.model.pdf;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPdfs.SAMPLE_PDF_1;
import static seedu.address.testutil.TypicalPdfs.SAMPLE_PDF_1_DUPLICATE;
import static seedu.address.testutil.TypicalPdfs.SAMPLE_PDF_2;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.CommandTestUtil;
import seedu.address.testutil.PdfBuilder;

public class PdfTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Pdf pdf = new PdfBuilder().build();
        thrown.expect(UnsupportedOperationException.class);
        pdf.getTags().remove(0);
    }

    @Test
    public void isSamePdf() {
        // same object -> returns true
        assertTrue(SAMPLE_PDF_1.isSamePdf(SAMPLE_PDF_1));

        // null -> returns false
        assertFalse(SAMPLE_PDF_1.isSamePdf(null));

        // different directory -> returns false
        Pdf comparisonPdf = new PdfBuilder(SAMPLE_PDF_1_DUPLICATE).build();
        assertFalse(SAMPLE_PDF_1.isSamePdf(comparisonPdf));

        // different name -> returns false
        comparisonPdf = new PdfBuilder(SAMPLE_PDF_1).withName(CommandTestUtil.NAME_2_VALID).build();
        assertFalse(SAMPLE_PDF_1.isSamePdf(comparisonPdf));

        // same name, same phone, different attributes -> returns true
        comparisonPdf = new PdfBuilder(SAMPLE_PDF_1).withTags(CommandTestUtil.TAG_VALID_CS2103T).build();
        assertTrue(SAMPLE_PDF_1.isSamePdf(comparisonPdf));

        // same name, same email, different attributes -> returns true
        comparisonPdf = new PdfBuilder(SAMPLE_PDF_2).withDeadline(CommandTestUtil.DEADLINE_JSON_COMPLETE).build();
        assertTrue(SAMPLE_PDF_2.isSamePdf(comparisonPdf));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Pdf acopy = new PdfBuilder(SAMPLE_PDF_1).build();
        assertTrue(SAMPLE_PDF_1.equals(acopy));

        // same object -> returns true
        assertTrue(SAMPLE_PDF_1.equals(SAMPLE_PDF_1));

        // null -> returns false
        assertFalse(SAMPLE_PDF_1.equals(null));

        // different type -> returns false
        assertFalse(SAMPLE_PDF_1.equals(5));

        // different pdf -> returns false
        assertFalse(SAMPLE_PDF_1.equals(SAMPLE_PDF_2));

        // different name -> returns false
        Pdf editedPdf = new PdfBuilder(SAMPLE_PDF_1).withName(CommandTestUtil.NAME_2_VALID).build();
        assertFalse(SAMPLE_PDF_1.equals(editedPdf));

        // different directory -> returns false
        editedPdf = new PdfBuilder(SAMPLE_PDF_1).withDirectory(CommandTestUtil.DIR_3_VALID).build();
        assertFalse(SAMPLE_PDF_1.equals(editedPdf));

        // different size -> returns false
        editedPdf = new PdfBuilder(SAMPLE_PDF_1).withSize(CommandTestUtil.SIZE_3_VALID).build();
        assertFalse(SAMPLE_PDF_1.equals(editedPdf));

        // different deaedline -> returns false
        editedPdf = new PdfBuilder(SAMPLE_PDF_1).withDeadline(CommandTestUtil.DEADLINE_JSON_COMPLETE).build();
        assertFalse(SAMPLE_PDF_1.equals(editedPdf));

        // different tags -> returns false
        editedPdf = new PdfBuilder(SAMPLE_PDF_1).withTags(CommandTestUtil.TAG_VALID_LECTURE).build();
        assertFalse(SAMPLE_PDF_1.equals(editedPdf));
    }
}
