package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_MEDICINES_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BATCHNUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMPANY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.testutil.TypicalMedicines.ACETAMINOPHEN;
import static seedu.address.testutil.TypicalMedicines.IBUPROFEN;
import static seedu.address.testutil.TypicalMedicines.LISINOPRIL;
import static seedu.address.testutil.TypicalMedicines.PREDNISONE;
import static seedu.address.testutil.TypicalMedicines.getTypicalInventory;

import java.util.Arrays;
import java.util.Collections;
import java.util.function.Predicate;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.medicine.Medicine;
import seedu.address.model.medicine.predicates.BatchContainsKeywordsPredicate;
import seedu.address.model.medicine.predicates.CompanyContainsKeywordsPredicate;
import seedu.address.model.medicine.predicates.NameContainsKeywordsPredicate;
import seedu.address.model.medicine.predicates.TagContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalInventory(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalInventory(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_nameContainsKeywordsPredicate_medicineFound() {
        String expectedMessage = String.format(MESSAGE_MEDICINES_LISTED_OVERVIEW, 1);
        Predicate<Medicine> predicate = preparePredicate(PREFIX_NAME, ACETAMINOPHEN.toString());
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredMedicineList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ACETAMINOPHEN), model.getFilteredMedicineList());
    }

    @Test
    public void execute_companyContainsKeywordsPredicate_medicineFound() {
        String expectedMessage = String.format(MESSAGE_MEDICINES_LISTED_OVERVIEW, 1);
        Predicate<Medicine> predicate = preparePredicate(PREFIX_COMPANY, LISINOPRIL.getCompany().toString());
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredMedicineList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(LISINOPRIL), model.getFilteredMedicineList());
    }

    @Test
    public void execute_tagContainsKeywordsPredicate_medicineFound() {
        String expectedMessage = String.format(MESSAGE_MEDICINES_LISTED_OVERVIEW, 1);
        String tag = IBUPROFEN.getTags().iterator().next().tagName;
        Predicate<Medicine> predicate = preparePredicate(PREFIX_TAG, tag);
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredMedicineList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(IBUPROFEN), model.getFilteredMedicineList());
    }

    @Test
    public void execute_batchContainsKeywordsPredicate_medicineFound() {
        String expectedMessage = String.format(MESSAGE_MEDICINES_LISTED_OVERVIEW, 1);
        String batch = PREDNISONE.getBatches().keySet().iterator().next().toString();
        Predicate<Medicine> predicate = preparePredicate(PREFIX_BATCHNUMBER, batch);
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredMedicineList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(PREDNISONE), model.getFilteredMedicineList());
    }

    @Test
    public void execute_multipleKeywords_multipleMedicinesFound() {
        String expectedMessage = String.format(MESSAGE_MEDICINES_LISTED_OVERVIEW, 3);
        Predicate<Medicine> predicate = preparePredicate(PREFIX_NAME, ACETAMINOPHEN.toString() + " "
                + LISINOPRIL.toString() + " " + PREDNISONE.toString());
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredMedicineList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ACETAMINOPHEN, LISINOPRIL, PREDNISONE), model.getFilteredMedicineList());
    }

    @Test
    public void execute_nonPresentKeywords_noMedicinesFound() {
        String expectedMessage = String.format(MESSAGE_MEDICINES_LISTED_OVERVIEW, 0);
        Predicate<Medicine> predicate = preparePredicate(PREFIX_COMPANY, "one two three");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredMedicineList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(), model.getFilteredMedicineList());
    }

    /**
     * Parses {@code userInput} into a {@code Predicate<Medicine>}.
     */
    private Predicate<Medicine> preparePredicate(Prefix prefix, String userInput) {
        String[] keywords = userInput.split("\\s+");
        if (prefix == PREFIX_NAME) {
            return new NameContainsKeywordsPredicate(Arrays.asList(keywords));
        }

        if (prefix == PREFIX_COMPANY) {
            return new CompanyContainsKeywordsPredicate(Arrays.asList(keywords));
        }

        if (prefix == PREFIX_TAG) {
            return new TagContainsKeywordsPredicate(Arrays.asList(keywords));
        }

        if (prefix == PREFIX_BATCHNUMBER) {
            return new BatchContainsKeywordsPredicate(Arrays.asList(keywords));
        }
        return null;
    }

    @Test
    public void equals() {
        NameContainsKeywordsPredicate firstPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("first"));
        NameContainsKeywordsPredicate secondPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("second"));

        FindCommand findFirstCommand = new FindCommand(firstPredicate);
        FindCommand findSecondCommand = new FindCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different medicine -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }
}
