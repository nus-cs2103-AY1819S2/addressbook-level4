package seedu.address.model.person.patient;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BENSON;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BENSON;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BENSON;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_BENSON;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BENSON;
import static seedu.address.testutil.TypicalPatients.ALICE;
import static seedu.address.testutil.TypicalPatients.BENSON;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.testutil.PatientBuilder;

public class PatientTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Patient patient = new PatientBuilder().build();
        thrown.expect(UnsupportedOperationException.class);
        patient.getTags().remove(0);
    }

    @Test
    public void isSamePatient() {
        // same object -> returns true
        assertTrue(ALICE.isSamePatient(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSamePatient(null));

        // different phone -> returns false
        Patient editedAlice = new PatientBuilder(ALICE)
            .withPhone(VALID_PHONE_BENSON).build();
        assertFalse(ALICE.isSamePatient(editedAlice));

        // different NRIC -> returns false
        editedAlice = new PatientBuilder(ALICE)
            .withNric(VALID_NRIC_BENSON).build();
        assertFalse(ALICE.isSamePatient(editedAlice));

        // different name -> returns false
        editedAlice = new PatientBuilder(ALICE)
            .withName(VALID_NAME_BENSON).build();
        assertFalse(ALICE.isSamePatient(editedAlice));

        // same name, same phone, different email -> returns true
        editedAlice = new PatientBuilder(ALICE).withEmail
            (VALID_EMAIL_BENSON).build();
        assertTrue(ALICE.isSamePatient(editedAlice));


        // same name, phone, same nric, different conditions ->
        // returns true
        editedAlice = new PatientBuilder(ALICE)
            .withConditions(BENSON.getConditions()).build();
        assertTrue(ALICE.isSamePatient(editedAlice));

    }

    @Test
    public void equals() {
        // same values -> returns true
        Patient editedAlice = new PatientBuilder(ALICE).build();
        assertTrue(ALICE.equals(editedAlice));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different person -> returns false
        assertFalse(ALICE.equals(BENSON));

        // different name -> returns false
        editedAlice = new PatientBuilder(ALICE)
            .withName(VALID_NAME_BENSON).build();
        assertFalse(ALICE.equals(editedAlice));

        // different NRIC -> returns false
        editedAlice = new PatientBuilder(ALICE)
            .withNric(VALID_NRIC_BENSON).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new PatientBuilder(ALICE)
            .withPhone(VALID_PHONE_BENSON).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new PatientBuilder(ALICE)
            .withEmail(VALID_EMAIL_BENSON).build();
        assertFalse(ALICE.equals(editedAlice));

        // different address -> returns false
        editedAlice = new PatientBuilder(ALICE)
            .withAddress(VALID_ADDRESS_BENSON).build();
        assertFalse(ALICE.equals(editedAlice));

    }
}
