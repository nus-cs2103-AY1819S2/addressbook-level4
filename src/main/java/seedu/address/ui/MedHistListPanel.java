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
import seedu.address.model.medicalhistory.MedicalHistory;

/**
 * Panel containing the list of medical histories.
 */
public class MedHistListPanel extends UiPart<Region> {
    private static final String FXML = "MedHistListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(MedHistListPanel.class);

    @FXML
    private ListView<MedicalHistory> medHistListView;

    public MedHistListPanel(ObservableList<MedicalHistory> medHistList, ObservableValue<MedicalHistory> selectedMedHist,
                            Consumer<MedicalHistory> onSelectedMedHistChange) {
        super(FXML);
        medHistListView.setItems(medHistList);
        medHistListView.setCellFactory(listView -> new MedHistListViewCell());
        medHistListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            logger.fine("Selection in medical history list panel changed to : '" + newValue + "'");
            onSelectedMedHistChange.accept(newValue);
        });
        selectedMedHist.addListener((observable, oldValue, newValue) -> {
            logger.fine("Selected medical history changed to: " + newValue);

            // Don't modify selection if we are already selecting the selected medical history,
            // otherwise we would have an infinite loop.
            if (Objects.equals(medHistListView.getSelectionModel().getSelectedItem(), newValue)) {
                return;
            }

            if (newValue == null) {
                medHistListView.getSelectionModel().clearSelection();
            } else {
                int index = medHistListView.getItems().indexOf(newValue);
                medHistListView.scrollTo(index);
                medHistListView.getSelectionModel().clearAndSelect(index);
            }
        });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code MedicalHistory} using a {@code MedHistCard}.
     */
    class MedHistListViewCell extends ListCell<MedicalHistory> {
        @Override
        protected void updateItem(MedicalHistory medHist, boolean empty) {
            super.updateItem(medHist, empty);

            if (empty || medHist == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new MedHistCard(medHist, getIndex() + 1).getRoot());
            }
        }
    }

}

