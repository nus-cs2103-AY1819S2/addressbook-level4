package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.Syntax.PREFIX_START_COUNT;
import static seedu.address.logic.parser.Syntax.PREFIX_START_INDEX;
import static seedu.address.logic.parser.Syntax.PREFIX_START_MODE;

import java.util.stream.Stream;

import seedu.address.logic.commands.management.QuizStartCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.quiz.QuizMode;
import seedu.address.model.session.Session;


/**
 * Parses user input when start a session quiz.
 */
public class StartCommandParser implements Parser {
    /**
     * Parses the given {@code String} of arguments in the context of the StartCommand
     * and returns an StartCommand object for execution.
     */
    @Override
    public QuizStartCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_START_INDEX, PREFIX_START_COUNT, PREFIX_START_MODE);
        if (!arePrefixesPresent(argMultimap, PREFIX_START_INDEX, PREFIX_START_MODE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, QuizStartCommand.MESSAGE_USAGE));
        }

        int index = ParserUtil.parseLessonIndex(argMultimap.getValue(PREFIX_START_INDEX).get());
        int count = ParserUtil.parseCount(argMultimap.getValue(PREFIX_START_COUNT)
            .orElse(String.valueOf(Session.CARD_COUNT_MINIMUM)));
        QuizMode mode = ParserUtil.parseMode(argMultimap.getValue(PREFIX_START_MODE).get());
        Session session = new Session(index, count, mode);
        return new QuizStartCommand(session);
    }

    /**
    * Returns true if none of the prefixes contains empty {@code Optional} values in the given
    * {@code ArgumentMultimap}.
    */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
