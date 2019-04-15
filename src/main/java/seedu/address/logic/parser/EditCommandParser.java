package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPECTED_MAX_GRADE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPECTED_MIN_GRADE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LAB_HOUR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LECTURE_HOUR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_INFO_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PREPARATION_HOUR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROJECT_HOUR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEMESTER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIAL_HOUR;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditModuleTakenDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_MODULE_INFO_CODE, PREFIX_SEMESTER,
                        PREFIX_EXPECTED_MIN_GRADE, PREFIX_EXPECTED_MAX_GRADE, PREFIX_LECTURE_HOUR, PREFIX_TUTORIAL_HOUR,
                        PREFIX_LAB_HOUR, PREFIX_PROJECT_HOUR, PREFIX_PREPARATION_HOUR, PREFIX_TAG);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
        }

        EditModuleTakenDescriptor editPersonDescriptor = new EditCommand.EditModuleTakenDescriptor();
        if (argMultimap.getValue(PREFIX_MODULE_INFO_CODE).isPresent()) {
            editPersonDescriptor.setModuleInfoCode(
                    ParserUtil.moduleInfoCode(argMultimap.getValue(PREFIX_MODULE_INFO_CODE).get()));
        }
        if (argMultimap.getValue(PREFIX_SEMESTER).isPresent()) {
            editPersonDescriptor.setSemester(ParserUtil.parseSemester(argMultimap.getValue(PREFIX_SEMESTER).get()));
        }
        if (argMultimap.getValue(PREFIX_EXPECTED_MIN_GRADE).isPresent()) {
            editPersonDescriptor.setExpectedMinGrade(
                    ParserUtil.parseGrade(argMultimap.getValue(PREFIX_EXPECTED_MIN_GRADE).get()));
        }
        if (argMultimap.getValue(PREFIX_EXPECTED_MAX_GRADE).isPresent()) {
            editPersonDescriptor.setExpectedMaxGrade(
                    ParserUtil.parseGrade(argMultimap.getValue(PREFIX_EXPECTED_MAX_GRADE).get()));
        }
        if (argMultimap.getValue(PREFIX_LECTURE_HOUR).isPresent()) {
            editPersonDescriptor.setLectureHour(
                    ParserUtil.parseHour(argMultimap.getValue(PREFIX_LECTURE_HOUR).get()));
        }
        if (argMultimap.getValue(PREFIX_TUTORIAL_HOUR).isPresent()) {
            editPersonDescriptor.setTutorialHour(
                    ParserUtil.parseHour(argMultimap.getValue(PREFIX_TUTORIAL_HOUR).get()));
        }
        if (argMultimap.getValue(PREFIX_LAB_HOUR).isPresent()) {
            editPersonDescriptor.setLabHour(
                    ParserUtil.parseHour(argMultimap.getValue(PREFIX_LAB_HOUR).get()));
        }
        if (argMultimap.getValue(PREFIX_PROJECT_HOUR).isPresent()) {
            editPersonDescriptor.setProjectHour(
                    ParserUtil.parseHour(argMultimap.getValue(PREFIX_PROJECT_HOUR).get()));
        }
        if (argMultimap.getValue(PREFIX_PREPARATION_HOUR).isPresent()) {
            editPersonDescriptor.setPreparationHour(
                    ParserUtil.parseHour(argMultimap.getValue(PREFIX_PREPARATION_HOUR).get()));
        }
        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editPersonDescriptor::setTags);

        if (!editPersonDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index, editPersonDescriptor);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTagsForEdit(Collection<String> tags) throws ParseException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }

}
