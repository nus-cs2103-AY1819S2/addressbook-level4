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

/**
 * Displays a summary of the progress of a project
 */
public class IndividualStatsCommand extends StatsCommand {

    public static final String MESSAGE_USAGE = StatsCommand.COMMAND_WORD
            + ": Displays A summary of the progress of a project.\n";
    private final ProjectName projectName;
    private final Index targetIndex;

    public IndividualStatsCommand(ProjectName targetName) {
        this.projectName = targetName;
        this.targetIndex = null;
    }

    public IndividualStatsCommand(Index index) {
        this.targetIndex = index;
        this.projectName = null;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        ProjectName targetName = this.projectName;
        if (targetName == null) {
            List<Project> lastShownList = model.getFilteredProjectList();
            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PROJECT_DISPLAYED_INDEX);
            }
            targetName = lastShownList.get(targetIndex.getZeroBased()).getProjectName();
        }
        Project targetProject = model.getProjectWithName(targetName);
        if (targetProject == null) {
            throw new CommandException(Messages.MESSAGE_INVALID_PROJECT_NAME);
        }
        String result = model.individualStats(targetProject);

        return new CommandResult(result);
    }

    @Override
    public boolean equals(Object other) {
        if (projectName != null) {
            return other == this // short circuit if same object
                    || (other instanceof IndividualStatsCommand // instanceof handles nulls
                    && projectName.equals(((IndividualStatsCommand) other).projectName));
            // state check
        } else {
            return other == this // short circuit if same object
                    || (other instanceof IndividualStatsCommand // instanceof handles nulls
                    && targetIndex.equals(((IndividualStatsCommand) other).targetIndex)); // state check
        }
    }
}
