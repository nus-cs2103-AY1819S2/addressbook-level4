package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.FIRST_SUBORDER_ASC_WITH_PREFIX;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SORT_TYPE_WITH_PREFIX;
import static seedu.address.logic.commands.CommandTestUtil.ORDER_ASC_WITHOUT_PREFIX;
import static seedu.address.logic.commands.CommandTestUtil.ORDER_DES_WITHOUT_PREFIX;
import static seedu.address.logic.commands.CommandTestUtil.ORDER_DES_WITH_PREFIX;
import static seedu.address.logic.commands.CommandTestUtil.SECOND_SUBORDER_DES_WITH_PREFIX;
import static seedu.address.logic.commands.CommandTestUtil.SORT_AUTHOR_WITHOUT_PREFIX;
import static seedu.address.logic.commands.CommandTestUtil.SORT_AUTHOR_WITH_PREFIX;
import static seedu.address.logic.commands.CommandTestUtil.SORT_NAME_WITHOUT_PREFIX;
import static seedu.address.logic.commands.CommandTestUtil.SORT_NAME_WITH_PREFIX;
import static seedu.address.logic.commands.CommandTestUtil.SORT_RATING_WITHOUT_PREFIX;
import static seedu.address.logic.commands.CommandTestUtil.SORT_RATING_WITH_PREFIX;
import static seedu.address.logic.commands.CommandTestUtil.SORT_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.THIRD_SUBORDER_ASC_WITH_PREFIX;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import seedu.address.logic.commands.SortBookCommand;
import seedu.address.model.Model;

public class SortBookCommandSystemTest extends BookShelfSystemTest {

    @Test
    public void sort() {
        Model model = getModel();
        List<String> sortTypes = new ArrayList<>();
        /* ----------------------------------- Perform valid sort operations ---------------------------------------- */

        /* Case: sort list by rating without specify order */
        String command = SortBookCommand.COMMAND_WORD + SORT_RATING_WITH_PREFIX;
        String mainOrder = null;
        Map<String, String> map = new HashMap<>();
        sortTypes.add(SORT_RATING_WITHOUT_PREFIX);
        model.sortBook(sortTypes, mainOrder, map);
        assertCommandSuccess(command, model, SortBookCommand.MESSAGE_SUCCESS);

        /* Case: sort list by rating with specify order */
        command = SortBookCommand.COMMAND_WORD + SORT_RATING_WITH_PREFIX + ORDER_DES_WITH_PREFIX;
        sortTypes = new ArrayList<>();
        sortTypes.add(SORT_RATING_WITHOUT_PREFIX);
        mainOrder = SORT_RATING_WITHOUT_PREFIX;

        model.sortBook(sortTypes, mainOrder, map);
        assertCommandSuccess(command, model, SortBookCommand.MESSAGE_SUCCESS);

        /* Case: sort list by author and rating with mainOrder*/
        command = SortBookCommand.COMMAND_WORD + SORT_AUTHOR_WITH_PREFIX
            + SORT_RATING_WITH_PREFIX + ORDER_DES_WITH_PREFIX;
        sortTypes = new ArrayList<>();
        sortTypes.add(SORT_AUTHOR_WITHOUT_PREFIX);
        sortTypes.add(SORT_RATING_WITHOUT_PREFIX);
        mainOrder = ORDER_DES_WITHOUT_PREFIX;
        model.sortBook(sortTypes, mainOrder, map);
        assertCommandSuccess(command, model, SortBookCommand.MESSAGE_SUCCESS);

        /* Case: sort list by author and rating with mainOrder and subOrder */
        command = SortBookCommand.COMMAND_WORD + SORT_AUTHOR_WITH_PREFIX
            + SORT_RATING_WITH_PREFIX + ORDER_DES_WITH_PREFIX
            + FIRST_SUBORDER_ASC_WITH_PREFIX + SECOND_SUBORDER_DES_WITH_PREFIX;
        sortTypes = new ArrayList<>();
        sortTypes.add(SORT_AUTHOR_WITHOUT_PREFIX);
        sortTypes.add(SORT_RATING_WITHOUT_PREFIX);
        mainOrder = ORDER_DES_WITHOUT_PREFIX;
        map.put(SORT_AUTHOR_WITHOUT_PREFIX, ORDER_ASC_WITHOUT_PREFIX);
        map.put(SORT_RATING_WITHOUT_PREFIX, ORDER_DES_WITHOUT_PREFIX);
        model.sortBook(sortTypes, mainOrder, map);
        assertCommandSuccess(command, model, SortBookCommand.MESSAGE_SUCCESS);

        /* Case: sort list by rating, book name and author with subOrder */
        command = SortBookCommand.COMMAND_WORD + SORT_RATING_WITH_PREFIX
            + SORT_NAME_WITH_PREFIX + SORT_AUTHOR_WITH_PREFIX
            + FIRST_SUBORDER_ASC_WITH_PREFIX + SECOND_SUBORDER_DES_WITH_PREFIX + THIRD_SUBORDER_ASC_WITH_PREFIX;
        sortTypes = new ArrayList<>();
        sortTypes.add(SORT_RATING_WITHOUT_PREFIX);
        sortTypes.add(SORT_NAME_WITHOUT_PREFIX);
        sortTypes.add(SORT_AUTHOR_WITHOUT_PREFIX);

        mainOrder = null;
        map = new HashMap<>();
        map.put(SORT_RATING_WITHOUT_PREFIX, ORDER_ASC_WITHOUT_PREFIX);
        map.put(SORT_NAME_WITHOUT_PREFIX, ORDER_DES_WITHOUT_PREFIX);
        map.put(SORT_AUTHOR_WITHOUT_PREFIX, ORDER_ASC_WITHOUT_PREFIX);
        model.sortBook(sortTypes, mainOrder, map);
        assertCommandSuccess(command, model, SortBookCommand.MESSAGE_SUCCESS);





        /* ----------------------------------- Perform invalid sort operations -------------------------------------- */

        /* Case: sort list without st */
        command = SortBookCommand.COMMAND_WORD;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortBookCommand.MESSAGE_USAGE));

        /* Case: sort list with unknown st type */
        command = SortBookCommand.COMMAND_WORD + INVALID_SORT_TYPE_WITH_PREFIX;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortBookCommand.MESSAGE_USAGE));

        /* Case: sort list with extra attributes */
        command = SortBookCommand.COMMAND_WORD + SORT_UNKNOWN_COMMAND;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortBookCommand.MESSAGE_USAGE));

        /* Case: sort list with extra sub order */
        command = SortBookCommand.COMMAND_WORD + SORT_RATING_WITH_PREFIX
            + FIRST_SUBORDER_ASC_WITH_PREFIX + SECOND_SUBORDER_DES_WITH_PREFIX;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortBookCommand.MESSAGE_USAGE));

    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Result display box displays {@code expectedResultMessage}.<br>
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedBookCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Storage} and {@code BookListPanel} remain unchanged.<br>
     * 5. Browser url, selected card and status bar remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code BookShelfTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see BookShelfSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedBookCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
