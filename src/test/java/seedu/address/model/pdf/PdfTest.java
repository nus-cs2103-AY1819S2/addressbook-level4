package seedu.address.model.pdf;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.TypicalPdfs.ALICE;
import static seedu.address.testutil.TypicalPdfs.BOB;

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
        assertTrue(ALICE.isSamePdf(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSamePdf(null));

        // different phone and email -> returns false
        Pdf editedAlice = new PdfBuilder(ALICE).withSize(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.isSamePdf(editedAlice));

        // different name -> returns false
        editedAlice = new PdfBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSamePdf(editedAlice));

        // same name, same phone, different attributes -> returns true
        editedAlice = new PdfBuilder(ALICE).withEmail(VALID_EMAIL_BOB).withLocation(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSamePdf(editedAlice));

        // same name, same email, different attributes -> returns true
        editedAlice = new PdfBuilder(ALICE).withSize(VALID_PHONE_BOB).withLocation(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSamePdf(editedAlice));

        // same name, same phone, same email, different attributes -> returns true
        editedAlice = new PdfBuilder(ALICE).withLocation(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSamePdf(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Pdf aliceCopy = new PdfBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different pdf -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Pdf editedAlice = new PdfBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new PdfBuilder(ALICE).withSize(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new PdfBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different address -> returns false
        editedAlice = new PdfBuilder(ALICE).withLocation(VALID_ADDRESS_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new PdfBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));
    }
}
