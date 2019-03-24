package seedu.travel.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.travel.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.travel.logic.parser.CliSyntax.PREFIX_COUNTRY_CODE;
import static seedu.travel.logic.parser.CliSyntax.PREFIX_DATE_VISITED;
import static seedu.travel.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.travel.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.travel.logic.parser.CliSyntax.PREFIX_RATING;
import static seedu.travel.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.travel.model.Model.PREDICATE_SHOW_ALL_PLACES;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.travel.commons.core.Messages;
import seedu.travel.commons.core.index.Index;
import seedu.travel.commons.util.CollectionUtil;
import seedu.travel.logic.CommandHistory;
import seedu.travel.logic.commands.exceptions.CommandException;
import seedu.travel.model.Model;
import seedu.travel.model.place.Address;
import seedu.travel.model.place.CountryCode;
import seedu.travel.model.place.DateVisited;
import seedu.travel.model.place.Description;
import seedu.travel.model.place.Name;
import seedu.travel.model.place.Place;
import seedu.travel.model.place.Rating;
import seedu.travel.model.tag.Tag;

/**
 * Edits the details of an existing place in the travel book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";
    public static final String COMMAND_ALIAS = "e";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the place identified "
            + "by the index number used in the displayed place list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_COUNTRY_CODE + "COUNTRY_CODE] "
            + "[" + PREFIX_DATE_VISITED + "DATE_VISITED] "
            + "[" + PREFIX_RATING + "RATING] "
            + "[" + PREFIX_DESCRIPTION + "DESCRIPTION] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_COUNTRY_CODE + "SGP "
            + PREFIX_DATE_VISITED + "23-03-2019 "
            + PREFIX_RATING + "4 "
            + PREFIX_DESCRIPTION + "No description";

    public static final String MESSAGE_EDIT_PLACE_SUCCESS = "Edited Place: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PLACE = "This place already exists in the travel book.";

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
            throw new CommandException(Messages.MESSAGE_INVALID_PLACE_DISPLAYED_INDEX);
        }

        Place placeToEdit = lastShownList.get(index.getZeroBased());
        Place editedPlace = createEditedPlace(placeToEdit, editPlaceDescriptor);

        if (!placeToEdit.isSamePlace(editedPlace) && model.hasPlace(editedPlace)) {
            throw new CommandException(MESSAGE_DUPLICATE_PLACE);
        }

        model.setPlace(placeToEdit, editedPlace);
        model.updateFilteredPlaceList(PREDICATE_SHOW_ALL_PLACES);
        model.commitTravelBuddy();
        return new CommandResult(String.format(MESSAGE_EDIT_PLACE_SUCCESS, editedPlace));
    }

    /**
     * Creates and returns a {@code Place} with the details of {@code placeToEdit}
     * edited with {@code editPlaceDescriptor}.
     */
    private static Place createEditedPlace(Place placeToEdit, EditPlaceDescriptor editPlaceDescriptor) {
        assert placeToEdit != null;

        Name updatedName = editPlaceDescriptor.getName().orElse(placeToEdit.getName());
        CountryCode updatedCountryCode = editPlaceDescriptor.getCountryCode().orElse(placeToEdit.getCountryCode());
        DateVisited updatedDateVisited = editPlaceDescriptor.getDateVisited().orElse(placeToEdit.getDateVisited());
        Rating updatedRating = editPlaceDescriptor.getRating().orElse(placeToEdit.getRating());
        Description updatedDescription = editPlaceDescriptor.getDescription().orElse(placeToEdit.getDescription());
        Address updatedAddress = editPlaceDescriptor.getAddress().orElse(placeToEdit.getAddress());
        Set<Tag> updatedTags = editPlaceDescriptor.getTags().orElse(placeToEdit.getTags());

        return new Place(updatedName, updatedCountryCode, updatedDateVisited, updatedRating, updatedDescription,
            updatedAddress, updatedTags);
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
        private CountryCode countryCode;
        private DateVisited dateVisited;
        private Rating rating;
        private Description description;
        private Address address;
        private Set<Tag> tags;

        public EditPlaceDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditPlaceDescriptor(EditPlaceDescriptor toCopy) {
            setName(toCopy.name);
            setCountryCode(toCopy.countryCode);
            setDateVisited(toCopy.dateVisited);
            setRating(toCopy.rating);
            setDescription(toCopy.description);
            setAddress(toCopy.address);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, rating, description, address, tags);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setCountryCode(CountryCode countryCode) {
            this.countryCode = countryCode;
        }

        public Optional<CountryCode> getCountryCode() {
            return Optional.ofNullable(countryCode);
        }

        public void setDateVisited(DateVisited dateVisited) {
            this.dateVisited = dateVisited;
        }

        public Optional<DateVisited> getDateVisited() {
            return Optional.ofNullable(dateVisited);
        }

        public void setRating(Rating rating) {
            this.rating = rating;
        }

        public Optional<Rating> getRating() {
            return Optional.ofNullable(rating);
        }

        public void setDescription(Description description) {
            this.description = description;
        }

        public Optional<Description> getDescription() {
            return Optional.ofNullable(description);
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
                    && getCountryCode().equals(e.getCountryCode())
                    && getDateVisited().equals(e.getDateVisited())
                    && getRating().equals(e.getRating())
                    && getDescription().equals(e.getDescription())
                    && getAddress().equals(e.getAddress())
                    && getTags().equals(e.getTags());
        }
    }
}
