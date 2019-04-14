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
 * Contains integration tests (interaction with the Model) for {@code ArchiveFindCommand}.
 */
public class ArchiveFindCommandTest {
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

        ArchiveFindCommand archiveFindFirstCommand = new ArchiveFindCommand(firstPredicate);
        ArchiveFindCommand archiveFindSecondCommand = new ArchiveFindCommand(secondPredicate);

        // same object -> returns true
        assertTrue(archiveFindFirstCommand.equals(archiveFindFirstCommand));

        // same values -> returns true
        ArchiveFindCommand archiveFindFirstCommandCopy = new ArchiveFindCommand(firstPredicate);
        assertTrue(archiveFindFirstCommand.equals(archiveFindFirstCommandCopy));

        // different types -> returns false
        assertFalse(archiveFindFirstCommand.equals(1));

        // null -> returns false
        assertFalse(archiveFindFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(archiveFindFirstCommand.equals(archiveFindSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        PersonContainsKeywordsPredicate predicate = preparePredicate(" ");
        ArchiveFindCommand command = new ArchiveFindCommand(predicate);
        expectedModel.updateFilteredArchivedPersonList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredArchivedPersonList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        PersonContainsKeywordsPredicate predicate = preparePredicate("cathy diana enid ");
        ArchiveFindCommand command = new ArchiveFindCommand(predicate);
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
