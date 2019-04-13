package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MAX_CAP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MAX_LAB_HOUR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MAX_LECTURE_HOUR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MAX_PREPARATION_HOUR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MAX_PROJECT_HOUR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MAX_TUTORIAL_HOUR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MIN_CAP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MIN_LAB_HOUR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MIN_LECTURE_HOUR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MIN_PREPARATION_HOUR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MIN_PROJECT_HOUR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MIN_TUTORIAL_HOUR;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.SetSemesterLimitCommand;
import seedu.address.logic.commands.SetSemesterLimitCommand.EditSemesterLimitDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.moduletaken.Semester;

/**
 * Parses input arguments and creates a new SetLimitCommand object
 */
public class SetSemesterLimitCommandParser implements Parser<SetSemesterLimitCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SetLimitCommand
     * and returns an SetLimitCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SetSemesterLimitCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args,
                        PREFIX_MIN_CAP, PREFIX_MAX_CAP, PREFIX_MIN_LECTURE_HOUR, PREFIX_MAX_LECTURE_HOUR,
                        PREFIX_MIN_TUTORIAL_HOUR, PREFIX_MAX_TUTORIAL_HOUR, PREFIX_MIN_LAB_HOUR,
                        PREFIX_MAX_LAB_HOUR, PREFIX_MIN_PROJECT_HOUR, PREFIX_MAX_PROJECT_HOUR,
                        PREFIX_MIN_PREPARATION_HOUR, PREFIX_MAX_PREPARATION_HOUR);

        Index index;
        String rawSemester = argMultimap.getPreamble();
        boolean isValidSemester = Semester.isValidSemesterForTakingModules(rawSemester);
        if (!isValidSemester) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    SetSemesterLimitCommand.MESSAGE_USAGE));
        }

        int zeroBasedIndex = Semester.valueOf(rawSemester).getIndex();
        index = ParserUtil.parseIndex(String.valueOf(++zeroBasedIndex));

        EditSemesterLimitDescriptor editSemesterLimitDescriptor = new EditSemesterLimitDescriptor();
        if (argMultimap.getValue(PREFIX_MIN_CAP).isPresent()) {
            editSemesterLimitDescriptor.setMinCap(
                    ParserUtil.parseCap(argMultimap.getValue(PREFIX_MIN_CAP).get()));
        }
        if (argMultimap.getValue(PREFIX_MAX_CAP).isPresent()) {
            editSemesterLimitDescriptor.setMaxCap(
                    ParserUtil.parseCap(argMultimap.getValue(PREFIX_MAX_CAP).get()));
        }
        if (argMultimap.getValue(PREFIX_MIN_LECTURE_HOUR).isPresent()) {
            editSemesterLimitDescriptor.setMinLectureHour(
                    ParserUtil.parseHour(argMultimap.getValue(PREFIX_MIN_LECTURE_HOUR).get()));
        }
        if (argMultimap.getValue(PREFIX_MAX_LECTURE_HOUR).isPresent()) {
            editSemesterLimitDescriptor.setMaxLectureHour(
                    ParserUtil.parseHour(argMultimap.getValue(PREFIX_MAX_LECTURE_HOUR).get()));
        }
        if (argMultimap.getValue(PREFIX_MIN_TUTORIAL_HOUR).isPresent()) {
            editSemesterLimitDescriptor.setMinTutorialHour(
                    ParserUtil.parseHour(argMultimap.getValue(PREFIX_MIN_TUTORIAL_HOUR).get()));
        }
        if (argMultimap.getValue(PREFIX_MAX_TUTORIAL_HOUR).isPresent()) {
            editSemesterLimitDescriptor.setMaxTutorialHour(
                    ParserUtil.parseHour(argMultimap.getValue(PREFIX_MAX_TUTORIAL_HOUR).get()));
        }
        if (argMultimap.getValue(PREFIX_MIN_LAB_HOUR).isPresent()) {
            editSemesterLimitDescriptor.setMinLabHour(
                    ParserUtil.parseHour(argMultimap.getValue(PREFIX_MIN_LAB_HOUR).get()));
        }
        if (argMultimap.getValue(PREFIX_MAX_LAB_HOUR).isPresent()) {
            editSemesterLimitDescriptor.setMaxLabHour(
                    ParserUtil.parseHour(argMultimap.getValue(PREFIX_MAX_LAB_HOUR).get()));
        }
        if (argMultimap.getValue(PREFIX_MIN_PROJECT_HOUR).isPresent()) {
            editSemesterLimitDescriptor.setMinProjectHour(
                    ParserUtil.parseHour(argMultimap.getValue(PREFIX_MIN_PROJECT_HOUR).get()));
        }
        if (argMultimap.getValue(PREFIX_MAX_PROJECT_HOUR).isPresent()) {
            editSemesterLimitDescriptor.setMaxProjectHour(
                    ParserUtil.parseHour(argMultimap.getValue(PREFIX_MAX_PROJECT_HOUR).get()));
        }
        if (argMultimap.getValue(PREFIX_MIN_PREPARATION_HOUR).isPresent()) {
            editSemesterLimitDescriptor.setMinPreparationHour(
                    ParserUtil.parseHour(argMultimap.getValue(PREFIX_MIN_PREPARATION_HOUR).get()));
        }
        if (argMultimap.getValue(PREFIX_MAX_PREPARATION_HOUR).isPresent()) {
            editSemesterLimitDescriptor.setMaxPreparationHour(
                    ParserUtil.parseHour(argMultimap.getValue(PREFIX_MAX_PREPARATION_HOUR).get()));
        }

        if (!editSemesterLimitDescriptor.isAnyFieldEdited()) {
            throw new ParseException(SetSemesterLimitCommand.MESSAGE_NOT_EDITED);
        }

        return new SetSemesterLimitCommand(index, editSemesterLimitDescriptor);
    }
}
