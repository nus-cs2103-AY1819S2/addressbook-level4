package seedu.address.ui;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.logging.Logger;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.moduleinfo.ModuleInfo;

/**
 * Panel containing the list of ModuleInfo.
 */
public class DisplayModuleInfoList extends UiPart<Region> {
    private static final String FXML = "DisplayModuleInfoList.fxml";
    private final Logger logger = LogsCenter.getLogger(DisplayModuleInfoList.class);

    @FXML
    private ListView<ModuleInfo> moduleinfolistview;

    public DisplayModuleInfoList(ObservableList<ModuleInfo> moduleInfoList,
                                 ObservableValue<ModuleInfo> selectedModuleInfo,
                                 Consumer<ModuleInfo> onSelectedModuleInfoChange) {
        super(FXML);
        moduleinfolistview.setItems(moduleInfoList);
        moduleinfolistview.setCellFactory(listView -> new ModuleInfoListViewCell());
        moduleinfolistview.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            logger.fine("Selection in ModuleInfo list panel changed to : '" + newValue + "'");
            onSelectedModuleInfoChange.accept(newValue);
        });

        selectedModuleInfo.addListener((observable, oldValue, newValue) -> {
            logger.fine("Selected ModuleInfo changed to: " + newValue);

            if (Objects.equals(moduleinfolistview.getSelectionModel().getSelectedItems(), newValue)) {
                return;
            }

            if (newValue == null) {
                moduleinfolistview.getSelectionModel().clearSelection();
            } else {
                int index = moduleinfolistview.getItems().indexOf(newValue);
                moduleinfolistview.scrollTo(index);
                moduleinfolistview.getSelectionModel().clearAndSelect(index);
            }
        });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code ModuleInfo} using a {@code ModuleInfoDisplay}.
     */
    class ModuleInfoListViewCell extends ListCell<ModuleInfo> {
        @Override
        protected void updateItem(ModuleInfo moduleinfo, boolean empty) {
            super.updateItem(moduleinfo, empty);

            if (empty || moduleinfo == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new DisplayModuleInfo(moduleinfo, getIndex() + 1).getRoot());
            }
        }
    }


}
