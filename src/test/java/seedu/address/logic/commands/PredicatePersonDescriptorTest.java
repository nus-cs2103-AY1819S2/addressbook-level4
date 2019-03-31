package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.Test;

import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.testutil.EditPersonDescriptorBuilder;

public class PredicatePersonDescriptorTest {

    @Test
    public void equals() {
        SearchCommand.PredicatePersonDescriptor descriptor = new SearchCommand.PredicatePersonDescriptor();
        descriptor.setName
            (new HashSet<>(Arrays.asList("Alice", "Bob")));

        // same object -> returns true
        assertTrue(descriptor.equals(descriptor));

        // same reverse values -> returns true
        SearchCommand.PredicatePersonDescriptor descriptorReverse = new SearchCommand.PredicatePersonDescriptor();
        descriptorReverse.setName
            (new HashSet<>(Arrays.asList("Bob", "Alice")));
        assertTrue(descriptorReverse.equals(descriptor));

        // null -> returns false
        assertFalse(descriptor.equals(null));

        // different types -> returns false
        assertFalse(descriptor.equals(5));

        // different name -> returns false
        SearchCommand.PredicatePersonDescriptor descriptorDifferent = new SearchCommand.PredicatePersonDescriptor();
        descriptorDifferent.setName
            (new HashSet<>(Arrays.asList("Alice", "Tom")));
        assertFalse(descriptor.equals(descriptorDifferent));
    }
}
