package seedu.finance.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.finance.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.finance.logic.parser.CliSyntax.COMMAND_FLAG_AMOUNT;
import static seedu.finance.logic.parser.CliSyntax.COMMAND_FLAG_ASCENDING;
import static seedu.finance.logic.parser.CliSyntax.COMMAND_FLAG_CATEGORY;
import static seedu.finance.logic.parser.CliSyntax.COMMAND_FLAG_DATE;
import static seedu.finance.logic.parser.CliSyntax.COMMAND_FLAG_DESCENDING;
import static seedu.finance.logic.parser.CliSyntax.COMMAND_FLAG_NAME;

import java.util.Comparator;

import seedu.finance.logic.commands.SortCommand;
import seedu.finance.logic.parser.comparator.RecordAmountComparator;
import seedu.finance.logic.parser.comparator.RecordCategoryComparator;
import seedu.finance.logic.parser.comparator.RecordDateComparator;
import seedu.finance.logic.parser.comparator.RecordNameComparator;
import seedu.finance.logic.parser.exceptions.ParseException;
import seedu.finance.model.record.Record;

/**
 * Parses input arguments and creates a new SortCommand object.
 */
public class SortCommandParser implements Parser<SortCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns a SortCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format.
     */
    public SortCommand parse(String args) throws ParseException {
        requireNonNull(args);
        Comparator<Record> comparator = null;

        String[] words = args.trim().split("\\s+");

        if (words.length == 0 || words.length > 2 || !isValidCommandFlag(words[0])) {
            // no arguments supplied or too many arguments supplied or invalid command flag
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        boolean orderArgPresent = false;

        // if ORDER is supplied, check whether valid
        if (words.length != 1) {
            orderArgPresent = true;
            if (!isValidOrder(words[1])) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
            }
        }

        // Check which comparison function to use, flag and order supplied will be valid
        switch (words[0]) {
        case "-name":
            // no order supplied, or order supplied is -asc
            if (!orderArgPresent || words[1].equals(COMMAND_FLAG_ASCENDING.getFlag())) {
                comparator = new RecordNameComparator();
            } else {
                // order supplied is -desc
                comparator = new RecordNameComparator().reversed();
            }
            break;

        case "-amount":
            // no order supplied, or order supplied is -desc
            if (!orderArgPresent || words[1].equals(COMMAND_FLAG_DESCENDING.getFlag())) {
                comparator = new RecordAmountComparator();
            } else {
                // order supplied is -asc
                comparator = new RecordAmountComparator().reversed();
            }
            break;

        case "-date":
            // no order supplied, or order supplied is -desc
            if (!orderArgPresent || words[1].equals(COMMAND_FLAG_DESCENDING.getFlag())) {
                comparator = new RecordDateComparator();
            } else {
                // order supplied is -asc
                comparator = new RecordDateComparator().reversed();
            }
            break;

        case "-cat":
            // no order supplied, or order supplied is -asc
            if (!orderArgPresent || words[1].equals(COMMAND_FLAG_ASCENDING.getFlag())) {
                comparator = new RecordCategoryComparator();
            } else {
                // order supplied is -desc
                comparator = new RecordCategoryComparator().reversed();
            }
            break;

        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));

        }

        return new SortCommand(comparator);


    }

    /**
     * Checks if {@code String} flag is a valid one (-name, -amount, -date, -cat)
     * @param flag first argument after sort command word
     * @return true if flag is valid.
     */
    private static boolean isValidCommandFlag(String flag) {
        return flag.equals(COMMAND_FLAG_NAME.getFlag()) || flag.equals(COMMAND_FLAG_AMOUNT.getFlag())
                || flag.equals(COMMAND_FLAG_DATE.getFlag()) || flag.equals(COMMAND_FLAG_CATEGORY.getFlag());
    }

    /**
     * Checks if {@code String} order is a valid one (-asc, -desc)
     * @param order second argument after sort command word
     * @return true if order is valid
     */
    private static boolean isValidOrder(String order) {
        return order.equals(COMMAND_FLAG_ASCENDING.getFlag()) || order.equals(COMMAND_FLAG_DESCENDING.getFlag());
    }

}
