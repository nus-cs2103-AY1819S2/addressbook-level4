package seedu.address.model.util;

import java.sql.Timestamp;
import java.time.LocalDateTime;
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
            new Restaurant(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"),
                getTagSet("friends"),
                getReviewSet("I love the chicken here! Great service too!", "5.0", "2019-03-04T13:32:20.323")),
            new Restaurant(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getTagSet("colleagues", "friends"),
                getReviewSet("Love the ambience. Food can be better though.", "3.5", "2019-03-04T13:35:20.321")),
            new Restaurant(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getTagSet("neighbours"),
                getReviewSet("Many great options.", "3.9", "2019-03-04T13:36:19.223",
                        "Commendable service.", "4.5", "2019-03-04T13:37:20.323")),
            new Restaurant(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getTagSet("family"),
                getReviewSet("Lovely staff, great tender chicken.", "4.8", "2019-03-04T13:38:10.100",
                        "Amazing delivery. 10/10 service.", "5.0", "2019-03-04T13:39:25.421")),
            new Restaurant(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"),
                getTagSet("classmates"),
                getReviewSet("Love the food.", "4.9", "2019-03-04T13:40:21.323")),
            new Restaurant(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"),
                getTagSet("colleagues"),
                getReviewSet("Love the waiters. Food looks great too.", "4", "2019-03-04T13:41:20.323")),
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
