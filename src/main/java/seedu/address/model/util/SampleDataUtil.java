package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.FashionMatch;
import seedu.address.model.ReadOnlyFashionMatch;
import seedu.address.model.apparel.Apparel;
import seedu.address.model.apparel.ClothingType;
import seedu.address.model.apparel.Color;
import seedu.address.model.apparel.Name;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code FashionMatch} with sample data.
 */
public class SampleDataUtil {
    public static Apparel[] getSamplePersons() {
        return new Apparel[] {
            new Apparel(new Name("Formal Shirt"), new Color("White"), new ClothingType("Top"),
                    true, 3),

            new Apparel(new Name("Formal Pants"), new Color("Black"), new ClothingType("Bottom"),
                    true, 4),

            new Apparel(new Name("Formal Belt"), new Color("Black"), new ClothingType("Belt"),
                    true, 5),

            new Apparel(new Name("Formal Shoes"), new Color("Black"), new ClothingType("Shoes"),
                    true, 7),

            new Apparel(new Name("Uniqlo Tshirt"), new Color("Yellow"), new ClothingType("Top"),
                    true, 10),

            new Apparel(new Name("Casual Belt"), new Color("Red"), new ClothingType("Belt"),
                    true, 8),

            new Apparel(new Name("Sneakers"), new Color("White"), new ClothingType("Shoes"),
                    true, 9),

            new Apparel(new Name("Zara Tshirt"), new Color("Pink"), new ClothingType("Top"),
                    false, 6),

            new Apparel(new Name("Running Shoes"), new Color("Grey"), new ClothingType("Shoes"),
                    false, 9),

            new Apparel(new Name("Skinny Jeans"), new Color("Blue"), new ClothingType("Bottom"),
                    false, 8),

            new Apparel(new Name("Bermudas"), new Color("Cream"), new ClothingType("Bottom"),
                    false, 7),

            new Apparel(new Name("Cool Shirt"), new Color("Khaki"), new ClothingType("Top"),
                    false, 5),
        };
    }

    public static ReadOnlyFashionMatch getSampleAddressBook() {
        FashionMatch sampleAb = new FashionMatch();
        for (Apparel sampleApparel : getSamplePersons()) {
            sampleAb.addApparel(sampleApparel);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
