package seedu.address.ui.appointment;

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
import seedu.address.model.appointment.Appointment;
import seedu.address.ui.UiPart;

/**
 * Panel containing the list of appointments.
 */
public class AppointmentListPanel extends UiPart<Region> {
    private static final String FXML = "AppointmentListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(AppointmentListPanel.class);

    @FXML
    private ListView<Appointment> appointmentListView;

    public AppointmentListPanel(ObservableList<Appointment> appointmentList,
                                ObservableValue<Appointment> selectedAppointment,
                                Consumer<Appointment> onSelectedAppointmentChange) {
        super(FXML);
        appointmentListView.setItems(appointmentList);
        appointmentListView.setCellFactory(listView -> new AppointmentListViewCell());
        appointmentListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            logger.fine("Selection in appointment list panel changed to : '" + newValue + "'");
            onSelectedAppointmentChange.accept(newValue);
        });
        selectedAppointment.addListener((observable, oldValue, newValue) -> {
            logger.fine("Selected appointment changed to: " + newValue);

            // Don't modify selection if we are already selecting the selected appointment,
            // otherwise we would have an infinite loop.
            if (Objects.equals(appointmentListView.getSelectionModel().getSelectedItem(), newValue)) {
                return;
            }

            if (newValue == null) {
                appointmentListView.getSelectionModel().clearSelection();
            } else {
                int index = appointmentListView.getItems().indexOf(newValue);
                appointmentListView.scrollTo(index);
                appointmentListView.getSelectionModel().clearAndSelect(index);
            }
        });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Appointment} using a {@code AppointmentCard}.
     */
    class AppointmentListViewCell extends ListCell<Appointment> {
        @Override
        protected void updateItem(Appointment appointment, boolean empty) {
            super.updateItem(appointment, empty);

            if (empty || appointment == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new AppointmentCard(appointment, getIndex() + 1).getRoot());
            }
        }
    }

}
