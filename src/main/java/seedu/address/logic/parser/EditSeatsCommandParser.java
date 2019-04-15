package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.EditSeatsCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.table.TableNumber;
import seedu.address.model.table.TableStatus;

/**
 * Parses input arguments and creates a new EditPaxCommand object
 */
public class EditSeatsCommandParser implements Parser<EditSeatsCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditPaxCommand
     * and returns an EditPaxCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditSeatsCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        String[] splitArgs = trimmedArgs.split("\\s+");
        if (trimmedArgs.isEmpty() || splitArgs.length != 2) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditSeatsCommand.MESSAGE_USAGE));
        }
        if (!TableNumber.isValidTableNumber(splitArgs[0])) {
            throw new ParseException(TableNumber.MESSAGE_CONSTRAINTS);
        }
        if (!TableStatus.isValidNumberOfSeats(splitArgs[1])) {
            throw new ParseException(String.format(TableStatus.MESSAGE_CONSTRAINTS));
        }

        return new EditSeatsCommand(new TableNumber(splitArgs[0]), splitArgs[1]);
    }
}
