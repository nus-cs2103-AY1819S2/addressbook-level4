package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OPENING_HOURS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POSTAL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEBLINK;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_RESTAURANTS;

import java.util.ArrayList;
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
import seedu.address.model.restaurant.Address;
import seedu.address.model.restaurant.Email;
import seedu.address.model.restaurant.Name;
import seedu.address.model.restaurant.OpeningHours;
import seedu.address.model.restaurant.Phone;
import seedu.address.model.restaurant.Postal;
import seedu.address.model.restaurant.Restaurant;
import seedu.address.model.restaurant.Weblink;
import seedu.address.model.restaurant.categories.Categories;
import seedu.address.model.review.Review;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing restaurant in the food diary.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the restaurant identified "
            + "by the index number used in the displayed restaurant list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_POSTAL + "POSTAL] "
            + "[" + PREFIX_TAG + "TAG]... "
            + "[" + PREFIX_WEBLINK + "WEBLINK] "
            + "[" + PREFIX_OPENING_HOURS + "OPENING HOURS]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_EDIT_RESTAURANT_SUCCESS = "Edited Restaurant: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_RESTAURANT = "This restaurant already exists in the food diary.";

    private final Index index;
    private final EditRestaurantDescriptor editRestaurantDescriptor;
    private String commandMessage;

    /**
     * @param index of the restaurant in the filtered restaurant list to edit
     * @param editRestaurantDescriptor details to edit the restaurant with
     */
    public EditCommand(Index index, EditRestaurantDescriptor editRestaurantDescriptor) {
        this(index, editRestaurantDescriptor, MESSAGE_EDIT_RESTAURANT_SUCCESS);
    }

    public EditCommand(Index index, EditRestaurantDescriptor editRestaurantDescriptor, String commandMessage) {
        requireNonNull(index);
        requireNonNull(editRestaurantDescriptor);

        this.index = index;
        this.editRestaurantDescriptor = new EditRestaurantDescriptor(editRestaurantDescriptor);
        this.commandMessage = commandMessage;
        if (!commandMessage.equals(MESSAGE_EDIT_RESTAURANT_SUCCESS)) {
            this.commandMessage = commandMessage.concat(MESSAGE_EDIT_RESTAURANT_SUCCESS);
        }
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Restaurant> lastShownList = model.getFilteredRestaurantList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_RESTAURANT_DISPLAYED_INDEX);
        }

        Restaurant restaurantToEdit = lastShownList.get(index.getZeroBased());
        Restaurant editedRestaurant = createEditedRestaurant(restaurantToEdit, editRestaurantDescriptor);

        if (!restaurantToEdit.isSameRestaurant(editedRestaurant) && model.hasRestaurant(editedRestaurant)) {
            throw new CommandException(MESSAGE_DUPLICATE_RESTAURANT);
        }

        model.setRestaurant(restaurantToEdit, editedRestaurant);
        model.updateFilteredRestaurantList(PREDICATE_SHOW_ALL_RESTAURANTS);
        model.commitFoodDiary();
        return new CommandResult(String.format(commandMessage, editedRestaurant));
    }

    /**
     * Creates and returns a {@code Restaurant} with the details of {@code restaurantToEdit}
     * edited with {@code editRestaurantDescriptor}.
     */
    private static Restaurant createEditedRestaurant(Restaurant restaurantToEdit,
                                                     EditRestaurantDescriptor editRestaurantDescriptor) {
        assert restaurantToEdit != null;

        Name updatedName = editRestaurantDescriptor.getName().orElse(restaurantToEdit.getName());
        Phone updatedPhone = editRestaurantDescriptor.getPhone().orElse(restaurantToEdit.getPhone());
        Email updatedEmail = editRestaurantDescriptor.getEmail().orElse(restaurantToEdit.getEmail());
        Address updatedAddress = editRestaurantDescriptor.getAddress().orElse(restaurantToEdit.getAddress());
        Postal updatedPostal = editRestaurantDescriptor.getPostal().orElse(restaurantToEdit.getPostal());
        Set<Tag> updatedTags = editRestaurantDescriptor.getTags().orElse(restaurantToEdit.getTags());
        Weblink updatedWeblink = editRestaurantDescriptor.getWeblink().orElse(restaurantToEdit.getWeblink());
        OpeningHours updatedOpeninghours = editRestaurantDescriptor.getOpeningHours()
                .orElse(restaurantToEdit.getOpeningHours());

        Categories updatedCategories = restaurantToEdit.getCategories();

        //Ensures that reviews are copied over exactly, because they are not modified by this command.
        ArrayList<Review> sameReviews = new ArrayList<>();
        sameReviews.addAll(restaurantToEdit.getReviews());

        return new Restaurant(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedPostal, updatedTags,
                updatedWeblink, updatedOpeninghours, updatedCategories, sameReviews);
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
                && editRestaurantDescriptor.equals(e.editRestaurantDescriptor);
    }

    /**
     * Stores the details to edit the restaurant with. Each non-empty field value will replace the
     * corresponding field value of the restaurant.
     */
    public static class EditRestaurantDescriptor {
        private Name name;
        private Phone phone;
        private Email email;
        private Address address;
        private Postal postal;
        private Set<Tag> tags;
        private Weblink weblink;
        private OpeningHours openingHours;

        public EditRestaurantDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditRestaurantDescriptor(EditRestaurantDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setAddress(toCopy.address);
            setPostal(toCopy.postal);
            setTags(toCopy.tags);
            setWeblink(toCopy.weblink);
            setOpeningHours(toCopy.openingHours);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, email, address, postal, tags, weblink, openingHours);
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

        public void setPostal(Postal postal) {
            this.postal = postal;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        public Optional<Postal> getPostal() {
            return Optional.ofNullable(postal);
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

        public void setWeblink(Weblink weblink) {
            this.weblink = weblink;
        }

        public Optional<Weblink> getWeblink() {
            return Optional.ofNullable(weblink);
        }

        public void setOpeningHours(OpeningHours openingHours) {
            this.openingHours = openingHours;
        }

        public Optional<OpeningHours> getOpeningHours() {
            return Optional.ofNullable(openingHours);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditRestaurantDescriptor)) {
                return false;
            }

            // state check
            EditRestaurantDescriptor e = (EditRestaurantDescriptor) other;
            return getName().equals(e.getName())
                    && getPhone().equals(e.getPhone())
                    && getEmail().equals(e.getEmail())
                    && getAddress().equals(e.getAddress())
                    && getPostal().equals(e.getPostal())
                    && getTags().equals(e.getTags())
                    && getWeblink().equals(e.getWeblink())
                    && getOpeningHours().equals(e.getOpeningHours());
        }

    }
}
