package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EMPLOYEE;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.employee.Email;
import seedu.address.model.employee.EmployeeName;
import seedu.address.model.employee.GitHubAccount;
import seedu.address.model.employee.Phone;
import seedu.address.model.project.ProjectTaskDescription;
import seedu.address.model.project.Status;
import seedu.address.model.skill.Skill;
import seedu.address.testutil.Assert;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_GITHUB = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_SKILL = "";
    private static final String INVALID_TASKDESCRIPTION = " ";
    private static final String INVALID_STATUS = " ";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "123456";
    private static final String VALID_GITHUB = "terrylewis";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_SKILL_1 = "friend";
    private static final String VALID_SKILL_2 = "neighbour";
    private static final String VALID_TASKDESCRIPTION = "valid task";
    private static final String VALID_STATUS = "ongoing";

    private static final String WHITESPACE = " \t\r\n";

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void parseIndex_invalidInput_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        ParserUtil.parseIndex("10 a");
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_INVALID_INDEX);
        ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST_EMPLOYEE, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_EMPLOYEE, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseName_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseName((String) null));
    }

    @Test
    public void parseName_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseName(INVALID_NAME));
    }

    @Test
    public void parseName_validValueWithoutWhitespace_returnsName() throws Exception {
        EmployeeName expectedEmployeeName = new EmployeeName(VALID_NAME);
        assertEquals(expectedEmployeeName, ParserUtil.parseName(VALID_NAME));
    }

    @Test
    public void parseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        EmployeeName expectedEmployeeName = new EmployeeName(VALID_NAME);
        assertEquals(expectedEmployeeName, ParserUtil.parseName(nameWithWhitespace));
    }

    @Test
    public void parsePhone_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parsePhone((String) null));
    }

    @Test
    public void parsePhone_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parsePhone(INVALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithoutWhitespace_returnsPhone() throws Exception {
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(VALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithWhitespace_returnsTrimmedPhone() throws Exception {
        String phoneWithWhitespace = WHITESPACE + VALID_PHONE + WHITESPACE;
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(phoneWithWhitespace));
    }

    @Test
    public void parseGithub_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseAccount((String) null));
    }

    @Test
    public void parseGithub_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseAccount(INVALID_GITHUB));
    }

    @Test
    public void parseGithub_validValueWithoutWhitespace_returnsGithub() throws Exception {
        GitHubAccount expectedGitHubAccount = new GitHubAccount(VALID_GITHUB);
        assertEquals(expectedGitHubAccount, ParserUtil.parseAccount(VALID_GITHUB));
    }

    @Test
    public void parseGithub_validValueWithWhitespace_returnsTrimmedGithub() throws Exception {
        String githubWithWhitespace = WHITESPACE + VALID_GITHUB + WHITESPACE;
        GitHubAccount expectedGitHubAccount = new GitHubAccount(VALID_GITHUB);
        assertEquals(expectedGitHubAccount, ParserUtil.parseAccount(githubWithWhitespace));
    }

    @Test
    public void parseEmail_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseEmail((String) null));
    }

    @Test
    public void parseEmail_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseEmail(INVALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithoutWhitespace_returnsEmail() throws Exception {
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(VALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithWhitespace_returnsTrimmedEmail() throws Exception {
        String emailWithWhitespace = WHITESPACE + VALID_EMAIL + WHITESPACE;
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(emailWithWhitespace));
    }

    @Test
    public void parseSkill_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseSkill(null);
    }

    @Test
    public void parseSkill_invalidValue_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        ParserUtil.parseSkill(INVALID_SKILL);
    }

    @Test
    public void parseSkill_validValueWithoutWhitespace_returnsSkill() throws Exception {
        Skill expectedSkill = new Skill(VALID_SKILL_1);
        assertEquals(expectedSkill, ParserUtil.parseSkill(VALID_SKILL_1));
    }

    @Test
    public void parseSkill_validValueWithWhitespace_returnsTrimmedSkill() throws Exception {
        String skillWithWhitespace = WHITESPACE + VALID_SKILL_1 + WHITESPACE;
        Skill expectedSkill = new Skill(VALID_SKILL_1);
        assertEquals(expectedSkill, ParserUtil.parseSkill(skillWithWhitespace));
    }

    @Test
    public void parseSkills_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseSkills(null);
    }

    @Test
    public void parseSkills_collectionWithInvalidSkills_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        ParserUtil.parseSkills(Arrays.asList(VALID_SKILL_1, INVALID_SKILL));
    }

    @Test
    public void parseSkills_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseSkills(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseSkills_collectionWithValidSkills_returnsSkillSet() throws Exception {
        Set<Skill> actualSkillSet = ParserUtil.parseSkills(Arrays.asList(VALID_SKILL_1, VALID_SKILL_2));
        Set<Skill> expectedSkillSet =
                new HashSet<Skill>(Arrays.asList(new Skill(VALID_SKILL_1), new Skill(VALID_SKILL_2)));

        assertEquals(expectedSkillSet, actualSkillSet);
    }

    @Test
    public void parseProjectTaskDescription_validDescription_returnsTrimmedProjectTaskDescription() throws Exception {
        String projectTaskNameWithWhitespace = WHITESPACE + VALID_TASKDESCRIPTION + WHITESPACE;
        ProjectTaskDescription expectedProjectTaskDescription = new ProjectTaskDescription(VALID_TASKDESCRIPTION);
        assertEquals(expectedProjectTaskDescription,
                ParserUtil.parseProjectTaskDescription(projectTaskNameWithWhitespace));
    }

    @Test
    public void parseProjectTaskDescription_nullDescription_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseProjectTaskDescription(null);
    }

    @Test
    public void parseProjectTaskDescription_invalidTaskDescription_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        ParserUtil.parseProjectTaskDescription(INVALID_TASKDESCRIPTION);
    }

    @Test
    public void parseStatus_validStatus_returnsTrimmedStatus() throws Exception {
        String statusWithWhitespace = WHITESPACE + VALID_STATUS + WHITESPACE;
        Status expectedStatus = new Status(VALID_STATUS);
        assertEquals(expectedStatus, ParserUtil.parseStatus(statusWithWhitespace));
    }

    @Test
    public void parseStatus_nullStatus_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseStatus(null);
    }

    @Test
    public void parseStatus_invalidStatus_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        ParserUtil.parseStatus(INVALID_STATUS);
    }
}
