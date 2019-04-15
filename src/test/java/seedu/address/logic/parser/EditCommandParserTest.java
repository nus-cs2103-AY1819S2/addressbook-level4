package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.CLIENT_DESC_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.CLIENT_DESC_ZULU;
import static seedu.address.logic.commands.CommandTestUtil.DEADLINE_DESC_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.DEADLINE_DESC_ZULU;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.FLEXI_DEADLINE_DESC_ZULU;
import static seedu.address.logic.commands.CommandTestUtil.GITHUB_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.GITHUB_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CLIENT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DEADLINE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DESCRIPTION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_FLEXI_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_GITHUB_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PROJECT_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_ZULU;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.SKILL_DESC_C;
import static seedu.address.logic.commands.CommandTestUtil.SKILL_DESC_JAVA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CLIENT_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CLIENT_ZULU;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_ZULU;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FLEXIDATE_ZULU;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GITHUB_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GITHUB_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PROJECT_NAME_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PROJECT_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PROJECT_NAME_ZULU;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SKILL_C;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SKILL_JAVA;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SKILL;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EMPLOYEE;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_EMPLOYEE;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_EMPLOYEE;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditEmployeeCommand;
import seedu.address.logic.commands.EditProjectCommand;
import seedu.address.logic.commands.EditProjectInfoCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.employee.Email;
import seedu.address.model.employee.EmployeeName;
import seedu.address.model.employee.GitHubAccount;
import seedu.address.model.employee.Phone;
import seedu.address.model.project.Client;
import seedu.address.model.project.ProjectDescription;
import seedu.address.model.project.ProjectName;
import seedu.address.model.skill.Skill;
import seedu.address.model.util.PocketProjectDate;
import seedu.address.testutil.EditEmployeeDescriptorBuilder;
import seedu.address.testutil.EditProjectDescriptorBuilder;

public class EditCommandParserTest {

    private static final String SKILL_EMPTY = " " + PREFIX_SKILL;

