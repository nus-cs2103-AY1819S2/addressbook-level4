package seedu.address.ui;

import javafx.scene.layout.Region;

/**
 * a panel that renders in the main part of the window.
 */
public abstract class MainPanel extends UiPart<Region> {
    public MainPanel(String fxmlFileName) {
        super(fxmlFileName);
    }
}
