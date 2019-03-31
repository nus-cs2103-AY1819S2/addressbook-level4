package seedu.address.logic.commands;

import java.util.Comparator;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.record.Record;

/**
 * Sorts data in accordance to the given parameter
 */
public class SortRecordCommand extends SortCommand<Record> {

    public SortRecordCommand(Comparator<Record> chosenCompare, String paraType, boolean sortOrder) {
        super(chosenCompare, paraType, sortOrder);
    }

    /**
     * Sets default sortOrder to ascending
     */
    public SortRecordCommand(Comparator<Record> chosenCompare, String paraType) {
        this(chosenCompare, paraType, false);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        model.sortRecordsBook(this.attrCompare, this.isReverse);
        model.updateFilteredRecordList(Model.PREDICATE_SHOW_ALL_RECORDS);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, paraType));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
            || (other instanceof SortRecordCommand
            && attrCompare.equals(((SortRecordCommand) other).attrCompare)
            && paraType.equals(((SortRecordCommand) other).paraType)
            && isReverse == ((SortRecordCommand) other).isReverse);
    }
}

