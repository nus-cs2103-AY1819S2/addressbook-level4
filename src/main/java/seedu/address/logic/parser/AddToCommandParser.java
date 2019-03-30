package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.AddCommandParser.arePrefixesPresent;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FUNCTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_IMPORTANCE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MILESTONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REASON;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USER;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddEmployeeToCommand;
import seedu.address.logic.commands.AddMilestoneToCommand;
import seedu.address.logic.commands.AddTaskToCommand;
import seedu.address.logic.commands.AddToCommand;
import seedu.address.logic.commands.AddUserStoryToCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.project.Milestone;
import seedu.address.model.project.ProjectName;
import seedu.address.model.project.Task;
import seedu.address.model.project.TaskName;
import seedu.address.model.project.UserStory;
import seedu.address.model.project.UserStoryFunction;
import seedu.address.model.project.UserStoryImportance;
import seedu.address.model.project.UserStoryReason;
import seedu.address.model.project.UserStoryUser;

/**
 * Parses input arguments and creates a new AddToCommand object
 */
public class AddToCommandParser implements Parser<AddToCommand> {

    /**
     * Used for separation of type keyword and args.
     */
    private static final Pattern ADD_TO_COMMAND_FORMAT = Pattern.compile("(?<project>(\\S+\\s)+)"
            + "(?<keyword>employee\\s|milestone\\s|userstory\\s|task\\s)(?<arguments>.*)");

    private static final Pattern USER_STORY_FORMAT = Pattern.compile("\\d");

    /**
     * Parses the given {@code String} of arguments in the context of the AddToCommand
     * and returns an AddToCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddToCommand parse(String args) throws ParseException {

        final Matcher matcher = ADD_TO_COMMAND_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddToCommand.MESSAGE_USAGE));
        }
        final ProjectName projectName = new ProjectName(matcher.group("project").trim());
        final String keyword = matcher.group("keyword").trim().toLowerCase();
        final String arguments = matcher.group("arguments");

        if (keyword.equals(AddEmployeeToCommand.ADD_EMPLOYEE_KEYWORD)) {

            try {
                Index index = ParserUtil.parseIndex(arguments.trim());
                return new AddEmployeeToCommand(index, projectName);
            } catch (ParseException pe) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEmployeeToCommand.MESSAGE_USAGE), pe);
            }
        } else if (keyword.equals(AddMilestoneToCommand.ADD_MILESTONE_KEYWORD)) {
            try {
                Milestone milestone = ParserUtil.parseMilestone(arguments.trim());
                return new AddMilestoneToCommand(projectName, milestone);
            } catch (ParseException pe) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMilestoneToCommand.MESSAGE_USAGE), pe);
            }

        } else if (keyword.equals(AddUserStoryToCommand.ADD_USERSTORY_KEYWORD)) {
            try {

                String s = " " + arguments; //add whitespace to allow tokenizer to detect regex
                ArgumentMultimap argMultimap =
                        ArgumentTokenizer.tokenize(s, PREFIX_USER, PREFIX_FUNCTION, PREFIX_REASON,
                                PREFIX_IMPORTANCE);

                if (!arePrefixesPresent(argMultimap, PREFIX_USER, PREFIX_FUNCTION, PREFIX_IMPORTANCE)
                        || !argMultimap.getPreamble().isEmpty()) {
                    throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            AddUserStoryToCommand.MESSAGE_USAGE));
                }

                UserStoryImportance importanceLevel = ParserUtil.parseStoryImportance(
                        argMultimap.getValue(PREFIX_IMPORTANCE).get());
                UserStoryUser user = ParserUtil.parseStoryUser(argMultimap.getValue(PREFIX_USER).get());
                UserStoryFunction func = ParserUtil.parseStoryFunction(argMultimap.getValue(PREFIX_FUNCTION).get());
                //since reason can be empty we initialise it to empty first then update it if present
                UserStoryReason reason = new UserStoryReason(UserStoryReason.DEFAULT_REASON);
                if (argMultimap.getValue(PREFIX_REASON).isPresent()) {
                    reason = ParserUtil.parseStoryReason(argMultimap.getValue(PREFIX_REASON).get());
                }
                UserStory story = new UserStory(importanceLevel, user, func, reason);

                return new AddUserStoryToCommand(projectName, story);
            } catch (ParseException pe) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddUserStoryToCommand.MESSAGE_USAGE), pe);
            }

        } else if (keyword.equals(AddTaskToCommand.ADD_TASK_KEYWORD)) {
            try {
                String s = " " + arguments; //add whitespace to allow tokenizer to detect regex
                ArgumentMultimap argMultimap =
                        ArgumentTokenizer.tokenize(s, PREFIX_NAME, PREFIX_MILESTONE);

                if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_MILESTONE)
                        || !argMultimap.getPreamble().isEmpty()) {
                    throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            AddTaskToCommand.MESSAGE_USAGE));
                }
                TaskName name = ParserUtil.parseTaskName(argMultimap.getValue(PREFIX_NAME).get());
                Task newTask = new Task(name);

                return new AddTaskToCommand(projectName, newTask);
            } catch (ParseException pe) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskToCommand.MESSAGE_USAGE), pe);
            }
        } else {
            throw new ParseException (
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddToCommand.MESSAGE_USAGE)
            );
        }
    }

}
