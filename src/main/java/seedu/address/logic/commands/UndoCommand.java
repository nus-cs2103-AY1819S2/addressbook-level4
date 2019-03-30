package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
//import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Album;
import seedu.address.model.CurrentEdit;
import seedu.address.model.Model;
import seedu.address.model.image.Image;

/**
 * Reverts the {@code model}'s address book to its previous state.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_SUCCESS = "Undo success!";
    public static final String MESSAGE_FAILURE = "No more commands to undo!";

    /*
    @Override
    public CommandResult execute(CurrentEdit currentEdit, Album album,
                                 Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (!model.canUndoAddressBook()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        model.undoAddressBook();
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(MESSAGE_SUCCESS);


    }*/

    @Override
    public CommandResult execute(CurrentEdit current, Album album, Model model, CommandHistory history)
        throws CommandException {
        requireNonNull(current);
        //AddressBookParser parser = new AddressBookParser();
        CommandResult commandResult;
        Image initialImage = current.getTempImage();
        if (initialImage == null) {
            throw new CommandException(Messages.MESSAGE_DID_NOT_OPEN);
        }

        if (!current.canUndoTemp()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        current.getTempImage().setUndo();
        current.replaceTempWithOriginal();
        List<Command> tempHistory = current.getTempSubHistory();
        for (Command command :tempHistory) {
            commandResult = command.execute(current, album, model, history);
        }
        current.displayTempImage();

        return new CommandResult(MESSAGE_SUCCESS);


    }
}
