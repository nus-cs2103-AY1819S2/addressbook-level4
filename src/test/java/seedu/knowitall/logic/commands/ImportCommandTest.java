package seedu.knowitall.logic.commands;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.knowitall.testutil.SampleBloodCards.getBloodFolder;
import static seedu.knowitall.testutil.TypicalCards.getTypicalFolderOne;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.knowitall.logic.CommandHistory;
import seedu.knowitall.logic.commands.exceptions.CommandException;
import seedu.knowitall.model.Model;
import seedu.knowitall.model.ModelManager;
import seedu.knowitall.model.ReadOnlyCardFolder;
import seedu.knowitall.model.UserPrefs;
import seedu.knowitall.model.card.Card;
import seedu.knowitall.storage.csvmanager.CsvFile;
import seedu.knowitall.storage.csvmanager.CsvUtils;
import seedu.knowitall.storage.csvmanager.exceptions.CsvManagerNotInitialized;



public class ImportCommandTest {

    private static final String TYPICAL_CARD_FOLDER = "Typical Folder 1.csv";
    private static final String TYPICAL_CARD_FOLDER_TEST = "Typical Folder 1 test.csv";
    private static final String INVALID_FILE_NAME = "Fake Cards.csv";
    private static final String BLOOD_CARD_FOLDER = "Blood.csv";
    private static final String BLOOD_CARD_FOLDER_TEST = "Blood test.csv";

    @Rule
    public ExpectedException thrown = ExpectedException.none();


