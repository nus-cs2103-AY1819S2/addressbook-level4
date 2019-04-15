package systemtests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.COMPANY_DESC_AMOXICILLIN;
import static seedu.address.logic.commands.CommandTestUtil.COMPANY_DESC_GABAPENTIN;
import static seedu.address.logic.commands.CommandTestUtil.COMPANY_DESC_LISNOPRIL;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_COMPANY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMOXICILLIN;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_GABAPENTIN;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FEVER;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_PAINKILER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COMPANY_AMOXICILLIN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COMPANY_GABAPENTIN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMOXICILLIN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_GABAPENTIN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FEVER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_MEDICINES;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_MEDICINE;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_MEDICINE;
import static seedu.address.testutil.TypicalMedicines.AMOXICILLIN;
import static seedu.address.testutil.TypicalMedicines.GABAPENTIN;
import static seedu.address.testutil.TypicalMedicines.KEYWORD_MATCHING_SODIUM;
import static seedu.address.testutil.TypicalMedicines.LISINOPRIL;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.medicine.Company;
import seedu.address.model.medicine.Medicine;
import seedu.address.model.medicine.Name;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.MedicineBuilder;
import seedu.address.testutil.MedicineUtil;

public class EditCommandSystemTest extends MediTabsSystemTest {

    @Test
    public void edit() {
        Model model = getModel();

        /* ----------------- Performing edit operation while an unfiltered list is being shown ---------------------- */

        /* Case: edit all fields, command with leading spaces, trailing spaces and multiple spaces between each field
         * -> edited
         */
        Index index = INDEX_FIRST_MEDICINE;
        Medicine medicineToEdit = model.getFilteredMedicineList().get(index.getZeroBased());
        String command = " " + EditCommand.COMMAND_WORD + "  " + index.getOneBased() + "  " + NAME_DESC_AMOXICILLIN
                + "  " + COMPANY_DESC_AMOXICILLIN + " " + TAG_DESC_FEVER + " ";
        Medicine editedMedicine = new MedicineBuilder(medicineToEdit).withName(VALID_NAME_AMOXICILLIN)
                .withCompany(VALID_COMPANY_AMOXICILLIN).withTags(VALID_TAG_FEVER).build();
        assertCommandSuccess(command, index, editedMedicine);

        /* Case: undo editing the last medicine in the list -> last medicine restored */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo editing the last medicine in the list -> last medicine edited again */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        model.setMedicine(getModel().getFilteredMedicineList().get(INDEX_FIRST_MEDICINE.getZeroBased()),
                editedMedicine);
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: edit a medicine with new values same as existing values -> edited */
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_AMOXICILLIN
                + COMPANY_DESC_AMOXICILLIN + TAG_DESC_FEVER;
        medicineToEdit = getModel().getFilteredMedicineList().get(index.getZeroBased());
        editedMedicine = new MedicineBuilder(AMOXICILLIN).withUneditableFields(medicineToEdit).build();
        assertEquals(medicineToEdit, editedMedicine);
        assertCommandSuccess(command, index, editedMedicine);

        /* Case: edit a medicine with new values same as another medicine's values but with different name -> edited */
        assertTrue(getModel().getInventory().getMedicineList().contains(LISINOPRIL));
        index = INDEX_SECOND_MEDICINE;
        medicineToEdit = getModel().getFilteredMedicineList().get(index.getZeroBased());
        assertNotEquals(medicineToEdit, LISINOPRIL);
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_GABAPENTIN + " "
                + PREFIX_TAG.getPrefix() + " " + COMPANY_DESC_LISNOPRIL;
        editedMedicine = new MedicineBuilder(LISINOPRIL).withName(VALID_NAME_GABAPENTIN)
                .withUneditableFields(medicineToEdit).build();
        assertCommandSuccess(command, index, editedMedicine);

        /* Case: edit a medicine with new values same as another medicine's values but with different company
         * -> edited
         */
        index = INDEX_SECOND_MEDICINE;
        medicineToEdit = model.getFilteredMedicineList().get(index.getZeroBased());
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_AMOXICILLIN
                + COMPANY_DESC_GABAPENTIN + TAG_DESC_FEVER;
        editedMedicine = new MedicineBuilder(AMOXICILLIN).withUneditableFields(medicineToEdit)
                .withCompany(VALID_COMPANY_GABAPENTIN).build();
        assertCommandSuccess(command, index, editedMedicine);

        /* Case: clear tags -> cleared */
        index = INDEX_FIRST_MEDICINE;
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + " " + PREFIX_TAG.getPrefix();
        medicineToEdit = getModel().getFilteredMedicineList().get(index.getZeroBased());
        editedMedicine = new MedicineBuilder(medicineToEdit).withTags().build();
        assertCommandSuccess(command, index, editedMedicine);

        /* ------------------ Performing edit operation while a filtered list is being shown ------------------------ */

        /* Case: filtered medicine list, edit index within bounds of inventory and medicine list -> edited */
        showMedicinesWithName(KEYWORD_MATCHING_SODIUM);
        index = INDEX_FIRST_MEDICINE;
        assertTrue(index.getZeroBased() < getModel().getFilteredMedicineList().size());
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + " " + NAME_DESC_AMOXICILLIN;
        medicineToEdit = getModel().getFilteredMedicineList().get(index.getZeroBased());
        editedMedicine = new MedicineBuilder(medicineToEdit).withName(VALID_NAME_AMOXICILLIN).build();
        assertCommandSuccess(command, index, editedMedicine);

        /* Case: filtered medicine list, edit index within bounds of inventory but out of bounds of medicine list
         * -> rejected
         */
        showMedicinesWithName(KEYWORD_MATCHING_SODIUM);
        int invalidIndex = getModel().getInventory().getMedicineList().size();
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + invalidIndex + NAME_DESC_GABAPENTIN,
                Messages.MESSAGE_INVALID_MEDICINE_DISPLAYED_INDEX);

