package seedu.address.logic.parser;

import seedu.address.logic.commands.QuizCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new QuizCommand object.
 */
public class QuizCommandParser implements Parser<QuizCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the StatsCommand
     * and returns an QuizCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public QuizCommand parse(String args) throws ParseException {
        if (args.trim().isEmpty()) {
            return new QuizCommand();
        }

        switch (args.trim().toLowerCase()) {

        case "review":
            return new QuizCommand(false);

        case "srs":
            return new QuizCommand(true);

        default:
            throw new ParseException(QuizCommand.MESSAGE_QUIZ_FAILURE_UNKNOWN_MODE);
        }

    }
}
