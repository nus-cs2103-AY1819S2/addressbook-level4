package seedu.equipment.logic.parser;

import static seedu.equipment.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.equipment.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.equipment.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.equipment.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.equipment.logic.parser.CliSyntax.PREFIX_PM;
import static seedu.equipment.logic.parser.CliSyntax.PREFIX_SERIALNUMBER;
import static seedu.equipment.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;

import seedu.equipment.logic.commands.FilterCommand;
import seedu.equipment.logic.parser.exceptions.ParseException;
import seedu.equipment.model.equipment.EquipmentContainsKeywordsPredicate;

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
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }
        if (!argsContainsPrefixes(trimmedArgs)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_ADDRESS, PREFIX_PM, PREFIX_PHONE,
                        PREFIX_TAG, PREFIX_SERIALNUMBER);

        List<String> addressKeywords = argMultimap.getAllValues(PREFIX_ADDRESS);
        List<String> nameKeywords = argMultimap.getAllValues(PREFIX_NAME);
        List<String> dateKeywords = argMultimap.getAllValues(PREFIX_PM);
        List<String> phoneKeywords = argMultimap.getAllValues(PREFIX_PHONE);
        List<String> tagKeywords = argMultimap.getAllValues(PREFIX_TAG);
        List<String> serialNumberKeywords = argMultimap.getAllValues(PREFIX_SERIALNUMBER);

        return new FilterCommand(new EquipmentContainsKeywordsPredicate(nameKeywords, addressKeywords, dateKeywords,
                phoneKeywords, tagKeywords, serialNumberKeywords));
    }

    /**
     * Check if arguments contain the following prefixes.
     * @param args
     * @return true if arguments contains prefixes, false otherwise.
     */
    private boolean argsContainsPrefixes(String args) {
        return args.contains(PREFIX_NAME.toString())
                || args.contains(PREFIX_ADDRESS.toString())
                || args.contains(PREFIX_NAME.toString())
                || args.contains(PREFIX_PM.toString())
                || args.contains(PREFIX_PHONE.toString())
                || args.contains(PREFIX_TAG.toString())
                || args.contains(PREFIX_SERIALNUMBER.toString());
    }

}
