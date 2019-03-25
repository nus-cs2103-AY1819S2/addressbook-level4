package seedu.address.storage.csvmanager;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.exceptions.CommandException;
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


    @Override
    public void readFoldersToCsv(CsvFile csvFile) throws IOException, CommandException, FileNotFoundException {
        if (!fileExists(csvFile)) {
            throw new FileNotFoundException();
        }
        String filePath = getDefaultFilePath() + "/" + csvFile.filename;
        bufferedReader = new BufferedReader(new FileReader(filePath));
        String line;
        String header = bufferedReader.readLine();

        if (!checkCorrectHeaders(header)) {
            throw new CommandException("hekki");
        }

        while ((line = bufferedReader.readLine()) != null) {

            // use comma as separator
            String[] card = line.split(COMMA_DELIMITTER);

            System.out.println("card : " + card[0] + " " + card[1]);
        }
        if (bufferedReader != null) {
            bufferedReader.close();
        }
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
        String filepath = getDefaultFilePath();
        for (ReadOnlyCardFolder readOnlyCardFolder : cardFolders) {
            List<Card> cardList = readOnlyCardFolder.getCardList();
            String foldername = readOnlyCardFolder.getFolderName();
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

    public static boolean fileExists(CsvFile csvFile) throws IOException {
        return new File(getDefaultFilePath() + "/" +  csvFile.filename).isFile();
    }

    public static String getDefaultFilePath() throws IOException {
        return new File("./").getCanonicalPath();
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
