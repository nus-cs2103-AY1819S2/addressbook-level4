package seedu.equipment.ui;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.logging.Logger;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.equipment.commons.core.LogsCenter;
import seedu.equipment.model.equipment.Equipment;

/**
 * Panel containing the list of clients.
 */
public class ClientListPanel extends UiPart<Region> {
    private static final String FXML = "ClientListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ClientListPanel.class);


    @FXML
    private ListView<Equipment> clientListView;

    public ClientListPanel(ObservableList<Equipment> equipmentList) {
        super(FXML);
        clientListView.setItems(equipmentList);
        clientListView.setCellFactory(listView -> new ClientListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Equipment} using a {@code ClientListCard}.
     */
    class ClientListViewCell extends ListCell<Equipment> {
        @Override
        protected void updateItem(Equipment equipment, boolean empty) {
            super.updateItem(equipment, empty);

            if (empty || equipment == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ClientListCard(equipment, getIndex() + 1).getRoot());
            }
        }
    }

}
