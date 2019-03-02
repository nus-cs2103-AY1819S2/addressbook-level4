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
import seedu.address.model.Item;
import seedu.address.model.menu.MenuItem;
import seedu.address.model.person.Person;

/**
 * Panel containing the list of persons.
 */
public class ListPanel extends UiPart<Region> {
    private static final String FXML = "PersonListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);
    
    @FXML
    private ListView<Person> personListView;
    private ListView<Item> itemListView;
    
    public ListPanel(ObservableList<Item> itemList, ObservableValue<Item> selectedItem,
                     Consumer<Item> onSelectedItemChange) {
        super(FXML);
        itemListView.setItems(itemList);
        itemListView.setCellFactory(listView -> new ListPanel.ItemListViewCell());
        itemListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            logger.fine("Selection in list panel changed to : '" + newValue +"'");
            onSelectedItemChange.accept(newValue);
        });
        selectedItem.addListener(((observable, oldValue, newValue) -> {
            logger.fine("Selected item changed to: " + newValue);
            
            // Don't modify selection if we are already selecting the selected item,
            // otherwise we would have an infinite loop.
            if (Objects.equals(itemListView.getSelectionModel().getSelectedItem(), newValue)) {
                return;
            }
            
            if (newValue == null) {
                itemListView.getSelectionModel().clearSelection();
            } else {
                int index = itemListView.getItems().indexOf(newValue);
                itemListView.scrollTo(index);
                itemListView.getSelectionModel().clearAndSelect(index);
            }
        }));
    }

    //    public ListPanel(ObservableList<Person> personList, ObservableValue<Person> selectedPerson,
    //                           Consumer<Person> onSelectedPersonChange) {
    //        super(FXML);
    //        personListView.setItems(personList);
    //        personListView.setCellFactory(listView -> new ListPanel.PersonListViewCell());
    //        personListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
    //            logger.fine("Selection in person list panel changed to : '" + newValue + "'");
    //            onSelectedPersonChange.accept(newValue);
    //        });
    //        selectedPerson.addListener((observable, oldValue, newValue) -> {
    //            logger.fine("Selected person changed to: " + newValue);
    //            
    //            // Don't modify selection if we are already selecting the selected person,
    //            // otherwise we would have an infinite loop.
    //            if (Objects.equals(personListView.getSelectionModel().getSelectedItem(), newValue)) {
    //                return;
    //            }
    //            
    //            if (newValue == null) {
    //                personListView.getSelectionModel().clearSelection();
    //            } else {
    //                int index = personListView.getItems().indexOf(newValue);
    //                personListView.scrollTo(index);
    //                personListView.getSelectionModel().clearAndSelect(index);
    //            }
    //        });
    //    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Item} using a {@code ItemCard}.
     */
    class ItemListViewCell extends ListCell<Item> {
        @Override
        protected void updateItem(Item item, boolean empty) {
            super.updateItem(item, empty);
            
            if (empty || item == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ItemCard(item, getIndex() + 1).getRoot());
            }
        }
    }
    //    class PersonListViewCell extends ListCell<Person> {
    //        @Override
    //        protected void updateItem(Person person, boolean empty) {
    //            super.updateItem(person, empty);
    //            
    //            if (empty || person == null) {
    //                setGraphic(null);
    //                setText(null);
    //            } else {
    //                setGraphic(new PersonCard(person, getIndex() + 1).getRoot());
    //            }
    //        }
    //    }
    
}
