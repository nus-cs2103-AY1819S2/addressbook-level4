package seedu.pdf.logic.parser;

import static seedu.pdf.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.pdf.logic.commands.CommandTestUtil.DEADLINE_DESC_VALID;
import static seedu.pdf.logic.commands.CommandTestUtil.FILE_DESC_1_PDF;
import static seedu.pdf.logic.commands.CommandTestUtil.NAME_DESC_1_VALID;
import static seedu.pdf.logic.commands.CommandTestUtil.PASSWORD_DESC_VALID;
import static seedu.pdf.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.pdf.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.pdf.commons.core.index.Index;
import seedu.pdf.logic.commands.MergeCommand;

public class MergeCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            MergeCommand.MESSAGE_USAGE);

    private MergeCommandParser parser = new MergeCommandParser();

    @Test
    public void parse_containsIrrelevantPrefixes_failure() {
        // redundant file prefix
        assertParseFailure(parser, FILE_DESC_1_PDF, MESSAGE_INVALID_FORMAT);

        // redundant name prefix
        assertParseFailure(parser, NAME_DESC_1_VALID, MESSAGE_INVALID_FORMAT);

        // redundant password prefix
        assertParseFailure(parser, PASSWORD_DESC_VALID, MESSAGE_INVALID_FORMAT);

        // redundant date prefix
        assertParseFailure(parser, DEADLINE_DESC_VALID, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5", MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0", MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 se/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_fieldCorrectlySpecified_success() {
        Index firstIndex = Index.fromOneBased(1);

        String userInput = "" + firstIndex.getOneBased();

        MergeCommand expectedCommand = new MergeCommand(firstIndex);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_moreThanOneIndexSpecified_success() {
        Index firstIndex = Index.fromOneBased(1);
        Index secondIndex = Index.fromOneBased(2);

        String userInput = firstIndex.getOneBased() + " " + secondIndex.getOneBased();

        MergeCommand expectedCommand = new MergeCommand(firstIndex, secondIndex);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_repeatedIndexSpecified_success() {
        Index firstIndex = Index.fromOneBased(1);
        Index secondIndex = Index.fromOneBased(1);

        String userInput = firstIndex.getOneBased() + " " + secondIndex.getOneBased();

        MergeCommand expectedCommand = new MergeCommand(firstIndex, secondIndex);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

}
