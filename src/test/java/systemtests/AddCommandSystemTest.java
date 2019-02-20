package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.COMPANY_DESC_AMOXICILLIN;
import static seedu.address.logic.commands.CommandTestUtil.COMPANY_DESC_GABAPENTIN;
import static seedu.address.logic.commands.CommandTestUtil.EXPIRY_DESC_AMOXICILLIN;
import static seedu.address.logic.commands.CommandTestUtil.EXPIRY_DESC_GABAPENTIN;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_COMPANY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EXPIRY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_QUANTITY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMOXICILLIN;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_GABAPENTIN;
import static seedu.address.logic.commands.CommandTestUtil.QUANTITY_DESC_AMOXICILLIN;
import static seedu.address.logic.commands.CommandTestUtil.QUANTITY_DESC_GABAPENTIN;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COMPANY_GABAPENTIN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXPIRY_GABAPENTIN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_GABAPENTIN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUANTITY_GABAPENTIN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.testutil.TypicalMedicines.PARACETAMOL;
import static seedu.address.testutil.TypicalMedicines.AMOXICILLIN;
import static seedu.address.testutil.TypicalMedicines.GABAPENTIN;
import static seedu.address.testutil.TypicalMedicines.ACETAMINOPHEN;
import static seedu.address.testutil.TypicalMedicines.HYDROCHLOROTHIAZIDE;
import static seedu.address.testutil.TypicalMedicines.KEYWORD_MATCHING_SODIUM;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.medicine.Company;
import seedu.address.model.medicine.Expiry;
import seedu.address.model.medicine.Name;
import seedu.address.model.medicine.Medicine;
import seedu.address.model.medicine.Quantity;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.MedicineBuilder;
import seedu.address.testutil.MedicineUtil;

public class AddCommandSystemTest extends MediTabsSystemTest {

    @Test
    public void add() {
        Model model = getModel();

        /* ------------------------ Perform add operations on the shown unfiltered list ----------------------------- */

        /* Case: add a medicine without tags to a non-empty inventory, command with leading spaces and trailing spaces
         * -> added
         */
        Medicine toAdd = AMOXICILLIN;
        String command = "   " + AddCommand.COMMAND_WORD + "  " + NAME_DESC_AMOXICILLIN + "  " + QUANTITY_DESC_AMOXICILLIN + " "
                + EXPIRY_DESC_AMOXICILLIN + "   " + COMPANY_DESC_AMOXICILLIN + "   " + TAG_DESC_FRIEND + " ";
        assertCommandSuccess(command, toAdd);

        /* Case: undo adding Amoxicillin to the list -> Amoxicillin deleted */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo adding Amoxicillin to the list -> Amoxicillin added again */
        command = RedoCommand.COMMAND_WORD;
        model.addMedicine(toAdd);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: add a medicine with all fields same as another medicine in the inventory except name -> added */
        toAdd = new MedicineBuilder(AMOXICILLIN).withName(VALID_NAME_GABAPENTIN).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_GABAPENTIN + QUANTITY_DESC_AMOXICILLIN + EXPIRY_DESC_AMOXICILLIN + COMPANY_DESC_AMOXICILLIN
                + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

        /* Case: add a medicine with all fields same as another medicine in the inventory except quantity and expiry
         * -> added
         */
        toAdd = new MedicineBuilder(AMOXICILLIN).withQuantity(VALID_QUANTITY_GABAPENTIN).withExpiry(VALID_EXPIRY_GABAPENTIN).build();
        command = MedicineUtil.getAddCommand(toAdd);
        assertCommandSuccess(command, toAdd);

        /* Case: add to empty inventory -> added */
        deleteAllMedicines();
        assertCommandSuccess(PARACETAMOL);

        /* Case: add a medicine with tags, command with parameters in random order -> added */
        toAdd = GABAPENTIN;
        command = AddCommand.COMMAND_WORD + TAG_DESC_FRIEND + QUANTITY_DESC_GABAPENTIN + COMPANY_DESC_GABAPENTIN + NAME_DESC_GABAPENTIN
                + TAG_DESC_HUSBAND + EXPIRY_DESC_GABAPENTIN;
        assertCommandSuccess(command, toAdd);

        /* Case: add a medicine, missing tags -> added */
        assertCommandSuccess(AMOXICILLIN);

        /* -------------------------- Perform add operation on the shown filtered list ------------------------------ */

        /* Case: filters the medicine list before adding -> added */
        showMedicinesWithName(KEYWORD_MATCHING_SODIUM);
        assertCommandSuccess(HYDROCHLOROTHIAZIDE);

        /* ------------------------ Perform add operation while a medicine card is selected --------------------------- */

        /* Case: selects first card in the medicine list, add a medicine -> added, card selection remains unchanged */
        selectMedicine(Index.fromOneBased(1));
        assertCommandSuccess(ACETAMINOPHEN);

        /* ----------------------------------- Perform invalid add operations --------------------------------------- */

        /* Case: add a duplicate medicine -> rejected */
        command = MedicineUtil.getAddCommand(AMOXICILLIN);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_MEDICINE);

