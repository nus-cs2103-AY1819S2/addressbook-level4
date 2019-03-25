package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.medicine.Medicine;
import seedu.address.commons.util.warning.WarningPanelListType;
import seedu.address.commons.util.warning.WarningPanelPredicateAccessor;

/**
 * A ui for the warning panel that is displayed at the right of the application.
 */
public class WarningPanel extends UiPart<Region> {
    private static final String FXML = "WarningPanel.fxml";
    private static final Logger logger = LogsCenter.getLogger(WarningPanel.class);
    private final WarningPanelPredicateAccessor warningPanelPredicateAccessor;

    @FXML
    private VBox warningVBox;

    public WarningPanel(ObservableList<Medicine> expiringMedicineList,
                        ObservableList<Medicine> lowQuantityMedicineList,
                        WarningPanelPredicateAccessor warningPanelPredicateAccessor) {
        super(FXML);

        this.warningPanelPredicateAccessor = warningPanelPredicateAccessor;
        setUpVBox(expiringMedicineList, lowQuantityMedicineList);

    }

    /**
     * Sets up the VBox which holds the lists.
     */
    private void setUpVBox(ObservableList<Medicine> expiringMedicineList,
                            ObservableList<Medicine> lowQuantityMedicineList) {
        Label expiringListTitle = new Label("Expiring Soon");
        expiringListTitle.getStyleClass().add("label-bright");
        warningVBox.getChildren().addAll(expiringListTitle,
                new WarningListView(expiringMedicineList,
                WarningPanelListType.EXPIRY, warningPanelPredicateAccessor).getRoot());

        Label lowQuantityListTitle = new Label("Low in Stock");
        lowQuantityListTitle.getStyleClass().add("label-bright");
        warningVBox.getChildren().addAll(lowQuantityListTitle,
                new WarningListView(lowQuantityMedicineList,
                WarningPanelListType.LOW_STOCK, warningPanelPredicateAccessor).getRoot());

    }

}
