package seedu.travel.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.travel.ui.testutil.GuiTestAssert.assertCardDisplaysPlace;

import org.junit.Test;

import guitests.guihandles.PlaceCardHandle;
import seedu.travel.model.place.Place;
import seedu.travel.testutil.PlaceBuilder;

public class PlaceCardTest extends GuiUnitTest {

    @Test
    public void display() {
        // no tags
        Place placeWithNoTags = new PlaceBuilder().withTags(new String[0]).build();
        PlaceCard placeCard = new PlaceCard(placeWithNoTags, 1);
        uiPartRule.setUiPart(placeCard);
        assertCardDisplay(placeCard, placeWithNoTags, 1);

        // with tags
        Place placeWithTags = new PlaceBuilder().build();
        placeCard = new PlaceCard(placeWithTags, 2);
        uiPartRule.setUiPart(placeCard);
        assertCardDisplay(placeCard, placeWithTags, 2);
    }

    @Test
    public void equals() {
        Place place = new PlaceBuilder().build();
        PlaceCard placeCard = new PlaceCard(place, 0);

        // same place, same index -> returns true
        PlaceCard copy = new PlaceCard(place, 0);
        assertTrue(placeCard.equals(copy));

        // same object -> returns true
        assertTrue(placeCard.equals(placeCard));

        // null -> returns false
        assertFalse(placeCard.equals(null));

        // different types -> returns false
        assertFalse(placeCard.equals(0));

        // different place, same index -> returns false
        Place differentPlace = new PlaceBuilder().withName("differentName").build();
        assertFalse(placeCard.equals(new PlaceCard(differentPlace, 0)));

        // same place, different index -> returns false
        assertFalse(placeCard.equals(new PlaceCard(place, 1)));
    }

    /**
     * Asserts that {@code placeCard} displays the details of {@code expectedPlace} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(PlaceCard placeCard, Place expectedPlace, int expectedId) {
        guiRobot.pauseForHuman();

        PlaceCardHandle placeCardHandle = new PlaceCardHandle(placeCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", placeCardHandle.getId());

        // verify place details are displayed correctly
        assertCardDisplaysPlace(expectedPlace, placeCardHandle);
    }
}
