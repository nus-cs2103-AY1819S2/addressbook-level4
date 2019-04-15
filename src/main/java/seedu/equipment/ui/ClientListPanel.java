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
import seedu.equipment.model.equipment.Name;

/**
 * Panel containing the list of clients.
 */
public class ClientListPanel extends UiPart<Region> {
    private static final String FXML = "ClientListPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(ClientListPanel.class);


    @FXML
    private ListView<Name> clientListView;

    public ClientListPanel(ObservableList<Name> nameList, ObservableValue<Name> selectedClient,
                           Consumer<Name> onSelectedClientChange) {
        super(FXML);
        clientListView.setItems(nameList);
        clientListView.setCellFactory(listView -> new ClientListViewCell());

        clientListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            logger.fine("Selection in client list panel changed to : '" + newValue + "'");
            onSelectedClientChange.accept(newValue);
        });
        selectedClient.addListener((observable, oldValue, newValue) -> {
            logger.fine("Selected client changed to: " + newValue);

            // Don't modify selection if we are already selecting the selected equipment,
            // otherwise we would have an infinite loop.
            if (Objects.equals(clientListView.getSelectionModel().getSelectedItem(), newValue)) {
                return;
            }

            if (newValue == null) {
                clientListView.getSelectionModel().clearSelection();
            } else {
                int index = clientListView.getItems().indexOf(newValue);
                clientListView.scrollTo(index);
                clientListView.getSelectionModel().clearAndSelect(index);
            }
        });
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
