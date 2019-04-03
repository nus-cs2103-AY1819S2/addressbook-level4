package seedu.finance.logic.parser;

import static seedu.finance.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.finance.logic.parser.CliSyntax.COMMAND_FLAG_CATEGORY;
import static seedu.finance.logic.parser.CliSyntax.COMMAND_FLAG_DATE;
import static seedu.finance.logic.parser.CliSyntax.COMMAND_FLAG_NAME;

import java.util.Arrays;

import seedu.finance.logic.commands.SearchCommand;
import seedu.finance.logic.parser.exceptions.ParseException;
import seedu.finance.model.record.CategoryContainsKeywordsPredicate;
import seedu.finance.model.record.Date;
import seedu.finance.model.record.DateContainsKeywordsPredicate;
import seedu.finance.model.record.NameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new SearchCommand object
 */
public class SearchCommandParser implements Parser<SearchCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an SearchCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SearchCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE));
        }

        String[] argsWithFlag = trimmedArgs.split("\\s+");
        String[] keywords = Arrays.copyOfRange(argsWithFlag, 1, argsWithFlag.length);

        switch (argsWithFlag[0]) {
        case "-name":
            return new SearchCommand(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        case "-cat":
            return new SearchCommand(new CategoryContainsKeywordsPredicate(Arrays.asList(keywords)));
        case "-date":
            if (!checkKeywordsValidDate(keywords)) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, Date.MESSAGE_CONSTRAINTS));
            }
            return new SearchCommand(new DateContainsKeywordsPredicate(Arrays.asList(keywords)));
        default:
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.INVALID_FLAG));
        }
    }

    private static boolean checkKeywordsValidDate(String[] keywords) {
        for (String keyword : keywords) {
            if (!Date.isValidDate(keyword)) {
                return false;
            }
        }
        return true;
    }
}
