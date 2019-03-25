package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.VersionedCardFolder;

/**
 * Panel containing the list of folders.
 */
public class FolderListPanel extends UiPart<Region> {
    private static final String FXML = "FolderListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(FolderListPanel.class);

    @FXML
    private ListView<VersionedCardFolder> folderListView;

    public FolderListPanel(ObservableList<VersionedCardFolder> folderList) {
        super(FXML);
        folderListView.setItems(folderList);
        folderListView.setCellFactory(listView -> new FolderListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code CardFolder} using a {@code CardFolderThumbnail}.
     */
    class FolderListViewCell extends ListCell<VersionedCardFolder> {
        @Override
        protected void updateItem(VersionedCardFolder folder, boolean empty) {
            super.updateItem(folder, empty);

            if (empty || folder == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new FolderThumbnail(folder, getIndex() + 1).getRoot());
            }
        }
    }

}