        /* Case: add a duplicate medicine except with different quantity -> rejected */
        toAdd = new MedicineBuilder(AMOXICILLIN).withQuantity(VALID_QUANTITY_GABAPENTIN).build();
        command = MedicineUtil.getAddCommand(toAdd);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_MEDICINE);

        /* Case: add a duplicate medicine except with different expiry date-> rejected */
        toAdd = new MedicineBuilder(AMOXICILLIN).withExpiry(VALID_EXPIRY_GABAPENTIN).build();
        command = MedicineUtil.getAddCommand(toAdd);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_MEDICINE);

        /* Case: add a duplicate medicine except with different company -> rejected */
        toAdd = new MedicineBuilder(AMOXICILLIN).withCompany(VALID_COMPANY_GABAPENTIN).build();
        command = MedicineUtil.getAddCommand(toAdd);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_MEDICINE);

        /* Case: add a duplicate medicine except with different tags -> rejected */
        command = MedicineUtil.getAddCommand(AMOXICILLIN) + " " + PREFIX_TAG.getPrefix() + "friends";
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_MEDICINE);

        /* Case: missing name -> rejected */
        command = AddCommand.COMMAND_WORD + QUANTITY_DESC_AMOXICILLIN + EXPIRY_DESC_AMOXICILLIN + COMPANY_DESC_AMOXICILLIN;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing quantity -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMOXICILLIN + EXPIRY_DESC_AMOXICILLIN + COMPANY_DESC_AMOXICILLIN;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing expiry date-> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMOXICILLIN + QUANTITY_DESC_AMOXICILLIN + COMPANY_DESC_AMOXICILLIN;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing company -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMOXICILLIN + QUANTITY_DESC_AMOXICILLIN + EXPIRY_DESC_AMOXICILLIN;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: invalid keyword -> rejected */
        command = "adds " + MedicineUtil.getMedicineDetails(toAdd);
        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);

        /* Case: invalid name -> rejected */
        command = AddCommand.COMMAND_WORD + INVALID_NAME_DESC + QUANTITY_DESC_AMOXICILLIN + EXPIRY_DESC_AMOXICILLIN + COMPANY_DESC_AMOXICILLIN;
        assertCommandFailure(command, Name.MESSAGE_CONSTRAINTS);

        /* Case: invalid quantity -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMOXICILLIN + INVALID_QUANTITY_DESC + EXPIRY_DESC_AMOXICILLIN + COMPANY_DESC_AMOXICILLIN;
        assertCommandFailure(command, Quantity.MESSAGE_CONSTRAINTS);

        /* Case: invalid expiry date-> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMOXICILLIN + QUANTITY_DESC_AMOXICILLIN + INVALID_EXPIRY_DESC + COMPANY_DESC_AMOXICILLIN;
        assertCommandFailure(command, Expiry.MESSAGE_CONSTRAINTS);

        /* Case: invalid company -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMOXICILLIN + QUANTITY_DESC_AMOXICILLIN + EXPIRY_DESC_AMOXICILLIN + INVALID_COMPANY_DESC;
        assertCommandFailure(command, Company.MESSAGE_CONSTRAINTS);

        /* Case: invalid tag -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMOXICILLIN + QUANTITY_DESC_AMOXICILLIN + EXPIRY_DESC_AMOXICILLIN + COMPANY_DESC_AMOXICILLIN
                + INVALID_TAG_DESC;
        assertCommandFailure(command, Tag.MESSAGE_CONSTRAINTS);
    }

    /**
     * Executes the {@code AddCommand} that adds {@code toAdd} to the model and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code AddCommand} with the details of
     * {@code toAdd}.<br>
     * 4. {@code Storage} and {@code MedicineListPanel} equal to the corresponding components in
     * the current model added with {@code toAdd}.<br>
     * 5. Browser url and selected card remain unchanged.<br>
     * 6. Status bar's sync status changes.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code MediTabsSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see MediTabsSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(Medicine toAdd) {
        assertCommandSuccess(MedicineUtil.getAddCommand(toAdd), toAdd);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(Medicine)}. Executes {@code command}
     * instead.
     * @see AddCommandSystemTest#assertCommandSuccess(Medicine)
     */
    private void assertCommandSuccess(String command, Medicine toAdd) {
        Model expectedModel = getModel();
        expectedModel.addMedicine(toAdd);
        String expectedResultMessage = String.format(AddCommand.MESSAGE_SUCCESS, toAdd);

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Medicine)} except asserts that
     * the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Storage} and {@code MedicineListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     * @see AddCommandSystemTest#assertCommandSuccess(String, Medicine)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Storage} and {@code MedicineListPanel} remain unchanged.<br>
     * 5. Browser url, selected card and status bar remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code MediTabsSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see MediTabsSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
