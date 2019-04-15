package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.moduletaken.ModuleTaken;

/**
 * An UI component that displays information of a {@code ModuleTaken}.
 */
public class ModuleTakenCard extends UiPart<Region> {

    private static final String FXML = "ModuleTakenListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on GradTrak level 4</a>
     */

    public final ModuleTaken moduleTaken;

    @FXML
    private HBox cardPane;
    @FXML
    private Label moduleInfoCode;
    @FXML
    private Label id;
    @FXML
    private Label semester;
    @FXML
    private Label expectedMaxGrade;
    @FXML
    private Label expectedMinGrade;
    @FXML
    private FlowPane tags;

    public ModuleTakenCard(ModuleTaken moduleTaken, int displayedIndex) {
        super(FXML);
        this.moduleTaken = moduleTaken;
        id.setText(displayedIndex + ". ");
        moduleInfoCode.setText(moduleTaken.getModuleInfoCode().toString());
        semester.setText(moduleTaken.getSemester().toString());
        expectedMinGrade.setText(moduleTaken.getExpectedMinGrade().toString());
        expectedMaxGrade.setText(moduleTaken.getExpectedMaxGrade().toString());
        moduleTaken.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModuleTakenCard)) {
            return false;
        }

        // state check
        ModuleTakenCard card = (ModuleTakenCard) other;
        return id.getText().equals(card.id.getText())
                && moduleTaken.equals(card.moduleTaken);
    }
}
