package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BATCHNUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMPANY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPIRY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Inventory;
import seedu.address.model.Model;
import seedu.address.model.medicine.Medicine;
import seedu.address.model.medicine.predicates.NameContainsKeywordsPredicate;
import seedu.address.testutil.EditMedicineDescriptorBuilder;
import seedu.address.testutil.UpdateBatchDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMOXICILLIN = "Amoxicillin";
    public static final String VALID_NAME_GABAPENTIN = "Gabapentin";
    public static final String VALID_QUANTITY_AMOXICILLIN = "111";
    public static final String VALID_QUANTITY_GABAPENTIN = "0";
    public static final String VALID_QUANTITY_LISNOPRIL = "100";
    public static final String VALID_EXPIRY_AMOXICILLIN = "27/11/2019";
    public static final String VALID_EXPIRY_GABAPENTIN = "-";
    public static final String VALID_EXPIRY_LISPINOL = "21/1/2020";
    public static final String VALID_COMPANY_AMOXICILLIN = "Merck & Co. (MSD)";
    public static final String VALID_COMPANY_GABAPENTIN = "Sanofi";
    public static final String VALID_COMPANY_LISNOPRIL = "Takeda Pharmaceutical Co.";
    public static final String VALID_TAG_PAINKILLER = "painkiller";
    public static final String VALID_TAG_FEVER = "fever";
    public static final String VALID_BATCHNUMBER_AMOXICILLIN = "s9c4xs9532";
    public static final String VALID_BATCHNUMBER_GABAPENTIN = "O26M997570";
    public static final String VALID_BATCHNUMBER_LISNOPRIL = "ABC1234";

    public static final String NAME_DESC_AMOXICILLIN = " " + PREFIX_NAME + VALID_NAME_AMOXICILLIN;
    public static final String NAME_DESC_GABAPENTIN = " " + PREFIX_NAME + VALID_NAME_GABAPENTIN;
    public static final String QUANTITY_DESC_AMOXICILLIN = " " + PREFIX_QUANTITY + VALID_QUANTITY_AMOXICILLIN;
    public static final String QUANTITY_DESC_GABAPENTIN = " " + PREFIX_QUANTITY + VALID_QUANTITY_GABAPENTIN;
    public static final String QUANTITY_DESC_LISNOPRIL = " " + PREFIX_QUANTITY + VALID_QUANTITY_LISNOPRIL;
    public static final String EXPIRY_DESC_AMOXICILLIN = " " + PREFIX_EXPIRY + VALID_EXPIRY_AMOXICILLIN;
    public static final String EXPIRY_DESC_GABAPENTIN = " " + PREFIX_EXPIRY + VALID_EXPIRY_GABAPENTIN;
    public static final String EXPIRY_DESC_LISNOPRIL = " " + PREFIX_EXPIRY + VALID_EXPIRY_LISPINOL;
    public static final String COMPANY_DESC_AMOXICILLIN = " " + PREFIX_COMPANY + VALID_COMPANY_AMOXICILLIN;
    public static final String COMPANY_DESC_GABAPENTIN = " " + PREFIX_COMPANY + VALID_COMPANY_GABAPENTIN;
    public static final String COMPANY_DESC_LISNOPRIL = " " + PREFIX_COMPANY + VALID_COMPANY_LISNOPRIL;
    public static final String TAG_DESC_FEVER = " " + PREFIX_TAG + VALID_TAG_FEVER;
    public static final String TAG_DESC_PAINKILER = " " + PREFIX_TAG + VALID_TAG_PAINKILLER;
    public static final String BATCHNUMBER_DESC_AMOXICILLIN = " " + PREFIX_BATCHNUMBER + VALID_BATCHNUMBER_AMOXICILLIN;
    public static final String BATCHNUMBER_DESC_GABAPENTIN = " " + PREFIX_BATCHNUMBER + VALID_BATCHNUMBER_GABAPENTIN;
    public static final String BATCHNUMBER_DESC_LISNOPRIL = " " + PREFIX_BATCHNUMBER + VALID_BATCHNUMBER_LISNOPRIL;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "Paraceta&mol"; // '&' not allowed in names
    public static final String INVALID_QUANTITY_DESC = " " + PREFIX_QUANTITY + "911a"; // 'a' not allowed in quantities
    public static final String INVALID_EXPIRY_DESC = " " + PREFIX_EXPIRY + "1211/2020"; // missing '/' symbol
    public static final String INVALID_COMPANY_DESC = " " + PREFIX_COMPANY; // empty string not allowed for company name
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "fever*"; // '*' not allowed in tags
    public static final String INVALID_BATCHNUMBER_DESC = " " + PREFIX_BATCHNUMBER + "!4815%&"; // '!' not allowed

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = " NonEmptyPreamble";

    public static final EditCommand.EditMedicineDescriptor DESC_AMOXICILLIN;
    public static final EditCommand.EditMedicineDescriptor DESC_GABAPENTIN;

    public static final UpdateCommand.UpdateBatchDescriptor DESC_AMOXICILLIN_BATCH;
    public static final UpdateCommand.UpdateBatchDescriptor DESC_GABAPENTIN_BATCH;

    static {
        DESC_AMOXICILLIN = new EditMedicineDescriptorBuilder().withName(VALID_NAME_AMOXICILLIN)
                .withCompany(VALID_COMPANY_AMOXICILLIN).withTags(VALID_TAG_FEVER).build();
        DESC_GABAPENTIN = new EditMedicineDescriptorBuilder().withName(VALID_NAME_GABAPENTIN)
                .withCompany(VALID_COMPANY_GABAPENTIN).withTags(VALID_TAG_PAINKILLER, VALID_TAG_FEVER).build();

        DESC_AMOXICILLIN_BATCH = new UpdateBatchDescriptorBuilder().withBatchNumber(VALID_BATCHNUMBER_AMOXICILLIN)
                .withQuantity(VALID_QUANTITY_AMOXICILLIN).withExpiry(VALID_EXPIRY_AMOXICILLIN).build();
        DESC_GABAPENTIN_BATCH = new UpdateBatchDescriptorBuilder().withBatchNumber(VALID_BATCHNUMBER_GABAPENTIN)
                .withQuantity(VALID_QUANTITY_GABAPENTIN).withExpiry(VALID_EXPIRY_GABAPENTIN).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel} <br>
     * - the {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandHistory actualCommandHistory,
            CommandResult expectedCommandResult, Model expectedModel) {
        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);
        try {
            CommandResult result = command.execute(actualModel, actualCommandHistory);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
            assertEquals(expectedCommandHistory, actualCommandHistory);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandHistory, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandHistory actualCommandHistory,
            String expectedMessage, Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, actualCommandHistory, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the inventory, filtered medicine list and selected medicine in {@code actualModel} remain unchanged <br>
     * - {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandFailure(Command command, Model actualModel, CommandHistory actualCommandHistory,
            String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        Inventory expectedInventory = new Inventory(actualModel.getInventory());
        List<Medicine> expectedFilteredList = new ArrayList<>(actualModel.getFilteredMedicineList());
        Medicine expectedSelectedMedicine = actualModel.getSelectedMedicine();

        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);

        try {
            command.execute(actualModel, actualCommandHistory);
            throw new AssertionError("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedInventory, actualModel.getInventory());
            assertEquals(expectedFilteredList, actualModel.getFilteredMedicineList());
            assertEquals(expectedSelectedMedicine, actualModel.getSelectedMedicine());
            assertEquals(expectedCommandHistory, actualCommandHistory);
        }
    }

    /**
     * Updates {@code model}'s filtered list to show only the medicine at the given {@code targetIndex} in the
     * {@code model}'s inventory.
     */
    public static void showMedicineAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredMedicineList().size());

        Medicine medicine = model.getFilteredMedicineList().get(targetIndex.getZeroBased());
        final String[] splitName = medicine.getName().fullName.split("\\s+");
        model.updateFilteredMedicineList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredMedicineList().size());
    }

    /**
     * Deletes the first medicine in {@code model}'s filtered list from {@code model}'s inventory.
     */
    public static void deleteFirstMedicine(Model model) {
        Medicine firstMedicine = model.getFilteredMedicineList().get(0);
        model.deleteMedicine(firstMedicine);
        model.commitInventory();
    }

}
