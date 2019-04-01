package seedu.address.ui;

import static seedu.address.ui.testutil.GuiTestAssert.assertPanelDisplaysReview;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.BookBrowserPanelHandle;
import javafx.beans.property.SimpleObjectProperty;
import seedu.address.model.book.Review;
import seedu.address.testutil.ReviewBuilder;

public class BookBrowserPanelTest extends GuiUnitTest {
    private Review review = new ReviewBuilder().build();
    private SimpleObjectProperty<Review> selectedReview = new SimpleObjectProperty<>();
    private BookBrowserPanel browserPanel;
    private BookBrowserPanelHandle browserPanelHandle;

    @Before
    public void setUp() {
        guiRobot.interact(() -> browserPanel = new BookBrowserPanel(selectedReview));
        uiPartRule.setUiPart(browserPanel);

        browserPanelHandle = new BookBrowserPanelHandle(browserPanel.getRoot());
    }

    @Test
    public void display() throws Exception {
        guiRobot.interact(() -> selectedReview.set(review));
        guiRobot.pauseForHuman();
        assertPanelDisplaysReview(review, browserPanelHandle);
    }
}
