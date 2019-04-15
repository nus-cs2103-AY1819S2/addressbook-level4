package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.apparel.Apparel;




/**
 * Edits the details of an existing apparel in the address book.
 */
public class SwapCommand extends Command {

    public static final String COMMAND_WORD = "swap";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + " [indexX] [indexY]: Swap the position between apparel at indexX and apparel at indexY.\n"
            + "Example: swap 2 5\n"
            + "Outcome: index 2 and 5 apparels swapped position.\n";


    public static final String MESSAGE_SWAP_APPAREL_SUCCESS = "Apparels swapped";
    public static final String MESSAGE_FAILURE_INDEX_OUT_OF_BOUND = "Index out of bound. Please choose a valid index.";

    private final Index first;
    private final Index second;

    /**
     * @param first the index of the first item to swap.
     * @param second the index of the second item to swap.
     */
    public SwapCommand(Index first, Index second) {
        requireNonNull(first);
        requireNonNull(second);

        this.first = first;
        this.second = second;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Apparel> lastShownList = model.getFilteredApparelList();
        List<Apparel> lastShownListUntouch = new ArrayList<>(lastShownList);
        List<Apparel> modifiableList = new ArrayList<Apparel>(lastShownList);

        try {
            Apparel firstApparel = modifiableList.get(first.getZeroBased());
            Apparel secondApparel = modifiableList.get(second.getZeroBased());
            modifiableList.set(first.getZeroBased(), secondApparel);
            modifiableList.set(second.getZeroBased(), firstApparel);

            for (Apparel apparelToDelete : lastShownListUntouch) {
                model.deleteApparel(apparelToDelete);
            }
            for (Apparel apparelToAdd : modifiableList) {
                model.addApparel(apparelToAdd);
            }
        } catch (IndexOutOfBoundsException e) {
            throw new CommandException(MESSAGE_FAILURE_INDEX_OUT_OF_BOUND);
        }


        model.commitAddressBook();
        return new CommandResult(MESSAGE_SWAP_APPAREL_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SwapCommand)) {
            return false;
        }

        // state check
        SwapCommand e = (SwapCommand) other;
        return first.equals(e.first) && second.equals(e.second);
    }
}
