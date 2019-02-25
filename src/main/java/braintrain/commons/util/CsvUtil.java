package braintrain.commons.util;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import braintrain.commons.core.LogsCenter;
import braintrain.commons.exceptions.DataConversionException;

/**
 * Converts a Java object instance to CSV and vice versa
 */
public class CsvUtil {
    private static final Logger logger = LogsCenter.getLogger(CsvUtil.class);

    /**
     * Returns the Csv object from the given file or {@code Optional.empty()} object if the file is not found.
     * If any values are missing from the file, default values will be used, as long as the file is a valid csv file.
     *
     * @param filePath                   cannot be null.
     * @param classOfObjectToDeserialize Csv file has to correspond to the structure in the class given here.
     * @throws DataConversionException if the file format is not as expected.
     */
    public static <T> Optional<T> readCsvFile(
        Path filePath, Class<T> classOfObjectToDeserialize) throws DataConversionException, IOException {
        requireNonNull(filePath);

        if (!Files.exists(filePath)) {
            logger.info("Csv file " + filePath + " not found");
            return Optional.empty();
        }


        T csvFile = null;
        /*
        try {
            csvFile = null; //TODO
        } catch (IOException e) {
            logger.warning("Error reading from csvFile file " + filePath + ": " + e);
            throw new DataConversionException(e);
        }
        */

        return Optional.of(csvFile);
    }

    /**
     * Saves the Csv object to the specified file.
     * Overwrites existing file if it exists, creates a new file if it doesn't.
     *
     * @param csvFile  cannot be null
     * @param filePath cannot be null
     * @throws IOException if there was an error during writing to the file
     */
    public static <T> void saveCsvFile(T csvFile, Path filePath) throws IOException {
        requireNonNull(filePath);
        requireNonNull(csvFile);

        //serializeObjectToCsvFile(filePath, csvFile);
    }


}
