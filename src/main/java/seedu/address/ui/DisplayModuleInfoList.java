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
    private static final String FXML = "PersonListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);

    @FXML
    private ListView<ModuleInfo> moduleInfoListView;

    public DisplayModuleInfoList(ObservableList<ModuleInfo> moduleInfoList,
                                 ObservableValue<ModuleInfo> selectedModuleInfo,
                                 Consumer<ModuleInfo> onSelectedModuleInfoChange) {
        super(FXML);
        moduleInfoListView.setItems(moduleInfoList);
        moduleInfoListView.setCellFactory(listView -> new ModuleInfoListViewCell());
        moduleInfoListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            logger.fine("Selection in ModuleInfo list panel changed to : '" + newValue + "'");
            onSelectedModuleInfoChange.accept(newValue);
        });

        selectedModuleInfo.addListener((observable, oldValue, newValue) -> {
            logger.fine("Selected ModuleInfo changed to: " + newValue);

            if (Objects.equals(moduleInfoListView.getSelectionModel().getSelectedItems(), newValue)) {
                return;
            }

            if (newValue == null) {
                moduleInfoListView.getSelectionModel().clearSelection();
            } else {
                int index = moduleInfoListView.getItems().indexOf(newValue);
                moduleInfoListView.scrollTo(index);
                moduleInfoListView.getSelectionModel().clearAndSelect(index);
            }
        });
    }


    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
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