    private static final String MESSAGE_EMPLOYEE_INVALID_FORMAT =
        String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditEmployeeCommand.MESSAGE_USAGE);

    private static final String MESSAGE_PROJECT_INVALID_FORMAT =
        String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditProjectCommand.MESSAGE_USAGE);


    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, EditEmployeeCommand.EDIT_EMPLOYEE_KEYWORD + " " + VALID_NAME_AMY,
            MESSAGE_EMPLOYEE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, EditEmployeeCommand.EDIT_EMPLOYEE_KEYWORD + " 1",
            EditEmployeeCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, EditEmployeeCommand.EDIT_EMPLOYEE_KEYWORD + "",
            MESSAGE_EMPLOYEE_INVALID_FORMAT);

        //=========== EditProjectCommand ==============================================================================
        // no project name specified
        assertParseFailure(parser, EditProjectCommand.EDIT_PROJECT_KEYWORD + " "
                + EditProjectInfoCommand.EDIT_INFO_KEYWORD + " n/ " + VALID_PROJECT_NAME_ALICE,
            MESSAGE_PROJECT_INVALID_FORMAT);
        // no field specified
        assertParseFailure(parser, EditProjectCommand.EDIT_PROJECT_KEYWORD + " " + VALID_PROJECT_NAME_BOB,
            MESSAGE_PROJECT_INVALID_FORMAT);
        // no name and no field specified
        assertParseFailure(parser, EditProjectCommand.EDIT_PROJECT_KEYWORD + "" ,
            MESSAGE_PROJECT_INVALID_FORMAT);

    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, EditEmployeeCommand.EDIT_EMPLOYEE_KEYWORD + " -5" + NAME_DESC_AMY,
            MESSAGE_EMPLOYEE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, EditEmployeeCommand.EDIT_EMPLOYEE_KEYWORD + " 0" + NAME_DESC_AMY,
            MESSAGE_EMPLOYEE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, EditEmployeeCommand.EDIT_EMPLOYEE_KEYWORD + " 1 some random string",
            MESSAGE_EMPLOYEE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, EditEmployeeCommand.EDIT_EMPLOYEE_KEYWORD + " 1 i/ string",
            MESSAGE_EMPLOYEE_INVALID_FORMAT);

        //=========== EditProjectCommand ==============================================================================
        // invalid project name
        assertParseFailure(parser, EditProjectCommand.EDIT_PROJECT_KEYWORD + " " + " 23423/34///`"
                + EditProjectInfoCommand.EDIT_INFO_KEYWORD + " n/" + VALID_PROJECT_NAME_ALICE ,
            MESSAGE_PROJECT_INVALID_FORMAT);

        //missing keyword arguments
        assertParseFailure(parser, EditProjectCommand.EDIT_PROJECT_KEYWORD + " "
            + VALID_PROJECT_NAME_ALICE + " " + "Alice" + " n/John", MESSAGE_PROJECT_INVALID_FORMAT);

        //invalid argument
        assertParseFailure(parser, EditProjectCommand.EDIT_PROJECT_KEYWORD + " " + VALID_PROJECT_NAME_ALICE
            + EditProjectInfoCommand.EDIT_INFO_KEYWORD + " lalalalalal" , MESSAGE_PROJECT_INVALID_FORMAT);

        //invalid prefix
        assertParseFailure(parser, EditProjectCommand.EDIT_PROJECT_KEYWORD
                + " " + VALID_PROJECT_NAME_ALICE + " " + EditProjectInfoCommand.EDIT_INFO_KEYWORD + " j/john",
            MESSAGE_PROJECT_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, EditEmployeeCommand.EDIT_EMPLOYEE_KEYWORD + " 1"
            + INVALID_NAME_DESC, EmployeeName.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, EditEmployeeCommand.EDIT_EMPLOYEE_KEYWORD + " 1"
            + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS); // invalid phone
        assertParseFailure(parser, EditEmployeeCommand.EDIT_EMPLOYEE_KEYWORD + " 1"
            + INVALID_EMAIL_DESC, Email.MESSAGE_CONSTRAINTS); // invalid email
        assertParseFailure(parser, EditEmployeeCommand.EDIT_EMPLOYEE_KEYWORD + " 1"
            + INVALID_GITHUB_DESC, GitHubAccount.MESSAGE_CONSTRAINTS); //invalid gh

        // invalid phone followed by valid email
        assertParseFailure(parser, EditEmployeeCommand.EDIT_EMPLOYEE_KEYWORD + " 1" + INVALID_PHONE_DESC
            + EMAIL_DESC_AMY, Phone.MESSAGE_CONSTRAINTS);

        // valid phone followed by invalid phone. The test case for invalid phone followed by valid phone
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, EditEmployeeCommand.EDIT_EMPLOYEE_KEYWORD + " 1" + PHONE_DESC_BOB
            + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS);

        // while parsing {@code PREFIX_SKILL} alone will reset the skills of the {@code Employee} being edited,
        // parsing it together with a valid skill results in error
        assertParseFailure(parser, EditEmployeeCommand.EDIT_EMPLOYEE_KEYWORD + " 1" + SKILL_DESC_C
                + SKILL_DESC_JAVA + SKILL_EMPTY,
            Skill.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, EditEmployeeCommand.EDIT_EMPLOYEE_KEYWORD + " 1" + SKILL_DESC_C
                + SKILL_EMPTY + SKILL_DESC_JAVA,
            Skill.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, EditEmployeeCommand.EDIT_EMPLOYEE_KEYWORD + " 1" + SKILL_EMPTY
                + SKILL_DESC_C + SKILL_DESC_JAVA,
            Skill.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, EditEmployeeCommand.EDIT_EMPLOYEE_KEYWORD + " 1" + INVALID_NAME_DESC
            + INVALID_EMAIL_DESC + VALID_GITHUB_AMY + VALID_PHONE_AMY, EmployeeName.MESSAGE_CONSTRAINTS);

        //=========== EditProjectCommand ==============================================================================

        assertParseFailure(parser, EditProjectCommand.EDIT_PROJECT_KEYWORD + " " + VALID_PROJECT_NAME_ALICE
                + " " + EditProjectInfoCommand.EDIT_INFO_KEYWORD + INVALID_PROJECT_NAME_DESC,
            ProjectName.MESSAGE_CONSTRAINTS); //invalid name
        assertParseFailure(parser, EditProjectCommand.EDIT_PROJECT_KEYWORD + " " + VALID_PROJECT_NAME_ALICE
                + " " + EditProjectInfoCommand.EDIT_INFO_KEYWORD + INVALID_CLIENT_DESC,
            Client.MESSAGE_CONSTRAINTS); //invalid client
        assertParseFailure(parser, EditProjectCommand.EDIT_PROJECT_KEYWORD + " " + VALID_PROJECT_NAME_ALICE
                + " " + EditProjectInfoCommand.EDIT_INFO_KEYWORD + INVALID_DEADLINE_DESC,
            PocketProjectDate.MESSAGE_CONSTRAINTS); //invalid deadline
        assertParseFailure(parser, EditProjectCommand.EDIT_PROJECT_KEYWORD + " " + VALID_PROJECT_NAME_ALICE
                + " " + EditProjectInfoCommand.EDIT_INFO_KEYWORD + INVALID_FLEXI_DATE_DESC,
            PocketProjectDate.MESSAGE_CONSTRAINTS); //invalid deadline
        assertParseFailure(parser, EditProjectCommand.EDIT_PROJECT_KEYWORD + " " + VALID_PROJECT_NAME_ALICE
                + " " + EditProjectInfoCommand.EDIT_INFO_KEYWORD + INVALID_DESCRIPTION_DESC,
            ProjectDescription.MESSAGE_CONSTRAINTS); //invalid description
    }

    @Test
    public void parse_allFieldsSpecified_success() throws ParseException {
        Index targetIndex = INDEX_SECOND_EMPLOYEE;
        String userInput = EditEmployeeCommand.EDIT_EMPLOYEE_KEYWORD + " " + targetIndex.getOneBased()
            + PHONE_DESC_BOB + SKILL_DESC_JAVA + EMAIL_DESC_AMY + GITHUB_DESC_AMY + NAME_DESC_AMY + SKILL_DESC_C;

        EditEmployeeCommand.EditEmployeeDescriptor descriptor = new EditEmployeeDescriptorBuilder()
            .withName(VALID_NAME_AMY).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_AMY)
            .withGithubAccount(VALID_GITHUB_AMY).withSkills(VALID_SKILL_JAVA, VALID_SKILL_C).build();
        EditEmployeeCommand expectedCommand = new EditEmployeeCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);

        //=========== EditProjectCommand ==============================================================================

        ProjectName targetName = new ProjectName(VALID_PROJECT_NAME_ZULU);
        userInput = EditProjectCommand.EDIT_PROJECT_KEYWORD + " " + VALID_PROJECT_NAME_ZULU + " "
            + EditProjectInfoCommand.EDIT_INFO_KEYWORD + NAME_DESC_ALICE + CLIENT_DESC_ZULU
            + DEADLINE_DESC_ALICE + DESCRIPTION_DESC;

        EditProjectInfoCommand.EditProjectDescriptor descriptorProj = new EditProjectDescriptorBuilder()
            .withName(VALID_PROJECT_NAME_ALICE).withClient(VALID_CLIENT_ZULU).withDeadline(VALID_DEADLINE_ALICE)
            .withDescription(VALID_DESCRIPTION).build();
        EditProjectInfoCommand expectedProjectCommand = new EditProjectInfoCommand(targetName, descriptorProj);

        assertParseSuccess(parser, userInput, expectedProjectCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() throws ParseException {
        Index targetIndex = INDEX_FIRST_EMPLOYEE;
        String userInput = EditEmployeeCommand.EDIT_EMPLOYEE_KEYWORD + " " + targetIndex.getOneBased() + PHONE_DESC_BOB
            + EMAIL_DESC_AMY;

        EditEmployeeCommand.EditEmployeeDescriptor descriptor = new EditEmployeeDescriptorBuilder()
            .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_AMY).build();
        EditEmployeeCommand expectedCommand = new EditEmployeeCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);

        //=========== EditProjectCommand ==============================================================================

        ProjectName targetName = new ProjectName(VALID_PROJECT_NAME_ZULU);
        userInput = EditProjectCommand.EDIT_PROJECT_KEYWORD + " " + VALID_PROJECT_NAME_ZULU + " "
            + EditProjectInfoCommand.EDIT_INFO_KEYWORD + NAME_DESC_ALICE + CLIENT_DESC_ZULU;

        EditProjectInfoCommand.EditProjectDescriptor descriptorProj = new EditProjectDescriptorBuilder()
            .withName(VALID_PROJECT_NAME_ALICE).withClient(VALID_CLIENT_ZULU).build();
        EditProjectInfoCommand expectedProjectCommand = new EditProjectInfoCommand(targetName, descriptorProj);

        assertParseSuccess(parser, userInput, expectedProjectCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() throws ParseException {
        // name
        Index targetIndex = INDEX_THIRD_EMPLOYEE;
        String userInput = EditEmployeeCommand.EDIT_EMPLOYEE_KEYWORD + " " + targetIndex.getOneBased() + NAME_DESC_AMY;
        EditEmployeeCommand.EditEmployeeDescriptor descriptor =
            new EditEmployeeDescriptorBuilder().withName(VALID_NAME_AMY).build();
        EditEmployeeCommand expectedCommand = new EditEmployeeCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // phone
        userInput = EditEmployeeCommand.EDIT_EMPLOYEE_KEYWORD + " " + targetIndex.getOneBased() + PHONE_DESC_AMY;
        descriptor = new EditEmployeeDescriptorBuilder().withPhone(VALID_PHONE_AMY).build();
        expectedCommand = new EditEmployeeCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // email
        userInput = EditEmployeeCommand.EDIT_EMPLOYEE_KEYWORD + " " + targetIndex.getOneBased() + EMAIL_DESC_AMY;
        descriptor = new EditEmployeeDescriptorBuilder().withEmail(VALID_EMAIL_AMY).build();
        expectedCommand = new EditEmployeeCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // github
        userInput = EditEmployeeCommand.EDIT_EMPLOYEE_KEYWORD + " " + targetIndex.getOneBased() + GITHUB_DESC_AMY;
        descriptor = new EditEmployeeDescriptorBuilder().withGithubAccount(VALID_GITHUB_AMY).build();
        expectedCommand = new EditEmployeeCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // skills
        userInput = EditEmployeeCommand.EDIT_EMPLOYEE_KEYWORD + " " + targetIndex.getOneBased() + SKILL_DESC_C;
        descriptor = new EditEmployeeDescriptorBuilder().withSkills(VALID_SKILL_C).build();
        expectedCommand = new EditEmployeeCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        //=========== EditProjectCommand ==============================================================================

        //name
        ProjectName targetName = new ProjectName(VALID_PROJECT_NAME_ZULU);
        userInput = EditProjectCommand.EDIT_PROJECT_KEYWORD + " " + VALID_PROJECT_NAME_ZULU + " "
            + EditProjectInfoCommand.EDIT_INFO_KEYWORD + NAME_DESC_ALICE;
        EditProjectInfoCommand.EditProjectDescriptor projectDescriptor = new EditProjectDescriptorBuilder()
            .withName(VALID_PROJECT_NAME_ALICE).build();
        EditProjectInfoCommand expectedProjCommand = new EditProjectInfoCommand(targetName, projectDescriptor);
        assertParseSuccess(parser, userInput, expectedProjCommand);

        //client
        userInput = EditProjectCommand.EDIT_PROJECT_KEYWORD + " " + VALID_PROJECT_NAME_ZULU + " "
            + EditProjectInfoCommand.EDIT_INFO_KEYWORD + CLIENT_DESC_ZULU;
        projectDescriptor = new EditProjectDescriptorBuilder().withClient(VALID_CLIENT_ZULU).build();
        expectedProjCommand = new EditProjectInfoCommand(targetName, projectDescriptor);
        assertParseSuccess(parser, userInput, expectedProjCommand);

        //description
        userInput = EditProjectCommand.EDIT_PROJECT_KEYWORD + " " + VALID_PROJECT_NAME_ZULU + " "
            + EditProjectInfoCommand.EDIT_INFO_KEYWORD + DESCRIPTION_DESC;
        projectDescriptor = new EditProjectDescriptorBuilder().withDescription(VALID_DESCRIPTION).build();
        expectedProjCommand = new EditProjectInfoCommand(targetName, projectDescriptor);
        assertParseSuccess(parser, userInput, expectedProjCommand);

        //deadline
        userInput = EditProjectCommand.EDIT_PROJECT_KEYWORD + " " + VALID_PROJECT_NAME_ZULU + " "
            + EditProjectInfoCommand.EDIT_INFO_KEYWORD + DEADLINE_DESC_ALICE;
        projectDescriptor = new EditProjectDescriptorBuilder().withDeadline(VALID_DEADLINE_ALICE).build();
        expectedProjCommand = new EditProjectInfoCommand(targetName, projectDescriptor);
        assertParseSuccess(parser, userInput, expectedProjCommand);

        //flexi deadline
        userInput = EditProjectCommand.EDIT_PROJECT_KEYWORD + " " + VALID_PROJECT_NAME_ZULU + " "
            + EditProjectInfoCommand.EDIT_INFO_KEYWORD + FLEXI_DEADLINE_DESC_ZULU;
        projectDescriptor = new EditProjectDescriptorBuilder().withDeadline(VALID_FLEXIDATE_ZULU).build();
        expectedProjCommand = new EditProjectInfoCommand(targetName, projectDescriptor);
        assertParseSuccess(parser, userInput, expectedProjCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() throws ParseException {
        Index targetIndex = INDEX_FIRST_EMPLOYEE;
        String userInput = EditEmployeeCommand.EDIT_EMPLOYEE_KEYWORD + " " + targetIndex.getOneBased() + PHONE_DESC_AMY
            + GITHUB_DESC_AMY + EMAIL_DESC_AMY + SKILL_DESC_C + PHONE_DESC_AMY + GITHUB_DESC_AMY + EMAIL_DESC_AMY
            + SKILL_DESC_C + PHONE_DESC_BOB + GITHUB_DESC_BOB + EMAIL_DESC_BOB + SKILL_DESC_JAVA;

        EditEmployeeCommand.EditEmployeeDescriptor descriptor = new EditEmployeeDescriptorBuilder()
            .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withGithubAccount(VALID_GITHUB_BOB)
            .withSkills(VALID_SKILL_C, VALID_SKILL_JAVA).build();
        EditEmployeeCommand expectedCommand = new EditEmployeeCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);

        //=========== EditProjectCommand ==============================================================================
        ProjectName targetProject = new ProjectName(VALID_PROJECT_NAME_ALICE);

        userInput = EditProjectCommand.EDIT_PROJECT_KEYWORD + " " + VALID_PROJECT_NAME_ALICE + " "
            + EditProjectInfoCommand.EDIT_INFO_KEYWORD + NAME_DESC_AMY + CLIENT_DESC_ZULU + DEADLINE_DESC_ALICE
            + NAME_DESC_BOB + CLIENT_DESC_ALICE + DEADLINE_DESC_ZULU;
        EditProjectInfoCommand.EditProjectDescriptor projectDescriptor = new EditProjectDescriptorBuilder()
            .withName(VALID_NAME_BOB).withClient(VALID_CLIENT_ALICE).withDeadline(VALID_DEADLINE_ZULU).build();
        EditProjectInfoCommand expectedProjCommand = new EditProjectInfoCommand(targetProject, projectDescriptor);

        assertParseSuccess(parser, userInput, expectedProjCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() throws ParseException {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_EMPLOYEE;
        String userInput = EditEmployeeCommand.EDIT_EMPLOYEE_KEYWORD + " " + targetIndex.getOneBased()
            + INVALID_PHONE_DESC + PHONE_DESC_BOB;
        EditEmployeeCommand.EditEmployeeDescriptor descriptor =
            new EditEmployeeDescriptorBuilder().withPhone(VALID_PHONE_BOB).build();
        EditEmployeeCommand expectedCommand = new EditEmployeeCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = EditEmployeeCommand.EDIT_EMPLOYEE_KEYWORD + " " + targetIndex.getOneBased() + EMAIL_DESC_BOB
            + INVALID_PHONE_DESC + GITHUB_DESC_BOB + PHONE_DESC_BOB;
        descriptor = new EditEmployeeDescriptorBuilder().withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
            .withGithubAccount(VALID_GITHUB_BOB).build();
        expectedCommand = new EditEmployeeCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        //=========== EditProjectCommand ==============================================================================
        ProjectName targetProject = new ProjectName(VALID_PROJECT_NAME_ALICE);
        userInput = EditProjectCommand.EDIT_PROJECT_KEYWORD + " " + VALID_PROJECT_NAME_ALICE + " "
            + EditProjectInfoCommand.EDIT_INFO_KEYWORD + INVALID_PROJECT_NAME_DESC + NAME_DESC_ZULU;
        EditProjectInfoCommand.EditProjectDescriptor projectDescriptor = new EditProjectDescriptorBuilder()
            .withName(VALID_PROJECT_NAME_ZULU).build();
        EditProjectInfoCommand expectedProjCommand = new EditProjectInfoCommand(targetProject, projectDescriptor);

        assertParseSuccess(parser, userInput, expectedProjCommand);

        userInput = EditProjectCommand.EDIT_PROJECT_KEYWORD + " " + VALID_PROJECT_NAME_ALICE + " "
            + EditProjectInfoCommand.EDIT_INFO_KEYWORD + INVALID_PROJECT_NAME_DESC + NAME_DESC_ZULU + CLIENT_DESC_ZULU
            + DESCRIPTION_DESC;
        projectDescriptor = new EditProjectDescriptorBuilder().withName(VALID_PROJECT_NAME_ZULU)
            .withClient(VALID_CLIENT_ZULU).withDescription(VALID_DESCRIPTION).build();
        expectedProjCommand = new EditProjectInfoCommand(targetProject, projectDescriptor);

        assertParseSuccess(parser, userInput, expectedProjCommand);
    }

    @Test
    public void parse_resetSkills_success() {
        Index targetIndex = INDEX_THIRD_EMPLOYEE;
        String userInput = EditEmployeeCommand.EDIT_EMPLOYEE_KEYWORD + " " + targetIndex.getOneBased() + SKILL_EMPTY;

        EditEmployeeCommand.EditEmployeeDescriptor descriptor = new EditEmployeeDescriptorBuilder()
            .withSkills().build();
        EditEmployeeCommand expectedCommand = new EditEmployeeCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
