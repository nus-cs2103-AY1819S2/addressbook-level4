package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COORDINATES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORIENTATION;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
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
            + "[" + PREFIX_ORIENTATION + "ORIENTATION\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Destroyer "
            + PREFIX_COORDINATES + "c1"
            + PREFIX_ORIENTATION + "horizontal";

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

        if (!isHeadWithinBounds(model, coordinates)) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        } else if (this.orientation.isHorizontal() && !this.orientation.isVertical()) {
            if (!isBattleshipAbsent(model, coordinates)) {
                throw new CommandException(MESSAGE_BATTLESHIP_PRESENT);
            } else if (!isBodyWithinHorizontalBounds(model, coordinates, battleship)) {
                throw new CommandException(Messages.MESSAGE_BODY_LENGTH_TOO_LONG);
            } else if (!isHorizontalClear(model, coordinates, battleship)) {
                throw new CommandException(MESSAGE_BATTLESHIP_PRESENT_BODY_HORIZONTAL);
            } else {
                try {
                    checkEnoughBattleships(model, battleship, 1);
                    putAlongHorizontal(model, coordinates, battleship);
                    model.deployBattleship(battleship, coordinates, orientation);
                } catch (ArrayIndexOutOfBoundsException aiobe) {
                    throw new CommandException(MESSAGE_OUT_OF_BOUNDS);
                } catch (Exception e) {
                    throw new CommandException(e.getMessage());
                }
            }
        } else if (this.orientation.isVertical() && !this.orientation.isHorizontal()) {
            if (!isBattleshipAbsent(model, coordinates)) {
                throw new CommandException(MESSAGE_BATTLESHIP_PRESENT);
            } else if (!isBodyWithinVerticalBounds(model, coordinates, battleship)) {
                throw new CommandException(Messages.MESSAGE_BODY_LENGTH_TOO_LONG);
            } else if (!isVerticalClear(model, coordinates, battleship)) {
                throw new CommandException(MESSAGE_BATTLESHIP_PRESENT_BODY_VERTICAL);
            } else {
                try {
                    checkEnoughBattleships(model, battleship, 1);
                    putAlongVertical(model, coordinates, battleship);
                    model.deployBattleship(battleship, coordinates, orientation);
                } catch (ArrayIndexOutOfBoundsException aiobe) {
                    throw new CommandException(MESSAGE_OUT_OF_BOUNDS);
                } catch (Exception e) {
                    throw new CommandException(e.getMessage());
                }
            }
        } else {
            throw new CommandException(MESSAGE_USAGE);
        }

        model.updateUi();
        Cell cellToEdit = model.getHumanMapGrid().getCell(coordinates);

        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, cellToEdit));
    }

    /**
     * Checks if given coordinates falls within the MapGrid.
     * @return true or false whether the coordinates fall within the MapGrid
     */
    public static boolean isHeadWithinBounds(Model model, Coordinates coordinates) {
        Index rowIndex = coordinates.getRowIndex();
        Index colIndex = coordinates.getColIndex();

        return (rowIndex.getZeroBased() <= model.getMapSize())
                && colIndex.getZeroBased() <= model.getMapSize();
    }

    /**
     * Checks if the body is within horizontal bounds of the map grid.
     * @return boolean of whether the body of battleship is within bounds of map grid.
     */
    public static boolean isBodyWithinHorizontalBounds(Model model,
                                                       Coordinates coordinates, Battleship battleship) {
        Index colIndex = coordinates.getColIndex();

        int length = battleship.getLength();

        return colIndex.getZeroBased() + length <= model.getMapSize();
    }

    /**
     * Checks if the body is within vertical bounds of the map grid.
     * @return boolean of whether body of battleship is within bounds of map grid.
     */
    public static boolean isBodyWithinVerticalBounds(Model model,
                                                     Coordinates coordinates, Battleship battleship) {
        Index rowIndex = coordinates.getRowIndex();

        int length = battleship.getLength();

        return rowIndex.getZeroBased() + length <= model.getMapSize();
    }

    /**
     * Checks if the battleship is absent.
     * @return boolean of whether battleship is absent or present.
     */
    public static boolean isBattleshipAbsent(Model model, Coordinates coordinates) {
        Index rowIndex = coordinates.getRowIndex();
        Index colIndex = coordinates.getColIndex();

        Cell cellToInspect = model.getHumanMapGrid().getCell(rowIndex.getZeroBased(), colIndex.getZeroBased());

        return !cellToInspect.hasBattleShip();
    }

    /**
     * Checks if vertical length is clear, i.e., there are no other battleship objects.
     * @return boolean of whether vertical length is clear.
     */
    public static boolean isVerticalClear(Model model, Coordinates coordinates, Battleship battleship) {
        Index rowIndex = coordinates.getRowIndex();
        Index colIndex = coordinates.getColIndex();

        int length = battleship.getLength();

        for (int i = 1; i < length; i++) {
            Cell cellToInspect = model.getHumanMapGrid().getCell(rowIndex.getZeroBased() + i,
                    colIndex.getZeroBased());

            if (cellToInspect.hasBattleShip()) {
                return false;
            }
        }

        return true;
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
     * Checks if horizontal length is clear, i.e., there are no other battleship objects.
     * @return boolean of whether horizontal length is clear.
     */
    public static boolean isHorizontalClear(Model model, Coordinates coordinates, Battleship battleship) {
        Index rowIndex = coordinates.getRowIndex();
        Index colIndex = coordinates.getColIndex();

        int length = battleship.getLength();

        for (int i = 1; i < length; i++) {
            Cell cellToInspect = model.getHumanMapGrid().getCell(rowIndex.getZeroBased(),
                    colIndex.getZeroBased() + i);

            if (cellToInspect.hasBattleShip()) {
                return false;
            }
        }

        return true;
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