        /* --------------------------------- Performing invalid edit operation -------------------------------------- */

        /* Case: invalid index (0) -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " 0" + NAME_DESC_GABAPENTIN,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        /* Case: invalid index (-1) -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " -1" + NAME_DESC_GABAPENTIN,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        /* Case: invalid index (size + 1) -> rejected */
        invalidIndex = getModel().getFilteredMedicineList().size() + 1;
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + invalidIndex + NAME_DESC_GABAPENTIN,
                Messages.MESSAGE_INVALID_MEDICINE_DISPLAYED_INDEX);

        /* Case: missing index -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + NAME_DESC_GABAPENTIN,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        /* Case: missing all fields -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_MEDICINE.getOneBased(),
                EditCommand.MESSAGE_NOT_EDITED);

        /* Case: invalid name -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_MEDICINE.getOneBased() + INVALID_NAME_DESC,
                Name.MESSAGE_CONSTRAINTS);

        /* Case: invalid company -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_MEDICINE.getOneBased() + INVALID_COMPANY_DESC,
                Company.MESSAGE_CONSTRAINTS);

        /* Case: invalid tag -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_MEDICINE.getOneBased() + INVALID_TAG_DESC,
                Tag.MESSAGE_CONSTRAINTS);

        /* Case: edit a medicine with new values same as another medicine's values -> rejected */
        executeCommand(MedicineUtil.getAddCommand(GABAPENTIN));
        assertTrue(getModel().getInventory().getMedicineList().contains(GABAPENTIN));
        index = INDEX_FIRST_MEDICINE;
        assertFalse(getModel().getFilteredMedicineList().get(index.getZeroBased()).equals(GABAPENTIN));
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_GABAPENTIN + COMPANY_DESC_GABAPENTIN
                + TAG_DESC_FEVER + TAG_DESC_PAINKILER;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_MEDICINE);

        /* Case: edit a medicine with new values same as another medicine's but with different tags -> rejected */
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_GABAPENTIN
                + COMPANY_DESC_GABAPENTIN + TAG_DESC_PAINKILER;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_MEDICINE);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Index, Medicine, Index)} except that
     * the loaded information table and selected card remain unchanged.
     * @param toEdit the index of the current model's filtered list
     * @see EditCommandSystemTest#assertCommandSuccess(String, Index, Medicine, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, Medicine editedMedicine) {
        assertCommandSuccess(command, toEdit, editedMedicine, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} and in addition,<br>
     * 1. Asserts that result display box displays the success message of executing {@code EditCommand}.<br>
     * 2. Asserts that the model related components are updated to reflect the medicine at index {@code toEdit} being
     * updated to values specified {@code editedMedicine}.<br>
     * @param toEdit the index of the current model's filtered list.
     * @see EditCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, Medicine editedMedicine,
            Index expectedSelectedCardIndex) {
        Model expectedModel = getModel();
        expectedModel.setMedicine(expectedModel.getFilteredMedicineList().get(toEdit.getZeroBased()), editedMedicine);
        expectedModel.updateFilteredMedicineList(PREDICATE_SHOW_ALL_MEDICINES);

        assertCommandSuccess(command, expectedModel,
                String.format(EditCommand.MESSAGE_EDIT_MEDICINE_SUCCESS, editedMedicine), expectedSelectedCardIndex);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} except that the
     * loaded information table and selected card remain unchanged.
     * @see EditCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the loaded information table and selected card update accordingly depending on the card at
     * {@code expectedSelectedCardIndex}.<br>
     * 4. Asserts that the status bar's sync status changes.<br>
     * 5. Asserts that the command box has the default style class.<br>
     * Verifications 1 and 2 are performed by
     * {@code MediTabsSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see MediTabsSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     * @see MediTabsSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
            Index expectedSelectedCardIndex) {
        executeCommand(command);
        expectedModel.updateFilteredMedicineList(PREDICATE_SHOW_ALL_MEDICINES);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        if (expectedSelectedCardIndex != null) {
            assertSelectedCardChanged(expectedSelectedCardIndex);
        } else {
            assertSelectedCardUnchanged();
        }
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays {@code command}.<br>
     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the loaded information table, selected card and status bar remain unchanged.<br>
     * 4. Asserts that the command box has the error style.<br>
     * Verifications 1 and 2 are performed by
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
