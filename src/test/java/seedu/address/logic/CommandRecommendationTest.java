package seedu.address.logic;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.apparel.Apparel;
import seedu.address.model.apparel.ClothingType;
import seedu.address.model.apparel.ClothingTypeValue;
import seedu.address.model.apparel.Color;
import seedu.address.model.apparel.ColorValue;
import seedu.address.model.apparel.Name;

// @@author PhilipPhil
public class CommandRecommendationTest {

    private Model model = new ModelManager();

    @Test
    public void returnZeroItemsCase() {

        final CommandRecommendation commandRecommendation = new CommandRecommendation(model);
        assertEquals(commandRecommendation.returnRecommendationString(), "");

    }
    @Test
    public void returnThreeItemsCase() {
        Name name = new Name("Red Shirt");
        Color color = new Color(ColorValue.RED);
        ClothingType clothingType = new ClothingType(ClothingTypeValue.TOP);
        Apparel apparel = new Apparel(name, color, clothingType);
        model.addApparel(apparel);


        name = new Name("Blue Jeans");
        color = new Color(ColorValue.BLUE);
        clothingType = new ClothingType(ClothingTypeValue.BOTTOM);
        apparel = new Apparel(name, color, clothingType);
        model.addApparel(apparel);

        name = new Name("Black Shoes");
        color = new Color(ColorValue.BLACK);
        clothingType = new ClothingType(ClothingTypeValue.SHOES);
        apparel = new Apparel(name, color, clothingType);
        model.addApparel(apparel);

        final CommandRecommendation commandRecommendation = new CommandRecommendation(model);


        assertEquals(commandRecommendation.returnRecommendationString(),
                "Red Shirt Color: RED Type: TOP Status: Clean Usage-count: 0\n"
                        + "Blue Jeans Color: BLUE Type: BOTTOM Status: Clean Usage-count: 0\n"
                        + "Black Shoes Color: BLACK Type: SHOES Status: Clean Usage-count: 0");


    }
    @Test
    public void returnFourItemsCase() {
        Name name = new Name("Red Shirt");
        Color color = new Color(ColorValue.RED);
        ClothingType clothingType = new ClothingType(ClothingTypeValue.TOP);
        Apparel apparel = new Apparel(name, color, clothingType);
        model.addApparel(apparel);


        name = new Name("Blue Jeans");
        color = new Color(ColorValue.BLUE);
        clothingType = new ClothingType(ClothingTypeValue.BOTTOM);
        apparel = new Apparel(name, color, clothingType);
        model.addApparel(apparel);

        name = new Name("Black Belt");
        color = new Color(ColorValue.BLACK);
        clothingType = new ClothingType(ClothingTypeValue.BELT);
        apparel = new Apparel(name, color, clothingType);
        model.addApparel(apparel);

        name = new Name("Black Shoes");
        color = new Color(ColorValue.BLACK);
        clothingType = new ClothingType(ClothingTypeValue.SHOES);
        apparel = new Apparel(name, color, clothingType);
        model.addApparel(apparel);

        final CommandRecommendation commandRecommendation = new CommandRecommendation(model);


        assertEquals(commandRecommendation.returnRecommendationString(),
                "Red Shirt Color: RED Type: TOP Status: Clean Usage-count: 0\n"
                + "Blue Jeans Color: BLUE Type: BOTTOM Status: Clean Usage-count: 0\n"
                + "Black Belt Color: BLACK Type: BELT Status: Clean Usage-count: 0\n"
                + "Black Shoes Color: BLACK Type: SHOES Status: Clean Usage-count: 0");
    }

    @Test
    public void withNoShoesCase() {
        Name name = new Name("White Shirt");
        Color color = new Color(ColorValue.WHITE);
        ClothingType clothingType = new ClothingType(ClothingTypeValue.TOP);
        Apparel apparel = new Apparel(name, color, clothingType);
        model.addApparel(apparel);

        name = new Name("Red Jeans");
        color = new Color(ColorValue.RED);
        clothingType = new ClothingType(ClothingTypeValue.BOTTOM);
        apparel = new Apparel(name, color, clothingType);
        model.addApparel(apparel);

        name = new Name("White Belt");
        color = new Color(ColorValue.WHITE);
        clothingType = new ClothingType(ClothingTypeValue.BELT);
        apparel = new Apparel(name, color, clothingType);
        model.addApparel(apparel);


        final CommandRecommendation commandRecommendation = new CommandRecommendation(model);


        assertEquals(commandRecommendation.returnRecommendationString(), "");
    }


