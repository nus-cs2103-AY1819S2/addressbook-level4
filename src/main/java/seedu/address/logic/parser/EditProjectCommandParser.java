package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.ArgumentMultimap.isAnyPrefixPresent;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLIENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FUNCTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_IMPORTANCE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MILESTONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REASON;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USER;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditProjectCommand;
import seedu.address.logic.commands.EditProjectInfoCommand.EditProjectDescriptor;
import seedu.address.logic.commands.EditProjectInfoCommand;
import seedu.address.logic.commands.EditProjectMilestoneCommand;
import seedu.address.logic.commands.EditProjectMilestoneCommand.EditMilestoneDescriptor;
import seedu.address.logic.commands.EditProjectUserStoryCommand;
import seedu.address.logic.commands.EditProjectUserStoryCommand.EditUserStoryDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.project.ProjectName;

/**
 * Parse input arguments and creates a new EditProjectCommand object
 */
public class EditProjectCommandParser {

    /**
     * Used for separation of type keyword and args.
     */
    private static final Pattern EDIT_PROJECT_COMMAND_FORMAT = Pattern.compile("(?<project>(\\S+\\s)+)"
        + "(?<keyword>milestone\\s|info\\s|userstory\\s)(?<arguments>.*)");

    private static final String WHITE_SPACE = " ";

    /**
     * Parse the input for the EditProjectCommand
     * @param userInput
     * @return
     * @throws ParseException
     */
    public EditProjectCommand parseProjectCommand(String userInput) throws ParseException {

        final Matcher matcher = EDIT_PROJECT_COMMAND_FORMAT.matcher(userInput.trim());

        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditProjectCommand.MESSAGE_USAGE));
        }

        final ProjectName projectToEdit = new ProjectName(matcher.group("project").trim());
        final String keyword = matcher.group("keyword").trim().toLowerCase();
        final String arguments = matcher.group("arguments");

        if (keyword.equals(EditProjectInfoCommand.EDIT_INFO_KEYWORD)) {
            requireNonNull(arguments);
            String s = " " + arguments;
            ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(s, PREFIX_NAME, PREFIX_CLIENT, PREFIX_DEADLINE, PREFIX_DESCRIPTION);

            if (!isAnyPrefixPresent(argMultimap, PREFIX_NAME, PREFIX_CLIENT, PREFIX_DEADLINE, PREFIX_DESCRIPTION)) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditProjectCommand.MESSAGE_USAGE));
            }

            EditProjectDescriptor editProjectDescriptor = new EditProjectDescriptor();

            if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
                editProjectDescriptor.setProjectName(ParserUtil.parseProjectName(argMultimap
                    .getValue(PREFIX_NAME).get()));
            }
            if (argMultimap.getValue(PREFIX_CLIENT).isPresent()) {
                editProjectDescriptor.setClient(ParserUtil.parseClient(argMultimap.getValue(PREFIX_CLIENT).get()));
            }
            if (argMultimap.getValue(PREFIX_DEADLINE).isPresent()) {
                editProjectDescriptor.setDeadline(ParserUtil.parseDate(argMultimap
                    .getValue(PREFIX_DEADLINE).get()));
            }
            if (argMultimap.getValue(PREFIX_DESCRIPTION).isPresent()) {
                editProjectDescriptor.setDescription(ParserUtil.parseDescription(argMultimap
                    .getValue(PREFIX_DESCRIPTION).get()));
            }

            if (!editProjectDescriptor.isAnyFieldEdited()) {
                throw new ParseException(EditProjectInfoCommand.MESSAGE_NOT_EDITED);
            }

            return new EditProjectInfoCommand(projectToEdit, editProjectDescriptor);

        } else if (keyword.equals(EditProjectMilestoneCommand.EDIT_MILESTONE_KEYWORD)) {

            requireNonNull(arguments);
            String s = WHITE_SPACE + arguments;
            ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(s, PREFIX_MILESTONE, PREFIX_DATE);

            Index milestoneIndex;

            try {
                milestoneIndex = ParserUtil.parseIndex(argMultimap.getPreamble());
            } catch (ParseException pe) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditProjectMilestoneCommand.MESSAGE_USAGE), pe);
            }

            EditMilestoneDescriptor editMilestoneDescriptor = new EditMilestoneDescriptor();

            if (argMultimap.getValue(PREFIX_MILESTONE).isPresent()) {
                editMilestoneDescriptor.setMilestoneDesc(ParserUtil.parseMilestoneDescription(
                    argMultimap.getValue(PREFIX_MILESTONE).get()));
            }

            if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
                editMilestoneDescriptor.setDate(ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get()));
            }

            if (!editMilestoneDescriptor.isAnyFieldEdited()) {
                throw new ParseException(EditProjectMilestoneCommand.MESSAGE_NOT_EDITED);
            }

            return new EditProjectMilestoneCommand(projectToEdit, milestoneIndex, editMilestoneDescriptor);

        } else if (keyword.equals(EditProjectUserStoryCommand.EDIT_USER_STORY_KEYWORD)) {
            requireNonNull(arguments);
            String s = " " + arguments;
            ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(s, PREFIX_USER, PREFIX_FUNCTION, PREFIX_REASON, PREFIX_IMPORTANCE);

            Index index;

            try {
                index = ParserUtil.parseIndex(argMultimap.getPreamble());
            } catch (ParseException pe) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditProjectUserStoryCommand.MESSAGE_USAGE), pe);
            }

            EditUserStoryDescriptor editUserStoryDescriptor = new EditUserStoryDescriptor();
            if (argMultimap.getValue(PREFIX_USER).isPresent()) {
                editUserStoryDescriptor.setUser(ParserUtil.parseStoryUser(argMultimap.getValue(PREFIX_USER).get()));
            }
            if (argMultimap.getValue(PREFIX_FUNCTION).isPresent()) {
                editUserStoryDescriptor.setFunction(ParserUtil.parseStoryFunction(
                    argMultimap.getValue(PREFIX_FUNCTION).get()));
            }
            if (argMultimap.getValue(PREFIX_REASON).isPresent()) {
                editUserStoryDescriptor.setReason(ParserUtil.parseStoryReason(
                    argMultimap.getValue(PREFIX_REASON).get()));
            }
            if (argMultimap.getValue(PREFIX_IMPORTANCE).isPresent()) {
                editUserStoryDescriptor.setImportance(ParserUtil.parseStoryImportance(
                    argMultimap.getValue(PREFIX_IMPORTANCE).get()));
            }

            return new EditProjectUserStoryCommand(projectToEdit, index, editUserStoryDescriptor);

        } else {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditProjectCommand.MESSAGE_USAGE));
        }

    }
}

