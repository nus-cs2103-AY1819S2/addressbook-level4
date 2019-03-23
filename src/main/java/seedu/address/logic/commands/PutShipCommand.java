package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COORDINATES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORIENTATION;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.BoundaryValueChecker;
import seedu.address.model.MapGrid;
import seedu.address.model.Model;
import seedu.address.model.battleship.Battleship;
import seedu.address.model.battleship.Orientation;
import seedu.address.model.cell.Cell;
import seedu.address.model.cell.Coordinates;

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
            + "[" + PREFIX_COORDINATES + "COORDINATES]\n"
            + "[" + PREFIX_ORIENTATION + "ORIENTATION]\n"
            + "Example: " + COMMAND_WORD
            + PREFIX_NAME + "Destroyer "
            + PREFIX_COORDINATES + "c1"
            + PREFIX_ORIENTATION + "vertical";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Put ship in cell: %1$s";
    public static final String MESSAGE_BATTLESHIP_PRESENT = "There is already a ship on the coordinate.";
    public static final String MESSAGE_BATTLESHIP_PRESENT_BODY_VERTICAL =
            "There is already a ship along the vertical coordinates";
    public static final String MESSAGE_BATTLESHIP_PRESENT_BODY_HORIZONTAL =
            "There is already a ship along the horizontal coordinates";
    public static final String MESSAGE_OUT_OF_BOUNDS = "Out of bounds";

    private final Coordinates coordinates;
    private final Battleship battleship;
    private final Orientation orientation;

    /**
     * @param coordinates of the cell in the filtered cell list to edit
     * @param battleship battleship to place in the cell
     */
    public PutShipCommand(Coordinates coordinates, Battleship battleship, Orientation orientation) {
        requireNonNull(coordinates);
        requireNonNull(battleship);
        requireNonNull(orientation);

        this.coordinates = coordinates;
        this.battleship = battleship;
        this.orientation = orientation;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        MapGrid mapGrid = model.getMapGrid();

        BoundaryValueChecker boundaryValueChecker = new BoundaryValueChecker(mapGrid, battleship,
                coordinates, orientation);

        boundaryValueChecker.performChecks();

        if (this.orientation.isHorizontal()) {
            try {
                checkEnoughBattleships(model, battleship, 1);
                putAlongHorizontal(model, coordinates, battleship);
                model.deployBattleship(battleship, coordinates, orientation);
            } catch (ArrayIndexOutOfBoundsException aiobe) {
                throw new CommandException(MESSAGE_OUT_OF_BOUNDS);
            } catch (Exception e) {
                throw new CommandException(e.getMessage());
            }
        } else if (this.orientation.isVertical()) {
            try {
                checkEnoughBattleships(model, battleship, 1);
                putAlongVertical(model, coordinates, battleship);
                model.deployBattleship(battleship, coordinates, orientation);
            } catch (ArrayIndexOutOfBoundsException aiobe) {
                throw new CommandException(MESSAGE_OUT_OF_BOUNDS);
            } catch (Exception e) {
                throw new CommandException(e.getMessage());
            }
        } else {
            throw new CommandException(MESSAGE_USAGE);
        }

        model.updateUi();
        Cell cellToEdit = model.getHumanMapGrid().getCell(coordinates);

        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, cellToEdit));
    }

    /**
     * Checks if there are enough battleships to use. Throws exception if otherwise.
     */
    public static void checkEnoughBattleships(Model model, Battleship battleship, int numBattleship)
            throws Exception {
        if (!model.isEnoughBattleships(battleship, numBattleship)) {
            throw new Exception("Not enough " + battleship.getName() + "s.");
        }
    }

    /**
     * Puts the *same* battleship object along vertical length.
     * Pre-conditions: there are NO existing battleships along the vertical length, else will throw
     * and exception.
     */
    public static void putAlongVertical(Model model, Coordinates coordinates, Battleship battleship) {
        Index rowIndex = coordinates.getRowIndex();
        Index colIndex = coordinates.getColIndex();

        int length = battleship.getLength();

        for (int i = 0; i < length; i++) {
            Cell cellToInspect = model.getHumanMapGrid().getCell(rowIndex.getZeroBased() + i,
                    colIndex.getZeroBased());

            cellToInspect.putShip(battleship);
        }
    }

    /**
     * Puts the *same* battleship object along horizontal length.
     * Pre-conditions: there are NO existing battleships along the horizontal length, else will throw
     * and exception.
     */
    public static void putAlongHorizontal(Model model, Coordinates coordinates, Battleship battleship) {
        Index rowIndex = coordinates.getRowIndex();
        Index colIndex = coordinates.getColIndex();

        int length = battleship.getLength();

        for (int i = 0; i < length; i++) {
            Cell cellToInspect = model.getHumanMapGrid().getCell(rowIndex.getZeroBased(),
                    colIndex.getZeroBased() + i);

            cellToInspect.putShip(battleship);
        }
    }

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
                && coordinates.equals(e.coordinates);
    }
}

