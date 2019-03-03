package seedu.address.model.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.FoodDiary;
import seedu.address.model.ReadOnlyFoodDiary;
import seedu.address.model.restaurant.Address;
import seedu.address.model.restaurant.Email;
import seedu.address.model.restaurant.Name;
import seedu.address.model.restaurant.Phone;
import seedu.address.model.restaurant.Restaurant;
import seedu.address.model.restaurant.Weblink;
import seedu.address.model.review.Entry;
import seedu.address.model.review.Rating;
import seedu.address.model.review.Review;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code FoodDiary} with sample data.
 */
public class SampleDataUtil {
    public static Restaurant[] getSampleRestaurants() {
        return new Restaurant[] {
            new Restaurant(new Name("Astons"), new Phone("66123024"), new Email("astons@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"),
                getTagSet("Western"), new Weblink("astons.com.sg"),
                    getReviewSet("I love the chicken here! Great service too! # 5.0")),
            new Restaurant(new Name("Bangkok Jam"), new Phone("65272758"), new Email("bangkokjam@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getTagSet("Thai"), new Weblink("bangkokjam.com.sg"),
                    getReviewSet("Love the ambience. Food can be better though. # 3.5")),
            new Restaurant(new Name("Chilis"), new Phone("63210283"), new Email("chilis@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getTagSet("Mexican"), new Weblink("chilis.com.sg"),
                    getReviewSet("Love the food. # 4.9")),
            new Restaurant(new Name("Ding Tai Fung"), new Phone("61031282"), new Email("dingtaifung@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getTagSet("Chinese"), new Weblink("bangkokjam.com.sg"),
                    getReviewSet("Many great options. # 3.9", "Commendable service. # 4.5")),
            new Restaurant(new Name("Itacho"), new Phone("62492021"), new Email("itacho@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"),
                getTagSet("Japanese", "Ramen"), new Weblink("itacho.com.sg"),
                    getReviewSet("Lovely staff, great tender chicken. # 4.8", "Amazing delivery. "
                            + "10/10 service. # 5.0")),
            new Restaurant(new Name("McDonalds"), new Phone("62624417"), new Email("mcdonalds@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"),
                getTagSet("FastFood"), new Weblink("mcdonalds.com.sg"),
                    getReviewSet("Love the waiters. Food looks great too. # 4"))
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

    /**
     * Returns a review set containing the list of Reviews given.
     */
    public static Set<Review> getReviewSet(String... strings) {
        Set<Review> reviews = new HashSet<>();
        for (String string : strings) {
            String[] data = string.split("( # )");
            reviews.add(new Review(new Entry(data[0]), new Rating(data[1])));
        }
        return reviews;
    }
}
