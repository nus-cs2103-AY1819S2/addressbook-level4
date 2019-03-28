package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.model.ArchiveBook;
import seedu.address.model.Model;

/**
 * Clears the address book.
 */
public class ArchiveClearCommand extends Command {

    public static final String COMMAND_WORD = "archiveclear";
    public static final String MESSAGE_SUCCESS = "Archive book has been cleared!";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.setArchiveBook(new ArchiveBook());
        model.commitAddressBook();
        model.commitArchiveBook();
        return new CommandResult(MESSAGE_SUCCESS, false, false , true);
    }

    @Override
    public boolean requiresMainList() {
        return false;
    }

    @Override
    public boolean requiresArchiveList() {
        return true;
    }
}
