package seedu.address.testutil;

import seedu.address.model.menu.Code;
import seedu.address.model.menu.MenuItem;
import seedu.address.model.menu.Name;
import seedu.address.model.menu.Price;

/**
 * A utility class to help with building MenuItem objects.
 */
public class MenuItemBuilder {

    public static final String DEFAULT_NAME = "Chicken Wings";
    public static final String DEFAULT_CODE = "W09";
    public static final String DEFAULT_PRICE = "3.99";

    private Name name;
    private Code code;
    private Price price;

    public MenuItemBuilder() {
        name = new Name(DEFAULT_NAME);
        code = new Code(DEFAULT_CODE);
        price = new Price(DEFAULT_PRICE);
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public MenuItemBuilder(MenuItem itemToCopy) {
        name = itemToCopy.getName();
        code = itemToCopy.getCode();
        price = itemToCopy.getPrice();
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public MenuItemBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public MenuItemBuilder withCode(String code) {
        this.code = new Code(code);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public MenuItemBuilder withPrice(String price) {
        this.price = new Price(price);
        return this;
    }

    public MenuItem build() {
        return new MenuItem(name, code, price);
    }

}
