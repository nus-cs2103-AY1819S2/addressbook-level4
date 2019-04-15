package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.project.ProjectContainsKeywordsPredicate;

/**
 * Finds and lists all projects in Pocket project that contians any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindAllCommand extends FindCommand {

    public static final String FIND_ALL_KEYWORD = "all";

    public static final String MESSAGE_USAGE = FindCommand.COMMAND_WORD + " " + FIND_ALL_KEYWORD
        + " [ARGUMENTS]" + "\n"
        + ": Find all projects with names/client/description containing [ARGUMENTS]";

    private final ProjectContainsKeywordsPredicate predicate;

    public FindAllCommand(ProjectContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredProjectList(predicate);
        return new CommandResult(
            String.format(Messages.MESSAGE_PROJECTS_LISTED_OVERVIEW, model.getFilteredProjectList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof FindAllCommand // instanceof handles nulls
            && predicate.equals(((FindAllCommand) other).predicate)); // state check
    }
}
