package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_APPARELS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
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
public class WashCommand extends Command {

    public static final String COMMAND_WORD = "available";
    public static final String ALTERNATE_COMMAND_WORD = "wash";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Resets the cleanliness of the apparel identified "
            + "by the index number used in the displayed apparel list. "
            + "Parameters: INDEX (must be a positive integer) "
            + "Example: " + COMMAND_WORD + " 1 or " + ALTERNATE_COMMAND_WORD + " 1";

    public static final String MESSAGE_CLEAN_APPAREL_SUCCESS = "Apparel %1$d. washed: \n%2$s";
    public static final String MESSAGE_APPAREL_ALREADY_CLEAN_OCD = "Apparel %1$d. already washed: \n%2$s";
    public static final String MESSAGE_DUPLICATE_APPAREL = "This apparel already exists in the address book.";

    private final Index index;

    /**
     * @param index of the apparel in the filtered apparel list to edit
     */
    public WashCommand(Index index) {
        requireNonNull(index);
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Apparel> lastShownList = model.getFilteredApparelList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_APPAREL_DISPLAYED_INDEX);
        }

        Apparel apparelToWash = lastShownList.get(index.getZeroBased());

        if (apparelToWash.isAvailable()) {
            throw new CommandException(String.format(MESSAGE_APPAREL_ALREADY_CLEAN_OCD,
                    index.getOneBased(), apparelToWash));
        }

        Apparel washedApparel = new Apparel(apparelToWash).setWashed();

        model.setPerson(apparelToWash, washedApparel);
        model.updateFilteredApparelList(PREDICATE_SHOW_ALL_APPARELS);
        model.commitAddressBook();

        return new CommandResult(String.format(MESSAGE_CLEAN_APPAREL_SUCCESS, index.getOneBased(),
                washedApparel));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof WashCommand)) {
            return false;
        }

        // state check
        WashCommand e = (WashCommand) other;
        return index.equals(e.index);
    }

    /**
     * Stores the details to edit the apparel with. Each non-empty field value will replace the
     * corresponding field value of the apparel.
     */
    public static class AvailablePersonDescriptor {
        private Name name;
        private Color color;
        private ClothingType clothingType;
        private Address address;
        private Set<Tag> tags;

        public AvailablePersonDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public AvailablePersonDescriptor(AvailablePersonDescriptor toCopy) {
            setName(toCopy.name);
            setColor(toCopy.color);
            setClothingType(toCopy.clothingType);
            setAddress(toCopy.address);
            setTags(toCopy.tags);
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
            if (!(other instanceof AvailablePersonDescriptor)) {
                return false;
            }

            // state check
            AvailablePersonDescriptor e = (AvailablePersonDescriptor) other;

            return getName().equals(e.getName())
                    && getColor().equals(e.getColor())
                    && getClothingType().equals(e.getClothingType())
                    && getAddress().equals(e.getAddress())
                    && getTags().equals(e.getTags());
        }
    }
}
