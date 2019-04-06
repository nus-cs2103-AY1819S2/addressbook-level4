package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FRISTORDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SECONDORDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SORTTYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_THIRDORDER;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import seedu.address.logic.commands.SortBookCommand;
import seedu.address.logic.parser.exceptions.ParseException;


/**
 * Parses input arguments and creates a new SortBookCommand object
 */
public class SortBookCommandParser implements Parser<SortBookCommand> {

    public static final String ASCENDING = "asc";
    public static final String DESCENDING = "des";
    public static final String AUTHOR = "author";
    public static final String BOOKNAME = "name";
    public static final String RATING = "rating";
    private static final int FIRST = 0;
    private static final int SECOND = 1;
    private static final int THIRD = 2;
    private static Map<String, String> subOrder;
    private static List<String> sortTypeSet;
    /**
     * Parses the given {@code String} of arguments in the context of the SortBookCommand
     * and returns an SortBookCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortBookCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_SORTTYPE,
            PREFIX_ORDER, PREFIX_FRISTORDER, PREFIX_SECONDORDER, PREFIX_THIRDORDER);

        subOrder = new HashMap<>();
        sortTypeSet = new ArrayList<>();

        if (!argMultimap.getValue(PREFIX_SORTTYPE).isPresent()
            || !argMultimap.getPreamble().isEmpty()
            || !isValidSortType(argMultimap.getAllValues(PREFIX_SORTTYPE))
            || !isValidSortOrder(argMultimap.getValue(PREFIX_ORDER).isPresent()
                ? argMultimap.getValue(PREFIX_ORDER).get()
                : null)
            || !isValidSortOrder(argMultimap.getValue(PREFIX_FRISTORDER).isPresent()
                ? argMultimap.getValue(PREFIX_FRISTORDER).get()
                : null)
            || !isValidSortOrder(argMultimap.getValue(PREFIX_SECONDORDER).isPresent()
                ? argMultimap.getValue(PREFIX_SECONDORDER).get()
                : null)
            || !isValidSortOrder(argMultimap.getValue(PREFIX_THIRDORDER).isPresent()
                ? argMultimap.getValue(PREFIX_THIRDORDER).get()
                : null)
            || !isTypeAndSubOrderMatch(argMultimap)) {

            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortBookCommand.MESSAGE_USAGE));
        }

        String mainOrder = null;
        if (argMultimap.getValue(PREFIX_ORDER).isPresent()) {
            mainOrder = argMultimap.getValue(PREFIX_ORDER).get();
        }

        return new SortBookCommand(sortTypeSet, mainOrder, subOrder);
    }

    /**
     * Check whether sub order is more than current sorting types.
     * @param argMultimap input formatter
     * @return true if is it valid sub order, otherwise false.
     */
    private static Boolean isTypeAndSubOrderMatch(ArgumentMultimap argMultimap) {

        if (argMultimap.getValue(PREFIX_FRISTORDER).isPresent()) {
            subOrder.put(sortTypeSet.get(FIRST).toLowerCase(), argMultimap.getValue(PREFIX_FRISTORDER).get());
        }
        if (argMultimap.getValue(PREFIX_SECONDORDER).isPresent()) {
            if (sortTypeSet.size() < 2) {
                return false;
            }
            subOrder.put(sortTypeSet.get(SECOND).toLowerCase(), argMultimap.getValue(PREFIX_SECONDORDER).get());
        }
        if (argMultimap.getValue(PREFIX_THIRDORDER).isPresent()) {
            if (sortTypeSet.size() < 3) {
                return false;
            }
            subOrder.put(sortTypeSet.get(THIRD).toLowerCase(), argMultimap.getValue(PREFIX_THIRDORDER).get());
        }
        return true;
    }

    /**
     * Checks the sort types are valid and assign to sortTypeSet if it is.
     * @param sortTypes list of attributes types
     * @return true if sort types are valid, otherwise return false
     */
    private static boolean isValidSortType(List<String> sortTypes) {
        for (String type : sortTypes) {
            if ((!type.equalsIgnoreCase(AUTHOR)
                && !type.equalsIgnoreCase(BOOKNAME)
                && !type.equalsIgnoreCase(RATING))
                || sortTypeSet.contains(type)) {
                return false;
            }
            sortTypeSet.add(type.toLowerCase());
        }
        return true;
    }

    private static boolean isValidSortOrder(String order) {
        return order == null
            || order.equalsIgnoreCase(ASCENDING)
            || order.equalsIgnoreCase(DESCENDING);
    }
}
