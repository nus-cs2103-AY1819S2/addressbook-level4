package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.RandomItemCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.apparel.ClothingTypeValue;

/**
 * Parses input arguments and creates a new RandomItemCommand object
 */
// @@author PhilipPhil
public class RandomItemParser implements Parser<RandomItemCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RandomItemCommand
     * and returns an RandomItemCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public RandomItemCommand parse(String userInput) throws ParseException {
        try {
            ClothingTypeValue clothingTypeValue = ParserUtil.parseClothingType(userInput).getClothingTypeValue();
            return new RandomItemCommand(clothingTypeValue);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RandomItemCommand.MESSAGE_USAGE), pe);
        }
    }
}
