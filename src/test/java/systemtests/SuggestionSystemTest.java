package systemtests;

import static org.junit.Assert.assertFalse;

import org.junit.Test;
import org.testfx.api.FxRobot;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.BackCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.CopyCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.GoToCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.model.Model;
import seedu.address.ui.MainWindow;


public class SuggestionSystemTest extends AddressBookSystemTest {

    @Test
    public void shortcuts() {
        Model model = getModel();

        assertFalse(MainWindow.isGoToMode());

        //patient mode
        String suggestCmd = AddCommand.COMMAND_WORD;
        String expMsg = AddCommand.MAIN_USAGE;
        assertSuggestionCommandResult(suggestCmd, expMsg, model);

        suggestCmd = ClearCommand.COMMAND_WORD;
        expMsg = ClearCommand.MAIN_USAGE;
        assertSuggestionCommandResult(suggestCmd, expMsg, model);

        suggestCmd = CopyCommand.COMMAND_WORD;
        expMsg = CopyCommand.MAIN_USAGE;
        assertSuggestionCommandResult(suggestCmd, expMsg, model);

        suggestCmd = DeleteCommand.COMMAND_WORD;
        expMsg = DeleteCommand.MAIN_USAGE;
        assertSuggestionCommandResult(suggestCmd, expMsg, model);

        suggestCmd = EditCommand.COMMAND_WORD;
        expMsg = EditCommand.MAIN_USAGE;
        assertSuggestionCommandResult(suggestCmd, expMsg, model);

        suggestCmd = FindCommand.COMMAND_WORD;
        expMsg = FindCommand.MAIN_USAGE;
        assertSuggestionCommandResult(suggestCmd, expMsg, model);

        suggestCmd = ListCommand.COMMAND_WORD;
        expMsg = ListCommand.MAIN_USAGE;
        assertSuggestionCommandResult(suggestCmd, expMsg, model);

        suggestCmd = SelectCommand.COMMAND_WORD;
        expMsg = SelectCommand.MAIN_USAGE;
        assertSuggestionCommandResult(suggestCmd, expMsg, model);

        //Entering patient 1
        String command = GoToCommand.COMMAND_WORD + " 1";
        assertCommandResultSuccess(command, model);

        //Goto Mode
        suggestCmd = AddCommand.COMMAND_WORD;
        expMsg = AddCommand.GOTO_USAGE;
        assertSuggestionCommandGotoResult(suggestCmd, expMsg, model);

        suggestCmd = ClearCommand.COMMAND_WORD;
        expMsg = ClearCommand.GOTO_USAGE;
        assertSuggestionCommandGotoResult(suggestCmd, expMsg, model);

        suggestCmd = CopyCommand.COMMAND_WORD;
        expMsg = CopyCommand.GOTO_USAGE;
        assertSuggestionCommandGotoResult(suggestCmd, expMsg, model);

        suggestCmd = DeleteCommand.COMMAND_WORD;
        expMsg = DeleteCommand.GOTO_USAGE;
        assertSuggestionCommandGotoResult(suggestCmd, expMsg, model);

        suggestCmd = EditCommand.COMMAND_WORD;
        expMsg = EditCommand.GOTO_USAGE;
        assertSuggestionCommandGotoResult(suggestCmd, expMsg, model);

        suggestCmd = FindCommand.COMMAND_WORD;
        expMsg = FindCommand.GOTO_USAGE;
        assertSuggestionCommandGotoResult(suggestCmd, expMsg, model);

        suggestCmd = ListCommand.COMMAND_WORD;
        expMsg = ListCommand.GOTO_USAGE;
        assertSuggestionCommandGotoResult(suggestCmd, expMsg, model);

        suggestCmd = SelectCommand.COMMAND_WORD;
        expMsg = SelectCommand.GOTO_USAGE;
        assertSuggestionCommandGotoResult(suggestCmd, expMsg, model);

        command = BackCommand.COMMAND_WORD;
        executeCommand(command);
        alertRobotClick();
    }

    /**
     * For use for checking suggestion messages in patient mode
     */
    private void assertSuggestionCommandResult (String commmand, String expectedResultMessage, Model expModel) {
        executeCommand(commmand);
        assertApplicationDisplaysExpected("", expectedResultMessage, expModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }

    /**
     * For use for checking suggestion messages in goto mode
     */
    private void assertSuggestionCommandGotoResult (String commmand, String expectedResultMessage, Model expModel) {
        executeCommand(commmand);
        assertApplicationRecordDisplaysExpected("", expectedResultMessage, expModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }

    /**
     * For use with goto mode
     */
    private void assertCommandResultSuccess(String command, Model expectedModel) {
        String expectedResultMessage = String.format(GoToCommand.MESSAGE_EXPAND_PERSON_SUCCESS, 1);

        executeCommand(command);
        assertApplicationRecordDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }

    /**
     * To click on the generated AlertBox
     */
    private void alertRobotClick() {
        FxRobot clickBot = new FxRobot();
        clickBot.clickOn("Yes");
    }
}
