package seedu.address.ui.doctor;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.doctor.Doctor;
import seedu.address.ui.UiPart;

/**
 * An UI component that displays information of a {@code Doctor}.
 */
public class DoctorCard extends UiPart<Region> {

    private static final String FXML = "DoctorListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on DocX level 4</a>
     */

    public final Doctor doctor;

    @FXML
    private HBox cardPane;
    @FXML
    private Label did;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label gender;
    @FXML
    private Label year;
    @FXML
    private Label phone;
    @FXML
    private FlowPane specialisations;

    public DoctorCard(Doctor doctor, int displayedIndex) {
        super(FXML);
        this.doctor = doctor;
        id.setText(displayedIndex + ". ");
        name.setText(doctor.getName().fullName);
        did.setText("| did: " + doctor.getIdToString());
        gender.setText(doctor.getGender().value);
        year.setText(doctor.getYear().value);
        phone.setText(doctor.getPhone().value);

        doctor.getSpecs().forEach(spec -> specialisations.getChildren().add(new Label(spec.specialisation)));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DoctorCard)) {
            return false;
        }

        // state check
        DoctorCard card = (DoctorCard) other;
        return id.getText().equals(card.id.getText())
                && doctor.equals(card.doctor);
    }
}
