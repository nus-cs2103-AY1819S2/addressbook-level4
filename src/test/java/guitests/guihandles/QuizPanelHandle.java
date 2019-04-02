package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;

/**
 * Provides a handle to a flashcard card in the flashcard list panel.
 */
public class QuizPanelHandle extends NodeHandle<Node> {
    private static final String CARDS_REMAINING_ID = "#cardsRemaining";
    private static final String GOOD_ID = "#good";
    private static final String BAD_ID = "#bad";

    private final Label cardsRemaining;
    private final Label good;
    private final Label bad;

    public QuizPanelHandle(Node cardNode) {
        super(cardNode);

        cardsRemaining = getChildNode(CARDS_REMAINING_ID);
        good = getChildNode(GOOD_ID);
        bad = getChildNode(BAD_ID);
    }

    public int getCardsRemaining() {
        return Integer.valueOf(cardsRemaining.getText());
    }

    public int getGood() {
        return Integer.valueOf(good.getText());
    }

    public int getBad() {
        return Integer.valueOf(bad.getText());
    }

    /**
     * Returns true if this handle displays the correct cardsRemaining, good, and bad.
     */
    public boolean equals(int cardsRemaining, int good, int bad) {
        return getCardsRemaining() == cardsRemaining
            && getGood() == good
            && getBad() == bad;
    }
}
