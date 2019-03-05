package seedu.address.logic.commands.request;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONDITION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.request.Request;

/**
 * Creates a new request to the request book.
 */
public class CreateCommand extends RequestCommand {

    public static final String COMMAND_WORD = "create";

    public static final String MESSAGE_USAGE = RequestCommand.COMMAND_WORD + " " + COMMAND_WORD
            + ": Creates a new request in the request book. " + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_ADDRESS + "ADDRESS "
            + PREFIX_DATE + "DATETIME"
            + PREFIX_CONDITION + "CONDITION...\n"
            + "Example: " + RequestCommand.COMMAND_WORD + " " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "81234567 "
            + PREFIX_ADDRESS + "123, Sengkang Ave 3, #04-12, 214632 "
            + PREFIX_DATE + "01-01-2019 08:00:00 "
            + PREFIX_CONDITION + "Physiotherapy";

    public static final String MESSAGE_SUCCESS = "Created new request successfully: %1$s";
    public static final String MESSAGE_DUPLICATE_REQUEST = "This request already exists.";

    private final Request newRequest;

    public CreateCommand(Request newRequest) {
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
        // TODO write created request into the JSON file
        //  if (model.hasRequest(this.newRequest)) {
        //      throw new CommandException(MESSAGE_DUPLICATE_REQUEST);
        //  }
        //  model.addRequest(newRequest);
        //  model.commitRequestBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, newRequest));
    }
}
