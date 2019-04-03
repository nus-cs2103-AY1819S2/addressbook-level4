package seedu.equipment.ui;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.equipment.model.equipment.Equipment;

/**
 * Panel containing the list of clients.
 */
public class ClientListPanel extends UiPart<Region> {
    private static final String FXML = "ClientListPanel.fxml";

    private ObservableList<String> uniqueNameList = FXCollections.observableArrayList();
    private ArrayList<String> nameList = new ArrayList<>();


    @FXML
    private ListView<String> clientListView;

    public ClientListPanel(ObservableList<Equipment> equipmentList) {
        super(FXML);

        for (int i = 0; i < equipmentList.size(); i++) {
            if (!nameList.contains(equipmentList.get(i).getName().name)) {
                nameList.add(equipmentList.get(i).getName().name);
            }
        }
        uniqueNameList.addAll(nameList);
        clientListView.setItems(uniqueNameList);
        clientListView.setCellFactory(listView -> new ClientListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Equipment} using a {@code ClientListCard}.
     */
    class ClientListViewCell extends ListCell<String> {
        @Override
        protected void updateItem(String clientName, boolean empty) {
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
