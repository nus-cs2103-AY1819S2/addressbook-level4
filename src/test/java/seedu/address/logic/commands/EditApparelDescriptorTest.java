package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_ARMANI;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOBOHOO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COLOR_BLUE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_B;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TYPE_BOTTOM;

import org.junit.Test;

import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.testutil.EditApparelDescriptorBuilder;

public class EditApparelDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditPersonDescriptor descriptorWithSameValues = new EditPersonDescriptor(DESC_ARMANI);
        assertTrue(DESC_ARMANI.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_ARMANI.equals(DESC_ARMANI));

        // null -> returns false
        assertFalse(DESC_ARMANI.equals(null));

        // different types -> returns false
        assertFalse(DESC_ARMANI.equals(5));

        // different values -> returns false
        assertFalse(DESC_ARMANI.equals(DESC_BOBOHOO));

        // different name -> returns false
        EditPersonDescriptor editedAmy = new EditApparelDescriptorBuilder(DESC_ARMANI).withName(VALID_NAME_B).build();
        assertFalse(DESC_ARMANI.equals(editedAmy));

        // different color -> returns false
        editedAmy = new EditApparelDescriptorBuilder(DESC_ARMANI).withColor(VALID_COLOR_BLUE).build();
        assertFalse(DESC_ARMANI.equals(editedAmy));

        // different clothingType -> returns false
        editedAmy = new EditApparelDescriptorBuilder(DESC_ARMANI).withClothingType(VALID_TYPE_BOTTOM).build();
        assertFalse(DESC_ARMANI.equals(editedAmy));

        /* not dealing with address and tags anymore
        // different address -> returns false
        editedAmy = new EditApparelDescriptorBuilder(DESC_ARMANI).build();
        assertFalse(DESC_ARMANI.equals(editedAmy));

        // different tags -> returns false
        editedAmy = new EditApparelDescriptorBuilder(DESC_ARMANI).build();
        assertFalse(DESC_ARMANI.equals(editedAmy));*/
    }
}
