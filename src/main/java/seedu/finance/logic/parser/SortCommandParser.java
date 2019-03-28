package seedu.finance.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.finance.logic.parser.CliSyntax.FLAG_AMOUNT;
import static seedu.finance.logic.parser.CliSyntax.FLAG_CATEGORY;
import static seedu.finance.logic.parser.CliSyntax.FLAG_DATE;
import static seedu.finance.logic.parser.CliSyntax.FLAG_NAME;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

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

        String[] words = args.split("\\s");
        List<String> list = Arrays.asList(words);

        if (list.contains(FLAG_NAME.getFlag())) {
            comparator = new RecordNameComparator();
            numFlags++;
        }
        if (list.contains(FLAG_AMOUNT.getFlag())) {
            comparator = new RecordAmountComparator();
            numFlags++;
        }
        if (list.contains(FLAG_DATE.getFlag())) {
            comparator = new RecordDateComparator();
            numFlags++;
        }
        if (list.contains(FLAG_CATEGORY.getFlag())) {
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
