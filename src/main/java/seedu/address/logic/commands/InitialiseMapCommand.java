package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.LogicManager;
import seedu.address.battle.state.BattleState;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.cell.Cell;
import seedu.address.model.cell.Coordinates;


/**
 * Initialise map to size input by user.
 */
public class InitialiseMapCommand extends Command {

    public static final String COMMAND_WORD = "init";
    public static final String COMMAND_ALIAS = "i";
    public static final String COMMAND_ALIAS1 = "initialise";

    public static final int MAXIMUM_MAP_SIZE = 10;
    public static final int MINIMUM_MAP_SIZE = 6;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Initialise the map to specified size.\n"
            + "Parameters: "
            + String.format("MAPSIZE (must be between %d to %d, inclusive)\n", MINIMUM_MAP_SIZE, MAXIMUM_MAP_SIZE)
            + "Example: " + COMMAND_WORD + " "
            + "8 ";

    public static final String MESSAGE_SUCCESS = "Map initialised to size: %d";
    public static final String MESSAGE_INVALID_MAP_SIZE = "Map size must be between %d to %d, inclusive.";

    private final int mapSize;
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    /**
     * Initialise map command
     */
    public InitialiseMapCommand(int size) {
        mapSize = size;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        assert canExecuteIn(model.getBattleState());

        if (mapSize > MAXIMUM_MAP_SIZE || mapSize < MINIMUM_MAP_SIZE) {
            throw new CommandException(String.format(MESSAGE_INVALID_MAP_SIZE, MINIMUM_MAP_SIZE, MAXIMUM_MAP_SIZE));
        }

        Cell[][] cellGrid = initialise2dArray(mapSize);

        model.getHumanMapGrid().initialise(cellGrid);
        model.getEnemyMapGrid().initialise(cellGrid);
        logger.info("HumanPlayer and EnemyPlayer maps initialised");

        model.getHumanPlayer().resetFleet(mapSize);
        model.getEnemyPlayer().resetFleet(mapSize);

        logger.info("HumanPlayer and EnemyPlayer fleet reset.");

        model.getHumanPlayer().resetTargetHistory();
        model.getEnemyPlayer().resetTargetHistory();
        logger.info("HumanPlayer and EnemyPlayer target history reset");

        model.setBattleState(BattleState.PLAYER_PUT_SHIP);
        logger.info("Battle state reset to PLAYER_PUT_SHIP");

        model.getPlayerStats().resetData();
        logger.info("Statistics Data Refreshed");

        logger.info("--------------------------------");


        return new CommandResult(String.format(MESSAGE_SUCCESS, mapSize));
    }

    /**
     * Create a 2D array of {@Cell} which will be used to initialise the map.
     */
    private Cell[][] initialise2dArray(int mapSize) {
        Cell[][] cellGrid = new Cell[mapSize][mapSize];

        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {

                cellGrid[i][j] = new Cell(new Coordinates(i, j));
            }
        }
        return cellGrid;
    }
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof InitialiseMapCommand); // instanceof handles nulls
    }
}
