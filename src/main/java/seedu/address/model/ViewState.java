package seedu.address.model;

import javafx.scene.layout.Region;
import seedu.address.logic.parser.ViewStateParser;
import seedu.address.ui.UiPart;

/**
 * The state. States must minimally provide policies for parsing and rendering.
 *
 * Holds state-specific data.
 * Data that need to persist regardless of the state belong to the Model (e.g. deck data).
 */
public interface ViewState {
    /**
     * Construct a ViewStateParser to be used for parsing commands specific to this state.
     */
    ViewStateParser getViewStateParser();

    /**
     * Construct a Panel object to render this state in the UI.
     */
    UiPart<Region> getPanel();
}
