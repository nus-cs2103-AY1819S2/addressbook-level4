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
import seedu.address.model.ClassForPrinting;
import seedu.address.model.moduletaken.ModuleTaken;

/**
 * Panel containing the list of persons.
 */
public class ModuleTakenListPanel extends UiPart<Region> {
    private static final String FXML = "ModuleTakenListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ModuleTakenListPanel.class);

    @FXML
    private ListView<ModuleTaken> moduleTakenListView;

    public ModuleTakenListPanel(ObservableList<ModuleTaken> moduleTakenList,
                                ObservableValue<ClassForPrinting> selectedModuleTaken,
                                Consumer<ClassForPrinting> onSelectedModuleTakenChange) {
        super(FXML);
        moduleTakenListView.setItems(moduleTakenList);
        moduleTakenListView.setCellFactory(listView -> new ModuleTakenListViewCell());
        moduleTakenListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            logger.fine("Selection in moduleTaken list panel changed to : '" + newValue + "'");
            onSelectedModuleTakenChange.accept(newValue);
        });
        selectedModuleTaken.addListener((observable, oldValue, newValue) -> {
            logger.fine("Selected moduleTaken changed to: " + newValue);

            // Don't modify selection if we are already selecting the selected moduleTaken,
            // otherwise we would have an infinite loop.
            if (Objects.equals(moduleTakenListView.getSelectionModel().getSelectedItem(), newValue)) {
                return;
            }

            if (newValue == null) {
                moduleTakenListView.getSelectionModel().clearSelection();
            } else {
                int index = moduleTakenListView.getItems().indexOf(newValue);
                moduleTakenListView.scrollTo(index);
                moduleTakenListView.getSelectionModel().clearAndSelect(index);
            }
        });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code ModuleTaken} using a {@code ModuleTakenCard}.
     */
    class ModuleTakenListViewCell extends ListCell<ModuleTaken> {
        @Override
        protected void updateItem(ModuleTaken moduleTaken, boolean empty) {
            super.updateItem(moduleTaken, empty);

            if (empty || moduleTaken == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ModuleTakenCard(moduleTaken, getIndex() + 1).getRoot());
            }
        }
    }

}
