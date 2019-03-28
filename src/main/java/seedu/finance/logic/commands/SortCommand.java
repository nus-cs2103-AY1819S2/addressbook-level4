package seedu.finance.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.finance.logic.parser.CliSyntax.FLAG_AMOUNT;
import static seedu.finance.logic.parser.CliSyntax.FLAG_CATEGORY;
import static seedu.finance.logic.parser.CliSyntax.FLAG_DATE;
import static seedu.finance.logic.parser.CliSyntax.FLAG_NAME;

import java.util.Comparator;

import seedu.finance.logic.CommandHistory;
import seedu.finance.logic.commands.exceptions.CommandException;
import seedu.finance.model.Model;
import seedu.finance.model.record.Record;


/**
 * Sorts the records in the finance tracker
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts the records in the list "
            + "either by name, amount, date or category.\n"
            + "Parameters: FLAG \n"
            + "Possible flags: "
            +  FLAG_NAME + ", "
            +  FLAG_AMOUNT + ", "
            +  FLAG_DATE  + ", "
            +  FLAG_CATEGORY + "\n"
            + "Example: " + COMMAND_WORD + " " + FLAG_NAME;

    public static final String MESSAGE_SUCCESS = "List is sorted.";
    public static final String MESSAGE_NOT_SORTED = "One flag must be provided.";

    private final Comparator<Record> comparator;

    /**
     * Creates a SortCommand to sort the list according to the comparator supplied.
     */
    public SortCommand(Comparator<Record> comparator) {
        requireNonNull(comparator);
        this.comparator = comparator;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        model.sortFilteredRecordList(comparator);

        model.updateFilteredRecordList(Model.PREDICATE_SHOW_ALL_RECORD);
        model.commitFinanceTracker();
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SortCommand // instanceof handles null
                && comparator.equals(((SortCommand) other).comparator));
    }

}
