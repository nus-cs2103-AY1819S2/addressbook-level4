package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DEADLINE_DESC_VALID;
import static seedu.address.logic.commands.CommandTestUtil.FILE_DESC_1_PDF;
import static seedu.address.logic.commands.CommandTestUtil.PASSWORD_1_VALID;
import static seedu.address.logic.commands.CommandTestUtil.PASSWORD_DESC_VALID;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_LECTURE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PDF;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PDF;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DecryptCommand;
import seedu.address.logic.commands.DeleteCommand;

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
