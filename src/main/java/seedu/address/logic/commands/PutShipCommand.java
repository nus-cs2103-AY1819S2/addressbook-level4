package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COORDINATES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.battleship.Battleship;
import seedu.address.model.battleship.Name;
import seedu.address.model.cell.Address;
import seedu.address.model.cell.Cell;
import seedu.address.model.cell.Coordinates;
import seedu.address.model.cell.Email;
import seedu.address.model.cell.Phone;
import seedu.address.model.tag.Tag;

/**
 * Puts ship in an existing cell on the map.
 */
public class PutShipCommand extends Command {

    public static final String COMMAND_WORD = "put";
    public static final String COMMAND_ALIAS = "p";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Puts ship in cell that is identified "
            + "by the row number provided by the user. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_COORDINATES + "COORDINATES]\n"
            + "Example: " + COMMAND_WORD
            + PREFIX_NAME + "Destroyer "
            + PREFIX_COORDINATES + "c1";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Put ship in cell: %1$s";
    public static final String MESSAGE_BATTLESHIP_PRESENT = "There is already a ship on the coordinate.";
    public static final String MESSAGE_BATTLESHIP_PRESENT_BODY_VERTICAL =
            "There is already a ship along the vertical coordinates";
    public static final String MESSAGE_BATTLESHIP_PRESENT_BODY_HORIZONTAL =
            "There is already a ship along the vertical coordinates";

    private final Coordinates coordinates;
    private final Battleship battleship;

    /**
     * @param coordinates of the cell in the filtered cell list to edit
     * @param battleship battleship to place in the cell
     */
    public PutShipCommand(Coordinates coordinates, Battleship battleship) {
        requireNonNull(coordinates);
        requireNonNull(battleship);

        this.coordinates = coordinates;
        this.battleship = battleship;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (!isHeadWithinBounds(model, coordinates)) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        } else if (!isBodyWithinBounds(model, coordinates, battleship)) {
            throw new CommandException(Messages.MESSAGE_BODY_LENGTH_TOO_LONG);
        } else if (!isVerticalClear(model, coordinates, battleship)) {
            throw new CommandException(MESSAGE_BATTLESHIP_PRESENT_BODY_VERTICAL);
        } else if (!isHorizontalClear(model, coordinates, battleship)) {
            throw new CommandException(MESSAGE_BATTLESHIP_PRESENT_BODY_HORIZONTAL);
        }

        Cell cellToEdit = model.getMapGrid().getCell(coordinates);

        if (cellToEdit.hasBattleShip()) {
            throw new CommandException(MESSAGE_BATTLESHIP_PRESENT);
        } else {
            try {
                putAlongHorizontal(model, coordinates, battleship);
            } catch (Exception e) {
                throw new CommandException(MESSAGE_BATTLESHIP_PRESENT_BODY_HORIZONTAL);
            }
        }

        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, cellToEdit));
    }

    /**
     * Checks if given coordinates falls within the MapGrid.
     * @return true or false whether the coordinates fall within the MapGrid
     */
    public static boolean isHeadWithinBounds(Model model, Coordinates coordinates) {
        Index rowIndex = coordinates.getRowIndex();
        Index colIndex = coordinates.getColIndex();

        if ((rowIndex.getZeroBased() > model.getMapSize())
                || colIndex.getZeroBased() > model.getMapSize()) {
            return false;
        }

        return true;
    }

    /**
     * Checks if the body is within bounds of the map grid.
     * @return boolean of whether body of battleship is within bounds of map grid.
     */
    public static boolean isBodyWithinBounds(Model model, Coordinates coordinates, Battleship battleship) {
        Index rowIndex = coordinates.getRowIndex();
        Index colIndex = coordinates.getColIndex();

        int length = battleship.getLength();

        if ((rowIndex.getZeroBased() + length > model.getMapSize())
                || colIndex.getZeroBased() + length > model.getMapSize()) {
            return false;
        }

        return true;
    }

    /**
     * Checks if vertical length is clear, i.e., there are no other battleship objects.
     * @return boolean of whether vertical length is clear.
     */
    public static boolean isVerticalClear(Model model, Coordinates coordinates, Battleship battleship) {
        Index rowIndex = coordinates.getRowIndex();
        Index colIndex = coordinates.getColIndex();

        int length = battleship.getLength();

        for (int i = 0; i < length; i++) {
            Cell cellToInspect = model.getMapGrid().getCell(rowIndex.getZeroBased() + i,
                    colIndex.getZeroBased());

            if (cellToInspect.hasBattleShip()) {
                return false;
            }
        }

        return true;
    }

    /**
     * Checks if horizontal length is clear, i.e., there are no other battleship objects.
     * @return boolean of whether horizontal length is clear.
     */
    public static boolean isHorizontalClear(Model model, Coordinates coordinates, Battleship battleship) {
        Index rowIndex = coordinates.getRowIndex();
        Index colIndex = coordinates.getColIndex();

        int length = battleship.getLength();

        for (int i = 0; i < length; i++) {
            Cell cellToInspect = model.getMapGrid().getCell(rowIndex.getZeroBased(),
                    colIndex.getZeroBased() + i);

            if (cellToInspect.hasBattleShip()) {
                return false;
            }
        }

        return true;
    }

    /**
     * Puts the *same* battleship object along vertical length.
     * Pre-conditions: there are NO existing battleships along the vertical length, else will throw
     * and exception.
     */
    public static void putAlongVertical(Model model, Coordinates coordinates, Battleship battleship)
            throws Exception {
        Index rowIndex = coordinates.getRowIndex();
        Index colIndex = coordinates.getColIndex();

        int length = battleship.getLength();

        for (int i = 0; i < length; i++) {
            Cell cellToInspect = model.getMapGrid().getCell(rowIndex.getZeroBased() + i,
                    colIndex.getZeroBased());

            if (cellToInspect.hasBattleShip()) {
                throw new Exception();
            } else {
                cellToInspect.putShip(battleship);
            }
        }
    }

    /**
     * Puts the *same* battleship object along horizontal length.
     * Pre-conditions: there are NO existing battleships along the horizontal length, else will throw
     * and exception.
     */
    public static void putAlongHorizontal(Model model, Coordinates coordinates, Battleship battleship)
            throws Exception {
        Index rowIndex = coordinates.getRowIndex();
        Index colIndex = coordinates.getColIndex();

        int length = battleship.getLength();

        for (int i = 0; i < length; i++) {
            Cell cellToInspect = model.getMapGrid().getCell(rowIndex.getZeroBased(),
                    colIndex.getZeroBased() + i);

            if (cellToInspect.hasBattleShip()) {
                throw new Exception();
            } else {
                cellToInspect.putShip(battleship);
            }
        }
    }

    /**
     * Creates and returns a {@code Cell} with the details of {@code cellToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Cell createEditedPerson(Cell cellToEdit, EditPersonDescriptor editPersonDescriptor) {
        assert cellToEdit != null;

        Name updatedName = editPersonDescriptor.getName().orElse(cellToEdit.getName());
        Phone updatedPhone = editPersonDescriptor.getPhone().orElse(cellToEdit.getPhone());
        Email updatedEmail = editPersonDescriptor.getEmail().orElse(cellToEdit.getEmail());
        Address updatedAddress = editPersonDescriptor.getAddress().orElse(cellToEdit.getAddress());
        Set<Tag> updatedTags = editPersonDescriptor.getTags().orElse(cellToEdit.getTags());

        Battleship battleship = new Battleship(updatedName, updatedTags);

        return new Cell(battleship);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PutShipCommand)) {
            return false;
        }

        // state check
        PutShipCommand e = (PutShipCommand) other;
        return battleship.equals(e.battleship)
                && coordinates.equals(e.coordinates);
    }

    /**
     * Stores the details to edit the cell with. Each non-empty field value will replace the
     * corresponding field value of the cell.
     */
    public static class EditPersonDescriptor {
        private Name name;
        private Phone phone;
        private Email email;
        private Address address;
        private Set<Tag> tags;
        private Battleship battleship;

        public EditPersonDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setAddress(toCopy.address);
            setTags(toCopy.tags);
            setBattleship(toCopy.battleship);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, email, address, tags);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        public void setBattleship(Battleship battleship) {
            this.battleship = battleship;
        }

        public Optional<Battleship> getBattleship() {
            return Optional.ofNullable(battleship);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditPersonDescriptor)) {
                return false;
            }

            // state check
            EditPersonDescriptor e = (EditPersonDescriptor) other;

            return getName().equals(e.getName())
                    && getPhone().equals(e.getPhone())
                    && getEmail().equals(e.getEmail())
                    && getAddress().equals(e.getAddress())
                    && getTags().equals(e.getTags());
        }
    }
}
