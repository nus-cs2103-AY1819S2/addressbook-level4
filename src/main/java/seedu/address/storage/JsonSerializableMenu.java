package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlyRestOrRant;
import seedu.address.model.RestOrRant;
import seedu.address.model.menu.Menu;
import seedu.address.model.menu.MenuItem;
import seedu.address.model.menu.ReadOnlyMenu;
import seedu.address.model.person.Person;

/**
 * An Immutable RestOrRant that is serializable to JSON format.
 */
@JsonRootName(value = "menu")
class JsonSerializableMenu {

    public static final String MESSAGE_DUPLICATE_ITEM = "Menu list contains duplicate items(s).";
    private final List<JsonAdaptedMenu> items = new ArrayList<>();
    /**
     * Constructs a {@code JsonSerializableMenu} with the given persons.
     */
    @JsonCreator
    public JsonSerializableMenu(@JsonProperty("menu") List<JsonAdaptedMenu> items) {
        this.items.addAll(items);
    }

    /**
     * Converts a given {@code ReadOnlyRestOrRant} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableMenu}.
     */
    public JsonSerializableMenu(ReadOnlyMenu source) {
        items.addAll(source.getMenuItemList().stream().map(JsonAdaptedMenu::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code RestOrRant} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public Menu toModelType() throws IllegalValueException {
        Menu menu = new Menu();
        for (JsonAdaptedMenu jsonAdaptedMenu : items) {
            MenuItem item = jsonAdaptedMenu.toModelType();
            if (menu.hasMenuItem(item)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_ITEM);
            }
            menu.addMenuItem(item);
        }
        return menu;
    }

}
