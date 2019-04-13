package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_SHOW_CURRENT_THRESHOLDS;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalMedicines.ACETAMINOPHEN;
import static seedu.address.testutil.TypicalMedicines.IBUPROFEN;
import static seedu.address.testutil.TypicalMedicines.LEVOTHYROXINE;
import static seedu.address.testutil.TypicalMedicines.LISINOPRIL;
import static seedu.address.testutil.TypicalMedicines.getTypicalInventory;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

import seedu.address.commons.util.warning.WarningPanelPredicateType;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.medicine.Quantity;
import seedu.address.model.medicine.predicates.NameContainsKeywordsPredicate;
import seedu.address.model.threshold.Threshold;
import seedu.address.testutil.Assert;

/**
 * Contains integration tests (interaction with the Model) for {@code WarningCommand}.
 */
@SuppressWarnings("ALL")
public class WarningCommandTest {
    private Model model = new ModelManager(getTypicalInventory(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalInventory(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullWarningPanelPredicateType_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, ()
            -> new WarningCommand(null, new Threshold("0", WarningPanelPredicateType.EXPIRY)));
        Assert.assertThrows(NullPointerException.class, ()
            -> new WarningCommand(null, new Threshold("0", WarningPanelPredicateType.LOW_STOCK)));
    }

    @Test
    public void constructor_nullThreshold_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new WarningCommand(
                WarningPanelPredicateType.EXPIRY, null));
    }

    @Test
    public void constructor_nullShowString_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new WarningCommand(null));
    }

    @Test
    public void equals() {
        Threshold firstThreshold = Model.DEFAULT_EXPIRY_THRESHOLD;
        Threshold secondThreshold = Model.DEFAULT_LOW_STOCK_THRESHOLD;

        WarningPanelPredicateType firstType = WarningPanelPredicateType.EXPIRY;
        WarningPanelPredicateType secondType = WarningPanelPredicateType.LOW_STOCK;

        WarningCommand warningFirstCommand = new WarningCommand(firstType, firstThreshold);
        WarningCommand warningSecondCommand = new WarningCommand(secondType, secondThreshold);
        WarningCommand warningThirdCommand = new WarningCommand(true);

        // same object -> returns true
        assertTrue(warningFirstCommand.equals(warningFirstCommand));

        // same values -> returns true
        WarningCommand warningFirstCommandCopy = new WarningCommand(firstType, firstThreshold);
        assertTrue(warningFirstCommand.equals(warningFirstCommandCopy));

        // different types -> returns false
        assertFalse(warningFirstCommand.equals(1));

        // null -> returns false
        assertFalse(warningFirstCommand.equals(null));

        // different command -> returns false
        assertFalse(warningFirstCommand.equals(warningSecondCommand));
        assertFalse(warningFirstCommand.equals(warningThirdCommand));
    }

    @Test
    public void execute_show_defaultThresholds() {
        String expectedMessage = String.format(MESSAGE_SHOW_CURRENT_THRESHOLDS,
                Model.DEFAULT_EXPIRY_THRESHOLD.getNumericValue(),
                Model.DEFAULT_EXPIRY_THRESHOLD.getNumericValue() == 1 ? "" : "s",
                Model.DEFAULT_LOW_STOCK_THRESHOLD.getNumericValue());
        WarningCommand command = new WarningCommand(true);

        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Model.DEFAULT_EXPIRY_THRESHOLD,
                model.getWarningPanelThreshold(WarningPanelPredicateType.EXPIRY));
        assertEquals(Model.DEFAULT_LOW_STOCK_THRESHOLD,
                model.getWarningPanelThreshold(WarningPanelPredicateType.LOW_STOCK));
    }

    @Test
    public void execute_changeExpiryThreshold_zeroExpiringMedicine() {
        String expectedMessage = String.format(MESSAGE_SHOW_CURRENT_THRESHOLDS,
                0, "s", Model.DEFAULT_LOW_STOCK_THRESHOLD.getNumericValue());

        Threshold threshold = new Threshold("0", WarningPanelPredicateType.EXPIRY);
        WarningCommand command = new WarningCommand(WarningPanelPredicateType.EXPIRY, threshold);
        expectedModel.changeWarningPanelListThreshold(WarningPanelPredicateType.EXPIRY, threshold);

        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getExpiringMedicinesList());
    }

    @Test
    public void execute_changeExpiryThreshold_multipleExpiringMedicines() {
        String expectedMessage = String.format(MESSAGE_SHOW_CURRENT_THRESHOLDS,
                180, "s", Model.DEFAULT_LOW_STOCK_THRESHOLD.getNumericValue());

        Threshold threshold = new Threshold("180", WarningPanelPredicateType.EXPIRY);
        WarningCommand command = new WarningCommand(WarningPanelPredicateType.EXPIRY, threshold);
        expectedModel.changeWarningPanelListThreshold(WarningPanelPredicateType.EXPIRY, threshold);
        expectedModel.updateFilteredMedicineList(medicine -> medicine.getNextExpiry() != null);

        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(IBUPROFEN, LEVOTHYROXINE, LISINOPRIL), model.getExpiringMedicinesList());
    }

    @Test
    public void execute_changeLowStockThreshold_zeroLowStockMedicines() {
        String expectedMessage = String.format(MESSAGE_SHOW_CURRENT_THRESHOLDS,
                Model.DEFAULT_EXPIRY_THRESHOLD.getNumericValue(),
                Model.DEFAULT_EXPIRY_THRESHOLD.getNumericValue() == 1 ? "" : "s",
                Quantity.MAX_QUANTITY + 1);

        Threshold threshold = new Threshold(Integer.toString(Threshold.MAX_QUANTITY_THRESHOLD),
                WarningPanelPredicateType.LOW_STOCK);
        WarningCommand command = new WarningCommand(WarningPanelPredicateType.LOW_STOCK, threshold);
        expectedModel.changeWarningPanelListThreshold(WarningPanelPredicateType.LOW_STOCK, threshold);

        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(model.getFilteredMedicineList(), model.getLowStockMedicinesList());
    }

    @Test
    public void execute_changeLowStockThreshold_multipleLowStockMedicines() {
        String expectedMessage = String.format(MESSAGE_SHOW_CURRENT_THRESHOLDS,
                Model.DEFAULT_EXPIRY_THRESHOLD.getNumericValue(),
                Model.DEFAULT_EXPIRY_THRESHOLD.getNumericValue() == 1 ? "" : "s", 0);

        Threshold threshold = new Threshold("0", WarningPanelPredicateType.LOW_STOCK);
        WarningCommand command = new WarningCommand(WarningPanelPredicateType.LOW_STOCK, threshold);
        expectedModel.changeWarningPanelListThreshold(WarningPanelPredicateType.LOW_STOCK, threshold);

        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ACETAMINOPHEN), model.getLowStockMedicinesList());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private NameContainsKeywordsPredicate preparePredicate(String userInput) {
        return new NameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
