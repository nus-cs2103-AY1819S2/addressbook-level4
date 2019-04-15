package seedu.hms.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_CAPACITY;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_RATE;

import java.util.List;
import java.util.Optional;

import seedu.hms.commons.core.Messages;
import seedu.hms.commons.core.index.Index;
import seedu.hms.commons.util.CollectionUtil;
import seedu.hms.logic.CommandHistory;
import seedu.hms.logic.commands.exceptions.CommandException;
import seedu.hms.model.ReservationModel;
import seedu.hms.model.reservation.roomType.RoomType;
import seedu.hms.model.util.TimeRange;

/**
 * Edits a reservation in the hotel management system
 */
public class EditRoomTypeCommand extends ReservationCommand {

    public static final String COMMAND_ALIAS = "ert";
    public static final String COMMAND_WORD = "edit-room-type";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits a room type in the hotel management system.\n"
        + "Parameters: ROOM TYPE INDEX (to edit) "
        + "[" + PREFIX_NAME + "ROOM NAME] "
        + "[" + PREFIX_CAPACITY + "NUMBER OF ROOM] "
        + "[" + PREFIX_RATE + "RATE PER DAY]\n"
        + "Example: " + COMMAND_WORD + " 1 "
        + PREFIX_NAME + "BILLIARDS "
        + PREFIX_CAPACITY + "20 "
        + PREFIX_RATE + "4.0 ";

    public static final String MESSAGE_EDIT_ROOM_TYPE_SUCCESS = "Room Type edited: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_ROOM_TYPE = "This room type already exists in the hms book";

    private final EditRoomTypeDescriptor editRoomTypeDescriptor;
    private final Index index;

    /**
     * Creates an EditRoomTypeCommand to edit the roomType at specified index
     */
    public EditRoomTypeCommand(Index index, EditRoomTypeDescriptor editRoomTypeDescriptor) {
        requireNonNull(editRoomTypeDescriptor);
        requireNonNull(index);

        this.editRoomTypeDescriptor = editRoomTypeDescriptor;
        this.index = index;
    }

    /**
     * Creates and returns a {@code RoomType} with the details of {@code roomTypeToEdit}
     * edited with {@code editRoomTypeDescriptor}.
     */
    private static RoomType createEditedRoomType(RoomType roomTypeToEdit,
                                                 EditRoomTypeCommand.EditRoomTypeDescriptor editRoomTypeDescriptor) {
        assert roomTypeToEdit != null;

        String updatedName = editRoomTypeDescriptor.getName().orElse(roomTypeToEdit.getName());
        int updatedNumberOfRooms = editRoomTypeDescriptor.getNumberOfRooms().orElse(roomTypeToEdit.getNumberOfRooms());
        double updatedRate = editRoomTypeDescriptor.getRatePerDay().orElse(roomTypeToEdit.getRatePerDay());

        return new RoomType(updatedNumberOfRooms, updatedName, updatedRate);
    }

    @Override
    public CommandResult execute(ReservationModel model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<RoomType> lastShownList = model.getRoomTypeList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ROOM_TYPE_DISPLAYED_INDEX);
        }

        RoomType roomTypeToEdit = lastShownList.get(index.getZeroBased());
        RoomType editedRoomType = createEditedRoomType(roomTypeToEdit, editRoomTypeDescriptor);

        if (!roomTypeToEdit.equals(editedRoomType) && model.hasRoomType(editedRoomType)) {
            throw new CommandException(MESSAGE_DUPLICATE_ROOM_TYPE);
        }

        model.setRoomType(index.getZeroBased(), editedRoomType);
        model.commitHotelManagementSystem();
        return new CommandResult(String.format(MESSAGE_EDIT_ROOM_TYPE_SUCCESS, editedRoomType));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditRoomTypeCommand)) {
            return false;
        }

        // state check
        EditRoomTypeCommand e = (EditRoomTypeCommand) other;
        return index.equals(e.index)
            && editRoomTypeDescriptor.equals(e.editRoomTypeDescriptor);
    }

    /**
     * Stores the details to edit the roomType with. Each non-empty field value will replace the
     * corresponding field value of the roomType.
     */
    public static class EditRoomTypeDescriptor {
        private String name;
        private TimeRange timing;
        private Integer numberOfRooms;
        private Double ratePerDay;

        public EditRoomTypeDescriptor() {
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditRoomTypeDescriptor(EditRoomTypeCommand.EditRoomTypeDescriptor toCopy) {
            setName(toCopy.name);
            setTiming(toCopy.timing);
            setNumberOfRooms(toCopy.numberOfRooms);
            setRatePerDay(toCopy.ratePerDay);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, timing, numberOfRooms, ratePerDay);
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

        public Optional<Integer> getNumberOfRooms() {
            return Optional.ofNullable(numberOfRooms);
        }

        public void setNumberOfRooms(int numberOfRooms) {
            this.numberOfRooms = numberOfRooms;
        }

        public Optional<Double> getRatePerDay() {
            return Optional.ofNullable(ratePerDay);
        }

        public void setRatePerDay(double rph) {
            this.ratePerDay = rph;
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditRoomTypeCommand.EditRoomTypeDescriptor)) {
                return false;
            }

            // state check
            EditRoomTypeCommand.EditRoomTypeDescriptor e = (EditRoomTypeCommand.EditRoomTypeDescriptor) other;

            return getName().equals(e.getName())
                && getTiming().equals(e.getTiming())
                && getNumberOfRooms().equals(e.getNumberOfRooms())
                && getRatePerDay().equals(e.getRatePerDay());
        }
    }
}
