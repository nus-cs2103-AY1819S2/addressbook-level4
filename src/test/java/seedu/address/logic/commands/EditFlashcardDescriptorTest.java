package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_GOOD;
import static seedu.address.logic.commands.CommandTestUtil.DESC_HITBAG;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BACKFACE_HITBAG;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FRONTFACE_HITBAG;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_CHINESE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_INDONESIAN;

import org.junit.Test;

import seedu.address.logic.commands.EditCommand.EditFlashcardDescriptor;
import seedu.address.testutil.EditFlashcardDescriptorBuilder;

public class EditFlashcardDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditFlashcardDescriptor descriptorWithSameValues = new EditFlashcardDescriptor(DESC_GOOD);
        assertTrue(DESC_GOOD.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_GOOD.equals(DESC_GOOD));

        // null -> returns false
        assertFalse(DESC_GOOD.equals(null));

        // different types -> returns false
        assertFalse(DESC_GOOD.equals(5));

        // different values -> returns false
        assertFalse(DESC_GOOD.equals(DESC_HITBAG));

        // different frontFace -> returns false
        EditFlashcardDescriptor editedGood =
            new EditFlashcardDescriptorBuilder(DESC_GOOD).withFrontFace(VALID_FRONTFACE_HITBAG).build();
        assertFalse(DESC_GOOD.equals(editedGood));

        // different backFace -> returns false
        editedGood = new EditFlashcardDescriptorBuilder(DESC_GOOD).withBackFace(VALID_BACKFACE_HITBAG).build();
        assertFalse(DESC_GOOD.equals(editedGood));

        // different tags -> returns false
        editedGood = new EditFlashcardDescriptorBuilder(DESC_GOOD)
            .withTags(VALID_TAG_INDONESIAN, VALID_TAG_CHINESE).build();
        assertFalse(DESC_GOOD.equals(editedGood));
    }
}
