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
import seedu.address.model.medicine.Medicine;

/**
 * Panel containing the list of medicines.
 */
public class MedicineListPanel extends UiPart<Region> {
    private static final String FXML = "MedicineListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(MedicineListPanel.class);

    @FXML
    private ListView<Medicine> medicineListView;

    public MedicineListPanel(ObservableList<Medicine> medicineList, ObservableValue<Medicine> selectedMedicine,
            Consumer<Medicine> onSelectedMedicineChange) {
        super(FXML);
        medicineListView.setItems(medicineList);
        medicineListView.setCellFactory(listView -> new MedicineListViewCell());
        medicineListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            logger.fine("Selection in medicine list panel changed to : '" + newValue + "'");
            onSelectedMedicineChange.accept(newValue);
        });
        selectedMedicine.addListener((observable, oldValue, newValue) -> {
            logger.fine("Selected medicine changed to: " + newValue);

            // Don't modify selection if we are already selecting the selected medicine,
            // otherwise we would have an infinite loop.
            if (Objects.equals(medicineListView.getSelectionModel().getSelectedItem(), newValue)) {
                return;
            }

            if (newValue == null) {
                medicineListView.getSelectionModel().clearSelection();
            } else {
                int index = medicineListView.getItems().indexOf(newValue);
                medicineListView.scrollTo(index);
                medicineListView.getSelectionModel().clearAndSelect(index);
            }
        });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Medicine} using a {@code MedicineCard}.
     */
    class MedicineListViewCell extends ListCell<Medicine> {
        @Override
        protected void updateItem(Medicine medicine, boolean empty) {
            super.updateItem(medicine, empty);

            if (empty || medicine == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new MedicineCard(medicine, getIndex() + 1).getRoot());
            }
        }
    }

}
