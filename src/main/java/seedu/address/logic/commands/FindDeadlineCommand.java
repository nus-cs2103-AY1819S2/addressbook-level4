package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.project.ProjectContainsDeadlinePredicate;

/**
 * Find and list all the projects in Pocket Project which contains deadline which are before the deadline keyword
 * input by the user
 * Also accepts flexible dates
 */
public class FindDeadlineCommand extends FindCommand {

    public static final String FIND_DEADLINE_KEYWORD = "deadline";

    public static final String MESSAGE_USAGE = FindCommand.COMMAND_WORD + " " + FIND_DEADLINE_KEYWORD + " [ARGUMENT]\n"
        + "Find all projects with deadline before or same as the argument";

    private final ProjectContainsDeadlinePredicate predicate;

    public FindDeadlineCommand(ProjectContainsDeadlinePredicate predicate) {
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
            || (other instanceof FindDeadlineCommand // instanceof handles nulls
            && predicate.equals(((FindDeadlineCommand) other).predicate)); // state check
    }
}
