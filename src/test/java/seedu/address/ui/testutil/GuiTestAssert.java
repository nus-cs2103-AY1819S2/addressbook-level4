package seedu.address.ui.testutil;

import static org.junit.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

import guitests.guihandles.RestaurantCardHandle;
import guitests.guihandles.RestaurantListPanelHandle;
import guitests.guihandles.ResultDisplayHandle;
import guitests.guihandles.ReviewCardHandle;
import seedu.address.model.restaurant.Restaurant;
import seedu.address.model.review.Review;

/**
 * A set of assertion methods useful for writing GUI tests.
 */
public class GuiTestAssert {
    private static final SimpleDateFormat TIMESTAMP = new SimpleDateFormat("EEE, d MMM yyyy, h.mm aa");
    private static final String ONE_DP = "%.1f";

    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertCardEquals(RestaurantCardHandle expectedCard, RestaurantCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getAddress(), actualCard.getAddress());
        assertEquals(expectedCard.getEmail(), actualCard.getEmail());
        assertEquals(expectedCard.getName(), actualCard.getName());
        assertEquals(expectedCard.getPhone(), actualCard.getPhone());
        assertEquals(expectedCard.getTags(), actualCard.getTags());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedRestaurant}.
     */
    public static void assertCardDisplaysRestaurant(Restaurant expectedRestaurant, RestaurantCardHandle actualCard) {
        assertEquals(expectedRestaurant.getName().fullName, actualCard.getName());
        assertEquals(expectedRestaurant.getPhone().value, actualCard.getPhone());
        assertEquals(expectedRestaurant.getEmail().value, actualCard.getEmail());
        assertEquals(expectedRestaurant.getAddress().value, actualCard.getAddress());
        assertEquals(expectedRestaurant.getTags().stream().map(tag -> tag.tagName).collect(Collectors.toList()),
                actualCard.getTags());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedReview}.
     */
    public static void assertCardDisplaysReview(Review expectedReview, ReviewCardHandle actualCard) {
        assertEquals(expectedReview.getEntry().toString(), actualCard.getEntry());
        String formattedRating = String.format(ONE_DP, expectedReview.getRating().toFloat()) + " / 5.0";
        assertEquals(formattedRating, actualCard.getRating());
        String formattedTimeStamp = TIMESTAMP.format(expectedReview.getTimeStamp());
        assertEquals(formattedTimeStamp, actualCard.getTimestamp());
    }

    /**
     * Asserts that the list in {@code restaurantListPanelHandle} displays the details of {@code restaurants} correctly
     * and in the correct order.
     */
    public static void assertListMatching(RestaurantListPanelHandle restaurantListPanelHandle,
                                          Restaurant... restaurants) {
        for (int i = 0; i < restaurants.length; i++) {
            restaurantListPanelHandle.navigateToCard(i);
            assertCardDisplaysRestaurant(restaurants[i], restaurantListPanelHandle.getRestaurantCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code restaurantListPanelHandle} displays the details of {@code restaurants} correctly
     * and in the correct order.
     */
    public static void assertListMatching(RestaurantListPanelHandle restaurantListPanelHandle,
                                          List<Restaurant> restaurants) {
        assertListMatching(restaurantListPanelHandle, restaurants.toArray(new Restaurant[0]));
    }

    /**
     * Asserts the size of the list in {@code restaurantListPanelHandle} equals to {@code size}.
     */
    public static void assertListSize(RestaurantListPanelHandle restaurantListPanelHandle, int size) {
        int numberOfPeople = restaurantListPanelHandle.getListSize();
        assertEquals(size, numberOfPeople);
    }

    /**
     * Asserts the message shown in {@code resultDisplayHandle} equals to {@code expected}.
     */
    public static void assertResultMessage(ResultDisplayHandle resultDisplayHandle, String expected) {
        assertEquals(expected, resultDisplayHandle.getText());
    }
}
