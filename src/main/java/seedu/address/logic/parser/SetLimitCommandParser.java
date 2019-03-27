package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MAX_GRADE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MAX_LECTURE_HOUR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MIN_GRADE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MIN_LECTURE_HOUR;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.SetCurrentSemesterCommand;
import seedu.address.logic.commands.SetSemLimitCommand;
import seedu.address.logic.commands.SetSemLimitCommand.EditSemLimitDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Semester;

/**
 * Parses input arguments and creates a new SetLimitCommand object
 */
public class SetLimitCommandParser implements Parser<SetSemLimitCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SetLimitCommand
     * and returns an SetLimitCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SetSemLimitCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args,
                        PREFIX_MIN_GRADE, PREFIX_MAX_GRADE, PREFIX_MIN_LECTURE_HOUR, PREFIX_MAX_LECTURE_HOUR);

        Index index;
        String rawSemester = argMultimap.getPreamble();
        boolean isValidSemester = Semester.isValidSemester(rawSemester);
        if (!isValidSemester) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    SetCurrentSemesterCommand.MESSAGE_USAGE));
        }
        index = ParserUtil.parseIndex(rawSemester);

        EditSemLimitDescriptor editSemLimitDescriptor = new EditSemLimitDescriptor();
        if (argMultimap.getValue(PREFIX_MIN_GRADE).isPresent()) {
            editSemLimitDescriptor.setMinGrade(
                    ParserUtil.parseGrade(argMultimap.getValue(PREFIX_MIN_GRADE).get()));
        }
        if (argMultimap.getValue(PREFIX_MAX_GRADE).isPresent()) {
            editSemLimitDescriptor.setMaxGrade(
                    ParserUtil.parseGrade(argMultimap.getValue(PREFIX_MAX_GRADE).get()));
        }
        if (argMultimap.getValue(PREFIX_MIN_LECTURE_HOUR).isPresent()) {
            editSemLimitDescriptor.setMinLectureHour(
                    ParserUtil.parseHour(argMultimap.getValue(PREFIX_MIN_LECTURE_HOUR).get()));
        }
        if (argMultimap.getValue(PREFIX_MAX_LECTURE_HOUR).isPresent()) {
            editSemLimitDescriptor.setMaxLectureHour(
                    ParserUtil.parseHour(argMultimap.getValue(PREFIX_MAX_LECTURE_HOUR).get()));
        }

        if (!editSemLimitDescriptor.isAnyFieldEdited()) {
            throw new ParseException(SetSemLimitCommand.MESSAGE_NOT_EDITED);
        }

        return new SetSemLimitCommand(index, editSemLimitDescriptor);
    }
}
