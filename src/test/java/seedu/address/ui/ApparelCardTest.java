package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysPerson;

import org.junit.Test;

import guitests.guihandles.PersonCardHandle;
import seedu.address.model.apparel.Apparel;
import seedu.address.testutil.ApparelBuilder;

public class ApparelCardTest extends GuiUnitTest {

    @Test
    public void display() {
        // no tags
        Apparel apparelWithNoTags = new ApparelBuilder().build();
        PersonCard personCard = new PersonCard(apparelWithNoTags, 1);
        uiPartRule.setUiPart(personCard);
        assertCardDisplay(personCard, apparelWithNoTags, 1);

        // with tags
        Apparel apparelWithTags = new ApparelBuilder().build();
        personCard = new PersonCard(apparelWithTags, 2);
        uiPartRule.setUiPart(personCard);
        assertCardDisplay(personCard, apparelWithTags, 2);
    }

    @Test
    public void equals() {
        Apparel apparel = new ApparelBuilder().build();
        PersonCard personCard = new PersonCard(apparel, 0);

        // same apparel, same index -> returns true
        PersonCard copy = new PersonCard(apparel, 0);
        assertTrue(personCard.equals(copy));

        // same object -> returns true
        assertTrue(personCard.equals(personCard));

        // null -> returns false
        assertFalse(personCard.equals(null));

        // different types -> returns false
        assertFalse(personCard.equals(0));

        // different apparel, same index -> returns false
        Apparel differentApparel = new ApparelBuilder().withName("differentName").build();
        assertFalse(personCard.equals(new PersonCard(differentApparel, 0)));

        // same apparel, different index -> returns false
        assertFalse(personCard.equals(new PersonCard(apparel, 1)));
    }

    /**
     * Asserts that {@code personCard} displays the details of {@code expectedApparel} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(PersonCard personCard, Apparel expectedApparel, int expectedId) {
        guiRobot.pauseForHuman();

        PersonCardHandle personCardHandle = new PersonCardHandle(personCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", personCardHandle.getId());

        // verify apparel details are displayed correctly
        assertCardDisplaysPerson(expectedApparel, personCardHandle);
    }
}
