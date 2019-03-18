package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.menu.MenuItem;

/**
 * The Browser Panel for the menu.
 */
public class MenuItemFlowPanel extends UiPart<Region> {

    private static final String FXML = "MenuItemFlowPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(MenuItemFlowPanel.class);

    @FXML
    private FlowPane menuItemFlowPane;

    public MenuItemFlowPanel(ObservableList<MenuItem> menuItemObservableList, ScrollPane scrollPane) {
        super(FXML);

        // To prevent triggering events for typing inside the loaded FlowPane.
        getRoot().setOnKeyPressed(Event::consume);
        menuItemFlowPane.setHgap(1);
        menuItemFlowPane.setVgap(1);
        menuItemFlowPane.prefWidthProperty().bind(scrollPane.widthProperty());
        menuItemFlowPane.prefHeightProperty().bind(scrollPane.heightProperty());

        // Creates a TableCard for each Table and adds to FlowPane
        for (MenuItem menuItem : menuItemObservableList) {
            menuItemFlowPane.getChildren().add(new MenuItemCard(menuItem).getRoot());
        }

        menuItemObservableList.addListener((ListChangeListener<MenuItem>) c -> {
            menuItemFlowPane.getChildren().clear();
            for (MenuItem menuItem : menuItemObservableList) {
                menuItemFlowPane.getChildren().add(new MenuItemCard(menuItem).getRoot());
            }
        });
    }

}
