package seedu.hms.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_CAPACITY;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_RATE;

import seedu.hms.logic.CommandHistory;
import seedu.hms.logic.commands.exceptions.CommandException;
import seedu.hms.model.ReservationModel;
import seedu.hms.model.reservation.roomType.RoomType;

/**
 * Adds a roomType to the hms book.
 */
public class AddRoomTypeCommand extends ReservationCommand {

    public static final String COMMAND_ALIAS = "art";
    public static final String COMMAND_WORD = "add-room-type";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a room type to the hotel management system.\n"
        + "Parameters: "
        + PREFIX_NAME + "ROOM NAME "
        + PREFIX_CAPACITY + "NUMBER OF ROOMS "
        + PREFIX_RATE + "RATE PER DAY\n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_NAME + "SHARING ROOM "
        + PREFIX_CAPACITY + "200 "
        + PREFIX_RATE + "400.0 ";

    public static final String MESSAGE_SUCCESS = "New room type added: %1$s";
    public static final String MESSAGE_DUPLICATE_ROOM_TYPE = "This room type already exists in the hms book";

    private final RoomType toAdd;

    /**
     * Creates an AddRoomTypeCommand to add the specified {@code RoomType}
     */
    public AddRoomTypeCommand(RoomType roomType) {
        requireNonNull(roomType);
        toAdd = roomType;
    }

    @Override
    public CommandResult execute(ReservationModel model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        if (model.hasRoomType(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_ROOM_TYPE);
        }
        model.addRoomType(toAdd);
        model.commitHotelManagementSystem();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof AddRoomTypeCommand // instanceof handles nulls
            && toAdd.equals(((AddRoomTypeCommand) other).toAdd));
    }
}
