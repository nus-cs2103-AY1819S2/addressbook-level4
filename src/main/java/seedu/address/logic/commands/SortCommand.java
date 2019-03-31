package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Sorts data in accordance to the given parameter
 */
public abstract class SortCommand<T> extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + " : Sorts the displayed list of patients OR a patient's records according to the desired parameter and "
        + "order.\n"
        + "Parameters: PARAMETER ORDER\n"
        + "Example 1: " + COMMAND_WORD + " name asce\n"
        + "Example 2: " + COMMAND_WORD + " name desc\n";

    public static final String MESSAGE_SUCCESS = "List has been sorted by %1$s!";

    protected final Comparator<T> attrCompare;
    protected final String paraType;
    protected final boolean isReverse;

    public SortCommand(Comparator<T> chosenCompare, String paraType, boolean sortOrder) {
        requireNonNull(chosenCompare);
        this.attrCompare = chosenCompare;
        this.paraType = paraType;
        this.isReverse = sortOrder;
    }

    /**
     * Sets default sortOrder to ascending
     */
    public SortCommand(Comparator<T> chosenCompare, String paraType) {
        this(chosenCompare, paraType, false);
    }

    @Override
    public abstract CommandResult execute(Model model, CommandHistory history) throws CommandException;

    @Override
    public boolean equals(Object other) {
        return other == this
            || (other instanceof SortCommand
            && attrCompare.equals(((SortCommand) other).attrCompare)
            && paraType.equals(((SortCommand) other).paraType)
            && isReverse == ((SortCommand) other).isReverse);
    }
}
