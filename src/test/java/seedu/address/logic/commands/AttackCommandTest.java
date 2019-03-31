package seedu.address.logic.commands;

import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.SizeTenMapGrid.initialisePlayerSizeTen;

import java.util.Collections;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.battle.BattleManager;
import seedu.address.logic.battle.state.BattleState;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.battleship.Battleship;
import seedu.address.model.battleship.DestroyerBattleship;
import seedu.address.model.battleship.Orientation;
import seedu.address.model.cell.Coordinates;
import seedu.address.model.player.Enemy;
import seedu.address.model.player.Player;
import seedu.address.testutil.TypicalIndexes;

public class AttackCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Player player;
    private Enemy enemy;
    private Model model;

    @Before
    public void prepareModel() {
        player = new Player();
        enemy = new Enemy();
        initialisePlayerSizeTen(player);
        initialisePlayerSizeTen(enemy);
        model = new ModelManager(new BattleManager(player, enemy));
        model.setBattleState(BattleState.PLAYER_ATTACK);
    }

    @Test
    public void execute_duplicateAttack_fail() throws CommandException {
        AttackCommand cmd = new AttackCommand(TypicalIndexes.COORDINATES_A1);
        cmd.execute(model, new CommandHistory());
        thrown.expect(CommandException.class);
        thrown.expectMessage(AttackCommand.MESSAGE_DUPLICATE);
        cmd.execute(model, new CommandHistory());
    }

    @Test
    public void execute_attackHits_hitsAndDecreasesShipHealthAndUpdatesStats() throws CommandException {
        Battleship ship = new DestroyerBattleship(Collections.emptySet());
        int initialLife = ship.getLife();
        enemy.getMapGrid().putShip(ship, TypicalIndexes.COORDINATES_A1, new Orientation("v"));

        int initialMissCount = model.getPlayerStats().getMissCount();
        int initialHitCount = model.getPlayerStats().getHitCount();

        AttackCommand cmd = new AttackCommand(TypicalIndexes.COORDINATES_A1);
        CommandResult res = cmd.execute(model, new CommandHistory());
        assertTrue(res.getFeedbackToUser().contains("hit"));
        assertTrue(ship.getLife() < initialLife);
        assertTrue(model.getPlayerStats().getMissCount() == initialMissCount);
        assertTrue(model.getPlayerStats().getHitCount() > initialHitCount);
        assertTrue(player.getTargetHistory().contains(TypicalIndexes.COORDINATES_A1));
    }

    @Test
    public void execute_missAttack_missesAndUpdatesStats() throws CommandException {
        int initialMissCount = model.getPlayerStats().getMissCount();
        int initialHitCount = model.getPlayerStats().getHitCount();
        AttackCommand cmd = new AttackCommand(TypicalIndexes.COORDINATES_A1);
        CommandResult res = cmd.execute(model, new CommandHistory());
        assertTrue(res.getFeedbackToUser().contains("miss"));
        assertTrue(model.getPlayerStats().getMissCount() > initialMissCount);
        assertTrue(model.getPlayerStats().getHitCount() == initialHitCount);
        assertTrue(player.getTargetHistory().contains(TypicalIndexes.COORDINATES_A1));
    }

    @Test
    public void execute_attackDestroysShip_destroysAndUpdatesStats() throws CommandException {
        Battleship ship = new DestroyerBattleship(Collections.emptySet());
        int initialLife = ship.getLife();
        enemy.getMapGrid().putShip(ship, TypicalIndexes.COORDINATES_A1, new Orientation("v"));

        int initialDestroyedCount = model.getPlayerStats().getEnemyShipsDestroyed();

        // attack all the cells of the ship except one
        int shipLength = ship.getLength();
        for (int i = 1; i < shipLength; i++) {
            new AttackCommand(new Coordinates(i, 0)).execute(model, new CommandHistory());
        }

        AttackCommand cmd = new AttackCommand(TypicalIndexes.COORDINATES_A1);
        CommandResult res = cmd.execute(model, new CommandHistory());
        assertTrue(res.getFeedbackToUser().contains("destroy"));
        assertTrue(ship.isDestroyed());
        assertTrue(model.getPlayerStats().enemyShipsDestroyed() > initialDestroyedCount);
        assertTrue(player.getTargetHistory().contains(TypicalIndexes.COORDINATES_A1));
    }

    @Test
    public void execute_outOfBounds_fail() throws CommandException {
        Coordinates invalid = TypicalIndexes.INVALID_COORDINATE;

        AttackCommand cmd = new AttackCommand(invalid);
        CommandResult res = cmd.execute(model, new CommandHistory());
        assertTrue(res.getFeedbackToUser().contains("failed"));
    }
}
