package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import static seedu.address.logic.commands.CommandTestUtil.CLIENT_DESC_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.CLIENT_DESC_ZULU;
import static seedu.address.logic.commands.CommandTestUtil.DEADLINE_DESC_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.DEADLINE_DESC_ZULU;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.FLEXI_DEADLINE_DESC_ZULU;
import static seedu.address.logic.commands.CommandTestUtil.GITHUB_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.GITHUB_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CLIENT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DEADLINE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_FLEXI_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_GITHUB2_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_GITHUB3_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_GITHUB_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PROJECT_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SKILL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_ZULU;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.SKILL_DESC_C;
import static seedu.address.logic.commands.CommandTestUtil.SKILL_DESC_JAVA;
import static seedu.address.logic.commands.CommandTestUtil.START_DESC_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.START_DESC_ZULU;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CLIENT_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FLEXIDATE_ZULU;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GITHUB_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LATE_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LATE_DAY_ZULU_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LATE_MONTH_ZULU_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LATE_YEAR_ZULU_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PROJECT_NAME_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SKILL_C;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SKILL_JAVA;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalEmployees.AMY;
import static seedu.address.testutil.TypicalEmployees.BOB;
import static seedu.address.testutil.TypicalProjects.PROJECT_ZULU;

import org.junit.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddEmployeeCommand;
import seedu.address.logic.commands.AddProjectCommand;
import seedu.address.model.employee.Email;
import seedu.address.model.employee.Employee;
import seedu.address.model.employee.EmployeeName;
import seedu.address.model.employee.GitHubAccount;
import seedu.address.model.employee.Phone;
import seedu.address.model.project.Client;
import seedu.address.model.project.Project;
import seedu.address.model.project.ProjectName;
import seedu.address.model.skill.Skill;
import seedu.address.model.util.PocketProjectDate;
import seedu.address.testutil.EmployeeBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Employee expectedEmployee = new EmployeeBuilder(BOB).withSkills(VALID_SKILL_C).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + AddEmployeeCommand.ADD_EMPLOYEE_KEYWORD
            + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + GITHUB_DESC_BOB + SKILL_DESC_C,
                new AddEmployeeCommand(expectedEmployee));

        // multiple names - last name accepted
        assertParseSuccess(parser, AddEmployeeCommand.ADD_EMPLOYEE_KEYWORD + NAME_DESC_AMY + NAME_DESC_BOB
            + PHONE_DESC_BOB + EMAIL_DESC_BOB + GITHUB_DESC_BOB + SKILL_DESC_C,
                new AddEmployeeCommand(expectedEmployee));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, AddEmployeeCommand.ADD_EMPLOYEE_KEYWORD + NAME_DESC_BOB + PHONE_DESC_AMY
            + PHONE_DESC_BOB + EMAIL_DESC_BOB + GITHUB_DESC_BOB + SKILL_DESC_C,
                new AddEmployeeCommand(expectedEmployee));

        // multiple emails - last email accepted
        assertParseSuccess(parser, AddEmployeeCommand.ADD_EMPLOYEE_KEYWORD + NAME_DESC_BOB + PHONE_DESC_BOB
            + EMAIL_DESC_AMY + EMAIL_DESC_BOB + GITHUB_DESC_BOB + SKILL_DESC_C,
                new AddEmployeeCommand(expectedEmployee));

        // multiple github - last github accepted
        assertParseSuccess(parser, AddEmployeeCommand.ADD_EMPLOYEE_KEYWORD + NAME_DESC_BOB + PHONE_DESC_BOB
            + EMAIL_DESC_BOB + GITHUB_DESC_AMY + GITHUB_DESC_BOB + SKILL_DESC_C,
                new AddEmployeeCommand(expectedEmployee));

        // multiple skills - all accepted
        Employee expectedEmployeeMultipleTags =
                new EmployeeBuilder(BOB).withSkills(VALID_SKILL_C, VALID_SKILL_JAVA)
                .build();
        assertParseSuccess(parser, AddEmployeeCommand.ADD_EMPLOYEE_KEYWORD + NAME_DESC_BOB + PHONE_DESC_BOB
            + EMAIL_DESC_BOB + GITHUB_DESC_BOB + SKILL_DESC_JAVA + SKILL_DESC_C,
                new AddEmployeeCommand(expectedEmployeeMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Employee expectedEmployee = new EmployeeBuilder(AMY).withSkills().build();
        assertParseSuccess(parser, AddEmployeeCommand.ADD_EMPLOYEE_KEYWORD + NAME_DESC_AMY + PHONE_DESC_AMY
            + EMAIL_DESC_AMY + GITHUB_DESC_AMY,
                new AddEmployeeCommand(expectedEmployee));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEmployeeCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, AddEmployeeCommand.ADD_EMPLOYEE_KEYWORD + " " + VALID_NAME_BOB
            + PHONE_DESC_BOB + EMAIL_DESC_BOB + GITHUB_DESC_BOB, expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, AddEmployeeCommand.ADD_EMPLOYEE_KEYWORD + NAME_DESC_BOB
            + VALID_PHONE_BOB + EMAIL_DESC_BOB + GITHUB_DESC_BOB, expectedMessage);

        // missing email prefix
        assertParseFailure(parser, AddEmployeeCommand.ADD_EMPLOYEE_KEYWORD + NAME_DESC_BOB
            + PHONE_DESC_BOB + VALID_EMAIL_BOB + GITHUB_DESC_BOB, expectedMessage);

        // missing github prefix
        assertParseFailure(parser, AddEmployeeCommand.ADD_EMPLOYEE_KEYWORD + NAME_DESC_BOB
            + PHONE_DESC_BOB + EMAIL_DESC_BOB + VALID_GITHUB_BOB, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, AddEmployeeCommand.ADD_EMPLOYEE_KEYWORD + " " + VALID_NAME_BOB
            + VALID_PHONE_BOB + VALID_EMAIL_BOB + VALID_GITHUB_BOB, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, AddEmployeeCommand.ADD_EMPLOYEE_KEYWORD + INVALID_NAME_DESC + PHONE_DESC_BOB
            + EMAIL_DESC_BOB + GITHUB_DESC_BOB + SKILL_DESC_JAVA + SKILL_DESC_C, EmployeeName.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, AddEmployeeCommand.ADD_EMPLOYEE_KEYWORD + NAME_DESC_BOB + INVALID_PHONE_DESC
            + EMAIL_DESC_BOB + GITHUB_DESC_BOB + SKILL_DESC_JAVA + SKILL_DESC_C, Phone.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, AddEmployeeCommand.ADD_EMPLOYEE_KEYWORD + NAME_DESC_BOB + PHONE_DESC_BOB
            + INVALID_EMAIL_DESC + GITHUB_DESC_BOB + SKILL_DESC_JAVA
            + SKILL_DESC_C, Email.MESSAGE_CONSTRAINTS);

        // invalid github
        assertParseFailure(parser, AddEmployeeCommand.ADD_EMPLOYEE_KEYWORD + NAME_DESC_BOB + PHONE_DESC_BOB
            + EMAIL_DESC_BOB + INVALID_GITHUB_DESC + SKILL_DESC_JAVA
            + SKILL_DESC_C, GitHubAccount.MESSAGE_CONSTRAINTS);

        // invalid github 2
        assertParseFailure(parser, AddEmployeeCommand.ADD_EMPLOYEE_KEYWORD + NAME_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + INVALID_GITHUB2_DESC + SKILL_DESC_JAVA
                + SKILL_DESC_C, GitHubAccount.MESSAGE_CONSTRAINTS);

        // invalid github test 3
        assertParseFailure(parser, AddEmployeeCommand.ADD_EMPLOYEE_KEYWORD + NAME_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + INVALID_GITHUB3_DESC + SKILL_DESC_JAVA
                + SKILL_DESC_C, GitHubAccount.MESSAGE_CONSTRAINTS);

        // invalid skill
        assertParseFailure(parser, AddEmployeeCommand.ADD_EMPLOYEE_KEYWORD + NAME_DESC_BOB + PHONE_DESC_BOB
            + EMAIL_DESC_BOB + GITHUB_DESC_BOB + INVALID_SKILL_DESC + SKILL_DESC_C, Skill.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, AddEmployeeCommand.ADD_EMPLOYEE_KEYWORD + INVALID_NAME_DESC + PHONE_DESC_BOB
            + EMAIL_DESC_BOB + INVALID_GITHUB_DESC, EmployeeName.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + " " + AddEmployeeCommand.ADD_EMPLOYEE_KEYWORD
            + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
            + GITHUB_DESC_BOB + SKILL_DESC_JAVA + SKILL_DESC_C,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_allFieldsPresentProject_success() {
        Project expectedProject = PROJECT_ZULU;

        // whitespace only preamble fixed date
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + AddProjectCommand.ADD_PROJECT_KEYWORD
            + NAME_DESC_ZULU + CLIENT_DESC_ZULU + START_DESC_ZULU + DEADLINE_DESC_ZULU,
                 new AddProjectCommand(expectedProject));

        // whitespace only preamble flexible date
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + AddProjectCommand.ADD_PROJECT_KEYWORD
            + NAME_DESC_ZULU + CLIENT_DESC_ZULU + START_DESC_ZULU + FLEXI_DEADLINE_DESC_ZULU,
                 new AddProjectCommand(expectedProject));
    }

    @Test
    public void parse_allFieldPresentRandomlyProject_success() {
        Project expectedProject = PROJECT_ZULU;

        // whitespace only preamble fixed date
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + AddProjectCommand.ADD_PROJECT_KEYWORD
            + CLIENT_DESC_ZULU + DEADLINE_DESC_ZULU + NAME_DESC_ZULU + START_DESC_ZULU,
                 new AddProjectCommand(expectedProject));

        // whitespace only preamble flexible date
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + AddProjectCommand.ADD_PROJECT_KEYWORD
            + START_DESC_ZULU + CLIENT_DESC_ZULU + FLEXI_DEADLINE_DESC_ZULU
            + NAME_DESC_ZULU, new AddProjectCommand(expectedProject));
    }

    @Test
    public void parse_startAndEndDate_failure() {
        Project expectedProject = PROJECT_ZULU;

        //start date day field later than deadline day field
        assertParseFailure(parser, PREAMBLE_WHITESPACE + AddProjectCommand.ADD_PROJECT_KEYWORD
            + NAME_DESC_ZULU + CLIENT_DESC_ZULU + VALID_LATE_DAY_ZULU_DESC + DEADLINE_DESC_ZULU,
                 PocketProjectDate.START_END_DATE_CONSTRAINTS);

        //start date month field later than deadline month field
        assertParseFailure(parser, PREAMBLE_WHITESPACE + AddProjectCommand.ADD_PROJECT_KEYWORD
            + NAME_DESC_ZULU + CLIENT_DESC_ZULU + VALID_LATE_MONTH_ZULU_DESC + DEADLINE_DESC_ZULU,
                 PocketProjectDate.START_END_DATE_CONSTRAINTS);

        //start date year field later than deadline year field
        assertParseFailure(parser, PREAMBLE_WHITESPACE + AddProjectCommand.ADD_PROJECT_KEYWORD
            + NAME_DESC_ZULU + CLIENT_DESC_ZULU + VALID_LATE_YEAR_ZULU_DESC + DEADLINE_DESC_ZULU,
                 PocketProjectDate.START_END_DATE_CONSTRAINTS);

        //start date later than deadline all fields
        assertParseFailure(parser, PREAMBLE_WHITESPACE + AddProjectCommand.ADD_PROJECT_KEYWORD
            + NAME_DESC_ZULU + CLIENT_DESC_ZULU + VALID_LATE_DATE_DESC + DEADLINE_DESC_ZULU,
                 PocketProjectDate.START_END_DATE_CONSTRAINTS);
    }


    @Test
    public void parse_compulsoryFieldMissingProject_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddProjectCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, AddProjectCommand.ADD_PROJECT_KEYWORD + " " + VALID_PROJECT_NAME_ALICE
                + CLIENT_DESC_ALICE + DEADLINE_DESC_ALICE, expectedMessage);

        // missing client prefix
        assertParseFailure(parser, AddProjectCommand.ADD_PROJECT_KEYWORD + NAME_DESC_ALICE
                + VALID_CLIENT_ALICE + DEADLINE_DESC_ALICE, expectedMessage);

        // missing date prefix
        assertParseFailure(parser, AddProjectCommand.ADD_PROJECT_KEYWORD + NAME_DESC_ALICE
                + CLIENT_DESC_ALICE + VALID_DEADLINE_ALICE, expectedMessage);

        // missing deadline prefix
        assertParseFailure(parser, AddProjectCommand.ADD_PROJECT_KEYWORD + NAME_DESC_ALICE
                + CLIENT_DESC_ALICE + VALID_FLEXIDATE_ZULU, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, AddProjectCommand.ADD_PROJECT_KEYWORD + " " + VALID_PROJECT_NAME_ALICE
                + VALID_CLIENT_ALICE + VALID_DEADLINE_ALICE, expectedMessage);
    }

    @Test
    public void parse_invalidValueProject_failure() {
        // invalid project name
        assertParseFailure(parser, AddProjectCommand.ADD_PROJECT_KEYWORD + INVALID_PROJECT_NAME_DESC
            + CLIENT_DESC_ALICE + START_DESC_ALICE + DEADLINE_DESC_ALICE, ProjectName.MESSAGE_CONSTRAINTS);

        // invalid client
        assertParseFailure(parser, AddProjectCommand.ADD_PROJECT_KEYWORD + NAME_DESC_ALICE
            + INVALID_CLIENT_DESC + START_DESC_ALICE + DEADLINE_DESC_ALICE, Client.MESSAGE_CONSTRAINTS);
        // invalid deadline fixed format
        assertParseFailure(parser, AddProjectCommand.ADD_PROJECT_KEYWORD + NAME_DESC_ALICE + CLIENT_DESC_ALICE
                + START_DESC_ALICE + INVALID_DEADLINE_DESC, PocketProjectDate.MESSAGE_CONSTRAINTS);

        // invalid deadline flexible format
        assertParseFailure(parser, AddProjectCommand.ADD_PROJECT_KEYWORD + NAME_DESC_ALICE + CLIENT_DESC_ALICE
                + START_DESC_ALICE + INVALID_FLEXI_DATE_DESC, PocketProjectDate.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, AddProjectCommand.ADD_PROJECT_KEYWORD + INVALID_PROJECT_NAME_DESC
                + INVALID_CLIENT_DESC + START_DESC_ALICE + DEADLINE_DESC_ALICE, ProjectName.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + " " + AddProjectCommand.ADD_PROJECT_KEYWORD
                        + NAME_DESC_ALICE + CLIENT_DESC_ALICE + START_DESC_ALICE + DEADLINE_DESC_ALICE,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }

}
