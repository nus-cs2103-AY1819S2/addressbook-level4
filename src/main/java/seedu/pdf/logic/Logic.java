package seedu.pdf.logic;

import java.nio.file.Path;
import java.util.List;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.pdf.commons.core.GuiSettings;
import seedu.pdf.logic.commands.CommandResult;
import seedu.pdf.logic.commands.exceptions.CommandException;
import seedu.pdf.logic.parser.exceptions.ParseException;
import seedu.pdf.model.ReadOnlyPdfBook;
import seedu.pdf.model.pdf.Pdf;

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
     * @see seedu.pdf.model.Model#getPdfBook()
     */
    ReadOnlyPdfBook getPdfBook();

    /** Returns an unmodifiable view of the filtered list of pdfs */
    ObservableList<Pdf> getFilteredPdfList();

    /**
     * Returns an unmodifiable view of the list of commands entered by the user.
     * The list is ordered from the least recent command to the most recent command.
     */
    ObservableList<String> getHistory();

    /**
     * Returns the user prefs' pdf book file value.
     */
    Path getPdfBookFilePath();

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
     * @see seedu.pdf.model.Model#selectedPdfProperty()
     */
    ReadOnlyProperty<Pdf> selectedPdfProperty();

    /**
     * Sets the selected pdf in the filtered pdf list.
     *
     * @see seedu.pdf.model.Model#setSelectedPdf(Pdf)
     */
    void setSelectedPdf(Pdf pdf);

    /**
     * Provides a list of Pdfs that are about to hit their deadines.
     *
     * @return list of pdfs that are due soon.
     */
    public List<Pdf> getDuePdfs();
}
