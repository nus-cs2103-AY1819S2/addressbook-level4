package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CUISINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OCCASION;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.SetCategoryCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.restaurant.categories.Category;
import seedu.address.model.restaurant.categories.Cuisine;
import seedu.address.model.restaurant.categories.Occasion;

/**
 * Parses input arguments and creates a new SetCategoryCommand object
 */
public class CategoryCommandParser implements Parser<SetCategoryCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SetCategoryCommand
     * and returns a SetCategoryCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SetCategoryCommand parse(String userInput) throws ParseException {
        requireNonNull(userInput);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(userInput, PREFIX_CUISINE, PREFIX_OCCASION);
        Index index;
        Cuisine cuisine;
        Occasion occasion;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    SetCategoryCommand.MESSAGE_USAGE), pe);
        }

        if (argMultimap.getValue(PREFIX_CUISINE).isPresent()) {
            cuisine = ParserUtil.parseCuisine(argMultimap.getValue(PREFIX_CUISINE).get());
        } else {
            cuisine = null;
        }

        if (argMultimap.getValue(PREFIX_OCCASION).isPresent()) {
            occasion = ParserUtil.parseOccasion(argMultimap.getValue(PREFIX_OCCASION).get());
        } else {
            occasion = null;
        }

        if (cuisine == null && occasion == null) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetCategoryCommand.MESSAGE_USAGE));
        }
        return new SetCategoryCommand(index, new Category(cuisine, occasion));
    }
}
