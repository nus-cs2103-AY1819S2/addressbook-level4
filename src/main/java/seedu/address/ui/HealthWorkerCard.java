package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.person.healthworker.HealthWorker;

/**
 * An UI component that displays a simple card info of a {@code HealthWorker},
 * displaying its specialisation, as well as its availability.
 *
 * @author Hui Chun
 */
public class HealthWorkerCard extends UiPart<Region> {

    private static final String FXML = "HealthWorkerListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     */

    public final HealthWorker worker;

    @FXML
    private BorderPane borderPane;

    @FXML
    private VBox healthWorkerInfo;

    @FXML
    private Label id;

    @FXML
    private Label name;

    @FXML
    private Label organisation;

    @FXML
    private Label nric;

    @FXML
    private Label phone;

    @FXML
    private FlowPane specialisations;


    public HealthWorkerCard(HealthWorker worker, int displayedIndex) {
        super(FXML);
        this.worker = worker;
        this.id.setText(displayedIndex + ". ");
        this.name.setText(worker.getName().toString());
        this.organisation.setText("(" + worker.getOrganization().toString() + ")");
        this.nric.setText(worker.getNric().toString());
        this.phone.setText(worker.getPhone().toString());
        this.worker.getSkills().getSkills().forEach(s ->
            specialisations.getChildren().add(new Label(s.toString().toLowerCase())));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof HealthWorkerCard)) {
            return false;
        }

        // state check
        HealthWorkerCard card = (HealthWorkerCard) other;
        return name.getText().equals(card.name.getText())
                && organisation.getText().equals(card.organisation.getText());
    }
}
