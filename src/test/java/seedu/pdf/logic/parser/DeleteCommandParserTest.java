package seedu.pdf.logic.parser;

import static seedu.pdf.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.pdf.logic.commands.CommandTestUtil.DEADLINE_DESC_VALID;
import static seedu.pdf.logic.commands.CommandTestUtil.FILE_DESC_1_PDF;
import static seedu.pdf.logic.commands.CommandTestUtil.PASSWORD_DESC_VALID;
import static seedu.pdf.logic.commands.CommandTestUtil.TAG_DESC_LECTURE;
import static seedu.pdf.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.pdf.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.pdf.testutil.TypicalIndexes.INDEX_FIRST_PDF;

import org.junit.Test;

import seedu.pdf.commons.core.index.Index;
import seedu.pdf.logic.commands.DeleteCommand;

public class DeleteCommandParserTest {

    private DeleteCommandParser parser = new DeleteCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, "1", new DeleteCommand(INDEX_FIRST_PDF));
    }

    @Test
    public void parse_invalidArguments_throwsParseException() {
        assertParseFailure(parser, "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "1 SEROCKS",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "1 hard redundantTest",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "1 " + DEADLINE_DESC_VALID + PASSWORD_DESC_VALID,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "1 " + DEADLINE_DESC_VALID + TAG_DESC_LECTURE,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "1 " + DEADLINE_DESC_VALID + FILE_DESC_1_PDF,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_PDF;

        String userInput = "" + targetIndex.getOneBased();

        DeleteCommand expectedCommand = new DeleteCommand(targetIndex);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
