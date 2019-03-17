package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

    private static final String CORE_ESCAPE = "*";
    private static final String QUESTION_ESCAPE = "?";

    private Path folderPath;

    public CsvUserStorage(Path folderPath) {
        this.folderPath = folderPath;
    }

    @Override
    public Path getUserFolderPath() {
        return folderPath;
    }

    @Override
    public void setUserFolderPath(Path folderPath) {
        requireNonNull(folderPath);
        this.folderPath = folderPath;
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

        String[] header = data.get(0);
        List<String> fields = Arrays.asList(header);

        int hashCode = 0;
        int streak = 0;
        int numofattempts = 0;
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        Instant srsduedate = Instant.from(zonedDateTime);

        CardSrsData newCardSrsData = new CardSrsData(hashCode, numofattempts, streak, srsduedate);

        for (int i = 1; i < data.size(); i++) {
            newCardSrsData.getCard(i);
        }

        return Optional.of(newCardSrsData);
    }

    /**
     * Returns a String[] containing correctly formatted strings for saving.
     *
     * @param card
     * @return Header data with relevant escape characters.
     */
    private String[] parseHeaderData(CardSrsData card) {
        String[] header;

        List<String> headerList = new ArrayList<>();

        int headerSize = headerList.size();

        header = new String[headerSize];
        headerList.toArray(header);

        header[card.getHashCode()] = QUESTION_ESCAPE + header[card.getHashCode()];
        header[card.getStreak()] = QUESTION_ESCAPE + header[card.getStreak()];
        header[card.getNumOfAttempts()] = QUESTION_ESCAPE + header[card.getNumOfAttempts()];

        for (int i = 0; i < headerSize; i++) {
            header[i] = CORE_ESCAPE + header[i];
        }
        return header;
    }

    /**
     * Returns a String[] of all card fields in order.
     *
     * @param card
     * @return Formatted card data.
     */
    private String[] parseCardData(CardSrsData card) {
        String[] cardArray;

        List<String> cardData = new ArrayList<>();
        cardData.addAll(cardData);

        cardArray = new String[card.getCardData().size()];
        cardData.toArray(cardArray);

        return cardArray;
    }

    /**
     * @param user
     */
    private void saveUserToFile(User user, Path folderPath) throws IOException {
        List<String[]> data = new ArrayList<>();
        Path filePath = Paths.get(folderPath.toString(), user.getName() + ".csv");

        data.add(parseHeaderData((CardSrsData) user));

        for (CardSrsData cardsrsdata : user.getCardData()) {
            data.add(parseCardData(cardsrsdata));
        }

        CsvUtil.writeCsvFile(filePath, data);
    }

    @Override
    public Optional<User> readUser() throws IOException {
        return readUser(folderPath);
    }

    @Override
    public Optional<User> readUser(Path folderPath) {
        requireNonNull(folderPath);
        List<Path> paths = new ArrayList<>();
        User user = new User();
        try {
            Files.walk(folderPath, 1).filter(path ->
                    path.toString().endsWith(".csv")).forEach(paths::add);
        } catch (IOException e) {
            return Optional.empty();
        }
        for (Path filePath : paths) {
            Optional<User> newUser = parseFileIntoUser(filePath);
            newUser.ifPresent(user::addUser);
        }
        return Optional.of(user);
    }

    @Override
    public int saveUser(User user) {
        return saveUser(user, folderPath);
    }

    @Override
    public int saveUser(User users, Path folderPath) {
        requireNonNull(users);
        requireNonNull(folderPath);

        int saveCount = 0;

        List<CardSrsData> allUser = users.getCardData();

        for (CardSrsData cardSrsData: allUser) {
            try {
                saveUserToFile(cardSrsData, folderPath);
                saveCount++;
            } catch (IOException e) {
                logger.warning(cardSrsData.getName() + " failed to save; IOException occurred");
            }
        }
        return saveCount;
    }
}
