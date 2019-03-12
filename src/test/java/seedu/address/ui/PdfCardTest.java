package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysPerson;

import org.junit.Test;

import guitests.guihandles.PersonCardHandle;
import seedu.address.model.pdf.Pdf;
import seedu.address.testutil.PersonBuilder;

public class PdfCardTest extends GuiUnitTest {

    @Test
    public void display() {
        // no tags
        Pdf pdfWithNoTags = new PersonBuilder().withTags(new String[0]).build();
        PersonCard personCard = new PersonCard(pdfWithNoTags, 1);
        uiPartRule.setUiPart(personCard);
        assertCardDisplay(personCard, pdfWithNoTags, 1);

        // with tags
        Pdf pdfWithTags = new PersonBuilder().build();
        personCard = new PersonCard(pdfWithTags, 2);
        uiPartRule.setUiPart(personCard);
        assertCardDisplay(personCard, pdfWithTags, 2);
    }

    @Test
    public void equals() {
        Pdf pdf = new PersonBuilder().build();
        PersonCard personCard = new PersonCard(pdf, 0);

        // same pdf, same index -> returns true
        PersonCard copy = new PersonCard(pdf, 0);
        assertTrue(personCard.equals(copy));

        // same object -> returns true
        assertTrue(personCard.equals(personCard));

        // null -> returns false
        assertFalse(personCard.equals(null));

        // different types -> returns false
        assertFalse(personCard.equals(0));

        // different pdf, same index -> returns false
        Pdf differentPdf = new PersonBuilder().withName("differentName").build();
        assertFalse(personCard.equals(new PersonCard(differentPdf, 0)));

        // same pdf, different index -> returns false
        assertFalse(personCard.equals(new PersonCard(pdf, 1)));
    }

    /**
     * Asserts that {@code personCard} displays the details of {@code expectedPdf} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(PersonCard personCard, Pdf expectedPdf, int expectedId) {
        guiRobot.pauseForHuman();

        PersonCardHandle personCardHandle = new PersonCardHandle(personCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", personCardHandle.getId());

        // verify pdf details are displayed correctly
        assertCardDisplaysPerson(expectedPdf, personCardHandle);
    }
}
