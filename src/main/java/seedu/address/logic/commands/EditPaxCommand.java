package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.Optional;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.Mode;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.table.Table;
import seedu.address.model.table.TableNumber;
import seedu.address.model.table.TableStatus;

/**
 * Updates the status of the table.
 */
public class EditPaxCommand extends Command {

    public static final String COMMAND_WORD = "editPax";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Updates the number of pax of the table."
            + "Parameters: TABLE_NUMBER NEW_OCCUPANCY\n" + "Example: " + COMMAND_WORD + " 2 0";

    public static final String MESSAGE_SUCCESS = "Table status updated: \nTable %1$s: %2$s";

    public static final String MESSAGE_INVALID_TABLE_NUMBER = "Table %1$s does not exist";

    public static final String MESSAGE_NO_CHANGE_IN_STATUS = "Table status of Table %1$s is already %2$s";

    private final String[] newTableStatus;

    /**
     * Creates an EditPaxCommand to update the status of the table specified by the table number.
     */
    public EditPaxCommand(String[] newTableStatusInString) {
        this.newTableStatus = newTableStatusInString;
    }

    @Override
    public CommandResult execute(Mode mode, Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        TableNumber tableNumber = new TableNumber(newTableStatus[0]);
        Optional<Table> optionalTable = model.getRestOrRant().getTables().getTableFromNumber(tableNumber);
        if (!optionalTable.isPresent()) {
            throw new CommandException(String.format(MESSAGE_INVALID_TABLE_NUMBER, tableNumber));
        }
        String updatedTableStatusInString =
                newTableStatus[1] + "/" + optionalTable.get().getTableStatus().getNumberOfSeats();
        TableStatus updatedTableStatus;
        try {
            updatedTableStatus = new TableStatus(updatedTableStatusInString);
        } catch (IllegalArgumentException e) {
            throw new CommandException(String.format(TableStatus.MESSAGE_INVALID_NUMBER_OF_CUSTOMERS,
                    updatedTableStatusInString.split("/")[1]));
        }
        if (updatedTableStatus.equals(optionalTable.get().getTableStatus())) {
            throw new CommandException(String.format(MESSAGE_NO_CHANGE_IN_STATUS, optionalTable.get().getTableNumber(),
                    optionalTable.get().getTableStatus()));
        }
        Table updatedTable = new Table(tableNumber, updatedTableStatus);
        model.setTable(optionalTable.get(), updatedTable);
        if (Integer.parseInt(newTableStatus[1]) == 0) {
            model.clearOrderItemsFrom(tableNumber);
        }
        return new CommandResult(
                String.format(MESSAGE_SUCCESS, updatedTable.getTableNumber(), updatedTableStatusInString));
    }

    @Override
    public boolean equals(Object other) {
        return other == this || (other instanceof EditPaxCommand
                && Arrays.equals(newTableStatus, ((EditPaxCommand) other).newTableStatus));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String string : newTableStatus) {
            sb.append(string);
        }
        return sb.toString();
    }
}
