package seedu.address.logic;

import java.nio.file.Path;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ReadOnlyTopDeck;
import seedu.address.model.deck.Card;
import seedu.address.model.deck.Deck;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns TopDeck.
     *
     * @see seedu.address.model.Model#getTopDeck()
     */
    ReadOnlyTopDeck getTopDeck();

    /**
     * Returns an unmodifiable view of the filtered list of decks
     */
    ObservableList<Deck> getFilteredDeckList();

    /** Returns an unmodifiable view of the filtered list of cards */
    ObservableList<Card> getFilteredCardList();

    /**
     * Returns an unmodifiable view of the list of commands entered by the user.
     * The list is ordered from the least recent command to the most recent command.
     */
    ObservableList<String> getHistory();

    /**
     * Returns the user prefs' TopDeck file path.
     */
    Path getTopDeckFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Selected card in the filtered card list.
     * null if no card is selected.
     *
     * @see seedu.address.model.Model#selectedCardProperty()
     */
    ReadOnlyProperty<Card> selectedCardProperty();

    /**
     * Sets the selected card in the filtered card list.
     *
     * @see seedu.address.model.Model#setSelectedCard(Card)
     */
    void setSelectedCard(Card card);

    /**
     * Selected deck in the filtered deck list.
     * null if no deck is selected.
     *
     * @see seedu.address.model.Model#selectedDeckProperty()
     */
    ReadOnlyProperty<Deck> selectedDeckProperty();

    /**
     * Sets the selected deck in the filtered deck list.
     *
     * @see seedu.address.model.Model#setSelectedDeck(Deck)
     */
    void setSelectedDeck(Deck deck);
}
