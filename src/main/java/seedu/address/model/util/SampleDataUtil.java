package seedu.address.model.util;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.FoodDiary;
import seedu.address.model.ReadOnlyFoodDiary;
import seedu.address.model.restaurant.Address;
import seedu.address.model.restaurant.Email;
import seedu.address.model.restaurant.Name;
import seedu.address.model.restaurant.OpeningHours;
import seedu.address.model.restaurant.Phone;
import seedu.address.model.restaurant.Restaurant;
import seedu.address.model.restaurant.Weblink;
import seedu.address.model.restaurant.categories.Category;
import seedu.address.model.restaurant.categories.Cuisine;
import seedu.address.model.restaurant.categories.Occasion;
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
                    getTagSet("Birthday"), new Weblink("https://astons.com.sg"),
                    new OpeningHours("1100 to 2130"), getCategories("Western", "Fast Casual"),
                    getReviewList("I love the chicken here! Great service too!",
                            "5.0", "2019-03-04T13:32:20.323")),
            new Restaurant(new Name("Bangkok Jam"), new Phone("65272758"), new Email("bangkokjam@example.com"),
                    new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                    getTagSet("Farewell"), new Weblink("https://bangkokjam.com.sg"),
                    new OpeningHours("1100 to 2200"), getCategories("Thai", "Fast Casual"),
                    getReviewList("Love the ambience. Food can be better though.",
                            "3.5", "2019-03-04T13:35:20.321")),
            new Restaurant(new Name("Chilis"), new Phone("63210283"), new Email("chilis@example.com"),
                    new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                    getTagSet("Welcome"), new Weblink("https://chilis.sg"), new OpeningHours("0900 to 2100"),
                    getCategories("American", "Casual"),
                    getReviewList("Love the food.", "4.9", "2019-03-04T13:40:21.323")),
            new Restaurant(new Name("Din Tai Fung"), new Phone("61031282"), new Email("dintaifung@example.com"),
                    new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                    getTagSet("Taiwan"), new Weblink("https://dintaifung.com.sg"),
                    new OpeningHours("1000 to 2200"), getCategories("Chinese", "Premium Casual"),
                    getReviewList("Many great options.", "3.9", "2019-03-04T13:36:19.223",
                            "Commendable service.", "4.5", "2019-03-04T13:37:20.323")),
            new Restaurant(new Name("Itacho"), new Phone("62492021"), new Email("itacho@example.com"),
                    new Address("Blk 47 Tampines Street 20, #17-35"),
                    getTagSet("Cravings", "Treat"), new Weblink("https://itacho.com.sg"),
                    new OpeningHours("1200 to 0000"), getCategories("Japanese", "Premium Casual"),
                    getReviewList("Lovely staff, great tender chicken.", "4.8", "2019-03-04T13:38:10.100",
                            "Amazing delivery. 10/10 service.", "5.0", "2019-03-04T13:39:25.421")),
            new Restaurant(new Name("McDonalds"), new Phone("62624417"), new Email("mcdonalds@example.com"),
                    new Address("Blk 45 Aljunied Street 85, #11-31"), getTagSet("Supper"),
                    new Weblink("https://mcdonalds.com.sg"), new OpeningHours("24hrs"),
                    getCategories("Western", "Fast Food"),
                    getReviewList("Love the waiters. Food looks great too.", "4", "2019-03-04T13:41:20.323"))
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
     * Returns a Category containing the cuisine and occasion given
     */
    public static Category getCategories(String cuisine, String occasion) {
        return new Category(new Cuisine(cuisine), new Occasion(occasion));
    }

    /**
     * Returns a review set containing the list of Reviews given.
     */
    public static List<Review> getReviewList(String... strings) {
        List<Review> reviews = new ArrayList<>();

        for (int i = 0; i < strings.length; i += 3) {
            Entry newEntry = new Entry(strings[i]);
            Rating newRating = new Rating(strings[i + 1]);
            Timestamp newTimestamp = Timestamp.valueOf(LocalDateTime.parse(strings[i + 2]));
            Review newReview = new Review(newEntry, newRating, newTimestamp);
            reviews.add(newReview);
        }

        return reviews;
    }
}
