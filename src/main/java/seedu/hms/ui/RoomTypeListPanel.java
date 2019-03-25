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
import seedu.hms.model.booking.ServiceType;

/**
 * todo: need to be changed to shown roomType after room commands are implemented
 * Panel containing the list of serviceTypes.
 */
public class RoomTypeListPanel extends UiPart<Region> {
    private static final String FXML = "ServiceTypeListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ServiceTypeListPanel.class);

    @FXML
    private ListView<ServiceType> serviceTypeListView;

    public RoomTypeListPanel(ObservableList<ServiceType> serviceTypeList,
                                ObservableValue<ServiceType> selectedServiceType,
                                Consumer<ServiceType> onSelectedServiceTypeChange,
                                CommandBox.CommandExecutor commandExecutor) {
        super(FXML);
        serviceTypeListView.setItems(serviceTypeList);
        serviceTypeListView.setCellFactory(listView -> new ServiceTypeListViewCell());
        serviceTypeListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            logger.fine("Selection in service type list panel changed to : '" + newValue + "'");
            onSelectedServiceTypeChange.accept(newValue);
        });
        selectedServiceType.addListener((observable, oldValue, newValue) -> {
            logger.fine("Selected serviceType changed to: " + newValue);

            // Don't modify selection if we are already selecting the selected booking,
            // otherwise we would have an infinite loop.
            if (Objects.equals(serviceTypeListView.getSelectionModel().getSelectedItem(), newValue)) {
                return;
            }

            if (newValue == null) {
                serviceTypeListView.getSelectionModel().clearSelection();
            } else {
                int index = serviceTypeListView.getItems().indexOf(newValue);
                serviceTypeListView.scrollTo(index);
                serviceTypeListView.getSelectionModel().clearAndSelect(index);
            }
        });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Booking} using a {@code BookingCard}.
     */
    class ServiceTypeListViewCell extends ListCell<ServiceType> {
        @Override
        protected void updateItem(ServiceType serviceType, boolean empty) {
            super.updateItem(serviceType, empty);

            if (empty || serviceType == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ServiceTypeListCard(serviceType).getRoot());
            }
        }
    }

}
