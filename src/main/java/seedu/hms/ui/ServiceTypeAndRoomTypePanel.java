package seedu.hms.ui;

import java.util.Objects;
import java.util.function.Consumer;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

/**
 * The ServiceTypeAndRoomType Panel of the App.
 */
public class ServiceTypeAndRoomTypePanel extends UiPart<Region> {
    private static final String FXML = "ServiceTypeAndRoomTypePanel.fxml";

    @FXML
    private StackPane serviceTypeListPanelPlaceholder;

    @FXML
    private StackPane roomTypeListPanelPlaceholder;

    @FXML
    private TabPane tabPane;

    public ServiceTypeAndRoomTypePanel(ServiceTypeListPanel serviceTypeListPanel, RoomTypeListPanel roomTypeListPanel,
                                       ObservableValue<Integer> selectedTabIndex,
                                       Consumer<Integer> onSelectedTabIndexChange) {
        super(FXML);
        tabPane.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            onSelectedTabIndexChange.accept(newValue.intValue());
        });
        selectedTabIndex.addListener((observable, oldValue, newValue) -> {
            // Don't modify selection if we are already selecting the selected booking,
            // otherwise we would have an infinite loop.
            if (Objects.equals(tabPane.getSelectionModel().getSelectedIndex(), newValue.intValue())) {
                return;
            }

            tabPane.getSelectionModel().select(newValue);
        });

        selectedTabIndex.addListener(((observable, oldValue, newValue) -> {
            tabPane.getSelectionModel().select(newValue);
        }));
        serviceTypeListPanelPlaceholder.getChildren().add(serviceTypeListPanel.getRoot());
        roomTypeListPanelPlaceholder.getChildren().add(roomTypeListPanel.getRoot());
    }
}
