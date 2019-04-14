package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.Syntax.PREFIX_START_COUNT;
import static seedu.address.logic.parser.Syntax.PREFIX_START_MODE;

import java.util.stream.Stream;

import seedu.address.logic.commands.management.StartCommand;
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
     * Checks if userInput is valid.
     * @param args from commandBox
     * @return the correct parser as a {@link Session}.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public StartCommand parse(String args) throws ParseException {
        String[] splited = args.split("\\s+");
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_START_COUNT, PREFIX_START_MODE);
        if (!arePrefixesPresent(argMultimap, PREFIX_START_MODE)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, StartCommand.MESSAGE_USAGE));
        }
        int index;
        try {
            index = Integer.parseInt(splited[1]);
        } catch (NumberFormatException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, StartCommand.MESSAGE_USAGE));
        }
        int count = ParserUtil.parseCount(argMultimap.getValue(PREFIX_START_COUNT)
            .orElse(String.valueOf(Session.CARD_COUNT_MINIMUM)));
        QuizMode mode = ParserUtil.parseMode(argMultimap.getValue(PREFIX_START_MODE).get());
        Session session = new Session(index, count, mode);
        return new StartCommand(session);
    }

    /**
     * Returns true if none of the prefixes contains empty values in the given {@code ArgumentMultimap}.
     *
     * @param argumentMultimap the mapping of prefixes to their respective arguments
     * @param prefixes the prefixes to check if present
     * @return true if prefixes are present in {@see argumentMultimap}; false otherwise
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
