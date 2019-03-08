package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.commands.QuizAnswerCommand;
import seedu.address.logic.commands.QuizCommand;

/**
 * Parses user input in QuizMode
 */
public class QuizModeParser {

    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\\\\\w*)|(?<answer>^\\S.+)");
    private static Matcher matcher;

    /**
     * Checks if userInput is valid
     * @param userInput
     * @return
     * @throws ParseException
     */
    public QuizCommand parse(String userInput) throws ParseException {
        matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            // todo change to quiz help not mgmt help command
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }


        final String commandWord = matcher.group("commandWord");
        final String answer = matcher.group("answer");

        if (commandWord != null) {
            return parseCommand(commandWord);
        } else {
            return parseAnswer(answer);
        }
    }

    private QuizCommand parseAnswer(String answer) {
        return new QuizAnswerCommand(answer);
    }

    /**
     * Parses user input into command for execution.
     *
     * @param command full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    private QuizCommand parseCommand(String command) throws ParseException {
        final String commandWord = matcher.group("commandWord");

        switch (commandWord) {
        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }
}
