//package systemtests;
//
//import static org.junit.Assert.assertTrue;
//import static seedu.address.commons.core.Messages.MESSAGE_INVALID_DOCTOR_DISPLAYED_INDEX;
//import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
//import static seedu.address.logic.commands.DeleteDoctorCommand.MESSAGE_DELETE_DOCTOR_SUCCESS;
//import static seedu.address.testutil.TestUtil.getDoctor;
//import static seedu.address.testutil.TestUtil.getDoctorLastIndex;
//import static seedu.address.testutil.TestUtil.getDoctorMidIndex;
//import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
//import static seedu.address.testutil.TypicalPatients.KEYWORD_MATCHING_MEIER;
//
//import org.junit.Test;
//
//import seedu.address.commons.core.Messages;
//import seedu.address.commons.core.index.Index;
//import seedu.address.logic.commands.DeleteDoctorCommand;
//import seedu.address.logic.commands.RedoCommand;
//import seedu.address.logic.commands.UndoCommand;
//import seedu.address.model.Model;
//import seedu.address.model.person.Doctor;
//
//public class DeleteDoctorCommandSystemTest extends DocXSystemTest {
//
//    private static final String MESSAGE_INVALID_DELETE_COMMAND_FORMAT =
//            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteDoctorCommand.MESSAGE_USAGE);
//
//    @Test
//    public void delete() {
//        /* ---------------- Performing delete operation while an unfiltered list is being shown ------------------- */
//
//        /* Case: delete the first doctor in the list, command with leading spaces and trailing spaces -> deleted */
//        Model expectedModel = getModel();
//        String command = "     "
//                + DeleteDoctorCommand.COMMAND_WORD + "      " + INDEX_FIRST_PERSON.getOneBased() + "       ";
//        Doctor deletedDoctor = removeDoctor(expectedModel, INDEX_FIRST_PERSON);
//        String expectedResultMessage = String.format(MESSAGE_DELETE_DOCTOR_SUCCESS, deletedDoctor);
//        assertCommandSuccess(command, expectedModel, expectedResultMessage);
//
//        /* Case: delete the last doctor in the list -> deleted */
//        Model modelBeforeDeletingLast = getModel();
//        Index lastDoctorIndex = getDoctorLastIndex(modelBeforeDeletingLast);
//        assertCommandSuccess(lastDoctorIndex);
//
//        /* Case: undo deleting the last doctor in the list -> last doctor restored */
//        command = UndoCommand.COMMAND_WORD;
//        expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
//        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);
//
//        /* Case: redo deleting the last doctor in the list -> last doctor deleted again */
//        command = RedoCommand.COMMAND_WORD;
//        removeDoctor(modelBeforeDeletingLast, lastDoctorIndex);
//        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
//        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);
//
//        /* Case: delete the middle doctor in the list -> deleted */
//        Index middleDoctorIndex = getDoctorMidIndex(getModel());
//        assertCommandSuccess(middleDoctorIndex);
//
//        /* ----------------- Performing delete operation while a filtered list is being shown --------------------- */
//
//        /* Case: filtered doctor list, delete index within bounds of address book and doctor list -> deleted */
//        showDoctorsWithName(KEYWORD_MATCHING_MEIER);
//        Index index = INDEX_FIRST_PERSON;
//        assertTrue(index.getZeroBased() < getModel().getFilteredDoctorList().size());
//        assertCommandSuccess(index);
//
//        /* Case: filtered doctor list, delete index within bounds of address book but out of bounds of doctor list
//         * -> rejected
//         */
//        showDoctorsWithName(KEYWORD_MATCHING_MEIER);
//        int invalidIndex = getModel().getAddressBook().getDoctorList().size();
//        command = DeleteDoctorCommand.COMMAND_WORD + " " + invalidIndex;
//        assertCommandFailure(command, MESSAGE_INVALID_DOCTOR_DISPLAYED_INDEX);
//
//        /* -------------------- Performing delete operation while a doctor card is selected ---------------------- */
//
//        /* Case: delete the doctor patient -> doctor list panel selects the doctor before the deleted doctor */
//        showAllDoctors(); showAllPatients();
//        expectedModel = getModel();
//        Index selectedIndex = getDoctorLastIndex(expectedModel);
//        Index expectedIndex = Index.fromZeroBased(selectedIndex.getZeroBased() - 1);
//        //selectDoctor(selectedIndex);
//        command = DeleteDoctorCommand.COMMAND_WORD + " " + selectedIndex.getOneBased();
//        deletedDoctor = removeDoctor(expectedModel, selectedIndex);
//        expectedResultMessage = String.format(MESSAGE_DELETE_DOCTOR_SUCCESS, deletedDoctor);
//        assertCommandSuccess(command, expectedModel, expectedResultMessage, expectedIndex);
//
//        /* -------------------------------- Performing invalid delete operation ----------------------------------- */
//
//        /* Case: invalid index (0) -> rejected */
//        command = DeleteDoctorCommand.COMMAND_WORD + " 0";
//        assertCommandFailure(command, MESSAGE_INVALID_DELETE_COMMAND_FORMAT);
//
//        /* Case: invalid index (-1) -> rejected */
//        command = DeleteDoctorCommand.COMMAND_WORD + " -1";
//        assertCommandFailure(command, MESSAGE_INVALID_DELETE_COMMAND_FORMAT);
//
//        /* Case: invalid index (size + 1) -> rejected */
//        Index outOfBoundsIndex = Index.fromOneBased(
//                getModel().getAddressBook().getDoctorList().size() + 1);
//        command = DeleteDoctorCommand.COMMAND_WORD + " " + outOfBoundsIndex.getOneBased();
//        assertCommandFailure(command, MESSAGE_INVALID_DOCTOR_DISPLAYED_INDEX);
//
//        /* Case: invalid arguments (alphabets) -> rejected */
//        assertCommandFailure(
//                DeleteDoctorCommand.COMMAND_WORD + " abc", MESSAGE_INVALID_DELETE_COMMAND_FORMAT);
//
//        /* Case: invalid arguments (extra argument) -> rejected */
//        assertCommandFailure(
//                DeleteDoctorCommand.COMMAND_WORD + " 1 abc", MESSAGE_INVALID_DELETE_COMMAND_FORMAT);
//
//        /* Case: mixed case command word -> rejected */
//        assertCommandFailure("DelETE 1", MESSAGE_UNKNOWN_COMMAND);
//    }
//
//    /**
//     * Removes the {@code Doctor} at the specified {@code index} in {@code model}'s docX.
//     *
//     * @return the removed doctor
//     */
//    private Doctor removeDoctor(Model model, Index index) {
//        Doctor targetDoctor = getDoctor(model, index);
//        model.deleteDoctor(targetDoctor);
//        return targetDoctor;
//    }
//
//    /**
//     * Deletes the doctor at {@code toDelete} by creating a default {@code DeleteDoctorCommand}
//     * using {@code toDelete} and
//     * performs the same verification as {@code assertCommandSuccess(String, Model, String)}.
//     *
//     * @see DeleteDoctorCommandSystemTest#assertCommandSuccess(String, Model, String)
//     */
//    private void assertCommandSuccess(Index toDelete) {
//        Model expectedModel = getModel();
//        Doctor deletedDoctor = removeDoctor(expectedModel, toDelete);
//        String expectedResultMessage = String.format(MESSAGE_DELETE_DOCTOR_SUCCESS, deletedDoctor);
//
//        assertCommandSuccess(
//                DeleteDoctorCommand.COMMAND_WORD + " " + toDelete.getOneBased(),
//                      expectedModel, expectedResultMessage);
//    }
//
//    /**
//     * Executes {@code command} and in addition,<br>
//     * 1. Asserts that the command box displays an empty string.<br>
//     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
//     * 3. Asserts that the browser url and selected card remains unchanged.<br>
//     * 4. Asserts that the status bar's sync status changes.<br>
//     * 5. Asserts that the command box has the default style class.<br>
//     * Verifications 1 and 2 are performed by
//     * {@code DocXSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.
//     *
//     * @see DocXSystemTest#assertApplicationDisplaysExpected(String, String, Model)
//     */
//    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
//        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
//    }
//
//    /**
//     * Performs the same verification as {@code assertCommandSuccess(String, Model, String)}
//     * except that the browser url
//     * and selected card are expected to update accordingly depending
//     * on the card at {@code expectedSelectedCardIndex}.
//     *
//     * @see DeleteDoctorCommandSystemTest#assertCommandSuccess(String, Model, String)
//     * @see DocXSystemTest#assertSelectedCardChanged(Index)
//     */
//    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
//                                      Index expectedSelectedCardIndex) {
//        executeCommand(command);
//        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
//
//        if (expectedSelectedCardIndex != null) {
//            assertSelectedCardChanged(expectedSelectedCardIndex);
//        } else {
//            assertSelectedCardUnchanged();
//        }
//
//        assertCommandBoxShowsDefaultStyle();
//        assertStatusBarUnchangedExceptSyncStatus();
//    }
//
//    /**
//     * Executes {@code command} and in addition,<br>
//     * 1. Asserts that the command box displays {@code command}.<br>
//     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
//     * 3. Asserts that the browser url, selected card and status bar remain unchanged.<br>
//     * 4. Asserts that the command box has the error style.<br>
//     * Verifications 1 and 2 are performed by
//     * {@code DocXSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
//     *
//     * @see DocXSystemTest#assertApplicationDisplaysExpected(String, String, Model)
//     */
//    private void assertCommandFailure(String command, String expectedResultMessage) {
//        Model expectedModel = getModel();
//
//        executeCommand(command);
//        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
//        assertSelectedCardUnchanged();
//        assertCommandBoxShowsErrorStyle();
//        assertStatusBarUnchanged();
//    }
//}
