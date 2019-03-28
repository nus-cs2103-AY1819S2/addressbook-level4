package seedu.address.logic.commands;


import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_REVIEWS;

/**
 * Lists all reviews in the book shelf to the user.
 */
public class ListAllReviewsCommand extends Command {

    public static final String COMMAND_WORD = "listAllReviews";

    public static final String MESSAGE_SUCCESS = "Listed all reviews!";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredReviewList(PREDICATE_SHOW_ALL_REVIEWS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
