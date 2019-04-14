package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLOTHING_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COLOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_APPARELS;

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
import seedu.address.model.apparel.Address;
import seedu.address.model.apparel.Apparel;
import seedu.address.model.apparel.ClothingType;
import seedu.address.model.apparel.Color;
import seedu.address.model.apparel.Name;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing apparel in the address book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the apparel identified "
            + "by the index number used in the displayed apparel list. "
            + "To edit availability use dirty/unavailable and wash/available commands. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_COLOR + "COLOR] "
            + "[" + PREFIX_CLOTHING_TYPE + "TYPE] \n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_COLOR + "RED "
            + PREFIX_CLOTHING_TYPE + "BOTTOM";

    public static final String MESSAGE_EDIT_APPAREL_SUCCESS = "Edited Apparel: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_APPAREL = "This apparel already exists in the address book.";

    private final Index index;
    private final EditPersonDescriptor editPersonDescriptor;

    /**
     * @param index of the apparel in the filtered apparel list to edit
     * @param editPersonDescriptor details to edit the apparel with
     */
    public EditCommand(Index index, EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(index);
        requireNonNull(editPersonDescriptor);

        this.index = index;
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Apparel> lastShownList = model.getFilteredApparelList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_APPAREL_DISPLAYED_INDEX);
        }

        Apparel apparelToEdit = lastShownList.get(index.getZeroBased());
        Apparel editedApparel = createEditedPerson(apparelToEdit, editPersonDescriptor);

        if (!apparelToEdit.isSameApparel(editedApparel) && model.hasApparel(editedApparel)) {
            throw new CommandException(MESSAGE_DUPLICATE_APPAREL);
        }

        model.setPerson(apparelToEdit, editedApparel);
        model.updateFilteredApparelList(PREDICATE_SHOW_ALL_APPARELS);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_EDIT_APPAREL_SUCCESS, editedApparel));
    }

    /**
     * Creates and returns a {@code Apparel} with the details of {@code apparelToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Apparel createEditedPerson(Apparel apparelToEdit, EditPersonDescriptor editPersonDescriptor) {
        assert apparelToEdit != null;

        Name updatedName = editPersonDescriptor.getName().orElse(apparelToEdit.getName());
        Color updatedColor = editPersonDescriptor.getColor().orElse(apparelToEdit.getColor());
        ClothingType updatedClothingType =
                editPersonDescriptor.getClothingType().orElse(apparelToEdit.getClothingType());

        return new Apparel(updatedName, updatedColor, updatedClothingType);
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
                && editPersonDescriptor.equals(e.editPersonDescriptor);
    }

    /**
     * Stores the details to edit the apparel with. Each non-empty field value will replace the
     * corresponding field value of the apparel.
     */
    public static class EditPersonDescriptor {
        private Name name;
        private Color color;
        private ClothingType clothingType;
        private Address address;
        private Set<Tag> tags;

        public EditPersonDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
            setName(toCopy.name);
            setColor(toCopy.color);
            setClothingType(toCopy.clothingType);
            setAddress(toCopy.address);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, color, clothingType, address, tags);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setColor(Color color) {
            this.color = color;
        }

        public Optional<Color> getColor() {
            return Optional.ofNullable(color);
        }

        public void setClothingType(ClothingType clothingType) {
            this.clothingType = clothingType;
        }

        public Optional<ClothingType> getClothingType() {
            return Optional.ofNullable(clothingType);
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
            if (!(other instanceof EditPersonDescriptor)) {
                return false;
            }

            // state check
            EditPersonDescriptor e = (EditPersonDescriptor) other;

            return getName().equals(e.getName())
                    && getColor().equals(e.getColor())
                    && getClothingType().equals(e.getClothingType())
                    && getAddress().equals(e.getAddress())
                    && getTags().equals(e.getTags());
        }
    }
}
