package seedu.address.ui;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.logging.Logger;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.prescription.Prescription;


/**
 * Panel containing the list of prescriptions.
 */
public class PrescriptionListPanel extends UiPart<Region> {
    private static final String FXML = "PrescriptionListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PrescriptionListPanel.class);

    @FXML
    private ListView<Prescription> prescriptionListView;

    public PrescriptionListPanel(ObservableList<Prescription> prescriptionList,
                                 ObservableValue<Prescription> selectedPrescription,
                            Consumer<Prescription> onSelectedPrescriptionChange) {
        super(FXML);
        prescriptionListView.setItems(prescriptionList);
        prescriptionListView.setCellFactory(listView -> new PrescriptionListViewCell());
        prescriptionListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    logger.fine("Selection in prescription list panel changed to : '" + newValue + "'");
                    onSelectedPrescriptionChange.accept(newValue);
                });
        selectedPrescription.addListener((observable, oldValue, newValue) -> {
            logger.fine("Selected prescription changed to: " + newValue);

            // Don't modify selection if we are already selecting the selected prescription,
            // otherwise we would have an infinite loop.
            if (Objects.equals(prescriptionListView.getSelectionModel().getSelectedItem(), newValue)) {
                return;
            }

            if (newValue == null) {
                prescriptionListView.getSelectionModel().clearSelection();
            } else {
                int index = prescriptionListView.getItems().indexOf(newValue);
                prescriptionListView.scrollTo(index);
                prescriptionListView.getSelectionModel().clearAndSelect(index);
            }
        });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Prescription} using a {@code PrescriptionCard}.
     */
    class PrescriptionListViewCell extends ListCell<Prescription> {
        @Override
        protected void updateItem(Prescription prescription, boolean empty) {
            super.updateItem(prescription, empty);

            if (empty || prescription == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new PrescriptionCard(prescription, getIndex() + 1).getRoot());
            }
        }
    }

}


