package quickdocs.logic.parser;

//import java.util.stream.Stream;

import quickdocs.logic.commands.ListConsultationCommand;
import quickdocs.logic.parser.exceptions.ParseException;

/**
 * Parses argument to produce a ListConsultationCommand
 */
public class ListConsultationCommandParser implements Parser<ListConsultationCommand> {

    public static final String NO_LIST_ARGUMENTS = "Search parameters are missing for the listing of consultation\n"
            + ListConsultationCommand.MESSAGE_USAGE;
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
}
