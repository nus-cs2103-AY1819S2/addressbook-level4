package systemtests;

import static org.junit.Assert.assertFalse;
import static seedu.equipment.commons.core.Messages.MESSAGE_EQUIPMENTS_LISTED_OVERVIEW;
import static seedu.equipment.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.equipment.testutil.TypicalEquipments.ANCHORVALECC;
import static seedu.equipment.testutil.TypicalEquipments.AYERRAJAHCC;
import static seedu.equipment.testutil.TypicalEquipments.BUKITGCC;
import static seedu.equipment.testutil.TypicalEquipments.CHENGSANCC;
import static seedu.equipment.testutil.TypicalEquipments.HWIYOHCC;
import static seedu.equipment.testutil.TypicalEquipments.JURONGREENCC;
import static seedu.equipment.testutil.TypicalEquipments.KEYWORD_MATCHING_CC;
import static seedu.equipment.testutil.TypicalEquipments.TECKGHEECC;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import seedu.equipment.commons.core.index.Index;
import seedu.equipment.logic.commands.DeleteCommand;
import seedu.equipment.logic.commands.FindCommand;
import seedu.equipment.logic.commands.RedoCommand;
import seedu.equipment.logic.commands.UndoCommand;
import seedu.equipment.model.Model;
import seedu.equipment.model.tag.Tag;

public class FindCommandSystemTest extends EquipmentManagerSystemTest {

    @Test
    public void find() {
        /* Case: find multiple persons in equipment book, command with leading spaces and trailing spaces
         * -> 7 persons found
         */
        String command = "   " + FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_CC + "   ";
        Model expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, ANCHORVALECC, HWIYOHCC, AYERRAJAHCC, TECKGHEECC, BUKITGCC,
                CHENGSANCC, JURONGREENCC);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: repeat previous find command where equipment list is displaying the persons we are finding
         * -> 7 equipment found
         */
        command = FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_CC;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find equipment where equipment list is not displaying the equipment we are finding
         * -> 1 equipment found
         */
        command = FindCommand.COMMAND_WORD + " Teck Ghee";
        ModelHelper.setFilteredList(expectedModel, TECKGHEECC);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple persons in equipment book, 2 keywords -> 2 equipments found */
        command = FindCommand.COMMAND_WORD + " Hwi Ayer";
        ModelHelper.setFilteredList(expectedModel, HWIYOHCC, AYERRAJAHCC);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple persons in equipment book, 2 keywords in reversed order -> 2 equipments found */
        command = FindCommand.COMMAND_WORD + " Hwi Ayer";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple persons in equipment book, 2 keywords with 1 repeat -> 2 equipments found */
        command = FindCommand.COMMAND_WORD + " Hwi Ayer Hwi";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple persons in equipment book, 2 matching keywords and 1 non-matching keyword
         * -> 2 equipments found
         */
        command = FindCommand.COMMAND_WORD + " Hwi Ayer NonMatchingKeyWord";
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

        /* Case: find same persons in equipment book after deleting 1 of them -> 1 equipment found */
        executeCommand(DeleteCommand.COMMAND_WORD + " 1");
        assertFalse(getModel().getEquipmentManager().getPersonList().contains(HWIYOHCC));
        command = FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_CC;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, ANCHORVALECC, AYERRAJAHCC, TECKGHEECC, BUKITGCC,
                CHENGSANCC, JURONGREENCC);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find equipment in equipment book, keyword is same as name but of different case -> 1 equipment found */
        command = FindCommand.COMMAND_WORD + " ANcHoRVaLe";
        ModelHelper.setFilteredList(expectedModel, ANCHORVALECC);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find equipment in equipment book, keyword is substring of name -> 0 persons found */
        command = FindCommand.COMMAND_WORD + " Mei";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find equipment in equipment book, name is substring of keyword -> 0 persons found */
        command = FindCommand.COMMAND_WORD + " Meiers";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find equipment not in equipment book -> 0 persons found */
        command = FindCommand.COMMAND_WORD + " Mark";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find phone number of equipment in equipment book -> 0 persons found */
        command = FindCommand.COMMAND_WORD + " " + AYERRAJAHCC.getPhone().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find equipment of equipment in equipment book -> 0 persons found */
        command = FindCommand.COMMAND_WORD + " " + AYERRAJAHCC.getAddress().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find email of equipment in equipment book -> 0 persons found */
        command = FindCommand.COMMAND_WORD + " " + AYERRAJAHCC.getEmail().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find tags of equipment in equipment book -> 0 persons found */
        List<Tag> tags = new ArrayList<>(AYERRAJAHCC.getTags());
        command = FindCommand.COMMAND_WORD + " " + tags.get(0).tagName;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find while a equipment is selected -> selected card deselected */
        showAllPersons();
        selectPerson(Index.fromOneBased(1));
        assertFalse(getPersonListPanel().getHandleToSelectedCard().getName().equals(AYERRAJAHCC.getName().name));
        command = FindCommand.COMMAND_WORD + " Ayer";
        ModelHelper.setFilteredList(expectedModel, AYERRAJAHCC);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardDeselected();

        /* Case: find equipment in empty equipment book -> 0 persons found */
        deleteAllPersons();
        command = FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_CC;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, AYERRAJAHCC);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: mixed case command word -> rejected */
        command = "FiNd Meier";
        assertCommandFailure(command, MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code Messages#MESSAGE_EQUIPMENTS_LISTED_OVERVIEW} with the number of people in the filtered list,
     * and the model related components equal to {@code expectedModel}.
     * These verifications are done by
     * {@code EquipmentManagerSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar remains unchanged, and the command box has the default style class, and the
     * selected card updated accordingly, depending on {@code cardStatus}.
     * @see EquipmentManagerSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel) {
        String expectedResultMessage = String.format(
                MESSAGE_EQUIPMENTS_LISTED_OVERVIEW, expectedModel.getFilteredPersonList().size());

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code EquipmentManagerSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
     * @see EquipmentManagerSystemTest#assertApplicationDisplaysExpected(String, String, Model)
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
