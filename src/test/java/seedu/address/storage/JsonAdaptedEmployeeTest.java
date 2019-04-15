package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.JsonAdaptedEmployee.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalEmployees.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.employee.Email;
import seedu.address.model.employee.EmployeeName;
import seedu.address.model.employee.GitHubAccount;
import seedu.address.model.employee.Phone;
import seedu.address.testutil.Assert;

public class JsonAdaptedEmployeeTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_GITHUB = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_SKILL = "";

    private static final String VALID_NAME = BENSON.getEmployeeName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_GITHUB = BENSON.getGithub().toString();
    private static final List<JsonAdaptedSkill> VALID_SKILLS = BENSON.getSkills().stream()
            .map(JsonAdaptedSkill::new)
            .collect(Collectors.toList());
    private static final List<JsonAdaptedProjectName> VALID_PROJECT_NAMES = new ArrayList<>();

    @Test
    public void toModelType_validEmployeeDetails_returnsEmployee() throws Exception {
        JsonAdaptedEmployee employee = new JsonAdaptedEmployee(BENSON);
        assertEquals(BENSON, employee.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedEmployee employee =
                new JsonAdaptedEmployee(INVALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_GITHUB, VALID_SKILLS,
                        VALID_PROJECT_NAMES);
        String expectedMessage = EmployeeName.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, employee::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedEmployee employee =
                new JsonAdaptedEmployee(null, VALID_PHONE, VALID_EMAIL, VALID_GITHUB, VALID_SKILLS,
                        VALID_PROJECT_NAMES);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, EmployeeName.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, employee::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedEmployee employee =
                new JsonAdaptedEmployee(VALID_NAME, INVALID_PHONE, VALID_EMAIL, VALID_GITHUB, VALID_SKILLS,
                        VALID_PROJECT_NAMES);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, employee::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedEmployee employee =
                new JsonAdaptedEmployee(VALID_NAME, null, VALID_EMAIL, VALID_GITHUB, VALID_SKILLS,
                        VALID_PROJECT_NAMES);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, employee::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedEmployee employee =
                new JsonAdaptedEmployee(VALID_NAME, VALID_PHONE, INVALID_EMAIL, VALID_GITHUB, VALID_SKILLS,
                        VALID_PROJECT_NAMES);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, employee::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedEmployee employee =
                new JsonAdaptedEmployee(VALID_NAME, VALID_PHONE, null, VALID_GITHUB, VALID_SKILLS,
                        VALID_PROJECT_NAMES);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, employee::toModelType);
    }

    @Test
    public void toModelType_invalidGithub_throwsIllegalValueException() {
        JsonAdaptedEmployee employee =
                new JsonAdaptedEmployee(VALID_NAME, VALID_PHONE, VALID_EMAIL, INVALID_GITHUB, VALID_SKILLS,
                        VALID_PROJECT_NAMES);
        String expectedMessage = GitHubAccount.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, employee::toModelType);
    }

    @Test
    public void toModelType_nullGithub_throwsIllegalValueException() {
        JsonAdaptedEmployee employee =
                new JsonAdaptedEmployee(VALID_NAME, VALID_PHONE, VALID_EMAIL, null, VALID_SKILLS,
                        VALID_PROJECT_NAMES);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, GitHubAccount.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, employee::toModelType);
    }

    @Test
    public void toModelType_invalidSkills_throwsIllegalValueException() {
        List<JsonAdaptedSkill> invalidSkills = new ArrayList<>(VALID_SKILLS);
        invalidSkills.add(new JsonAdaptedSkill(INVALID_SKILL));
        JsonAdaptedEmployee employee =
                new JsonAdaptedEmployee(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_GITHUB, invalidSkills,
                        VALID_PROJECT_NAMES);
        Assert.assertThrows(IllegalValueException.class, employee::toModelType);
    }

}
