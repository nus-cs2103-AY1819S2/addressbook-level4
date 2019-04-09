package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.AppMode;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class ModeCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_modeNoArgs_success() {

        String modeText = model.getAddressBookMode().toString();
        CommandResult expectedCommandResult = new CommandResult(String.format("Mode %s", modeText));

        ModeCommand modeCommand = new ModeCommand(null);
        assertCommandSuccess(modeCommand, model, commandHistory, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_modeMemberToActivity_success() {

        CommandResult expectedCommandResult = new CommandResult(String.format("Mode changed to %s",
                "ACTIVITY"),
                false, false, true);

        ModeCommand modeCommand = new ModeCommand(AppMode.Modes.ACTIVITY);
        assertCommandSuccess(modeCommand, model, commandHistory, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_modeActivityToMember_success() {

        model.setAddressBookMode(AppMode.Modes.ACTIVITY);

        CommandResult expectedCommandResult = new CommandResult(String.format("Mode changed to %s",
                "MEMBER"),
                false, false, true);

        ModeCommand modeCommand = new ModeCommand(AppMode.Modes.MEMBER);
        assertCommandSuccess(modeCommand, model, commandHistory, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_modeMemberToMember_success() {

        String modeText = model.getAddressBookMode().toString();
        CommandResult expectedCommandResult = new CommandResult(String.format("Mode is already: %s", modeText));

        ModeCommand modeCommand = new ModeCommand(AppMode.Modes.MEMBER);
        assertCommandSuccess(modeCommand, model, commandHistory, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_modeActivityToActivity_success() {

        model.setAddressBookMode(AppMode.Modes.ACTIVITY);

        String modeText = model.getAddressBookMode().toString();
        CommandResult expectedCommandResult = new CommandResult(String.format("Mode is already: %s", modeText));

        ModeCommand modeCommand = new ModeCommand(AppMode.Modes.ACTIVITY);
        assertCommandSuccess(modeCommand, model, commandHistory, expectedCommandResult, expectedModel);
    }
}
