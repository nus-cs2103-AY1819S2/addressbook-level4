package seedu.address.model;

import javafx.scene.layout.Region;
import seedu.address.logic.parser.ViewStateParser;
import seedu.address.ui.UiPart;

public interface ViewState {
    ViewStateParser getViewStateParser();

    /**
     * Construct a Panel object to be rendered by the UI.
     */
    UiPart<Region> getPanel();
}
