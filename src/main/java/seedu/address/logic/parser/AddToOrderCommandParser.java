package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.List;

import seedu.address.logic.commands.AddToOrderCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.menu.Code;

/**
 * Parses input arguments and creates a new AddToOrderCommand object
 */
public class AddToOrderCommandParser implements Parser<AddToOrderCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddToOrderCommand
     * and returns an AddToOrderCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddToOrderCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        String[] itemDetails = trimmedArgs.split("\\s+");
        if (trimmedArgs.isEmpty() || itemDetails.length % 2 == 1) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddToOrderCommand.MESSAGE_USAGE));
        }

        List<Code> itemCodes = new ArrayList<>();
        List<Integer> itemQuantities = new ArrayList<>();
        for (int i = 0; i < itemDetails.length - 1; i += 2) {
            itemCodes.add(ParserUtil.parseCode(itemDetails[i]));
            itemQuantities.add(ParserUtil.parseQuantity(itemDetails[i + 1]));
        }

        return new AddToOrderCommand(itemCodes, itemQuantities);
    }
}
