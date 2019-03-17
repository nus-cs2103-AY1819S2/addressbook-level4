package seedu.address.logic;

import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.transformation.FilteredList;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.parser.AddCommandParser;
import seedu.address.logic.parser.DeleteCommandParser;
import seedu.address.logic.parser.EditCommandParser;
import seedu.address.logic.parser.SelectCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.deck.Card;
import seedu.address.model.deck.Deck;

public class CardsView implements ViewState {
    private Model model;
    public final FilteredList<Card> filteredCards;
    private final SimpleObjectProperty<Card> selectedCard = new SimpleObjectProperty<>();
    public final Deck activeDeck = null;

    // TODO: pass in deck instead of card list
    public CardsView(Model model, FilteredList<Card> cardList) {
        this.model = model;
        filteredCards = cardList;
        filteredCards.addListener(this::ensureSelectedItemIsValid);
    }

    @Override
    public Command parse(String commandWord, String arguments) throws ParseException {
        switch (commandWord) {
            case AddCommand.COMMAND_WORD:
                return new AddCommandParser().parse(arguments);
            case DeleteCommand.COMMAND_WORD:
                return new DeleteCommandParser().parse(arguments);
            case EditCommand.COMMAND_WORD:
                return new EditCommandParser().parse(arguments);
            case SelectCommand.COMMAND_WORD:
                return new SelectCommandParser(model.getViewState()).parse(arguments);
            default:
                throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
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
