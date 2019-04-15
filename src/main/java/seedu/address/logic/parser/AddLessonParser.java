package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_EMPTY_INPUT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_INPUT;
import static seedu.address.commons.util.StringUtil.hasEmptyStrings;
import static seedu.address.logic.parser.Syntax.PREFIX_HINT;
import static seedu.address.logic.parser.Syntax.PREFIX_LESSON_NAME;
import static seedu.address.logic.parser.Syntax.PREFIX_TEST;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import seedu.address.logic.commands.management.AddLessonCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.lesson.Lesson;

/**
 * Parses input arguments and creates a new {@link AddLessonCommand} object.
 */
public class AddLessonParser implements Parser<AddLessonCommand> {
    /**
     * Checks if the supplied prefixes only have 1 supplied value each.
     *
     * @param argMultimap the multimap to check
     * @param prefixes the prefixes in the multimap to check
     * @throws ParseException if specified prefixes does not have exactly one value supplied
     */
    private void requireOnlyOneValue(ArgumentMultimap argMultimap, Prefix ... prefixes) throws ParseException {
        for (Prefix prefix : prefixes) {
            if (!argMultimap.hasExactlyOneValue(prefix)) {
                throw new ParseException(String.format(MESSAGE_INVALID_INPUT, prefix));
            }
        }
    }

    /**
     * Parses the given {@code String} of arguments in the context of the {@link AddLessonCommand}
     * and returns an {@link AddLessonCommand} object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddLessonCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_LESSON_NAME, PREFIX_TEST, PREFIX_HINT);

        List<String> testValues = argMultimap.getAllValues(PREFIX_TEST);
        if (!arePrefixesPresent(argMultimap, PREFIX_LESSON_NAME)
                || !argMultimap.getPreamble().isEmpty()
                || testValues.size() < 2) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddLessonCommand.MESSAGE_USAGE));
        }

        // Name can only be specified once
        requireOnlyOneValue(argMultimap, PREFIX_LESSON_NAME);

        String name = argMultimap.getValue(PREFIX_LESSON_NAME).get();

        ArrayList<String> coreHeaders = new ArrayList<>(testValues);
        ArrayList<String> optHeaders = new ArrayList<>(argMultimap.getAllValues(PREFIX_HINT));

        // Name cannot be empty, strings in coreHeaders cannot be empty
        // and if optionalHeaders isn't empty, strings it mustn't be empty
        if (name.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_EMPTY_INPUT, PREFIX_LESSON_NAME));
        } else if (hasEmptyStrings(coreHeaders)) {
            throw new ParseException(String.format(MESSAGE_EMPTY_INPUT, PREFIX_TEST));
        } else if (hasEmptyStrings(optHeaders)) {
            throw new ParseException(String.format(MESSAGE_EMPTY_INPUT, PREFIX_HINT));
        }

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
