package seedu.address.model.pdf;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_TUTORIAL;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
//import static seedu.address.testutil.TypicalPdfs.ALICE;
import static seedu.address.testutil.TypicalPdfs.A_DUP_PDF;
import static seedu.address.testutil.TypicalPdfs.A_PDF;
//import static seedu.address.testutil.TypicalPdfs.B_DUP_PDF;
import static seedu.address.testutil.TypicalPdfs.B_PDF;
//import static seedu.address.testutil.TypicalPdfs.BOB;

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
        assertTrue(A_PDF.isSamePdf(A_PDF));

        // null -> returns false
        assertFalse(A_PDF.isSamePdf(null));

        // different directory -> returns false
        Pdf comparisonPdf = new PdfBuilder(A_DUP_PDF).build();
        assertFalse(A_PDF.isSamePdf(comparisonPdf));

        /*// different name -> returns false
        comparisonPdf = new PdfBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSamePdf(comparisonPdf));

        // same name, same phone, different attributes -> returns true
        comparisonPdf = new PdfBuilder(ALICE).withEmail(VALID_EMAIL_BOB).withLocation(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSamePdf(comparisonPdf));*/

        /*// same name, same email, different attributes -> returns true
        comparisonPdf = new PdfBuilder(ALICE).withSize(VALID_PHONE_BOB).withLocation(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSamePdf(comparisonPdf));

        // same name, same phone, same email, different attributes -> returns true
        comparisonPdf = new PdfBuilder(ALICE).withLocation(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSamePdf(comparisonPdf));*/
    }

    @Test
    public void equals() {
        // same values -> returns true
        Pdf a_copy = new PdfBuilder(A_PDF).build();
        assertTrue(A_PDF.equals(a_copy));

        // same object -> returns true
        assertTrue(A_PDF.equals(A_PDF));

        // null -> returns false
        assertFalse(A_PDF.equals(null));

        // different type -> returns false
        assertFalse(A_PDF.equals(5));

        // different pdf -> returns false
        assertFalse(A_PDF.equals(B_PDF));

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
        Pdf comparison = new PdfBuilder(A_DUP_PDF).build();
        assertFalse(A_PDF.equals(A_DUP_PDF));

        /*// different tags -> returns false
        comparison = new PdfBuilder(A_PDF).withTags(VALID_TAG_TUTORIAL).build();
        assertFalse(A_PDF.equals(editedAlice));*/
    }
}
