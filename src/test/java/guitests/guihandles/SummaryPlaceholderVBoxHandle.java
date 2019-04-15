package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * Provides a handle to a restaurant card in the restaurant list panel.
 */
public class SummaryPlaceholderVBoxHandle extends NodeHandle<Node> {
    private static final String PLACEHOLDER_FIELD_ID = "#panelName";

    private final Label placeholderLabel;

    public SummaryPlaceholderVBoxHandle(VBox vBox) {
        super(vBox);

        placeholderLabel = getChildNode(PLACEHOLDER_FIELD_ID);
    }

    public String getPlaceholder() {
        return placeholderLabel.getText();
    }

    /**
     * Returns true if this handle contains {@code restaurant}.
     */
    public boolean equals(String placeholder) {
        return getPlaceholder().equals(placeholder);
    }
}