    @Test
    public void withNoPantsCase() {
        Name name = new Name("White Shirt");
        Color color = new Color(ColorValue.WHITE);
        ClothingType clothingType = new ClothingType(ClothingTypeValue.TOP);
        Apparel apparel = new Apparel(name, color, clothingType);
        model.addApparel(apparel);

        name = new Name("White Shoes");
        color = new Color(ColorValue.WHITE);
        clothingType = new ClothingType(ClothingTypeValue.SHOES);
        apparel = new Apparel(name, color, clothingType);
        model.addApparel(apparel);

        name = new Name("Brown Belt");
        color = new Color(ColorValue.BROWN);
        clothingType = new ClothingType(ClothingTypeValue.BELT);
        apparel = new Apparel(name, color, clothingType);
        model.addApparel(apparel);


        final CommandRecommendation commandRecommendation = new CommandRecommendation(model);

        assertEquals(commandRecommendation.returnRecommendationString(), "");
    }
    @Test
    public void withNoShirtCase() {
        Name name = new Name("Red Pants");
        Color color = new Color(ColorValue.RED);
        ClothingType clothingType = new ClothingType(ClothingTypeValue.BOTTOM);
        Apparel apparel = new Apparel(name, color, clothingType);
        model.addApparel(apparel);

        name = new Name("Brown Belt");
        color = new Color(ColorValue.BROWN);
        clothingType = new ClothingType(ClothingTypeValue.BELT);
        apparel = new Apparel(name, color, clothingType);
        model.addApparel(apparel);

        name = new Name("Brown Shoes");
        color = new Color(ColorValue.BROWN);
        clothingType = new ClothingType(ClothingTypeValue.SHOES);
        apparel = new Apparel(name, color, clothingType);
        model.addApparel(apparel);

        final CommandRecommendation commandRecommendation = new CommandRecommendation(model);


        assertEquals(commandRecommendation.returnRecommendationString(), "");

    }

    @Test
    public void withBrownBeltCase() {
        Name name = new Name("White Shirt");
        Color color = new Color(ColorValue.WHITE);
        ClothingType clothingType = new ClothingType(ClothingTypeValue.TOP);
        Apparel apparel = new Apparel(name, color, clothingType);
        model.addApparel(apparel);


        name = new Name("Red Pants");
        color = new Color(ColorValue.RED);
        clothingType = new ClothingType(ClothingTypeValue.BOTTOM);
        apparel = new Apparel(name, color, clothingType);
        model.addApparel(apparel);

        name = new Name("Brown Belt");
        color = new Color(ColorValue.BROWN);
        clothingType = new ClothingType(ClothingTypeValue.BELT);
        apparel = new Apparel(name, color, clothingType);
        model.addApparel(apparel);

        name = new Name("Brown Shoes");
        color = new Color(ColorValue.BROWN);
        clothingType = new ClothingType(ClothingTypeValue.SHOES);
        apparel = new Apparel(name, color, clothingType);
        model.addApparel(apparel);

        final CommandRecommendation commandRecommendation = new CommandRecommendation(model);


        assertEquals(commandRecommendation.returnRecommendationString(),
                "White Shirt Color: WHITE Type: TOP Status: Clean Usage-count: 0\n"
                        + "Red Pants Color: RED Type: BOTTOM Status: Clean Usage-count: 0\n"
                        + "Brown Belt Color: BROWN Type: BELT Status: Clean Usage-count: 0\n"
                        + "Brown Shoes Color: BROWN Type: SHOES Status: Clean Usage-count: 0");
    }

