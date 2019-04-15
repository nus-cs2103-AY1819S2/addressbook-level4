package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.RestaurantSummaryPanelHandle;
import javafx.beans.property.SimpleObjectProperty;
import seedu.address.model.restaurant.Restaurant;
import seedu.address.testutil.RestaurantBuilder;

public class RestaurantSummaryPanelTest extends GuiUnitTest {
    private static final String PLACEHOLDER_TEXT = "Select a restaurant to see its summary in this panel!";

    private SimpleObjectProperty<Restaurant> selectedRestaurant = new SimpleObjectProperty<>();
    private RestaurantSummaryPanel restaurantSummaryPanel;
    private RestaurantSummaryPanelHandle restaurantSummaryPanelHandle;

    @Before
    public void setUp() {
        guiRobot.interact(() -> restaurantSummaryPanel = new RestaurantSummaryPanel(selectedRestaurant));
        uiPartRule.setUiPart(restaurantSummaryPanel);

        restaurantSummaryPanelHandle = new RestaurantSummaryPanelHandle(getChildNode(restaurantSummaryPanel.getRoot(),
                RestaurantSummaryPanelHandle.SUMMARY_PANE_ID));
    }

    @Test
    public void displayPlaceholder() {
        String actualPlaceholderImgUrl = restaurantSummaryPanelHandle.getPlaceholderImg().getUrl();
        String expectedPlaceholderImgUrl = "/images/summary.png";
        assertTrue(actualPlaceholderImgUrl.endsWith(expectedPlaceholderImgUrl));

        String actualPlaceholder = restaurantSummaryPanelHandle.getSummaryPlaceholderVBoxHandle().getPlaceholder();
        String expectedPlaceholder = PLACEHOLDER_TEXT;
        assertEquals(actualPlaceholder, expectedPlaceholder);
    }

    @Test
    public void display() {
        Restaurant testRestaurant = new RestaurantBuilder().build();
        guiRobot.interact(() -> restaurantSummaryPanel.loadSummary(testRestaurant));

        assert(restaurantSummaryPanelHandle.getSummaryInfoVBoxHandle().equals(testRestaurant));
        assert(restaurantSummaryPanelHandle.getSummaryTitleVBoxHandle().equals(testRestaurant));
    }
}
