package seedu.address.storage.csvmanager;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.CardFolder;
import seedu.address.model.ReadOnlyCardFolder;
import seedu.address.model.card.*;
import seedu.address.model.hint.Hint;


/**
 * Manages the importing and exporting of flashcards into model
 */
public class CsvManager implements CsvCommands {

    private static final String COMMA_DELIMITTER = ",";
    private static final String NEW_LINE_SEPARATOR = "\n";
    private static final String CARD_HEADERS = "Question,Answer,Options,Hints";
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


    private Card buildCard(String[] cardValues) {
        // cardValues = {"question", "answer", " .. ", " ...", "hint"}
        Question question = new Question(cardValues[0]);
        Answer answer = new Answer(cardValues[1]);
        String hint = cardValues[cardValues.length - 1];
        Set<Option> option = new HashSet<>();
        option.add(new Option(cardValues[0]));
        Set<Hint> hintSet = new HashSet<>();
        Card card = new Card(question, answer, new Score(0, 0), option, hintSet);
        return card;
    }

    private Set<Option> buildOptions(String[] card) {
        Set<Option> optionSet = new HashSet<>();
        String[] options = Arrays.copyOfRange(card, 2, card.length - 1);
        if (options[0] == " ") {
            return optionSet;
        }
        Arrays.stream(options).map(Option::new).forEach(option -> optionSet.add(option));
        return optionSet;
    }

    private Set<Hint> buildHint(String[] card) {
        Set<Hint> hintSet = new HashSet<>();
        String hint = card[card.length - 1];
        if (hint == " ") {
            return hintSet;
        }
        hintSet.add(new Hint(hint));
        return hintSet;
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

    private void parseOptions(Set<Option> options, StringBuilder stringBuilder) {
        if (options.isEmpty()) {
            stringBuilder.append(" " + COMMA_DELIMITTER);
        } else {
            options.forEach(option -> stringBuilder.append(option.optionValue)
                    .append(COMMA_DELIMITTER));
        }
    }

    private void parseHints(Set<Hint> hintSet, StringBuilder stringBuilder) {
        if (hintSet.isEmpty()) {
            stringBuilder.append(" " + COMMA_DELIMITTER);
        } else {
            hintSet.forEach(hint -> stringBuilder.append(hint.hintName)
                    .append(COMMA_DELIMITTER));
        }
    }
}
