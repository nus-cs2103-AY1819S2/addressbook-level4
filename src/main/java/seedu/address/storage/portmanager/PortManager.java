package seedu.address.storage.portmanager;

import static seedu.address.commons.core.Messages.MESSAGE_FILEPATH_INVALID;
import static seedu.address.commons.core.Messages.MESSAGE_IMPORTED_DECK_INVALID;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.deck.Deck;
import seedu.address.model.deck.Name;
import seedu.address.model.deck.exceptions.DeckImportException;
import seedu.address.storage.JsonExportableDeck;


/**
 * Manages the import and exports of decks
 */

public class PortManager implements Porter {

    private static final Logger logger = LogsCenter.getLogger(PortManager.class);

    private Path baseFilePath;

    public PortManager() {
        baseFilePath = Paths.get("");
    }

    public PortManager(Path bfp) {
        baseFilePath = bfp;
    }

    public String getBfp() {
        return baseFilePath.toAbsolutePath().toString();
    }

    @Override
    public String exportDeck(Deck deck) {
        Name deckName = deck.getName();
        Path filePath = makeFilePath(deckName.fullName);

        JsonExportableDeck adaptedDeck = new JsonExportableDeck(deck);

        try {
            //If file doesn't exist, create it
            FileUtil.createIfMissing(filePath);

            //Write to file.
            JsonUtil.saveDataToFile(filePath, adaptedDeck);
        } catch (Exception e) {
            e.printStackTrace();
            throw new AssertionError("Unexpected exception " + e.getMessage(), e);
        }

        return filePath.toAbsolutePath().toString();
    }

    @Override
    public Deck importDeck(String stringPath) throws DeckImportException {
        Path filepath = makeFilePath(stringPath);
        Optional <JsonExportableDeck> jsonDeck = loadDeckFromFile(filepath);

        return convertDeck(jsonDeck.get());
    }

    /**
     * Attempts to load the data from the file at filepath.
     * Returns a JsonExportableDeck object.
     */

    private <T> Optional <JsonExportableDeck> loadDeckFromFile(Path filepath) throws DeckImportException {
        Optional <JsonExportableDeck> jsonDeck;
        try {
            jsonDeck = JsonUtil.getDataFromFile(filepath, JsonExportableDeck.class);
            return jsonDeck;
        } catch (FileNotFoundException e) {
            throw new DeckImportException(String.format(MESSAGE_FILEPATH_INVALID, filepath));
        } catch (DataConversionException e) {
            throw new DeckImportException(String.format(MESSAGE_IMPORTED_DECK_INVALID, filepath));
        }
    }

    /**
     * Converts the JsonExportableDeck to a Deck
     * Returns a deck object
     * If contents are invalid, throw DataConversionException
     */

    private Deck convertDeck(JsonExportableDeck targetDeck) {
        try {
            return targetDeck.toModelType();
        } catch (IllegalValueException e) {
            logger.info("Illegal values found in " + targetDeck + ": " + e.getMessage());
            throw new DeckImportException(MESSAGE_IMPORTED_DECK_INVALID);
        }

    }

    /**
     * Convert the string into a file path.
     *
     * @param name The name of the file, can be the absolute or relative file path
     * @return a Path that represents the file path
     */

    private Path makeFilePath(String name) {
        if (name.length() > 5 && name.substring(name.length() - 5).equals(".json")) {
            return baseFilePath.resolve(name);
        } else {
            return baseFilePath.resolve(name + ".json");
        }
    }

}
