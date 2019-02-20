package seedu.address.model.medicine;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COMPANY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUANTITY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.TypicalMedicines.ALICE;
import static seedu.address.testutil.TypicalMedicines.BOB;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.testutil.MedicineBuilder;

public class MedicineTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Medicine medicine = new MedicineBuilder().build();
        thrown.expect(UnsupportedOperationException.class);
        medicine.getTags().remove(0);
    }

    @Test
    public void isSameMedicine() {
        // same object -> returns true
        assertTrue(ALICE.isSameMedicine(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSameMedicine(null));

        // different quantity and email -> returns false
        Medicine editedAlice = new MedicineBuilder(ALICE).withQuantity(VALID_QUANTITY_BOB).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.isSameMedicine(editedAlice));

        // different name -> returns false
        editedAlice = new MedicineBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSameMedicine(editedAlice));

        // same name, same quantity, different attributes -> returns true
        editedAlice = new MedicineBuilder(ALICE).withEmail(VALID_EMAIL_BOB).withCompany(VALID_COMPANY_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameMedicine(editedAlice));

        // same name, same email, different attributes -> returns true
        editedAlice = new MedicineBuilder(ALICE).withQuantity(VALID_QUANTITY_BOB).withCompany(VALID_COMPANY_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameMedicine(editedAlice));

        // same name, same quantity, same email, different attributes -> returns true
        editedAlice = new MedicineBuilder(ALICE).withCompany(VALID_COMPANY_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameMedicine(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Medicine aliceCopy = new MedicineBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different medicine -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Medicine editedAlice = new MedicineBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different quantity -> returns false
        editedAlice = new MedicineBuilder(ALICE).withQuantity(VALID_QUANTITY_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new MedicineBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different company -> returns false
        editedAlice = new MedicineBuilder(ALICE).withCompany(VALID_COMPANY_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new MedicineBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));
    }
}
