package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.model.FoodDiary;
import seedu.address.model.Model;

/**
 * Clears the food diary.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Food diary has been cleared!";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.setFoodDiary(new FoodDiary());
        model.commitFoodDiary();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
