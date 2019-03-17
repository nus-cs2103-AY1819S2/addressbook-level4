package seedu.address.logic;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.transformation.FilteredList;
import seedu.address.model.deck.Card;

public class CardsView implements ViewState {
    public final FilteredList<Card> filteredCards;
    private final SimpleObjectProperty<Card> selectedCard = new SimpleObjectProperty<>();

    public CardsView(FilteredList<Card> cardList) {
        filteredCards = cardList;
        filteredCards.addListener(this::ensureSelectedItemIsValid);
    }

    /**
     * Ensures {@code selectedItem} is a valid card in {@code filteredItems}.
     */
    private void ensureSelectedItemIsValid(ListChangeListener.Change<? extends Card> change) {
        while (change.next()) {
            if (selectedCard.getValue() == null) {
                // null is always a valid selected card, so we do not need to check that it is valid anymore.
                return;
            }

            boolean wasSelectedItemReplaced = change.wasReplaced() && change.getAddedSize() == change.getRemovedSize()
                    && change.getRemoved().contains(selectedCard.getValue());
            if (wasSelectedItemReplaced) {
                // Update selectedCard to its new value.
                int index = change.getRemoved().indexOf(selectedCard.getValue());
                selectedCard.setValue(change.getAddedSubList().get(index));
                continue;
            }

            boolean wasSelectedItemRemoved = change.getRemoved().stream()
                    .anyMatch(removedItem -> selectedCard.getValue().equals(removedItem));
            if (wasSelectedItemRemoved) {
                // Select the card that came before it in the list,
                // or clear the selection if there is no such card.
                selectedCard.setValue(change.getFrom() > 0 ? change.getList().get(change.getFrom() - 1) : null);
            }
        }
    }

}
