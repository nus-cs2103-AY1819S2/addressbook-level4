package seedu.address.logic.parser;

import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.management.HelpCommand;
import seedu.address.logic.commands.quiz.QuizAnswerCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parse user input in QuizMode
 */
public class QuizModeParserTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final QuizModeParser parser = new QuizModeParser();

    @Test
    public void parseAnswer() throws Exception {
        assertTrue(parser.parse("some answer") instanceof QuizAnswerCommand);
        assertTrue(parser.parse("someanswer") instanceof QuizAnswerCommand);
    }

    @Test
    public void parse_unrecognisedInput_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        parser.parse("");
    }

    @Test
    public void parse_onlyWhitespace_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        parser.parse("   ");
    }

    @Test
    public void parse_unknownCommand_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_UNKNOWN_COMMAND);
        parser.parse("\\unknownCommand");
    }
}
