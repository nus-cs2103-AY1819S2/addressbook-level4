package seedu.travel.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.travel.model.Model.PREDICATE_SHOW_ALL_PLACES;

import seedu.travel.logic.CommandHistory;
import seedu.travel.model.Model;

/**
 * Lists all places in the travel book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    public static final String COMMAND_ALIAS = "l";
    public static final String MESSAGE_SUCCESS = "Listed all places";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredPlaceList(PREDICATE_SHOW_ALL_PLACES);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
