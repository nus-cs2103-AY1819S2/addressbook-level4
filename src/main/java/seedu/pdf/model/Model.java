package seedu.pdf.model;

import static java.util.Comparator.reverseOrder;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.function.Predicate;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.pdf.commons.core.GuiSettings;
import seedu.pdf.model.pdf.Pdf;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Pdf> PREDICATE_SHOW_ALL_PDFS = unused -> true;

    /** {@code Comparator} that compares two PDFs alphabetically based on name, case insensitive */
    Comparator<Pdf> COMPARATOR_NAME_ASCENDING_PDFS = (o1, o2) ->
            o1.getName().toString().compareToIgnoreCase(o2.getName().toString());
    Comparator<Pdf> COMPARATOR_NAME_DESCENDING_PDFS = COMPARATOR_NAME_ASCENDING_PDFS.reversed();

    /** {@code Comparator} that compares two PDFs chronologically based on deadline */
    Comparator<Pdf> COMPARATOR_DEADLINE_ASCENDING_PDFS = (o1, o2) -> {
        if (!o1.getDeadline().exists()) {
            return 1;
        } else if (!o2.getDeadline().exists()) {
            return -1;
        } else {
            return o1.getDeadline().compareTo(o2.getDeadline());
        }
    };
    Comparator<Pdf> COMPARATOR_DEADLINE_DESCENDING_PDFS = Comparator.comparing(Pdf::getDeadline, reverseOrder());

    /** {@code Comparator} that compares two PDFs numerically based on size */
    Comparator<Pdf> COMPARATOR_SIZE_ASCENDING_PDFS = Comparator.comparing(Pdf::getSize);
    Comparator<Pdf> COMPARATOR_SIZE_DESCENDING_PDFS = COMPARATOR_SIZE_ASCENDING_PDFS.reversed();

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' pdf book file value.
     */
    Path getPdfBookFilePath();

    /**
     * Sets the user prefs' pdf book file value.
     */
    void setPdfBookFilePath(Path pdfBookFilePath);

    /**
     * Replaces pdf book data with the data in {@code pdfBook}.
     */
    void setPdfBook(ReadOnlyPdfBook pdfBook);

    /** Returns the PdfBook */
    ReadOnlyPdfBook getPdfBook();

    /**
     * Returns true if a pdf with the same identity as {@code pdf} exists in the pdf book.
     */
    boolean hasPdf(Pdf pdf);

    /**
     * Deletes the given pdf.
     * The pdf must exist in the pdf book.
     */
    void deletePdf(Pdf target);

    /**
     * Adds the given pdf.
     * {@code pdf} must not already exist in the pdf book.
     */
    void addPdf(Pdf pdf);

    /**
     * Replaces the given pdf {@code target} with {@code editedPdf}.
     * {@code target} must exist in the pdf book.
     * The pdf identity of {@code editedPdf} must not be the same as another existing pdf in the pdf book.
     */
    void setPdf(Pdf target, Pdf editedPdf);

    /** Returns an unmodifiable view of the filtered pdf list */
    ObservableList<Pdf> getFilteredPdfList();

    /**
     * Updates the filter of the filtered pdf list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPdfList(Predicate<Pdf> predicate);

    /**
     * Returns true if the model has previous pdf book states to restore.
     */
    boolean canUndoPdfBook();

    /**
     * Returns true if the model has undone pdf book states to restore.
     */
    boolean canRedoPdfBook();

    /**
     * Restores the model's pdf book to its previous state.
     */
    void undoPdfBook();

    /**
     * Restores the model's pdf book to its previously undone state.
     */
    void redoPdfBook();

    /**
     * Saves the current pdf book state for undo/redo.
     */
    void commitPdfBook();

    /**
     * Selected pdf in the filtered pdf list.
     * null if no pdf is selected.
     */
    ReadOnlyProperty<Pdf> selectedPdfProperty();

    /**
     * Returns the selected pdf in the filtered pdf list.
     * null if no pdf is selected.
     */
    Pdf getSelectedPdf();

    /**
     * Sets the selected pdf in the filtered pdf list.
     */
    void setSelectedPdf(Pdf pdf);
}
