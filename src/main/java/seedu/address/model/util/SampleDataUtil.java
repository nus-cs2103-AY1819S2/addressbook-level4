package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.FoodDiary;
import seedu.address.model.ReadOnlyFoodDiary;
import seedu.address.model.restaurant.Address;
import seedu.address.model.restaurant.Email;
import seedu.address.model.restaurant.Name;
import seedu.address.model.restaurant.Phone;
import seedu.address.model.restaurant.Restaurant;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code FoodDiary} with sample data.
 */
public class SampleDataUtil {
    public static Restaurant[] getSampleRestaurants() {
        return new Restaurant[] {
            new Restaurant(new Name("Astons"), new Phone("66123024"), new Email("astons@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"),
                getTagSet("Western")),
            new Restaurant(new Name("Bangkok Jam"), new Phone("65272758"), new Email("bangkokjam@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getTagSet("Thai")),
            new Restaurant(new Name("Chili's"), new Phone("63210283"), new Email("chilis@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getTagSet("Mexican")),
            new Restaurant(new Name("Ding Tai Fung"), new Phone("61031282"), new Email("dingtaifung@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getTagSet("Chinese")),
            new Restaurant(new Name("Itacho"), new Phone("62492021"), new Email("itacho@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"),
                getTagSet("Japanese", "Ramen")),
            new Restaurant(new Name("McDonalds"), new Phone("62624417"), new Email("mcdonalds@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"),
                getTagSet("FastFood"))
        };
    }

    public static ReadOnlyFoodDiary getSampleFoodDiary() {
        FoodDiary sampleAb = new FoodDiary();
        for (Restaurant sampleRestaurant : getSampleRestaurants()) {
            sampleAb.addRestaurant(sampleRestaurant);
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
