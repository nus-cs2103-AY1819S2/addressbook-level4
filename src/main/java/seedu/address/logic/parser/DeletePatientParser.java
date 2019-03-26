package seedu.address.logic.parser;

import java.util.stream.Stream;

import seedu.address.logic.commands.DeletePatientCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.patient.Nric;

/**
 * Parses NRIC and command into a delete patient command to be executed
 */
public class DeletePatientParser implements Parser<DeletePatientCommand> {


    public static final Prefix PREFIX_NRIC = new Prefix("r/");
    public static final String INVALID_DELETE_ARGUMENT = "Invalid input parameters entered.\n"
            + DeletePatientCommand.MESSAGE_USAGE;


    @Override
    public DeletePatientCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NRIC);

        boolean prefixesPresent = arePrefixesPresent(argMultimap,
                PREFIX_NRIC);
        //boolean preamblePresent = argMultimap.getPreamble().isEmpty();

        if (!prefixesPresent) {
            throw new ParseException(INVALID_DELETE_ARGUMENT);
        }

        Nric toDelete = new Nric(argMultimap.getValue(PREFIX_NRIC).get());

        return new DeletePatientCommand(toDelete);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
