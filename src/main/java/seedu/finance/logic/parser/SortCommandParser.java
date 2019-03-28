package seedu.finance.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.finance.logic.parser.CliSyntax.FLAG_AMOUNT;
import static seedu.finance.logic.parser.CliSyntax.FLAG_CATEGORY;
import static seedu.finance.logic.parser.CliSyntax.FLAG_DATE;
import static seedu.finance.logic.parser.CliSyntax.FLAG_NAME;

import java.util.Comparator;

import seedu.finance.logic.commands.SortCommand;
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
        int numFlags = 0;

        Comparator<Record> comparator = null;

        if (args.contains(" " + FLAG_NAME)) {
            comparator = new RecordNameComparator();
            numFlags++;
        }
        if (args.contains(" " + FLAG_AMOUNT)) {
            comparator = new RecordAmountComparator();
            numFlags++;
        }
        if (args.contains(" " + FLAG_DATE)) {
            comparator = new RecordDateComparator();
            numFlags++;
        }
        if (args.contains(" " + FLAG_CATEGORY)) {
            comparator = new RecordCategoryComparator();
            numFlags++;
        }

        if (comparator == null) { // no flags provided
            throw new ParseException(SortCommand.MESSAGE_USAGE);
        }

        else if (numFlags > 1) {
            throw new ParseException(SortCommand.MESSAGE_NOT_SORTED);
        }

        return new SortCommand(comparator);
    }

}
