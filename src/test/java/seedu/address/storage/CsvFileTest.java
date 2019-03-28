package seedu.address.storage;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import seedu.address.logic.CommandHistory;
import seedu.address.model.*;
import seedu.address.model.card.Card;
import seedu.address.storage.csvmanager.CsvFile;
import seedu.address.storage.csvmanager.Exceptions.CsvManagerNotInitialized;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static seedu.address.storage.csvmanager.CsvManager.DEFAULT_TEST_PATH;
import static seedu.address.testutil.TypicalCards.getTypicalCardFolders;

public class CsvFileTest {

    private Model model = new ModelManager(new ArrayList<ReadOnlyCardFolder>(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalCardFolders(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();
    private final String TYPICAL_CARD_FOLDER = "Typical Cards.csv";
    private final String TYPICAL_CARD_FOLDER_TEST = "Typical Cards test.csv";

    public CsvFileTest() throws IOException {
        model.setTestCsvPath();
        expectedModel.setTestCsvPath();
    }


    @Test
    public void execute_importCsvFile_correctFormat() throws Exception {
        //Model newModel = new ModelManager(getTypicalCardFolders(), new UserPrefs());
        model.importCardFolders(new CsvFile(TYPICAL_CARD_FOLDER));

        assert checkSameCardFolders(model, expectedModel);
    }

    private boolean checkSameCardFolders(Model model, Model expectedModel) {
        List<ReadOnlyCardFolder> cardFolderModel = model.getCardFolders();
        List<ReadOnlyCardFolder> cardFolderExpectedModel = expectedModel.getCardFolders();

        assert(cardFolderExpectedModel.size() == cardFolderModel.size());
        assert (isSameCardFolderList(cardFolderModel, cardFolderExpectedModel));
        return true;
    }

    private boolean isSameCardFolderList(List<ReadOnlyCardFolder> cardFolders,
                                         List<ReadOnlyCardFolder> expectedCardFolders) {
        for (int i = 0; i < cardFolders.size(); i++) {
            if (!sameCardsInFolder(cardFolders.get(i), expectedCardFolders.get(i))) {
                return false;
            }
        }
        return true;
    }


    private boolean sameCardsInFolder(ReadOnlyCardFolder folder, ReadOnlyCardFolder expected) {
        List<Card> folderCardList = folder.getCardList();
        List<Card> expectedCardList = expected.getCardList();
        assert (folderCardList.size() == expectedCardList.size());
        int size = folderCardList.size();
        for (int index = 0; index < size; index++) {
            if (!isSameCard(folderCardList.get(index), expectedCardList.get(index)))
                return false;
        }
        return true;
    }

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
        String defaultPath = expectedModel.getDefaultPath();
        String testFilePath = expectedModel.getDefaultPath() + "/" + TYPICAL_CARD_FOLDER_TEST;
        File testFile = new File(testFilePath);
        assert(testFile.exists());
        byte[] f1 = Files.readAllBytes(Paths.get(testFile.toString()));
        byte[] f2 = Files.readAllBytes(Paths.get(new File(defaultPath + "/" + TYPICAL_CARD_FOLDER)
                .toString()));
        assert (Arrays.equals(f1, f2));
    }
}
