package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.project.Project;
import seedu.address.model.project.ProjectName;
import seedu.address.model.util.PocketProjectDate;


/**
 * Deletes a project identified using it's displayed index/name from the pocket project.
 */
public class CompleteCommand extends Command {

    public static final String COMMAND_WORD = "complete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Completes the project identified by the index of the project on \n"
            + "a specified date. The PROJECT_INDEX needs to be a positive integer and and cannot be larger than "
            + "maximum integer value which is 2,147,483,647\n"
            + "Parameters: PROJECT_INDEX, COMPLETION_DATE\n"
            + "Example: " + COMMAND_WORD + " 1 11/11/2011\n";

    public static final String MESSAGE_COMPLETE_PROJECT_SUCCESS = "Completed Project: %1$s";
    private final Index targetIndex;
    private final PocketProjectDate completionDate;

    public CompleteCommand(Index index, PocketProjectDate completionDate) {
        this.targetIndex = index;
        this.completionDate = completionDate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Project> lastShownList = model.getFilteredProjectList();
        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PROJECT_DISPLAYED_INDEX);
        }
        ProjectName targetName = lastShownList.get(targetIndex.getZeroBased()).getProjectName();
        Project projectToComplete = model.getProjectWithName(targetName);
        if (projectToComplete == null) {
            throw new CommandException(Messages.MESSAGE_INVALID_PROJECT_NAME);
        }
        model.completeProject(projectToComplete, this.completionDate);
        model.commitPocketProject();

        return new CommandResult(String.format(MESSAGE_COMPLETE_PROJECT_SUCCESS, projectToComplete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                    || (other instanceof CompleteCommand // instanceof handles nulls
                    && targetIndex.equals(((CompleteCommand) other).targetIndex)
                    && completionDate.equals(((CompleteCommand) other).completionDate)); // state check
    }
}
