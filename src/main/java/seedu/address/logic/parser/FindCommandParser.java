package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BATCHNUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMPANY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Arrays;
import java.util.function.Predicate;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.medicine.Medicine;
import seedu.address.model.medicine.predicates.BatchContainsKeywordsPredicate;
import seedu.address.model.medicine.predicates.CompanyContainsKeywordsPredicate;
import seedu.address.model.medicine.predicates.NameContainsKeywordsPredicate;
import seedu.address.model.medicine.predicates.TagContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_COMPANY, PREFIX_TAG,
                PREFIX_BATCHNUMBER);

        // First argument must be a prefix
        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        Predicate<Medicine> predicate = null;
        String[] keywords;
        Prefix[] prefixes = {PREFIX_NAME, PREFIX_COMPANY, PREFIX_TAG, PREFIX_BATCHNUMBER};

        for (Prefix prefix: prefixes) {
            if (argMultimap.getValue(prefix).isPresent()) {
                if (predicate != null) {
                    throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
                }

                keywords = getKeywords(argMultimap, prefix);
                predicate = getPredicate(prefix, keywords);
            }
        }

        if (predicate == null) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        return new FindCommand(predicate);
    }

    /**
     * Returns the appropriate predicate depending on which prefix is present in the argument.
     * @throws ParseException if unknown prefix is entered.
     */
    private Predicate<Medicine> getPredicate(Prefix prefix, String[] keywords) throws ParseException {
        if (prefix == PREFIX_NAME) {
            return new NameContainsKeywordsPredicate(Arrays.asList(keywords));
        }

        if (prefix == PREFIX_COMPANY) {
            return new CompanyContainsKeywordsPredicate(Arrays.asList(keywords));
        }

        if (prefix == PREFIX_TAG) {
            return new TagContainsKeywordsPredicate(Arrays.asList(keywords));
        }

        if (prefix == PREFIX_BATCHNUMBER) {
            return new BatchContainsKeywordsPredicate(Arrays.asList(keywords));
        }

        throw new ParseException("Unknown prefix");
    }

    /**
     * Get keywords from argMultimap depending on which prefix is present in the argument.
     * @throws ParseException if no keywords were entered.
     */
    private String[] getKeywords(ArgumentMultimap argMultimap, Prefix prefix) throws ParseException {

        String trimmedKeywords = argMultimap.getValue(prefix).get().trim();
        if (trimmedKeywords.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        return trimmedKeywords.split("\\s+");
    }
}
