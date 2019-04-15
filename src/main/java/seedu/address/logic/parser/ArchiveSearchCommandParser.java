package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.ArchiveSearchCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.PersonContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new ArchiveSearchCommand object
 */
public class ArchiveSearchCommandParser implements Parser<ArchiveSearchCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ArchiveSearchCommand
     * and returns an ArchiveSearchCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ArchiveSearchCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ArchiveSearchCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");
        return new ArchiveSearchCommand(new PersonContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

}
