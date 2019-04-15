package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GITHUB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SKILL;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditEmployeeCommand;
import seedu.address.logic.commands.EditEmployeeCommand.EditEmployeeDescriptor;
import seedu.address.logic.commands.EditProjectCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.skill.Skill;

/**
 * Parses input arguments and creates a new EditEmployeeCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {

    private static final Pattern EDIT_COMMAND_FORMAT = Pattern.compile("(?<keyword>\\S+)(?<arguments>.*)");

    /**
     * Parses the given {@code String} of arguments in the context of the EditEmployeeCommand
     * and returns an EditEmployeeCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public EditCommand parse(String args) throws ParseException {

        String trimmedArgs = args.trim();
        final Matcher matcher = EDIT_COMMAND_FORMAT.matcher(trimmedArgs);

        if (!matcher.matches()) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }

        final String keyword = matcher.group("keyword").toLowerCase();
        final String arguments = matcher.group("arguments");

        if (keyword.equals(EditEmployeeCommand.EDIT_EMPLOYEE_KEYWORD)) {

            requireNonNull(arguments);
            ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(arguments, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                    PREFIX_GITHUB, PREFIX_SKILL);

            Index index;

            try {
                index = ParserUtil.parseIndex(argMultimap.getPreamble());
            } catch (ParseException pe) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditEmployeeCommand.MESSAGE_USAGE), pe);
            }

            EditEmployeeDescriptor editEmployeeDescriptor = new EditEmployeeDescriptor();
            if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
                editEmployeeDescriptor.setEmployeeName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
            }
            if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
                editEmployeeDescriptor.setPhone(ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get()));
            }
            if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
                editEmployeeDescriptor.setEmail(ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get()));
            }
            if (argMultimap.getValue(PREFIX_GITHUB).isPresent()) {
                editEmployeeDescriptor.setGitHubAccount(ParserUtil.parseAccount(
                    argMultimap.getValue(PREFIX_GITHUB).get()));
            }
            parseSkillsForEdit(argMultimap.getAllValues(PREFIX_SKILL)).ifPresent(editEmployeeDescriptor::setSkills);

            if (!editEmployeeDescriptor.isAnyFieldEdited()) {
                throw new ParseException(EditEmployeeCommand.MESSAGE_NOT_EDITED);
            }

            return new EditEmployeeCommand(index, editEmployeeDescriptor);

        } else if (keyword.equals(EditProjectCommand.EDIT_PROJECT_KEYWORD)) {

            EditProjectCommandParser projectParser = new EditProjectCommandParser();
            return projectParser.parseProjectCommand(arguments);

        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Parses {@code Collection<String> skills} into a {@code Set<Skill>} if {@code skills} is non-empty.
     * If {@code skills} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Skill>} containing zero skills.
     */
    private Optional<Set<Skill>> parseSkillsForEdit(Collection<String> skills) throws ParseException {
        assert skills != null;

        if (skills.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> skillSet = skills.size() == 1 && skills.contains("") ? Collections.emptySet() : skills;
        return Optional.of(ParserUtil.parseSkills(skillSet));
    }

}
