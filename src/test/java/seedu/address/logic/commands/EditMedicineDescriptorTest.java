package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMOXICILLIN;
import static seedu.address.logic.commands.CommandTestUtil.DESC_GABAPENTIN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COMPANY_GABAPENTIN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_GABAPENTIN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_PAINKILLER;

import org.junit.Test;

import seedu.address.logic.commands.EditCommand.EditMedicineDescriptor;
import seedu.address.testutil.EditMedicineDescriptorBuilder;

public class EditMedicineDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditMedicineDescriptor descriptorWithSameValues = new EditMedicineDescriptor(DESC_AMOXICILLIN);
        assertTrue(DESC_AMOXICILLIN.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_AMOXICILLIN.equals(DESC_AMOXICILLIN));

        // null -> returns false
        assertFalse(DESC_AMOXICILLIN.equals(null));

        // different types -> returns false
        assertFalse(DESC_AMOXICILLIN.equals(5));

        // different values -> returns false
        assertFalse(DESC_AMOXICILLIN.equals(DESC_GABAPENTIN));

        // different name -> returns false
        EditMedicineDescriptor editedAmoxicillin = new EditMedicineDescriptorBuilder(DESC_AMOXICILLIN)
                .withName(VALID_NAME_GABAPENTIN).build();
        assertFalse(DESC_AMOXICILLIN.equals(editedAmoxicillin));

        // different company -> returns false
        editedAmoxicillin = new EditMedicineDescriptorBuilder(DESC_AMOXICILLIN).withCompany(VALID_COMPANY_GABAPENTIN)
                .build();
        assertFalse(DESC_AMOXICILLIN.equals(editedAmoxicillin));

        // different tags -> returns false
        editedAmoxicillin = new EditMedicineDescriptorBuilder(DESC_AMOXICILLIN).withTags(VALID_TAG_PAINKILLER).build();
        assertFalse(DESC_AMOXICILLIN.equals(editedAmoxicillin));
    }
}
