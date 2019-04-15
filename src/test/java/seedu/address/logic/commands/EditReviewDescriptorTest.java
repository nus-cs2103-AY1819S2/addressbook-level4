package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.logic.commands.EditReviewCommand.EditReviewDescriptor;
import seedu.address.model.review.Entry;

public class EditReviewDescriptorTest {

    @Test
    public void equals() {
        EditReviewDescriptor descriptor = new EditReviewDescriptor();
        EditReviewDescriptor sameValueDescriptor = new EditReviewDescriptor();
        EditReviewDescriptor diffValueDescriptor = new EditReviewDescriptor();
        diffValueDescriptor.setEntry(new Entry("Not the same!"));

        //same values: return true
        assertTrue(descriptor.equals(sameValueDescriptor));

        //same object: return true
        assertTrue(descriptor.equals(descriptor));

        //null: return false
        assertFalse(descriptor.equals(null));

        //diff type: return false
        assertFalse(descriptor.equals(5));

        //diff value: return false
        assertFalse(descriptor.equals(diffValueDescriptor));
    }
}
