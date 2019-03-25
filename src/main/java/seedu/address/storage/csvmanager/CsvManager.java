package seedu.address.storage.csvmanager;

import java.io.*;
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
    private BufferedReader bufferedReader;


    @Override
    public void readFoldersToCsv(CsvFile csvFile) throws IOException {
        String filePath = getDefaultFilePath() + "/" + csvFile.filename;
        try {
            bufferedReader = new BufferedReader(new FileReader(filePath));
            String line;
            String header = bufferedReader.readLine();

            while ((line = bufferedReader.readLine()) != null) {

                // use comma as separator
                String[] card = line.split(COMMA_DELIMITTER);

                System.out.println("card : " + card[0] + " " + card[1]);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private boolean checkCorrectHeaders(String header) {
        String[] cardHeaders = CARD_HEADERS.split(",");
        String[] fileHeaders = header.split(",")
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
