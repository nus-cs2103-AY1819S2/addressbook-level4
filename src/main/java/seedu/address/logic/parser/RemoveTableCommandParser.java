package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.List;

import seedu.address.logic.commands.RemoveTableCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.table.TableNumber;

public class RemoveTableCommandParser implements Parser<RemoveTableCommand> {
    @Override
    public RemoveTableCommand parse(String userInput) throws ParseException, CommandException {
        List<TableNumber> tableNumberList = new ArrayList<>();
        String[] splitArgs = userInput.trim().split("\\s+");

        if (splitArgs.length == 0) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveTableCommand.MESSAGE_USAGE));
        }

        for (String splitArg : splitArgs) {
            TableNumber tableNumber;
            try {
                tableNumber = new TableNumber(splitArg);
            } catch (IllegalArgumentException e) {
                throw new ParseException(e.getMessage());
            }
            tableNumberList.add(tableNumber);
        }

        return new RemoveTableCommand(tableNumberList);
    }
}
