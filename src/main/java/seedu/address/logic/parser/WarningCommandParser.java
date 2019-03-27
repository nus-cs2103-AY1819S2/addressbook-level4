package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.*;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

import seedu.address.commons.util.warning.WarningPanelPredicateAccessor;
import seedu.address.logic.commands.WarningCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.medicine.*;
import seedu.address.model.threshold.Threshold;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class WarningCommandParser implements Parser<WarningCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */

    Threshold threshold;
    Predicate predicate;

    public WarningCommand parse(String args) throws ParseException {
        if (isShow(args)) {
            return new WarningCommand(args.trim());
        }

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_EXPIRY, PREFIX_QUANTITY);

        if (hasOnePrefix(argMultimap)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, WarningCommand.MESSAGE_USAGE));
        }

        Optional<String> optionalExpiryThreshold = argMultimap.getValue(PREFIX_EXPIRY);
        Optional<String> optionalLowStockThreshold = argMultimap.getValue(PREFIX_QUANTITY);

        if (optionalExpiryThreshold.isPresent()) {
            threshold = ParserUtil.parseThreshold(optionalExpiryThreshold.get());
            predicate = new MedicineExpiryThresholdPredicate(threshold.getNumericValue());
        } else if (optionalLowStockThreshold.isPresent()){
            threshold = ParserUtil.parseThreshold(optionalLowStockThreshold.get());
            predicate = new MedicineLowStockThresholdPredicate(threshold.getNumericValue());
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, WarningCommand.MESSAGE_USAGE));
        }

        return new WarningCommand(predicate);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Checks if "show" in args, ignoring trailing whitespaces and case.
     * @param args
     * @return True if the argument is "show", otherwise False.
     */
    private boolean isShow(String args) {
        String trimmedArgs = args.trim();
        return trimmedArgs.equalsIgnoreCase("show");
    }

    /**
     * Check if there is only one prefix in args, using XOR.
     * @param argMultimap
     * @return True if there is only one prefix in args, False otherwise.
     */
    private boolean hasOnePrefix(ArgumentMultimap argMultimap) {
        return !(arePrefixesPresent(argMultimap, PREFIX_EXPIRY) ^ arePrefixesPresent(argMultimap, PREFIX_QUANTITY));
    }

}
