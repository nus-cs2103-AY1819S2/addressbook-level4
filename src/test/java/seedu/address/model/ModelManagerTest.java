package seedu.address.model;

import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertTrue;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
//import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PDFS;
//import static seedu.address.testutil.TypicalPdfs.ALICE;
//import static seedu.address.testutil.TypicalPdfs.BENSON;
//import static seedu.address.testutil.TypicalPdfs.BOB;

import java.nio.file.Path;
import java.nio.file.Paths;
//import java.util.Arrays;
//import java.util.Collections;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.GuiSettings;
//import seedu.address.model.pdf.NameContainsKeywordsPredicate;
//import seedu.address.model.pdf.Pdf;
//import seedu.address.model.pdf.exceptions.PdfNotFoundException;
//import seedu.address.testutil.AddressBookBuilder;
//import seedu.address.testutil.PdfBuilder;

public class ModelManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new PdfBook(), new PdfBook(modelManager.getPdfBook()));
        assertEquals(null, modelManager.getSelectedPdf());
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.setUserPrefs(null);
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setPdfBookFilePath(Paths.get("address/book/file/value"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setPdfBookFilePath(Paths.get("new/address/book/file/value"));
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
    public void setPdfBookFilePath_nullPath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.setPdfBookFilePath(null);
    }

    @Test
    public void setPdfBookFilePath_validPath_setsAddressBookFilePath() {
        Path path = Paths.get("address/book/file/value");
        modelManager.setPdfBookFilePath(path);
        assertEquals(path, modelManager.getPdfBookFilePath());
    }

    @Test
    public void hasPdf_nullPdf_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.hasPdf(null);
    }
    /*
    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasPdf(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        modelManager.addPdf(ALICE);
        assertTrue(modelManager.hasPdf(ALICE));
    }

    @Test
    public void deletePerson_personIsSelectedAndFirstPersonInFilteredPersonList_selectionCleared() {
        modelManager.addPdf(ALICE);
        modelManager.setSelectedPdf(ALICE);
        modelManager.deletePdf(ALICE);
        assertEquals(null, modelManager.getSelectedPdf());
    }

    @Test
    public void deletePerson_personIsSelectedAndSecondPersonInFilteredPersonList_firstPersonSelected() {
        modelManager.addPdf(ALICE);
        modelManager.addPdf(BOB);
        assertEquals(Arrays.asList(ALICE, BOB), modelManager.getFilteredPdfList());
        modelManager.setSelectedPdf(BOB);
        modelManager.deletePdf(BOB);
        assertEquals(ALICE, modelManager.getSelectedPdf());
    }

    @Test
    public void setPerson_personIsSelected_selectedPersonUpdated() {
        modelManager.addPdf(ALICE);
        modelManager.setSelectedPdf(ALICE);
        Pdf updatedAlice = new PdfBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        modelManager.setPdf(ALICE, updatedAlice);
        assertEquals(updatedAlice, modelManager.getSelectedPdf());
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredPdfList().remove(0);
    }

    @Test
    public void setSelectedPerson_personNotInFilteredPersonList_throwsPersonNotFoundException() {
        thrown.expect(PdfNotFoundException.class);
        modelManager.setSelectedPdf(ALICE);
    }

    @Test
    public void setSelectedPerson_personInFilteredPersonList_setsSelectedPerson() {
        modelManager.addPdf(ALICE);
        assertEquals(Collections.singletonList(ALICE), modelManager.getFilteredPdfList());
        modelManager.setSelectedPdf(ALICE);
        assertEquals(ALICE, modelManager.getSelectedPdf());
    }

    @Test
    public void equals() {
        PdfBook addressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        PdfBook differentAddressBook = new PdfBook();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(addressBook, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(addressBook, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentAddressBook, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredPdfList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(addressBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredPdfList(PREDICATE_SHOW_ALL_PDFS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setPdfBookFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(addressBook, differentUserPrefs)));
    }*/
}
