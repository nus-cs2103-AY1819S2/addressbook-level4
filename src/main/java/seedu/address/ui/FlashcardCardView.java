package seedu.address.ui;

import java.io.File;

import java.util.Objects;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    @FXML
    private ImageView flashcardImage;

    /**
     * Constructs a {@code FlashCardView} with the given mode.
     *
     * @param flashcard the flashcard to show
     * @param mode      -1 for front only, 1 for back only, and 0 for showing both
     */
    public FlashcardCardView(Flashcard flashcard, int mode) {
        super(FXML);
        this.flashcard = flashcard;
        frontFace.setText(flashcard.getFrontFace().text);
        if (mode != -1) {
            backFace.setText(flashcard.getBackFace().text);
        } else {
            backFace.setText("");
        }
        if (flashcard.getImagePath().hasImagePath()) {
            File file = new File(flashcard.getImagePath().getImagePath());
            Image image = new Image(file.toURI().toString());
            flashcardImage.setImage(image);
            flashcardImage.setFitHeight(192);
            flashcardImage.setPreserveRatio(true);
        }
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
        if (flashcardImage.getImage() == null && that.flashcardImage.getImage() == null) {
            return frontFace.getText().equals(that.frontFace.getText())
                && backFace.getText().equals(that.backFace.getText());
        } else if (flashcardImage.getImage() != null && that.flashcardImage == null) {
            return false;
        } else if (flashcardImage == null && that.flashcardImage.getImage() != null) {
            return false;
        } else {
            return frontFace.getText().equals(that.frontFace.getText())
                && backFace.getText().equals(that.backFace.getText())
                && flashcardImage.getImage().equals(that.flashcardImage.getImage());
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(frontFace, backFace, flashcardImage);
    }
}
