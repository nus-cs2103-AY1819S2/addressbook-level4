package seedu.address.logic.parser;

import org.junit.Test;
import seedu.address.logic.commands.ExportCommand;
import seedu.address.storage.csv_manager.CardFolderExport;
import seedu.address.storage.csv_manager.CsvFile;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.*;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

public class ExportCommandParserTest {

    private ExportCommandParser parser = new ExportCommandParser();

    @Test
    public void parse_allFields_present() {
        ExportCommand exportCommandWithOneFolder = buildExportCommand(FILENAME_DESC_SAMPLE, FOLDER_DESC_SAMPLE_1);
        ExportCommand exportCommandWithMultipleFolder = buildExportCommand(FILENAME_DESC_SAMPLE,
                FOLDER_DESC_SAMPLE_1, FOLDER_DESC_SAMPLE_2);

        // whitespace preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + FOLDER_DESC_SAMPLE_1
                + FILENAME_DESC_SAMPLE, exportCommandWithOneFolder);

        // multiple folders
        assertParseSuccess(parser, FOLDER_DESC_SAMPLE_1 + FOLDER_DESC_SAMPLE_2
                + FILENAME_DESC_SAMPLE, exportCommandWithMultipleFolder);
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE);

        // missing folder prefix
        assertParseFailure(parser, VALID_FOLDER_NAME_1
                + FILENAME_DESC_SAMPLE, String.format(expectedMessage));

        // missing filename prefix
        assertParseFailure(parser, FOLDER_DESC_SAMPLE_1
                + VALID_FILENAME, String.format(expectedMessage));

        // missing all prefix
        assertParseFailure(parser, VALID_FOLDER_NAME_1
                + VALID_FILENAME, String.format(expectedMessage));
    }

    @Test
    public void parse_invalidValue_failure() {

        // missing Folder name
        assertParseFailure(parser, INVALID_FOLDER_DESC + FILENAME_DESC_SAMPLE,
                CardFolderExport.MESSAGE_CONSTRAINTS);

        // missing filename
        assertParseFailure(parser, FOLDER_DESC_SAMPLE_1 + INVALID_FILENAME_DESC,
                CsvFile.MESSAGE_CONSTRAINTS);
    }


    /**
     *
     * helper method to build export command for testing
     */
    private ExportCommand buildExportCommand(String validFileName, String... validFolderDescriptions) {
        Set<CardFolderExport> cardFolderExports = Stream.of(validFolderDescriptions)
                .map(CardFolderExport::new)
                .collect(Collectors.toSet());
        CsvFile csvFile = new CsvFile(validFileName);
        return new ExportCommand(cardFolderExports, csvFile);
    }
}
