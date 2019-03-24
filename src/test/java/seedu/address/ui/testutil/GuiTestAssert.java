package seedu.address.ui.testutil;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import guitests.guihandles.PatientCardHandle;
import guitests.guihandles.PatientListPanelHandle;
import guitests.guihandles.ResultDisplayHandle;
//import javax.print.Doc;

import guitests.guihandles.DoctorCardHandle;
import guitests.guihandles.DoctorListPanelHandle;
import seedu.address.model.person.Doctor;
import seedu.address.model.person.Patient;


/**
 * A set of assertion methods useful for writing GUI tests.
 */
public class GuiTestAssert {
    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertCardEquals(PatientCardHandle expectedCard, PatientCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getName(), actualCard.getName());
        assertEquals(expectedCard.getGender(), actualCard.getGender());
        assertEquals(expectedCard.getAge(), actualCard.getAge());
        assertEquals(expectedCard.getPhone(), actualCard.getPhone());
        assertEquals(expectedCard.getAddress(), actualCard.getAddress());
        assertEquals(expectedCard.getTags(), actualCard.getTags());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedPatient}.
     */
    public static void assertCardDisplaysPatient(Patient expectedPatient, PatientCardHandle actualCard) {
        assertEquals(expectedPatient.getName().fullName, actualCard.getName());
        assertEquals(expectedPatient.getGender().value, actualCard.getGender());
        assertEquals(expectedPatient.getAge().value, actualCard.getAge());
        assertEquals(expectedPatient.getPhone().value, actualCard.getPhone());
        assertEquals(expectedPatient.getAddress().value, actualCard.getAddress());
        assertEquals(expectedPatient.getTags().stream().map(tag -> tag.tagName).collect(Collectors.toList()),
                actualCard.getTags());
    }

    /**
     * Asserts that the list in {@code patientListPanelHandle} displays the details of {@code patients} correctly and
     * in the correct order.
     */
    public static void assertListMatching(PatientListPanelHandle patientListPanelHandle, Patient... patients) {
        for (int i = 0; i < patients.length; i++) {
            patientListPanelHandle.navigateToCard(i);
            assertCardDisplaysPatient(patients[i], patientListPanelHandle.getPatientCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code patientListPanelHandle} displays the details of {@code patients} correctly and
     * in the correct order.
     */
    public static void assertListMatching(PatientListPanelHandle patientListPanelHandle, List<Patient> patients) {
        assertListMatching(patientListPanelHandle, patients.toArray(new Patient[0]));
    }

    /**
     * Asserts the size of the list in {@code patientListPanelHandle} equals to {@code size}.
     */
    public static void assertListSize(PatientListPanelHandle patientListPanelHandle, int size) {
        int numberOfPeople = patientListPanelHandle.getListSize();
        assertEquals(size, numberOfPeople);
    }

    /**
     * Asserts the message shown in {@code resultDisplayHandle} equals to {@code expected}.
     */
    public static void assertResultMessage(ResultDisplayHandle resultDisplayHandle, String expected) {
        assertEquals(expected, resultDisplayHandle.getText());
    }






    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertCardEquals(DoctorCardHandle expectedCard, DoctorCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getName(), actualCard.getName());
        assertEquals(expectedCard.getPhone(), actualCard.getPhone());
        assertEquals(expectedCard.getGender(), actualCard.getGender());
        assertEquals(expectedCard.getAge(), actualCard.getAge());
        assertEquals(expectedCard.getSpecialisations(), actualCard.getSpecialisations());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedPerson}.
     */
    public static void assertCardDisplaysDoctor(Doctor expectedDoctor, DoctorCardHandle actualCard) {
        assertEquals(expectedDoctor.getName().fullName, actualCard.getName());
        assertEquals(expectedDoctor.getPhone().value, actualCard.getPhone());
        assertEquals(expectedDoctor.getGender().value, actualCard.getGender());
        assertEquals(expectedDoctor.getAge().value, actualCard.getAge());
        assertEquals(expectedDoctor.getSpecs().stream().map(spec -> spec.specialisation).collect(Collectors.toList()),
                actualCard.getSpecialisations());
    }

    /**
     * Asserts that the list in {@code doctorListPanelHandle} displays the details of {@code doctors} correctly and
     * in the correct order.
     */
    public static void assertListMatching(DoctorListPanelHandle doctorListPanelHandle, Doctor... doctors) {
        for (int i = 0; i < doctors.length; i++) {
            doctorListPanelHandle.navigateToCard(i);
            assertCardDisplaysDoctor(doctors[i], doctorListPanelHandle.getDoctorCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code doctorListPanelHandle} displays the details of {@code doctors} correctly and
     * in the correct order.
     */
    public static void assertListMatching(DoctorListPanelHandle doctorListPanelHandle, List<Doctor> doctors) {
        assertListMatching(doctorListPanelHandle, doctors.toArray(new Doctor[0]));
    }

    /**
     * Asserts the size of the list in {@code doctorListPanelHandle} equals to {@code size}.
     */
    public static void assertListSize(DoctorListPanelHandle doctorListPanelHandle, int size) {
        int numberOfPeople = doctorListPanelHandle.getListSize();
        assertEquals(size, numberOfPeople);
    }

}
