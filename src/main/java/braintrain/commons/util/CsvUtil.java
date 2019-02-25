package braintrain.commons.util;

import com.opencsv.CSVReader;
import java.io.Reader;
import java.util.List;
import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Logger;

import braintrain.commons.core.LogsCenter;
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
     * @throws IOException
     */
    public static List<String[]> readCsvFile(Path filePath) throws IOException {
        requireNonNull(filePath);

        if (!Files.exists(filePath)) {
            logger.info("csv file at [" + filePath + "] not found");
            return null;
        }

        CSVReader csvReader;
        List<String[]> values;
        try {
            Reader reader = Files.newBufferedReader(filePath);
            csvReader = new CSVReader(reader);
            values = csvReader.readAll();
        } catch (IOException exception) {
            logger.info("Invalid csv file.");
            return null;
        }
        return values;
    }

    /*
     * Saves the Csv object to the specified file.
     * Overwrites existing file if it exists, creates a new file if it doesn't.
     *
     * @param csvFile  cannot be null
     * @param filePath cannot be null
     * @throws IOException if there was an error during writing to the file
     */
    /*
    public static <T> void saveCsvFile(T csvFile, Path filePath) throws IOException {
        requireNonNull(filePath);
        requireNonNull(csvFile);

        //serializeObjectToCsvFile(filePath, csvFile);
    }

    */
}
