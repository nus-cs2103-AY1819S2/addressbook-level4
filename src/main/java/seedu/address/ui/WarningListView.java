package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.medicine.Medicine;

/**
 * Cell containing content of warning.
 */
public class WarningListView extends UiPart<Region> {
    private static final String FXML = "WarningListView.fxml";
    private final Logger logger = LogsCenter.getLogger(WarningListView.class);

    @FXML
    private ListView<Medicine> warningListView;

    private String listType;

    public WarningListView(ObservableList<Medicine> medicineList, String listType) {
        super(FXML);

        this.listType = listType;

        // placeholder if the list is empty
        warningListView.setPlaceholder(new Label("Nothing to show"));

        warningListView.setItems(medicineList);
        warningListView.setCellFactory(listView -> new WarningListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Medicine} using a {@code WarningCard}.
     */
    class WarningListViewCell extends ListCell<Medicine> {
        @Override
        protected void updateItem(Medicine medicine, boolean empty) {
            super.updateItem(medicine, empty);

            if (empty || medicine == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new WarningCard(medicine, getIndex() + 1, listType).getRoot());
            }
        }
    }

}
