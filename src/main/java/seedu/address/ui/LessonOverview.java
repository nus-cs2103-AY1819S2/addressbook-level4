package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.lesson.Lesson;

/**
 * An UI component that displays information of a {@link Lesson}.
 */
public class LessonOverview extends UiPart<Region> {

    private static final String FXML = "LessonOverview.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Lesson lesson;

    //@FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label count;
    @FXML
    private FlowPane bigHeaders;

    public LessonOverview(Lesson lesson) {
        super(FXML);
        this.lesson = lesson;
        name.setText(lesson.getName());

        int cardCount = lesson.getCardCount();
        if (cardCount > 1) {
            count.setText(cardCount + " cards");
        } else {
            count.setText(cardCount + " card");
        }

        int i = 0;
        int questionIndex = lesson.getQuestionCoreIndex();
        int answerIndex = lesson.getAnswerCoreIndex();
        for (String s: lesson.getCoreHeaders()) {
            Label label = new Label(s);

            if (i == questionIndex || i == answerIndex) {
                label.getStyleClass().add("questionAnswer");
                bigHeaders.getChildren().add(label);
            } else {
                label.getStyleClass().add("core");
                bigHeaders.getChildren().add(label);
            }

            i++;
        }

        for (String s: lesson.getOptionalHeaders()) {
            Label label = new Label(s);
            label.getStyleClass().add("opt");
            bigHeaders.getChildren().add(label);
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof LessonCard)) {
            return false;
        }

        // state check
        LessonOverview overview = (LessonOverview) other;
        return lesson.equals(overview.lesson);
    }
}
