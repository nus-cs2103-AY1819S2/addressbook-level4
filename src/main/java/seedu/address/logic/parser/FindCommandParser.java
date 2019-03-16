package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_YEAR;

import java.util.Arrays;
import java.util.List;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.util.predicate.AddressContainsKeywordsPredicate;
import seedu.address.model.util.predicate.ContainsKeywordsPredicate;
import seedu.address.model.util.predicate.DateOfBirthContainsKeywordsPredicate;
import seedu.address.model.util.predicate.EmailContainsKeywordsPredicate;
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

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_NRIC,
                PREFIX_YEAR);

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        Prefix[] prefixArr = new Prefix[] {PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
            PREFIX_NRIC, PREFIX_YEAR};

        String[] keywords = new String[1];
        ContainsKeywordsPredicate predicate = new PhoneContainsKeywordsPredicate(Arrays.asList(keywords));

        for (Prefix pref: prefixArr) {
            if (argMultimap.getValue(pref).isPresent()) {
                keywords = argMultimap.getValue(pref).get().split("\\s+");
                predicate = getKeywordsPredicate(pref, Arrays.asList(keywords));
                prefixNum++;
            }
        }

        if (prefixNum != 1) {
            throw new ParseException("Find only accepts 1 parameter searching!");
        }

        //TODO: Check which parameter is used.
        return new FindCommand(predicate);
    }

    private static ContainsKeywordsPredicate getKeywordsPredicate(Prefix prefix, List<String> keywords)
        throws ParseException {

        switch (prefix.getPrefix()) {
        case "n/":
            return new NameContainsKeywordsPredicate(keywords);

        case "p/":
            return new PhoneContainsKeywordsPredicate(keywords);

        case "a/":
            return new AddressContainsKeywordsPredicate(keywords);

        case "e/":
            return new EmailContainsKeywordsPredicate(keywords);

        case "ic/":
            return new NricContainsKeywordsPredicate(keywords);

        case "dob/":
            return new DateOfBirthContainsKeywordsPredicate(keywords);

        default:
            throw new ParseException("");
        }
    }

}
