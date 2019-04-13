package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ACTIVITYNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.activity.ActivityContainsKeywordsPredicate;

/**
 * Finds and lists all activities in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class ActivityFindCommand extends ActivityCommand {
    public static final String COMMAND_WORD = "activityFind";
    public static final String COMMAND_ALIAS = "aFind";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all activities whose attribute contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "The attributes that activityFind search for is specified by the prefix input by the user.\n"
            + "Parameters: [PREFIX] KEYWORD [MORE_KEYWORDS] [NEXT_PREFIX] [KEYWORDS] ...\n"
            + "The provided PREFIX includes " + PREFIX_ACTIVITYNAME + " (ActivityName), "
            + PREFIX_ADESCRIPTION + " (ActivityDescription) and " + PREFIX_LOCATION + " (ActivityLocation)." + "\n"
            + "If no prefix is provided, all the attributes mentioned above will be searched. \n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_ACTIVITYNAME + " cohesion dance outing";

    private final ActivityContainsKeywordsPredicate predicate;

    public ActivityFindCommand(ActivityContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredActivityList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_ACTIVITIES_LISTED_OVERVIEW, model.getFilteredActivityList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ActivityFindCommand // instanceof handles nulls
                && predicate.equals(((ActivityFindCommand) other).predicate)); // state check
    }
}
