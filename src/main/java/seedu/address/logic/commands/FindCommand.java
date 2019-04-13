package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.function.Predicate;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.medicine.Medicine;

/**
 * Finds and lists all medicines in inventory whose data contains any of the argument keywords.
 * The field to search depends on the prefix entered. Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all medicines whose data contain any of the "
            + "specified keywords (case-insensitive). Select a field to search by entering the appropriate prefix.\n"
            + "Parameters: PREFIX KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_NAME + " Paracetamol Amoxicillin Ibuprofen\n"
            + "Possible prefixes: 'n/': Name, 'c/': Company, 't/': Tags, 'b/': Batch\n";

    private final Predicate<Medicine> predicate;

    /**
     * Creates a FindCommand to filter the medicine list with {@code predicate}
     */
    public FindCommand(Predicate<Medicine> predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredMedicineList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_MEDICINES_LISTED_OVERVIEW, model.getFilteredMedicineList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && predicate.equals(((FindCommand) other).predicate)); // state check
    }
}
