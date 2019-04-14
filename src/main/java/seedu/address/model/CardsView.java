package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.Objects;
import java.util.function.Predicate;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.logic.parser.CardsViewParser;
import seedu.address.logic.parser.ViewStateParser;
import seedu.address.model.deck.Card;
import seedu.address.model.deck.Deck;
import seedu.address.ui.ListPanel;
import seedu.address.ui.MainPanel;

/**
 * ViewState of TopDeck when browsing a list of cards from a specific deck.
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

    public Deck getActiveDeck() {
        return activeDeck;
    }

    @Override
    public void updateFilteredList(Predicate<Card> predicate) {
        requireNonNull(predicate);
        filteredCards.setPredicate(predicate);
    }

    @Override
    public ObservableList<Card> getFilteredList() {
        return filteredCards;
    }

    @Override
    public void setSelectedItem(Card card) {
        selectedCard.setValue(card);
    }

    @Override
    public Card getSelectedItem() {
        return selectedCard.getValue();
    }

    @Override
    public ViewStateParser getViewStateParser() {
        return new CardsViewParser(this);
    }

    @Override
    public MainPanel getPanel() {
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
