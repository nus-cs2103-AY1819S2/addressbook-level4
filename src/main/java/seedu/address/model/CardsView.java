package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.Objects;
import java.util.function.Predicate;

import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.layout.Region;
import seedu.address.logic.commands.AddCardCommand;
import seedu.address.logic.commands.BackCommand;
import seedu.address.logic.commands.ClearCardCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteCardCommand;
import seedu.address.logic.commands.EditCardCommand;
import seedu.address.logic.commands.FindCardCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.StudyDeckCommand;
import seedu.address.logic.parser.AddCardCommandParser;
import seedu.address.logic.parser.DeleteCardCommandParser;
import seedu.address.logic.parser.EditCardCommandParser;
import seedu.address.logic.parser.FindCardCommandParser;
import seedu.address.logic.parser.SelectCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.deck.Card;
import seedu.address.model.deck.Deck;
import seedu.address.ui.ListPanel;
import seedu.address.ui.UiPart;

/**
 * Stores the state of the Card's view.
 */
public class CardsView implements ListViewState<Card> {

    public final FilteredList<Card> filteredCards;
    private final SimpleObjectProperty<Card> selectedCard = new SimpleObjectProperty<>();
    private final Deck activeDeck;

    public CardsView(Deck deck) {
        this.activeDeck = deck;
        filteredCards = new FilteredList<>(deck.getCards().asUnmodifiableObservableList());
    }

    public CardsView(CardsView cardsView) {
        this(cardsView.getActiveDeck());
    }

    @Override
    public Command parse(String commandWord, String arguments) throws ParseException {
        switch (commandWord) {
            case AddCardCommand.COMMAND_WORD:
                return new AddCardCommandParser(this).parse(arguments);
            case ClearCardCommand.COMMAND_WORD:
                return new ClearCardCommand(this);
            case DeleteCardCommand.COMMAND_WORD:
                return new DeleteCardCommandParser(this).parse(arguments);
            case EditCardCommand.COMMAND_WORD:
                return new EditCardCommandParser(this).parse(arguments);
            case FindCardCommand.COMMAND_WORD:
                return new FindCardCommandParser(this).parse(arguments);
            case SelectCommand.COMMAND_WORD:
                return new SelectCommandParser(this).parse(arguments);
            case BackCommand.COMMAND_WORD:
                return new BackCommand();
            case StudyDeckCommand.COMMAND_WORD:
                return new StudyDeckCommand(activeDeck);
            default:
                throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    public Deck getActiveDeck() {
        return activeDeck;
    }

    /**
     * Updates the filtered list in CardsView.
     */
    @Override
    public void updateFilteredList(Predicate<Card> predicate) {
        requireNonNull(predicate);
        filteredCards.setPredicate(predicate);
    }

    @Override
    public ObservableList<Card> getFilteredList() {
        return filteredCards;
    }

    /**
     * Sets the selected Item in the filtered list.
     */
    @Override
    public void setSelectedItem(Card card) {
        selectedCard.setValue(card);
    }

    /**
     * Returns the selected Item in the filtered list.
     * null if no card is selected.
     */
    @Override
    public Card getSelectedItem() {
        return selectedCard.getValue();
    }

    public UiPart<Region> getPanel() {
        return new ListPanel<>(getFilteredList(), selectedCard, this::setSelectedItem);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof CardsView)) {
            return false;
        }

        // state check
        CardsView other = (CardsView) obj;
        return filteredCards.equals(other.filteredCards)
                && Objects.equals(selectedCard.getValue(), other.selectedCard.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(filteredCards, selectedCard.getValue());
    }
}
