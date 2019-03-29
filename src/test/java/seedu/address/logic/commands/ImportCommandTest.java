package seedu.address.logic.commands;

import static seedu.address.testutil.TypicalCards.getTypicalCardFolders;

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

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyCardFolder;
import seedu.address.model.UserPrefs;
import seedu.address.model.card.Card;
import seedu.address.storage.csvmanager.CsvFile;
import seedu.address.storage.csvmanager.exceptions.CsvManagerNotInitialized;



public class ImportCommandTest {

    private static final String TYPICAL_CARD_FOLDER = "Typical Cards.csv";
    private static final String TYPICAL_CARD_FOLDER_TEST = "Typical Cards test.csv";
    private static final String INVALID_FILE_NAME = "Fake Cards.csv";

    @Rule
    public ExpectedException thrown = ExpectedException.none();


    private Model model = new ModelManager(new ArrayList<ReadOnlyCardFolder>(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalCardFolders(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();
    private File typicalCardsFile;
    private File typicalCardsFileTest;



    public ImportCommandTest() throws IOException {
        model.setTestCsvPath();
        expectedModel.setTestCsvPath();
    }

    @Before
    public void setUp() {
        typicalCardsFile = new File(model.getDefaultPath() + "/" + TYPICAL_CARD_FOLDER);
        assert typicalCardsFile.exists();
    }

    @Test
    public void execute_importTypicalCards_success() throws Exception {
        ImportCommand importCommand = new ImportCommand(new CsvFile(TYPICAL_CARD_FOLDER));

        Model expectedModel = new ModelManager(getTypicalCardFolders(), new UserPrefs());
        CommandResult commandResult = importCommand.execute(model, commandHistory);
        String expectedMessage = String.format(ImportCommand.MESSAGE_SUCCESS, TYPICAL_CARD_FOLDER);
        assert (commandResult.getFeedbackToUser().equals(expectedMessage));
        assert isSameCardFolders(model, expectedModel);
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

        assert isSameCardFolders(model, expectedModel);
    }

    /**
     * this method checks whether two models are equal. i.e have the same card folders and cards
     */
    private boolean isSameCardFolders(Model model, Model expectedModel) {
        List<ReadOnlyCardFolder> cardFolderModel = model.getCardFolders();
        List<ReadOnlyCardFolder> cardFolderExpectedModel = expectedModel.getCardFolders();

        assert(cardFolderExpectedModel.size() == cardFolderModel.size());
        assert (isSameCardFolderList(cardFolderModel, cardFolderExpectedModel));
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
        assert (folderCardList.size() == expectedCardList.size());
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
        assert (card.getHints().equals(expected.getHints()));
        assert (card.getOptions().equals(expected.getOptions()));
        assert (card.getQuestion().equals(expected.getQuestion()));
        assert (card.getAnswer().equals(expected.getAnswer()));
        return true;
    }

    @Test
    public void execute_exportCsvFile_correctFile() throws IOException, CsvManagerNotInitialized {
        expectedModel.exportCardFolders(new ArrayList<>(Arrays.asList(1)));
        // System.out.println(DEFAULT_TEST_PATH);
        typicalCardsFileTest = new File(model.getDefaultPath() + "/" + TYPICAL_CARD_FOLDER_TEST);
        assert(typicalCardsFileTest.exists());
        byte[] f1 = Files.readAllBytes(Paths.get(typicalCardsFile.toString()));
        byte[] f2 = Files.readAllBytes(Paths.get(new File(typicalCardsFileTest.toString())
                .toString()));
        assert (Arrays.equals(f1, f2));
        typicalCardsFileTest.delete();
    }
}
