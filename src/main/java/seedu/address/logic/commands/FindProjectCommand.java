package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.project.ProjectNameContainsKeywordsPredicate;

/**
 * Finds and lists all projects in PocketProject with name containing any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindProjectCommand extends FindCommand {

    public static final String FIND_PROJECT_KEYWORD = "project";

    public static final String MESSAGE_USAGE = FindCommand.COMMAND_WORD + " " + FIND_PROJECT_KEYWORD
        + " [ARGUMENTS]" + "\n"
        + ": Find all projects with names containing [ARGUMENTS]";

    private final ProjectNameContainsKeywordsPredicate predicate;

    public FindProjectCommand(ProjectNameContainsKeywordsPredicate predicate) {
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
            || (other instanceof FindProjectCommand // instanceof handles nulls
            && predicate.equals(((FindProjectCommand) other).predicate)); // state check
    }
}
