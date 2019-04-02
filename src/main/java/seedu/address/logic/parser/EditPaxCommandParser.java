package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.EditPaxCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.table.TableNumber;
import seedu.address.model.table.TableStatus;

/**
 * Parses input arguments and creates a new EditPaxCommand object
 */
public class EditPaxCommandParser implements Parser<EditPaxCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditPaxCommand
     * and returns an EditPaxCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditPaxCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        String[] splitArgs = trimmedArgs.split("\\s+");
        if (trimmedArgs.isEmpty() || splitArgs.length != 2 || !TableStatus.isValidNumberOfSeats(splitArgs[1])
                || !TableNumber.isValidTableNumber(splitArgs[0])) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditPaxCommand.MESSAGE_USAGE));
        }

        return new EditPaxCommand(splitArgs);
    }
}
