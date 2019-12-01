package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestDoctorAssert.assertCardDisplaysDoctor;

import org.junit.Test;

import guitests.guihandles.DoctorCardHandle;
import seedu.address.model.person.doctor.Doctor;
import seedu.address.testutil.DoctorBuilder;
import seedu.address.ui.doctor.DoctorCard;

public class DoctorCardTest extends GuiUnitTest {

    @Test
    public void display() {
        // no specs
        Doctor doctorWithNoSpecs = new DoctorBuilder().withSpecs(new String[0]).build();
        DoctorCard doctorCard = new DoctorCard(doctorWithNoSpecs, 1);
        uiPartRule.setUiPart(doctorCard);
        assertCardDisplay(doctorCard, doctorWithNoSpecs, 1);

        // with tags
        Doctor doctorWithSpecs = new DoctorBuilder().build();
        doctorCard = new DoctorCard(doctorWithSpecs, 2);
        uiPartRule.setUiPart(doctorCard);
        assertCardDisplay(doctorCard, doctorWithSpecs, 2);
    }

    @Test
    public void equals() {
        Doctor doctor = new DoctorBuilder().build();
        DoctorCard doctorCard = new DoctorCard(doctor, 0);

        // same doctor, same index -> returns true
        DoctorCard copy = new DoctorCard(doctor, 0);
        assertTrue(doctorCard.equals(copy));

        // same object -> returns true
        assertTrue(doctorCard.equals(doctorCard));

        // null -> returns false
        assertFalse(doctorCard.equals(null));

        // different types -> returns false
        assertFalse(doctorCard.equals(0));

        // different doctor, same index -> returns false
        Doctor differentDoctor = new DoctorBuilder().withName("differentName").build();
        assertFalse(doctorCard.equals(new DoctorCard(differentDoctor, 0)));

        // same doctor, different index -> returns false
        assertFalse(doctorCard.equals(new DoctorCard(doctor, 1)));
    }

    /**
     * Asserts that {@code doctorCard} displays the details of {@code expectedDoctor} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(DoctorCard doctorCard, Doctor expectedDoctor, int expectedId) {
        guiRobot.pauseForHuman();

        DoctorCardHandle doctorCardHandle = new DoctorCardHandle(doctorCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", doctorCardHandle.getId());

        // verify doctor details are displayed correctly
        assertCardDisplaysDoctor(expectedDoctor, doctorCardHandle);
    }
}
