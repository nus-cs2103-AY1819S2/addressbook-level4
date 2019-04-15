package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_COLOR_BLUE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COLOR_GREEN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_A;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_B;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TYPE_BOTTOM;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TYPE_TOP;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.FashionMatch;
import seedu.address.model.apparel.Apparel;

/**
 * A utility class containing a list of {@code Apparel} objects to be used in tests.
 */
public class TypicalApparels {

    public static final String NAME_FORMAL_SHIRT = "A Formal Shirt";
    public static final String NAME_FORMAL_PANTS = "B Formal Pants";
    public static final String NAME_INFORMAL_SHIRT = "C Informal Shirt";
    public static final String NAME_FORMAL_BELT = "D Formal Belt";
    public static final String NAME_CASUAL_BELT = "E Casual Belt";
    public static final String NAME_COOL_BELT = "F Cool Belt";
    public static final String NAME_FORMAL_SHOES = "G Formal Shoes";
    public static final String NAME_CASUAL_SHOES = "H Casual Shoes";
    public static final String NAME_SNEAKERS = "I Sneakers";

    public static final String NAME_FORMAL_SHIRT_LOWER = "aa formal shirt";

    public static final String TYPE_TOP = "Top";
    public static final String TYPE_BOTTOM = "Bottom";
    public static final String TYPE_BELT = "Belt";
    public static final String TYPE_SHOES = "Shoes";

    public static final String COLOR_WHITE = "White";
    public static final String COLOR_BLACK = "Black";
    public static final String COLOR_NAVY = "Navy";
    public static final String COLOR_BROWN = "Brown";
    public static final String COLOR_GREEN = "Green";
    public static final String COLOR_GREY = "Grey";



    public static final Apparel SHIRT1 = new ApparelBuilder().withName(NAME_FORMAL_SHIRT)
            .withColor(COLOR_WHITE).withClothingType(TYPE_TOP).build();
    public static final Apparel SHIRT2 = new ApparelBuilder().withName(NAME_INFORMAL_SHIRT)
            .withColor(COLOR_NAVY).withClothingType(TYPE_TOP).build();
    public static final Apparel SHIRT3 = new ApparelBuilder().withName(NAME_INFORMAL_SHIRT)
            .withColor(COLOR_NAVY).withClothingType(TYPE_TOP).build().setWorn();

    public static final Apparel PANTS1 = new ApparelBuilder().withName(NAME_FORMAL_PANTS)
            .withColor(COLOR_BROWN).withClothingType(TYPE_BOTTOM).build();

    public static final Apparel BELT1 = new ApparelBuilder().withName(NAME_FORMAL_BELT)
            .withColor(COLOR_BLACK).withClothingType(TYPE_BELT).build();
    public static final Apparel BELT2 = new ApparelBuilder().withName(NAME_CASUAL_BELT)
            .withColor(COLOR_WHITE).withClothingType(TYPE_BELT).build();
    public static final Apparel BELT3 = new ApparelBuilder().withName(NAME_COOL_BELT)
            .withColor(COLOR_NAVY).withClothingType(TYPE_BELT).build();
    public static final Apparel SHOES1 = new ApparelBuilder().withName(NAME_FORMAL_SHOES)
            .withColor(COLOR_BROWN).withClothingType(TYPE_SHOES).build();
    public static final Apparel SHOES2 = new ApparelBuilder().withName(NAME_CASUAL_SHOES)
            .withColor(COLOR_GREEN).withClothingType(TYPE_SHOES).build();
    public static final Apparel SHOES3 = new ApparelBuilder().withName(NAME_SNEAKERS)
            .withColor(COLOR_GREY).withClothingType(TYPE_SHOES).build();

    public static final Apparel SHIRT1_LOWER = new ApparelBuilder().withName(NAME_FORMAL_SHIRT_LOWER)
            .withColor(COLOR_WHITE).withClothingType(TYPE_TOP).build();

    // Manually added - Apparel's details found in {@code CommandTestUtil}
    public static final Apparel ARMANY = new ApparelBuilder().withName(VALID_NAME_A)
            .withColor(VALID_COLOR_GREEN).withClothingType(VALID_TYPE_TOP).build();
    public static final Apparel BOBYIN = new ApparelBuilder().withName(VALID_NAME_B)
            .withColor(VALID_COLOR_BLUE).withClothingType(VALID_TYPE_BOTTOM).build();

    public static final String KEYWORD_MATCHING_BELT = "Belt"; // A keyword that matches Belt

    private TypicalApparels() {} // prevents instantiation

