package seedu.address.model.medicine;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COMPANY_GABAPENTIN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXPIRY_GABAPENTIN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_GABAPENTIN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUANTITY_GABAPENTIN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_PAINKILLER;
import static seedu.address.testutil.TypicalMedicines.GABAPENTIN;
import static seedu.address.testutil.TypicalMedicines.PARACETAMOL;

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
        assertTrue(PARACETAMOL.isSameMedicine(PARACETAMOL));

        // null -> returns false
        assertFalse(PARACETAMOL.isSameMedicine(null));

        // different name -> returns false
        Medicine editedParacetamol = new MedicineBuilder(PARACETAMOL).withName(VALID_NAME_GABAPENTIN).build();
        assertFalse(PARACETAMOL.isSameMedicine(editedParacetamol));

        // same name, same quantity, different attributes -> returns false
        editedParacetamol = new MedicineBuilder(PARACETAMOL).withCompany(VALID_COMPANY_GABAPENTIN)
                .withExpiry(VALID_EXPIRY_GABAPENTIN).withTags(VALID_TAG_PAINKILLER).build();
        assertFalse(PARACETAMOL.isSameMedicine(editedParacetamol));

        // same name, same expiry, different attributes -> returns false
        editedParacetamol = new MedicineBuilder(PARACETAMOL).withCompany(VALID_COMPANY_GABAPENTIN)
                .withQuantity(VALID_QUANTITY_GABAPENTIN).withTags(VALID_TAG_PAINKILLER).build();
        assertFalse(PARACETAMOL.isSameMedicine(editedParacetamol));

        // same name, same quantity, same expiry, different attributes -> returns false
        editedParacetamol = new MedicineBuilder(PARACETAMOL).withCompany(VALID_COMPANY_GABAPENTIN)
                .withTags(VALID_TAG_PAINKILLER).build();
        assertFalse(PARACETAMOL.isSameMedicine(editedParacetamol));

        // same name, same company, different attributes -> returns true
        editedParacetamol = new MedicineBuilder(PARACETAMOL).withQuantity(VALID_QUANTITY_GABAPENTIN)
                .withExpiry(VALID_EXPIRY_GABAPENTIN).withTags(VALID_TAG_PAINKILLER).build();
        assertTrue(PARACETAMOL.isSameMedicine(editedParacetamol));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Medicine paracetamolCopy = new MedicineBuilder(PARACETAMOL).build();
        assertTrue(PARACETAMOL.equals(paracetamolCopy));

        // same object -> returns true
        assertTrue(PARACETAMOL.equals(PARACETAMOL));

        // null -> returns false
        assertFalse(PARACETAMOL.equals(null));

        // different type -> returns false
        assertFalse(PARACETAMOL.equals(5));

        // different medicine -> returns false
        assertFalse(PARACETAMOL.equals(GABAPENTIN));

        // different name -> returns false
        Medicine editedParacetamol = new MedicineBuilder(PARACETAMOL).withName(VALID_NAME_GABAPENTIN).build();
        assertFalse(PARACETAMOL.equals(editedParacetamol));

        // different company -> returns false
        editedParacetamol = new MedicineBuilder(PARACETAMOL).withCompany(VALID_COMPANY_GABAPENTIN).build();
        assertFalse(PARACETAMOL.equals(editedParacetamol));

        // different quantity -> returns false
        editedParacetamol = new MedicineBuilder(PARACETAMOL).withQuantity(VALID_QUANTITY_GABAPENTIN).build();
        assertFalse(PARACETAMOL.equals(editedParacetamol));

        // different expiry date-> returns false
        editedParacetamol = new MedicineBuilder(PARACETAMOL).withExpiry(VALID_EXPIRY_GABAPENTIN).build();
        assertFalse(PARACETAMOL.equals(editedParacetamol));

        // different tags -> returns false
        editedParacetamol = new MedicineBuilder(PARACETAMOL).withTags(VALID_TAG_PAINKILLER).build();
        assertFalse(PARACETAMOL.equals(editedParacetamol));
    }
}
