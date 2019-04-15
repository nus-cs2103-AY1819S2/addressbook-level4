package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLIENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATE;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.project.Project;

/**
 * Adds a project to the list of projects in the Pocket Project application.
 */

public class AddProjectCommand extends AddCommand {

    public static final String ADD_PROJECT_KEYWORD = "project";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " project"
            + ": Adds a project to the application .\n"
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_CLIENT + "CLIENT "
            + PREFIX_START_DATE + "START DATE "
            + PREFIX_DEADLINE + "DEADLINE "
            + "Example: " + COMMAND_WORD + " project "
            + PREFIX_NAME + "Apollo "
            + PREFIX_CLIENT + "SymbCorp "
            + PREFIX_START_DATE + "23/06/2019 "
            + PREFIX_DEADLINE + "23/11/2020 ";

    public static final String MESSAGE_ADD_PROJECT_SUCCESS = "Added Project: %1$s";

    private final Project toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Employee}
     */
    public AddProjectCommand(Project project) {
        requireNonNull(project);
        toAdd = project;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasProject(toAdd)) {
            throw new CommandException(Messages.MESSAGE_DUPLICATE_PROJECT);
        }

        model.addProject(toAdd);
        model.commitPocketProject();
        return new CommandResult(String.format(MESSAGE_ADD_PROJECT_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddProjectCommand // instanceof handles nulls
                && toAdd.equals(((AddProjectCommand) other).toAdd));
    }
    @Override
    public String toString() {
        return this.toAdd.toString();
    }


}
