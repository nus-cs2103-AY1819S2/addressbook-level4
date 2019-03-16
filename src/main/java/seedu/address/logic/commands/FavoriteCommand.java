package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.person.NameContainsKeywordsPredicate;


/**
 * Finds and adds all persons in address book whose name contains any of the argument keywords to a favorites list.
 * Keyword matching is case sensitive.
 */
public class FavoriteCommand extends Command {

    public static final String COMMAND_WORD = "favorite";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names contain\n"
            + "any of the specified \"\n"
            + "keywords (case-sensitive) and adds them to a list of favorite contacts.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD
            + " John";

    public static final String MESSAGE_SUCCESS = "New person added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the favorite list";
    public static final String MESSAGE_COMPLETE = "Adding to favorite list completed.";


    private final NameContainsKeywordsPredicate predicate;

    public FavoriteCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }



    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        model.addFavorites(model.getFilteredPersonList().get(0));
        model.commitAddressBook();
        return new CommandResult(
                String.format(MESSAGE_SUCCESS, model.getFilteredPersonList().get(0).getName()));
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FavoriteCommand // instanceof handles nulls
                && predicate.equals(((FavoriteCommand) other).predicate)); // state check
    }

}

