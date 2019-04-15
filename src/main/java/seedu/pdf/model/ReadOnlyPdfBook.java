package seedu.pdf.model;

import javafx.beans.Observable;
import javafx.collections.ObservableList;
import seedu.pdf.model.pdf.Pdf;

/**
 * Unmodifiable view of an pdf book
 */
public interface ReadOnlyPdfBook extends Observable {

    /**
     * Returns an unmodifiable view of the pdf list.
     * This list will not contain any duplicate pdfs.
     */
    ObservableList<Pdf> getPdfList();

}
