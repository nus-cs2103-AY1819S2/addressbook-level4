package seedu.address.storage.csvmanager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.CardFolder;
import seedu.address.model.ReadOnlyCardFolder;
import seedu.address.model.card.Answer;
import seedu.address.model.card.Card;
import seedu.address.model.card.Option;
import seedu.address.model.card.Question;
import seedu.address.model.card.Score;
import seedu.address.model.hint.Hint;


/**
 * Manages the importing and exporting of flashcards into model
 */
public class CsvManager implements CsvCommands {

    private static final String COMMA_DELIMITTER = ",";
    private static final String NEW_LINE_SEPARATOR = "\n";
    private static final String CARD_HEADERS = "Question,Answer,Options,Hints";
    private static final String DEFAULT_TEST_PATH = "/src/test/data/CsvCardFolderTest";
    private static final String DEFAULT_FILE_PATH = "./";
    private static final String TEST_FOLDER_PATH = "test";
    private String defaultPath;
    private boolean setTestDefaultPath = false;



    public CsvManager() throws IOException {
        defaultPath = getDefaultFilePath();
    }

    @Override
    public CardFolder readFoldersToCsv(CsvFile csvFile) throws IOException, CommandException {
        if (!fileExists(csvFile)) {
            throw new FileNotFoundException();
        }
        String filePath = defaultPath + "/" + csvFile.filename;
        String filename = csvFile.filename;
        String folderName = filename.split("\\.")[0];
        CardFolder cardFolder = new CardFolder(folderName);
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
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

    /**
     * helper method that build a card object from each line of the csv file imported
     */
    private Card buildCard(String[] cardValues) {
        // cardValues = {"question", "answer", " .. ", " ...", "hint"}
        Question question = new Question(cardValues[0]);
        Answer answer = new Answer(cardValues[1]);
        Set<Option> optionSet = buildOptions(cardValues);
        Set<Hint> hintSet = buildHint(cardValues);
        Card card = new Card(question, answer, new Score(0, 0), optionSet, hintSet);
        return card;
    }

    /**
     * reconstructs a set of options from each line of the csv file to import
     */
    private Set<Option> buildOptions(String[] card) {
        Set<Option> optionSet = new HashSet<>();
        String[] options = Arrays.copyOfRange(card, 2, card.length - 1);
        if (options[0].equals(" ")) {
            return optionSet;
        }
        Arrays.stream(options).map(Option::new).forEach(option -> optionSet.add(option));
        return optionSet;
    }

    /**
     * reconstructs a set of hints from each line of the csv file to import
     */
    private Set<Hint> buildHint(String[] card) {
        Set<Hint> hintSet = new HashSet<>();
        String hint = card[card.length - 1];
        if (hint.equals(" ")) {
            return hintSet;
        }
        hintSet.add(new Hint(hint));
        return hintSet;
    }


    /**
     * checks whether the headers of the imported file conforms to the specifications of the Card headers
     */
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
        for (ReadOnlyCardFolder readOnlyCardFolder : cardFolders) {
            List<Card> cardList = readOnlyCardFolder.getCardList();
            String folderName = getFolderName(readOnlyCardFolder);
            FileWriter fileWriter = new FileWriter(defaultPath + "/" + folderName + ".csv");
            fileWriter.append(CARD_HEADERS + NEW_LINE_SEPARATOR);
            for (Card card : cardList) {
                String cardRepresentation = getCardString(card);
                fileWriter.append(cardRepresentation);
                fileWriter.append(NEW_LINE_SEPARATOR);
            }
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

    public boolean fileExists(CsvFile csvFile) throws IOException {
        return new File(defaultPath + "/" + csvFile.filename).exists();
    }

    public static String getDefaultFilePath() throws IOException {
        return new File(DEFAULT_FILE_PATH).getCanonicalPath();
    }

    public String getDefaultPath() {
        return defaultPath;
    }

    public void setTestDefaultPath() {
        defaultPath = defaultPath + DEFAULT_TEST_PATH;
        setTestDefaultPath = true;
    }

    private String getCardString(Card card) {
        StringBuilder stringBuilder = new StringBuilder();
        parseQuestion(card.getQuestion(), stringBuilder);
        parseAnswer(card.getAnswer(), stringBuilder);
        parseOptions(card.getOptions(), stringBuilder);
        parseHints(card.getHints(), stringBuilder);
        return stringBuilder.toString();
    }

    private void parseQuestion(Question question, StringBuilder stringBuilder) {
        stringBuilder.append(question + COMMA_DELIMITTER);
    }

    private void parseAnswer(Answer answer, StringBuilder stringBuilder) {
        stringBuilder.append(answer + COMMA_DELIMITTER);
    }

    /**
     * helper method that parses each {@code Set<Option>} of the card attribute into a string
     */
    private void parseOptions(Set<Option> options, StringBuilder stringBuilder) {
        if (options.isEmpty()) {
            stringBuilder.append(" " + COMMA_DELIMITTER);
        } else {
            options.forEach(option -> stringBuilder.append(option.optionValue)
                    .append(COMMA_DELIMITTER));
        }
    }

    /**
     * helper method that parses each {@code Set<Hint>} of the card attribute into a string
     */
    private void parseHints(Set<Hint> hintSet, StringBuilder stringBuilder) {
        if (hintSet.isEmpty()) {
            stringBuilder.append(" " + COMMA_DELIMITTER);
        } else {
            hintSet.forEach(hint -> stringBuilder.append(hint.hintName)
                    .append(COMMA_DELIMITTER));
        }
    }
}
