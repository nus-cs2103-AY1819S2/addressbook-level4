package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.healthworker.HealthWorker;

/**
 * Adds a HealthWorker to the address book.
 * @author Lookaz
 */
public class AddHealthWorkerCommand extends AddCommand implements HealthWorkerCommand {

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + COMMAND_OPTION
            + ": Add a health worker to the address book. Parameters:" + ADD_COMMAND_PARAMETERS + "\n"
            + "Example: " + COMMAND_WORD + " " + ADD_COMMAND_EXAMPLE;

    public static final String MESSAGE_SUCCESS = "New health worker added: %1$s";

    private final HealthWorker toAdd;

    public AddHealthWorkerCommand(HealthWorker toAdd) {
        requireNonNull(toAdd);
        this.toAdd = toAdd;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasHealthWorker(toAdd)) {
            throw new CommandException(DUPLICATE_HEALTH_WORKER);
        }

        add(model, toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public void add(Model model, Object toAdd) {
        model.addHealthWorker((HealthWorker) toAdd);
        model.commitHealthWorkerBook();
    }

    @Override
    public boolean equals(Object other) {
        return (other == this) || (other instanceof AddHealthWorkerCommand
                && (this.toAdd.equals(((AddHealthWorkerCommand) other).toAdd)));
    }
}
