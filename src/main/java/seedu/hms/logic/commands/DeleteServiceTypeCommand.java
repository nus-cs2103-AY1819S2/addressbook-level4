package seedu.hms.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.hms.commons.core.Messages;
import seedu.hms.commons.core.index.Index;
import seedu.hms.logic.CommandHistory;
import seedu.hms.logic.commands.exceptions.CommandException;
import seedu.hms.model.BookingModel;
import seedu.hms.model.booking.serviceType.ServiceType;

/**
 * Deletes a service type identified using it's displayed index from the hms book.
 */
public class DeleteServiceTypeCommand extends BookingCommand {

    public static final String COMMAND_ALIAS = "dst";
    public static final String COMMAND_WORD = "delete-service-type";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Deletes the service type identified by the index number used in the displayed service type list.\n"
        + "Parameters: INDEX (must be a positive integer)\n"
        + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_SERVICE_TYPE_SUCCESS = "Deleted Service Type: %1$s";

    private final Index targetIndex;

    public DeleteServiceTypeCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(BookingModel model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<ServiceType> lastShownList = model.getServiceTypeList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_SERVICE_TYPE_DISPLAYED_INDEX);
        }

        ServiceType serviceType = lastShownList.get(targetIndex.getZeroBased());
        model.deleteServiceType(serviceType);
        model.commitHotelManagementSystem();
        return new CommandResult(String.format(MESSAGE_DELETE_SERVICE_TYPE_SUCCESS, serviceType));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof DeleteServiceTypeCommand // instanceof handles nulls
            && targetIndex.equals(((DeleteServiceTypeCommand) other).targetIndex)); // state check
    }
}
