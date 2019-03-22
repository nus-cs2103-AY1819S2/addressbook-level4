/* @@author Carrein */

package seedu.address.ui;

import java.beans.PropertyChangeEvent;

import java.beans.PropertyChangeListener;
import java.io.File;
import java.net.MalformedURLException;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import seedu.address.Notifier;

/**
 * The Image Panel of the App.
 */
public class ImagePanel extends UiPart<Region> implements PropertyChangeListener {

    private static final String FXML = "ImagePanel.fxml";

    @FXML
    private ImageView imageView;

    public ImagePanel(Pane pane) {
        super(FXML);
        Notifier.addPropertyChangeListener(this);
        imageView.setPreserveRatio(true);
    }

    /**
     * Updates the imageview based on incoming event parameter.
     * @param event url of new image to display.
     */
    public void propertyChange(PropertyChangeEvent event) {
        if (event.getPropertyName().equals("import")) {
            File f = new File(event.getNewValue().toString());
            try {
                System.out.println(f.toURI().toURL().toExternalForm());
                Image i = new Image(f.toURI().toURL().toExternalForm());
                imageView.setImage(i);
            } catch (MalformedURLException e) {
                System.out.println(e.toString());
            }
        }
    }
}

