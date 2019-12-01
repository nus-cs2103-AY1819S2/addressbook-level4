package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENDER_STEVEN;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PATIENTS;
import static seedu.address.testutil.TypicalDoctors.ALVINA;
import static seedu.address.testutil.TypicalDoctors.STEVEN;
import static seedu.address.testutil.TypicalPatients.ALICE;
import static seedu.address.testutil.TypicalPatients.BENSON;
import static seedu.address.testutil.TypicalPatients.BOB;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.person.doctor.Doctor;
import seedu.address.model.person.exceptions.DoctorNotFoundException;
import seedu.address.model.person.exceptions.PatientNotFoundException;
import seedu.address.model.person.patient.Patient;
import seedu.address.model.person.patient.PatientNameContainsKeywordsPredicate;
import seedu.address.testutil.DocXBuilder;
import seedu.address.testutil.DoctorBuilder;
import seedu.address.testutil.PatientBuilder;

//import static seedu.address.model.Model.PREDICATE_SHOW_ALL_DOCTORS;
//import seedu.address.model.person.DoctorNameContainsKeywordsPredicate;

public class ModelManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new DocX(), new DocX(modelManager.getDocX()));
        assertEquals(null, modelManager.getSelectedPatient());
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.setUserPrefs(null);
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setDocXFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setDocXFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.setGuiSettings(null);
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setDocXFilePath_nullPath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.setDocXFilePath(null);
    }

    @Test
    public void setDocXFilePath_validPath_setsDocXFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setDocXFilePath(path);
        assertEquals(path, modelManager.getDocXFilePath());
    }

    @Test
    public void hasPatient_nullPatient_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.hasPatient(null);
    }

    @Test
    public void hasPatient_patientNotInDocX_returnsFalse() {
        assertFalse(modelManager.hasPatient(ALICE));
    }

    @Test
    public void hasPatient_patientInDocX_returnsTrue() {
        modelManager.addPatient(ALICE);
        assertTrue(modelManager.hasPatient(ALICE));
    }

    @Test
    public void deletePatient_patientIsSelectedAndFirstPatientInFilteredPatientList_selectionCleared() {
        modelManager.addPatient(ALICE);
        modelManager.setSelectedPatient(ALICE);
        modelManager.deletePatient(ALICE);
        assertEquals(null, modelManager.getSelectedPatient());
    }

    @Test
    public void deletePatient_patientIsSelectedAndSecondPatientInFilteredPatientList_firstPatientSelected() {
        modelManager.addPatient(ALICE);
        modelManager.addPatient(BOB);
        assertEquals(Arrays.asList(ALICE, BOB), modelManager.getFilteredPatientList());
        modelManager.setSelectedPatient(BOB);
        modelManager.deletePatient(BOB);
        assertEquals(ALICE, modelManager.getSelectedPatient());
    }

    @Test
    public void setPatient_patientIsSelected_selectedPatientUpdated() {
        modelManager.addPatient(ALICE);
        modelManager.setSelectedPatient(ALICE);
        Patient updatedAlice = new PatientBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        modelManager.setPatient(ALICE, updatedAlice);
        assertEquals(updatedAlice, modelManager.getSelectedPatient());
    }

    @Test
    public void getFilteredPatientList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredPatientList().remove(0);
    }

    @Test
    public void setSelectedPatient_patientNotInFilteredPatientList_throwsPatientNotFoundException() {
        thrown.expect(PatientNotFoundException.class);
        modelManager.setSelectedPatient(ALICE);
    }

    @Test
    public void setSelectedPatient_patientInFilteredPatientList_setsSelectedPatient() {
        modelManager.addPatient(ALICE);
        assertEquals(Collections.singletonList(ALICE), modelManager.getFilteredPatientList());
        modelManager.setSelectedPatient(ALICE);
        assertEquals(ALICE, modelManager.getSelectedPatient());
    }

    @Test
    public void hasDoctor_doctorNotInDocX_returnsFalse() {
        assertFalse(modelManager.hasDoctor(ALVINA));
    }

    @Test
    public void hasDoctor_doctorInDocX_returnsTrue() {
        modelManager.addDoctor(ALVINA);
        assertTrue(modelManager.hasDoctor(ALVINA));
    }

    @Test
    public void deleteDoctor_doctorIsSelectedAndFirstDoctorInFilteredDoctorList_selectionCleared() {
        modelManager.addDoctor(ALVINA);
        modelManager.setSelectedDoctor(ALVINA);
        modelManager.deleteDoctor(ALVINA);
        assertEquals(null, modelManager.getSelectedDoctor());
    }

    @Test
    public void deleteDoctor_doctorIsSelectedAndSecondDoctorInFilteredDoctorList_firstDoctorSelected() {
        modelManager.addDoctor(ALVINA);
        modelManager.addDoctor(STEVEN);
        assertEquals(Arrays.asList(ALVINA, STEVEN), modelManager.getFilteredDoctorList());
        modelManager.setSelectedDoctor(STEVEN);
        modelManager.deleteDoctor(STEVEN);
        assertEquals(ALVINA, modelManager.getSelectedDoctor());
    }

    @Test
    public void setDoctor_doctorIsSelected_selectedDoctorUpdated() {
        modelManager.addDoctor(ALVINA);
        modelManager.setSelectedDoctor(ALVINA);
        Doctor updatedAlvina = new DoctorBuilder(ALVINA).withGender(VALID_GENDER_STEVEN).build();
        modelManager.setDoctor(ALVINA, updatedAlvina);
        assertEquals(updatedAlvina, modelManager.getSelectedDoctor());
    }

    @Test
    public void getFilteredDoctorList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredDoctorList().remove(0);
    }

    @Test
    public void setSelectedDoctor_doctorNotInFilteredDoctorList_throwsDoctorNotFoundException() {
        thrown.expect(DoctorNotFoundException.class);
        modelManager.setSelectedDoctor(ALVINA);
    }

    @Test
    public void setSelectedDoctor_doctorInFilteredDoctorList_setsSelectedDoctor() {
        modelManager.addDoctor(ALVINA);
        assertEquals(Collections.singletonList(ALVINA), modelManager.getFilteredDoctorList());
        modelManager.setSelectedDoctor(ALVINA);
        assertEquals(ALVINA, modelManager.getSelectedDoctor());
    }

    @Test
    public void equals() {
        DocX docX = new DocXBuilder().withPatient(ALICE).withPatient(BENSON)
                .withDoctor(ALVINA).withDoctor(STEVEN).build();
        DocX differentDocX = new DocX();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(docX, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(docX, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different docX -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentDocX, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredPatientList(new PatientNameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(docX, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredPatientList(PREDICATE_SHOW_ALL_PATIENTS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setDocXFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(docX, differentUserPrefs)));

        /*
        // different filteredList -> returns false
        String[] keywords_doctor = ALVINA.getName().fullName.split("\\s+");
        modelManager.updateFilteredDoctorList(new DoctorNameContainsKeywordsPredicate(Arrays.asList(keywords_doctor)));
        assertFalse(modelManager.equals(new ModelManager(docX, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredDoctorList(PREDICATE_SHOW_ALL_DOCTORS);
        */
    }
}
