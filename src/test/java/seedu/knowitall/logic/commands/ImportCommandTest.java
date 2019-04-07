package seedu.knowitall.logic.commands;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.knowitall.testutil.TypicalCards.getTypicalCardFolders;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

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
import seedu.knowitall.storage.csvmanager.exceptions.CsvManagerNotInitialized;



public class ImportCommandTest {

    private static final String TYPICAL_CARD_FOLDER = "Typical Cards.csv";
    private static final String TYPICAL_CARD_FOLDER_TEST = "Typical Cards test.csv";
    private static final String INVALID_FILE_NAME = "Fake Cards.csv";
    private static final String BLOOD_CARD_FOLDER = "Blood.csv";
    private static final String BLOOD_CARD_FOLDER_TEST = "Blood test.csv";

    @Rule
    public ExpectedException thrown = ExpectedException.none();


    private Model model = new ModelManager(new ArrayList<ReadOnlyCardFolder>(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalCardFolders(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();


    private File typicalCardsFile;
    private File typicalCardsFileTest;
    private File bloodCardsFile;
    private File bloodCardsTestFile;



    @Before
    public void setUp() throws Exception {

        // set test paths for model
        model.setTestCsvPath();
        expectedModel.setTestCsvPath();

        // initialize respective files
        String testDefaultPath = model.getDefaultPath();
        typicalCardsFile = new File(testDefaultPath + "/" + TYPICAL_CARD_FOLDER);
        typicalCardsFileTest = new File(testDefaultPath + "/" + TYPICAL_CARD_FOLDER_TEST);
        bloodCardsFile = new File(testDefaultPath + "/" + BLOOD_CARD_FOLDER);
        bloodCardsTestFile = new File(testDefaultPath + "/" + BLOOD_CARD_FOLDER_TEST);


        assert typicalCardsFile.exists();
        assert bloodCardsFile.exists();
    }

    @Test
    public void execute_importTypicalCards_success() throws Exception {
        ImportCommand importCommand = new ImportCommand(new CsvFile(TYPICAL_CARD_FOLDER));

        Model expectedModel = new ModelManager(getTypicalCardFolders(), new UserPrefs());
        CommandResult commandResult = importCommand.execute(model, commandHistory);
        String expectedMessage = String.format(ImportCommand.MESSAGE_SUCCESS, TYPICAL_CARD_FOLDER);
        assertEquals(expectedMessage, commandResult.getFeedbackToUser());
        assertTrue(isSameCardFolders(model, expectedModel));
    }

    @Test
    public void execute_importNonExistentCards_failure() throws Exception {
        ImportCommand importCommand = new ImportCommand(new CsvFile(INVALID_FILE_NAME));
        thrown.expect(CommandException.class);
        thrown.expectMessage(ImportCommand.MESSAGE_FILE_OPS_FAILURE);
        importCommand.execute(model, commandHistory);
    }


    @Test
    public void execute_importCsvFile_correctFormat() throws Exception {
        //Model newModel = new ModelManager(getTypicalCardFolders(), new UserPrefs());
        model.importCardFolders(new CsvFile(TYPICAL_CARD_FOLDER));

        assertTrue (isSameCardFolders(model, expectedModel));
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
     * this helper method checks whether the two models have the same card folders
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
     * this helper method checks whether the two card folders have the same cards
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
     * this helper method checks that the two cards are the same. Two cards are the same if they have the same question,
     * answer, options and hint
     */
    private boolean isSameCard(Card card, Card expected) {
        assertTrue (card.getHints().equals(expected.getHints()));
        assertTrue (card.getOptions().equals(expected.getOptions()));
        assertTrue (card.getQuestion().equals(expected.getQuestion()));
        assertTrue (card.getAnswer().equals(expected.getAnswer()));
        return true;
    }

    @Test
    public void execute_exportCsvFile_correctFile(File actual, File expected) throws IOException, CsvManagerNotInitialized {
        expectedModel.exportCardFolders(new ArrayList<>(Arrays.asList(1)));
        // System.out.println(DEFAULT_TEST_PATH);
        assert(typicalCardsFileTest.exists());
        byte[] f1 = Files.readAllBytes(Paths.get(typicalCardsFile.toString()));
        byte[] f2 = Files.readAllBytes(Paths.get(typicalCardsFileTest.toString()));

        assertArrayEquals (f1, f2);
        typicalCardsFileTest.delete();
    }

    @Test
    public void execute_importDuplicateCsvFile_exception() throws Exception {
        ImportCommand importCommand = new ImportCommand(new CsvFile(TYPICAL_CARD_FOLDER));

        thrown.expect(CommandException.class);
        thrown.expectMessage(ImportCommand.MESSAGE_DUPLICATE_CARD_FOLDERS);
        importCommand.execute(expectedModel, commandHistory);
    }
}
