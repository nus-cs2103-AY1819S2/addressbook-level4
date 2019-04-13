package seedu.address.model;

import javafx.scene.layout.Region;
import seedu.address.logic.commands.Command;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.ui.UiPart;

public interface ViewState {
    Command parse(String commandWord, String arguments) throws ParseException;

    /**
     * Construct a Panel object to be rendered by the UI.
     */
    UiPart<Region> getPanel();
}
