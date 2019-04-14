package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMOXICILLIN_BATCH;
import static seedu.address.logic.commands.CommandTestUtil.DESC_GABAPENTIN_BATCH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_GABAPENTIN;
import static seedu.address.logic.commands.UpdateCommand.UpdateBatchDescriptor;

import org.junit.Test;

import seedu.address.testutil.UpdateBatchDescriptorBuilder;

public class UpdateBatchDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        UpdateBatchDescriptor descriptorWithSameValues = new UpdateBatchDescriptor(DESC_AMOXICILLIN_BATCH);
        assertTrue(DESC_AMOXICILLIN_BATCH.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_AMOXICILLIN_BATCH.equals(DESC_AMOXICILLIN_BATCH));

        // null -> returns false
        assertFalse(DESC_AMOXICILLIN_BATCH.equals(null));

        // different types -> returns false
        assertFalse(DESC_AMOXICILLIN_BATCH.equals(5));

        // different values -> returns false
        assertFalse(DESC_AMOXICILLIN_BATCH.equals(DESC_GABAPENTIN_BATCH));

        // different name -> returns false
        UpdateBatchDescriptor updateBatchDescriptor = new UpdateBatchDescriptorBuilder(DESC_AMOXICILLIN_BATCH)
                .withBatchNumber(VALID_NAME_GABAPENTIN).build();
        assertFalse(DESC_AMOXICILLIN_BATCH.equals(updateBatchDescriptor));

        // different quantity -> returns false
        updateBatchDescriptor = new UpdateBatchDescriptorBuilder(DESC_AMOXICILLIN_BATCH).withNoQuantity().build();
        assertFalse(DESC_AMOXICILLIN_BATCH.equals(updateBatchDescriptor));

        // different expiry -> returns false
        updateBatchDescriptor = new UpdateBatchDescriptorBuilder(DESC_AMOXICILLIN_BATCH).withNoExpiry().build();
        assertFalse(DESC_AMOXICILLIN_BATCH.equals(updateBatchDescriptor));
    }
}

