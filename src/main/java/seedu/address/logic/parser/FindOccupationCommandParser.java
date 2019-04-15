package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.FindOccupationCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.OccupationContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindOccupationCommandParser implements Parser<FindOccupationCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindOccupationCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindOccupationCommand.MESSAGE_USAGE));
        }

        String[] occupationKeywords = trimmedArgs.split("\\s+");

        return new FindOccupationCommand(new OccupationContainsKeywordsPredicate(Arrays.asList(occupationKeywords)));
    }

}
