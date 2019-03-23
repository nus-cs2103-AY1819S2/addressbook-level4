package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.EnumSet;
import java.util.Set;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.battle.state.BattleState;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command {
    /**
     * The states that this command can be executed in.
     */
    private Set<BattleState> permissibleStates = EnumSet.allOf(BattleState.class);
    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @param history {@code CommandHistory} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    public abstract CommandResult execute(Model model, CommandHistory history) throws CommandException;

    /**
     * Changes the permissible states of the command.
     */
    protected final void setPermissibleStates(Set<BattleState> permissibleStates) {
        requireNonNull(permissibleStates);
        this.permissibleStates = permissibleStates;
    }

    /**
     * Checks if this command can be executed in a certain state.
     * <br>
     * By default, commands can be run in all states. Those commands which are not
     * should set the permissible states using <code>setPermissibleStates()</code>
     * inside the constructor.
     * <br>
     * Before calling execute() using a <code>Model</code>, one must call this
     * method on the BattleState stored in it.
     */
    public final boolean canExecuteIn(BattleState battleState) {
        return permissibleStates.contains(battleState);
    }
}
