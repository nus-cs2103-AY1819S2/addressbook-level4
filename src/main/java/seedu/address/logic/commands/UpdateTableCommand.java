package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.Mode;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.table.Table;
import seedu.address.model.table.TableStatus;

/**
 * Updates the status of the table.
 */
public class UpdateTableCommand extends Command {

    public static final String COMMAND_WORD = "updateTable";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Updates the status of the table."
            + "Parameters: TABLE_NUMBER NEW_STATUS\n"
            + "Example: " + COMMAND_WORD + " 2 0";

    public static final String MESSAGE_SUCCESS = "Table status updated: \n%1$s";

    private final Table selectedTable;
    private final TableStatus tableStatus;
    /**
     * Creates an UpdateTableCommand to update the status of the table specified by the table number.
     */
    public UpdateTableCommand(Table selectedTable, TableStatus tableStatus) {
        this.selectedTable = selectedTable;
        this.tableStatus = tableStatus;
    }

    @Override
    public CommandResult execute(Mode mode, Model model, CommandHistory history) throws CommandException {
        return null;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof UpdateTableCommand
                && selectedTable.equals(((UpdateTableCommand) other).selectedTable)
                && tableStatus.equals(((UpdateTableCommand) other).tableStatus));
    }
}
