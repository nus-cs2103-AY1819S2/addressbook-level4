package seedu.address.storage.csvmanager;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import seedu.address.model.ReadOnlyCardFolder;
import seedu.address.model.card.Card;


/**
 * Manages the importing and exporting of flashcards into model
 */
public class CsvManager implements CsvCommands {

    private static final String COMMA_DELIMITTER = ",";
    private static final String NEW_LINE_SEPARATOR = "\n";
    private static final String CARD_HEADERS = "Question,Answer,Hints";
    private String defaultPath;
    private boolean setTestDefaultPath = false;


    public CsvManager() throws IOException {
        defaultPath = getDefaultFilePath();
    }

    @Override
    public void readFoldersToCsv(CsvFile csvFile) throws IOException {


    }

    @Override
    public void writeFoldersToCsv(List<ReadOnlyCardFolder> cardFolders) throws IOException {
        String filepath = defaultPath;
        for (ReadOnlyCardFolder readOnlyCardFolder : cardFolders) {
            List<Card> cardList = readOnlyCardFolder.getCardList();
            String foldername;
            if (setTestDefaultPath) {
                foldername = readOnlyCardFolder.getFolderName() + " test";
            } else {
                foldername = readOnlyCardFolder.getFolderName();
            }
            FileWriter fileWriter = new FileWriter(filepath + "/" + foldername + ".csv");
            fileWriter.append(CARD_HEADERS + NEW_LINE_SEPARATOR);
            for (Card card : cardList) {
                String cardRepresentation = getCardString(card);
                fileWriter.append(cardRepresentation);
                fileWriter.append(NEW_LINE_SEPARATOR);
            }
            fileWriter.append(NEW_LINE_SEPARATOR);
            fileWriter.flush();
            fileWriter.close();
        }
    }

    public static String getFilePathAsString(CsvFile csvFile) throws IOException {
        return new File("./" + csvFile.filename).getCanonicalPath();
    }

    public static String getDefaultFilePath() throws IOException {
        return new File("./").getCanonicalPath();
    }

    public void setTestDefaultPath() throws IOException {
        defaultPath = new File("./test/data/").getCanonicalPath();
        setTestDefaultPath = true;
    }

    private String getCardString(Card card) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(card.getQuestion() + COMMA_DELIMITTER)
                .append(card.getAnswer() + COMMA_DELIMITTER);
        card.getHints().forEach(hint -> stringBuilder.append(hint.hintName)
                .append(COMMA_DELIMITTER));
        return stringBuilder.toString();
    }
}
