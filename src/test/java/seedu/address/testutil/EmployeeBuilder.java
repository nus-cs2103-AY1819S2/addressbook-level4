package seedu.address.testutil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.model.employee.Email;
import seedu.address.model.employee.Employee;
import seedu.address.model.employee.EmployeeName;
import seedu.address.model.employee.GitHubAccount;
import seedu.address.model.employee.Phone;
import seedu.address.model.project.ProjectName;
import seedu.address.model.skill.Skill;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Employee objects.
 */
public class EmployeeBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final String DEFAULT_GITHUB = "aliceballer";

    private EmployeeName employeeName;
    private Phone phone;
    private Email email;
    private GitHubAccount github;
    private Set<Skill> skills;
    private List<ProjectName> projectNames;

    public EmployeeBuilder() {
        employeeName = new EmployeeName(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        github = new GitHubAccount(DEFAULT_GITHUB);
        skills = new HashSet<>();
        projectNames = new ArrayList<>();
    }

    /**
     * Initializes the EmployeeBuilder with the data of {@code employeeToCopy}.
     */
    public EmployeeBuilder(Employee employeeToCopy) {
        employeeName = employeeToCopy.getEmployeeName();
        phone = employeeToCopy.getPhone();
        email = employeeToCopy.getEmail();
        github = employeeToCopy.getGithub();
        skills = new HashSet<>(employeeToCopy.getSkills());
        projectNames = new ArrayList<>(employeeToCopy.getCurrentProjects());
    }

    /**
     * Sets the {@code EmployeeName} of the {@code Employee} that we are building.
     */
    public EmployeeBuilder withName(String name) {
        this.employeeName = new EmployeeName(name);
        return this;
    }

    /**
     * Parses the {@code skills} into a {@code Set<Skill>} and set it to the {@code Employee} that we are building.
     */
    public EmployeeBuilder withSkills(String ... skills) {
        this.skills = SampleDataUtil.getSkillSet(skills);
        return this;
    }

    /**
     * Sets the {@code GitHubAccount} of the {@code Employee} that we are building.
     */
    public EmployeeBuilder withGitHubAccount(String account) {
        this.github = new GitHubAccount(account);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Employee} that we are building.
     */
    public EmployeeBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Employee} that we are building.
     */
    public EmployeeBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the names of the lists of projects working on of this employee.
     */
    public EmployeeBuilder withProjects(String... projectNames) {
        this.projectNames.addAll(SampleDataUtil.getProjectNamesList(projectNames));
        return this;
    }
    public Employee build() {
        return new Employee(employeeName, phone, email, github, skills, projectNames);
    }

}
