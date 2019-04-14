package seedu.pdf.model.pdf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.pdf.testutil.TypicalPdfs.SAMPLE_PDF_6;
import static seedu.pdf.testutil.TypicalPdfs.SAMPLE_PDF_7;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.pdf.model.pdf.exceptions.DuplicatePdfException;
import seedu.pdf.model.pdf.exceptions.PdfNotFoundException;
import seedu.pdf.testutil.PdfBuilder;





public class UniquePdfListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniquePdfList uniquePdfList = new UniquePdfList();

    @Test
    public void contains_nullPdf_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniquePdfList.contains(null);
    }

    @Test
    public void contains_pdfNotInList_returnsFalse() {
        assertFalse(uniquePdfList.contains(SAMPLE_PDF_7));
    }

    @Test
    public void contains_pdfInList_returnsTrue() {
        uniquePdfList.add(SAMPLE_PDF_7);
        assertTrue(uniquePdfList.contains(SAMPLE_PDF_7));
    }

    @Test
    public void contains_pdfWithSameIdentityFieldsInList_returnsTrue() {
        uniquePdfList.add(SAMPLE_PDF_7);
        Pdf editedPdf = new PdfBuilder(SAMPLE_PDF_7).build();
        assertTrue(uniquePdfList.contains(editedPdf));
    }

    @Test
    public void add_nullPdf_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniquePdfList.add(null);
    }

    @Test
    public void add_duplicatePdf_throwsDuplicatePdfException() {
        uniquePdfList.add(SAMPLE_PDF_7);
        thrown.expect(DuplicatePdfException.class);
        uniquePdfList.add(SAMPLE_PDF_7);
    }

    @Test
    public void setPdf_nullTargetPdf_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniquePdfList.setPdf(null, SAMPLE_PDF_7);
    }

    @Test
    public void setPdf_nullEditedPdf_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniquePdfList.setPdf(SAMPLE_PDF_7, null);
    }

    @Test
    public void setPdf_targetPdfNotInList_throwsPdfNotFoundException() {
        thrown.expect(PdfNotFoundException.class);
        uniquePdfList.setPdf(SAMPLE_PDF_7, SAMPLE_PDF_7);
    }

    @Test
    public void setPdf_editedPdfIsSamePdf_success() {
        uniquePdfList.add(SAMPLE_PDF_7);
        uniquePdfList.setPdf(SAMPLE_PDF_7, SAMPLE_PDF_7);
        UniquePdfList expectedUniquePdfList = new UniquePdfList();
        expectedUniquePdfList.add(SAMPLE_PDF_7);
        assertEquals(expectedUniquePdfList, uniquePdfList);
    }

    @Test
    public void setPdf_editedPdfHasSameIdentity_success() {
        uniquePdfList.add(SAMPLE_PDF_7);
        Pdf editedAlice = new PdfBuilder(SAMPLE_PDF_7).build();
        uniquePdfList.setPdf(SAMPLE_PDF_7, editedAlice);
        UniquePdfList expectedUniquePdfList = new UniquePdfList();
        expectedUniquePdfList.add(editedAlice);
        assertEquals(expectedUniquePdfList, uniquePdfList);
    }

    @Test
    public void setPdf_editedPdfHasDifferentIdentity_success() {
        uniquePdfList.add(SAMPLE_PDF_7);
        uniquePdfList.setPdf(SAMPLE_PDF_7, SAMPLE_PDF_6);
        UniquePdfList expectedUniquePdfList = new UniquePdfList();
        expectedUniquePdfList.add(SAMPLE_PDF_6);
        assertEquals(expectedUniquePdfList, uniquePdfList);
    }

    @Test
    public void setPdf_editedPdfHasNonUniqueIdentity_throwsDuplicatePdfException() {
        uniquePdfList.add(SAMPLE_PDF_7);
        uniquePdfList.add(SAMPLE_PDF_6);
        thrown.expect(DuplicatePdfException.class);
        uniquePdfList.setPdf(SAMPLE_PDF_7, SAMPLE_PDF_6);
    }

    @Test
    public void remove_nullPdf_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniquePdfList.remove(null);
    }

    @Test
    public void remove_pdfDoesNotExist_throwsPdfNotFoundException() {
        thrown.expect(PdfNotFoundException.class);
        uniquePdfList.remove(SAMPLE_PDF_7);
    }

    @Test
    public void remove_existingPdf_removesPdf() {
        uniquePdfList.add(SAMPLE_PDF_7);
        uniquePdfList.remove(SAMPLE_PDF_7);
        UniquePdfList expectedUniquePdfList = new UniquePdfList();
        assertEquals(expectedUniquePdfList, uniquePdfList);
    }

    @Test
    public void setPdf_nullUniquePdfList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniquePdfList.setPdfs((UniquePdfList) null);
    }

    @Test
    public void setPdf_uniquePdfList_replacesOwnListWithProvidedUniquePdfList() {
        uniquePdfList.add(SAMPLE_PDF_7);
        UniquePdfList expectedUniquePdfList = new UniquePdfList();
        expectedUniquePdfList.add(SAMPLE_PDF_6);
        uniquePdfList.setPdfs(expectedUniquePdfList);
        assertEquals(expectedUniquePdfList, uniquePdfList);
    }

    @Test
    public void setPdf_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniquePdfList.setPdfs((List<Pdf>) null);
    }

    @Test
    public void setPdfs_list_replacesOwnListWithProvidedList() {
        uniquePdfList.add(SAMPLE_PDF_7);
        List<Pdf> pdfList = Collections.singletonList(SAMPLE_PDF_6);
        uniquePdfList.setPdfs(pdfList);
        UniquePdfList expectedUniquePdfList = new UniquePdfList();
        expectedUniquePdfList.add(SAMPLE_PDF_6);
        assertEquals(expectedUniquePdfList, uniquePdfList);
    }

    @Test
    public void setPdfs_listWithDuplicatePdfs_throwsDuplicatedfException() {
        List<Pdf> listWithDuplicatePdfs = Arrays.asList(SAMPLE_PDF_7, SAMPLE_PDF_7);
        thrown.expect(DuplicatePdfException.class);
        uniquePdfList.setPdfs(listWithDuplicatePdfs);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniquePdfList.asUnmodifiableObservableList().remove(0);
    }
}
