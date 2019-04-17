package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import seedu.address.commons.core.index.Index;
import seedu.address.ui.BrowserPanel;

/**
 * A handler for the {@code BrowserPanel} of the UI.
 */
public class BrowserPanelHandle extends NodeHandle<Node> {

    public static final String BROWSER_APPAREL_ID = "#browserApparelPane";

    private static final String APPAREL_IMAGE_VIEW = "#apparelImageView";
    private static final String NAME_LABEL = "#nameLabel";
    private static final String CLOTHING_TYPE_LABEL = "#clothingTypeLabel";
    private static final String COLOR_LABEL = "#colorLabel";
    private static final String STATUS_LABEL = "#statusLabel";
    private static final String USAGE_COUNT_LABEL = "#usageCountLabel";

    private Index lastRememberIndex;
    private ImageView imageView;
    private Label nameLabel;
    private Label clothingTypeLabel;
    private Label colorLabel;
    private Label statusLabel;
    private Label usageCountLabel;

    private Image prevImage;
    private String prevName;
    private String prevClothingType;
    private String prevColor;
    private String prevStatus;
    private String prevUsageCount;

    public BrowserPanelHandle(Node browserPanelNode) {
        super(browserPanelNode);

        imageView = getChildNode(APPAREL_IMAGE_VIEW);
        nameLabel = getChildNode(NAME_LABEL);
        clothingTypeLabel = getChildNode(CLOTHING_TYPE_LABEL);
        colorLabel = getChildNode(COLOR_LABEL);
        statusLabel = getChildNode(STATUS_LABEL);
        usageCountLabel = getChildNode(USAGE_COUNT_LABEL);

        prevImage = new Image(getClass().getResource(BrowserPanel.DEFAULT_IMAGE_PATH).toString(), true);
        prevName = BrowserPanel.DEFAULT_NAME_LABEL;
        prevClothingType = BrowserPanel.DEFAULT_CLOTHING_TYPE_LABEL;
        prevColor = BrowserPanel.DEFAULT_COLOR_LABEL;
        prevStatus = BrowserPanel.DEFAULT_STATUS_LABEL;
        prevUsageCount = BrowserPanel.DEFAULT_USAGE_COUNT_LABEL;
    }
    /**
     * Get the image.
     */
    public Image getImage() {
        return imageView.getImage();
    }

    /**
     * Get the apparel name.
     */
    public String getName() {
        return nameLabel.getText();
    }

    /**
     * Get the clothing type for the apparel.
     */
    public String getClothingType() {
        return clothingTypeLabel.getText();
    }

    /**
     * Get the color of the apparel.
     */
    public String getColor() {
        return colorLabel.getText();
    }

    /**
     * Get the status of the apparel.
     */
    public String getStatus() {
        return statusLabel.getText();
    }

    /**
     * Get the usage count for the apparel.
     */
    public String getUsageCount() {
        return usageCountLabel.getText();
    }

    /**
     * Remember the state of the selected apparel
     */
    public void rememberState() {
        prevImage = imageView.getImage();
        prevName = nameLabel.getText();
        prevClothingType = clothingTypeLabel.getText();
        prevColor = colorLabel.getText();
        prevStatus = statusLabel.getText();
        prevUsageCount = usageCountLabel.getText();
    }

    /**
     * Return true if the gui changed.
     */
    public boolean isGuiChanged() {
        if (imageView.getImage().getUrl().equals(prevImage.getUrl())
                        && nameLabel.getText().equals(prevName)
                        && clothingTypeLabel.getText().equals(prevClothingType)
                        && colorLabel.getText().equals(prevColor)
                        && statusLabel.getText().equals(prevStatus)
                        && usageCountLabel.getText().equals(prevUsageCount)
        ) {
            return false;
        }

        return true;
    }

    /**
     * Return true is default gui display default items.
     */
    public boolean isDefaultLoaded() {
        if (!imageView.getImage().getUrl().equals(BrowserPanel.DEFAULT_IMAGE_PATH)) {
            System.out.println("image view different");
            System.out.println(imageView.getImage().getUrl() + " vs " + BrowserPanel.DEFAULT_IMAGE_PATH);
        }
        if (!nameLabel.getText().equals(BrowserPanel.DEFAULT_NAME_LABEL)) {
            System.out.println("name different");
            System.out.println(nameLabel.getText() + " vs " + BrowserPanel.DEFAULT_NAME_LABEL);
        }
        if (!clothingTypeLabel.getText().equals(BrowserPanel.DEFAULT_CLOTHING_TYPE_LABEL)) {
            System.out.println("clothing type different");
            System.out.println(clothingTypeLabel.getText() + " vs " + BrowserPanel.DEFAULT_CLOTHING_TYPE_LABEL);
        }
        if (!colorLabel.getText().equals(BrowserPanel.DEFAULT_COLOR_LABEL)) {
            System.out.println("color different");
            System.out.println(colorLabel.getText() + " vs " + BrowserPanel.DEFAULT_COLOR_LABEL);
        }
        if (!statusLabel.getText().equals(BrowserPanel.DEFAULT_STATUS_LABEL)) {
            System.out.println("status different");
            System.out.println(statusLabel.getText() + " vs " + BrowserPanel.DEFAULT_STATUS_LABEL);
        }
        if (!usageCountLabel.getText().equals(BrowserPanel.DEFAULT_USAGE_COUNT_LABEL)) {
            System.out.println("usage count different");
            System.out.println(usageCountLabel.getText() + " vs " + BrowserPanel.DEFAULT_USAGE_COUNT_LABEL);
        }

        if (imageView.getImage().getUrl().equals(new Image(BrowserPanel.DEFAULT_IMAGE_PATH).getUrl())
                && nameLabel.getText().equals(BrowserPanel.DEFAULT_NAME_LABEL)
                && clothingTypeLabel.getText().equals(BrowserPanel.DEFAULT_CLOTHING_TYPE_LABEL)
                && colorLabel.getText().equals(BrowserPanel.DEFAULT_COLOR_LABEL)
                && statusLabel.getText().equals(BrowserPanel.DEFAULT_STATUS_LABEL)
                && usageCountLabel.getText().equals(BrowserPanel.DEFAULT_USAGE_COUNT_LABEL)
        ) {
            return true;
        }

        return false;
    }
}
