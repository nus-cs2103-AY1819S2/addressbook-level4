package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Optional;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.Mode;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.table.Table;
import seedu.address.model.table.TableNumber;
import seedu.address.model.table.TableStatus;

/**
 * Edits the number of seats at a table.
 */
public class EditSeatsCommand extends Command {

    public static final String COMMAND_WORD = "editSeats";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Updates the number of seats of the table."
            + "Parameters: TABLE_NUMBER NEW_NUMBER_OF_SEATS\n" + "Example: " + COMMAND_WORD + " 2 5";
    public static final String MESSAGE_SUCCESS = "Table seats has been updated!\nTable %1$s: %2$s";
    public static final String INVALID_TABLE_NUMBER = "Table %1$s does not exist.";
    public static final String INVALID_TABLE_SEATS =
            "Number of seats has to be greater than current number of customers: %1$s";

    private TableNumber tableNumber;
    private String numberOfSeats;

    public EditSeatsCommand(TableNumber tableNumber, String numberOfSeats) {
        this.tableNumber = tableNumber;
        this.numberOfSeats = numberOfSeats;
    }

    @Override
    public CommandResult execute(Mode mode, Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        Optional<Table> oldTable = model.getRestOrRant().getTables().getTableFromNumber(tableNumber);
        if (!oldTable.isPresent()) {
            throw new CommandException(String.format(INVALID_TABLE_NUMBER, tableNumber));
        }
        Table editedTable;
        try {
            editedTable = new Table(tableNumber,
                    new TableStatus(oldTable.get().getTableStatus().getNumberOfTakenSeats() + "/" + numberOfSeats));
        } catch (IllegalArgumentException e) {
            throw new CommandException(e.getMessage());
        }
        if (editedTable.getTableStatus().equals(oldTable.get().getTableStatus())) {
            throw new CommandException(String.format(EditPaxCommand.MESSAGE_NO_CHANGE_IN_STATUS, tableNumber.toString(),
                    editedTable.getTableStatus()));
        }
        model.setTable(oldTable.get(), editedTable);
        return new CommandResult(String.format(MESSAGE_SUCCESS, tableNumber, editedTable.getTableStatus()));
    }

    @Override
    public String toString() {
        return "Edit Table " + tableNumber + " to have " + numberOfSeats + " number of seats.";
    }

    @Override
    public boolean equals(Object other) {
        return this == other
                || (other instanceof EditSeatsCommand
                && this.tableNumber.equals(((EditSeatsCommand) other).tableNumber)
                && this.numberOfSeats.equals(((EditSeatsCommand) other).numberOfSeats));
    }
}
