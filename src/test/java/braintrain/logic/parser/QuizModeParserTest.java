package braintrain.logic.parser;

import static braintrain.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static braintrain.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import braintrain.logic.commands.HelpCommand;
import braintrain.logic.parser.exceptions.ParseException;
import braintrain.quiz.commands.AnswerCommand;

/**
 * Parse user input in QuizMode
 */
public class QuizModeParserTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final QuizModeParser parser = new QuizModeParser();

    @Test
    public void parseAnswer() throws Exception {
        assertTrue(parser.parse("some answer") instanceof AnswerCommand);
        assertTrue(parser.parse("someanswer") instanceof AnswerCommand);
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
