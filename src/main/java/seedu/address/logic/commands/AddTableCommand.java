package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.Mode;
import seedu.address.model.Model;
import seedu.address.model.table.TableNumber;

/**
 * Adds a table to Tables.
 */
public class AddTableCommand extends Command {

    public static final String COMMAND_WORD = "addTable";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds table(s) to the RestOrRant application."
            + "Parameters: NUMBER_OF_SEATS [NUMBER_OF_SEATS]...\n"
            + "Example: " + COMMAND_WORD + " 2 1 4";

    public static final String MESSAGE_SUCCESS = "New table added:\n%1$s";
    public static final String MESSAGE_DUPLICATE_TABLE = "This table already exists in RestOrRant";
    public static final String MESSAGE_INVALID_TABLE_NUMBER = "The table number [%1$s] is invalid";

    private final List<TableNumber> tableNumberList;

    /**
     * Creates an AddTableCommand to add table specified by the number of seats.
     */
    public AddTableCommand(List<TableNumber> tableNumbers) {
        requireNonNull(tableNumbers);
        tableNumberList = tableNumbers;
    }

    @Override
    public CommandResult execute(Mode mode, Model model, CommandHistory history) {
        requireNonNull(model);

        return null;
    }
    
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddTableCommand
                && tableNumberList.equals(((AddTableCommand) other).tableNumberList));
    }
}
