package seedu.pdf.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.pdf.commons.exceptions.DataConversionException;
import seedu.pdf.model.PdfBook;
import seedu.pdf.model.ReadOnlyPdfBook;

/**
 * Represents a storage for {@link PdfBook}.
 */
public interface PdfBookStorage {

    /**
     * Returns the file value of the data file.
     */
    Path getPdfBookFilePath();

    /**
     * Returns PdfBook data as a {@link ReadOnlyPdfBook}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyPdfBook> readPdfBook() throws DataConversionException, IOException;

    /**
     * @see #getPdfBookFilePath()
     */
    Optional<ReadOnlyPdfBook> readPdfBook(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyPdfBook} to the storage.
     * @param pdfBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void savePdfBook(ReadOnlyPdfBook pdfBook) throws IOException;

    /**
     * @see #savePdfBook(ReadOnlyPdfBook)
     */
    void savePdfBook(ReadOnlyPdfBook pdfBook, Path filePath) throws IOException;

}
