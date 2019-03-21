package seedu.address.logic.commands.request;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONDITION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.request.Request;

/**
 * Creates a new request to the request book.
 */
public class AddRequestCommand extends AddCommand {

    public static final String COMMAND_WORD = "request";

    public static final String MESSAGE_USAGE = RequestCommand.COMMAND_WORD + " " + AddCommand.COMMAND_WORD
        + ": Creates a new request in the request book. " + "Parameters: "
        + PREFIX_NAME + "NAME "
        + PREFIX_NRIC + "NRIC "
        + PREFIX_PHONE + "PHONE "
        + PREFIX_ADDRESS + "ADDRESS "
        + PREFIX_DATE + "DATETIME"
        + PREFIX_CONDITION + "CONDITION...\n"
        + "Example: " + RequestCommand.COMMAND_WORD + " " + AddCommand.COMMAND_WORD + " "
        + PREFIX_NAME + "John Doe "
        + PREFIX_PHONE + "81234567 "
        + PREFIX_ADDRESS + "123, Sengkang Ave 3, #04-12, 214632 "
        + PREFIX_DATE + "01-01-2019 08:00:00 "
        + PREFIX_CONDITION + "Physiotherapy";

    public static final String MESSAGE_SUCCESS = "Created new request successfully: %1$s";
    public static final String MESSAGE_DUPLICATE_REQUEST = "This request already exists.";

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
