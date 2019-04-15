package seedu.finance.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.finance.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.finance.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.finance.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.finance.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.finance.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.finance.model.Model.PREDICATE_SHOW_ALL_RECORD;

import java.util.List;
import java.util.Optional;

import seedu.finance.commons.core.Messages;
import seedu.finance.commons.core.index.Index;
import seedu.finance.commons.util.CollectionUtil;
import seedu.finance.logic.CommandHistory;
import seedu.finance.logic.commands.exceptions.CommandException;
import seedu.finance.model.Model;
import seedu.finance.model.category.Category;
import seedu.finance.model.record.Amount;
import seedu.finance.model.record.Date;
import seedu.finance.model.record.Description;
import seedu.finance.model.record.Name;
import seedu.finance.model.record.Record;

/**
 * Edits the details of an existing record in the finance tracker.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";
    public static final String COMMAND_ALIAS = "e";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the record identified "
            + "by the index number used in the displayed record list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_AMOUNT + "AMOUNT] "
            + "[" + PREFIX_DATE + "DATE] "
            + "[" + PREFIX_CATEGORY + "CATEGORY] "
            + "[" + PREFIX_DESCRIPTION + "DESCRIPTION]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_AMOUNT + "1234 "
            + PREFIX_DATE + "12/02/2009";

    public static final String MESSAGE_EDIT_RECORD_SUCCESS = "Edited Record: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";

    private final Index index;
    private final EditRecordDescriptor editRecordDescriptor;

    /**
     * @param index of the record in the filtered record list to edit
     * @param editRecordDescriptor details to edit the record with
     */
    public EditCommand(Index index, EditRecordDescriptor editRecordDescriptor) {
        requireNonNull(index);
        requireNonNull(editRecordDescriptor);

        this.index = index;
        this.editRecordDescriptor = new EditRecordDescriptor(editRecordDescriptor);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Record> lastShownList = model.getFilteredRecordList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_RECORD_DISPLAYED_INDEX);
        }

        Record recordToEdit = lastShownList.get(index.getZeroBased());
        Record editedRecord = createEditedRecord(recordToEdit, editRecordDescriptor);

        model.setRecord(recordToEdit, editedRecord);
        model.updateFilteredRecordList(PREDICATE_SHOW_ALL_RECORD);
        model.commitFinanceTracker();
        return new CommandResult(String.format(MESSAGE_EDIT_RECORD_SUCCESS, editedRecord),
                true, false, false);
    }

    /**
     * Creates and returns a {@code Record} with the details of {@code RecordToEdit}
     * edited with {@code editRecordDescriptor}.
     */
    private static Record createEditedRecord(Record recordToEdit, EditRecordDescriptor editRecordDescriptor) {
        assert recordToEdit != null;

        Name updatedName = editRecordDescriptor.getName().orElse(recordToEdit.getName());
        Amount updatedAmount = editRecordDescriptor.getAmount().orElse(recordToEdit.getAmount());
        Date updatedDate = editRecordDescriptor.getDate().orElse(recordToEdit.getDate());
        Description updatedDescription = editRecordDescriptor.getDescription().orElse(recordToEdit.getDescription());
        Category updatedCategory = editRecordDescriptor.getCategory().orElse(recordToEdit.getCategory());

        return new Record(updatedName, updatedAmount, updatedDate, updatedDescription, updatedCategory);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        // state check
        EditCommand e = (EditCommand) other;
        return index.equals(e.index)
                && editRecordDescriptor.equals(e.editRecordDescriptor);
    }

    /**
     * Stores the details to edit the record with. Each non-empty field value will replace the
     * corresponding field value of the record.
     */
    public static class EditRecordDescriptor {
        private Name name;
        private Amount amount;
        private Date date;
        private Category category;
        private Description description;

        public EditRecordDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code categories} is used internally.
         */
        public EditRecordDescriptor(EditRecordDescriptor toCopy) {
            setName(toCopy.name);
            setAmount(toCopy.amount);
            setDate(toCopy.date);
            setCategory(toCopy.category);
            setDescription(toCopy.description);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, amount, date, category, description);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setAmount(Amount amount) {
            this.amount = amount;
        }

        public Optional<Amount> getAmount() {
            return Optional.ofNullable(amount);
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public Optional<Date> getDate() {
            return Optional.ofNullable(date);
        }


        /**
         * Sets {@code categories} to this object's {@code categories}.
         * A defensive copy of {@code categories} is used internally.
         */
        public void setCategory(Category category) {
            this.category = (category != null) ? category : null;
        }

        /**
         * Returns an unmodifiable category set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code categories} is null.
         */
        public Optional<Category> getCategory() {
            return (category != null) ? Optional.of(category) : Optional.empty();
        }

        public void setDescription(Description description) {
            this.description = description;
        }

        public Optional<Description> getDescription() {
            return Optional.ofNullable(description);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditRecordDescriptor)) {
                return false;
            }

            // state check
            EditRecordDescriptor e = (EditRecordDescriptor) other;

            return getName().equals(e.getName())
                    && getAmount().equals(e.getAmount())
                    && getDate().equals(e.getDate())
                    && getCategory().equals(e.getCategory())
                    && getDescription().equals(e.getDescription());
        }
    }
}
