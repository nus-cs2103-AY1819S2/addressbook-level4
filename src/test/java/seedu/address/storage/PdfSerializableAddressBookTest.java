package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.testutil.TypicalPersons;

public class PdfSerializableAddressBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "PdfSerializableAddressBookTest");
    private static final Path TYPICAL_PERSONS_PDF_TEST = TEST_DATA_FOLDER.resolve("typicalPersonsAddressBookTest.pdf");
    private static final Path TYPICAL_PERSONS_PDF_SAVE = TEST_DATA_FOLDER.resolve("typicalPersonsAddressBookSaved.pdf");
    private static final Path INVALID_PERSON_PDF = TEST_DATA_FOLDER.resolve("invalidPersonAddressBookTest.pdf");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void saveAsPdf_typicalPersonsFileTime_failure() throws Exception {
        InOutAddressBookStorage inOutAddressBookStorage = new InOutAddressBookStorage(TEST_DATA_FOLDER);
        ReadOnlyAddressBook addressBook = TypicalPersons.getTypicalAddressBook();
        inOutAddressBookStorage.saveAsPdf(addressBook, TYPICAL_PERSONS_PDF_SAVE);

        // Loading an existing document
        File testFile = TYPICAL_PERSONS_PDF_TEST.toFile();
        PDDocument testDocument = PDDocument.load(testFile);
        File savedFile = TYPICAL_PERSONS_PDF_SAVE.toFile();
        PDDocument savedDocument = PDDocument.load(savedFile);

        // Instantiate PDFTextStripper class
        PDFTextStripper pdfStripper = new PDFTextStripper();

        // Retrieving text from PDF document
        String testText = pdfStripper.getText(testDocument);
        String savedText = pdfStripper.getText(savedDocument);

        // Closing the document
        testDocument.close();
        savedDocument.close();

        assertNotEquals(testText, savedText);
    }

    @Test
    public void saveAsPdf_typicalPersonsFile_success() throws Exception {
        InOutAddressBookStorage inOutAddressBookStorage = new InOutAddressBookStorage(TEST_DATA_FOLDER);
        ReadOnlyAddressBook addressBook = TypicalPersons.getTypicalAddressBook();
        inOutAddressBookStorage.saveAsPdf(addressBook, TYPICAL_PERSONS_PDF_SAVE);

        // Loading an existing document
        File testFile = TYPICAL_PERSONS_PDF_TEST.toFile();
        PDDocument testDocument = PDDocument.load(testFile);
        File savedFile = TYPICAL_PERSONS_PDF_SAVE.toFile();
        PDDocument savedDocument = PDDocument.load(savedFile);

        // Instantiate PDFTextStripper class
        PDFTextStripper pdfStripper = new PDFTextStripper();

        // Retrieving text from PDF document
        String testText = pdfStripper.getText(testDocument);
        String savedText = pdfStripper.getText(savedDocument);

        // Closing the document
        testDocument.close();
        savedDocument.close();

        // As saveAsPdf saves the date and time, it needs to be ignored for file compare
        String ignoreDateTimeRegex = "^(TeethHub Date saved/exported:)"
                                        + "|([0-9]{2,2}/[0-9]{2,2}/[0-9]{4,4})[\\r\\n]+([^\\r\\n]+)"
                                        + "|([0-9]{2,2}:[0-9]{2,2}:[0-9]{2,2})$";

        String[] splitTest = testText.split(ignoreDateTimeRegex);
        String[] splitSaved = savedText.split(ignoreDateTimeRegex);


        assertEquals(splitTest.length, splitSaved.length);

        for (int i = 0; i < splitTest.length; i++) {
            assertEquals(splitTest[i], splitSaved[i]);
        }
    }

    @Test
    public void saveAsPdf_invalidTypicalPersonsFile_failure() throws Exception {
        InOutAddressBookStorage inOutAddressBookStorage = new InOutAddressBookStorage(TEST_DATA_FOLDER);
        ReadOnlyAddressBook addressBook = TypicalPersons.getTypicalAddressBook();
        inOutAddressBookStorage.saveAsPdf(addressBook, TYPICAL_PERSONS_PDF_SAVE);

        // Loading an existing document
        File testFile = INVALID_PERSON_PDF.toFile();
        PDDocument testDocument = PDDocument.load(testFile);
        File savedFile = TYPICAL_PERSONS_PDF_SAVE.toFile();
        PDDocument savedDocument = PDDocument.load(savedFile);

        // Instantiate PDFTextStripper class
        PDFTextStripper pdfStripper = new PDFTextStripper();

        // Retrieving text from PDF document
        String testText = pdfStripper.getText(testDocument);
        String savedText = pdfStripper.getText(savedDocument);

        // Closing the document
        testDocument.close();
        savedDocument.close();

        // As saveAsPdf saves the date and time, it needs to be ignored for file compare
        String ignoreDateTimeRegex = "^(TeethHub Date saved/exported:)"
            + "|([0-9]{2,2}/[0-9]{2,2}/[0-9]{4,4})[\\r\\n]+([^\\r\\n]+)"
            + "|([0-9]{2,2}:[0-9]{2,2}:[0-9]{2,2})$";

        String[] splitTest = testText.split(ignoreDateTimeRegex);
        String[] splitSaved = savedText.split(ignoreDateTimeRegex);

        StringBuilder testSb = new StringBuilder();
        StringBuilder saveSb = new StringBuilder();

        for (int i = 0; i < splitTest.length && i < splitSaved.length; i++) {
            testSb.append(splitTest[i]);
            saveSb.append(splitSaved[i]);
        }
        String newTest = testSb.toString();
        String newSaved = saveSb.toString();

        assertNotEquals(newTest, newSaved);
    }
}
