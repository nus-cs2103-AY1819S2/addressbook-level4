/* @@author Carrein */

package seedu.address.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.logging.Logger;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.image.Image;

/**
 * The Image Panel of the App.
 */
public class InformationPanel extends UiPart<Region> {

    private static final String FXML = "InformationPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(InformationPanel.class);

    @FXML
    private TabPane informationPanel;

    public InformationPanel(ObservableList<Image> imageList) {
        super(FXML);
        ListView<Image> imageListView = new ListView();
        Tab tab = informationPanel.getTabs().get(0);
        tab.setContent(imageListView);
        imageListView.setItems(imageList);
        imageListView.setCellFactory(listView -> new ImageListViewCell());
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
}

