package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENDER_STEVEN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_STEVEN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_STEVEN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SPECIALISATION_GENERAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SPECIALISATION_MASSAGE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_YEAR_STEVEN;
import static seedu.address.testutil.TypicalDoctors.ALVINA;
import static seedu.address.testutil.TypicalDoctors.STEVEN;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.person.doctor.Doctor;
import seedu.address.testutil.DoctorBuilder;


public class DoctorTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Doctor doctor = new DoctorBuilder().build();
        thrown.expect(UnsupportedOperationException.class);
        doctor.getSpecs().remove(0);
    }

    @Test
    public void isSameDoctor() {
        // same object -> returns true
        assertTrue(ALVINA.isSameDoctor(ALVINA));

        // null -> returns false
        assertFalse(ALVINA.isSameDoctor(null));

        // different age and phone -> returns false
        Doctor editedAlvina = new DoctorBuilder(ALVINA).withYear(VALID_YEAR_STEVEN)
                .withPhone(VALID_PHONE_STEVEN).build();
        assertFalse(ALVINA.isSameDoctor(editedAlvina));

        // different name, same phone -> returns false
        editedAlvina = new DoctorBuilder(ALVINA).withName(VALID_NAME_STEVEN).build();
        assertTrue(ALVINA.isSameDoctor(editedAlvina));

        // same name, same phone, different attributes -> returns true
        editedAlvina = new DoctorBuilder(ALVINA)
                .withGender(VALID_GENDER_STEVEN).withYear(VALID_YEAR_STEVEN)
                .withSpecs(VALID_SPECIALISATION_MASSAGE).build();
        assertTrue(ALVINA.isSameDoctor(editedAlvina));

    }

    @Test
    public void equals() {
        // same values -> returns true
        Doctor alvinaCopy = new DoctorBuilder(ALVINA).build();
        assertTrue(ALVINA.equals(alvinaCopy));

        // same object -> returns true
        assertTrue(ALVINA.equals(ALVINA));

        // null -> returns false
        assertFalse(ALVINA.equals(null));

        // different type -> returns false
        assertFalse(ALVINA.equals(5));

        // different doctor -> returns false
        assertFalse(ALVINA.equals(STEVEN));

        // different name -> returns false
        Doctor editedAlvina = new DoctorBuilder(ALVINA).withName(VALID_NAME_STEVEN).build();
        assertFalse(ALVINA.equals(editedAlvina));

        // different gender -> returns false
        editedAlvina = new DoctorBuilder(ALVINA).withGender(VALID_GENDER_STEVEN).build();
        assertFalse(ALVINA.equals(editedAlvina));

        // different years of experience -> returns false
        editedAlvina = new DoctorBuilder(ALVINA).withYear(VALID_YEAR_STEVEN).build();
        assertFalse(ALVINA.equals(editedAlvina));

        // different phone -> returns false
        editedAlvina = new DoctorBuilder(ALVINA).withPhone(VALID_PHONE_STEVEN).build();
        assertFalse(ALVINA.equals(editedAlvina));

        // different specialisations -> returns false
        editedAlvina = new DoctorBuilder(ALVINA).withSpecs(VALID_SPECIALISATION_GENERAL).build();
        assertFalse(ALVINA.equals(editedAlvina));
    }
}
