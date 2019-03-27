package seedu.address.model.pdf;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.pdf.exceptions.DuplicatePdfException;
import seedu.address.model.pdf.exceptions.PdfNotFoundException;

/**
 * A list of pdfs that enforces uniqueness between its elements and does not allow nulls.
 * A pdf is considered unique by comparing using {@code Pdf#isSamePdf(Pdf)}. As such, adding and updating of
 * pdfs uses Pdf#isSamePdf(Pdf) for equality so as to ensure that the pdf being added or updated is
 * unique in terms of identity in the UniquePdfList. However, the removal of a pdf uses Pdf#equals(Object) so
 * as to ensure that the pdf with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Pdf#isSamePdf(Pdf)
 */
public class UniquePdfList implements Iterable<Pdf> {

    private final ObservableList<Pdf> internalList = FXCollections.observableArrayList();
    private final ObservableList<Pdf> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent pdf as the given argument.
     */
    public boolean contains(Pdf toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSamePdf);
    }

    /**
     * Adds a pdf to the list.
     * The pdf must not already exist in the list.
     */
    public void add(Pdf toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicatePdfException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the pdf {@code target} in the list with {@code editedPdf}.
     * {@code target} must exist in the list.
     * The pdf identity of {@code editedPdf} must not be the same as another existing pdf in the list.
     */
    public void setPdf(Pdf target, Pdf editedPdf) {
        requireAllNonNull(target, editedPdf);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new PdfNotFoundException();
        }

        if (!target.isSamePdf(editedPdf) && contains(editedPdf)) {
            throw new DuplicatePdfException();
        }

        internalList.set(index, editedPdf);
    }

    /**
     * Removes the equivalent pdf from the list.
     * The pdf must exist in the list.
     */
    public void remove(Pdf toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new PdfNotFoundException();
        }
    }

    public void setPdfs(UniquePdfList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code pdfs}.
     * {@code pdfs} must not contain duplicate pdfs.
     */
    public void setPdfs(List<Pdf> pdfs) {
        requireAllNonNull(pdfs);
        if (!pdfsAreUnique(pdfs)) {
            throw new DuplicatePdfException();
        }

        internalList.setAll(pdfs);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Pdf> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Pdf> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniquePdfList // instanceof handles nulls
                        && internalList.equals(((UniquePdfList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code pdfs} contains only unique pdfs.
     */
    private boolean pdfsAreUnique(List<Pdf> pdfs) {
        for (int i = 0; i < pdfs.size() - 1; i++) {
            for (int j = i + 1; j < pdfs.size(); j++) {
                if (pdfs.get(i).isSamePdf(pdfs.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
