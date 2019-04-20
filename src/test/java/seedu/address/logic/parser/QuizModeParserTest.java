package seedu.address.logic.parser;

import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.quiz.QuizAnswerCommand;
import seedu.address.logic.commands.quiz.QuizDifficultCommand;
import seedu.address.logic.commands.quiz.QuizHelpCommand;
import seedu.address.logic.commands.quiz.QuizHintCommand;
import seedu.address.logic.commands.quiz.QuizQuitCommand;
import seedu.address.logic.commands.quiz.QuizStatusCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.testutil.Assert;

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
    public void parseCommand() throws Exception {
        assertTrue(parser.parse("\\difficult") instanceof QuizDifficultCommand);
        assertTrue(parser.parse(QuizDifficultCommand.COMMAND_WORD) instanceof QuizDifficultCommand);

        assertTrue(parser.parse("\\help") instanceof QuizHelpCommand);
        assertTrue(parser.parse(QuizHelpCommand.COMMAND_WORD) instanceof QuizHelpCommand);

        assertTrue(parser.parse("\\hint") instanceof QuizHintCommand);
        assertTrue(parser.parse(QuizHintCommand.COMMAND_WORD) instanceof QuizHintCommand);

        assertTrue(parser.parse("\\quit") instanceof QuizQuitCommand);
        assertTrue(parser.parse(QuizQuitCommand.COMMAND_WORD) instanceof QuizQuitCommand);

        assertTrue(parser.parse("\\status") instanceof QuizStatusCommand);
        assertTrue(parser.parse(QuizStatusCommand.COMMAND_WORD) instanceof QuizStatusCommand);
    }

    @Test
    public void parse_unknownCommand_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_UNKNOWN_COMMAND);
        parser.parse("\\unknownCommand");
    }

    @Test
    public void parse_differentUnknownCommand_throwsParseException() {
        Assert.assertThrows(ParseException.class, () ->
            parser.parse("\\unknownCommand\\"));

        Assert.assertThrows(ParseException.class, () ->
            parser.parse("\\unknown command with space\\"));

        Assert.assertThrows(ParseException.class, () ->
            parser.parse("\\endsWithSpace        "));
    }
}
