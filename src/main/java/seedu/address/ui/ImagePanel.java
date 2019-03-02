package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;

/**
 * The Browser Panel of the App.
 */
public class ImagePanel extends UiPart<Region> {

    private static final String FXML = "ImagePanel.fxml";

    @FXML
    private ImageView imageView;

    public ImagePanel() {
        super(FXML);
        Image image = new Image("https://i.imgur.com/zy0dRYh.jpg");
        imageView.setImage(image);
    }

}
