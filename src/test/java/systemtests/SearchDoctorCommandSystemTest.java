package systemtests;

import static org.junit.Assert.assertFalse;
import static seedu.address.commons.core.Messages.MESSAGE_DOCTORS_LISTED_OVERVIEW;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.TypicalDoctors.ALICE;
import static seedu.address.testutil.TypicalDoctors.CARL;
import static seedu.address.testutil.TypicalDoctors.DANIEL;
import static seedu.address.testutil.TypicalDoctors.KEYWORD_MATCHING_MEIER;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteDoctorCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SearchDoctorCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.tag.Specialisation;

public class SearchDoctorCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void find() {
        /* Case: find multiple doctors in docX, command with leading spaces and trailing spaces
         * -> 2 doctors found
         */
        String command = "   " + SearchDoctorCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_MEIER + "   ";
        Model expectedModel = getModel();
        ModelHelper.setFilteredDoctorList(expectedModel, ALICE, DANIEL); // first names of Benson and Daniel are "Meier"
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: repeat previous find command where doctor list is displaying the doctors we are finding
         * -> 2 doctors found
         */
        command = SearchDoctorCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_MEIER;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find doctor where doctor list is not displaying the doctor we are finding -> 1 doctor found */
        command = SearchDoctorCommand.COMMAND_WORD + " Carl";
        ModelHelper.setFilteredDoctorList(expectedModel, CARL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple doctors in docX, 2 keywords -> 2 doctors found */
        command = SearchDoctorCommand.COMMAND_WORD + " Benson Daniel";
        ModelHelper.setFilteredDoctorList(expectedModel, ALICE, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple doctors in docX, 2 keywords in reversed order -> 2 doctors found */
        command = SearchDoctorCommand.COMMAND_WORD + " Daniel Benson";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple doctors in docX, 2 keywords with 1 repeat -> 2 doctors found */
        command = SearchDoctorCommand.COMMAND_WORD + " Daniel Benson Daniel";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple doctors in docX, 2 matching keywords and 1 non-matching keyword
         * -> 2 doctors found
         */
        command = SearchDoctorCommand.COMMAND_WORD + " Daniel Benson NonMatchingKeyWord";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: undo previous find command -> rejected */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: redo previous find command -> rejected */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: find same doctors in docX after deleting 1 of them -> 1 doctor found */
        executeCommand(DeleteDoctorCommand.COMMAND_WORD + " 1");
        assertFalse(getModel().getAddressBook().getDoctorList().contains(ALICE));
        command = SearchDoctorCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_MEIER;
        expectedModel = getModel();
        ModelHelper.setFilteredDoctorList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find doctor in docX, keyword is same as name but of different case -> 1 doctor found */
        command = SearchDoctorCommand.COMMAND_WORD + " MeIeR";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find doctor in docX, keyword is substring of name -> 0 doctor found */
        command = SearchDoctorCommand.COMMAND_WORD + " Mei";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find doctor in docX, name is substring of keyword -> 0 doctors found */
        command = SearchDoctorCommand.COMMAND_WORD + " Meiers";
        ModelHelper.setFilteredDoctorList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find doctor not in docX -> 0 doctors found */
        command = SearchDoctorCommand.COMMAND_WORD + " Mark";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find gender of doctor in docX -> 0 doctors found */
        command = SearchDoctorCommand.COMMAND_WORD + " " + DANIEL.getGender().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find age of doctor in docX -> 0 doctors found */
        command = SearchDoctorCommand.COMMAND_WORD + " " + DANIEL.getAge().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find phone number of doctor in docX -> 0 doctors found */
        command = SearchDoctorCommand.COMMAND_WORD + " " + DANIEL.getPhone().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find specialisations of patient in docX -> 0 doctors found */
        List<Specialisation> tags = new ArrayList<>(DANIEL.getSpecs());
        command = SearchDoctorCommand.COMMAND_WORD + " " + tags.get(0).specialisation;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find while a doctor is selected -> selected card deselected */
        showAllDoctors();
        selectDoctor(Index.fromOneBased(1));
        assertFalse(getDoctorListPanel().getHandleToSelectedCard().getName().equals(DANIEL.getName().fullName));
        command = SearchDoctorCommand.COMMAND_WORD + " Daniel";
        ModelHelper.setFilteredDoctorList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardDeselected();

        /* Case: find doctor in empty docX -> 0 doctors found */
        deleteAllDoctors();
        command = SearchDoctorCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_MEIER;
        expectedModel = getModel();
        ModelHelper.setFilteredDoctorList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: mixed case command word -> rejected */
        command = "FiNd Meier";
        assertCommandFailure(command, MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code Messages#MESSAGE_DOCTORS_LISTED_OVERVIEW} with the number of doctors in the filtered list,
     * and the model related components equal to {@code expectedModel}.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar remains unchanged, and the command box has the default style class, and the
     * selected card updated accordingly, depending on {@code cardStatus}.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel) {
        String expectedResultMessage = String.format(
                MESSAGE_DOCTORS_LISTED_OVERVIEW, expectedModel.getFilteredDoctorList().size());

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
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
