/* @@author: siyingpoof */
package seedu.address.logic.parser.doctor;

import java.util.Arrays;
import java.util.stream.Stream;

import seedu.address.logic.commands.doctor.ListDoctorCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.doctor.DoctorContainsKeywordsPredicate;

/**
 * Parses the given {@code String} of arguments in the context of the ListDoctorCommand
 * and returns a ListDoctorCommand object for execution.
 * @throws ParseException if the user input does not conform the expected format
 */
public class ListDoctorCommandParser implements Parser<ListDoctorCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ListDoctorCommand
     * and returns a ListDoctorCommand object for execution.
     * @param args
     * @return
     * @throws ParseException
     */
    public ListDoctorCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            return new ListDoctorCommand();
        }

        String[] keywords = trimmedArgs.split("\\s+");

        return new ListDoctorCommand(new DoctorContainsKeywordsPredicate(Arrays.asList(keywords)));

    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
