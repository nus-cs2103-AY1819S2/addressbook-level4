package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.Syntax.PREFIX_CORE;
import static seedu.address.logic.parser.Syntax.PREFIX_CORE_ANSWER;
import static seedu.address.logic.parser.Syntax.PREFIX_CORE_QUESTION;
import static seedu.address.logic.parser.Syntax.PREFIX_LESSON_NAME;
import static seedu.address.logic.parser.Syntax.PREFIX_OPTIONAL;

import java.util.ArrayList;
import java.util.stream.Stream;

import seedu.address.logic.commands.management.AddLessonCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.lesson.Lesson;

/**
 * Parses input arguments and creates a new {@link AddLessonCommand} object.
 */
public class AddLessonParser implements Parser<AddLessonCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the {@link AddLessonCommand}
     * and returns an {@link AddLessonCommand} object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddLessonCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_LESSON_NAME, PREFIX_CORE_QUESTION,
                        PREFIX_CORE_ANSWER, PREFIX_CORE, PREFIX_OPTIONAL);

        if (!arePrefixesPresent(
                argMultimap, PREFIX_LESSON_NAME, PREFIX_CORE_QUESTION, PREFIX_CORE_ANSWER)
                || !argMultimap.getPreamble().isEmpty()
                || argMultimap.getAllValues(PREFIX_CORE_QUESTION).size() != 1
                || argMultimap.getAllValues(PREFIX_CORE_ANSWER).size() != 1) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddLessonCommand.MESSAGE_USAGE));
        }

        String name = argMultimap.getValue(PREFIX_LESSON_NAME).get();
        ArrayList<String> coreHeaders = new ArrayList<>(argMultimap.getAllValues(PREFIX_CORE_QUESTION));
        coreHeaders.addAll(argMultimap.getAllValues(PREFIX_CORE_ANSWER));
        coreHeaders.addAll(argMultimap.getAllValues(PREFIX_CORE));
        ArrayList<String> optHeaders = new ArrayList<>(argMultimap.getAllValues(PREFIX_OPTIONAL));
        Lesson lesson = new Lesson(name, coreHeaders, optHeaders);
        return new AddLessonCommand(lesson);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     *
     * @param argumentMultimap the mapping of prefixes to their respective arguments
     * @param prefixes the prefixes to check if present
     * @return true if prefixes are present in {@see argumentMultimap}; false otherwise
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
