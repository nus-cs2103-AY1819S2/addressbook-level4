package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.AnswerCommand;
import seedu.address.model.card.Answer;

public class AnswerCommandParserTest {

    private AnswerCommandParser parser = new AnswerCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, AnswerCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        AnswerCommand expectedAnswerCommand =
                new AnswerCommand(new Answer("Golgi apparatus"));
        assertParseSuccess(parser, "Golgi apparatus ", expectedAnswerCommand);
    }

}
