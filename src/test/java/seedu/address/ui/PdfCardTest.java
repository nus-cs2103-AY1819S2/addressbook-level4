package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysPerson;

import org.junit.Test;

import guitests.guihandles.PersonCardHandle;
import seedu.address.model.pdf.Pdf;
import seedu.address.testutil.PdfBuilder;

public class PdfCardTest extends GuiUnitTest {

    @Test
    public void display() {
        // no tags
        Pdf pdfWithNoTags = new PdfBuilder().withTags(new String[0]).build();
        PdfCard pdfCard = new PdfCard(pdfWithNoTags, 1);
        uiPartRule.setUiPart(pdfCard);
        assertCardDisplay(pdfCard, pdfWithNoTags, 1);

        // with tags
        Pdf pdfWithTags = new PdfBuilder().build();
        pdfCard = new PdfCard(pdfWithTags, 2);
        uiPartRule.setUiPart(pdfCard);
        assertCardDisplay(pdfCard, pdfWithTags, 2);
    }

    @Test
    public void equals() {
        Pdf pdf = new PdfBuilder().build();
        PdfCard pdfCard = new PdfCard(pdf, 0);

        // same pdf, same index -> returns true
        PdfCard copy = new PdfCard(pdf, 0);
        assertTrue(pdfCard.equals(copy));

        // same object -> returns true
        assertTrue(pdfCard.equals(pdfCard));

        // null -> returns false
        assertFalse(pdfCard.equals(null));

        // different types -> returns false
        assertFalse(pdfCard.equals(0));

        // different pdf, same index -> returns false
        Pdf differentPdf = new PdfBuilder().withName("differentName").build();
        assertFalse(pdfCard.equals(new PdfCard(differentPdf, 0)));

        // same pdf, different index -> returns false
        assertFalse(pdfCard.equals(new PdfCard(pdf, 1)));
    }

    /**
     * Asserts that {@code pdfCard} displays the details of {@code expectedPdf} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(PdfCard pdfCard, Pdf expectedPdf, int expectedId) {
        guiRobot.pauseForHuman();

        PersonCardHandle personCardHandle = new PersonCardHandle(pdfCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", personCardHandle.getId());

        // verify pdf details are displayed correctly
        assertCardDisplaysPerson(expectedPdf, personCardHandle);
    }
}
