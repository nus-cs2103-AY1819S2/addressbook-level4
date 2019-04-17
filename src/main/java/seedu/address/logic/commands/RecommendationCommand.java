package seedu.address.logic.commands;
import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.CommandRecommendation;
import seedu.address.model.Model;
/**
 * Return a recommended outfit to the user.
 */
// @@author PhilipPhil
public class RecommendationCommand extends Command {
    public static final String COMMAND_WORD = "recommendation";
    public static final String MESSAGE_MESSAGE_SUCCESS = "the outfit is: ";
    public static final String MESSAGE_NO_RECOMMENDATION = "could not find a recommendation";
    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        CommandRecommendation rec = new CommandRecommendation(model);
        String outfit = rec.returnRecommendationString();
        if (outfit.length() <= 0) {
            return new CommandResult(MESSAGE_NO_RECOMMENDATION);
        } else {
            return new CommandResult(MESSAGE_MESSAGE_SUCCESS + "\n" + outfit);
        }
    }
}
