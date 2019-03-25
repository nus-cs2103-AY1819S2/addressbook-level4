package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysPerson;

import org.junit.Test;

import guitests.guihandles.PersonCardHandle;
import seedu.address.model.person.ModuleTaken;
import seedu.address.testutil.ModuleTakenBuilder;

public class ModuleTakenCardTest extends GuiUnitTest {

    @Test
    public void display() {
        // no tags
        ModuleTaken moduleTakenWithNoTags = new ModuleTakenBuilder().withTags(new String[0]).build();
        PersonCard personCard = new PersonCard(moduleTakenWithNoTags, 1);
        uiPartRule.setUiPart(personCard);
        assertCardDisplay(personCard, moduleTakenWithNoTags, 1);

        // with tags
        ModuleTaken moduleTakenWithTags = new ModuleTakenBuilder().build();
        personCard = new PersonCard(moduleTakenWithTags, 2);
        uiPartRule.setUiPart(personCard);
        assertCardDisplay(personCard, moduleTakenWithTags, 2);
    }

    @Test
    public void equals() {
        ModuleTaken moduleTaken = new ModuleTakenBuilder().build();
        PersonCard personCard = new PersonCard(moduleTaken, 0);

        // same moduleTaken, same index -> returns true
        PersonCard copy = new PersonCard(moduleTaken, 0);
        assertTrue(personCard.equals(copy));

        // same object -> returns true
        assertTrue(personCard.equals(personCard));

        // null -> returns false
        assertFalse(personCard.equals(null));

        // different types -> returns false
        assertFalse(personCard.equals(0));

        // different moduleTaken, same index -> returns false
        ModuleTaken differentModuleTaken = new ModuleTakenBuilder().withModuleInfoCode("CS2101").build();
        assertFalse(personCard.equals(new PersonCard(differentModuleTaken, 0)));

        // same moduleTaken, different index -> returns false
        assertFalse(personCard.equals(new PersonCard(moduleTaken, 1)));
    }

    /**
     * Asserts that {@code personCard} displays the details of {@code expectedModuleTaken} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(PersonCard personCard, ModuleTaken expectedModuleTaken, int expectedId) {
        guiRobot.pauseForHuman();

        PersonCardHandle personCardHandle = new PersonCardHandle(personCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", personCardHandle.getId());

        // verify moduleTaken details are displayed correctly
        assertCardDisplaysPerson(expectedModuleTaken, personCardHandle);
    }
}
