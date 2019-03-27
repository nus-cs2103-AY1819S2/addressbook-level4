package seedu.address.logic;

import java.nio.file.Path;
import java.util.List;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ReadOnlyPdfBook;
import seedu.address.model.pdf.Pdf;

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
     * Returns the PdfBook.
     *
     * @see seedu.address.model.Model#getPdfBook()
     */
    ReadOnlyPdfBook getAddressBook();

    /** Returns an unmodifiable view of the filtered list of persons */
    ObservableList<Pdf> getFilteredPersonList();

    /**
     * Returns an unmodifiable view of the list of commands entered by the user.
     * The list is ordered from the least recent command to the most recent command.
     */
    ObservableList<String> getHistory();

    /**
     * Returns the user prefs' address book file value.
     */
    Path getAddressBookFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Selected pdf in the filtered pdf list.
     * null if no pdf is selected.
     *
     * @see seedu.address.model.Model#selectedPdfProperty()
     */
    ReadOnlyProperty<Pdf> selectedPersonProperty();

    /**
     * Sets the selected pdf in the filtered pdf list.
     *
     * @see seedu.address.model.Model#setSelectedPdf(Pdf)
     */
    void setSelectedPerson(Pdf pdf);

    /**
     * Provides a list of Pdfs that are about to hit their deadines.
     *
     * @return list of pdfs that are due soon.
     */
    public List<Pdf> getDuePdfs();
}
