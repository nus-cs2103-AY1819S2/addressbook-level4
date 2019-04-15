package seedu.pdf.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.pdf.model.Model.PREDICATE_SHOW_ALL_PDFS;
import static seedu.pdf.testutil.TypicalPdfs.SAMPLE_PDF_1;
import static seedu.pdf.testutil.TypicalPdfs.SAMPLE_PDF_2;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.pdf.commons.core.GuiSettings;
import seedu.pdf.model.pdf.TagContainsKeywordsPredicate;
import seedu.pdf.model.pdf.exceptions.PdfNotFoundException;
import seedu.pdf.model.tag.Tag;
import seedu.pdf.testutil.PdfBookBuilder;

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
        userPrefs.setPdfBookFilePath(Paths.get("pdf/book/file/value"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setPdfBookFilePath(Paths.get("new/pdf/book/file/value"));
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
    public void setPdfBookFilePath_validPath_setsPdfBookFilePath() {
        Path path = Paths.get("pdf/book/file/value");
        modelManager.setPdfBookFilePath(path);
        assertEquals(path, modelManager.getPdfBookFilePath());
    }

    @Test
    public void addPdf_nullPdf_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.addPdf(null);
    }

    @Test
    public void addPdf_validPdf_success() {
        modelManager.addPdf(SAMPLE_PDF_1);
        Model sampleModelManager = new ModelManager();
        sampleModelManager.addPdf(SAMPLE_PDF_1);
        assertEquals(modelManager, sampleModelManager);
    }

    @Test
    public void hasPdf_nullPdf_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.hasPdf(null);
    }

    @Test
    public void hasPdf_existingPdf_success() {
        modelManager.addPdf(SAMPLE_PDF_2);
        assertTrue(modelManager.hasPdf(SAMPLE_PDF_2));
    }

    @Test
    public void deletePdf_nullPdf_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.deletePdf(null);
    }

    @Test
    public void deletePdf_deleteExistingPdf_success() {
        modelManager.addPdf(SAMPLE_PDF_1);
        modelManager.deletePdf(SAMPLE_PDF_1);
        assertEquals(modelManager, new ModelManager());
    }

    @Test
    public void setPdf_nullPdf_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.setPdf(SAMPLE_PDF_1, null);

        thrown.expect(NullPointerException.class);
        modelManager.setPdf(null, SAMPLE_PDF_1);
    }

    @Test
    public void setPdf_pdfDoesNotExist_throwsPdfNotFoundException() {
        thrown.expect(PdfNotFoundException.class);
        modelManager.setPdf(SAMPLE_PDF_1, SAMPLE_PDF_2);
    }

    @Test
    public void setPdf_replaceExistingPdf_success() {
        modelManager.addPdf(SAMPLE_PDF_1);
        modelManager.setPdf(SAMPLE_PDF_1, SAMPLE_PDF_2);
        Model sampleModelManager = new ModelManager();
        sampleModelManager.addPdf(SAMPLE_PDF_2);
        assertEquals(modelManager, sampleModelManager);
    }

    @Test
    public void updateFilteredPdfList_filteredByTag_success() {
        Model sampleModelManager = new ModelManager();
        sampleModelManager.addPdf(SAMPLE_PDF_2);

        modelManager.addPdf(SAMPLE_PDF_1);
        modelManager.addPdf(SAMPLE_PDF_2);

        // valid Tag
        modelManager.updateFilteredPdfList(x -> x.getTags().contains(new Tag("CS2103T")));
        assertEquals(modelManager.getFilteredPdfList(), sampleModelManager.getFilteredPdfList());

        // invalid Tag
        modelManager.updateFilteredPdfList(x -> x.getTags().contains(new Tag("SERocks")));
        sampleModelManager.updateFilteredPdfList(x -> x.getTags().contains(new Tag("SERocks")));
        assertEquals(modelManager.getFilteredPdfList(), sampleModelManager.getFilteredPdfList());
    }

    @Test
    public void setSelectedPdf_pdfDoesNotExist_throwsNullPointerException() {
        thrown.expect(PdfNotFoundException.class);
        modelManager.setSelectedPdf(SAMPLE_PDF_1);
    }

    @Test
    public void setSelectedPdf_pdfExist_success() {
        modelManager.addPdf(SAMPLE_PDF_1);
        modelManager.setSelectedPdf(SAMPLE_PDF_1);

        Model sampleModelManager = new ModelManager();
        sampleModelManager.addPdf(SAMPLE_PDF_1);
        sampleModelManager.setSelectedPdf(SAMPLE_PDF_1);
        assertEquals(sampleModelManager.getSelectedPdf(), modelManager.getSelectedPdf());
    }

    @Test
    public void equals() {
        PdfBook pdfBook = new PdfBookBuilder().withPdf(SAMPLE_PDF_1).withPdf(SAMPLE_PDF_2).build();

        PdfBook differentPdfBook = new PdfBook();
        UserPrefs userPrefs = new UserPrefs();

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // different types -> returns false
        assertFalse(modelManager.equals(SAMPLE_PDF_1));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // same values -> returns true
        modelManager = new ModelManager(pdfBook, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(pdfBook, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // different selected pdf -> returns false
        modelManager.setSelectedPdf(SAMPLE_PDF_1);
        assertFalse(modelManager.equals(modelManagerCopy));

        // same filtered pdf list -> returns true
        modelManager.updateFilteredPdfList(x -> x.getTags().contains(new Tag("CS2103T")));
        modelManagerCopy.updateFilteredPdfList(x -> x.getTags().contains(new Tag("CS2103T")));
        assertTrue(modelManager.equals(modelManagerCopy));

        // same selected pdf -> returns true
        modelManager.setSelectedPdf(SAMPLE_PDF_2);
        modelManagerCopy.setSelectedPdf(SAMPLE_PDF_2);
        assertTrue(modelManager.equals(modelManagerCopy));

        // different pdfBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentPdfBook, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = {"CS2103T", "w9"};
        modelManager.updateFilteredPdfList(new TagContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(pdfBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredPdfList(PREDICATE_SHOW_ALL_PDFS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setPdfBookFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(pdfBook, differentUserPrefs)));
    }
}
