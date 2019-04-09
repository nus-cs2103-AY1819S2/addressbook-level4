package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.Statistics.clearStatistics;
import static seedu.address.logic.commands.Statistics.updateStatistics;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_HEALTHWORKERS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_REQUESTS;

import java.util.Set;

import javafx.collections.ObservableList;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyRequestBook;
import seedu.address.model.request.Request;
import seedu.address.model.tag.Condition;



/**
 * Reverts the {@code model}'s address book to its previous state.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_SUCCESS = "Undo success!";
    public static final String MESSAGE_FAILURE = "No more commands to undo!";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (!model.canUndo()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        model.undo();
        model.updateFilteredHealthWorkerList(PREDICATE_SHOW_ALL_HEALTHWORKERS);
        model.updateFilteredRequestList(PREDICATE_SHOW_ALL_REQUESTS);

        ReadOnlyRequestBook requestBook = model.getRequestBook();
        ObservableList<Request> requestList = requestBook.getRequestList();
        clearStatistics();
        for (Request request : requestList) {
            Set<Condition> conditionSet = request.getConditions();
            updateStatistics(conditionSet);
        }

        return new CommandResult(MESSAGE_SUCCESS);
    }
}
