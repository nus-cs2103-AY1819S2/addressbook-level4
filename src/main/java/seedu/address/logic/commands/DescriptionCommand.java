package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Description;
import seedu.address.model.person.Person;


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

    public static final String MESSAGE_ADD_DESCRIPTION_SUCCESS = "Added description to Person: %1$s";
    public static final String MESSAGE_REMOVE_DESCRIPTION_SUCCESS = "Removed description from Person: %1$s";

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
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), personToEdit.getAmount(), personToEdit.getDate(),
                this.description, personToEdit.getTags());

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.commitAddressBook();

        return new CommandResult(makeSuccessMessage(editedPerson));
    }

    /**
     * Makes a command execution succcess message based on whether the descrption is added to or removed form
     * {@personToEdit}.
     */
    private String makeSuccessMessage(Person personToEdit) {

        String message = !description.value.isEmpty() ? MESSAGE_ADD_DESCRIPTION_SUCCESS
                : MESSAGE_REMOVE_DESCRIPTION_SUCCESS;
        return String.format(message, personToEdit);
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
