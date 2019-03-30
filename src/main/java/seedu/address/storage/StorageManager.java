package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyPdfBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of PdfBook data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private PdfBookStorage pdfBookStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(PdfBookStorage pdfBookStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.pdfBookStorage = pdfBookStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ PdfBook methods ==============================

    @Override
    public Path getPdfBookFilePath() {
        return pdfBookStorage.getPdfBookFilePath();
    }

    @Override
    public Optional<ReadOnlyPdfBook> readPdfBook() throws DataConversionException, IOException {
        return readPdfBook(pdfBookStorage.getPdfBookFilePath());
    }

    @Override
    public Optional<ReadOnlyPdfBook> readPdfBook(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return pdfBookStorage.readPdfBook(filePath);
    }

    @Override
    public void savePdfBook(ReadOnlyPdfBook pdfBook) throws IOException {
        savePdfBook(pdfBook, pdfBookStorage.getPdfBookFilePath());
    }

    @Override
    public void savePdfBook(ReadOnlyPdfBook pdfBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        pdfBookStorage.savePdfBook(pdfBook, filePath);
    }

}
