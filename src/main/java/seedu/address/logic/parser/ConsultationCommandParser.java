package seedu.address.logic.parser;

import java.util.stream.Stream;

import seedu.address.logic.commands.ConsultationCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses the arguments to search for a patient to start consultation
 */
public class ConsultationCommandParser implements Parser<ConsultationCommand> {

    public static final Prefix PREFIX_NRIC = new Prefix("r/");

    @Override
    public ConsultationCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NRIC);

        if (!arePrefixesPresent(argMultimap, PREFIX_NRIC)) {
            throw new ParseException("Some details are left out, please retype the command");
        }

        return new ConsultationCommand(argMultimap.getValue(PREFIX_NRIC).get());
    }

    // parsing methods
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
