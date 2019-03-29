package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.quiz.QuizAnswerCommand;
import seedu.address.logic.commands.quiz.QuizDifficultCommand;
import seedu.address.logic.commands.quiz.QuizHelpCommand;
import seedu.address.logic.commands.quiz.QuizQuitCommand;
import seedu.address.logic.commands.quiz.QuizStatusCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input in QuizMode
 */
public class QuizModeParser implements Parser<Command> {

    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\\\\\w*)|(?<answer>.*)");
    private static Matcher matcher;

    /**
     * Checks if userInput is valid
     * @param userInput from commandBox
     * @return the correct parser
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parse(String userInput) throws ParseException {
        matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        matcher.matches();

        final String commandWord = matcher.group("commandWord");
        final String answer = matcher.group("answer");

        if (commandWord != null) {
            return parseCommand(commandWord);
        } else {
            return parseAnswer(answer);
        }
    }

    private Command parseAnswer(String answer) {
        return new QuizAnswerCommand(answer);
    }

    /**
     * Parses user input into command for execution.
     *
     * @param commandWord full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    private Command parseCommand(String commandWord) throws ParseException {
        switch (commandWord) {
        case QuizDifficultCommand.COMMAND_WORD:
            return new QuizDifficultCommand();
        case QuizHelpCommand.COMMAND_WORD:
            return new QuizHelpCommand();
        case QuizQuitCommand.COMMAND_WORD:
            return new QuizQuitCommand();
        case QuizStatusCommand.COMMAND_WORD:
            return new QuizStatusCommand();
        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }
}
