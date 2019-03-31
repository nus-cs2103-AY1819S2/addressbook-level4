package seedu.address.logic;

import java.util.List;

import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.property.SimpleObjectProperty;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DoneCommand;
import seedu.address.logic.commands.GenerateQuestionCommand;
import seedu.address.logic.commands.ShowAnswerCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.deck.Card;
import seedu.address.model.deck.Deck;

/**
 * ViewState of the Application during a Study session.
 */
public class StudyView implements ViewState {
    private final Model model;
    private final Deck activeDeck;
    private Card currentCard;
    private final SimpleObjectProperty<StudyState> currentStudyState = new SimpleObjectProperty<>();
    private final SimpleObjectProperty<String> textShown = new SimpleObjectProperty<>();
    private final SimpleObjectProperty<String> userAnswer = new SimpleObjectProperty<>();
    private DeckShuffler deckShuffler;

    public final List<Card> listOfCards;

    /**
     * The type of possible states that the study view can have.
     */
    public enum StudyState {
        QUESTION, ANSWER
    }

    public StudyView(Model model, Deck deck) {
        this.model = model;
        this.activeDeck = deck;
        listOfCards = deck.getCards().internalList;
        setCurrentStudyState(StudyState.QUESTION);
        this.deckShuffler = new DeckShuffler(activeDeck);
        generateCard();
    }

    @Override
    public Command parse(String commandWord, String arguments) throws ParseException {

        switch (commandWord) {
            case DoneCommand.COMMAND_WORD:
                return new DoneCommand();
            default:
                if (getCurrentStudyState() == StudyState.QUESTION) {
                    return new ShowAnswerCommand(commandWord + arguments);
                } else {
                    return new GenerateQuestionCommand();
                }
        }
    }

    public Deck getActiveDeck() {
        return activeDeck;
    }

    //=========== Current Card ================================================================================

    /**
     * Sets the current card to be studied.
     */
    public void setCurrentCard(Card card) {
        currentCard = card;
    }

    /**
     * Generates the next card to be studied.
     */
    public void generateCard() {
        setCurrentCard(deckShuffler.generateCard());
        updateTextShown();
    };

    //=========== Study States ================================================================================

    public ReadOnlyProperty<StudyState> studyStateProperty() {
        return currentStudyState;
    }

    public void setCurrentStudyState(StudyState state) {
        currentStudyState.setValue(state);
    }

    public StudyState getCurrentStudyState() {
        return currentStudyState.getValue();
    }

    //=========== TextShown ================================================================================

    /**
     * Updates the text shown in the UI.
     */
    public void updateTextShown() {
        String text = (getCurrentStudyState() == StudyState.QUESTION)
                ? currentCard.getQuestion()
                : currentCard.getAnswer();
        textShown.setValue(text);
    }

    /**
     * Returns the current textShown
     */
    public ReadOnlyProperty<String> textShownProperty() {
        updateTextShown();
        return textShown;
    }

    //=========== User Answer ================================================================================

    /**
     * Returns the user's answer
     */
    public ReadOnlyProperty<String> userAnswerProperty() {
        return userAnswer;
    }

    public void setUserAnswer(String answer) {
        userAnswer.setValue(answer);
    }

    public String getUserAnswer() {
        return userAnswer.getValue();
    }

}
