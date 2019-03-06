package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.person.Pdf;

import java.util.Comparator;

/**
 * Sorts all PDF files in alphabetical order.
 */
public class SortCommand extends Command {
    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sorts all the pdfs based on sorting keywords.\n"
            + "Parameters: up (for ascending order), down (for descending order)\n"
            + "Example: " + COMMAND_WORD + " up";

    public static final String MESSAGE_SUCCESS = "Sort success!";

    private final Comparator<Pdf> pdfComparator;

    public SortCommand(Comparator<Pdf> cm){
        this.pdfComparator = cm;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        return new CommandResult(MESSAGE_SUCCESS, false, false);
    }

}
