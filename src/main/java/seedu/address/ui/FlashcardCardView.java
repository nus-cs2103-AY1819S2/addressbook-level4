package seedu.address.ui;

import java.util.Objects;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.model.flashcard.Flashcard;

/**
 * A UI component that displays information of a {@code Flashcard} as a card view.
 */
public class FlashcardCardView extends UiPart<Region> {

    private static final String FXML = "FlashcardCardView.fxml";

    public final Flashcard flashcard;

    @FXML
    private StackPane cardPane;
    @FXML
    private Label frontFace;
    @FXML
    private Label backFace;

    public FlashcardCardView(Flashcard flashcard) {
        super(FXML);
        this.flashcard = flashcard;
        frontFace.setText(flashcard.getFrontFace().text);
        backFace.setText(flashcard.getBackFace().text);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FlashcardCardView)) {
            return false;
        }
        FlashcardCardView that = (FlashcardCardView) o;
        return frontFace.getText().equals(that.frontFace.getText())
            && backFace.getText().equals(that.backFace.getText());
    }

    @Override
    public int hashCode() {
        return Objects.hash(frontFace, backFace);
    }
}
