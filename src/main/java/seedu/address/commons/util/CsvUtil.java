package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.logging.Logger;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import seedu.address.commons.core.LogsCenter;

/**
 * Converts a List of String arrays to CSV and vice versa.
 */
public class CsvUtil {
    private static final Logger logger = LogsCenter.getLogger(CsvUtil.class);

    /**
     * Returns a list of String arrays from the given file or null object if the file is not found.
     *
     * Files are automatically assumed to be UTF-8 encoded, and BOMs are checked for and removed.
     *
     * @param filePath cannot be null.
     * @throws IOException
     */
    public static List<String[]> readCsvFile(Path filePath) throws IOException, NullPointerException {
        requireNonNull(filePath);

        if (!Files.exists(filePath)) {
            logger.info("csv file at [" + filePath + "] not found");
            return null;
        }

        CSVReader csvReader;
        List<String[]> values;
        BufferedReader reader = Files.newBufferedReader(filePath, StandardCharsets.UTF_8);

        csvReader = new CSVReader(reader);
        values = csvReader.readAll();

        if (values.size() == 0) {
            logger.info("Invalid/empty file: " + filePath.getFileName().toString());
            return null;
        }

        //Extra handling of first line for UTF-8 BOM.
        String[] firstLine = values.get(0);
        if (firstLine[0].startsWith("\uFEFF")) {
            firstLine[0] = firstLine[0].substring(1);
            values.set(0, firstLine);
        }
        return values;
    }

    /**
     * Saves the Csv object to the specified file.
     * Overwrites existing file if it exists, creates a new file if it doesn't.
     * <p>
     * Defaults to UTF-8 csv by appending the UTF-8 BOM, \uFEFF.
     *
     * @param data     cannot be null
     * @param filePath cannot be null
     * @return Whether a file was successfully written to.
     * @throws IOException if there was an error during writing to the file
     */
    public static boolean writeCsvFile(Path filePath, List<String[]> data) throws IOException, NullPointerException {
        requireNonNull(filePath);
        requireNonNull(data);

        if (data.size() == 0) {
            logger.info("Attempted to write empty data.");
            return false;
        }

        CSVWriter csvWriter;

        BufferedWriter writer = Files.newBufferedWriter(filePath);

        //write UTF-8 BOM
        writer.write("\uFEFF");

        csvWriter = new CSVWriter(writer, CSVWriter.DEFAULT_SEPARATOR, CSVWriter.DEFAULT_QUOTE_CHARACTER,
            '\\', CSVWriter.DEFAULT_LINE_END);
        csvWriter.writeAll(data);
        csvWriter.close();
        return true;
    }
}
