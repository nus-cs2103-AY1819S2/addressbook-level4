package seedu.finance.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.finance.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.finance.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.finance.logic.parser.CliSyntax.PREFIX_CATEGORY;

import java.util.stream.Stream;

import seedu.finance.logic.commands.AllocateCommand;
import seedu.finance.logic.parser.exceptions.ParseException;
import seedu.finance.model.budget.CategoryBudget;
import seedu.finance.model.category.Category;
import seedu.finance.model.record.Amount;

/**
 * Parses input arguments and creates a new {@code AllocateCommand} object
 */
public class AllocateCommandParser implements Parser<AllocateCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the {@code AllocateCommand}
     * and returns a {@code AllocateCommand} object for execution
     * @throws ParseException if the user input does not conform the expected format
     */
    public AllocateCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_AMOUNT, PREFIX_CATEGORY);

        if (!arePrefixesPresent(argMultimap, PREFIX_AMOUNT, PREFIX_CATEGORY)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AllocateCommand.MESSAGE_USAGE));
        }

        Amount amount = ParserUtil.parseAmount(argMultimap.getValue(PREFIX_AMOUNT).get());
        Category category = ParserUtil.parseCategory(argMultimap.getValue(PREFIX_CATEGORY).get());

        return new AllocateCommand(new CategoryBudget(category.categoryName, amount.getValue()));
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
