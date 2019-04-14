package seedu.address.ui;

import java.util.logging.Logger;

import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.apparel.Apparel;
import seedu.address.model.apparel.ColorValue;

/**
 * The Browser Panel of the App.
 */
public class BrowserPanel extends UiPart<Region> {

    public static final String DEFAULT_IMAGE_PATH = "/images/firstpage.jpg";
    public static final String DEFAULT_NAME_LABEL = "Select";
    public static final String DEFAULT_CLOTHING_TYPE_LABEL = "to";
    public static final String DEFAULT_COLOR_LABEL = "display";
    public static final String DEFAULT_STATUS_LABEL = "an";
    public static final String DEFAULT_USAGE_COUNT_LABEL = "apparel";

    private static final String FXML = "BrowserPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private ImageView apparelImageView;
    @FXML
    private Label nameLabel;
    @FXML
    private Label clothingTypeLabel;
    @FXML
    private Label colorLabel;
    @FXML
    private Label usageCountLabel;
    @FXML
    private Label statusLabel;

    public BrowserPanel(ObservableValue<Apparel> selectedApparel) {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);
        // Show default screen
        showApparelDetails(null);
        selectedApparel.addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                showApparelDetails(null);
            } else {
                showApparelDetails(newValue);
            }
        });
    }

    /**
     * Fill all text fields to show details about the apparel.
     * If the specified apparel is null, all text fields are cleared.
     */
    public void showApparelDetails(Apparel apparel) {

        if (apparel == null) {
            nameLabel.setText(DEFAULT_NAME_LABEL);
            clothingTypeLabel.setText(DEFAULT_CLOTHING_TYPE_LABEL);
            colorLabel.setText(DEFAULT_COLOR_LABEL);
            statusLabel.setText(DEFAULT_STATUS_LABEL);
            usageCountLabel.setText(DEFAULT_USAGE_COUNT_LABEL);

            // set to welcome image if nothing selected / just booted
            Image image = new Image(getClass().getResource(DEFAULT_IMAGE_PATH).toString());
            apparelImageView.setImage(image);
        } else {
            // Fill the image
            Image image;
            ColorValue cv = apparel.getColor().getPrimary();
            switch(apparel.getClothingType().getClothingTypeValue()) {
            case TOP:
                image = new Image(getClass()
                        .getResource(FileUtil.getTopIconFilePath(cv)).toString(), true);
                break;
            case BOTTOM:
                image = new Image(getClass()
                        .getResource(FileUtil.getBottomIconFilePath(cv)).toString(), true);
                break;
            case BELT:
                image = new Image(getClass()
                        .getResource(FileUtil.getBeltIconFilePath(cv)).toString(), true);
                break;
            case SHOES:
                image = new Image(getClass()
                        .getResource(FileUtil.getShoeIconFilePath(cv)).toString(), true);
                break;
            default:
                throw new IllegalArgumentException("No such clothing type.");
            }
            apparelImageView.setImage(image);

            // Fill the labels with info from the apparel Object
            nameLabel.setText(apparel.getName().fullName);
            clothingTypeLabel.setText(apparel.getClothingType().toString());
            colorLabel.setText(apparel.getColor().toString());
            usageCountLabel.setText(String.valueOf(apparel.getUsageCount()));
            statusLabel.setText(apparel.getWornStatus());
        }
    }
}
