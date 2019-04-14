package seedu.hms.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_CAPACITY;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_RATE;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_TIMING;

import seedu.hms.logic.CommandHistory;
import seedu.hms.logic.commands.exceptions.CommandException;
import seedu.hms.model.BookingModel;
import seedu.hms.model.booking.serviceType.ServiceType;

/**
 * Adds a serviceType to the hms book.
 */
public class AddServiceTypeCommand extends BookingCommand {

    public static final String COMMAND_ALIAS = "ast";
    public static final String COMMAND_WORD = "add-service-type";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a service type to the hotel management system.\n"
        + "Parameters: "
        + PREFIX_NAME + "SERVICE NAME "
        + PREFIX_TIMING + "OPERATIONAL HOURS(HH - HH in 24 hour format) "
        + PREFIX_CAPACITY + "CAPACITY OF SERVICE "
        + PREFIX_RATE + "RATE PER HOUR\n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_NAME + "BILLIARDS "
        + PREFIX_TIMING + "08 - 18 "
        + PREFIX_CAPACITY + "20 "
        + PREFIX_RATE + "4.0 ";

    public static final String MESSAGE_SUCCESS = "New service type added: %1$s";
    public static final String MESSAGE_DUPLICATE_SERVICE_TYPE = "This service type already exists in the hms book";
    private final ServiceType toAdd;

    /**
     * Creates an AddServiceTypeCommand to add the specified {@code ServiceType}
     */
    public AddServiceTypeCommand(ServiceType serviceType) {
        requireNonNull(serviceType);
        toAdd = serviceType;
    }

    @Override
    public CommandResult execute(BookingModel model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        if (model.hasServiceType(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_SERVICE_TYPE);
        }
        model.addServiceType(toAdd);
        model.commitHotelManagementSystem();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof AddServiceTypeCommand // instanceof handles nulls
            && toAdd.equals(((AddServiceTypeCommand) other).toAdd));
    }
}
