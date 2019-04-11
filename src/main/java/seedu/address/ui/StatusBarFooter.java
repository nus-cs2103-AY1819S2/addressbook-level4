package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;

/**
 * A ui for the status bar that is displayed at the footer of the application.
 */
public class StatusBarFooter extends UiPart<Region> {

    private static final String FXML = "StatusBarFooter.fxml";

    @FXML
    private FlowPane headers;

    public StatusBarFooter() {
        super(FXML);
        setUpLegend();
    }

    /**
     * Sets up all the legends in footer.
     */
    private void setUpLegend() {
        Label questionAnswer = new Label("values to be tested");
        questionAnswer.getStyleClass().add("questionAnswer");

        Label core = new Label("values won't be tested");
        core.getStyleClass().add("core");

        Label hint = new Label("hints");
        hint.getStyleClass().add("opt");

        headers.getChildren().addAll(questionAnswer, core, hint);
    }

}
