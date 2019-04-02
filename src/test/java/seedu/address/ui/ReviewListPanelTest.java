package seedu.address.ui;

import static java.time.Duration.ofMillis;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static seedu.address.testutil.TypicalReviews.getTypicalReviews;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysReview;

import java.sql.Timestamp;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.ReviewCardHandle;
import guitests.guihandles.ReviewListPanelHandle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.restaurant.Restaurant;
import seedu.address.model.review.Entry;
import seedu.address.model.review.Rating;
import seedu.address.model.review.Review;
import seedu.address.testutil.RestaurantBuilder;

public class ReviewListPanelTest extends GuiUnitTest {
    private static final ObservableList<Review> TYPICAL_REVIEWS =
            FXCollections.observableList(getTypicalReviews());

    private static final long CARD_CREATION_AND_DELETION_TIMEOUT = 1000;

    private SimpleObjectProperty<Restaurant> selectedRestaurant = new SimpleObjectProperty<>();
    private ReviewListPanel reviewListPanel;
    private ReviewListPanelHandle reviewListPanelHandle;

    @Before
    public void setUp() {
        guiRobot.interact(() -> reviewListPanel = new ReviewListPanel(selectedRestaurant));
        uiPartRule.setUiPart(reviewListPanel);

        reviewListPanelHandle = new ReviewListPanelHandle(getChildNode(reviewListPanel.getRoot(),
                ReviewListPanelHandle.REVIEW_LIST_VIEW_ID));
    }

    @Test
    public void display() {
        initUi(TYPICAL_REVIEWS);

        for (int i = 0; i < TYPICAL_REVIEWS.size(); i++) {
            reviewListPanelHandle.navigateToCard(TYPICAL_REVIEWS.get(i));
            Review expectedReview = TYPICAL_REVIEWS.get(i);
            ReviewCardHandle actualCard = reviewListPanelHandle.getReviewCardHandle(i);

            assertCardDisplaysReview(expectedReview, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }

    /**
     * Verifies that creating and deleting large number of reviews in {@code ReviewListPanel} requires lesser
     * than {@code CARD_CREATION_AND_DELETION_TIMEOUT} milliseconds to execute.
     */
    @Test
    public void performanceTest() {
        ObservableList<Review> backingList = createBackingList(10000);

        assertTimeoutPreemptively(ofMillis(CARD_CREATION_AND_DELETION_TIMEOUT), () -> {
            initUi(backingList);
            guiRobot.interact(backingList::clear);
        }, "Creation and deletion of review cards exceeded time limit");
    }

    /**
     * Returns a restaurant containing a list of {@code reviewCount} reviews that is used to populate the
     * {@code ReviewListPanel}.
     */
    private ObservableList<Review> createBackingList(int reviewCount) {
        ObservableList<Review> backingList = FXCollections.observableArrayList();
        Timestamp timestamp = Timestamp.valueOf("2019-03-03 09:00:00");

        for (int i = 0; i < reviewCount; i++) {
            Entry entry = new Entry(i + "a");
            Rating rating = new Rating("4.5");
            timestamp.setTime(timestamp.getTime() + i * 1000);
            Review review = new Review(entry, rating, timestamp);
            backingList.add(review);
        }
        return backingList;
    }

    /**
     * Initializes {@code reviewListPanelHandle} with a {@code ReviewListPanel} backed by {@code backingList}.
     * Also shows the {@code Stage} that displays only {@code ReviewListPanel}.
     */
    private void initUi(ObservableList<Review> backingList) {
        Restaurant testRestaurant = new RestaurantBuilder().withReviews(backingList).build();
        ObservableValue<Restaurant> obsRestaurant = new SimpleObjectProperty<>(testRestaurant);

        guiRobot.interact(() -> selectedRestaurant.set(testRestaurant));
    }
}
