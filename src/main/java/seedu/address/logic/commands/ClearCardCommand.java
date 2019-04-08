package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CardsView;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.deck.Deck;

public class ClearCardCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Current deck has been cleared!";

    private final CardsView cardsView;

    /**
     * Creates an AddCardCommand to add the specified {@code Card}
     */
    public ClearCardCommand(CardsView cardsView) {
        requireNonNull(cardsView);

        this.cardsView = cardsView;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);

        Deck activeDeck = cardsView.getActiveDeck();

        model.setDeck(activeDeck, new Deck(activeDeck.getName()));
        model.commitTopDeck();
        return new UpdatePanelCommandResult(MESSAGE_SUCCESS);
    }
}
