package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.List;

import seedu.address.logic.commands.AddTableCommand;
import seedu.address.logic.commands.UpdateTableCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.table.TableStatus;

/**
 * Parses input arguments and creates a new UpdateTableCommand object
 */
public class UpdateTableCommandParser implements Parser<UpdateTableCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddTableCommand
     * and returns an AddTableCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UpdateTableCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        String[] numberOfSeatsInString = trimmedArgs.split("\\s+");
        if (trimmedArgs.isEmpty() || numberOfSeatsInString.length != 2) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTableCommand.MESSAGE_USAGE));
        }

        

        return new UpdateTableCommand(numberOfSeatsList);
    }
}
