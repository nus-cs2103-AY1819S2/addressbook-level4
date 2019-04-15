package seedu.address.ui.doctor;

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
import seedu.address.model.person.doctor.Doctor;
import seedu.address.ui.UiPart;

/**
 * Panel containing the list of doctors.
 */
public class DoctorListPanel extends UiPart<Region> {
    private static final String FXML = "DoctorListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(DoctorListPanel.class);

    @FXML
    private ListView<Doctor> doctorListView;

    public DoctorListPanel(ObservableList<Doctor> doctorList, ObservableValue<Doctor> selectedDoctor,
                           Consumer<Doctor> onSelectedDoctorChange) {
        super(FXML);
        doctorListView.setItems(doctorList);
        doctorListView.setCellFactory(listView -> new DoctorListViewCell());
        doctorListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            logger.fine("Selection in doctor list panel changed to : '" + newValue + "'");
            onSelectedDoctorChange.accept(newValue);
        });
        selectedDoctor.addListener((observable, oldValue, newValue) -> {
            logger.fine("Selected doctor changed to: " + newValue);

            // Don't modify selection if we are already selecting the selected doctor,
            // otherwise we would have an infinite loop.
            if (Objects.equals(doctorListView.getSelectionModel().getSelectedItem(), newValue)) {
                return;
            }

            if (newValue == null) {
                doctorListView.getSelectionModel().clearSelection();
            } else {
                int index = doctorListView.getItems().indexOf(newValue);
                doctorListView.scrollTo(index);
                doctorListView.getSelectionModel().clearAndSelect(index);
            }
        });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Doctor} using a {@code DoctorCard}.
     */
    class DoctorListViewCell extends ListCell<Doctor> {
        @Override
        protected void updateItem(Doctor doctor, boolean empty) {
            super.updateItem(doctor, empty);

            if (empty || doctor == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new DoctorCard(doctor, getIndex() + 1).getRoot());
            }
        }
    }

}
