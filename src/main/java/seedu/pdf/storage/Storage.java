package seedu.pdf.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.pdf.commons.exceptions.DataConversionException;
import seedu.pdf.model.ReadOnlyPdfBook;
import seedu.pdf.model.ReadOnlyUserPrefs;
import seedu.pdf.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends PdfBookStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getPdfBookFilePath();

    @Override
    Optional<ReadOnlyPdfBook> readPdfBook() throws DataConversionException, IOException;

    @Override
    void savePdfBook(ReadOnlyPdfBook pdfBook) throws IOException;

}
