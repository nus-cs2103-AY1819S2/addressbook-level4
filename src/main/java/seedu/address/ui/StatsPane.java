package seedu.address.ui;

/**
 * StatsPane is a Ui Pane under the Map that displays and update scores
 */
public class StatsPane extends UiPart<Region> {

    public static final String SHIP_LEFT = "Ships Left: %s";
    private static final String FXML = "StatsPane.fxml";

    public StatsPane(){
        super(FXML);
        //function to display and update stats upon change in score
    }

}
