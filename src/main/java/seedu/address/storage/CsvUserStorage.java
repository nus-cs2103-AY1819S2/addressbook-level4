package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.time.Instant;
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
    private static final Logger logger = LogsCenter.getLogger(CsvLessonsStorage.class);

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
     *
     * @param filePath
     * @return
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
            user.addCard(parseStringIntoCard(arr));
        }

        return Optional.of(user);
    }

    /**
     * TODO
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
     *
     * @param card
     * @return a String array with the cardData(hashcode, numAttempts, streak, srs)
     */
    private String[] parseCardIntoString(CardSrsData card) {
        boolean hasInvalid = false;
        String[] cardArray = new String[4];

        if (card.getHashCode() < 0) {
            logger.warning("Card Hashcode provided cannot be negative. Setting it to -1");
            cardArray[0] = Integer.toString(-1);
        }
        cardArray[0] = Integer.toString(card.getHashCode());

        if (card.getNumOfAttempts() < 0) {
            logger.warning("Number of attempts provided cannot be negative. Setting it to -1");
            cardArray[1] = Integer.toString(-1);
        }
        cardArray[1] = Integer.toString(card.getNumOfAttempts());

        if (card.getStreak() < 0) {
            logger.warning("Current streak provided cannot be negative. Setting it to -1");
            cardArray[2] = Integer.toString(-1);
        }
        cardArray[2] = Integer.toString(card.getStreak());

        if (card.getSrsDueDate().toString().equals(null)) {
            logger.warning("SrsDueDate provided cannot be null. Setting it to -1");
            cardArray[3] = Integer.toString(-1);
        }
        if (card.getSrsDueDate().toString().equals("")) {
            logger.warning("SrsDuedate provided cannot be empty. Setting it to -1");
            cardArray[3] = Integer.toString(-1);
        }
        cardArray[3] = card.getSrsDueDate().toString();

        for (String checker: cardArray) {
            if (checker.equals("-1")) {
                hasInvalid = true;
                break;
            }
        }
        if (hasInvalid == true) {
            logger.warning("Card array data returned with -1 as undesired parameters.");
        }

        return cardArray;
    }

    /**
     * TODO
     * @param cardArray
     * @return
     */
    private CardSrsData parseStringIntoCard(String[] cardArray) {
        int hashCode;
        int numOfAttempts;
        int streak;
        Instant srs;
        boolean hasInvalid = false;

        hashCode = Integer.parseInt(cardArray[0]);

        if (hashCode < 0) {
            logger.warning("Card Hashcode provided cannot be negative. Setting it to -1");
            hashCode = -1;
            hasInvalid = true;
        }

        numOfAttempts = Integer.parseInt(cardArray[1]);

        if (numOfAttempts < 0) {
            logger.warning("Number of attempts provided cannot be negative. Setting it to -1");
            numOfAttempts = -1;
            hasInvalid = true;
        }

        streak = Integer.parseInt(cardArray[2]);

        if (streak < 0) {
            logger.warning("Streak cannot be negative. Setting it to -1");
            streak = -1;
            hasInvalid = true;
        }

        srs = Instant.parse(cardArray[3]);

        if (srs.toString() == null) {
            logger.warning("SrsDueDate provided cannot be null. Setting it to -1");
            srs = Instant.parse("-1");
            hasInvalid = true;
        }

        if (srs.toString() == "") {
            logger.warning("SrsDueDate provided cannot be empty. Setting it to -1");
            srs = Instant.parse("-1");
            hasInvalid = true;
        }
        CardSrsData card = new CardSrsData(hashCode, numOfAttempts, streak, srs);

        if (hasInvalid == true) {
            logger.warning("Card returned with undesired parameters as -1");
        }
        return card;
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
