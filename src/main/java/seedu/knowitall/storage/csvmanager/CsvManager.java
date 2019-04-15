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
import seedu.knowitall.storage.csvmanager.exceptions.IncorrectCsvHeadersException;


/**
 * Manages the importing and exporting of flashcards into model
 */
public class CsvManager implements CsvCommands {


    private static final String COMMA_DELIMITTER = ",";
    private static final String NEW_LINE_SEPARATOR = "\n";
    private static final String CARD_HEADERS = "Question,Answer,Hints,Option,Option,Option";
    private static final String TEST_FOLDER_PATH = "test";

    private String defaultPath;
    private boolean isImportTest = false;




    public CsvManager() throws IOException {
        defaultPath = getDefaultFilePath();
    }

    @Override
    public CardFolder readFoldersFromCsv (CsvFile csvFile) throws IOException, CommandException,
            IllegalArgumentException, IncorrectCsvHeadersException {
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
            throw new IncorrectCsvHeadersException(Messages.MESSAGE_INCORRECT_CSV_FILE_HEADER);
        }

        while ((line = bufferedReader.readLine()) != null) {

            // split quotes within card field by commas
            String[] stringCard = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);


            Card card = buildCard(stringCard);
            cardFolder.addCard(card);
        }
        if (bufferedReader != null) {
            bufferedReader.close();
        }
        return cardFolder;
    }

    /**
     * Builds a card object from each line of the csv file imported. Throws IllegalArgumentException in the event that
     * lines in csv file do not follow {@code Card} field specifications.
     */
    private Card buildCard(String[] rawStringCard) throws IllegalArgumentException {
        // cardValues = {"question", "answer", "hint","option"}

        // Allow only max 3 options per card
        String[] stringCard = Arrays.copyOfRange(rawStringCard, 0, 7);

        // remove double quotes from each string array
        String[] cardValues = Stream.of(rawStringCard).map(line -> line.replace("\"", ""))
                .toArray(String[]::new);

        Question question = new Question(cardValues[0]);
        Answer answer = new Answer(cardValues[1]);
        Set<Option> optionSet = buildOptions(cardValues);
        Set<Hint> hintSet = buildHint(cardValues);
        return new Card(question, answer, new Score(0, 0), optionSet, hintSet);
    }

    /**
     * reconstructs a set of options from each line of the csv file to import
     */
    private Set<Option> buildOptions(String[] card) {
        Set<Option> optionSet = new HashSet<>();
        String[] optionValues = Arrays.copyOfRange(card, 3, card.length);
        // if all option fields are empty
        if (optionValues[0].equals("") && optionValues[1].equals("") && optionValues[2].equals("")) {
            return optionSet;
        }
        Arrays.stream(optionValues)
                .filter(option -> !option.equals(""))
                .forEach(optionString -> optionSet.add(new Option(optionString)));
        return optionSet;
    }

    /**
     * reconstructs a set of hints from each line of the csv file to import.
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
     * checks whether the headers of the imported file conforms to the specifications of the csv file header. Throws
     * Command Exception if file is empty or headers do not follow specifications.
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
            String cardHeader = cardHeaders[i].trim().toLowerCase();
            String fileHeader = fileHeaders[i].trim().toLowerCase();
            if (!cardHeader.equals(fileHeader)) {
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
        if (isImportTest) {
            return folder + " " + TEST_FOLDER_PATH;
        }
        return folderName;
    }

    private boolean fileExists(CsvFile csvFile) {
        return new File(defaultPath + "/" + csvFile.filename).exists();
    }

    private static String getDefaultFilePath() throws IOException {
        return new File(CsvUtils.DEFAULT_FILE_PATH).getCanonicalPath();
    }

    public String getDefaultPath() {
        return defaultPath;
    }

    public void setTestDefaultPath(String testPath) {
        defaultPath = defaultPath + testPath;

        if (testPath.equals(CsvUtils.DEFAULT_IMPORT_TEST_PATH)) {
            isImportTest = true;
        }
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
     * Parses quotation marks to card field strings if comma value is present within field.
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
     * Parses each {@code Set<Option>} of the card attribute into a string
     */
    private void parseOptions(Set<Option> options, StringBuilder stringBuilder) {
        if (options.isEmpty()) {
            // include up to 3 options
            stringBuilder.append(COMMA_DELIMITTER).append(COMMA_DELIMITTER);
        } else {
            Set<String> optionString = options.stream().map(x -> x.optionValue).collect(Collectors.toSet());
            String toJoin = String.join(COMMA_DELIMITTER, optionString);
            toJoin = fillCommas(toJoin);
            stringBuilder.append(toJoin);
        }
    }

    /**
     * Ensures that each row of the csv file has option fields filled correctly with commas
     */
    private String fillCommas(String toJoin) {
        // 3 options filled
        long numCommas = toJoin.chars().mapToObj(c -> (char) c)
                .filter(c -> String.valueOf(c).equals(COMMA_DELIMITTER))
                .count();
        if (numCommas == 0) {
            toJoin += COMMA_DELIMITTER;
            toJoin += COMMA_DELIMITTER;
        }
        if (numCommas == 1) {
            toJoin += COMMA_DELIMITTER;
        }
        return toJoin;
    }

    /**
     * Parses each {@code Set<Hint>} of the card attribute into a string
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
