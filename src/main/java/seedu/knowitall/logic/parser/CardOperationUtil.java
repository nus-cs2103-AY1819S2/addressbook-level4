package seedu.knowitall.logic.parser;

import static seedu.knowitall.commons.core.Messages.MESSAGE_INVALID_NUMBER_OF_CARD_ARGUMENTS;
import static seedu.knowitall.logic.parser.CliSyntax.PREFIX_ANSWER;
import static seedu.knowitall.logic.parser.CliSyntax.PREFIX_HINT;
import static seedu.knowitall.logic.parser.CliSyntax.PREFIX_OPTION;
import static seedu.knowitall.logic.parser.CliSyntax.PREFIX_QUESTION;

import seedu.knowitall.logic.parser.exceptions.ParseException;

/**
 * Contains utility methods for checking arguments to be parsed for {@code Card} operations.
 */
public class CardOperationUtil {

    private static final String QUESTION = "question";
    private static final String ANSWER = "answer";
    private static final String HINT = "hint";
    private static final String OPTION = "incorrect options";

    /**
     * Checks if number of values mapped to each card field {@code Prefix} in the {@code ArgumentMultimap} exceeds the
     * corresponding maximum number.
     * @throws ParseException if the number of values exceeds the corresponding maximum number.
     */
    public static void checkNumberOfValidArguments(ArgumentMultimap argumentMultimap) throws ParseException {
        if (argumentMultimap.getAllValues(PREFIX_QUESTION).size() > 1) {
            throw new ParseException(String.format(MESSAGE_INVALID_NUMBER_OF_CARD_ARGUMENTS, 1, QUESTION));
        }
        if (argumentMultimap.getAllValues(PREFIX_ANSWER).size() > 1) {
            throw new ParseException(String.format(MESSAGE_INVALID_NUMBER_OF_CARD_ARGUMENTS, 1, ANSWER));
        }
        if (argumentMultimap.getAllValues(PREFIX_HINT).size() > 1) {
            throw new ParseException(String.format(MESSAGE_INVALID_NUMBER_OF_CARD_ARGUMENTS, 1, HINT));
        }
        if (argumentMultimap.getAllValues(PREFIX_OPTION).size() > 3) {
            throw new ParseException(String.format(MESSAGE_INVALID_NUMBER_OF_CARD_ARGUMENTS, 1, OPTION));
        }
    }
}