    @Test
    public void withOrangeBeltCase() {
        Name name = new Name("White Shirt");
        Color color = new Color(ColorValue.WHITE);
        ClothingType clothingType = new ClothingType(ClothingTypeValue.TOP);
        Apparel apparel = new Apparel(name, color, clothingType);
        model.addApparel(apparel);


        name = new Name("Red Pants");
        color = new Color(ColorValue.RED);
        clothingType = new ClothingType(ClothingTypeValue.BOTTOM);
        apparel = new Apparel(name, color, clothingType);
        model.addApparel(apparel);

        name = new Name("Orange Belt");
        color = new Color(ColorValue.ORANGE);
        clothingType = new ClothingType(ClothingTypeValue.BELT);
        apparel = new Apparel(name, color, clothingType);
        model.addApparel(apparel);

        name = new Name("White Shoes");
        color = new Color(ColorValue.WHITE);
        clothingType = new ClothingType(ClothingTypeValue.SHOES);
        apparel = new Apparel(name, color, clothingType);
        model.addApparel(apparel);

        final CommandRecommendation commandRecommendation = new CommandRecommendation(model);


        assertEquals(commandRecommendation.returnRecommendationString(),
                "White Shirt Color: WHITE Type: TOP Status: Clean Usage-count: 0\n"
                        + "Red Pants Color: RED Type: BOTTOM Status: Clean Usage-count: 0\n"
                        + "Orange Belt Color: ORANGE Type: BELT Status: Clean Usage-count: 0\n"
                        + "White Shoes Color: WHITE Type: SHOES Status: Clean Usage-count: 0");
    }


    @Test
    public void withNoMatchingBeltCase() {
        Name name = new Name("White Shirt");
        Color color = new Color(ColorValue.WHITE);
        ClothingType clothingType = new ClothingType(ClothingTypeValue.TOP);
        Apparel apparel = new Apparel(name, color, clothingType);
        model.addApparel(apparel);


        name = new Name("Red Pants");
        color = new Color(ColorValue.RED);
        clothingType = new ClothingType(ClothingTypeValue.BOTTOM);
        apparel = new Apparel(name, color, clothingType);
        model.addApparel(apparel);

        name = new Name("Orange Belt");
        color = new Color(ColorValue.ORANGE);
        clothingType = new ClothingType(ClothingTypeValue.BELT);
        apparel = new Apparel(name, color, clothingType);
        model.addApparel(apparel);

        name = new Name("Grey Shoes");
        color = new Color(ColorValue.GREY);
        clothingType = new ClothingType(ClothingTypeValue.SHOES);
        apparel = new Apparel(name, color, clothingType);
        model.addApparel(apparel);

        final CommandRecommendation commandRecommendation = new CommandRecommendation(model);


        assertEquals(commandRecommendation.returnRecommendationString(),
                "White Shirt Color: WHITE Type: TOP Status: Clean Usage-count: 0\n"
                        + "Red Pants Color: RED Type: BOTTOM Status: Clean Usage-count: 0\n"
                        + "Grey Shoes Color: GREY Type: SHOES Status: Clean Usage-count: 0");
    }



    @Test
    public void noMatchesCase() {
        Name name = new Name("Grey Shirt");
        Color color = new Color(ColorValue.GREY);
        ClothingType clothingType = new ClothingType(ClothingTypeValue.TOP);
        Apparel apparel = new Apparel(name, color, clothingType);
        model.addApparel(apparel);


        name = new Name("Red Pants");
        color = new Color(ColorValue.RED);
        clothingType = new ClothingType(ClothingTypeValue.BOTTOM);
        apparel = new Apparel(name, color, clothingType);
        model.addApparel(apparel);

        name = new Name("Orange Belt");
        color = new Color(ColorValue.ORANGE);
        clothingType = new ClothingType(ClothingTypeValue.BELT);
        apparel = new Apparel(name, color, clothingType);
        model.addApparel(apparel);

        name = new Name("Grey Shoes");
        color = new Color(ColorValue.GREY);
        clothingType = new ClothingType(ClothingTypeValue.SHOES);
        apparel = new Apparel(name, color, clothingType);
        model.addApparel(apparel);

        final CommandRecommendation commandRecommendation = new CommandRecommendation(model);
        assertEquals(commandRecommendation.returnRecommendationString(), "");

    }
}
