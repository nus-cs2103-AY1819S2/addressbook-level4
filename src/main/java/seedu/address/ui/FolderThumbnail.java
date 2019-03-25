package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.CardFolder;

/**
 * An UI component that displays information of a {@code CardFolder}.
 */
public class FolderThumbnail extends UiPart<Region> {

    private static final String FXML = "FolderListThumbnail.fxml";
    private static final String CARD_COUNT_POSTFIX = " cards";

    public final CardFolder folder;

    @FXML
    private HBox folderThumbnailPane;
    @FXML
    private Label folderName;
    @FXML
    private Label folderSize;
    @FXML
    private Label id;

    public FolderThumbnail(CardFolder folder, int displayedIndex) {
        super(FXML);
        this.folder = folder;
        id.setText(displayedIndex + ". ");
        folderName.setText(folder.getFolderName());
        folderSize.setText(folder.countCards() + CARD_COUNT_POSTFIX);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FolderThumbnail)) {
            return false;
        }

        // state check
        FolderThumbnail folder = (FolderThumbnail) other;
        return id.getText().equals(folder.id.getText())
                && this.folder.equals(folder.folder);
    }
}
