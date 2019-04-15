package seedu.pdf.ui.testutil;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import guitests.guihandles.PdfCardHandle;
import guitests.guihandles.PdfListPanelHandle;
import guitests.guihandles.ResultDisplayHandle;
import seedu.pdf.model.pdf.Pdf;

/**
 * A set of assertion methods useful for writing GUI tests.
 */
public class GuiTestAssert {
    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertCardEquals(PdfCardHandle expectedCard, PdfCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getDirectory(), actualCard.getDirectory());
        assertEquals(expectedCard.getSize(), actualCard.getSize());
        assertEquals(expectedCard.getName(), actualCard.getName());
        assertEquals(expectedCard.getDeadline(), actualCard.getDeadline());
        assertEquals(expectedCard.getTags(), actualCard.getTags());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedPdf}.
     */
    public static void assertCardDisplaysPdf(Pdf expectedPdf, PdfCardHandle actualCard) {
        assertEquals(expectedPdf.getName().getFullName(), actualCard.getName());
        assertEquals(expectedPdf.getTags().stream().map(tag -> tag.tagName).collect(Collectors.toList()),
                actualCard.getTags());
    }

    /**
     * Asserts that the list in {@code pdfListPanelHandle} displays the details of {@code pdfs} correctly and
     * in the correct order.
     */
    public static void assertListMatching(PdfListPanelHandle pdfListPanelHandle, Pdf... pdfs) {
        for (int i = 0; i < pdfs.length; i++) {
            pdfListPanelHandle.navigateToCard(i);
            assertCardDisplaysPdf(pdfs[i], pdfListPanelHandle.getPdfCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code pdfListPanelHandle} displays the details of {@code pdfs} correctly and
     * in the correct order.
     */
    public static void assertListMatching(PdfListPanelHandle pdfListPanelHandle, List<Pdf> pdfs) {
        assertListMatching(pdfListPanelHandle, pdfs.toArray(new Pdf[0]));
    }

    /**
     * Asserts the size of the list in {@code pdfListPanelHandle} equals to {@code size}.
     */
    public static void assertListSize(PdfListPanelHandle pdfListPanelHandle, int size) {
        int numberOfPdf = pdfListPanelHandle.getListSize();
        assertEquals(size, numberOfPdf);
    }

    /**
     * Asserts the message shown in {@code resultDisplayHandle} equals to {@code expected}.
     */
    public static void assertResultMessage(ResultDisplayHandle resultDisplayHandle, String expected) {
        assertEquals(expected, resultDisplayHandle.getText());
    }
}
