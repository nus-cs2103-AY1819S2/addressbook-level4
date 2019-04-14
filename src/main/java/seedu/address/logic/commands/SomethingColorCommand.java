package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collections;

import javafx.collections.ObservableList;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.apparel.Apparel;
import seedu.address.model.apparel.ColorValue;

/**
 * Returns some item of selected color from address book.
 */
// @@author PhilipPhil
public class SomethingColorCommand extends Command {
    public static final String COMMAND_WORD = "something";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Randomly returns one item of selected color"
            + "Parameters: COLOR\n"
            + "Example: " + COMMAND_WORD + " " + "RED\n"
            + "Example: " + COMMAND_WORD + " " + "BLACK\n";


    public static final String MESSAGE_MESSAGE_SUCCESS = "Item of chosen color found";
    public static final String MESSAGE_RANDOM_NOT_FOUND = "Item of chosen color not found";


    private final ColorValue targetColor;

    public SomethingColorCommand(ColorValue targetColor) {
        this.targetColor = targetColor;
    }
    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);

        ArrayList<Apparel> randomApparels = shuffleApperals(model.getFilteredApparelList());
        if (randomApparels.size() <= 0) {
            return new CommandResult(MESSAGE_RANDOM_NOT_FOUND);
        }

        return new CommandResult(MESSAGE_MESSAGE_SUCCESS + "\n" + randomApparels.get(0).toString());
    }
    /**
     * Returns a list of Apparel that is in random order and only contains targetColor.
     */
    private ArrayList<Apparel> shuffleApperals(ObservableList<Apparel> filteredApparelList) {
        ArrayList<Apparel> colorApperals = new ArrayList<>();
        for (int i = 0; i < filteredApparelList.size(); i++) {
            Apparel currApparel = filteredApparelList.get(i);
            if (currApparel.getColor().getPrimary().equals(targetColor)) {
                colorApperals.add(currApparel);
            }
        }
        Collections.shuffle(colorApperals);
        return colorApperals;
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this // short circuit if same object
                || (obj instanceof SomethingColorCommand // instanceof handles nulls
                && targetColor.equals(((SomethingColorCommand) obj).targetColor));
    }
}
