package seedu.address.testutil;

import seedu.address.model.apparel.Apparel;
import seedu.address.model.apparel.ClothingType;
import seedu.address.model.apparel.Color;
import seedu.address.model.apparel.Name;

/**
 * A utility class to help with building Apparel objects.
 */
public class ApparelBuilder {

    public static final String DEFAULT_NAME = "Sleek Shirt";
    public static final String DEFAULT_COLOR = "White";
    public static final String DEFAULT_CLOTHING_TYPE = "Top";

    private Name name;
    private Color color;
    private ClothingType clothingType;

    public ApparelBuilder() {
        name = new Name(DEFAULT_NAME);
        color = new Color(DEFAULT_COLOR);
        clothingType = new ClothingType(DEFAULT_CLOTHING_TYPE);
    }

    /**
     * Initializes the ApparelBuilder with the data of {@code apparelToCopy}.
     */
    public ApparelBuilder(Apparel apparelToCopy) {
        name = apparelToCopy.getName();
        color = apparelToCopy.getColor();
        clothingType = apparelToCopy.getClothingType();
    }

    /**
     * Sets the {@code Name} of the {@code Apparel} that we are building.
     */
    public ApparelBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }



    /**
     * Sets the {@code Color} of the {@code Apparel} that we are building.
     */
    public ApparelBuilder withColor(String color) {
        this.color = new Color(color);
        return this;
    }

    /**
     * Sets the {@code ClothingType} of the {@code Apparel} that we are building.
     */
    public ApparelBuilder withClothingType(String clothingType) {
        this.clothingType = new ClothingType(clothingType);
        return this;
    }

    public Apparel build() {
        return new Apparel(name, color, clothingType);
    }

    public Apparel buildAvailable() {
        return new Apparel(name, color, clothingType, true);
    }

    public Apparel buildUnavailable() {
        return new Apparel(name, color, clothingType, false);
    }

}
