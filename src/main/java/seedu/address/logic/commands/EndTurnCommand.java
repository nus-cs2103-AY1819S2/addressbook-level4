package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.EnumSet;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.battle.AttackResult;
import seedu.address.logic.battle.BattleManager;
import seedu.address.logic.battle.state.BattleState;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Ends the player's turn.
 */
public class EndTurnCommand extends Command {
    public static final String COMMAND_WORD = "end";
    public static final String COMMAND_ALIAS1 = "endturn";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Ends the player's turn.\n";
    private static final Logger logger = LogsCenter.getLogger(BattleManager.class);

    public EndTurnCommand() {
        setPermissibleStates(EnumSet.of(BattleState.PLAYER_ATTACK, BattleState.PLAYER_ATTACK_AFTER));
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        // assert canExecuteIn(model.getBattleState());
        model.setBattleState(BattleState.ENEMY_ATTACK);
        AttackResult res = model.getBattle().takeComputerTurn();
        logger.info(String.format("+++++++ENDTURNCOMMAND SAYS: LAST HIT ON: "
                + " status: " + res.getStatus().toString()));
        model.getEnemyPlayer().receiveStatus(res.getStatus());
        model.updateUi();
        model.setBattleState(BattleState.PLAYER_ATTACK);
        return new CommandResult(res.toString() + "\nYour turn.");
    }
}
