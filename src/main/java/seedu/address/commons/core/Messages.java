package seedu.address.commons.core;
/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_INVALID_PATH = "Invalid file path given.";
    public static final String MESSAGE_UNABLE_TO_READ_FILE = "Unable to read file.";
    public static final String MESSAGE_INVALID_FORMAT = "File is not a valid type or is hidden.";
    public static final String MESSAGE_INVALID_SIZE = "File is larger than the 10mb limit.";
    public static final String MESSAGE_DUPLICATE_FILE = "Duplicate file name exists in assets folder. "
            + "Try using another name.";
    public static final String MESSAGE_SAMPLE_IMPORT = "Import sample can only be called on an empty Album.";
    public static final String MESSAGE_FILE_NOT_FOUND = "File does not exist. "
            + "Use listfiles to view existing files.";
    public static final String MESSAGE_ROTATE_SUCCESS = "Rotated successfully.";
    public static final String MESSAGE_CONTRAST_SUCCESS = "Contrast filter applied successfully.";
    public static final String MESSAGE_BRIGHTNESS_SUCCESS = "Brightness filter applied successfully.";
    public static final String MESSAGE_BLACKWHITE_SUCCESS = "Black/White filter applied successfully.";
    public static final String MESSAGE_CONTRAST_DOUBLE_ERROR = "Value keyed in is not double. \n%1$s";
    public static final String MESSAGE_BRIGHTNESS_DOUBLE_ERROR = "Value keyed in is not double. E.g: brightness 1.3 "
        + "n\n%1$s";
    public static final String MESSAGE_BLACKWHITE_INT_ERROR = "Value keyed in is not integer. E.g: bw 123 \n%1$s";
    public static final String MESSAGE_NEGATIVE_ERROR = "Value keyed in is not positive. \n%1$s";
    public static final String MESSAGE_ROTATE_DEGREE_ERROR = "Value keyed in is not a valid integer or rotating degree."
            + " \n%1$s";
    public static final String MESSAGE_CROP_INT_ERROR = "Values keyed in are not all integers. \n%1$s";
    public static final String MESSAGE_CROP_SUCCESS = "Cropped successfully.";
    public static final String MESSAGE_DID_NOT_OPEN = "Did not open any image to edit on.";
    public static final String MESSAGE_UNABLE_TO_SAVE = "Unable to save. Please open an image before saving.";
    public static final String MESSAGE_RESIZE_SUCCESS = "Resized successfully.";
    public static final String MESSAGE_RESIZE_VALUE_ERROR = "Values keyed in are not positive integers. "
            + "\n%1$s";
    public static final String MESSAGE_EXPORT_SUCCESS = "Image successfully exported.";
    public static final String MESSAGE_SAVEPRESET_SUCCESS = "Preset is successfully saved:";
    public static final String MESSAGE_SAVEPRESET_FAIL_DUPLICATE = "Duplicate preset name is found."
        + " Please use another preset name.";
    public static final String MESSAGE_SAVEPRESET_FAIL_EMPTY = "List of command in this preset is empty.";
    public static final String MESSAGE_SETPRESET_FAIL_NOTFOUND = "Preset is not found.";
    public static final String MESSAGE_SETPRESET_SUCCESS = "Preset is successfully applied:";
    public static final String MESSAGE_INVALID_SAVE_TYPE = "Invalid save file type."
            + "\nNames must end with either of the following: %1$s";
    public static final String MESSAGE_INVALID_SAVE_NAME = "Unable to save. Save file name cannot be empty.";
    public static final String MESSAGE_CLEAR_SUCCESS = "Album has been cleared!";
    public static final String MESSAGE_WATERMARK_SUCCESS = "Watermark created.";
    public static final String MESSAGE_HAS_WATERMARK = "A watermark has been added to this image already.";
    public static final String MESSAGE_RESIZE_VALUES_TOO_LARGE = "The resultant image is too large.";
}
