package seedu.equipment.logic.parser;

import java.util.Arrays;

import seedu.equipment.logic.commands.FilterCommand;
import seedu.equipment.logic.parser.exceptions.ParseException;
import seedu.equipment.model.equipment.EquipmentContainsKeywordsPredicate;
import seedu.equipment.commons.core.Messages;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FilterCommandParser implements Parser<FilterCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FilterCommand
     * and returns an FilterCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FilterCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }

        String[] tagKeywords = trimmedArgs.split("\\s+");

        return new FilterCommand(new EquipmentContainsKeywordsPredicate(Arrays.asList(tagKeywords)));
    }

}
