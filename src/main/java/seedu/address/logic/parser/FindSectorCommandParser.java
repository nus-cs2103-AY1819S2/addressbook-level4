package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.FindSectorCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.SectorContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindSectorCommandParser implements Parser<FindSectorCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindSectorCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindSectorCommand.MESSAGE_USAGE));
        }

        String[] sectorKeywords = trimmedArgs.split("\\s+");

        return new FindSectorCommand(new SectorContainsKeywordsPredicate(Arrays.asList(sectorKeywords)));
    }

}
