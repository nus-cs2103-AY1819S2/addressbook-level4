package seedu.address.ui;

import java.util.logging.Logger;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.commons.core.InformationPanelSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.medicine.Medicine;

/**
 * The Information Panel of the App.
 */
public class InformationPanel extends UiPart<Region> {

    private static final String FXML = "InformationPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(getClass());

    private InformationPanelSettings informationPanelSettings;

    @FXML
    private StackPane informationPanel;

    public InformationPanel(ObservableValue<Medicine> selectedMedicine,
            ObservableValue<InformationPanelSettings> settings) {
        super(FXML);
        informationPanelSettings = settings.getValue();

        // Load medicine information page when selected medicine changes.
        selectedMedicine.addListener((observable, oldSelectMedicine, newSelectedMedicine) ->
            display(newSelectedMedicine));

        settings.addListener((observable, oldSettings, newSettings) -> {
            logger.fine("Batch table sorted by " + newSettings);

            informationPanelSettings = newSettings;
            display(selectedMedicine.getValue());
        });
    }

    private void display(Medicine medicine) {
        emptyInformationPanel();
        showSelectedInformation(medicine);
    }

    /**
     * Empties the information panel by deleting its children.
     */
    private void emptyInformationPanel() {
        if (informationPanel.getChildren() == null) {
            return;
        }

        logger.fine("Information panel emptied");
        informationPanel.getChildren().clear();
    }

    /**
     * Creates a {@code BatchTable} with details from {@code medicine} to be displayed on the information panel.
     */
    private void showSelectedInformation(Medicine medicine) {
        if (medicine == null) {
            return;
        }

        logger.fine("Information panel displaying details of " + medicine);
        informationPanel.getChildren().add(new BatchTable(medicine, informationPanelSettings).getRoot());
    }
}
