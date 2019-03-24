package seedu.address.model.pdf;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPdfs.SAMPLE_PDF_1;
import static seedu.address.testutil.TypicalPdfs.SAMPLE_PDF_1_DUPLICATE;
import static seedu.address.testutil.TypicalPdfs.SAMPLE_PDF_2;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

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
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(SAMPLE_PDF_1.isSamePdf(SAMPLE_PDF_1));

        // null -> returns false
        assertFalse(SAMPLE_PDF_1.isSamePdf(null));

        // different directory -> returns false
        Pdf comparisonPdf = new PdfBuilder(SAMPLE_PDF_1_DUPLICATE).build();
        assertFalse(SAMPLE_PDF_1.isSamePdf(comparisonPdf));

        /*// different name -> returns false
        comparisonPdf = new PdfBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSamePdf(comparisonPdf));

        // same name, same phone, different attributes -> returns true
        comparisonPdf = new PdfBuilder(ALICE).withEmail(VALID_EMAIL_BOB).withDirectory(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSamePdf(comparisonPdf));*/

        /*// same name, same email, different attributes -> returns true
        comparisonPdf = new PdfBuilder(ALICE).withSize(VALID_PHONE_BOB).withDirectory(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSamePdf(comparisonPdf));

        // same name, same phone, same email, different attributes -> returns true
        comparisonPdf = new PdfBuilder(ALICE).withDirectory(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSamePdf(comparisonPdf));*/
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

        /*// different name -> returns false
        Pdf editedAlice = new PdfBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new PdfBuilder(ALICE).withSize(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new PdfBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));*/

        // different directory -> returns false
        Pdf comparison = new PdfBuilder(SAMPLE_PDF_1_DUPLICATE).build();
        assertFalse(SAMPLE_PDF_1.equals(SAMPLE_PDF_1_DUPLICATE));

        /*// different tags -> returns false
        comparison = new PdfBuilder(SAMPLE_PDF_1).withTags(VALID_TAG_LECTURE).build();
        assertFalse(SAMPLE_PDF_1.equals(editedAlice));*/
    }
}
