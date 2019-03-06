package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.SetCourseCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.course.Course;

/**
 * Parses input arguments and creates a new SetCourseCommand object
 */
public class SetCourseCommandParser implements Parser<SetCourseCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SetCourseCommand
     * and returns an SetCourseCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SetCourseCommand parse(String args) throws ParseException {
        try {
            Course course = Course.getCourseByName(ParserUtil.parseCourseName(args));
            return new SetCourseCommand(course);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetCourseCommand.MESSAGE_USAGE), pe);
        }
    }

}
