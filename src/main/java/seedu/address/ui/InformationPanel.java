package seedu.address.ui;

import java.util.logging.Logger;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.commons.core.InformationPanelSettings;
import seedu.address.commons.core.InformationPanelSettings.SortDirection;
import seedu.address.commons.core.InformationPanelSettings.SortProperty;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.medicine.Medicine;

/**
 * The Information Panel of the App.
 */
public class InformationPanel extends UiPart<Region> {

    private static final String FXML = "InformationPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(getClass());

    private BatchTable batchTable;
    private SortProperty sortProperty = InformationPanelSettings.DEFAULT_SORT_PROPERTY;
    private SortDirection sortDirection = InformationPanelSettings.DEFAULT_SORT_DIRECTION;

    @FXML
    private StackPane informationPanel;

    public InformationPanel(ObservableValue<Medicine> selectedMedicine,
            ObservableValue<InformationPanelSettings> settings) {
        super(FXML);

        // Load medicine information page when selected medicine changes.
        selectedMedicine.addListener((observable, oldSelectMedicine, newSelectedMedicine) -> {
            emptyInformationPanel();
            if (newSelectedMedicine != null) {
                logger.fine("Information panel displaying details of " + newSelectedMedicine);
                showSelectedInformation(newSelectedMedicine);
            }
        });

        settings.addListener((observable, oldSettings, newSettings) -> {
            sortProperty = newSettings.getSortProperty();
            sortDirection = newSettings.getSortDirection();
            logger.fine("Batch table sorted by SortProperty: " + sortProperty + " SortDirection: " + sortDirection);
            emptyInformationPanel();
            showSelectedInformation(selectedMedicine.getValue());
        });
    }

    private void showSelectedInformation(Medicine medicine) {
        batchTable = new BatchTable(medicine, sortProperty, sortDirection);
        informationPanel.getChildren().add(batchTable.getRoot());
    }

    /**
     * Empties the information panel by removing all children.
     */
    private void emptyInformationPanel() {
        if (informationPanel.getChildren() != null) {
            informationPanel.getChildren().clear();
            batchTable = null;
        }
    }

    public BatchTable getBatchTable() {
        return batchTable;
    }
}
