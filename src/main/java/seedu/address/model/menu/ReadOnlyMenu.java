package seedu.address.model.menu;

import java.util.Optional;

import javafx.beans.Observable;
import javafx.collections.ObservableList;

/**
 * Unmodifiable view of Menu
 */
public interface ReadOnlyMenu extends Observable {

    /**
     * Returns an unmodifiable view of the menu items list.
     */
    ObservableList<MenuItem> getMenuItemList();

    boolean hasMenuItem(MenuItem menuItem);

    void addMenuItem(MenuItem menuItem);

    void setMenuItem(MenuItem tobeReplaced, MenuItem newItem);

    void removeMenuItem(MenuItem menuItem);

    Optional<MenuItem> getItemFromCode(Code code);

    Name getNameFromItem(MenuItem menuItem);

    Code getCodeFromItem(MenuItem menuItem);

    Price getPriceFromItem(MenuItem menuItem);

}
