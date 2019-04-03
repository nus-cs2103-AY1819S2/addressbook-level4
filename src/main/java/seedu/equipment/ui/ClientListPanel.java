package seedu.equipment.ui;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.equipment.model.equipment.Name;

/**
 * Panel containing the list of clients.
 */
public class ClientListPanel extends UiPart<Region> {
    private static final String FXML = "ClientListPanel.fxml";

    @FXML
    private ListView<Name> clientListView;

    public ClientListPanel(ObservableList<Name> nameList) {
        super(FXML);
        clientListView.setItems(nameList);
        clientListView.setCellFactory(listView -> new ClientListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Equipment} using a {@code ClientListCard}.
     */
    class ClientListViewCell extends ListCell<Name> {
        @Override
        protected void updateItem(Name clientName, boolean empty) {
            super.updateItem(clientName, empty);

            if (empty || clientName == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ClientListCard(clientName, getIndex() + 1).getRoot());
            }
        }
    }

}
