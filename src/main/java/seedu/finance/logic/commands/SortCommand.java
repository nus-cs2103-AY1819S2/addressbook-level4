package seedu.finance.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.finance.logic.parser.CliSyntax.COMMAND_FLAG_AMOUNT;
import static seedu.finance.logic.parser.CliSyntax.COMMAND_FLAG_CATEGORY;
import static seedu.finance.logic.parser.CliSyntax.COMMAND_FLAG_DATE;
import static seedu.finance.logic.parser.CliSyntax.COMMAND_FLAG_NAME;

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
            + "either by name, amount, date or category based on the selected flag.\n"
            + "Parameters: FLAG \n"
            + "Flags: \n"
            + COMMAND_FLAG_NAME + ": Sort records by name in lexicographical order\n"
            + COMMAND_FLAG_AMOUNT + ": Sort records by amount in descending order\n"
            + COMMAND_FLAG_DATE + ": Sort records by date from most recent to least recent\n"
            + COMMAND_FLAG_CATEGORY + ": Sort records by category in lexicographical order\n"
            + "Example: " + COMMAND_WORD + " " + COMMAND_FLAG_NAME;

    public static final String MESSAGE_SUCCESS = "List is sorted.";
    public static final String MESSAGE_NOT_SORTED = "Only one flag should be provided.";

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

    public Comparator<Record> getComparator() {
        return comparator;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SortCommand // instanceof handles null
                && comparator.equals(((SortCommand) other).getComparator()));
    }

}
