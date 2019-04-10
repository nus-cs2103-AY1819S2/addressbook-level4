package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.Test;

public class PredicatePersonDescriptorTest {

    @Test
    public void equals() {
        FilterCommand.PredicatePersonDescriptor descriptor = new FilterCommand.PredicatePersonDescriptor();
        descriptor.setName(new HashSet<>(Arrays.asList("Alice", "Bob")));

        // same object -> returns true
        assertTrue(descriptor.equals(descriptor));

        // same reverse values -> returns true
        FilterCommand.PredicatePersonDescriptor descriptorReverse = new FilterCommand.PredicatePersonDescriptor();
        descriptorReverse.setName(new HashSet<>(Arrays.asList("Bob", "Alice")));
        assertTrue(descriptorReverse.equals(descriptor));

        // different types -> returns false
        assertFalse(descriptor.equals(5));

        // different name -> returns false
        FilterCommand.PredicatePersonDescriptor descriptorDifferent = new FilterCommand.PredicatePersonDescriptor();
        descriptorDifferent.setName(new HashSet<>(Arrays.asList("Alice", "Tom")));
        assertFalse(descriptor.equals(descriptorDifferent));
    }
}
