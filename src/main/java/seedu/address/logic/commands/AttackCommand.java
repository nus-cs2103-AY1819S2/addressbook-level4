package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.EnumSet;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.battle.AttackResult;
import seedu.address.logic.battle.state.BattleState;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.cell.Coordinates;
import seedu.address.model.player.Player;

/**
 * Attacks a cell on the board.
 */
public class AttackCommand extends Command {

    public static final String COMMAND_WORD = "attack";
    public static final String COMMAND_ALIAS1 = "shoot";
    public static final String COMMAND_ALIAS2 = "hit";
    public static final String COMMAND_ALIAS3 = "fire";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Attacks the specified cell.\n"
        + "Parameters: "
        + "SQUARE (a letter followed by a positive integer)\n"
        + "Example: attack b5";

    private Coordinates coord;

    public AttackCommand(Coordinates coord) {
        requireNonNull(coord);
        this.coord = coord;
        setPermissibleStates(EnumSet.of(BattleState.PLAYER_ATTACK));
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        // assert canExecuteIn(model.getBattleState());
        Player human = model.getHumanPlayer();
        AttackResult res;

        // check if the coordinate is already hit, prevent duplicate attacks
        if (human.addToTargetHistory(coord)) {
            res = model.getBattle().humanPerformAttack(coord);
        } else {
            throw new CommandException("You have already attacked cell " + coord);
        }
        model.getPlayerStats().addResultToStats(res);

        model.updateUi();

        return new CommandResult(res.toString());
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof AttackCommand) {
            AttackCommand o = (AttackCommand) other;
            return (this == o) || (this.coord.equals(o.coord));
        } else {
            return false;
        }
    }
}
