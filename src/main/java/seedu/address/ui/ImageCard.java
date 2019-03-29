/* @@author Carrein */

package seedu.address.ui;

import java.io.File;
import java.net.MalformedURLException;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import seedu.address.model.image.Image;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class ImageCard extends UiPart<Region> {

    private static final String FXML = "ImageListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Image image;

    @FXML
    private Text name;
    @FXML
    private Text height;
    @FXML
    private Text width;
    @FXML
    private Text size;
    @FXML
    private ImageView thumbnail;

    public ImageCard(Image image) {
        super(FXML);
        this.image = image;
        File file = new File(image.getUrl());
        try {
            thumbnail.setImage(new javafx.scene.image.Image(file.toURI().toURL().toExternalForm()));
        } catch (MalformedURLException e) {
            System.out.println(e.toString());
        }
        name.setText("\uD83D\uDDBC " + image.getName().name);
        height.setText("Height: " + image.getHeight().value + " px");
        width.setText("Width: " + image.getWidth().value + " px");
        size.setText("Size: " + image.getSize().value + " bytes");
    }
}
