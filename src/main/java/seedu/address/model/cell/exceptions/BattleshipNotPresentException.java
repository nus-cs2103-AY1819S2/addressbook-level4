package seedu.address.model.cell.exceptions;

/**
 * Signals that the cell has no battleship.
 */
public class BattleshipNotPresentException extends RuntimeException {

    public BattleshipNotPresentException() {
        super("No batttleship is present in this cell");
    }
}
