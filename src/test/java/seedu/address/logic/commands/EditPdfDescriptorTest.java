package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_1;
import static seedu.address.logic.commands.CommandTestUtil.DESC_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_LECTURE;

import org.junit.Test;

import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.testutil.EditPdfDescriptorBuilder;

public class EditPdfDescriptorTest {
    @Test
    public void equals() {
        // same values -> returns true
        EditPersonDescriptor descriptorWithSameValues = new EditPersonDescriptor(DESC_1);
        assertTrue(DESC_1.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_1.equals(DESC_1));

        // null -> returns false
        assertFalse(DESC_1.equals(null));

        // different types -> returns false
        assertFalse(DESC_1.equals(5));

        // different values -> returns false
        assertFalse(DESC_1.equals(DESC_2));

        // different name -> returns false
        EditPersonDescriptor editedA = new EditPdfDescriptorBuilder(DESC_1).withName(VALID_NAME_2).build();
        assertFalse(DESC_1.equals(editedA));

        // different tags -> returns false
        editedA = new EditPdfDescriptorBuilder(DESC_1).withTags(VALID_TAG_LECTURE).build();
        assertFalse(DESC_1.equals(editedA));
    }
}
