package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FUNCTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_IMPORTANCE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MILESTONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REASON;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USER;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EMPLOYEE;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PROJECT;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PROJECT_MILESTONE;
import static seedu.address.testutil.TypicalProjectNames.TYPICAL_PROJECT_NAME_1;
import static seedu.address.testutil.TypicalProjectTasks.PROJECT_TASK_DO_SOMETHING;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.AddEmployeeCommand;
import seedu.address.logic.commands.AddEmployeeToCommand;
import seedu.address.logic.commands.AddMilestoneToCommand;
import seedu.address.logic.commands.AddProjectCommand;
import seedu.address.logic.commands.AddProjectTaskToCommand;
import seedu.address.logic.commands.AddUserStoryToCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteEmployeeCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditEmployeeCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindEmployeeCommand;
import seedu.address.logic.commands.FindProjectCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ListEmployeeCommand;
import seedu.address.logic.commands.ListProjectCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.ViewCommand;
import seedu.address.logic.commands.ViewEmployeeCommand;
import seedu.address.logic.commands.ViewProjectCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.employee.Employee;
import seedu.address.model.employee.EmployeeNameContainsKeywordsPredicate;
import seedu.address.model.project.Milestone;
import seedu.address.model.project.MilestoneDescription;
import seedu.address.model.project.Project;
import seedu.address.model.project.ProjectNameContainsKeywordsPredicate;
import seedu.address.model.project.UserStory;
import seedu.address.model.project.UserStoryFunction;
import seedu.address.model.project.UserStoryImportance;
import seedu.address.model.project.UserStoryReason;
import seedu.address.model.project.UserStoryUser;
import seedu.address.model.util.PocketProjectDate;
import seedu.address.testutil.EditEmployeeDescriptorBuilder;
import seedu.address.testutil.EmployeeBuilder;
import seedu.address.testutil.EmployeeUtil;
import seedu.address.testutil.ProjectBuilder;
import seedu.address.testutil.ProjectUtil;

