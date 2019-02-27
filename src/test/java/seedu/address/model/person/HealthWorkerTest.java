package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_HW_B;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_HW_B;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_HW_B;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_HW_B;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ORGANIZATION_HW_B;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_HW_A;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_HW_B;
import static seedu.address.testutil.TypicalPersons.TEST_HW_A;
import static seedu.address.testutil.TypicalPersons.TEST_HW_B;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.testutil.HealthWorkerBuilder;

public class HealthWorkerTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        HealthWorker healthWorker = new HealthWorkerBuilder().build();
        thrown.expect(UnsupportedOperationException.class);
        healthWorker.getTags().remove(0);
    }

    @Test
    public void isSameHealthWorker() {
        // same object -> returns true
        assertTrue(TEST_HW_A.isSameHealthWorker(TEST_HW_A));

        // null -> returns false
        assertFalse(TEST_HW_A.isSameHealthWorker(null));

        // different phone -> returns false
        HealthWorker editedTestWorkerA = new HealthWorkerBuilder(TEST_HW_A)
                .withPhone(VALID_PHONE_HW_B).build();
        assertFalse(TEST_HW_A.isSameHealthWorker(editedTestWorkerA));

        // different NRIC -> returns false
        editedTestWorkerA = new HealthWorkerBuilder(TEST_HW_A)
                .withNric(VALID_NRIC_HW_B).build();
        assertFalse(TEST_HW_A.isSameHealthWorker(editedTestWorkerA));

        // different name -> returns false
        editedTestWorkerA = new HealthWorkerBuilder(TEST_HW_A)
                .withName(VALID_NAME_HW_B).build();
        assertFalse(TEST_HW_A.isSameHealthWorker(editedTestWorkerA));

        // same name, same phone, different organization -> returns false
        editedTestWorkerA = new HealthWorkerBuilder(TEST_HW_A).withPhone
                (VALID_PHONE_HW_A).withPhone(VALID_PHONE_HW_A)
                .withOrganization(VALID_ORGANIZATION_HW_B).build();
        assertFalse(TEST_HW_A.isSameHealthWorker(editedTestWorkerA));

        // same name, same phone, same organization, different email -> returns
        // true
        editedTestWorkerA = new HealthWorkerBuilder(TEST_HW_A).withEmail
                (VALID_EMAIL_HW_B).build();
        assertTrue(TEST_HW_A.isSamePerson(editedTestWorkerA));
    }

    @Test
    public void equals() {
        // same values -> returns true
        HealthWorker testWorkerA = new HealthWorkerBuilder(TEST_HW_A).build();
        assertTrue(TEST_HW_A.equals(testWorkerA));

        // same object -> returns true
        assertTrue(TEST_HW_A.equals(TEST_HW_A));

        // null -> returns false
        assertFalse(TEST_HW_A.equals(null));

        // different type -> returns false
        assertFalse(TEST_HW_A.equals(5));

        // different person -> returns false
        assertFalse(TEST_HW_A.equals(TEST_HW_B));

        // different name -> returns false
        HealthWorker editedWorkerA = new HealthWorkerBuilder(TEST_HW_A)
                .withName(VALID_NAME_HW_B).build();
        assertFalse(TEST_HW_A.equals(editedWorkerA));

        // different NRIC -> returns false
        editedWorkerA = new HealthWorkerBuilder(TEST_HW_A)
                .withNric(VALID_NRIC_HW_B).build();
        assertFalse(TEST_HW_A.equals(editedWorkerA));

        // different phone -> returns false
        editedWorkerA = new HealthWorkerBuilder(TEST_HW_A)
                .withPhone(VALID_PHONE_HW_B).build();
        assertFalse(TEST_HW_A.equals(editedWorkerA));

        // different email -> returns false
        editedWorkerA = new HealthWorkerBuilder(TEST_HW_A)
                .withEmail(VALID_EMAIL_HW_B).build();
        assertFalse(TEST_HW_A.equals(editedWorkerA));

        // different address -> returns false
        editedWorkerA = new HealthWorkerBuilder(TEST_HW_A)
                .withAddress(VALID_ADDRESS_HW_B).build();
        assertFalse(TEST_HW_A.equals(editedWorkerA));
    }
}
