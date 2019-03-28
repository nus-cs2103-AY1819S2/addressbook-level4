package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REVIEW;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REVIEWTITLE;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.book.Review;

/**
 * Adds a review to the book.
 */
public class AddReviewCommand extends Command {

    public static final String COMMAND_WORD = "addReview";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a review to a certain book. \n"
            + "Parameters: "
            + PREFIX_NAME + "BOOK NAME "
            + PREFIX_REVIEWTITLE + "REVIEW TITLE "
            + PREFIX_REVIEW + "REVIEW \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Alice in Wonderland "
            + PREFIX_REVIEWTITLE + "A great fairytale "
            + PREFIX_REVIEW + "While Lewis Carroll purists will scoff at the aging of his young protagonist...";

    public static final String MESSAGE_SUCCESS = "New review added: %1$s";
    public static final String MESSAGE_DUPLICATE_REVIEW = "This review already exists in the book shelf";

    private final Review toAdd;

    /**
     * Creates an AddReviewCommand to add the specified {@code Review}
     */
    public AddReviewCommand(Review review) {
        requireNonNull(review);
        toAdd = review;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasReview(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_REVIEW);
        }

        model.addReview(toAdd);
        model.commitBookShelf();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd.getTitle().fullName));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddReviewCommand // instanceof handles nulls
                && toAdd.equals(((AddReviewCommand) other).toAdd));
    }

    @Override
    public String toString() {
        return ("Add: " + toAdd.toString());
    }
}
