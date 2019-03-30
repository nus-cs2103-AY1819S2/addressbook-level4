package seedu.knowitall.logic;

import java.nio.file.Path;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.knowitall.commons.core.GuiSettings;
import seedu.knowitall.logic.commands.CommandResult;
import seedu.knowitall.logic.commands.exceptions.CommandException;
import seedu.knowitall.logic.parser.exceptions.ParseException;
import seedu.knowitall.model.ReadOnlyCardFolder;
import seedu.knowitall.model.VersionedCardFolder;
import seedu.knowitall.model.card.Card;

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
     * Returns the CardFolder.
     *
     * @see seedu.knowitall.model.Model#getActiveCardFolder()
     */
    ReadOnlyCardFolder getCardFolder();

    /** Returns an unmodifiable view of the filtered list of cards */
    ObservableList<Card> getFilteredCards();

    /** Returns an unmodifiable view of the filtered folders list */
    ObservableList<VersionedCardFolder> getFilteredCardFolders();

    /**
     * Returns an unmodifiable view of the list of commands entered by the user.
     * The list is ordered from the least recent command to the most recent command.
     */
    ObservableList<String> getHistory();

    /**
     * Returns the user prefs' card folder file path.
     */
    Path getcardFolderFilesPath();

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
     * @see seedu.knowitall.model.Model#selectedCardProperty()
     */
    ReadOnlyProperty<Card> selectedCardProperty();

    /**
     * Sets the selected card in the filtered card list.
     *
     * @see seedu.knowitall.model.Model#setSelectedCard(Card)
     */
    void setSelectedCard(Card card);
}
