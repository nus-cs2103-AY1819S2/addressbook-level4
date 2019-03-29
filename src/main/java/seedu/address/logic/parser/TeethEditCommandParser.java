package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;

import seedu.address.logic.commands.TeethEditCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.patient.Teeth;
import seedu.address.model.patient.Tooth;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class TeethEditCommandParser implements Parser<TeethEditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public TeethEditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_STATUS);

        int toothNumber;
        int status;

        try {
            toothNumber = Integer.parseInt(argMultimap.getPreamble());
        } catch (NumberFormatException e) {
            throw new ParseException(TeethEditCommand.MESSAGE_USAGE);
        }

        // Test if tooth number is valid.
        if (!Teeth.isValidTooth(toothNumber)) {
            throw new ParseException(TeethEditCommand.BAD_RANGE);
        }

        if (argMultimap.getValue(PREFIX_STATUS).isPresent()) {
            status = Integer.parseInt(argMultimap.getValue(PREFIX_STATUS).get());
        } else {
            throw new ParseException(TeethEditCommand.MESSAGE_NOT_EDITED);
        }

        // Test if status is valid.
        if (!Tooth.isValidStatus(status)) {
            throw new ParseException(TeethEditCommand.BAD_STATUS);
        }

        return new TeethEditCommand(toothNumber, status);
    }

}
