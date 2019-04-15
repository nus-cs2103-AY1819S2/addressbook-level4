package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.Mode;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.table.TableNumber;
import seedu.address.model.table.TableStatus;

/**
 * Adds a table to Tables.
 */
public class AddTableCommand extends Command {

    public static final String COMMAND_WORD = "addTable";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds table(s) to the RestOrRant application."
            + "Parameters: NUMBER_OF_SEATS [NUMBER_OF_SEATS]...\n" + "Example: " + COMMAND_WORD + " 2 1 4";

    public static final String MESSAGE_SUCCESS = "New table added:";

    public static final String MESSAGE_TOO_MANY_TABLES =
            "RestOrRant can only accommodate 400 tables!\n Currently there are %1$s tables.";

    public static final String MESSAGE_TABLE_ADDED = "\nTable %1$s: %2$s";

    private final List<TableStatus> tableStatusList;

    /**
     * Creates an AddTableCommand to add table specified by the number of seats.
     */
    public AddTableCommand(List<TableStatus> tableStatuses) {
        requireNonNull(tableStatuses);
        tableStatusList = tableStatuses;
    }

    @Override
    public CommandResult execute(Mode mode, Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        TableNumber addedTableNumber;
        StringBuilder sbFinalOutput = new StringBuilder(MESSAGE_SUCCESS);

        if (tableStatusList.size() + model.getRestOrRant().getTables().getTableList().size() > 400) {
            throw new CommandException(String.format(MESSAGE_TOO_MANY_TABLES,
                    String.valueOf(model.getRestOrRant().getTables().getTableList().size())));
        }

        for (TableStatus tableStatus : tableStatusList) {
            addedTableNumber = model.addTable(tableStatus);
            requireNonNull(addedTableNumber);
            sbFinalOutput.append(String.format(MESSAGE_TABLE_ADDED, addedTableNumber.toString(),
                    tableStatus.toString()));
        }

        return new CommandResult(sbFinalOutput.toString());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddTableCommand
                && tableStatusList.equals(((AddTableCommand) other).tableStatusList));
    }
}
