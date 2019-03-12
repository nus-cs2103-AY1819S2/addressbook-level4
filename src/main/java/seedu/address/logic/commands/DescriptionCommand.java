package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_RECORD;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.record.Description;
import seedu.address.model.record.Record;


/**
 * Changes the description of an existing expense entry in finance log.
 */
public class DescriptionCommand extends Command {

    public static final String COMMAND_WORD = "description";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the description of expense entry identified "
            + "by the index number used in the last expense entry listing. "
            + "Existing remark will be overwritten by the input.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_DESCRIPTION + "[DESCRIPTION]\n"
            + "Example: " + COMMAND_WORD + " 2 "
            + PREFIX_DESCRIPTION + "Father's birthday present.";

    public static final String MESSAGE_ADD_DESCRIPTION_SUCCESS = "Added description to Record: %1$s";
    public static final String MESSAGE_REMOVE_DESCRIPTION_SUCCESS = "Removed description from Record: %1$s";

    private final Index index;
    private final Description description;

    /**
     * @param index Index of the expense entry in the filtered expense list to edit description
     * @param description description of the expense entry to be updated to
     */
    public DescriptionCommand(Index index, Description description) {
        requireAllNonNull(index, description);

        this.index = index;
        this.description = description;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        List<Record> lastShownList = model.getFilteredRecordList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_RECORD_DISPLAYED_INDEX);
        }

        Record recordToEdit = lastShownList.get(index.getZeroBased());
        Record editedRecord = new Record(recordToEdit.getName(), recordToEdit.getAmount(), recordToEdit.getDate(),
                this.description, recordToEdit.getCategories());

        model.setRecord(recordToEdit, editedRecord);
        model.updateFilteredRecordList(PREDICATE_SHOW_ALL_RECORD);
        model.commitAddressBook();

        return new CommandResult(makeSuccessMessage(editedRecord));
    }

    /**
     * Makes a command execution succcess message based on whether the descrption is added to or removed form
     * {@recordToEdit}.
     * @param recordToEdit
     */
    private String makeSuccessMessage(Record recordToEdit) {

        String message = !description.value.isEmpty() ? MESSAGE_ADD_DESCRIPTION_SUCCESS
                : MESSAGE_REMOVE_DESCRIPTION_SUCCESS;
        return String.format(message, recordToEdit);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DescriptionCommand)) {
            return false;
        }

        DescriptionCommand e = (DescriptionCommand) other;
        return index.equals(e.index) && description.equals(e.description);
    }
}
