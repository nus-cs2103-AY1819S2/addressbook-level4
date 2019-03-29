package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.medicalhistory.MedicalHistory;

/**
 * An UI component that displays information of a {@code MedicalHistory}.
 */
public class MedHistCard extends UiPart<Region> {
    private static final String FXML = "MedHistListCard.fxml";
    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */
    public final MedicalHistory medHist;

    @javafx.fxml.FXML
    private HBox cardPane;
    @FXML
    private Label name;
    //@FXML
    //private Label id;
    @FXML
    private Label writeUp;

    public MedHistCard(MedicalHistory medHist, int displayedIndex) {
        super(FXML);
        this.medHist = medHist;
        //id.setText(displayedIndex + ". ");
        name.setText(medHist.getName().fullName);
        writeUp.setText(medHist.getWriteUp().value);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MedHistCard)) {
            return false;
        }

        // state check
        MedHistCard card = (MedHistCard) other;
        return /*id.getText().equals(card.id.getText())&& */name.equals(card.name) && writeUp.equals(card.writeUp);
    }

}

