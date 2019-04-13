package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_EMPTY_KEYWORD;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_NO_SEARCH_PARAMETER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DRUG_ALLERGY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOKA;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOKN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOKP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOKR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_YEAR;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.logic.commands.PatientFindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.patient.Patient;
import seedu.address.model.util.predicate.AddressContainsKeywordsPredicate;
import seedu.address.model.util.predicate.ContainsKeywordsPredicate;
import seedu.address.model.util.predicate.DateOfBirthContainsKeywordsPredicate;
import seedu.address.model.util.predicate.DescriptionContainsKeywordsPredicate;
import seedu.address.model.util.predicate.DrugAllergyContainsKeywordsPredicate;
import seedu.address.model.util.predicate.EmailContainsKeywordsPredicate;
import seedu.address.model.util.predicate.KinAddressContainsKeywordsPredicate;
import seedu.address.model.util.predicate.KinNameContainsKeywordsPredicate;
import seedu.address.model.util.predicate.KinPhoneContainsKeywordsPredicate;
import seedu.address.model.util.predicate.KinRelationContainsKeywordsPredicate;
import seedu.address.model.util.predicate.MultipleContainsKeywordsPredicate;
import seedu.address.model.util.predicate.NameContainsKeywordsPredicate;
import seedu.address.model.util.predicate.NricContainsKeywordsPredicate;
import seedu.address.model.util.predicate.PhoneContainsKeywordsPredicate;
import seedu.address.model.util.predicate.SexContainsKeywordsPredicate;
import seedu.address.model.util.predicate.TagsContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new PatientFindCommand object
 */
public class PatientFindCommandParser implements Parser<PatientFindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the PatientFindCommand
     * and returns an PatientFindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public PatientFindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        int prefixNum = 0;
        boolean isIgnoreCase = true;
        boolean isAnd = false;

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, PatientFindCommand.MESSAGE_USAGE));
        }

        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                PREFIX_NRIC, PREFIX_YEAR, PREFIX_SEX, PREFIX_DRUG_ALLERGY, PREFIX_NOKN, PREFIX_NOKR,
                PREFIX_NOKP, PREFIX_NOKA, PREFIX_DESC, PREFIX_TAG);

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
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                                            PatientFindCommand.MESSAGE_USAGE));
            }

        }

        Prefix[] prefixArr = new Prefix[] {PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
            PREFIX_NRIC, PREFIX_YEAR, PREFIX_SEX, PREFIX_DRUG_ALLERGY, PREFIX_NOKN, PREFIX_NOKR,
            PREFIX_NOKP, PREFIX_NOKA, PREFIX_DESC, PREFIX_TAG};

        String[] keywords = new String[1];
        ContainsKeywordsPredicate predicate;
        MultipleContainsKeywordsPredicate<Patient> multiPredicate =
            new MultipleContainsKeywordsPredicate<>(Arrays.asList(keywords), isIgnoreCase, isAnd);

        List<ContainsKeywordsPredicate> predicateList = new ArrayList<>();

        for (Prefix pref: prefixArr) {
            if (argMultimap.getValue(pref).isPresent()) {
                keywords = argMultimap.getValue(pref).get().split("\\s+");
                if (keywords[0].isEmpty()) {
                    throw new ParseException(MESSAGE_EMPTY_KEYWORD);
                }
                predicate = getKeywordsPredicate(pref, Arrays.asList(keywords), isIgnoreCase, isAnd);
                predicateList.add(predicate);
                prefixNum++;
            }
        }

        if (prefixNum < 1) {
            throw new ParseException(MESSAGE_NO_SEARCH_PARAMETER);
        }
        multiPredicate.setPredicateList(predicateList);

        return new PatientFindCommand(multiPredicate);
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

        case "sex/":
            return new SexContainsKeywordsPredicate(keywords, isIgnorecase, isAnd);

        case "da/":
            return new DrugAllergyContainsKeywordsPredicate(keywords, isIgnorecase, isAnd);

        case "desc/":
            return new DescriptionContainsKeywordsPredicate(keywords, isIgnorecase, isAnd);

        case "nokn/":
            return new KinNameContainsKeywordsPredicate(keywords, isIgnorecase, isAnd);

        case "nokr/":
            return new KinRelationContainsKeywordsPredicate(keywords, isIgnorecase, isAnd);

        case "nokp/":
            return new KinPhoneContainsKeywordsPredicate(keywords, isIgnorecase, isAnd);

        case "noka/":
            return new KinAddressContainsKeywordsPredicate(keywords, isIgnorecase, isAnd);

        case "tag/":
            return new TagsContainsKeywordsPredicate(keywords, isIgnorecase, isAnd);

        default:
            throw new ParseException("");
        }
    }

}
