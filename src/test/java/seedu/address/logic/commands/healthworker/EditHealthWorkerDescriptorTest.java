package seedu.address.logic.commands.healthworker;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_ANDY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BETTY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_BETTY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ORGANIZATION_BETTY;

import org.junit.Test;

import seedu.address.logic.commands.EditHealthWorkerCommand;
import seedu.address.model.tag.Specialisation;
import seedu.address.testutil.EditHealthWorkerDescriptorBuilder;

public class EditHealthWorkerDescriptorTest {

    @Test
    public void equals() {
        // same values-> return true
        EditHealthWorkerCommand.EditHealthWorkerDescriptor descriptor = new EditHealthWorkerCommand
                .EditHealthWorkerDescriptor(DESC_ANDY);
        assertTrue(DESC_ANDY.equals(descriptor));

        // same object -> returns true
        assertTrue(DESC_ANDY.equals(DESC_ANDY));

        // null -> return false
        assertFalse(DESC_ANDY.equals(null));

        // different types -> returns false
        assertFalse(DESC_ANDY.equals(5));

        // different name -> returns false
        descriptor = new EditHealthWorkerDescriptorBuilder(DESC_ANDY).withName(VALID_NAME_BETTY).build();
        assertFalse(DESC_ANDY.equals(descriptor));

        // different NRIC -> returns false
        descriptor = new EditHealthWorkerDescriptorBuilder(DESC_ANDY).withNric(VALID_NRIC_BETTY).build();
        assertFalse(DESC_ANDY.equals(descriptor));

        // different organization -> returns false
        descriptor = new EditHealthWorkerDescriptorBuilder(DESC_ANDY)
                .withOrganization(VALID_ORGANIZATION_BETTY).build();
        assertFalse(DESC_ANDY.equals(descriptor));

        // different skills -> return false
        descriptor = new EditHealthWorkerDescriptorBuilder(DESC_ANDY)
                .withSkills(Specialisation.ORTHOPAEDIC.name()).build();
        assertFalse(DESC_ANDY.equals(descriptor));
    }
}
