package seedu.address.logic;

import java.util.List;

import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.property.SimpleObjectProperty;
import seedu.address.logic.commands.DoneCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.GenerateQuestionCommand;
import seedu.address.logic.commands.ShowAnswerCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.deck.Card;
import seedu.address.model.deck.Deck;

public class StudyView implements ViewState {
    private final Model model;
    public final List<Card> listOfCards;
    private final Deck activeDeck;
    private Card currentCard;
    private studyState currentStudyState = studyState.QUESTION;
    private final SimpleObjectProperty<String> textShown = new SimpleObjectProperty<>();
    private DeckShuffler deckShuffler;

    public enum studyState {
        QUESTION, ANSWER;
    }

    public StudyView(Model model, Deck deck) {
        this.model = model;
        this.activeDeck = deck;
        listOfCards = deck.getCards().internalList;
        setCurrentStudyState(studyState.QUESTION);
        this.deckShuffler = new DeckShuffler(activeDeck);
        generateCard();
    }

    @Override
    public Command parse(String commandWord, String arguments) throws ParseException {

        switch (commandWord) {
            case DoneCommand.COMMAND_WORD:
                return new DoneCommand();
            default:
                if (getCurrentStudyState() == studyState.QUESTION) {
                    return new ShowAnswerCommand();
                } else {
                    return new GenerateQuestionCommand();
                }
                //throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    public Deck getActiveDeck() {
        return activeDeck;
    }

    public void setCurrentCard(Card card) {
        currentCard = card;
    }


    public void setCurrentStudyState(studyState state) {
        currentStudyState = state;
    }

    public ReadOnlyProperty<String> textShownProperty() {
        updateTextShown();
        return textShown;
    }

    public studyState getCurrentStudyState() {
        return currentStudyState;
    }

    public void updateTextShown() {
        String text =  (currentStudyState == studyState.QUESTION)
                ? currentCard.getQuestion()
                : currentCard.getAnswer();
        textShown.setValue(text);
    }

    public void generateCard() {
        setCurrentCard(deckShuffler.generateCard());
        updateTextShown();
    };


}
