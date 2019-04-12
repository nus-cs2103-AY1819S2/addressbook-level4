package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROCEDURE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import seedu.address.logic.commands.RecordFindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.util.predicate.ContainsKeywordsPredicate;
import seedu.address.model.util.predicate.DescriptionRecordContainsKeywordsPredicate;
import seedu.address.model.util.predicate.MultipleContainsKeywordsPredicate;
import seedu.address.model.util.predicate.ProcedureContainsKeywordsPredicate;


/**
 * Parses input arguments and creates a new RecordFindCommand object
 */
public class RecordFindCommandParser implements Parser<RecordFindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RecordFindCommand
     * and returns an RecordFindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RecordFindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        int prefixNum = 0;
        boolean isIgnoreCase = true;
        boolean isAnd = false;

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RecordFindCommand.MESSAGE_USAGE));
        }

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(trimmedArgs, PREFIX_PROCEDURE, PREFIX_DESC);

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
                    RecordFindCommand.MESSAGE_USAGE));
            }
        }

        Prefix[] prefixArr = new Prefix[] {PREFIX_PROCEDURE, PREFIX_DESC};

        String[] keywords;
        ContainsKeywordsPredicate predicate;
        MultipleContainsKeywordsPredicate multiPredicate =
            new MultipleContainsKeywordsPredicate(Collections.emptyList(), isIgnoreCase, isAnd);

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

        return new RecordFindCommand(multiPredicate);
    }

    private static ContainsKeywordsPredicate getKeywordsPredicate(Prefix prefix, List<String> keywords,
                                                                  boolean isIgnorecase, boolean isAnd)
        throws ParseException {

        switch (prefix.getPrefix()) {

        case "pro/":
            return new ProcedureContainsKeywordsPredicate(keywords, isIgnorecase, isAnd);

        case "desc/":
            return new DescriptionRecordContainsKeywordsPredicate(keywords, isIgnorecase, isAnd);

        default:
            throw new ParseException("Invalid prefix(es)");
        }
    }
}
