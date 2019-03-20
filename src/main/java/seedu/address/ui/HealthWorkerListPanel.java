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
import seedu.address.model.person.healthworker.HealthWorker;

/**
 * The HealthWorkerList Panel of the App.
 * Responsible for constructing the health worker list panel via its individual
 * {@code HealthWorker} cards and handling event logic.
 * @author Hui Chun
 */
public class HealthWorkerListPanel extends UiPart<Region> {
    private static final String FXML = "HealthWorkerListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(HealthWorkerListPanel.class);

    @FXML
    private ListView<HealthWorker> healthWorkerListView;

    public HealthWorkerListPanel(ObservableList<HealthWorker> healthWorkerList,
                                 ObservableValue<HealthWorker> selectedHealthWorker,
                                 Consumer<HealthWorker> onSelectedHealthWorkerChange) {
        super(FXML);
        healthWorkerListView.setItems(healthWorkerList);
        healthWorkerListView.setCellFactory(listView -> new HealthWorkerListPanel.HealthWorkerListCell());
        healthWorkerListView.getSelectionModel()
                .selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                    logger.fine("Selection in health worker list panel changed to : '" + newValue + "'");
                    onSelectedHealthWorkerChange.accept(newValue);
                });
        selectedHealthWorker.addListener((observable, oldValue, newValue) -> {
            logger.fine("Selected health worker changed to: " + newValue);

            // Don't modify selection if we are already selecting the selected health worker,
            // otherwise we would have an infinite loop.
            if (Objects.equals(healthWorkerListView.getSelectionModel().getSelectedItem(), newValue)) {
                return;
            }

            if (newValue == null) {
                healthWorkerListView.getSelectionModel().clearSelection();
            } else {
                int index = healthWorkerListView.getItems().indexOf(newValue);
                healthWorkerListView.scrollTo(index);
                healthWorkerListView.getSelectionModel().clearAndSelect(index);
            }
        });

    }


    /**
     * Custom {@code ListCell} that displays the graphics of a {@code HealthWorker} using a {@code HealthWorkerCard}.
     */
    class HealthWorkerListCell extends ListCell<HealthWorker> {
        @Override
        protected void updateItem(HealthWorker worker, boolean empty) {
            super.updateItem(worker, empty);

            if (empty || worker == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new PersonCard(worker, getIndex() + 1).getRoot());
            }
        }
    }
}
