package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.address.model.CardFolder;
import seedu.address.model.card.Card;

/**
 * An UI component when user enters a test session.
 */
public class TestSession extends UiPart<Region> {

    private static final String FXML = "TestSession.fxml";

    public final CardFolder cardFolder;

    @FXML
    private AnchorPane testSessionPage;
    @FXML
    private Label testCard;

    public TestSession(CardFolder cardFolder) {
        super(FXML);
        this.cardFolder = cardFolder;
        displayCard(cardFolder);
    }
    
    public void displayCard(CardFolder cardFolder) {
        ObservableList<Card> cards = cardFolder.getCardList();
        //TODO: remove the hard coding of the first question 
        displayCardQuestion(cards.get(1));
    }
    
    public void displayCardQuestion(Card cardToBeTested) {
        testCard.setText(cardToBeTested.getQuestion().fullQuestion);
    }
}

