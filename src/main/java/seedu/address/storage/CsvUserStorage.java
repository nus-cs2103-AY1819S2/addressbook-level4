package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.CsvUtil;
import seedu.address.model.user.CardSrsData;
import seedu.address.model.user.User;

/**
 * A class to access User stored in the hard disk as a csv file
 */
public class CsvUserStorage implements UserStorage {
    private static final Logger logger = LogsCenter.getLogger(CsvLessonListStorage.class);

    private Path filePath;

    public CsvUserStorage(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public Path getUserFilePath() {
        return filePath;
    }

    @Override
    public void setUserFilePath(Path filePath) {
        requireNonNull(filePath);
        this.filePath = filePath;
    }

    /**
     * Parses the given file at the path into a user
     * @param filePath is not null
     * @return the parsed user
     */
    private Optional<User> parseFileIntoUser(Path filePath) {
        List<String[]> data;

        try {
            data = CsvUtil.readCsvFile(filePath);
        } catch (IOException e) {
            logger.warning("Unable to read file at: " + filePath.toString());
            return Optional.empty();
        }
        if (data == null) {
            logger.warning("Empty/invalid file at: " + filePath.toString());
            return Optional.empty();
        }

        User user = new User();
        for (String[] arr : data) {
            try {
                user.addCard(parseStringIntoCard(arr));
            } catch (IllegalValueException e) {
                //do error handling here
                logger.warning("Values are empty" + filePath.toString());
            } catch (NumberFormatException e) {
                //do error handling here
                logger.warning("Values are not correct" + filePath.toString());
            } catch (DateTimeParseException e) {
                //do error handling here
                logger.warning("SrsDate is wrong" + filePath.toString());
            }
        }

        return Optional.of(user);
    }

    /**
     * Parses the current user into a file
     * @param user
     * @param filePath
     * @throws IOException
     */
    private void parseUserIntoFile(User user, Path filePath) throws IOException {
        requireNonNull(filePath);
        user.getCards();
        List<String[]> data = new ArrayList<>();

        for (Map.Entry<Integer, CardSrsData> entry : user.getCards().entrySet()) {
            data.add(parseCardIntoString(entry.getValue()));
        }

        CsvUtil.writeCsvFile(filePath, data);
    }

    /**
     * Converts a card with its constructor values into a String Array ready for CSV file
     * @param card
     * @return a String array with the cardData(hashcode, numAttempts, streak, srs, isDifficult)
     */
    private String[] parseCardIntoString(CardSrsData card) {
        String[] cardArray = new String[5];

        cardArray[0] = Integer.toString(card.getHashCode());
        cardArray[1] = Integer.toString(card.getNumOfAttempts());
        cardArray[2] = Integer.toString(card.getStreak());
        cardArray[3] = card.getSrsDueDate().toString();
        cardArray[4] = String.valueOf(card.isDifficult());

        return cardArray;
    }

    /**
     * Converts a card from a String Array in the CSV file
     * @param cardArray
     * @return card type with the constructor values
     */
    private CardSrsData parseStringIntoCard(String[] cardArray) throws IllegalValueException,
            NumberFormatException, DateTimeParseException {
        for(int i=0; i < cardArray.length-1; i++) {
            if (cardArray[i].isEmpty()) {
                //throw error for empty
                throw new IllegalValueException("There are empty vales in the file");
            }
        }

        //values are non-empty, can initialize them now
        int hashCode;
        int numOfAttempts;
        int streak;
        Instant srs;
        boolean isDifficult;
        CardSrsData card;

       try {
            hashCode = Integer.parseInt(cardArray[0]);
            numOfAttempts = Integer.parseInt(cardArray[1]);
            streak = Integer.parseInt(cardArray[2]);
            srs = Instant.parse(cardArray[3]);

           // TODO remove this check after session uses the new constructor
           if (cardArray.length == 5) {
               isDifficult = cardArray[4].equals("true");
           } else {
               isDifficult = false;
           }
           card = new CardSrsData(hashCode, numOfAttempts, streak, srs, isDifficult);

           return card;
       } catch (NumberFormatException e) {
           throw e;
       } catch(DateTimeParseException e) {
           throw e;
       }

    }

    @Override
    public Optional<User> readUser() {
        return readUser(filePath);
    }

    @Override
    public Optional<User> readUser(Path filePath) {
        requireNonNull(filePath);
        Optional<User> newUser = parseFileIntoUser(filePath);

        return newUser;
    }

    @Override
    public void saveUser(User user) {
        saveUser(user, filePath);
    }

    @Override
    public void saveUser(User users, Path filePath) {
        requireNonNull(users);
        requireNonNull(filePath);

        try {
            parseUserIntoFile(users, filePath);
        } catch (IOException e) {
            logger.warning("Failed to save user; IOException occured");
        }
    }
}