    private Model model = new ModelManager(new ArrayList<ReadOnlyCardFolder>(), new UserPrefs());
    private Model expectedModel = new ModelManager(new ArrayList<ReadOnlyCardFolder>(Arrays.asList(
            getTypicalFolderOne(), getBloodFolder())) , new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();


    private File typicalCardsFile;
    private File typicalCardsFileTest;
    private File bloodCardsFile;
    private File bloodCardsTestFile;



    @Before
    public void setUp() {

        // set test paths for model
        model.setTestCsvPath(CsvUtils.DEFAULT_IMPORT_TEST_PATH);
        expectedModel.setTestCsvPath(CsvUtils.DEFAULT_IMPORT_TEST_PATH);

        // initialize respective files
        String testDefaultPath = model.getDefaultPath();
        typicalCardsFile = new File(Paths.get(testDefaultPath, TYPICAL_CARD_FOLDER).toString());
        typicalCardsFileTest = new File(Paths.get(testDefaultPath, TYPICAL_CARD_FOLDER_TEST).toString());
        bloodCardsFile = new File(Paths.get(testDefaultPath, BLOOD_CARD_FOLDER).toString());
        bloodCardsTestFile = new File(Paths.get(testDefaultPath, BLOOD_CARD_FOLDER_TEST).toString());


        assert typicalCardsFile.exists();
        assert bloodCardsFile.exists();
    }

    @Test
    public void execute_importTypicalCards_success() throws Exception {
        importCardFolderSuccess(TYPICAL_CARD_FOLDER);
        importCardFolderSuccess(BLOOD_CARD_FOLDER);
        assertTrue(isSameCardFolders(model, expectedModel));
    }

    /**
     * Test import command success message and add flashcards to model
     */
    private void importCardFolderSuccess(String foldername)
            throws CommandException {
        ImportCommand importCommand = new ImportCommand(new CsvFile(foldername));


        CommandResult commandResult = importCommand.execute(model, commandHistory);
        String expectedMessage = String.format(ImportCommand.MESSAGE_SUCCESS, foldername);
        assertEquals(expectedMessage, commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_importNonExistentCards_failure() throws Exception {
        ImportCommand importCommand = new ImportCommand(new CsvFile(INVALID_FILE_NAME));
        thrown.expect(CommandException.class);
        thrown.expectMessage(ImportCommand.MESSAGE_FILE_OPS_FAILURE);
        importCommand.execute(model, commandHistory);
    }



    /**
     * this method checks whether two models are equal. i.e have the same card folders and cards
     */
    private boolean isSameCardFolders(Model model, Model expectedModel) {
        List<ReadOnlyCardFolder> cardFolderModel = model.getCardFolders();
        List<ReadOnlyCardFolder> cardFolderExpectedModel = expectedModel.getCardFolders();

        assertTrue(cardFolderExpectedModel.size() == cardFolderModel.size());
        assertTrue (isSameCardFolderList(cardFolderModel, cardFolderExpectedModel));
        return true;
    }

    /**
     * Checks whether the two models have the same card folders
     */
    private boolean isSameCardFolderList(List<ReadOnlyCardFolder> cardFolders,
                                         List<ReadOnlyCardFolder> expectedCardFolders) {
        for (int i = 0; i < cardFolders.size(); i++) {
            if (!sameCardsInFolder(cardFolders.get(i), expectedCardFolders.get(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks whether the two card folders have the same cards
     */
    private boolean sameCardsInFolder(ReadOnlyCardFolder folder, ReadOnlyCardFolder expected) {
        List<Card> folderCardList = folder.getCardList();
        List<Card> expectedCardList = expected.getCardList();
        assertTrue (folderCardList.size() == expectedCardList.size());
        int size = folderCardList.size();
        for (int index = 0; index < size; index++) {
            if (!isSameCard(folderCardList.get(index), expectedCardList.get(index))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks that the two cards are the same. Two cards are the same if they have the same question,
     * answer, options and hint
     */
    private boolean isSameCard(Card card, Card expected) {
        System.out.println("actual: ");
        card.getOptions().forEach(x -> System.out.print(x.optionValue + ", "));

        System.out.println("\nexpected: ");
        expected.getOptions().forEach(x -> System.out.print(x.optionValue + ", "));

        assertTrue (card.getHints().equals(expected.getHints()));
        assertTrue (card.getOptions().equals(expected.getOptions()));
        assertTrue (card.getQuestion().equals(expected.getQuestion()));
        assertTrue (card.getAnswer().equals(expected.getAnswer()));
        return true;
    }



    @Test
    public void execute_exportCsvFile_correctFile() throws IOException, CsvManagerNotInitialized {
        expectedModel.exportCardFolders(new ArrayList<>(Arrays.asList(1, 2)));
        isSameFileContent(typicalCardsFile, typicalCardsFileTest);
        isSameFileContent(bloodCardsFile, bloodCardsTestFile);
    }

    /**
     * Checks if two file contents are equal. Removes line endings for Unix files before comparison
     */
    private void isSameFileContent(File actualFile, File testFile) throws IOException, CsvManagerNotInitialized {
        // System.out.println(DEFAULT_IMPORT_TEST_PATH);
        assert(actualFile.exists());
        byte[] rawByteF1 = Files.readAllBytes(Paths.get(actualFile.toString()));
        byte[] rawByteF2 = Files.readAllBytes(Paths.get(testFile.toString()));

        // remove cr from file if any
        byte[] processedF1 = removeCarriageReturn(rawByteF1);
        byte[] processedF2 = removeCarriageReturn(rawByteF2);
        assertArrayEquals (processedF1, processedF2);
        assert (testFile.delete());
    }

    /**
     * Remove unix line ending from imported file
     */
    private byte[] removeCarriageReturn(byte[] file) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        for (Byte b : file) {
            if (b != 13) {
                byteArrayOutputStream.write(b);
            }
        }
        return byteArrayOutputStream.toByteArray();
    }

    @Test
    public void execute_importDuplicateCsvFile_exception() throws Exception {
        ImportCommand importCommand = new ImportCommand(new CsvFile(TYPICAL_CARD_FOLDER));

        thrown.expect(CommandException.class);
        thrown.expectMessage(ImportCommand.MESSAGE_DUPLICATE_CARD_FOLDERS);
        importCommand.execute(expectedModel, commandHistory);
    }
}
