package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CODE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CODE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXPECTED_MIN_GRADE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SEMESTER_AMY;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.KEYWORD_MATCHING_MEIER;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.FindCommand.FindModuleDescriptor;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.moduleinfo.ModuleInfoList;
import seedu.address.model.person.FindModulePredicate;
import seedu.address.testutil.FindModuleDescriptorBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new ModuleInfoList());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new ModuleInfoList());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        FindModuleDescriptor fd1 = new FindModuleDescriptorBuilder().withCode(VALID_CODE_AMY).build();
        FindModuleDescriptor fd2 = new FindModuleDescriptorBuilder().withCode(VALID_CODE_BOB).build();
        FindModuleDescriptor fd3 = new FindModuleDescriptorBuilder().withCode(VALID_CODE_AMY)
                .withSemester(VALID_SEMESTER_AMY).withGrade(VALID_EXPECTED_MIN_GRADE_AMY).build();
        FindModuleDescriptor fd4 = new FindModuleDescriptorBuilder().withCode("CS").build();
        FindModuleDescriptor fd5 = new FindModuleDescriptorBuilder().withCode("cs").build();

        FindCommand fc1 = new FindCommand(new FindModulePredicate(fd1));
        FindCommand fc2 = new FindCommand(new FindModulePredicate(fd2));
        FindCommand fc3 = new FindCommand(new FindModulePredicate(fd3));
        FindCommand fc4 = new FindCommand(new FindModulePredicate(fd4));
        FindCommand fc5 = new FindCommand(new FindModulePredicate(fd5));

        // same object -> returns true
        assertTrue(fc1.equals(fc1));

        // same values -> returns true
        FindCommand fc1Copy = new FindCommand(new FindModulePredicate(fd1));
        assertTrue(fc1.equals(fc1Copy));

        // same values (ignoring case) -> returns true
        assertTrue(fc4.equals(fc5));

        // different types -> returns false
        assertFalse(fc1.equals(1));

        // null -> returns false
        assertFalse(fc1.equals(null));

        // different code -> returns false
        assertFalse(fc1.equals(fc2));

        // different number of parameters -> returns false
        assertFalse(fc1.equals(fc3));
    }

    @Test
    public void execute_nonExistingCode_noModuleFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);

        FindModuleDescriptor fd = new FindModuleDescriptorBuilder().withCode("2103cs").build();
        FindModulePredicate fp = new FindModulePredicate(fd);

        FindCommand command = new FindCommand(fp);
        expectedModel.updateFilteredPersonList(fp);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_codeOnly_multipleModulesFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);

        FindModuleDescriptor fd = new FindModuleDescriptorBuilder().withCode(KEYWORD_MATCHING_MEIER).build();
        FindModulePredicate fp = new FindModulePredicate(fd);

        FindCommand command = new FindCommand(fp);
        expectedModel.updateFilteredPersonList(fp);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BENSON, DANIEL), model.getFilteredPersonList());
    }

    @Test
    public void execute_semesterOnly_multipleModulesFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);

        FindModuleDescriptor fd = new FindModuleDescriptorBuilder().withSemester("Y4S2").build();
        FindModulePredicate fp = new FindModulePredicate(fd);

        FindCommand command = new FindCommand(fp);
        expectedModel.updateFilteredPersonList(fp);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(DANIEL, FIONA), model.getFilteredPersonList());
    }

    @Test
    public void execute_gradeOnly_multipleModulesFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);

        FindModuleDescriptor fd = new FindModuleDescriptorBuilder().withGrade("F").build();
        FindModulePredicate fp = new FindModulePredicate(fd);

        FindCommand command = new FindCommand(fp);
        expectedModel.updateFilteredPersonList(fp);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, DANIEL), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleParameters_noModuleFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);

        FindModuleDescriptor fd = new FindModuleDescriptorBuilder().withCode(KEYWORD_MATCHING_MEIER)
                .withSemester("Y1S1").withGrade("A").withFinishedStatus("y").build();
        FindModulePredicate fp = new FindModulePredicate(fd);

        FindCommand command = new FindCommand(fp);
        expectedModel.updateFilteredPersonList(fp);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleParameters_multipleModulesFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);

        FindModuleDescriptor fd = new FindModuleDescriptorBuilder().withSemester("Y4S2")
                .withGrade("D").withFinishedStatus("n").build();
        FindModulePredicate fp = new FindModulePredicate(fd);

        FindCommand command = new FindCommand(fp);
        expectedModel.updateFilteredPersonList(fp);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(DANIEL, FIONA), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleParameters_oneModuleFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);

        FindModuleDescriptor fd = new FindModuleDescriptorBuilder().withCode(KEYWORD_MATCHING_MEIER)
                .withSemester("Y3S2").withFinishedStatus("n").build();
        FindModulePredicate fp = new FindModulePredicate(fd);

        FindCommand command = new FindCommand(fp);
        expectedModel.updateFilteredPersonList(fp);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BENSON), model.getFilteredPersonList());
    }
}
