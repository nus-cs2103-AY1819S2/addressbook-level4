package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.restaurant.Address;
import seedu.address.model.restaurant.Email;
import seedu.address.model.restaurant.Name;
import seedu.address.model.restaurant.OpeningHours;
import seedu.address.model.restaurant.Phone;
import seedu.address.model.restaurant.Restaurant;
import seedu.address.model.restaurant.Weblink;
import seedu.address.model.restaurant.categories.Cuisine;
import seedu.address.model.review.Review;
import seedu.address.model.tag.Tag;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OPENING_HOURS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEBLINK;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_RESTAURANTS;


/**
 * Edits the details of an existing review tagged to a restaurant in the Food Diary.
 */
public class EditReviewCommand extends Command {
    public static final String COMMAND_WORD = "editReview";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the restaurant identified "
            + "by the index number used in the displayed restaurant list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_TAG + "TAG]... "
            + "[" + PREFIX_WEBLINK + "WEBLINK] "
            + "[" + PREFIX_OPENING_HOURS + "OPENING HOURS]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_EDIT_RESTAURANT_SUCCESS = "Edited Restaurant: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_RESTAURANT = "This restaurant already exists in the food diary.";


    /**
     * @param index of the restaurant in the filtered restaurant list to edit
     * @param editRestaurantDescriptor details to edit the restaurant with
     */
    public EditReviewCommand() {
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        throw new CommandException("exception");
    }

    /**
     * Creates and returns a {@code Restaurant} with the details of {@code restaurantToEdit}
     * edited with {@code editRestaurantDescriptor}.
     */
    private static Restaurant createEditedRestaurant(Restaurant restaurantToEdit,
                                                     EditCommand.EditRestaurantDescriptor editRestaurantDescriptor) {
        assert restaurantToEdit != null;

        Name updatedName = editRestaurantDescriptor.getName().orElse(restaurantToEdit.getName());
        Phone updatedPhone = editRestaurantDescriptor.getPhone().orElse(restaurantToEdit.getPhone());
        Email updatedEmail = editRestaurantDescriptor.getEmail().orElse(restaurantToEdit.getEmail());
        Address updatedAddress = editRestaurantDescriptor.getAddress().orElse(restaurantToEdit.getAddress());
        Set<Tag> updatedTags = editRestaurantDescriptor.getTags().orElse(restaurantToEdit.getTags());
        Weblink updatedWeblink = editRestaurantDescriptor.getWeblink().orElse(restaurantToEdit.getWeblink());
        OpeningHours updatedOpeninghours = editRestaurantDescriptor.getOpeningHours()
                .orElse(restaurantToEdit.getOpeningHours());

        Optional<Cuisine> updatedCuisine = restaurantToEdit.getCuisine();

        //Ensures that reviews are copied over exactly, because they are not modified by this command.
        ArrayList<Review> sameReviews = new ArrayList<>();
        sameReviews.addAll(restaurantToEdit.getReviews());

        return new Restaurant(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedTags,
                updatedWeblink, updatedOpeninghours, updatedCuisine, sameReviews);
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

}
