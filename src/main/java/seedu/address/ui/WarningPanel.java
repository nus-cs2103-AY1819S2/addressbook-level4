package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.medicine.Medicine;

/**
 * A ui for the warning panel that is displayed at the right of the application.
 */
public class WarningPanel extends UiPart<Region> {
    private static final String FXML = "WarningPanel.fxml";
    private static final Logger logger = LogsCenter.getLogger(WarningPanel.class);

    @FXML
    private VBox warningVBox;

    public WarningPanel(ObservableList<Medicine> medicineList) {
        super(FXML);

        createVBox(medicineList);

    }

    /**
     * Creates the VBox which holds the lists.
     */
    private void createVBox(ObservableList<Medicine> medicineList) {
        String[] listTitles= {"Expiring Soon", "Low in Stock"};

        for (String title : listTitles) {
            Label listTitle = new Label(title);
            listTitle.getStyleClass().add("label-bright");
            warningVBox.getChildren().addAll(listTitle, new WarningListView(medicineList).getRoot());
        }

    }

}
