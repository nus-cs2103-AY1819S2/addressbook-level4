package seedu.address.logic.commands.request;

import seedu.address.commons.core.index.Index;

import static seedu.address.logic.parser.CliSyntax.*;

/**
 * Edits an order in the request book.
 */
public class EditRequestCommand {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = RequestCommand.COMMAND_WORD + " " + COMMAND_WORD
        + ": Edits the details of the order identified "
        + "by the index number used in the displayed request book. "
        + "Existing values will be overwritten by the input values.\n"
        + "Parameters: INDEX (must be a positive integer) "
        + "[" + PREFIX_NAME + "NAME] "
        + "[" + PREFIX_PHONE + "PHONE] "
        + "[" + PREFIX_ADDRESS + "ADDRESS] "
        + "[" + PREFIX_DATE + "DATE] "
        + "[" + PREFIX_CONDITION + "CONDITION]...\n"
        + "Example: " + RequestCommand.COMMAND_WORD + " " + COMMAND_WORD + " 1 "
        + PREFIX_PHONE + "91234567 "
        + PREFIX_CONDITION + "Physiotherapy "
        + PREFIX_CONDITION + "Dialysis";

    public static final String MESSAGE_EDIT_REQUEST_SUCCESS = "Edited Order: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_ORDER = "This request already exists in the " +
        "request book.";

//    private final Index index;
//    private final EditRequestDescriptor editRequestDescriptor;
}
