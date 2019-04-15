package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CUISINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OCCASION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE_RANGE;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.SetCategoriesCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.restaurant.categories.Categories;
import seedu.address.model.restaurant.categories.Cuisine;
import seedu.address.model.restaurant.categories.Occasion;
import seedu.address.model.restaurant.categories.PriceRange;

/**
 * Parses input arguments and creates a new SetCategoriesCommand object
 */
public class SetCategoriesCommandParser implements Parser<SetCategoriesCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SetCategoriesCommand
     * and returns a SetCategoriesCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SetCategoriesCommand parse(String userInput) throws ParseException {
        requireNonNull(userInput);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(userInput,
                PREFIX_CUISINE, PREFIX_OCCASION, PREFIX_PRICE_RANGE);
        Index index;
        Cuisine cuisine;
        Occasion occasion;
        PriceRange priceRange;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    SetCategoriesCommand.MESSAGE_USAGE), pe);
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

        if (argMultimap.getValue(PREFIX_PRICE_RANGE).isPresent()) {
            priceRange = ParserUtil.parsePriceRange(argMultimap.getValue(PREFIX_PRICE_RANGE).get());
        } else {
            priceRange = null;
        }

        if (cuisine == null && occasion == null && priceRange == null) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetCategoriesCommand.MESSAGE_USAGE));
        }
        return new SetCategoriesCommand(index, new Categories(cuisine, occasion, priceRange));
    }
}
