package seedu.pdf.logic.parser;

import static seedu.pdf.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.pdf.logic.commands.CommandTestUtil.DEADLINE_DESC_VALID;
import static seedu.pdf.logic.commands.CommandTestUtil.FILE_DESC_1_PDF;
import static seedu.pdf.logic.commands.CommandTestUtil.PASSWORD_1_VALID;
import static seedu.pdf.logic.commands.CommandTestUtil.PASSWORD_DESC_VALID;
import static seedu.pdf.logic.commands.CommandTestUtil.TAG_DESC_CS2103T;
import static seedu.pdf.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.pdf.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.pdf.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.pdf.testutil.TypicalIndexes.INDEX_FIRST_PDF;

import org.junit.Test;

import seedu.pdf.commons.core.index.Index;
import seedu.pdf.logic.commands.EncryptCommand;


public class EncryptCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            EncryptCommand.MESSAGE_USAGE);

    private EncryptCommandParser parser = new EncryptCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, PASSWORD_DESC_VALID, MESSAGE_INVALID_FORMAT);

        // no prefix and password
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);

        // no password prefix
        assertParseFailure(parser, "1" + PASSWORD_1_VALID, MESSAGE_INVALID_FORMAT);

        // no password
        assertParseFailure(parser, "1" + PREFIX_PASSWORD, MESSAGE_INVALID_FORMAT);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_containsIrrelevantParts_failure() {
        assertParseFailure(parser, "1 " + PASSWORD_DESC_VALID + DEADLINE_DESC_VALID, MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "1 " + PASSWORD_DESC_VALID + TAG_DESC_CS2103T, MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "1 " + PASSWORD_DESC_VALID + FILE_DESC_1_PDF, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + PASSWORD_DESC_VALID, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + PASSWORD_DESC_VALID, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 se/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_PDF;

        String userInput = "" + targetIndex.getOneBased() + PASSWORD_DESC_VALID;

        EncryptCommand expectedCommand = new EncryptCommand(targetIndex, PASSWORD_1_VALID);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_PDF;
        String userInput = targetIndex.getOneBased() + PASSWORD_DESC_VALID + PASSWORD_DESC_VALID;

        EncryptCommand expectedCommand = new EncryptCommand(targetIndex, PASSWORD_1_VALID);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

}
