package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.*;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

import seedu.address.commons.util.warning.WarningPanelPredicateAccessor;
import seedu.address.logic.commands.WarningCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.medicine.Batch;
import seedu.address.model.medicine.BatchNumber;
import seedu.address.model.medicine.Company;
import seedu.address.model.medicine.Expiry;
import seedu.address.model.medicine.Medicine;
import seedu.address.model.medicine.Name;
import seedu.address.model.medicine.Quantity;
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
    public WarningCommand parse(String args) throws ParseException {
        if (isShow(args)) {
            return new WarningCommand(args.trim());
        }

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_EXPIRY, PREFIX_QUANTITY);

        if ((arePrefixesPresent(argMultimap, PREFIX_EXPIRY))
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, WarningCommand.MESSAGE_USAGE));
        }

        Threshold expiryThreshold = ParserUtil.parseThreshold(argMultimap.getValue(PREFIX_EXPIRY).get());
        Threshold lowStockThreshold = ParserUtil.parseThreshold(argMultimap.getValue(PREFIX_QUANTITY).get());

//        Predicate predicate = new Predicate();

        return new WarningCommand(something -> true);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    private boolean isShow(String args) {
        String trimmedArgs = args.trim();
        return trimmedArgs.equalsIgnoreCase("show");
    }

}
