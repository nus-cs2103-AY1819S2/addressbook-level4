package seedu.hms.ui;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.logging.Logger;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.hms.commons.core.LogsCenter;
import seedu.hms.model.reservation.RoomType;

/**
 * todo: need to be changed to shown roomType after room commands are implemented
 * Panel containing the list of roomTypes.
 */
public class RoomTypeListPanel extends UiPart<Region> {
    private static final String FXML = "RoomTypeListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(RoomTypeListPanel.class);

    @FXML
    private ListView<RoomType> roomTypeListView;

    public RoomTypeListPanel(ObservableList<RoomType> roomTypeList,
                                ObservableValue<RoomType> selectedRoomType,
                                Consumer<RoomType> onSelectedRoomTypeChange) {
        super(FXML);
        roomTypeListView.setItems(roomTypeList);
        roomTypeListView.setCellFactory(listView -> new RoomTypeListViewCell());
        roomTypeListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            logger.fine("Selection in room type list panel changed to : '" + newValue + "'");
            onSelectedRoomTypeChange.accept(newValue);
        });
        selectedRoomType.addListener((observable, oldValue, newValue) -> {
            logger.fine("Selected roomType changed to: " + newValue);

            // Don't modify selection if we are already selecting the selected booking,
            // otherwise we would have an infinite loop.
            if (Objects.equals(roomTypeListView.getSelectionModel().getSelectedItem(), newValue)) {
                return;
            }

            if (newValue == null) {
                roomTypeListView.getSelectionModel().clearSelection();
            } else {
                int index = roomTypeListView.getItems().indexOf(newValue);
                roomTypeListView.scrollTo(index);
                roomTypeListView.getSelectionModel().clearAndSelect(index);
            }
        });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Booking} using a {@code BookingCard}.
     */
    class RoomTypeListViewCell extends ListCell<RoomType> {
        @Override
        protected void updateItem(RoomType roomType, boolean empty) {
            super.updateItem(roomType, empty);

            if (empty || roomType == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new RoomTypeListCard(roomType).getRoot());
            }
        }
    }

}
