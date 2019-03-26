package seedu.address.logic.parser;

import static seedu.address.logic.commands.ConsultationCommand.MESSAGE_USAGE;

import java.util.stream.Stream;

import seedu.address.logic.commands.ConsultationCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses the arguments to search for a patient to start consultation
 */
public class ConsultationCommandParser implements Parser<ConsultationCommand> {

    public static final Prefix PREFIX_NRIC = new Prefix("r/");
    public static final String INVALID_CONSULTATION_ARGUMENTS = "Invalid arguments entered for consultation command.\n"
            + MESSAGE_USAGE;

    @Override
    public ConsultationCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NRIC);

        if (!arePrefixesPresent(argMultimap, PREFIX_NRIC)) {
            //throw new ParseException("Some details are left out, please retype the command");
            throw new ParseException(INVALID_CONSULTATION_ARGUMENTS);
        }

        return new ConsultationCommand(argMultimap.getValue(PREFIX_NRIC).get());
    }

    // parsing methods
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
