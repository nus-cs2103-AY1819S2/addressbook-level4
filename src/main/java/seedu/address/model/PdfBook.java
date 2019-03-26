package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.beans.InvalidationListener;
import javafx.collections.ObservableList;
import seedu.address.commons.util.InvalidationListenerManager;
import seedu.address.model.pdf.Pdf;
import seedu.address.model.pdf.UniquePdfList;

/**
 * Wraps all data at the pdf-book level
 * Duplicates are not allowed (by .isSamePdf comparison)
 */
public class PdfBook implements ReadOnlyPdfBook {

    private final UniquePdfList pdfs;
    private final InvalidationListenerManager invalidationListenerManager = new InvalidationListenerManager();

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        pdfs = new UniquePdfList();
    }

    public PdfBook() {}

    /**
     * Creates an PdfBook using the Persons in the {@code toBeCopied}
     */
    public PdfBook(ReadOnlyPdfBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the pdf list with {@code pdfs}.
     * {@code pdfs} must not contain duplicate pdfs.
     */
    public void setPdfs(List<Pdf> pdfs) {
        this.pdfs.setPdfs(pdfs);
        indicateModified();
    }

    /**
     * Resets the existing data of this {@code PdfBook} with {@code newData}.
     */
    public void resetData(ReadOnlyPdfBook newData) {
        requireNonNull(newData);

        setPdfs(newData.getPdfList());
    }

    //// pdf-level operations

    /**
     * Returns true if a pdf with the same identity as {@code pdf} exists in the address book.
     */
    public boolean hasPdf(Pdf pdf) {
        requireNonNull(pdf);
        return pdfs.contains(pdf);
    }

    /**
     * Adds a pdf to the address book.
     * The pdf must not already exist in the address book.
     */
    public void addPdf(Pdf p) {
        pdfs.add(p);
        indicateModified();
    }

    /**
     * Replaces the given pdf {@code target} in the list with {@code editedPdf}.
     * {@code target} must exist in the address book.
     * The pdf identity of {@code editedPdf} must not be the same as another existing pdf in the address book.
     */
    public void setPdf(Pdf target, Pdf editedPdf) {
        requireNonNull(editedPdf);

        pdfs.setPdf(target, editedPdf);
        indicateModified();
    }

    /**
     * Removes {@code key} from this {@code PdfBook}.
     * {@code key} must exist in the address book.
     */
    public void removePdf(Pdf key) {
        pdfs.remove(key);
        indicateModified();
    }

    @Override
    public void addListener(InvalidationListener listener) {
        invalidationListenerManager.addListener(listener);
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        invalidationListenerManager.removeListener(listener);
    }

    /**
     * Notifies listeners that the address book has been modified.
     */
    protected void indicateModified() {
        invalidationListenerManager.callListeners(this);
    }

    //// util methods

    @Override
    public String toString() {
        return pdfs.asUnmodifiableObservableList().size() + " pdfs";
        // TODO: refine later
    }

    @Override
    public ObservableList<Pdf> getPdfList() {
        return pdfs.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PdfBook // instanceof handles nulls
                && pdfs.equals(((PdfBook) other).pdfs));
    }

    @Override
    public int hashCode() {
        return pdfs.hashCode();
    }
}
