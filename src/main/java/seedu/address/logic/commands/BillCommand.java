package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TABLENUMBER;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.Mode;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;


/**
 * Retrieves the Bill for a Table.
 */
public class BillCommand extends Command {

    public static final String COMMAND_WORD = "bill";
    public static final String COMMAND_ALIAS = "b";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Retrieves the Bill for a Table. "
            + "Parameters: "
            + PREFIX_TABLENUMBER + "TABLE NUMBER "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TABLENUMBER + "1 ";

    public static final String MESSAGE_SUCCESS = "Bill Calculated: %1$s";
    public static final String MESSAGE_TABLE_DOES_NOT_EXIST = "This table does not exist.";

    private final Table toBill;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public BillCommand(Table table) {
        requireNonNull(table);
        toBill = table;
    }

    @Override
    public CommandResult execute(Mode mode, Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (!model.hasTable(toBill)) {
            throw new CommandException(MESSAGE_TABLE_DOES_NOT_EXIST);
        }

        model.calculateBill(toBill);
        model.updateRestOrRant();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toBill));
    }
    
}
