package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SPECIALISATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_YEAR;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_DOCTORS;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.person.DoctorContainsKeywordsPredicate;

/**
 * Lists all doctors in the docX to the user.
 */
public class ListDoctorCommand extends Command {

    public static final String COMMAND_WORD = "list-doctor";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists the doctors who match the user inputs "
            + "Parameters: "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_GENDER + "GENDER] "
            + "[" + PREFIX_YEAR + "YEAR] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_SPECIALISATION + "SPECIALISATION]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 ";

    public static final String MESSAGE_SUCCESS = "Listed all doctors";

    private final DoctorContainsKeywordsPredicate predicate;

    public ListDoctorCommand() {
        this.predicate = null;
    }

    public ListDoctorCommand(DoctorContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        if (predicate == null) {
            model.updateFilteredDoctorList(PREDICATE_SHOW_ALL_DOCTORS);
        } else {
            model.updateFilteredDoctorList(predicate);
        }
        return new CommandResult(
                String.format(Messages.MESSAGE_DOCTORS_LISTED_OVERVIEW, model.getFilteredDoctorList().size()));
    }
}
