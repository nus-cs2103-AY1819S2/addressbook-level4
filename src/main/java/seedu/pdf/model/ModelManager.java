package seedu.pdf.model;

import static java.util.Objects.requireNonNull;
import static seedu.pdf.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.pdf.commons.core.GuiSettings;
import seedu.pdf.commons.core.LogsCenter;

import seedu.pdf.model.pdf.Pdf;
import seedu.pdf.model.pdf.exceptions.PdfNotFoundException;

/**
 * Represents the in-memory model of the pdf book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedPdfBook versionedPdfBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Pdf> filteredPdfs;
    private final SimpleObjectProperty<Pdf> selectedPdf = new SimpleObjectProperty<>();

    /**
     * Initializes a ModelManager with the given pdfBook and userPrefs.
     */
    public ModelManager(ReadOnlyPdfBook pdfBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(pdfBook, userPrefs);

        logger.fine("Initializing with pdf book: " + pdfBook + " and user prefs " + userPrefs);

        versionedPdfBook = new VersionedPdfBook(pdfBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPdfs = new FilteredList<>(versionedPdfBook.getPdfList());
        filteredPdfs.addListener(this::ensureSelectedPdfIsValid);
    }

    public ModelManager() {
        this(new PdfBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getPdfBookFilePath() {
        return userPrefs.getPdfBookFilePath();
    }

    @Override
    public void setPdfBookFilePath(Path pdfBookFilePath) {
        requireNonNull(pdfBookFilePath);
        userPrefs.setPdfBookFilePath(pdfBookFilePath);
    }

    //=========== PdfBook ================================================================================

    @Override
    public void setPdfBook(ReadOnlyPdfBook pdfBook) {
        versionedPdfBook.resetData(pdfBook);
    }

    @Override
    public ReadOnlyPdfBook getPdfBook() {
        return versionedPdfBook;
    }

    @Override
    public boolean hasPdf(Pdf pdf) {
        requireNonNull(pdf);
        return versionedPdfBook.hasPdf(pdf);
    }

    @Override
    public void deletePdf(Pdf target) {
        versionedPdfBook.removePdf(target);
    }

    @Override
    public void addPdf(Pdf pdf) {
        versionedPdfBook.addPdf(pdf);
        updateFilteredPdfList(PREDICATE_SHOW_ALL_PDFS);
    }

    @Override
    public void setPdf(Pdf target, Pdf editedPdf) {
        requireAllNonNull(target, editedPdf);

        versionedPdfBook.setPdf(target, editedPdf);
    }

    //=========== Filtered Pdf List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Pdf} backed by the internal list of
     * {@code versionedPdfBook}
     */
    @Override
    public ObservableList<Pdf> getFilteredPdfList() {
        return filteredPdfs;
    }

    @Override
    public void updateFilteredPdfList(Predicate<Pdf> predicate) {
        requireNonNull(predicate);
        filteredPdfs.setPredicate(predicate);
    }

    //=========== Undo/Redo =================================================================================

    @Override
    public boolean canUndoPdfBook() {
        return versionedPdfBook.canUndo();
    }

    @Override
    public boolean canRedoPdfBook() {
        return versionedPdfBook.canRedo();
    }

    @Override
    public void undoPdfBook() {
        versionedPdfBook.undo();
    }

    @Override
    public void redoPdfBook() {
        versionedPdfBook.redo();
    }

    @Override
    public void commitPdfBook() {
        versionedPdfBook.commit();
    }

    //=========== Selected pdf ===========================================================================

    @Override
    public ReadOnlyProperty<Pdf> selectedPdfProperty() {
        return selectedPdf;
    }

    @Override
    public Pdf getSelectedPdf() {
        return selectedPdf.getValue();
    }

    @Override
    public void setSelectedPdf(Pdf pdf) {
        if (pdf != null && !filteredPdfs.contains(pdf)) {
            throw new PdfNotFoundException();
        }
        selectedPdf.setValue(pdf);
    }

    /**
     * Ensures {@code selectedPdf} is a valid pdf in {@code filteredPdfs}.
     */
    private void ensureSelectedPdfIsValid(ListChangeListener.Change<? extends Pdf> change) {
        while (change.next()) {
            if (selectedPdf.getValue() == null) {
                // null is always a valid selected pdf, so we do not need to check that it is valid anymore.
                return;
            }

            boolean wasSelectedPdfReplaced = change.wasReplaced() && change.getAddedSize() == change.getRemovedSize()
                    && change.getRemoved().contains(selectedPdf.getValue());
            if (wasSelectedPdfReplaced) {
                // Update selectedPdf to its new value.
                int index = change.getRemoved().indexOf(selectedPdf.getValue());
                selectedPdf.setValue(change.getAddedSubList().get(index));
                continue;
            }

            boolean wasSelectedPdfRemoved = change.getRemoved().stream()
                    .anyMatch(removedPdf -> selectedPdf.getValue().isSamePdf(removedPdf));
            if (wasSelectedPdfRemoved) {
                // Select the pdf that came before it in the list,
                // or clear the selection if there is no such pdf.
                selectedPdf.setValue(change.getFrom() > 0 ? change.getList().get(change.getFrom() - 1) : null);
            }
        }
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return versionedPdfBook.equals(other.versionedPdfBook)
                && userPrefs.equals(other.userPrefs)
                && filteredPdfs.equals(other.filteredPdfs)
                && Objects.equals(selectedPdf.get(), other.selectedPdf.get());
    }

}
