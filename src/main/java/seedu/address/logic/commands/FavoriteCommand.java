package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.NameMatchesPredicate;


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

    public static final String MESSAGE_SUCCESS = "New person added";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the favorite list";
    public static final String MESSAGE_NOT_SPECIFIC = "Please be more specific";
    public static final String MESSAGE_NOT_EXIST = "No person exists with this name";



    private final NameMatchesPredicate predicate;
    private final NameContainsKeywordsPredicate containsPredicate;

    public FavoriteCommand(NameMatchesPredicate predicate, NameContainsKeywordsPredicate containsPredicate) {
        requireNonNull(predicate);
        requireNonNull(containsPredicate);
        this.predicate = predicate;
        this.containsPredicate = containsPredicate;
    }



    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);

        if (model.getFilteredPersonList().size() == 0) {
            model.updateFilteredPersonList(containsPredicate);
            throw new CommandException(MESSAGE_NOT_EXIST);
        } else if (model.getFavoritesList().contains(model.getFilteredPersonList().get(0))) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } else if (model.getFilteredPersonList().size() != 1) {
            model.updateFilteredPersonList(containsPredicate);
            return new CommandResult(String.format(MESSAGE_NOT_SPECIFIC));
        } else {
            model.addFavorites(model.getFilteredPersonList().get(0));
            model.commitAddressBook();
            return new CommandResult(String.format(MESSAGE_SUCCESS));
        }
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FavoriteCommand // instanceof handles nulls
                && predicate.equals(((FavoriteCommand) other).predicate)); // state check
    }

}

