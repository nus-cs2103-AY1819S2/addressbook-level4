package seedu.hms.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_CAPACITY;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_RATE;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_TIMING;

import java.util.List;
import java.util.Optional;

import seedu.hms.commons.core.Messages;
import seedu.hms.commons.core.index.Index;
import seedu.hms.commons.util.CollectionUtil;
import seedu.hms.logic.CommandHistory;
import seedu.hms.logic.commands.exceptions.CommandException;
import seedu.hms.model.BookingModel;
import seedu.hms.model.booking.serviceType.ServiceType;
import seedu.hms.model.util.TimeRange;

/**
 * Edits a booking in the hotel management system
 */
public class EditServiceTypeCommand extends BookingCommand {

    public static final String COMMAND_ALIAS = "est";
    public static final String COMMAND_WORD = "edit-service-type";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits a service type in the hotel management system.\n"
        + "Parameters: SERVICE TYPE INDEX (to edit) "
        + "[" + PREFIX_NAME + "SERVICE NAME] "
        + "[" + PREFIX_TIMING + "OPERATIONAL HOURS(HH - HH in 24 hour format)] "
        + "[" + PREFIX_CAPACITY + "CAPACITY OF SERVICE] "
        + "[" + PREFIX_RATE + "RATE PER HOUR]\n"
        + "Example: " + COMMAND_WORD + " 1 "
        + PREFIX_NAME + "BILLIARDS "
        + PREFIX_TIMING + "08 - 18 "
        + PREFIX_CAPACITY + "20 "
        + PREFIX_RATE + "4.0 ";

    public static final String MESSAGE_EDIT_SERVICE_TYPE_SUCCESS = "Service Type edited: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_SERVICE_TYPE = "This service type already exists in the hms book";

    private final EditServiceTypeDescriptor editServiceTypeDescriptor;
    private final Index index;

    /**
     * Creates an EditServiceTypeCommand to edit the serviceType at specified index
     */
    public EditServiceTypeCommand(Index index, EditServiceTypeDescriptor editServiceTypeDescriptor) {
        requireNonNull(editServiceTypeDescriptor);
        requireNonNull(index);

        this.editServiceTypeDescriptor = editServiceTypeDescriptor;
        this.index = index;
    }

    /**
     * Creates and returns a {@code ServiceType} with the details of {@code serviceTypeToEdit}
     * edited with {@code editServiceTypeDescriptor}.
     */
    private static ServiceType createEditedServiceType(ServiceType serviceTypeToEdit,
                                                       EditServiceTypeCommand.EditServiceTypeDescriptor
                                                           editServiceTypeDescriptor) {
        assert serviceTypeToEdit != null;

        String updatedName = editServiceTypeDescriptor.getName().orElse(serviceTypeToEdit.getName());
        TimeRange updatedTimeRange = editServiceTypeDescriptor.getTiming().orElse(serviceTypeToEdit.getTiming());
        int updatedCapacity = editServiceTypeDescriptor.getCapacity().orElse(serviceTypeToEdit.getCapacity());
        double updatedRate = editServiceTypeDescriptor.getRatePerHour().orElse(serviceTypeToEdit.getRatePerHour());

        return new ServiceType(updatedCapacity, updatedTimeRange, updatedName, updatedRate);
    }

    @Override
    public CommandResult execute(BookingModel model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<ServiceType> lastShownList = model.getServiceTypeList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_SERVICE_TYPE_DISPLAYED_INDEX);
        }

        ServiceType serviceTypeToEdit = lastShownList.get(index.getZeroBased());
        ServiceType editedServiceType = createEditedServiceType(serviceTypeToEdit, editServiceTypeDescriptor);

        if (!serviceTypeToEdit.equals(editedServiceType) && model.hasServiceType(editedServiceType)) {
            throw new CommandException(MESSAGE_DUPLICATE_SERVICE_TYPE);
        }

        model.setServiceType(index.getZeroBased(), editedServiceType);
        model.commitHotelManagementSystem();
        return new CommandResult(String.format(MESSAGE_EDIT_SERVICE_TYPE_SUCCESS, editedServiceType));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditServiceTypeCommand)) {
            return false;
        }

        // state check
        EditServiceTypeCommand e = (EditServiceTypeCommand) other;
        return index.equals(e.index)
            && editServiceTypeDescriptor.equals(e.editServiceTypeDescriptor);
    }

    /**
     * Stores the details to edit the serviceType with. Each non-empty field value will replace the
     * corresponding field value of the serviceType.
     */
    public static class EditServiceTypeDescriptor {
        private String name;
        private TimeRange timing;
        private Integer capacity;
        private Double ratePerHour;

        public EditServiceTypeDescriptor() {
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditServiceTypeDescriptor(EditServiceTypeCommand.EditServiceTypeDescriptor toCopy) {
            setName(toCopy.name);
            setTiming(toCopy.timing);
            setCapacity(toCopy.capacity);
            setRatePerHour(toCopy.ratePerHour);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, timing, capacity, ratePerHour);
        }

        public Optional<String> getName() {
            return Optional.ofNullable(name);
        }

        public void setName(String name) {
            this.name = name;
        }

        public Optional<TimeRange> getTiming() {
            return Optional.ofNullable(timing);
        }

        public void setTiming(TimeRange timing) {
            this.timing = timing;
        }

        public Optional<Integer> getCapacity() {
            return Optional.ofNullable(capacity);
        }

        public void setCapacity(int capacity) {
            this.capacity = capacity;
        }

        public Optional<Double> getRatePerHour() {
            return Optional.ofNullable(ratePerHour);
        }

        public void setRatePerHour(double rph) {
            this.ratePerHour = rph;
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditServiceTypeCommand.EditServiceTypeDescriptor)) {
                return false;
            }

            // state check
            EditServiceTypeCommand.EditServiceTypeDescriptor e =
                (EditServiceTypeCommand.EditServiceTypeDescriptor) other;

            return getName().equals(e.getName())
                && getTiming().equals(e.getTiming())
                && getCapacity().equals(e.getCapacity())
                && getRatePerHour().equals(e.getRatePerHour());
        }
    }
}
