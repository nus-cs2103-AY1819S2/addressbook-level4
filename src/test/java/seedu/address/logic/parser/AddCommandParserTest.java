package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DEADLINE_DESC_VALID;
import static seedu.address.logic.commands.CommandTestUtil.DIR_1_VALID;
import static seedu.address.logic.commands.CommandTestUtil.DIRECTORY_DESC_1;
import static seedu.address.logic.commands.CommandTestUtil.FILE_DESC_1_PDF;
import static seedu.address.logic.commands.CommandTestUtil.FILE_DESC_2_PDF;
import static seedu.address.logic.commands.CommandTestUtil.FILE_DESC_PATH_INVALID;
import static seedu.address.logic.commands.CommandTestUtil.NAME_1_VALID;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_1_VALID;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_CS2103T;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPdfs.SAMPLE_PDF_1;

import seedu.address.logic.commands.AddCommand;
import seedu.address.model.pdf.Pdf;
import seedu.address.testutil.PdfBuilder;

import org.junit.Test;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_onlyFileFieldPresent_success() {
        Pdf expectedPdf = new PdfBuilder(SAMPLE_PDF_1).build();

        assertParseSuccess(parser, PREAMBLE_WHITESPACE + FILE_DESC_1_PDF, new AddCommand(expectedPdf));
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + FILE_DESC_2_PDF + FILE_DESC_1_PDF,
                new AddCommand(expectedPdf));
    }

    @Test
    public void parse_additionalFieldsPresent_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        assertParseFailure(parser, FILE_DESC_1_PDF + TAG_DESC_CS2103T, expectedMessage);
        assertParseFailure(parser, FILE_DESC_2_PDF + DEADLINE_DESC_VALID, expectedMessage);
        assertParseFailure(parser, FILE_DESC_1_PDF + NAME_DESC_1_VALID, expectedMessage);
        assertParseFailure(parser, FILE_DESC_2_PDF + DIRECTORY_DESC_1, expectedMessage);
        assertParseFailure(parser, FILE_DESC_2_PDF + TAG_DESC_CS2103T + DEADLINE_DESC_VALID + NAME_DESC_1_VALID
                + DIRECTORY_DESC_1, expectedMessage);
    }

    @Test
    public void parse_compulsoryFileFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing file prefix
        assertParseFailure(parser, NAME_1_VALID, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + DIR_1_VALID,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        // invalid file path
        assertParseFailure(parser, FILE_DESC_PATH_INVALID, ParserUtil.MESSAGE_FILE_DOES_NOT_EXIST);
    }
}
