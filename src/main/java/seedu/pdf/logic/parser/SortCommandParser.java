package seedu.pdf.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.pdf.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.pdf.model.Model.COMPARATOR_DEADLINE_ASCENDING_PDFS;
import static seedu.pdf.model.Model.COMPARATOR_DEADLINE_DESCENDING_PDFS;
import static seedu.pdf.model.Model.COMPARATOR_NAME_ASCENDING_PDFS;
import static seedu.pdf.model.Model.COMPARATOR_NAME_DESCENDING_PDFS;
import static seedu.pdf.model.Model.COMPARATOR_SIZE_ASCENDING_PDFS;
import static seedu.pdf.model.Model.COMPARATOR_SIZE_DESCENDING_PDFS;

import java.util.Comparator;
import java.util.HashMap;

import seedu.pdf.logic.commands.SortCommand;
import seedu.pdf.logic.parser.exceptions.ParseException;
import seedu.pdf.model.pdf.Pdf;

/**
 * Parses input arguments and creates a new SortCommand object
 */
public class SortCommandParser implements Parser<SortCommand> {

    private static final String name_ascending = "name up";
    private static final String name_descending = "name down";
    private static final String deadline_ascending = "deadline up";
    private static final String deadline_descending = "deadline down";
    private static final String size_ascending = "size up";
    private static final String size_descending = "size down";

    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns an SortCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortCommand parse(String args) throws ParseException {
        requireNonNull(args);

        try {
            String parseArgs = args.trim();
            HashMap<String, Comparator<Pdf>> sortCriteria = getSortCriteriaMap();
            Comparator<Pdf> targetComparator = sortCriteria.get(parseArgs);
            if (targetComparator == null) {
                throw new ParseException(SortCommand.MESSAGE_USAGE);
            }
            return new SortCommand(targetComparator);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE), pe);
        }
    }

    /**
     * @return a hash map of String-Comparator pair
     */
    private HashMap<String, Comparator<Pdf>> getSortCriteriaMap() {
        HashMap<String, Comparator<Pdf>> sortCriteriaMap = new HashMap<>();
        sortCriteriaMap.put(name_ascending, COMPARATOR_NAME_ASCENDING_PDFS);
        sortCriteriaMap.put(name_descending, COMPARATOR_NAME_DESCENDING_PDFS);
        sortCriteriaMap.put(deadline_ascending, COMPARATOR_DEADLINE_ASCENDING_PDFS);
        sortCriteriaMap.put(deadline_descending, COMPARATOR_DEADLINE_DESCENDING_PDFS);
        sortCriteriaMap.put(size_ascending, COMPARATOR_SIZE_ASCENDING_PDFS);
        sortCriteriaMap.put(size_descending, COMPARATOR_SIZE_DESCENDING_PDFS);
        return sortCriteriaMap;
    }

}
