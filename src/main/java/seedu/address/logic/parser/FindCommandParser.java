package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_YEAR;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.util.predicate.AddressContainsKeywordsPredicate;
import seedu.address.model.util.predicate.ContainsKeywordsPredicate;
import seedu.address.model.util.predicate.DateOfBirthContainsKeywordsPredicate;
import seedu.address.model.util.predicate.EmailContainsKeywordsPredicate;
import seedu.address.model.util.predicate.MultipleContainsKeywordsPredicate;
import seedu.address.model.util.predicate.NameContainsKeywordsPredicate;
import seedu.address.model.util.predicate.NricContainsKeywordsPredicate;
import seedu.address.model.util.predicate.PhoneContainsKeywordsPredicate;


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
        String trimmedArgs = args.trim();
        int prefixNum = 0;
        boolean isIgnoreCase = true;
        boolean isAnd = false;

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_NRIC,
                PREFIX_YEAR);

        String preamble = argMultimap.getPreamble().toLowerCase();
        if (!preamble.isEmpty()) {
            switch (preamble) {

            case "and cs":
            case "cs and":
                isAnd = true;
                isIgnoreCase = false;
                break;

            case "and":
                isAnd = true;
                break;

            case "cs":
                isIgnoreCase = false;
                break;

            default:
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
            }

        }

        Prefix[] prefixArr = new Prefix[] {PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
            PREFIX_NRIC, PREFIX_YEAR};

        String[] keywords = new String[1];
        ContainsKeywordsPredicate predicate;
        MultipleContainsKeywordsPredicate multiPredicate =
            new MultipleContainsKeywordsPredicate(Arrays.asList(keywords), isIgnoreCase, isAnd);

        List<ContainsKeywordsPredicate> predicateList = new ArrayList<>();

        for (Prefix pref: prefixArr) {
            if (argMultimap.getValue(pref).isPresent()) {
                keywords = argMultimap.getValue(pref).get().split("\\s+");
                predicate = getKeywordsPredicate(pref, Arrays.asList(keywords), isIgnoreCase, isAnd);
                predicateList.add(predicate);
                prefixNum++;
            }
        }

        if (prefixNum < 1) {
            throw new ParseException("Find needs at least 1 parameter for searching!");
        }
        multiPredicate.setPredicateList(predicateList);

        return new FindCommand(multiPredicate);
    }

    private static ContainsKeywordsPredicate getKeywordsPredicate(Prefix prefix, List<String> keywords,
                                                                  boolean isIgnorecase, boolean isAnd)
        throws ParseException {

        switch (prefix.getPrefix()) {
        case "n/":
            return new NameContainsKeywordsPredicate(keywords, isIgnorecase, isAnd);

        case "p/":
            return new PhoneContainsKeywordsPredicate(keywords, isIgnorecase, isAnd);

        case "a/":
            return new AddressContainsKeywordsPredicate(keywords, isIgnorecase, isAnd);

        case "e/":
            return new EmailContainsKeywordsPredicate(keywords, isIgnorecase, isAnd);

        case "ic/":
            return new NricContainsKeywordsPredicate(keywords, isIgnorecase, isAnd);

        case "dob/":
            return new DateOfBirthContainsKeywordsPredicate(keywords, isIgnorecase, isAnd);

        default:
            throw new ParseException("");
        }
    }

}
