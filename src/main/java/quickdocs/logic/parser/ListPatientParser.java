package quickdocs.logic.parser;

import quickdocs.logic.commands.ListPatientCommand;
import quickdocs.logic.parser.exceptions.ParseException;
import quickdocs.model.tag.Tag;

/**
 * Parses arguments entered by user into a ListPatientCommand to list
 * single or a list of patient records.
 * If multiple arguments are entered, only first argument will be used to list
 */
public class ListPatientParser implements Parser<ListPatientCommand> {

    public static final String INDEX_INVALID_RANGE = "Index is beyond the valid range";
    public static final String INDEX_NUMERIC = "Index should be numeric";
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_NRIC = new Prefix("r/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");

    @Override
    public ListPatientCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_NRIC, PREFIX_TAG);

        if (!argMultimap.getPreamble().isEmpty()) {

            String userInput = argMultimap.getPreamble().trim();

            // prevent non integer input for indexes, also prevent negative values
            if (!userInput.matches("\\d+")) {
                throw new ParseException(INDEX_NUMERIC);
            }

            // prevent integer overflow, can only check up to Long's max value
            // any value beyond that will have a NumberFormatException thrown when
            // assigning value to a long variable
            if (Long.valueOf(userInput) > Integer.MAX_VALUE) {
                throw new ParseException(INDEX_INVALID_RANGE);
            }

            int index = Integer.valueOf(argMultimap.getPreamble());
            return new ListPatientCommand(index);
        }

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            // list patient by name, can get multiple records
            String name = argMultimap.getValue(PREFIX_NAME).get();
            return new ListPatientCommand(name, true);
        }

        if (argMultimap.getValue(PREFIX_NRIC).isPresent()) {
            // list patient by name, can get multiple records
            String nric = argMultimap.getValue(PREFIX_NRIC).get();
            return new ListPatientCommand(nric, false);
        }

        if (argMultimap.getValue(PREFIX_TAG).isPresent()) {
            // list all patients with the same tag
            return new ListPatientCommand(new Tag(argMultimap.getValue(PREFIX_TAG).get()));
        }

        // if nothing is supplied, will try to list all the patients, maximum 50;
        return new ListPatientCommand();
    }
}
