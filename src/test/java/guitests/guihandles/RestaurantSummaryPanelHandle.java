package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

/**
 * Provides a handle for {@code RestaurantSummaryPanel} containing attributes of a {@code Restaurant}.
 */
public class RestaurantSummaryPanelHandle extends NodeHandle<Node> {

    public static final String SUMMARY_PANE_ID = "#summaryPane";
    private static final String TITLE_VBOX_ID = "#titleVbox";
    private static final String INFO_VBOX_ID = "#infoVbox";
    private static final String PLACEHOLDER_VBOX_ID = "#placeholderVbox";
    private static final String PLACEHOLDER_IMAGEVIEW_ID = "#placeholderImg";

    public RestaurantSummaryPanelHandle(BorderPane restaurantSummaryPanelNode) {
        super(restaurantSummaryPanelNode);
    }

    /**
     * Returns the titleVbox in the RestaurantSummaryPanel of a restaurant,
     * containing {@code name, avgRating, totalVisits}.
     * @throws guitests.guihandles.exceptions.NodeNotFoundException if the titleVbox is currently
     * not in the scene graph.
     */
    public SummaryTitleVBoxHandle getSummaryTitleVBoxHandle() {
        return new SummaryTitleVBoxHandle(getChildNode(TITLE_VBOX_ID));
    }

    /**
     * Returns the infoVbox in the RestaurantSummaryPanel of a restaurant,
     * containing {@code addressPostal, phone, openingHours, email, weblink}.
     * @throws guitests.guihandles.exceptions.NodeNotFoundException if the infoVbox is currently
     * not in the scene graph.
     */
    public SummaryInfoVBoxHandle getSummaryInfoVBoxHandle() {
        return new SummaryInfoVBoxHandle(getChildNode(INFO_VBOX_ID));
    }

    /**
     * Returns the handle for the placeholderVbox in the RestaurantSummaryPanel of a restaurant.
     * @throws guitests.guihandles.exceptions.NodeNotFoundException if the placeholder VBox is currently
     * not in the scene graph.
     */
    public SummaryPlaceholderVBoxHandle getSummaryPlaceholderVBoxHandle() {
        return new SummaryPlaceholderVBoxHandle(getChildNode(PLACEHOLDER_VBOX_ID));
    }

    /**
     * Returns the placeholderImg in the RestaurantSummaryPanel of a restaurant.
     * @throws guitests.guihandles.exceptions.NodeNotFoundException if the placeholder VBox is currently
     * not in the scene graph.
     */
    public Image getPlaceholderImg() {
        ImageView imgView = getChildNode(PLACEHOLDER_IMAGEVIEW_ID);

        return imgView.getImage();
    }
}
