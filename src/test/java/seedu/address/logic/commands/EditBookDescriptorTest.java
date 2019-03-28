package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_ALI;
import static seedu.address.logic.commands.CommandTestUtil.DESC_CS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AUTHOR_CS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BOOKNAME_CS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RATING_CS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_TEXTBOOK;

import org.junit.Test;

import seedu.address.logic.commands.EditBookCommand.EditBookDescriptor;
import seedu.address.testutil.EditBookDescriptorBuilder;

public class EditBookDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditBookCommand.EditBookDescriptor descriptorWithSameValues = new EditBookDescriptor(DESC_ALI);
        assertTrue(DESC_ALI.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_ALI.equals(DESC_ALI));

        // null -> returns false
        assertFalse(DESC_ALI.equals(null));

        // different types -> returns false
        assertFalse(DESC_ALI.equals(5));

        // different values -> returns false
        assertFalse(DESC_ALI.equals(DESC_CS));

        // different name -> returns false
        EditBookDescriptor editedAlice = new EditBookDescriptorBuilder(DESC_ALI)
                .withBookName(VALID_BOOKNAME_CS)
                .build();
        assertFalse(DESC_ALI.equals(editedAlice));

        // different author -> returns false
        editedAlice = new EditBookDescriptorBuilder(DESC_ALI).withAuthor(VALID_AUTHOR_CS).build();
        assertFalse(DESC_ALI.equals(editedAlice));

        // different rating -> returns false
        editedAlice = new EditBookDescriptorBuilder(DESC_ALI).withRating(VALID_RATING_CS).build();
        assertFalse(DESC_ALI.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new EditBookDescriptorBuilder(DESC_ALI).withTags(VALID_TAG_TEXTBOOK).build();
        assertFalse(DESC_ALI.equals(editedAlice));
    }
}
