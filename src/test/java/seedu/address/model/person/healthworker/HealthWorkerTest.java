package seedu.address.model.person.healthworker;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BETTY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_BETTY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ORGANIZATION_BETTY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BETTY;
import static seedu.address.testutil.TypicalHealthWorkers.ANDY;
import static seedu.address.testutil.TypicalHealthWorkers.BETTY;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.testutil.HealthWorkerBuilder;

public class HealthWorkerTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void isSameHealthWorker() {
        // same object -> returns true
        assertTrue(ANDY.isSameHealthWorker(ANDY));

        // null -> returns false
        assertFalse(ANDY.isSameHealthWorker(null));

        // different phone -> returns true
        HealthWorker editedAndy = ((HealthWorkerBuilder) new HealthWorkerBuilder(ANDY)
                .withPhone(VALID_PHONE_BETTY)).build();
        assertTrue(ANDY.isSameHealthWorker(editedAndy));

        // different NRIC -> returns false
        editedAndy = ((HealthWorkerBuilder) new HealthWorkerBuilder(ANDY)
                .withNric(VALID_NRIC_BETTY)).build();
        assertFalse(ANDY.isSameHealthWorker(editedAndy));

        // different name -> returns false
        editedAndy = ((HealthWorkerBuilder) new HealthWorkerBuilder(ANDY)
                .withName(VALID_NAME_BETTY)).build();
        assertFalse(ANDY.isSameHealthWorker(editedAndy));

        // same name, same phone, different organization -> returns true
        editedAndy = new HealthWorkerBuilder(ANDY)
                .withOrganization(VALID_ORGANIZATION_BETTY).build();
        assertTrue(ANDY.isSameHealthWorker(editedAndy));
    }

    @Test
    public void equals() {
        // same values -> returns true
        HealthWorker editedAndy = new HealthWorkerBuilder(ANDY).build();
        assertTrue(ANDY.equals(editedAndy));

        // same object -> returns true
        assertTrue(ANDY.equals(ANDY));

        // null -> returns false
        assertFalse(ANDY.equals(null));

        // different type -> returns false
        assertFalse(ANDY.equals(5));

        // different person -> returns false
        assertFalse(ANDY.equals(BETTY));

        // different name -> returns false
        editedAndy = ((HealthWorkerBuilder) new HealthWorkerBuilder(ANDY)
                .withName(VALID_NAME_BETTY)).build();
        assertFalse(ANDY.equals(editedAndy));

        // different NRIC -> returns false
        editedAndy = ((HealthWorkerBuilder) new HealthWorkerBuilder(ANDY)
                .withNric(VALID_NRIC_BETTY)).build();
        assertFalse(ANDY.equals(editedAndy));

        // different phone -> returns false
        editedAndy = ((HealthWorkerBuilder) new HealthWorkerBuilder(ANDY)
                .withPhone(VALID_PHONE_BETTY)).build();
        assertFalse(ANDY.equals(editedAndy));

        // different skills -> returns false
        editedAndy = new HealthWorkerBuilder(ANDY)
                .withSkills(BETTY.getSkills()).build();
        assertFalse(ANDY.equals(editedAndy));

        // different organization -> returns false
        editedAndy = new HealthWorkerBuilder(ANDY)
                .withOrganization(VALID_ORGANIZATION_BETTY).build();
        assertFalse(ANDY.equals(editedAndy));

    }
}
