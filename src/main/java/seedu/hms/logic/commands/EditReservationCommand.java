package seedu.hms.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_COMMENT;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_CUSTOMERS;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_DATES;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_PAYER;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_ROOM;
import static seedu.hms.model.Model.PREDICATE_SHOW_ALL_RESERVATIONS;

import java.util.List;
import java.util.Optional;

import seedu.hms.commons.core.Messages;
import seedu.hms.commons.core.index.Index;
import seedu.hms.commons.util.CollectionUtil;
import seedu.hms.logic.CommandHistory;
import seedu.hms.logic.commands.exceptions.CommandException;
import seedu.hms.model.ReservationModel;
import seedu.hms.model.customer.Customer;
import seedu.hms.model.reservation.Reservation;
import seedu.hms.model.reservation.RoomType;
import seedu.hms.model.reservation.exceptions.RoomFullException;
import seedu.hms.model.reservation.exceptions.RoomUnavailableException;
import seedu.hms.model.util.DateRange;

/**
 * Edits a reservation in the hotel management system
 */
public class EditReservationCommand extends ReservationCommand {

    public static final String COMMAND_ALIAS = "er";
    public static final String COMMAND_WORD = "edit-reservation";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits a reservation to the hotel management system. "
        + "Parameters: RESERVATION INDEX (to edit) "
        + "[" + PREFIX_ROOM + "ROOM NAME "
        + "[" + PREFIX_DATES + "DATES(DD/MM/YY - DD/MM/YY)] "
        + "[" + PREFIX_PAYER + "PAYER INDEX]"
        + "[" + PREFIX_CUSTOMERS + "CUSTOMER INDEX(s)]... "
        + "[" + PREFIX_COMMENT + "COMMENT]\n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_ROOM + "GYM "
        + PREFIX_PAYER + "2 "
        + PREFIX_CUSTOMERS + "1,3 "
        + PREFIX_COMMENT + "Edited second reservation to add reservations and change payer and room\n";

    public static final String MESSAGE_EDIT_RESERVATION_SUCCESS = "Reservation edited: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_ROOM_FULL = "The room has been booked fully during your requested hours";
    public static final String MESSAGE_ROOM_UNAVAILABLE = "The room is not available during your requested hours";

    private final EditReservationDescriptor editReservationDescriptor;
    private final Index index;

    /**
     * Creates an EditReservationCommand to edit the reservation at specified index
     */
    public EditReservationCommand(Index index, EditReservationDescriptor editReservationDescriptor) {
        requireNonNull(editReservationDescriptor);
        requireNonNull(index);

        this.editReservationDescriptor = editReservationDescriptor;
        this.index = index;

    }

    /**
     * Creates and returns a {@code Reservation} with the details of {@code reservationToEdit}
     * edited with {@code editReservationDescriptor}.
     */
    private static Reservation createEditedReservation(Reservation reservationToEdit,
                                                       EditReservationCommand.EditReservationDescriptor editReservationDescriptor) {
        assert reservationToEdit != null;

        RoomType updatedRoomType = editReservationDescriptor.getRoomType().orElse(reservationToEdit.getRoom());
        DateRange updatedDateRange = editReservationDescriptor.getDates().orElse(reservationToEdit.getDates());
        Customer updatedPayer = editReservationDescriptor.getPayer().orElse(reservationToEdit.getPayer());
        Optional<List<Customer>> updatedOtherUsers =
            editReservationDescriptor.getOtherUsers().orElse(reservationToEdit.getOtherUsers());
        Optional<String> updatedComment = editReservationDescriptor.getComment().orElse(reservationToEdit.getComment());

        return new Reservation(updatedRoomType, updatedDateRange, updatedPayer, updatedOtherUsers, updatedComment);
    }

    @Override
    public CommandResult execute(ReservationModel model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Reservation> lastShownList = model.getFilteredReservationList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_RESERVATION_DISPLAYED_INDEX);
        }

        Reservation reservationToEdit = lastShownList.get(index.getZeroBased());
        Reservation editedReservation = createEditedReservation(reservationToEdit, editReservationDescriptor);

        try {
            model.setReservation(index.getZeroBased(), editedReservation);
        } catch (RoomUnavailableException e) {
            return new CommandResult(MESSAGE_ROOM_UNAVAILABLE);
        } catch (RoomFullException e) {
            return new CommandResult(MESSAGE_ROOM_FULL);
        }
        model.updateFilteredReservationList(PREDICATE_SHOW_ALL_RESERVATIONS);
        model.commitHotelManagementSystem();
        return new CommandResult(String.format(MESSAGE_EDIT_RESERVATION_SUCCESS, editedReservation));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditReservationCommand)) {
            return false;
        }

        // state check
        EditReservationCommand e = (EditReservationCommand) other;
        return index.equals(e.index)
            && editReservationDescriptor.equals(e.editReservationDescriptor);
    }

    /**
     * Stores the details to edit the reservation with. Each non-empty field value will replace the
     * corresponding field value of the reservation.
     */
    public static class EditReservationDescriptor {
        private RoomType roomType;
        private DateRange dates;
        private Customer payer;
        private Optional<List<Customer>> otherUsers;
        private Optional<String> comment;

        public EditReservationDescriptor() {
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditReservationDescriptor(EditReservationCommand.EditReservationDescriptor toCopy) {
            setRoomType(toCopy.roomType);
            setDates(toCopy.dates);
            setPayer(toCopy.payer);
            setOtherUsers(toCopy.otherUsers);
            setComment(toCopy.comment);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(roomType, dates, payer, otherUsers, comment);
        }

        public Optional<RoomType> getRoomType() {
            return Optional.ofNullable(roomType);
        }

        public void setRoomType(RoomType roomType) {
            this.roomType = roomType;
        }

        public Optional<DateRange> getDates() {
            return Optional.ofNullable(dates);
        }

        public void setDates(DateRange dates) {
            this.dates = dates;
        }

        public Optional<Customer> getPayer() {
            return Optional.ofNullable(payer);
        }

        public void setPayer(Customer payer) {
            this.payer = payer;
        }

        public Optional<Optional<List<Customer>>> getOtherUsers() {
            return Optional.ofNullable(otherUsers);
        }

        public void setOtherUsers(Optional<List<Customer>> otherUsers) {
            this.otherUsers = otherUsers;
        }

        public Optional<Optional<String>> getComment() {
            return Optional.ofNullable(comment);
        }

        public void setComment(Optional<String> comment) {
            this.comment = comment;
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditReservationCommand.EditReservationDescriptor)) {
                return false;
            }

            // state check
            EditReservationCommand.EditReservationDescriptor e =
                (EditReservationCommand.EditReservationDescriptor) other;

            return getRoomType().equals(e.getRoomType())
                && getDates().equals(e.getDates())
                && getPayer().equals(e.getPayer())
                && getOtherUsers().equals(e.getOtherUsers())
                && getComment().equals(e.getComment());
        }
    }
}
