package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COORDINATES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORIENTATION;

import java.util.EnumSet;

import seedu.address.battle.state.BattleState;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.BoundaryValueChecker;
import seedu.address.model.MapGrid;
import seedu.address.model.Model;
import seedu.address.model.battleship.Battleship;
import seedu.address.model.battleship.Orientation;
import seedu.address.model.cell.Coordinates;
import seedu.address.model.exceptions.BoundaryValueException;
/**
 * Puts ship in an existing cell on the map.
 */
public class PutShipCommand extends Command {

    public static final String COMMAND_WORD = "put";
    public static final String COMMAND_ALIAS = "p";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Puts ship in cell that is identified "
            + "by the row number and orientation (vertical/horizontal) provided by the user. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_COORDINATES + "COORDINATES] "
            + "[" + PREFIX_ORIENTATION + "ORIENTATION]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Destroyer "
            + PREFIX_COORDINATES + "c1 "
            + PREFIX_ORIENTATION + "vertical";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Put ship in cell: %1$s";
    public static final String MESSAGE_OUT_OF_BOUNDS = "Out of bounds";

    private final Coordinates coordinates;
    private final Battleship battleship;
    private final Orientation orientation;

    /**
     * Default constructor method for ListTagsCommand.
     *
     * @param coordinates of the cell in the map grid.
     * @param battleship battleship to place in the cell in the map grid.
     * @param orientation orientation of the battleship in the map grid.
     */
    public PutShipCommand(Coordinates coordinates, Battleship battleship, Orientation orientation) {
        requireNonNull(coordinates);
        requireNonNull(battleship);
        requireNonNull(orientation);

        this.coordinates = coordinates;
        this.battleship = battleship;
        this.orientation = orientation;

        setPermissibleStates(EnumSet.of(
            BattleState.PLAYER_PUT_SHIP));
    }

    /**
     * Checks if a ship can be put on a specific cell on the map grid and sends
     * feedback to the user.
     *
     * @param model {@code Model} which the command should operate on.
     * @param history {@code CommandHistory} which the command should operate on.
     * @return {@code CommandResult} which contains information about the battleship,
     * coordinates and orientation that has been put on the map grid.
     * @throws CommandException
     */
    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        assert canExecuteIn(model.getBattleState());

        MapGrid mapGrid = model.getHumanMapGrid();
        BoundaryValueChecker boundaryValueChecker = new BoundaryValueChecker(
                mapGrid,
                battleship,
                coordinates,
                orientation);

        if (!model.isEnoughBattleships(battleship, 1)) {
            throw new CommandException("Not enough battleships.");
        } else {
            try {
                boundaryValueChecker.performChecks();
                mapGrid.putShip(battleship, coordinates, orientation);
                model.deployBattleship(battleship, coordinates, orientation);
            } catch (ArrayIndexOutOfBoundsException | NumberFormatException nfe) {
                throw new CommandException(MESSAGE_OUT_OF_BOUNDS);
            } catch (BoundaryValueException obe) {
                throw new CommandException(obe.getMessage());
            }
        }

        model.updateUi(); // update the map grid cells

        String battleshipStatus = String.format("%s at %s in %s orientation",
                battleship.getName(),
                coordinates,
                orientation);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(battleshipStatus)
                .append("\n\nNumber of aircraft carriers left: ")
                .append(model.getFleet().getNumAircraftCarrierLeft())
                .append("\nNumber of cruisers left: ")
                .append(model.getFleet().getNumCruiserLeft())
                .append("\nNumber of destroyers left: ")
                .append(model.getFleet().getNumDestroyerLeft());

        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, stringBuilder.toString()));
    }

    /**
     * Checks equality of two PutShipCommand objects by comparing the respective
     * {@code battleship}, {@code coordinates} and {@code orientation}. If the other
     * object is not a {@code PutShipCommand} object, then they are not equal.
     *
     * @param other any object.
     * @return boolean of whether the objects are equal.
     */
    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PutShipCommand)) {
            return false;
        }

        // state check
        PutShipCommand e = (PutShipCommand) other;
        return battleship.equals(e.battleship)
                && coordinates.equals(e.coordinates)
                && orientation.equals(e.orientation);
    }
}

