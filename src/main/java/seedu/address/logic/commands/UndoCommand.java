package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

//import java.util.List;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
//import seedu.address.logic.parser.AddressBookParser;
//import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Album;
import seedu.address.model.CurrentEdit;
import seedu.address.model.Model;

/**
 * Reverts the {@code model}'s address book to its previous state.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_SUCCESS = "Undo success!";
    public static final String MESSAGE_FAILURE = "No more commands to undo!";


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


    }

    /*
    @Override
    public CommandResult execute(CurrentEdit current, CommandHistory history) throws CommandException {
        requireNonNull(current);
        AddressBookParser parser = new AddressBookParser();
        CommandResult commandResult;

        if (!current.canUndoImage()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        if (!current.canUndoAddressBook()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        current.replaceTempWithOriginal();
        current.getTempImage().setUndo();
        List<String> tempHistory = current.getTempImage().getHistory();
        for (String x :tempHistory) {
            try {
                Command command = parser.parseCommand(x);
                commandResult = command.execute(current, history);
            } catch (ParseException e) {
                System.out.println(e.toString());
            }

        }

        model.undoAddressBook();
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(MESSAGE_SUCCESS);


    }*/
}
