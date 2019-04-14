package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.Objects;
import java.util.function.Predicate;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.layout.Region;
import seedu.address.logic.parser.DecksViewParser;
import seedu.address.logic.parser.ViewStateParser;
import seedu.address.model.deck.Deck;
import seedu.address.ui.ListPanel;
import seedu.address.ui.UiPart;

/**
 * ViewState of TopDeck when browsing the collection as a list of decks.
 */
public class DecksView implements ListViewState<Deck> {

    public final FilteredList<Deck> filteredDecks;

    public final SimpleObjectProperty<Deck> selectedDeck = new SimpleObjectProperty<>();

    public DecksView(FilteredList<Deck> deckList) {
        filteredDecks = deckList;
    }

    public DecksView(DecksView decksView) {
        this(new FilteredList<>(decksView.getFilteredList()));
    }

    @Override
    public ViewStateParser getViewStateParser() {
        return new DecksViewParser(this);
    }

    @Override
    public void updateFilteredList(Predicate<Deck> predicate) {
        requireNonNull(predicate);
        filteredDecks.setPredicate(predicate);
    }

    @Override
    public ObservableList<Deck> getFilteredList() {
        return filteredDecks;
    }

    @Override
    public void setSelectedItem(Deck deck) {
        selectedDeck.setValue(deck);
    }

    @Override
    public Deck getSelectedItem() {
        return selectedDeck.getValue();
    }

    @Override
    public UiPart<Region> getPanel() {
        return new ListPanel<>(getFilteredList(), selectedDeck, this::setSelectedItem);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof DecksView)) {
            return false;
        }

        // state check
        DecksView other = (DecksView) obj;
        return filteredDecks.equals(other.filteredDecks)
                && Objects.equals(selectedDeck.getValue(), other.selectedDeck.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(filteredDecks, selectedDeck.getValue());
    }
}
