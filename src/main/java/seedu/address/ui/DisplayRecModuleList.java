package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.recmodule.RecModule;

/**
 * Panel containing the list of {@code RecModule}.
 */
public class DisplayRecModuleList extends UiPart<Region> {
    private static final String FXML = "DisplayRecModuleList.fxml";
    private final Logger logger = LogsCenter.getLogger(ModuleTakenListPanel.class);

    @FXML
    private ListView<RecModule> recModuleListView;

    public DisplayRecModuleList(ObservableList<RecModule> recModuleList) {
        super(FXML);
        recModuleListView.setItems(recModuleList);
        recModuleListView.setCellFactory(listView -> new RecModuleListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code RecModule} using a {@code DisplayRecModule}.
     */
    class RecModuleListViewCell extends ListCell<RecModule> {
        @Override
        protected void updateItem(RecModule recModule, boolean empty) {
            super.updateItem(recModule, empty);

            if (empty || recModule == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new DisplayRecModule(recModule, getIndex() + 1).getRoot());
            }
        }
    }


}
