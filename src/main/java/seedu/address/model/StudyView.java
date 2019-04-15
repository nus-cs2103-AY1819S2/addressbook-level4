package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.property.SimpleObjectProperty;
import seedu.address.logic.DeckShuffler;
import seedu.address.logic.parser.StudyViewParser;
import seedu.address.logic.parser.ViewStateParser;
import seedu.address.model.deck.Card;
import seedu.address.model.deck.Deck;
import seedu.address.model.deck.exceptions.EmptyDeckException;
import seedu.address.ui.MainPanel;
import seedu.address.ui.StudyPanel;

/**
 * ViewState of TopDeck during a study session.
 */
public class StudyView implements ViewState {
    private final Deck activeDeck;
    private final SimpleObjectProperty<StudyState> currentStudyState = new SimpleObjectProperty<>();
    private final SimpleObjectProperty<String> textShown = new SimpleObjectProperty<>();
    private final SimpleObjectProperty<String> userAnswer = new SimpleObjectProperty<>();
    private Card currentCard;
    private DeckShuffler deckShuffler;

    public StudyView(Deck deck) throws EmptyDeckException {
        if (deck.isEmpty()) {
            throw new EmptyDeckException("Unable to create study view with empty deck");
        }
        this.activeDeck = deck;
        this.deckShuffler = new DeckShuffler(activeDeck);
        generateCard();
        setCurrentStudyState(StudyState.QUESTION);
    }

    public StudyView(StudyView studyView) {
        this.activeDeck = studyView.getActiveDeck();
        this.deckShuffler = new DeckShuffler(studyView.getDeckShuffler());
        this.setCurrentCard(studyView.getCurrentCard());
        this.setCurrentStudyState(studyView.getCurrentStudyState());
    }

    public DeckShuffler getDeckShuffler() {
        return deckShuffler;
    }


    public Deck getActiveDeck() {
        return activeDeck;
    }

    public Card getCurrentCard() {
        return currentCard;
    }

    /**
     * Sets the current card to be studied.
     */
    public void setCurrentCard(Card card) {
        requireNonNull(card);
        currentCard = card;
        updateTextShown();
    }

    /**
     * Generates the next card to be studied.
     */
    public void generateCard() {
        Card card = deckShuffler.generateCard();
        setCurrentCard(card);
    }

    public StudyState getCurrentStudyState() {
        return currentStudyState.getValue();
    }

    public boolean isInQuestionState() {
        return currentStudyState.getValue() == StudyState.QUESTION;
    }

    public void setCurrentStudyState(StudyState state) {
        requireNonNull(state);
        currentStudyState.setValue(state);
        updateTextShown();
    }

    /**
     * Updates the text shown in the UI.
     */
    private void updateTextShown() {
        String text = (getCurrentStudyState() == StudyState.QUESTION) ? currentCard
                .getQuestion() : currentCard.getAnswer();
        textShown.setValue(text);
    }

    /**
     * Returns the current textShown
     */
    public ReadOnlyProperty<String> textShownProperty() {
        updateTextShown();
        return textShown;
    }

    public String getUserAnswer() {
        return userAnswer.getValue();
    }

    public void setUserAnswer(String answer) {
        requireNonNull(answer);
        userAnswer.setValue(answer);
    }

    @Override
    public ViewStateParser getViewStateParser() {
        return new StudyViewParser(this);
    }

    @Override
    public MainPanel getPanel() {
        return new StudyPanel(textShown, currentStudyState, userAnswer);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof StudyView)) {
            return false;
        }
        // state check
        StudyView other = (StudyView) obj;
        return Objects.equals(currentStudyState.getValue(), other.currentStudyState.getValue()) && Objects
                .equals(deckShuffler, other.deckShuffler);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currentStudyState.getValue(), deckShuffler);
    }

    /**
     * The type of possible states that the study view can have.
     */
    public enum StudyState { QUESTION, ANSWER }


}
