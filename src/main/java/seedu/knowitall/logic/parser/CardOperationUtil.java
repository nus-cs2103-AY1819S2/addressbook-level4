package seedu.knowitall.logic.parser;

import static seedu.knowitall.commons.core.Messages.MESSAGE_INVALID_NUMBER_OF_CARD_ARGUMENTS;
import static seedu.knowitall.logic.parser.CliSyntax.PREFIX_ANSWER;
import static seedu.knowitall.logic.parser.CliSyntax.PREFIX_HINT;
import static seedu.knowitall.logic.parser.CliSyntax.PREFIX_OPTION;
import static seedu.knowitall.logic.parser.CliSyntax.PREFIX_QUESTION;
import static seedu.knowitall.model.card.Card.MAX_ANSWERS;
import static seedu.knowitall.model.card.Card.MAX_HINTS;
import static seedu.knowitall.model.card.Card.MAX_OPTIONS;
import static seedu.knowitall.model.card.Card.MAX_QUESTIONS;

import seedu.knowitall.logic.parser.exceptions.ParseException;

/**
 * Contains utility methods for checking arguments to be parsed for {@code Card} operations.
 */
public class CardOperationUtil {

    public static final String QUESTION_STRING = "question";
    public static final String ANSWER_STRING = "answer";
    public static final String HINT_STRING = "hint";
    public static final String OPTION_STRING = "incorrect options";

    /**
     * Checks if number of values mapped to each card field {@code Prefix} in the {@code ArgumentMultimap} exceeds the
     * corresponding maximum number.
     *
     * @throws ParseException if the number of values exceeds the corresponding maximum number.
     */
    public static void checkNumberOfValidArguments(ArgumentMultimap argumentMultimap) throws ParseException {
        checkNumberOfArguments(argumentMultimap, PREFIX_QUESTION, MAX_QUESTIONS, QUESTION_STRING);
        checkNumberOfArguments(argumentMultimap, PREFIX_ANSWER, MAX_ANSWERS, ANSWER_STRING);
        checkNumberOfArguments(argumentMultimap, PREFIX_HINT, MAX_HINTS, HINT_STRING);
        checkNumberOfArguments(argumentMultimap, PREFIX_OPTION, MAX_OPTIONS, OPTION_STRING);
    }

    private static void checkNumberOfArguments(ArgumentMultimap argMultimap, Prefix prefix, int limit, String field)
            throws ParseException {
        if (argMultimap.getAllValues(prefix).size() > limit) {
            throw new ParseException(String.format(MESSAGE_INVALID_NUMBER_OF_CARD_ARGUMENTS, limit, field));
        }
    }
}
