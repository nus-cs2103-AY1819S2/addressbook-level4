package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.FindAllCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FindDeadlineCommand;
import seedu.address.logic.commands.FindEmployeeCommand;
import seedu.address.logic.commands.FindProjectCommand;
import seedu.address.logic.commands.FindSkillCommand;
import seedu.address.model.employee.EmployeeNameContainsKeywordsPredicate;
import seedu.address.model.project.ProjectContainsDeadlinePredicate;
import seedu.address.model.project.ProjectContainsKeywordsPredicate;
import seedu.address.model.project.ProjectNameContainsKeywordsPredicate;
import seedu.address.model.skill.EmployeeSkillContainsKeywordsPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, FindEmployeeCommand.FIND_EMPLOYEE_KEYWORD + "  ",
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));

        assertParseFailure(parser, " ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            FindCommand.MESSAGE_USAGE));

        assertParseFailure(parser, FindProjectCommand.FIND_PROJECT_KEYWORD + "  ",
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));

        assertParseFailure(parser, FindSkillCommand.FIND_SKILL_KEYWORD + "  ",
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));

        assertParseFailure(parser, FindAllCommand.FIND_ALL_KEYWORD + "  ",
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));

        assertParseFailure(parser, FindDeadlineCommand.FIND_DEADLINE_KEYWORD + " " ,
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
            new FindEmployeeCommand(new EmployeeNameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, FindEmployeeCommand.FIND_EMPLOYEE_KEYWORD
            + " Alice Bob", expectedFindCommand);

        assertParseSuccess(parser, "employee \t Alice \t Bob  \t", expectedFindCommand);

        expectedFindCommand =
            new FindProjectCommand(new ProjectNameContainsKeywordsPredicate(Arrays.asList("Apollo", "Gemini")));
        assertParseSuccess(parser, FindProjectCommand.FIND_PROJECT_KEYWORD + " Apollo Gemini",
            expectedFindCommand);
        assertParseSuccess(parser, "project \t Apollo  Gemini \t", expectedFindCommand);

        expectedFindCommand =
            new FindSkillCommand(new EmployeeSkillContainsKeywordsPredicate(Arrays.asList("java", "html")));
        assertParseSuccess(parser, FindSkillCommand.FIND_SKILL_KEYWORD + " java html",
            expectedFindCommand);
        assertParseSuccess(parser, "skill \t java \t html  \t", expectedFindCommand);

        expectedFindCommand =
            new FindAllCommand(new ProjectContainsKeywordsPredicate(Arrays.asList("hey" , "alice")));
        assertParseSuccess(parser, FindAllCommand.FIND_ALL_KEYWORD + " hey alice", expectedFindCommand);
        assertParseSuccess(parser, "all \t hey  \t alice \t", expectedFindCommand);

        expectedFindCommand =
            new FindDeadlineCommand(new ProjectContainsDeadlinePredicate("12/11/2010"));
        assertParseSuccess(parser, FindDeadlineCommand.FIND_DEADLINE_KEYWORD + " 12/11/2010",
            expectedFindCommand);
        assertParseSuccess(parser, "deadline \t    12/11/2010     ", expectedFindCommand);
    }

}