    /**
     * Returns an {@code FashionMatch} with all the typical persons.
     */
    public static FashionMatch getTypicalAddressBook() {
        FashionMatch ab = new FashionMatch();
        for (Apparel apparel : getTypicalApparels()) {
            ab.addApparel(apparel);
        }
        return ab;
    }

    public static FashionMatch getDirtyAddressBook() {
        FashionMatch ab = new FashionMatch();
        ab.addApparel(SHIRT3);
        return ab;
    }

    /**
     * Returns an {@code FashionMatch} with all the typical persons.
     */
    public static FashionMatch getTypicalAddressBookWithLowerCaseName() {
        FashionMatch ab = new FashionMatch();
        for (Apparel apparel : getTypicalApparelsWithLowerCaseName()) {
            ab.addApparel(apparel);
        }
        return ab;
    }

    /**
     * Returns an {@code FashionMatch} with all the typical persons sorted by name.
     */
    public static FashionMatch getTypicalAddressBookSortedByName() {
        FashionMatch ab = new FashionMatch();
        for (Apparel apparel : getTypicalApparelsSortedByName()) {
            ab.addApparel(apparel);
        }
        return ab;
    }

    /**
     * Returns an {@code FashionMatch} with all the typical persons sorted by name.
     */
    public static FashionMatch getTypicalAddressBookSortedByNameCaseInsensitive() {
        FashionMatch ab = new FashionMatch();
        for (Apparel apparel : getTypicalApparelsSortedByNameCaseInsensitive()) {
            ab.addApparel(apparel);
        }
        return ab;
    }

    /**
     * Returns an {@code FashionMatch} with all the typical persons sorted by color.
     */
    public static FashionMatch getTypicalAddressBookSortedByColor() {
        FashionMatch ab = new FashionMatch();
        for (Apparel apparel : getTypicalApparelsSortedByColor()) {
            ab.addApparel(apparel);
        }
        return ab;
    }

    /**
     * Returns an {@code FashionMatch} with all the typical persons sorted by color.
     */
    public static FashionMatch getTypicalAddressBookSortedByType() {
        FashionMatch ab = new FashionMatch();
        for (Apparel apparel : getTypicalApparelsSortedByType()) {
            ab.addApparel(apparel);
        }
        return ab;
    }

    /**
     * Returns an {@code FashionMatch} with first and second indexed item swapped.
     */
    public static FashionMatch getTypicalAddressBookSwappedIndexOneAndTwo() {
        FashionMatch ab = new FashionMatch();
        for (Apparel apparel : getTypicalApparelsSwappedOneAndTwo()) {
            ab.addApparel(apparel);
        }
        return ab;
    }



    public static List<Apparel> getTypicalApparels() {
        return new ArrayList<>(Arrays.asList(SHIRT1, SHIRT2, PANTS1, BELT1, BELT2, BELT3, SHOES1, SHOES2, SHOES3));
    }

    public static List<Apparel> getTypicalApparelsSwappedOneAndTwo() {
        return new ArrayList<>(Arrays.asList(SHIRT2, SHIRT1, PANTS1, BELT1, BELT2, BELT3, SHOES1, SHOES2, SHOES3));
    }

    public static List<Apparel> getTypicalApparelsWithLowerCaseName() {
        return new ArrayList<>(Arrays.asList(SHIRT1_LOWER, SHIRT1, SHIRT2, PANTS1,
                BELT1, BELT2, BELT3, SHOES1, SHOES2, SHOES3));
    }

    public static List<Apparel> getTypicalApparelsSortedByName() {
        return new ArrayList<>(Arrays.asList(SHIRT1, PANTS1, SHIRT2, BELT1, BELT2, BELT3, SHOES1, SHOES2, SHOES3));
    }

    public static List<Apparel> getTypicalApparelsSortedByNameCaseInsensitive() {
        return new ArrayList<>(Arrays.asList(SHIRT1, SHIRT1_LOWER, PANTS1, SHIRT2,
                BELT1, BELT2, BELT3, SHOES1, SHOES2, SHOES3));
    }

    public static List<Apparel> getTypicalApparelsSortedByColor() {
        return new ArrayList<>(Arrays.asList(BELT1, PANTS1, SHOES1, SHOES2, SHOES3, SHIRT2, BELT3, SHIRT1, BELT2));
    }

    public static List<Apparel> getTypicalApparelsSortedByType() {
        return new ArrayList<>(Arrays.asList(BELT1, BELT2, BELT3, PANTS1, SHOES1, SHOES2, SHOES3, SHIRT1, SHIRT2));
    }
}
