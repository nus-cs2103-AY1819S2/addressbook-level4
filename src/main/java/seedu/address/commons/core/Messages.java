package seedu.address.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_RESTAURANT_DISPLAYED_INDEX = "The restaurant index provided is invalid";
    public static final String MESSAGE_INVALID_REVIEW_INDEX = "The review index provided is invalid";
    public static final String MESSAGE_INVALID_DEFAULT_REVIEW_INDEX = "The default review number provided is invalid";
    public static final String MESSAGE_NO_REVIEWS = "There are no reviews for this restaurant.";
    public static final String MESSAGE_RESTAURANTS_LISTED_OVERVIEW = "%1$d restaurants listed!";
    public static final String MESSAGE_NO_RESTAURANT_SELECTED = "No restaurant selected";
    public static final String MESSAGE_CHANGE_WEBLINK = "%1$s is not found. Please add or update weblink of selected "
            + "restaurant using edit command.";
    public static final String MESSAGE_ADD_NO_INTERNET = "There is no internet connection hence FoodDiary is "
            + "unable to verify your weblink. Restaurant has been added without the input weblink.\n";
    public static final String MESSAGE_EDIT_NO_INTERNET = "There is no internet connection hence FoodDiary is "
            + "unable to verify your weblink. Weblink is not edited.\n";
}
