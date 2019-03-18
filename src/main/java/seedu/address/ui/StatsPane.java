package seedu.address.ui;


import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;

/**
 * StatsPane is a Ui Pane under the Map that displays and update scores
 */
public class StatsPane extends UiPart<Region> {

    public static final String SHIP_LEFT = "Ships Left: %s";
    private static final String FXML = "StatsPane.fxml";

    @FXML
    private TextArea resultDisplay;

    public StatsPane() {
        super(FXML);
        System.out.println("StatsPane initialized");
        //function to display and update stats upon change in score
    }

}
