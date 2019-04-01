package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.SizeTenMapGrid.initialisePlayerSizeTen;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.battle.BattleManager;
import seedu.address.logic.battle.state.BattleState;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.player.Player;
import seedu.address.testutil.InterceptedEnemy;

public class EndTurnCommandTest {
    private Player player;
    private InterceptedEnemy enemy;
    private Model model;

    @Before
    public void prepareModel() {
        player = new Player();
        enemy = new InterceptedEnemy();
        initialisePlayerSizeTen(player);
        initialisePlayerSizeTen(enemy);
        enemy.prepEnemy();
        model = new ModelManager(new BattleManager(player, enemy));
    }

    @Test
    public void execute_userAttackAfterState_callsEnemyAndSetsState() throws CommandException {
        model.setBattleState(BattleState.PLAYER_ATTACK_AFTER);
        CommandResult res = new EndTurnCommand().execute(model, new CommandHistory());
        assertTrue(enemy.isEnemyShootAtCalled());
        assertTrue(enemy.isReceiveStatusCalled());
        assertEquals(model.getBattleState(), BattleState.PLAYER_ATTACK);
    }

    @Test
    public void execute_incorrectState_fail() throws CommandException {
    }
}
