package seedu.address.ui;

import static java.time.Duration.ofMillis;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static seedu.address.testutil.TypicalBooks.getTypicalReviews;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_REVIEW;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysReview;
import static seedu.address.ui.testutil.GuiTestAssert.assertReviewCardEquals;

import org.junit.Test;

import guitests.guihandles.ReviewCardHandle;
import guitests.guihandles.ReviewListPanelHandle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.book.BookName;
import seedu.address.model.book.Review;
import seedu.address.model.book.ReviewTitle;


public class ReviewListPanelTest extends GuiUnitTest {
    private static final ObservableList<Review> TYPICAL_REVIEWS =
            FXCollections.observableList(getTypicalReviews());

    private static final long CARD_CREATION_AND_DELETION_TIMEOUT = 2500;

    private final SimpleObjectProperty<Review> selectedReview = new SimpleObjectProperty<>();
    private ReviewListPanelHandle reviewListPanelHandle;

    @Test
    public void display() {
        initUi(TYPICAL_REVIEWS);

        for (int i = 0; i < TYPICAL_REVIEWS.size(); i++) {
            reviewListPanelHandle.navigateToCard(TYPICAL_REVIEWS.get(i));
            Review expectedReview = TYPICAL_REVIEWS.get(i);
            ReviewCardHandle actualCard = reviewListPanelHandle.getReviewCardHandle(i);

            assertCardDisplaysReview(expectedReview, actualCard);
        }
    }

    @Test
    public void selection_modelSelectedReviewChanged_selectionChanges() {
        initUi(TYPICAL_REVIEWS);
        Review secondReview = TYPICAL_REVIEWS.get(INDEX_SECOND_REVIEW.getZeroBased());
        guiRobot.interact(() -> selectedReview.set(secondReview));
        guiRobot.pauseForHuman();

        ReviewCardHandle expectedReview = reviewListPanelHandle.getReviewCardHandle(INDEX_SECOND_REVIEW.getZeroBased());
        ReviewCardHandle selectedReview = reviewListPanelHandle.getHandleToSelectedCard();
        assertReviewCardEquals(expectedReview, selectedReview);
    }

    /**
     * Verifies that creating and deleting large number of Reviews in {@code ReviewListPanel} requires lesser than
     * {@code CARD_CREATION_AND_DELETION_TIMEOUT} milliseconds to execute.
     */
    @Test
    public void performanceTest() {
        ObservableList<Review> backingList = createBackingList(10000);

        assertTimeoutPreemptively(ofMillis(CARD_CREATION_AND_DELETION_TIMEOUT), () -> {
            initUi(backingList);
            guiRobot.interact(backingList::clear);
        }, "Creation and deletion of Review cards exceeded time limit");
    }

    /**
     * Returns a list of Reviews containing {@code ReviewCount} Reviews that is used to populate the
     * {@code ReviewListPanel}.
     */
    private ObservableList<Review> createBackingList(int ReviewCount) {
        ObservableList<Review> backingList = FXCollections.observableArrayList();
        for (int i = 0; i < ReviewCount; i++) {

            ReviewTitle name = new ReviewTitle(i + "a");
            BookName bookName = new BookName(i + "Alice");
            String reviewMessage = "message" + i;

            Review Review = new Review(name, bookName, reviewMessage);
            backingList.add(Review);
        }
        return backingList;
    }

    /**
     * Initializes {@code ReviewListPanelHandle} with a {@code ReviewListPanel} backed by {@code backingList}.
     * Also shows the {@code Stage} that displays only {@code ReviewListPanel}.
     */
    private void initUi(ObservableList<Review> backingList) {
        ReviewListPanel ReviewListPanel =
                new ReviewListPanel(backingList, selectedReview, selectedReview::set);
        uiPartRule.setUiPart(ReviewListPanel);

        reviewListPanelHandle = new ReviewListPanelHandle(getChildNode(ReviewListPanel.getRoot(),
                ReviewListPanelHandle.REVIEW_LIST_VIEW_ID));
    }
}
