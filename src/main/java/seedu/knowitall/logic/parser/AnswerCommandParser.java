package seedu.knowitall.logic.parser;

import static seedu.knowitall.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.knowitall.logic.commands.AnswerCommand;
import seedu.knowitall.logic.parser.exceptions.ParseException;
import seedu.knowitall.model.card.Answer;

/**
 * Parses input arguments and creates a new AnswerCommand object
 */
public class AnswerCommandParser implements Parser<AnswerCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AnswerCommand
     * and returns an AnswerCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AnswerCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AnswerCommand.MESSAGE_USAGE));
        }

        return new AnswerCommand(new Answer(trimmedArgs));
    }

}
