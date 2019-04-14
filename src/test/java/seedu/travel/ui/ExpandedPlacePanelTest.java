package seedu.travel.ui;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.travel.model.place.Photo;
import seedu.travel.model.place.Place;
import seedu.travel.testutil.Assert;
import seedu.travel.testutil.PlaceBuilder;

public class ExpandedPlacePanelTest extends GuiUnitTest {

    public static final String INVALID_PATH = "C://";
    public static final String EMPTY_PATH = "pBSgcMnA";

    @Test
    public void equals() {
        Place place = new PlaceBuilder().build();
        ExpandedPlacePanel placePanel = new ExpandedPlacePanel(place);

        // same place -> returns true
        ExpandedPlacePanel copy = new ExpandedPlacePanel(place);
        assertTrue(placePanel.equals(copy));

        // same object -> returns true
        assertTrue(placePanel.equals(placePanel));

        // null -> returns false
        assertFalse(placePanel.equals(null));

        // different types -> returns false
        assertFalse(placePanel.equals(0));

        // different place -> returns false
        Place differentPlace = new PlaceBuilder().withName("differentName").build();
        assertFalse(placePanel.equals(new ExpandedPlacePanel(differentPlace)));
    }

    @Test
    public void imageDisplay() {
        Place place = new PlaceBuilder().build();
        ExpandedPlacePanel test = new ExpandedPlacePanel(place);

        // null path
        Photo nullPath = new Photo(EMPTY_PATH);
        nullPath.setFilepath(null);
        Assert.assertThrows(NullPointerException.class, () -> test.initImg(nullPath));

        // invalid path, image file not found
        Photo invalidPath = new Photo(EMPTY_PATH);
        invalidPath.setFilepath(INVALID_PATH);
        assertFalse(test.initImg(invalidPath));

        // valid path, empty image
        Photo emptyPath = new Photo(EMPTY_PATH);
        assertTrue(test.initImg(emptyPath));
    }

}
