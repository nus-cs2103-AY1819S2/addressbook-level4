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
        if (Coordinates.isValidCoordinates(userInput)) {
            return new AttackCommand(new Coordinates(userInput));
        } else {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AttackCommand.MESSAGE_USAGE));
        }
    }
}
