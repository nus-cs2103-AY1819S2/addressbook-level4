package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.AttackCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.cell.Coordinates;

/**
 * Parses attack commands.
 */
public class AttackCommandParser implements Parser<AttackCommand> {
    @Override
    public AttackCommand parse(String userInput) throws ParseException {
        String uInput = userInput.trim();
        if (Coordinates.isValidCoordinates(uInput)) {
            return new AttackCommand(new Coordinates(uInput));
        } else {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AttackCommand.MESSAGE_USAGE));
        }
    }
}
