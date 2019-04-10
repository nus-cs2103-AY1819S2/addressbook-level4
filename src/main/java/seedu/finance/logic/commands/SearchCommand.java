package seedu.finance.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.finance.logic.parser.CliSyntax.COMMAND_FLAG_CATEGORY;
import static seedu.finance.logic.parser.CliSyntax.COMMAND_FLAG_DATE;
import static seedu.finance.logic.parser.CliSyntax.COMMAND_FLAG_NAME;

import java.util.Iterator;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.finance.commons.core.Messages;
import seedu.finance.logic.CommandHistory;
import seedu.finance.model.Model;
import seedu.finance.model.record.CategoryContainsKeywordsPredicate;
import seedu.finance.model.record.DateContainsKeywordsPredicate;
import seedu.finance.model.record.NameContainsKeywordsPredicate;
import seedu.finance.model.record.Record;

/**
 * Finds and lists all records in finance tracker whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class SearchCommand extends Command {

    public static final String COMMAND_WORD = "search";
    public static final String COMMAND_ALIAS = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all records which contain any of "
            + "the specified keywords (case-insensitive) based on the selected flag "
            + "and displays them as a list with index numbers.\n"
            + "Parameters: FLAG KEYWORD [MORE_KEYWORDS] ...\n"
            + "Flags: \n"
            + COMMAND_FLAG_NAME + ": Search based on names\n"
            + COMMAND_FLAG_DATE + ": Search based on dates\n"
            + COMMAND_FLAG_CATEGORY + ": Search based on categories\n"
            + "Example: " + COMMAND_WORD + " " + COMMAND_FLAG_NAME + " fries chicken bus fare";

    public static final String INVALID_FLAG = "Flag not recognised. Valid flags:\n"
            + COMMAND_FLAG_NAME + ": Find all records that contain specified keywords in name\n"
            + COMMAND_FLAG_DATE + ": Find all records that contain specified dates.\n"
            + COMMAND_FLAG_CATEGORY + ": Find all records with specified keywords in category.\n"
            + "Example: " + COMMAND_WORD + " " + COMMAND_FLAG_NAME + " fries chicken bus fare";

    public static final String ONE_FLAG_ONLY = "Only one flag should be used.";

    public static final String NO_FLAG = "Please input at least one flag. Valid flags:\n"
            + COMMAND_FLAG_NAME + ": Find all records that contain specified keywords in name\n"
            + COMMAND_FLAG_DATE + ": Find all records that contain specified dates.\n"
            + COMMAND_FLAG_CATEGORY + ": Find all records with specified keywords in category.\n"
            + "Example: " + COMMAND_WORD + " " + COMMAND_FLAG_NAME + " fries chicken bus fare";

    private final Predicate<Record> predicate;

    public SearchCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    public SearchCommand(CategoryContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    public SearchCommand(DateContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredRecordList(predicate);
        ObservableList<Record> filteredRecord = model.getFilteredRecordList();
        Double totalSpent = Double.valueOf(0);
        Iterator<Record> recordIterator = filteredRecord.iterator();
        while (recordIterator.hasNext()) {
            totalSpent += recordIterator.next().getAmount().getValue();
        }
        String outputMessage = Messages.MESSAGE_RECORDS_LISTED_OVERVIEW + "\nTotal spent on searched records = $ "
                + String.format("%.2f", totalSpent);

        return new CommandResult(
                String.format(outputMessage, model.getFilteredRecordList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SearchCommand // instanceof handles nulls
                && predicate.equals(((SearchCommand) other).predicate)); // state check
    }
}
