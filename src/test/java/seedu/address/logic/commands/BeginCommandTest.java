package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.testutil.SizeTenMapGrid.initialisePlayerSizeTen;
import static seedu.address.testutil.SizeTenMapGrid.setUpSingleShip;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.battle.BattleManager;
import seedu.address.battle.state.BattleState;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.player.Player;
import seedu.address.testutil.InterceptedEnemy;

public class BeginCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Player player;
    private InterceptedEnemy enemy;
    private Model model;

    @Before
    public void setUp() {
        player = new Player();
        enemy = new InterceptedEnemy();
        initialisePlayerSizeTen(player);
        initialisePlayerSizeTen(enemy);
        model = new ModelManager(new BattleManager(player, enemy));
        model.setBattleState(BattleState.PLAYER_PUT_SHIP);
    }

    @Test
    public void execute_oneShipPlaced_callsEnemyPrepAndSetsState() throws CommandException {
        setUpSingleShip(player);
        CommandResult res = new BeginCommand().execute(model, new CommandHistory());
        assertTrue(enemy.isPrepCalled());
        assertEquals(model.getBattleState(), BattleState.PLAYER_ATTACK);
        assertEquals(res.getFeedbackToUser(), BeginCommand.MESSAGE_BEGIN_SUCCESS);
    }

    @Test
    public void execute_noShipsPlaced_throwCommandException() {
        try {
            BeginCommand cmd = new BeginCommand();
            cmd.execute(model, new CommandHistory());
            fail();
        } catch (CommandException ex) {
            assertEquals(ex.getMessage(), BeginCommand.MESSAGE_NO_SHIPS);
            assertEquals(model.getBattleState(), BattleState.PLAYER_PUT_SHIP);
        }
    }

    @Test
    public void execute_invalidState_throwAssertionError() throws CommandException {
        thrown.expect(AssertionError.class);
        BeginCommand cmd = new BeginCommand();
        model.setBattleState(BattleState.PLAYER_ATTACK);
        cmd.execute(model, new CommandHistory());
    }
}
