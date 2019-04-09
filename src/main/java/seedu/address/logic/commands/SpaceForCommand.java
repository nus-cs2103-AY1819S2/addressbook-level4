package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.Mode;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.table.Table;
import seedu.address.model.table.TableStatus;

/**
 * Looks for best fit available table for customers
 */
public class SpaceForCommand extends Command {

    public static final String COMMAND_WORD = "spaceFor";
    public static final String MESSAGE_NO_AVAILABLE_TABLE =
            "There are no available tables that can accommodate %1$s customers!";
    public static final String MESSAGE_SUCCESS = "%1$s customers have been allocated to table %2$s!";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Looks for smallest available table to accommodate number of customers."
            + "Parameters: NUMBER_OF_CUSTOMERS\n" + "Example: " + COMMAND_WORD + " 2";
    public static final String MESSAGE_CONSTRAINT = "Number of customers has to be a whole number greater than 0.";

    private int size;

    /**
     * Creates a SpaceForCommand to look for a table based on specified size required.
     */
    public SpaceForCommand(int size) {
        this.size = size;
    }

    @Override
    public CommandResult execute(Mode mode, Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        Table bestFitTable = null;
        int smallestSize = Integer.MAX_VALUE;

        for (Table table : model.getRestOrRant().getTables().getTableList()) {
            int numberOfSeats = Integer.parseInt(table.getTableStatus().getNumberOfSeats());
            if (!table.isOccupied() && numberOfSeats >= size && numberOfSeats < smallestSize) {
                bestFitTable = table;
                smallestSize = numberOfSeats;
            }
            if (smallestSize == size) {
                break;
            }
        }

        if (bestFitTable == null) {
            throw new CommandException(String.format(MESSAGE_NO_AVAILABLE_TABLE, String.valueOf(size)));
        }
        model.setTable(bestFitTable, new Table(bestFitTable.getTableNumber(),
                new TableStatus(String.valueOf(size) + "/" + bestFitTable.getTableStatus().getNumberOfSeats())));

        return new CommandResult(String.format(MESSAGE_SUCCESS, String.valueOf(size), bestFitTable.getTableNumber()));
    }

    @Override
    public boolean equals(Object other) {
        return this == other
                || (other instanceof SpaceForCommand
                && this.size == ((SpaceForCommand) other).size);
    }
}
