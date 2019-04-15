package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.options.SortOption;
import seedu.address.model.Model;
import seedu.address.model.apparel.Apparel;

/**
 * Edits the details of an existing apparel in the address book.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";
    public static final String COMMAND_LIST_OPTIONS = "options";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sort the apparels based on the given option.\n"
            + "Example: sort name\n"
            + "Outcome: apparel list sorted in ascending order of the name.\n"
            + "To sort in descending order, add a optional parameter called 'desc'\n"
            + "Example: sort name desc\n"
            + "Outcome: apparel list sorted in descending order of the name.\n";


    public static final String MESSAGE_SORT_APPAREL_SUCCESS = "Apparels sorted";
    public static final String MESSAGE_INVALID_OPTION = "Invalid option: Type `sort see options` "
            + "to see valid options.\n";

    private final SortOption sortOption;
    private boolean sortInDescendingOrder = false;

    /**
     * @param sortOption the sorting option
     */
    public SortCommand(SortOption sortOption) {
        requireNonNull(sortOption);

        this.sortOption = sortOption;
    }

    /**
     * @param sortOption the sorting option
     */
    public SortCommand(SortOption sortOption, boolean sortInDescendingOrder) {
        this(sortOption);
        this.sortInDescendingOrder = sortInDescendingOrder;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Apparel> lastShownList = model.getFilteredApparelList();
        List<Apparel> lastShownListUntouch = new ArrayList<>(lastShownList);
        List<Apparel> modifiableList = new ArrayList<Apparel>(lastShownList);

        StringBuilder sb = new StringBuilder(MESSAGE_SORT_APPAREL_SUCCESS);
        sb.append(" by " + sortOption.toString().toLowerCase() + ".");
        if (COMMAND_LIST_OPTIONS.equalsIgnoreCase(sortOption.toString())) {
            return new CommandResult(SortOption.allOptions());
        }
        if (sortInDescendingOrder) {
            if (SortOption.NAME.equals(sortOption)) {
                modifiableList.sort((Apparel x, Apparel y) -> y.getName().compareTo(x.getName()));
            } else if (SortOption.COLOR.equals(sortOption)) {
                modifiableList.sort((Apparel x, Apparel y) -> y.getColor().compareTo(x.getColor()));
            } else if (SortOption.TYPE.equals(sortOption)) {
                modifiableList.sort((Apparel x, Apparel y) -> y.getClothingType().toString()
                        .compareTo(x.getClothingType().toString()));
            }
        } else {
            if (SortOption.NAME.equals(sortOption)) {
                modifiableList.sort((Apparel x, Apparel y) -> x.getName().compareTo(y.getName()));
            } else if (SortOption.COLOR.equals(sortOption)) {
                modifiableList.sort((Apparel x, Apparel y) -> x.getColor().compareTo(y.getColor()));
            } else if (SortOption.TYPE.equals(sortOption)) {
                modifiableList.sort((Apparel x, Apparel y) -> x.getClothingType().toString()
                        .compareTo(y.getClothingType().toString()));
            }
        }

        for (Apparel apparelToDelete: lastShownListUntouch) {
            model.deleteApparel(apparelToDelete);
        }
        for (Apparel apparelToAdd: modifiableList) {
            model.addApparel(apparelToAdd);
        }


        model.commitAddressBook();
        return new CommandResult(String.format(sb.toString()));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SortCommand)) {
            return false;
        }

        // state check
        SortCommand e = (SortCommand) other;
        return sortOption.equals(e.sortOption);
    }
}
