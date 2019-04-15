package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.employee.Email;
import seedu.address.model.employee.Employee;
import seedu.address.model.employee.EmployeeName;
import seedu.address.model.employee.GitHubAccount;
import seedu.address.model.employee.Phone;
import seedu.address.model.project.ProjectName;
import seedu.address.model.skill.Skill;

/**
 * Jackson-friendly version of {@link Employee}.
 */
class JsonAdaptedEmployee {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Employee's %s field is missing!";

    private final String name;
    private final String phone;
    private final String email;
    private final String github;
    private final List<JsonAdaptedSkill> skills = new ArrayList<>();
    private final List<JsonAdaptedProjectName> projectNames = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedEmployee} with the given employee details.
     */
    @JsonCreator
    public JsonAdaptedEmployee(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
                               @JsonProperty("email") String email, @JsonProperty("github") String github,
                               @JsonProperty("skills") List<JsonAdaptedSkill> skills,
                               @JsonProperty("projectNames") List<JsonAdaptedProjectName> projectNames) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.github = github;
        if (skills != null) {
            this.skills.addAll(skills);
        }
        if (projectNames != null) {
            this.projectNames.addAll(projectNames);
        }
    }

    /**
     * Converts a given {@code Employee} into this class for Jackson use.
     */
    public JsonAdaptedEmployee(Employee source) {
        name = source.getEmployeeName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        github = source.getGithub().value;
        skills.addAll(source.getSkills().stream()
                .map(JsonAdaptedSkill::new)
                .collect(Collectors.toList()));
        projectNames.addAll(source.getCurrentProjects().stream()
                .map(JsonAdaptedProjectName::new).collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted employee object into the model's {@code Employee} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted employee.
     */
    public Employee toModelType() throws IllegalValueException {
        final List<Skill> employeeSkills = new ArrayList<>();
        for (JsonAdaptedSkill skill : skills) {
            employeeSkills.add(skill.toModelType());
        }
        final List<ProjectName> modelProjectNames = new ArrayList<>();
        for (JsonAdaptedProjectName pn: projectNames) {
            modelProjectNames.add(pn.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                EmployeeName.class.getSimpleName()));
        }
        if (!EmployeeName.isValidName(name)) {
            throw new IllegalValueException(EmployeeName.MESSAGE_CONSTRAINTS);
        }
        final EmployeeName modelEmployeeName = new EmployeeName(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (github == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                GitHubAccount.class.getSimpleName()));
        }
        if (!GitHubAccount.isValidAccount(github)) {
            throw new IllegalValueException(GitHubAccount.MESSAGE_CONSTRAINTS);
        }
        final GitHubAccount modelGitHubAccount = new GitHubAccount(github);

        final Set<Skill> modelSkills = new HashSet<>(employeeSkills);
        return new Employee(modelEmployeeName, modelPhone, modelEmail, modelGitHubAccount, modelSkills,
            modelProjectNames);
    }

}
