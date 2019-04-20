package seedu.address.ui;

import static seedu.address.commons.util.StringUtil.truncateString;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import seedu.address.model.card.Card;

/**
 * An UI component that displays information of a {@link Card}.
 */
public class FlashcardCard extends UiPart<Region> {

    private static final String FXML = "FlashcardCard.fxml";

    /**
     * Maximum length of label text before truncation occurs.
     */
    private static final int labelMaxLen = 20;

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Card card;

    //@FXML
    //private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label name;
    @FXML
    private FlowPane headers;

    public FlashcardCard(Card card, int displayedIndex, int questionIndex, int answerIndex) {
        super(FXML);
        this.card = card;
        id.setText(displayedIndex + ". ");
        name.setText(formatName(card.getCore(questionIndex), card.getCore(answerIndex)));

        for (int i = 0; i < card.getCores().size(); i++) {
            String core = card.getCore(i);

            // Construct the numbering for the cores
            // Needed for setTest command
            StringBuilder sb = new StringBuilder();
            sb.append(i + 1).append(". ").append(core);
            Label label = new Label(truncateString(sb.toString(), labelMaxLen));
            label.setTooltip(new Tooltip(core));

            if (i == questionIndex || i == answerIndex) {
                label.getStyleClass().add("questionAnswer");
            } else {
                label.getStyleClass().add("core");
            }

            headers.getChildren().add(label);
        }

        for (int i = 0; i < card.getOptionals().size(); i++) {
            String optional = card.getOptional(i);
            Label label = new Label(truncateString(optional, labelMaxLen));
            label.setTooltip(new Tooltip(optional));
            label.getStyleClass().add("opt");
            headers.getChildren().add(label);
        }
    }

    public static String formatName(String question, String answer) {
        return question + " / " + answer;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FlashcardCard)) {
            return false;
        }

        // state check
        FlashcardCard otherCard = (FlashcardCard) other;
        return card.equals(otherCard.card);
    }
}
