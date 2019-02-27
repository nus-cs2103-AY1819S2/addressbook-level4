package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.place.Address;
import seedu.address.model.place.Email;
import seedu.address.model.place.Name;
import seedu.address.model.place.Phone;
import seedu.address.model.place.Place;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing place in the address book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";
    public static final String COMMAND_ALIAS = "e";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the place identified "
            + "by the index number used in the displayed place list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Place: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This place already exists in the address book.";

    private final Index index;
    private final EditPlaceDescriptor editPlaceDescriptor;

    /**
     * @param index of the place in the filtered place list to edit
     * @param editPlaceDescriptor details to edit the place with
     */
    public EditCommand(Index index, EditPlaceDescriptor editPlaceDescriptor) {
        requireNonNull(index);
        requireNonNull(editPlaceDescriptor);

        this.index = index;
        this.editPlaceDescriptor = new EditPlaceDescriptor(editPlaceDescriptor);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Place> lastShownList = model.getFilteredPlaceList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Place placeToEdit = lastShownList.get(index.getZeroBased());
        Place editedPlace = createEditedPlace(placeToEdit, editPlaceDescriptor);

        if (!placeToEdit.isSamePlace(editedPlace) && model.hasPlace(editedPlace)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.setPlace(placeToEdit, editedPlace);
        model.updateFilteredPlaceList(PREDICATE_SHOW_ALL_PERSONS);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, editedPlace));
    }

    /**
     * Creates and returns a {@code Place} with the details of {@code placeToEdit}
     * edited with {@code editPlaceDescriptor}.
     */
    private static Place createEditedPlace(Place placeToEdit, EditPlaceDescriptor editPlaceDescriptor) {
        assert placeToEdit != null;

        Name updatedName = editPlaceDescriptor.getName().orElse(placeToEdit.getName());
        Phone updatedPhone = editPlaceDescriptor.getPhone().orElse(placeToEdit.getPhone());
        Email updatedEmail = editPlaceDescriptor.getEmail().orElse(placeToEdit.getEmail());
        Address updatedAddress = editPlaceDescriptor.getAddress().orElse(placeToEdit.getAddress());
        Set<Tag> updatedTags = editPlaceDescriptor.getTags().orElse(placeToEdit.getTags());

        return new Place(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedTags);
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
                && editPlaceDescriptor.equals(e.editPlaceDescriptor);
    }

    /**
     * Stores the details to edit the place with. Each non-empty field value will replace the
     * corresponding field value of the place.
     */
    public static class EditPlaceDescriptor {
        private Name name;
        private Phone phone;
        private Email email;
        private Address address;
        private Set<Tag> tags;

        public EditPlaceDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditPlaceDescriptor(EditPlaceDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setAddress(toCopy.address);
            setTags(toCopy.tags);
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

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditPlaceDescriptor)) {
                return false;
            }

            // state check
            EditPlaceDescriptor e = (EditPlaceDescriptor) other;

            return getName().equals(e.getName())
                    && getPhone().equals(e.getPhone())
                    && getEmail().equals(e.getEmail())
                    && getAddress().equals(e.getAddress())
                    && getTags().equals(e.getTags());
        }
    }
}
