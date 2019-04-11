package seedu.address.model;

import javafx.scene.layout.Region;
import seedu.address.logic.commands.Command;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.ui.UiPart;

/**
 * Stores the state of TopDeck.
 */
public interface ViewState {
    Command parse(String commandWord, String arguments) throws ParseException;

    UiPart<Region> getPanel();
}
