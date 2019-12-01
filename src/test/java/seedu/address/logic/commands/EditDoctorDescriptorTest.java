package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_ALVINA;
import static seedu.address.logic.commands.CommandTestUtil.DESC_STEVEN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENDER_STEVEN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_STEVEN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_STEVEN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SPECIALISATION_MASSAGE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_YEAR_STEVEN;

import org.junit.Test;

import seedu.address.logic.commands.doctor.EditDoctorCommand.EditDoctorDescriptor;
import seedu.address.testutil.EditDoctorDescriptorBuilder;

public class EditDoctorDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditDoctorDescriptor descriptorWithSameValues = new EditDoctorDescriptor(DESC_ALVINA);
        assertTrue(DESC_ALVINA.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_ALVINA.equals(DESC_ALVINA));

        // null -> returns false
        assertFalse(DESC_ALVINA.equals(null));

        // different types -> returns false
        assertFalse(DESC_ALVINA.equals(5));

        // different values -> returns false
        assertFalse(DESC_ALVINA.equals(DESC_STEVEN));

        // different name -> returns false
        EditDoctorDescriptor editedAlvina = new EditDoctorDescriptorBuilder(DESC_ALVINA)
                .withName(VALID_NAME_STEVEN).build();
        assertFalse(DESC_ALVINA.equals(editedAlvina));

        // different phone -> returns false
        editedAlvina = new EditDoctorDescriptorBuilder(DESC_ALVINA).withPhone(VALID_PHONE_STEVEN).build();
        assertFalse(DESC_ALVINA.equals(editedAlvina));

        // different gender -> returns false
        editedAlvina = new EditDoctorDescriptorBuilder(DESC_ALVINA).withGender(VALID_GENDER_STEVEN).build();
        assertFalse(DESC_ALVINA.equals(editedAlvina));

        // different year -> returns false
        editedAlvina = new EditDoctorDescriptorBuilder(DESC_ALVINA).withYear(VALID_YEAR_STEVEN).build();
        assertFalse(DESC_ALVINA.equals(editedAlvina));

        // different specialisations -> returns false
        editedAlvina = new EditDoctorDescriptorBuilder(DESC_ALVINA).withSpecs(VALID_SPECIALISATION_MASSAGE).build();
        assertFalse(DESC_ALVINA.equals(editedAlvina));
    }
}
