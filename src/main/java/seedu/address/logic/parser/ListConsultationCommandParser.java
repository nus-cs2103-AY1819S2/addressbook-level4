package seedu.address.logic.parser;

import static seedu.address.logic.commands.ListConsultationCommand.MESSAGE_USAGE;

//import java.util.stream.Stream;

import seedu.address.logic.commands.ListConsultationCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses argument to produce a ListConsultationCommand
 */
public class ListConsultationCommandParser implements Parser<ListConsultationCommand> {

    public static final String NO_LIST_ARGUMENTS = "Search parameters are missing for the listing of consultation\n"
            + MESSAGE_USAGE;
    public static final String INVALID_INDEX = "Index should be numeric";
    public static final Prefix PREFIX_NRIC = new Prefix("r/");

    @Override
    public ListConsultationCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NRIC);

        if (argMultimap.getPreamble().isEmpty() && !argMultimap.getValue(PREFIX_NRIC).isPresent()) {
            throw new ParseException(NO_LIST_ARGUMENTS);
        }

        if (!argMultimap.getPreamble().isEmpty()) {
            if (!argMultimap.getPreamble().trim().matches("\\d+")) {
                throw new ParseException(INVALID_INDEX);
            }

            int index = Integer.valueOf(argMultimap.getPreamble());
            return new ListConsultationCommand(index);
        }

        String nric = argMultimap.getValue(PREFIX_NRIC).get();
        return new ListConsultationCommand(nric);

    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     *//*
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }*/
}
