package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Deletes multiple persons ranging from start index to end index in the address book.
 */
public class DeleteMultipleCommand extends Command {

    public static final String COMMAND_WORD = "deletem";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes multiple persons identified by the start and end index number in the last person listing.\n"
            + "Parameters: START_INDEX END_INDEX\n"
            + "Example: " + COMMAND_WORD + " 1" + " 5";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: ";

    private Index targetStartIndex;
    private Index targetEndIndex;
    private StringBuilder deletedPersonsList;

    public DeleteMultipleCommand(Index startIndex, Index endIndex) {
        this.targetStartIndex = startIndex;
        this.targetEndIndex = endIndex;
        this.deletedPersonsList = new StringBuilder();
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetStartIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        if (targetEndIndex.getZeroBased() >= lastShownList.size()) {
            this.targetEndIndex = Index.fromOneBased(lastShownList.size());
        }

        int numOfPersonsToDelete = targetEndIndex.getOneBased() - targetStartIndex.getZeroBased();
        int startIndex = targetStartIndex.getZeroBased();

        for (int i = 0; i < numOfPersonsToDelete; i++) {
            Person personToDelete = lastShownList.get(startIndex);
            model.deletePerson(personToDelete);
            buildDeletedPersonsList(personToDelete);
        }
        model.commitAddressBook();
        return new CommandResult(deletedPersonsList.toString());
    }

    private void buildDeletedPersonsList(Person target) {
        this.deletedPersonsList.append(MESSAGE_DELETE_PERSON_SUCCESS);
        this.deletedPersonsList.append(target);
        this.deletedPersonsList.append("\n");
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteMultipleCommand // instanceof handles nulls
                && targetStartIndex.equals(((DeleteMultipleCommand) other).targetStartIndex)) // state check
                && targetEndIndex.equals(((DeleteMultipleCommand) other).targetEndIndex); // state check
    }
}

