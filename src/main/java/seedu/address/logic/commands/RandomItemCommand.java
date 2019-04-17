package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collections;

import javafx.collections.ObservableList;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.apparel.Apparel;
import seedu.address.model.apparel.ClothingTypeValue;

/**
 * Returns random item of selected type from address book.
 */
// @@author PhilipPhil
public class RandomItemCommand extends Command {


    public static final String COMMAND_WORD = "random";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Returns a random apparel item."
            + "Parameters: TYPE\n"
            + "Example: " + COMMAND_WORD + " " + "TOP\n"
            + "Example: " + COMMAND_WORD + " " + "BOTTOM\n"
            + "Example: " + COMMAND_WORD + " " + "BELT\n"
            + "Example: " + COMMAND_WORD + " " + "SHOES\n";

    public static final String MESSAGE_RANDOM_NOT_FOUND = "could not find a random apparel item";
    public static final String MESSAGE_MESSAGE_SUCCESS = "random apparel found";


    private final ClothingTypeValue targetType;

    public RandomItemCommand(ClothingTypeValue targetType) {
        this.targetType = targetType;
    }
    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);

        ArrayList<Apparel> randomApperals = shuffleApperals(model.getFilteredApparelList());

        if (randomApperals.size() <= 0) {
            return new CommandResult(MESSAGE_RANDOM_NOT_FOUND);
        }

        return new CommandResult(MESSAGE_MESSAGE_SUCCESS + "\n" + randomApperals.get(0).toString());
    }
    /**
     * Returns a list of Apparel that is in random order and only contains targetType.
     */
    private ArrayList<Apparel> shuffleApperals(ObservableList<Apparel> filteredApparelList) {
        ArrayList<Apparel> randomApparels = new ArrayList<>();
        for (int i = 0; i < filteredApparelList.size(); i++) {
            Apparel currApparel = filteredApparelList.get(i);
            if (currApparel.getClothingType().getClothingTypeValue().equals(targetType)) {
                randomApparels.add(currApparel);
            }
        }
        Collections.shuffle(randomApparels);
        return randomApparels;
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this // short circuit if same object
                || (obj instanceof RandomItemCommand // instanceof handles nulls
                && targetType.equals(((RandomItemCommand) obj).targetType));
    }
}
