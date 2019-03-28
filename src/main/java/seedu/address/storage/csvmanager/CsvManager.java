package seedu.address.storage.csvmanager;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.CardFolder;
import seedu.address.model.ReadOnlyCardFolder;
import seedu.address.model.card.Answer;
import seedu.address.model.card.Card;
import seedu.address.model.card.Question;
import seedu.address.model.card.Score;
import seedu.address.model.hint.Hint;


/**
 * Manages the importing and exporting of flashcards into model
 */
public class CsvManager implements CsvCommands {

    private static final String COMMA_DELIMITTER = ",";
    private static final String NEW_LINE_SEPARATOR = "\n";
    private static final String CARD_HEADERS = "Question,Answer,Hints";
    private BufferedReader bufferedReader;
    private String defaultPath;
    private boolean setTestDefaultPath = false;
    public static final String DEFAULT_TEST_PATH = "./test/data/CsvCardFolderTest";
    private static final String DEFAULT_FILE_PATH = "./";
    private static final String TEST_FOLDER_PATH = "test";


    public CsvManager() throws IOException {
        defaultPath = getDefaultFilePath();
    }

    @Override
    public CardFolder readFoldersToCsv(CsvFile csvFile) throws IOException, CommandException {
        if (!fileExists(csvFile)) {
            throw new FileNotFoundException();
        }
        String filePath = getDefaultFilePath() + "/" + csvFile.filename;
        String filename = csvFile.filename;
        String folderName = filename.split("\\.")[0];
        CardFolder cardFolder = new CardFolder(folderName);
        bufferedReader = new BufferedReader(new FileReader(filePath));
        String line;
        String header = bufferedReader.readLine();

        if (!checkCorrectHeaders(header)) {
            throw new CommandException("hekki");
        }

        while ((line = bufferedReader.readLine()) != null) {

            // use comma as separator
            String[] stringCard = line.split(COMMA_DELIMITTER);

            Card card = buildCard(stringCard);
            cardFolder.addCard(card);
        }
        if (bufferedReader != null) {
            bufferedReader.close();
        }
        return cardFolder;
    }


    private Card buildCard(String[] cardValues) {
        Question question = new Question(cardValues[0]);
        Answer answer = new Answer(cardValues[1]);
        String[] hintArray = Arrays.copyOfRange(cardValues, 2, cardValues.length);
        Set<Hint> hintSet = Arrays.stream(hintArray).map(Hint::new).collect(Collectors.toSet());
        Card card = new Card(question, answer, new Score(0, 0), hintSet);
        return card;
    }



    private boolean checkCorrectHeaders(String header) {
        String[] cardHeaders = CARD_HEADERS.split(",");
        String[] fileHeaders = header.split(",");

        if (cardHeaders.length != fileHeaders.length) {
            return false;
        }

        for (int i = 0; i < cardHeaders.length; i++) {
            if (!cardHeaders[i].toLowerCase().equals(fileHeaders[i].toLowerCase())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void writeFoldersToCsv(List<ReadOnlyCardFolder> cardFolders) throws IOException {
        String filepath = defaultPath;
        for (ReadOnlyCardFolder readOnlyCardFolder : cardFolders) {
            List<Card> cardList = readOnlyCardFolder.getCardList();
            String folderName = getFolderName(readOnlyCardFolder);
            FileWriter fileWriter = new FileWriter(filepath + "/" + folderName + ".csv");
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

    private String getFolderName(ReadOnlyCardFolder folder) {
        String folderName = folder.getFolderName();
        if (setTestDefaultPath) {
            return folder + " " + TEST_FOLDER_PATH;
        }
        return folderName;
    }

    public static boolean fileExists(CsvFile csvFile) throws IOException {
        return new File(getDefaultFilePath() + "/" + csvFile.filename).isFile();
    }

    public static String getDefaultFilePath() throws IOException {
        return new File(DEFAULT_FILE_PATH).getCanonicalPath();
    }

    public void setTestDefaultPath() throws IOException {
        defaultPath = new File(DEFAULT_TEST_PATH).getCanonicalPath();
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
