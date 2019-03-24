package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.model.Model.COMPARATOR_ASCENDING_NAME_PDFS;
import static seedu.address.model.Model.COMPARATOR_DESCENDING_NAME_PDFS;
import static seedu.address.model.Model.COMPARATOR_ASCENDING_DEADLINE_PDFS;
import static seedu.address.model.Model.COMPARATOR_DESCENDING_DEADLINE_PDFS;


import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SortCommand object
 */
public class SortCommandParser implements Parser<SortCommand> {

    private static final String name_ascending = "name up";
    private static final String name_descending = "name down";
    private static final String deadline_ascending = "date up";
    private static final String deadline_descending = "date down";

    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns an SortCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortCommand parse(String args) throws ParseException {
        requireNonNull(args);
        try {
            String parseArgs = args.trim();
            if (parseArgs.equals(name_ascending)) {
                return new SortCommand(COMPARATOR_ASCENDING_NAME_PDFS);
            } else if (parseArgs.equals(name_descending)) {
                return new SortCommand(COMPARATOR_DESCENDING_NAME_PDFS);
            } else if (parseArgs.equals(deadline_ascending)) {
                return new SortCommand(COMPARATOR_ASCENDING_DEADLINE_PDFS);
            } else if (parseArgs.equals(deadline_descending)) {
                return new SortCommand(COMPARATOR_DESCENDING_DEADLINE_PDFS);
            } else {
                throw new ParseException(SortCommand.MESSAGE_USAGE);
            }
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE), pe);
        }
    }

}
