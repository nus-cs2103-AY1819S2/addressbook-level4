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
import seedu.address.commons.util.CsvUtil;
import seedu.address.model.user.CardSrsData;
import seedu.address.model.user.User;

/**
 * A class to access User stored in the hard disk as a csv file
 */
public class CsvUserStorage implements UserStorage {
    private static final Logger logger = LogsCenter.getLogger(CsvLessonListStorage.class);
    private static final int MAX_SIZE = 5;
    private static final int ZERO = 0;

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
     * Parses the given file and attempts to read it. Failure to read will return null.
     *
     * @param filePath
     * @return the parsed user or null
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
            Optional<CardSrsData> card = parseStringIntoCard(arr);
            if (card.isPresent()) {
                user.addCard(card.get());
            }
        }

        return Optional.of(user);
    }

    /**
     * Parses the current user into a file
     *
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
     *
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
     *
     * @param cardArray
     * @return card type with the constructor values
     */

    private Optional<CardSrsData> parseStringIntoCard(String[] cardArray) throws
            NumberFormatException, DateTimeParseException {

        if (cardArray.length < MAX_SIZE) {
            logger.warning("There are empty values in the file");
            return Optional.empty();
        }

        for (int i = 0; i < cardArray.length - 1; i++) {
            if (cardArray[i].isEmpty()) {
                logger.warning("There are empty values in the file");
                return Optional.empty();
            }
        }

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
            isDifficult = cardArray[4].equals("true");

            if (hashCode == ZERO) {
                logger.warning("Hashcode cannot be 0 in " + filePath.toString());
                return Optional.empty();
            }

            if (numOfAttempts < ZERO) {
                logger.warning("Number of attempts cannot be 0 in " + filePath.toString());
                return Optional.empty();
            }

            if (streak < ZERO) {
                logger.warning("Streak cannot be 0 in " + filePath.toString());
                return Optional.empty();
            }
        } catch (NumberFormatException e) {
            logger.warning("Values are not correct in " + filePath.toString());
            return Optional.empty();

        } catch (DateTimeParseException e) {
            logger.warning("SrsDate is wrong in " + filePath.toString());
            return Optional.empty();
        }

        card = new CardSrsData(hashCode, numOfAttempts, streak, srs, isDifficult);

        return Optional.of(card);
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
            logger.warning("Failed to save user; IOException occurred");
        }
    }
}
