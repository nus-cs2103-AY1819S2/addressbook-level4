package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_DOCTORS;
import static seedu.address.testutil.TypicalDoctors.getTypicalDocX_doctor;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.doctor.ListDoctorCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.doctor.DoctorContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListDoctorCommand.
 */
public class ListDoctorCommandTest {

    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalDocX_doctor(), new UserPrefs());
        expectedModel = new ModelManager(model.getDocX(), new UserPrefs());
    }

    @Test
    public void execute_zeroKeywords_listAllDoctors() {
        String expectedMessage = String.format(Messages.MESSAGE_DOCTORS_LISTED_OVERVIEW,
                model.getFilteredDoctorList().size());
        ListDoctorCommand command = new ListDoctorCommand();
        expectedModel.updateFilteredDoctorList(PREDICATE_SHOW_ALL_DOCTORS);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_oneKeyword_listRelevantDoctors() {
        DoctorContainsKeywordsPredicate predicate = new DoctorContainsKeywordsPredicate(Arrays.asList("acupunc"));
        ListDoctorCommand command = new ListDoctorCommand(predicate);
        expectedModel.updateFilteredDoctorList(predicate);
        model.updateFilteredDoctorList(predicate);
        String expectedMessage = String.format(Messages.MESSAGE_DOCTORS_LISTED_OVERVIEW,
                model.getFilteredDoctorList().size());
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_multipleKeywords_listRelevantDoctors() {
        DoctorContainsKeywordsPredicate predicate = new DoctorContainsKeywordsPredicate(
                Arrays.asList("acupunc", "ong"));
        ListDoctorCommand command = new ListDoctorCommand(predicate);
        expectedModel.updateFilteredDoctorList(predicate);
        model.updateFilteredDoctorList(predicate);
        String expectedMessage = String.format(Messages.MESSAGE_DOCTORS_LISTED_OVERVIEW,
                model.getFilteredDoctorList().size());
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        DoctorContainsKeywordsPredicate firstPredicate =
                new DoctorContainsKeywordsPredicate(firstPredicateKeywordList);
        DoctorContainsKeywordsPredicate secondPredicate =
                new DoctorContainsKeywordsPredicate(secondPredicateKeywordList);

        ListDoctorCommand listDoctorFirstCommand = new ListDoctorCommand(firstPredicate);
        ListDoctorCommand listDoctorSecondCOmmand = new ListDoctorCommand(secondPredicate);

        // same object -> returns true
        assertTrue(listDoctorFirstCommand.equals(listDoctorFirstCommand));

        // same values -> returns true
        ListDoctorCommand listDoctorFirstCommandCopy =
                new ListDoctorCommand(firstPredicate);
        assertTrue(listDoctorFirstCommand.equals(listDoctorFirstCommandCopy));

        // different types -> returns false
        assertFalse(listDoctorFirstCommand.equals(1));

        // null -> returns false
        assertFalse(listDoctorFirstCommand.equals(null));

        // different doctor -> returns false
        assertFalse(listDoctorFirstCommand.equals(listDoctorSecondCOmmand));
    }
}
