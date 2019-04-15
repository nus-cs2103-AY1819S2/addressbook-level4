package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.CATHY;
import static seedu.address.testutil.TypicalPersons.DIANA;
import static seedu.address.testutil.TypicalPersons.ENID;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalPersons.getTypicalArchiveBook;
import static seedu.address.testutil.TypicalPersons.getTypicalPinBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.PersonContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code ArchiveSearchCommand}.
 */
public class ArchiveSearchCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalArchiveBook(),
            getTypicalPinBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalArchiveBook(),
            getTypicalPinBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        PersonContainsKeywordsPredicate firstPredicate =
                new PersonContainsKeywordsPredicate(Collections.singletonList("first"));
        PersonContainsKeywordsPredicate secondPredicate =
                new PersonContainsKeywordsPredicate(Collections.singletonList("second"));

        ArchiveSearchCommand archiveSearchFirstCommand = new ArchiveSearchCommand(firstPredicate);
        ArchiveSearchCommand archiveSearchSecondCommand = new ArchiveSearchCommand(secondPredicate);

        // same object -> returns true
        assertTrue(archiveSearchFirstCommand.equals(archiveSearchFirstCommand));

        // same values -> returns true
        ArchiveSearchCommand archiveSearchFirstCommandCopy = new ArchiveSearchCommand(firstPredicate);
        assertTrue(archiveSearchFirstCommand.equals(archiveSearchFirstCommandCopy));

        // different types -> returns false
        assertFalse(archiveSearchFirstCommand.equals(1));

        // null -> returns false
        assertFalse(archiveSearchFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(archiveSearchFirstCommand.equals(archiveSearchSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        PersonContainsKeywordsPredicate predicate = preparePredicate(" ");
        ArchiveSearchCommand command = new ArchiveSearchCommand(predicate);
        expectedModel.updateFilteredArchivedPersonList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredArchivedPersonList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        PersonContainsKeywordsPredicate predicate = preparePredicate("cathy diana enid ");
        ArchiveSearchCommand command = new ArchiveSearchCommand(predicate);
        expectedModel.updateFilteredArchivedPersonList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CATHY, DIANA, ENID), model.getFilteredArchivedPersonList());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private PersonContainsKeywordsPredicate preparePredicate(String userInput) {
        return new PersonContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
