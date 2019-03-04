package seedu.address.model.menu;

import javafx.beans.Observable;
import javafx.collections.ObservableList;
import seedu.address.model.menu.MenuItem;

import java.util.Optional;

/**
 * Unmodifiable view of Menu
 */
public interface ReadOnlyMenu extends Observable{
    
    /**
     * Returns an unmodifiable view of the menu items list.
     */
    ObservableList<MenuItem> getMenuItemList();

    Optional<MenuItem> getItemFromCode(Code code);

}
