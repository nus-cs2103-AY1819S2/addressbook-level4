package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.SpaceForCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.table.TableStatus;

public class SpaceForCommandParser implements Parser<SpaceForCommand> {

    public static final String MESSAGE_TOO_MANY_CUSTOMERS =
            "Number of customers is too large, none of the tables can accommodate!";

    @Override
    public SpaceForCommand parse(String userInput) throws ParseException, CommandException {
        String trimmedArgs = userInput.trim();
        String[] splitArgs = trimmedArgs.split("\\s+");

        if (trimmedArgs.isEmpty() || splitArgs.length > 1) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SpaceForCommand.MESSAGE_USAGE));
        }
        if (trimmedArgs.length() > 9) {
            throw new ParseException(MESSAGE_TOO_MANY_CUSTOMERS);
        }
        if (!TableStatus.isValidNumberOfSeats(trimmedArgs)) {
            throw new ParseException(SpaceForCommand.MESSAGE_CONSTRAINT);
        }

        return new SpaceForCommand(Integer.parseInt(trimmedArgs));
    }
}
