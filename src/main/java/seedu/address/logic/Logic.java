package seedu.address.logic;

import java.nio.file.Path;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ReadOnlyCardFolder;
import seedu.address.model.VersionedCardFolder;
import seedu.address.model.card.Card;

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
     * @see seedu.address.model.Model#getActiveCardFolder()
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
     * Returns the user prefs' folder folder file path.
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
     * Selected folder in the filtered folder list.
     * null if no folder is selected.
     *
     * @see seedu.address.model.Model#selectedCardProperty()
     */
    ReadOnlyProperty<Card> selectedCardProperty();

    /**
     * Sets the selected folder in the filtered folder list.
     *
     * @see seedu.address.model.Model#setSelectedCard(Card)
     */
    void setSelectedCard(Card card);
}