public class PocketProjectParserTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final PocketProjectParser parser = new PocketProjectParser();

    @Test
    public void parseCommand_add() throws Exception {

        //add employee
        Employee employee = new EmployeeBuilder().build();
        AddEmployeeCommand command = (AddEmployeeCommand) parser.parseCommand
            (EmployeeUtil.getAddEmployeeCommand(employee));
        assertEquals(new AddEmployeeCommand(employee), command);

        //add project
        Project project = new ProjectBuilder().build();
        AddProjectCommand commandProject = (AddProjectCommand) parser.parseCommand
            (ProjectUtil.getAddProjectCommand(project));
        assertEquals(new AddProjectCommand(project), commandProject);

    }

    @Test
    public void parseCommand_addTo() throws Exception {

        //add employee to project
        AddEmployeeToCommand commandEmployee = (AddEmployeeToCommand) parser.parseCommand
            (AddEmployeeToCommand.COMMAND_WORD + " "
            + TYPICAL_PROJECT_NAME_1 + " " + AddEmployeeToCommand.ADD_EMPLOYEE_KEYWORD + " "
            + INDEX_FIRST_EMPLOYEE.getOneBased());
        assertEquals(new AddEmployeeToCommand(INDEX_FIRST_EMPLOYEE, TYPICAL_PROJECT_NAME_1), commandEmployee);

        //add milestone to project
        Milestone milestoneToTest = new Milestone(new MilestoneDescription("Completed UG"),
             new PocketProjectDate("23/05/2019"));
        AddMilestoneToCommand commandMilestone = (AddMilestoneToCommand) parser.parseCommand
            (AddMilestoneToCommand.COMMAND_WORD + " "
            + TYPICAL_PROJECT_NAME_1 + " " + AddMilestoneToCommand.ADD_MILESTONE_KEYWORD + " "
            + PREFIX_MILESTONE + "Completed UG" + " " + PREFIX_DATE + "23/05/2019");
        assertEquals(new AddMilestoneToCommand(TYPICAL_PROJECT_NAME_1, milestoneToTest), commandMilestone);

        //add userstory to project
        UserStory userStoryToTest = new UserStory(new UserStoryImportance("3"), new UserStoryUser("user"),
            new UserStoryFunction("test apps"), new UserStoryReason("fix bugs"));
        AddUserStoryToCommand commandUserStory = (AddUserStoryToCommand) parser.parseCommand
                (AddUserStoryToCommand.COMMAND_WORD + " "
                + TYPICAL_PROJECT_NAME_1 + " " + AddUserStoryToCommand.ADD_USERSTORY_KEYWORD + " "
                + PREFIX_USER + "user" + " " + PREFIX_FUNCTION + "test apps" + " " + PREFIX_REASON + "fix bugs"
                + " " + PREFIX_IMPORTANCE + "3");
        assertEquals(new AddUserStoryToCommand(TYPICAL_PROJECT_NAME_1, userStoryToTest), commandUserStory);


        //add task to project
        final String taskName = "Do something";
        AddProjectTaskToCommand commandTask = (AddProjectTaskToCommand) parser.parseCommand
            (AddProjectTaskToCommand.COMMAND_WORD + " "
            + TYPICAL_PROJECT_NAME_1 + " " + AddProjectTaskToCommand.ADD_PROJECTTASK_KEYWORD + " "
            + PREFIX_NAME + taskName + " " + PREFIX_MILESTONE + INDEX_FIRST_PROJECT_MILESTONE.getOneBased());
        assertEquals(new AddProjectTaskToCommand(TYPICAL_PROJECT_NAME_1, PROJECT_TASK_DO_SOMETHING,
            INDEX_FIRST_PROJECT_MILESTONE), commandTask);


    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
            DeleteCommand.COMMAND_WORD + " " + FindEmployeeCommand.FIND_EMPLOYEE_KEYWORD + " "
                + INDEX_FIRST_EMPLOYEE.getOneBased());
        assertEquals(new DeleteEmployeeCommand(INDEX_FIRST_EMPLOYEE), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Employee employee = new EmployeeBuilder().build();
        EditEmployeeCommand.EditEmployeeDescriptor descriptor = new EditEmployeeDescriptorBuilder(employee).build();
        EditEmployeeCommand command = (EditEmployeeCommand) parser.parseCommand(
            EditCommand.COMMAND_WORD + " " + EditEmployeeCommand.EDIT_EMPLOYEE_KEYWORD + " "
                + INDEX_FIRST_EMPLOYEE.getOneBased() + " " + EmployeeUtil.getEditEmployeeDescriptorDetails(descriptor));
        assertEquals(new EditEmployeeCommand(INDEX_FIRST_EMPLOYEE, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindEmployeeCommand employeeCommand = (FindEmployeeCommand) parser.parseCommand(
            FindEmployeeCommand.COMMAND_WORD + " " + FindEmployeeCommand.FIND_EMPLOYEE_KEYWORD + " "
                + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindEmployeeCommand(new EmployeeNameContainsKeywordsPredicate(keywords)), employeeCommand);

        FindProjectCommand projectCommand = (FindProjectCommand) parser.parseCommand(
            FindProjectCommand.COMMAND_WORD + " " + FindProjectCommand.FIND_PROJECT_KEYWORD + " "
                + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindProjectCommand(new ProjectNameContainsKeywordsPredicate(keywords)), projectCommand);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_history() throws Exception {
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD) instanceof HistoryCommand);
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD + " 3") instanceof HistoryCommand);

        try {
            parser.parseCommand("histories");
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_UNKNOWN_COMMAND, pe.getMessage());
        }
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " "
            + ListEmployeeCommand.LIST_EMPLOYEE_KEYWORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " "
            + ListEmployeeCommand.LIST_EMPLOYEE_KEYWORD) instanceof ListEmployeeCommand);

        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " "
            + ListProjectCommand.LIST_PROJECT_KEYWORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " "
            + ListProjectCommand.LIST_PROJECT_KEYWORD) instanceof ListProjectCommand);
    }

    @Test
    public void parseCommand_view() throws Exception {
        ViewEmployeeCommand employeeCommand = (ViewEmployeeCommand) parser.parseCommand(
            ViewCommand.COMMAND_WORD + " " + ViewEmployeeCommand.VIEW_EMPLOYEE_KEYWORD + " "
                + INDEX_FIRST_EMPLOYEE.getOneBased());
        assertEquals(new ViewEmployeeCommand(INDEX_FIRST_EMPLOYEE), employeeCommand);
        ViewProjectCommand projectCommand = (ViewProjectCommand) parser.parseCommand(
            ViewCommand.COMMAND_WORD + " " + ViewProjectCommand.VIEW_PROJECT_KEYWORD + " "
                + INDEX_FIRST_PROJECT.getOneBased());
        assertEquals(new ViewProjectCommand(INDEX_FIRST_PROJECT), projectCommand);
    }

    @Test
    public void parseCommand_redoCommandWord_returnsRedoCommand() throws Exception {
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_WORD) instanceof RedoCommand);
        assertTrue(parser.parseCommand("redo 1") instanceof RedoCommand);
    }

    @Test
    public void parseCommand_undoCommandWord_returnsUndoCommand() throws Exception {
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_WORD) instanceof UndoCommand);
        assertTrue(parser.parseCommand("undo 3") instanceof UndoCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        parser.parseCommand("");
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_UNKNOWN_COMMAND);
        parser.parseCommand("unknownCommand");
    }
}
