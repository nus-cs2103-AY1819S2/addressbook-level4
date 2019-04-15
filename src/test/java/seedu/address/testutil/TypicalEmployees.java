package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GITHUB_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GITHUB_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SKILL_C;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SKILL_JAVA;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.PocketProject;
import seedu.address.model.employee.Employee;

/**
 * A utility class containing a list of {@code Employee} objects to be used in tests.
 */
public class TypicalEmployees {

    public static final Employee ALICE = new EmployeeBuilder().withName("Alice Pauline")
            .withGitHubAccount("alicaballer").withEmail("alice@example.com")
            .withPhone("94351253")
            .withSkills("Python", "Java").withProjects("Project George", "Project Fiona").build();
    public static final Employee BENSON = new EmployeeBuilder().withName("Benson Meier")
            .withGitHubAccount("bensonballer")
            .withEmail("johnd@example.com").withPhone("98765432")
            .withSkills("CSS", "HTML", "Java")
            .withProjects("Project Alice hey", "Project George").build();
    public static final Employee CARL = new EmployeeBuilder().withName("Carl Kurz").withPhone("95352563")
            .withEmail("heinz@example.com")
            .withProjects("Project Alice hey", "Project Benson")
            .withGitHubAccount("carlballer").build();
    public static final Employee DANIEL = new EmployeeBuilder().withName("Daniel Meier").withPhone("87652533")
            .withEmail("cornelia@example.com")
            .withProjects("Project Benson", "Project Carl hey")
            .withGitHubAccount("danielballer").withSkills("Assembly").build();
    public static final Employee ELLE = new EmployeeBuilder().withName("Elle Meyer").withPhone("9482224")
            .withEmail("werner@example.com")
            .withProjects("Project Carl hey", "Project Daniel")
            .withGitHubAccount("elleballer").withSkills("Python").build();
    public static final Employee FIONA = new EmployeeBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withEmail("lydia@example.com").withGitHubAccount("fionaballer")
            .withProjects("Project Daniel", "Project Elle").build();
    public static final Employee GEORGE = new EmployeeBuilder().withName("George Best").withPhone("9482442")
            .withEmail("anna@example.com")
            .withProjects("Project Elle", "Project Fiona")
            .withGitHubAccount("georgeballer").build();

    // Manually added
    public static final Employee HOON = new EmployeeBuilder().withName("Hoon Meier").withPhone("8482424")
            .withEmail("stefan@example.com").withGitHubAccount("hoonballer").build();
    public static final Employee IDA = new EmployeeBuilder().withName("Ida Mueller").withPhone("8482131")
            .withEmail("hans@example.com").withGitHubAccount("idaballer").build();

    // Manually added - Employee's details found in {@code CommandTestUtil}
    public static final Employee AMY = new EmployeeBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withGitHubAccount(VALID_GITHUB_AMY).withSkills(VALID_SKILL_C).build();
    public static final Employee BOB = new EmployeeBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withGitHubAccount(VALID_GITHUB_BOB)
            .withSkills(VALID_SKILL_JAVA, VALID_SKILL_C).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalEmployees() {} // prevents instantiation

    /**
     * Returns an {@code PocketProject} with all the typical employees.
     */
    public static PocketProject getTypicalPocketProjectWithEmployees() {
        PocketProject ab = new PocketProject();
        for (Employee employee : getTypicalEmployees()) {
            ab.addEmployee(employee);
        }
        return ab;
    }

    /**
     * Returns an {@code PocketProject} with all the typical employees added to the given {@code PocketProject}.
     */
    public static PocketProject addTypicalEmployees(PocketProject ab) {
        for (Employee employee : getTypicalEmployees()) {
            ab.addEmployee(employee);
        }
        return ab;
    }

    public static List<Employee> getTypicalEmployees() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
