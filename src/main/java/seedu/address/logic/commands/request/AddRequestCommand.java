package seedu.address.logic.commands.request;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.Statistics;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.request.Request;

/**
 * Creates a new request to the request book.
 */
public class AddRequestCommand extends AddCommand implements RequestCommand {

    public static final String COMMAND_WORD = "request";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Creates a new request in the request book.\n" + "Parameters: "
        + ADD_COMMAND_PARAMETERS
        + "Example: " + COMMAND_WORD + " " + COMMAND_OPTION
        + ADD_COMMAND_EXAMPLE;

    public static final String MESSAGE_SUCCESS = "Created new request successfully: %1$s";

    private final Request newRequest;

    public AddRequestCommand(Request newRequest) {
        requireNonNull(newRequest);
        this.newRequest = newRequest;
    }

    /**
     * Executes the command and returns the result message.
     *
     * @param model   {@code Model} which the command should operate on.
     * @param history {@code CommandHistory} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        if (model.hasRequest(this.newRequest)) {
            throw new CommandException(MESSAGE_DUPLICATE_REQUEST);
        }
        model.addRequest(newRequest);
        model.commitRequestBook();
        Statistics.updateStatistics(newRequest.getConditions());
        return new CommandResult(String.format(MESSAGE_SUCCESS, newRequest));
    }

    @Override
    public void add(Model model, Object toAdd) {
        model.addRequest((Request) toAdd);
        model.commitRequestBook();
    }

    @Override
    public boolean equals(Object other) {
        return other == this
            || (other instanceof AddRequestCommand
            && newRequest.isSameRequest(((AddRequestCommand) other).newRequest));
    }
}
