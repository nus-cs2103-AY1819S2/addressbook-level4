package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.Mode;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.table.Table;
import seedu.address.model.table.TableNumber;

public class RemoveTableCommand extends Command {

    public static final String COMMAND_WORD = "removeTable";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Remove table(s) to the RestOrRant application.\n"
            + "Restaurant must be fully unoccupied when this command is being used."
            + "Parameters: TABLE_NUMBER [TABLE_NUMBER]...\n" + "Example: " + COMMAND_WORD + " 2 3 4";

    public static final String MESSAGE_SUCCESS = "Tables removed:";

    public static final String MESSAGE_TABLE_REMOVED = "\nTable %1$s";

    public static final String MESSAGE_SUCCESS_END = "\nall other tables have been flushed forward.";

    public static final String INVALID_TABLE_NUMBER = "Table %1$s does not exist in the restaurant!";

    public static final String INVALID_RESTORRANT_STATE = "Restaurant is still occupied. Deletion of table not allowed";

    private final List<TableNumber> tableNumberList;
    private int nextTableNumber = 1;

    /**
     * Creates a RemoveTableCommand to remove the table specified by the table number.
     */
    public RemoveTableCommand(List<TableNumber> tableNumberList) {
        requireNonNull(tableNumberList);
        this.tableNumberList = tableNumberList;
    }

    @Override
    public CommandResult execute(Mode mode, Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        StringBuilder sbFinalOutput = new StringBuilder(MESSAGE_SUCCESS);
        List<Table> tables = new ArrayList<>();
        List<Table> currentTableList = model.getRestOrRant().getTables().getTableList();

        for (TableNumber tableNumber : tableNumberList) {
            if (Integer.parseInt(tableNumber.toString()) > currentTableList.size()) {
                throw new CommandException(String.format(INVALID_TABLE_NUMBER, tableNumber));
            }
            sbFinalOutput.append(String.format(MESSAGE_TABLE_REMOVED, tableNumber));
        }
        sbFinalOutput.append(MESSAGE_SUCCESS_END);

        for (Table table : currentTableList) {
            if (table.isOccupied()) {
                throw new CommandException(INVALID_RESTORRANT_STATE);
            }
            if (!tableNumberList.contains(table.getTableNumber())) {
                tables.add(new Table(new TableNumber(String.valueOf(nextTableNumber)), table.getTableStatus()));
                nextTableNumber++;
            }
        }
        model.setTables(tables);

        return new CommandResult(sbFinalOutput.toString());
    }
}
