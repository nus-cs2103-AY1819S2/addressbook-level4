package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.project.Project;

/**
 * Selects a project identified using it's displayed index from the projects list.
 */
public class ViewProjectCommand extends ViewCommand {

    public static final String VIEW_PROJECT_KEYWORD = "project";

    public static final String MESSAGE_USAGE = ViewCommand.COMMAND_WORD
            + " "
            + VIEW_PROJECT_KEYWORD
            + ": Selects the project identified by the index number used in the displayed project list.\n"
            + "Parameters: INDEX (must be a positive integer) and cannot be larger than maximum integer value which is "
            + "2,147,483,647 \n"
            + "Example: "
            + ViewCommand.COMMAND_WORD
            + " "
            + VIEW_PROJECT_KEYWORD
            + " 1";

    public static final String MESSAGE_VIEW_PROJECT_SUCCESS = "Viewing project details at index: %1$s";

    private final Index targetIndex;

    public ViewProjectCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        List<Project> filteredProjectList = model.getFilteredProjectList();

        if (targetIndex.getZeroBased() >= filteredProjectList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PROJECT_DISPLAYED_INDEX);
        }

        model.setSelectedProject(filteredProjectList.get(targetIndex.getZeroBased()));
        return new CommandResult(String.format(MESSAGE_VIEW_PROJECT_SUCCESS, targetIndex.getOneBased()));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ViewProjectCommand // instanceof handles nulls
                && targetIndex.equals(((ViewProjectCommand) other).targetIndex)); // state check
    }
}
