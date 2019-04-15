package seedu.equipment.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.equipment.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.equipment.logic.parser.CliSyntax.DATE_SORT_PARAMETER;
import static seedu.equipment.logic.parser.CliSyntax.NAME_SORT_PARAMETER;
import static seedu.equipment.logic.parser.CliSyntax.PHONE_SORT_PARAMETER;
import static seedu.equipment.logic.parser.CliSyntax.SERIAL_SORT_PARAMETER;

import java.util.Comparator;

import seedu.equipment.logic.commands.SortCommand;
import seedu.equipment.logic.parser.exceptions.ParseException;
import seedu.equipment.model.equipment.Equipment;


/**
 * Parses input arguments and creates a new SortCommand object
 */
public class SortCommandParser implements Parser<SortCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns a SortCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortCommand parse(String args) throws ParseException {
        requireNonNull(args);
        Comparator<Equipment> comparator;
        String[] keywords = args.trim().split("\\s+");

        if (keywords.length == 0 || !isValidParameter(keywords[0])) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        switch(keywords[0]) {
        case "name":
            comparator = new NameComparator();
            break;
        case "date":
            comparator = new DateComparator();
            break;
        case "phone":
            comparator = new PhoneComparator();
            break;
        case "serial":
            comparator = new SerialNumberComparator();
            break;
        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }
        return new SortCommand(comparator);
    }
    /**
     * Checks if {@code String} flag is a valid one (name, date, serial number, phone)
     * @param keyword first argument after sort command keyword
     * @return true if flag is valid.
     */
    private static boolean isValidParameter(String keyword) {
        return keyword.equals(NAME_SORT_PARAMETER)
                || keyword.equals(PHONE_SORT_PARAMETER)
                || keyword.equals(DATE_SORT_PARAMETER)
                || keyword.equals(SERIAL_SORT_PARAMETER);
    }
}
