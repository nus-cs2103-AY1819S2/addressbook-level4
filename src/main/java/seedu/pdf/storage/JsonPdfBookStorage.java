package seedu.pdf.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.pdf.commons.core.LogsCenter;
import seedu.pdf.commons.exceptions.DataConversionException;
import seedu.pdf.commons.exceptions.IllegalValueException;
import seedu.pdf.commons.util.FileUtil;
import seedu.pdf.commons.util.JsonUtil;
import seedu.pdf.model.ReadOnlyPdfBook;

/**
 * A class to access PdfBook data stored as a json file on the hard disk.
 */
public class JsonPdfBookStorage implements PdfBookStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonPdfBookStorage.class);

    private Path filePath;

    public JsonPdfBookStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getPdfBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyPdfBook> readPdfBook() throws DataConversionException {
        return readPdfBook(filePath);
    }

    /**
     * Similar to {@link #readPdfBook()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyPdfBook> readPdfBook(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializablePdfBook> jsonPdfBook = JsonUtil.readJsonFile(
                filePath, JsonSerializablePdfBook.class);
        if (!jsonPdfBook.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonPdfBook.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void savePdfBook(ReadOnlyPdfBook pdfBook) throws IOException {
        savePdfBook(pdfBook, filePath);
    }

    /**
     * Similar to {@link #savePdfBook(ReadOnlyPdfBook)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void savePdfBook(ReadOnlyPdfBook pdfBook, Path filePath) throws IOException {
        requireNonNull(pdfBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializablePdfBook(pdfBook), filePath);
    }

}
