package seedu.equipment.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.equipment.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.equipment.commons.core.Messages;
import seedu.equipment.commons.core.index.Index;
import seedu.equipment.commons.util.CollectionUtil;
import seedu.equipment.logic.CommandHistory;
import seedu.equipment.logic.commands.exceptions.CommandException;
import seedu.equipment.logic.parser.CliSyntax;
import seedu.equipment.model.Model;
import seedu.equipment.model.equipment.Address;
import seedu.equipment.model.equipment.Email;
import seedu.equipment.model.equipment.Equipment;
import seedu.equipment.model.equipment.Name;
import seedu.equipment.model.equipment.Phone;
import seedu.equipment.model.equipment.SerialNumber;
import seedu.equipment.model.tag.Tag;

/**
 * Edits the details of an existing equipment in the equipment manager.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit-e";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the equipment identified "
            + "by the index number used in the displayed equipment list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + CliSyntax.PREFIX_NAME + "NAME] "
            + "[" + CliSyntax.PREFIX_PHONE + "PHONE] "
            + "[" + CliSyntax.PREFIX_EMAIL + "EMAIL] "
            + "[" + CliSyntax.PREFIX_ADDRESS + "ADDRESS] "
            + "[" + CliSyntax.PREFIX_SERIALNUMBER + "SERIAL NUMBER] "
            + "[" + CliSyntax.PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + CliSyntax.PREFIX_PHONE + "91234567 "
            + CliSyntax.PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Equipment: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_EQUIPMENT = "This equipment already exists in the Equipment Manager.";

    private final Index index;
    private final EditEquipmentDescriptor editEquipmentDescriptor;

    /**
     * @param index of the equipment in the filtered equipment list to edit
     * @param editEquipmentDescriptor details to edit the equipment with
     */
    public EditCommand(Index index, EditEquipmentDescriptor editEquipmentDescriptor) {
        requireNonNull(index);
        requireNonNull(editEquipmentDescriptor);

        this.index = index;
        this.editEquipmentDescriptor = new EditEquipmentDescriptor(editEquipmentDescriptor);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Equipment> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EQUIPMENT_DISPLAYED_INDEX);
        }

        Equipment equipmentToEdit = lastShownList.get(index.getZeroBased());
        Equipment editedEquipment = createEditedPerson(equipmentToEdit, editEquipmentDescriptor);

        if (!equipmentToEdit.isSameEquipment(editedEquipment) && model.hasEquipment(editedEquipment)) {
            throw new CommandException(MESSAGE_DUPLICATE_EQUIPMENT);
        }

        model.setEquipment(equipmentToEdit, editedEquipment);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.commitEquipmentManager();
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, editedEquipment));
    }

    /**
     * Creates and returns a {@code Equipment} with the details of {@code equipmentToEdit}
     * edited with {@code editEquipmentDescriptor}.
     */
    private static Equipment createEditedPerson(Equipment equipmentToEdit, EditEquipmentDescriptor
            editEquipmentDescriptor) {
        assert equipmentToEdit != null;

        Name updatedName = editEquipmentDescriptor.getName().orElse(equipmentToEdit.getName());
        Phone updatedPhone = editEquipmentDescriptor.getPhone().orElse(equipmentToEdit.getPhone());
        Email updatedEmail = editEquipmentDescriptor.getEmail().orElse(equipmentToEdit.getEmail());
        Address updatedAddress = editEquipmentDescriptor.getAddress().orElse(equipmentToEdit.getAddress());
        SerialNumber updatedSerialNumber = editEquipmentDescriptor.getSerialNumber()
                .orElse(equipmentToEdit.getSerialNumber());
        Set<Tag> updatedTags = editEquipmentDescriptor.getTags().orElse(equipmentToEdit.getTags());

        return new Equipment(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedSerialNumber, updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        // state check
        EditCommand e = (EditCommand) other;
        return index.equals(e.index)
                && editEquipmentDescriptor.equals(e.editEquipmentDescriptor);
    }

    /**
     * Stores the details to edit the equipment with. Each non-empty field value will replace the
     * corresponding field value of the equipment.
     */
    public static class EditEquipmentDescriptor {
        private Name name;
        private Phone phone;
        private Email email;
        private Address address;
        private SerialNumber serialNumber;
        private Set<Tag> tags;

        public EditEquipmentDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditEquipmentDescriptor(EditEquipmentDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setAddress(toCopy.address);
            setSerialNumber(toCopy.serialNumber);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, email, address, serialNumber, tags);
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

        public void setSerialNumber(SerialNumber serialNumber) {
            this.serialNumber = serialNumber;
        }

        public Optional<SerialNumber> getSerialNumber() {
            return Optional.ofNullable(serialNumber);
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

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditEquipmentDescriptor)) {
                return false;
            }

            // state check
            EditEquipmentDescriptor e = (EditEquipmentDescriptor) other;

            return getName().equals(e.getName())
                    && getPhone().equals(e.getPhone())
                    && getEmail().equals(e.getEmail())
                    && getAddress().equals(e.getAddress())
                    && getSerialNumber().equals(e.getSerialNumber())
                    && getTags().equals(e.getTags());
        }
    }
}
