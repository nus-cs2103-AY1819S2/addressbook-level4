package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DEADLINE_DESC_VALID;
import static seedu.address.logic.commands.CommandTestUtil.FILE_DESC_1_PDF;
import static seedu.address.logic.commands.CommandTestUtil.PASSWORD_1_VALID;
import static seedu.address.logic.commands.CommandTestUtil.PASSWORD_DESC_VALID;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_CS2103T;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.EncryptCommand.MESSAGE_ENCRYPT_PDF_SUCCESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PDF;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PDF;
import static seedu.address.testutil.TypicalPdfs.SAMPLE_PDF_2;
import static seedu.address.testutil.TypicalPdfs.getTypicalPdfBook;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.DecryptCommand;
import seedu.address.logic.commands.EncryptCommand;


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

        // no password
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
