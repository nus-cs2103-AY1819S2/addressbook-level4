package seedu.hms.ui;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

/**
 * The Browser Panel of the App.
 */
public class ServiceTypeAndRoomTypePanel extends UiPart<Region> {
    private static final String FXML = "ServiceTypeAndRoomTypePanel.fxml";

    @FXML
    private StackPane serviceTypeListPanelPlaceholder;

    @FXML
    private StackPane roomTypeListPanelPlaceholder;

    public ServiceTypeAndRoomTypePanel(ServiceTypeListPanel serviceTypeListPanel, RoomTypeListPanel roomTypeListPanel) {
        super(FXML);
        serviceTypeListPanelPlaceholder.getChildren().add(serviceTypeListPanel.getRoot());
        roomTypeListPanelPlaceholder.getChildren().add(roomTypeListPanel.getRoot());
    }
}
