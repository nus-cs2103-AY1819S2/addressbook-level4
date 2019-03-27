package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.logic.commands.request.FindRequestCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.request.RequestDatePredicateUtil;
import seedu.address.logic.parser.request.RequestStatusPredicateUtil;
import seedu.address.model.request.Request;
import seedu.address.model.request.RequestAddressContainsKeywordPredicate;
import seedu.address.model.request.RequestConditionContainsKeywordPredicate;
import seedu.address.model.request.RequestDatePredicate;
import seedu.address.model.request.RequestNameContainsKeywordPredicate;
import seedu.address.model.request.RequestNricContainsKeywordPredicate;
import seedu.address.model.request.RequestPhoneContainsKeywordPredicate;
import seedu.address.model.request.RequestStatus;
import seedu.address.model.request.RequestStatusContainsKeywordPredicate;

/**
 * Util to parse Request's predicate
 */
public class RequestPredicateUtil {
    public static final String MESSAGE_EMPTY_KEYWORD = "%1$s cannot be empty";

    private static final String STRING_PREFIX_NAME = "n/";
    private static final String STRING_PREFIX_PHONE = "p/";
    private static final String STRING_PREFIX_ADDRESS = "a/";
    private static final String STRING_PREFIX_DATE = "dt/";
    private static final String STRING_PREFIX_CONDITION = "c/";
    private static final String STRING_PREFIX_STATUS = "st/";
    private static final String STRING_PREFIX_NRIC = "i/";
    private static final String STRING_PREFIX_HEALTHWORKER = "hw/";

    private Predicate<Request> chainedPredicate;

    /**
     * Parses the given {@code argMultimap} to a chained predicate and returns the chained
     * predicate.
     */
    public Predicate<Request> parsePredicate(ArgumentMultimap argumentMultimap) throws ParseException {
        Set<Prefix> prefixes = argumentMultimap.getAllPrefixes();

        for (Prefix prefix : prefixes) {
            if (prefix.toString().equals("")) {
                continue;
            }

            List<String> keywords = argumentMultimap.getAllValues(prefix);

            ensureNonEmptyField(prefix, keywords);
            chainPredicate(prefix, keywords);
        }
        return chainedPredicate;
    }

    /**
     * Parses the different {@code prefix}, forces the related predicate and chains them up
     *
     * @throws ParseException if an invalid {@code prefix} is supplied
     */
    private void chainPredicate(Prefix prefix, List<String> keywords) throws ParseException {
        switch (prefix.toString()) {
        case STRING_PREFIX_NAME:
            String name = getLastValueFromList(keywords);
            RequestNameContainsKeywordPredicate namePredicate =
                new RequestNameContainsKeywordPredicate(name);

            setToPredicate(namePredicate);
            break;

        case STRING_PREFIX_PHONE:
            String phone = getLastValueFromList(keywords);
            String[] phoneKeywords = trimAndSplitStringByWhiteSpaces(phone);
            RequestPhoneContainsKeywordPredicate phonePredicate =
                new RequestPhoneContainsKeywordPredicate(Arrays.asList(phoneKeywords));

            setToPredicate(phonePredicate);
            break;
        case STRING_PREFIX_ADDRESS:
            String address = getLastValueFromList(keywords);
            RequestAddressContainsKeywordPredicate predicate =
                new RequestAddressContainsKeywordPredicate(address);
            setToPredicate(predicate);

            break;

        case STRING_PREFIX_CONDITION:
            String condition = getLastValueFromList(keywords);
            RequestConditionContainsKeywordPredicate conditionContainsKeywordPredicate =
                new RequestConditionContainsKeywordPredicate(condition);
            setToPredicate(conditionContainsKeywordPredicate);

            break;

        case STRING_PREFIX_STATUS:
            String status = getLastValueFromList(keywords);
            String[] statusKeywords = trimAndSplitStringByWhiteSpaces(status);
            List<RequestStatus> requestStatuses =
                new RequestStatusPredicateUtil().parseOrderStatusKeywords(Arrays.asList(statusKeywords));
            RequestStatusContainsKeywordPredicate statusPredicate =
                new RequestStatusContainsKeywordPredicate(requestStatuses);

            setToPredicate(statusPredicate);
            break;

        case STRING_PREFIX_NRIC:
            String nric = getLastValueFromList(keywords);
            RequestNricContainsKeywordPredicate requestNricContainsKeywordPredicate =
                new RequestNricContainsKeywordPredicate(nric);
            setToPredicate(requestNricContainsKeywordPredicate);

            break;

        case STRING_PREFIX_DATE:
            List<Date> date = new RequestDatePredicateUtil().parseDateKeywords(keywords);
            RequestDatePredicate datePredicate = new RequestDatePredicate(date);
            setToPredicate(datePredicate);

            break;

        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindRequestCommand.MESSAGE_USAGE));
        }
    }
    private String getLastValueFromList(List<String> list) {
        int lastIndex = list.size() - 1;
        return list.get(lastIndex);
    }

    private String[] trimAndSplitStringByWhiteSpaces(String value) {
        return value.trim().split("\\s+");
    }

    /**
     * Set {@code chainedPredicate} to {@code predicate} if predicate is not set
     * else AND the predicates
     */
    private void setToPredicate(Predicate<Request> predicate) {
        // predicate is not set
        if (chainedPredicate == null) {
            chainedPredicate = predicate;
        } else {
            chainedPredicate = chainedPredicate.and(predicate);
        }
    }

    /**
     * Ensure if prefix given has a non-empty field
     *
     * @throws ParseException if one prefix is empty
     */
    private void ensureNonEmptyField(Prefix prefix, List<String> keywords) throws ParseException {
        for (String keyword : keywords) {
            if (keyword.equals("")) {
                throw new ParseException(String.format(MESSAGE_EMPTY_KEYWORD, prefix.toString()));
            }
        }
    }
}
