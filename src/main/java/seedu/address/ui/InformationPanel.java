/* @@author Carrein */

package seedu.address.ui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Region;
import seedu.address.Notifier;
import seedu.address.model.Album;
import seedu.address.model.image.Image;

/**
 * The Image Panel of the App.
 */
public class InformationPanel extends UiPart<Region> implements PropertyChangeListener {

    private static final String FXML = "InformationPanel.fxml";

    @FXML
    private TabPane informationPanel;

    private final ListView<Image> imageListView = new ListView<>();
    private final Tab albumTab = informationPanel.getTabs().get(0);
    private final Tab detailsTab = informationPanel.getTabs().get(1);
    private final Tab historyTab = informationPanel.getTabs().get(2);
    private final Album album = Album.getInstance();


    public InformationPanel() {
        super(FXML);
        Notifier.addPropertyChangeListener(this);
        refresh();
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Image} using a {@code ImageCard}.
     */
    class ImageListViewCell extends ListCell<Image> {
        @Override
        protected void updateItem(Image image, boolean empty) {
            super.updateItem(image, empty);
            if (empty || image == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ImageCard(image, getIndex() + 1).getRoot());
            }
        }
    }

    /**
     * Updates the imageview based on incoming event parameter.
     *
     * @param event url of new image to display.
     */
    public void propertyChange(PropertyChangeEvent event) {
        if (event.getPropertyName().equals("refresh")) {
            refresh();
        }
    }

    /**
     * Helper method to refresh the import list.
     */
    private void refresh() {
        List<Image> tempList = album.getImageList();
        imageListView.setItems(FXCollections.observableArrayList(tempList));
        imageListView.setCellFactory(listView -> new ImageListViewCell());
        albumTab.setContent(imageListView);
    }
}

