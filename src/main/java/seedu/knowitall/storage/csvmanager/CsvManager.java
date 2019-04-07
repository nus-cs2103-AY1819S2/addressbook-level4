package seedu.knowitall.storage.csvmanager;

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
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.knowitall.commons.core.Messages;
import seedu.knowitall.logic.commands.exceptions.CommandException;
import seedu.knowitall.model.CardFolder;
import seedu.knowitall.model.ReadOnlyCardFolder;
import seedu.knowitall.model.card.Answer;
import seedu.knowitall.model.card.Card;
import seedu.knowitall.model.card.Option;
import seedu.knowitall.model.card.Question;
import seedu.knowitall.model.card.Score;
import seedu.knowitall.model.hint.Hint;


/**
 * Manages the importing and exporting of flashcards into model
 */
public class CsvManager implements CsvCommands {

    private static final String COMMA_DELIMITTER = ",";
    private static final String NEW_LINE_SEPARATOR = "\n";
    private static final String CARD_HEADERS = "Question,Answer,Hints,Options";
    private static final String DEFAULT_TEST_PATH = "/src/test/data/CsvCardFolderTest";
    private static final String DEFAULT_FILE_PATH = "./";
    private static final String TEST_FOLDER_PATH = "test";
    private String defaultPath;
    private boolean setTestDefaultPath = false;



    public CsvManager() throws IOException {
        defaultPath = getDefaultFilePath();
    }

    @Override
    public CardFolder readFoldersToCsv(CsvFile csvFile) throws IOException, CommandException, IllegalArgumentException {
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
            throw new CommandException(Messages.MESSAGE_INCORRECT_CSV_FILE_HEADER);
        }

        while ((line = bufferedReader.readLine()) != null) {

            // split double quotes by commas
            String[] stringCard = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
            //System.out.println(a);
            //String[] stringCard = parseCardFieldCommas(line);
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
    private Card buildCard(String[] cardValues1) throws IllegalArgumentException {
        // cardValues = {"question", "answer", "hint","option"}

        // Allow only one option per card
        String[] cardValues2 = Arrays.copyOfRange(cardValues1, 0, 4);

        // remove double quotes from each string array
        String[] cardValues = Stream.of(cardValues2).map(line -> line.replace("\"", ""))
                .toArray(String[]::new);

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
        String[] options = Arrays.copyOfRange(card, 3, card.length - 1);
        if (options.length == 0) {
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
        String hint = card[2];
        if (hint.equals("")) {
            return hintSet;
        }
        hintSet.add(new Hint(hint));
        return hintSet;
    }

    /**
     * checks whether the headers of the imported file conforms to the specifications of the Card headers
     */
    private boolean checkCorrectHeaders(String header) throws CommandException {
        if (header == null) {
            throw new CommandException(Messages.MESSAGE_EMPTY_CSV_FILE);
        }

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

    public boolean fileExists(CsvFile csvFile) {
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
        parseHints(card.getHints(), stringBuilder);
        parseOptions(card.getOptions(), stringBuilder);
        return stringBuilder.toString();
    }

    /**
     * Method ensures the correct parsing of commas within card field names
     */
    private String parseQuotationMarks(String cardField) {
        if (cardField.contains(",")) {
            return "\"" + cardField + "\"";
        } else {
            return cardField;
        }
    }

    private void parseQuestion(Question question, StringBuilder stringBuilder) {
        String questionString = parseQuotationMarks(question.toString());
        stringBuilder.append(questionString + COMMA_DELIMITTER);
    }

    private void parseAnswer(Answer answer, StringBuilder stringBuilder) {
        String answerString = parseQuotationMarks(answer.toString());
        stringBuilder.append(answerString + COMMA_DELIMITTER);
    }

    /**
     * helper method that parses each {@code Set<Option>} of the card attribute into a string
     */
    private void parseOptions(Set<Option> options, StringBuilder stringBuilder) {
        if (options.isEmpty()) {
            return;
        } else {
            Set<String> optionString = options.stream().map(x -> x.optionValue).collect(Collectors.toSet());
            String toJoin = String.join(",", optionString);
            stringBuilder.append(toJoin);

        }
    }

    /**
     * helper method that parses each {@code Set<Hint>} of the card attribute into a string
     */
    private void parseHints(Set<Hint> hintSet, StringBuilder stringBuilder) {
        if (hintSet.isEmpty()) {
            stringBuilder.append("" + COMMA_DELIMITTER);
        } else {
            hintSet.forEach(hint -> stringBuilder.append(parseQuotationMarks(hint.hintName))
                    .append(COMMA_DELIMITTER));
        }
    }
}
