package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.menu.MenuItem;

/**
 * An UI component that displays information of a {@code MenuItem}.
 */
public class MenuItemListCard extends UiPart<Region> {

    private static final String FXML = "MenuItemListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on RestOrRant level 4</a>
     */

    public final MenuItem item;

    @FXML
    private HBox menuItemListCardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label code;
    @FXML
    private Label price;

    public MenuItemListCard(MenuItem item, int displayedIndex) {
        super(FXML);
        this.item = item;
        id.setText(displayedIndex + ". ");

        name.setText(item.getName().itemName);
        code.setText(item.getCode().itemCode);
        price.setText(item.getPrice().itemPrice);
    }
    
    public MenuItemListCard(MenuItem item) {
        super(FXML);
        this.item = item;
        
        name.setText(item.getName().itemName);
        code.setText(item.getCode().itemCode);
        price.setText(item.getPrice().itemPrice);
    }
    
    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MenuItemListCard)) {
            return false;
        }

        // state check
        MenuItemListCard card = (MenuItemListCard) other;
        return id.getText().equals(card.id.getText()) && item.equals(card.item);
    }
}
