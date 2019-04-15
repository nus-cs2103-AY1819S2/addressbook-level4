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
import seedu.address.model.restaurant.Postal;
import seedu.address.model.restaurant.Restaurant;
import seedu.address.model.restaurant.Weblink;
import seedu.address.model.restaurant.categories.Categories;
import seedu.address.model.restaurant.categories.Cuisine;
import seedu.address.model.restaurant.categories.Occasion;
import seedu.address.model.restaurant.categories.PriceRange;
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
                    new Address("Blk 30 Geylang Street 29, #06-40"), new Postal("267951"),
                    getTagSet("Birthday"), new Weblink("https://astons.com.sg"),
                    new OpeningHours("1100 to 2130"), getCategories("Western", "Fast Casual", "$$"),
                    getReviewList("I love the chicken here! Great service too!",
                            "5.0", "2019-03-04T13:32:20.323")),
            new Restaurant(new Name("Bangkok Jam"), new Phone("65272758"), new Email("bangkokjam@example.com"),
                    new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), new Postal("018956"),
                    getTagSet("Farewell"), new Weblink("https://bangkokjam.com.sg"),
                    new OpeningHours("1100 to 2200"), getCategories("Thai", "Fast Casual", "$$"),
                    getReviewList("Love the ambience. Food can be better though.",
                            "3.5", "2019-03-04T13:35:20.321")),
            new Restaurant(new Name("Chilis"), new Phone("63210283"), new Email("chilis@example.com"),
                    new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), new Postal("123456"),
                    getTagSet("Welcome"), new Weblink("https://chilis.sg"), new OpeningHours("0900 to 2100"),
                    getCategories("American", "Casual", "$$$"),
                    getReviewList("Love the food.", "4.9", "2019-03-04T13:40:21.323")),
            new Restaurant(new Name("Din Tai Fung"), new Phone("61031282"), new Email("dintaifung@example.com"),
                    new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), new Postal("267951"),
                    getTagSet("Taiwan"), new Weblink("https://dintaifung.com.sg"),
                    new OpeningHours("1000 to 2200"), getCategories("Chinese", "Premium Casual", "$$$$$"),
                    getReviewList("Many great options.", "3.9", "2019-03-04T13:36:19.223",
                            "Commendable service.", "4.5", "2019-03-04T13:37:20.323")),
            new Restaurant(new Name("Itacho"), new Phone("62492021"), new Email("itacho@example.com"),
                    new Address("Blk 47 Tampines Street 20, #17-35"), new Postal("267951"),
                    getTagSet("Cravings", "Treat"), new Weblink("No weblink added"),
                    new OpeningHours("1200 to 0000"), getCategories("Japanese", "Premium Casual", "$$$$"),
                    getReviewList("Lovely staff, great tender chicken.", "4.8", "2019-03-04T13:38:10.100",
                            "Amazing delivery. 10/10 service.", "5.0", "2019-03-04T13:39:25.421")),
            new Restaurant(new Name("McDonalds"), new Phone("62624417"), new Email("mcdonalds@example.com"),
                    new Address("Blk 45 Aljunied Street 85, #11-31"), new Postal("267951"),
                    getTagSet("Supper"),
                    new Weblink("https://mcdonalds.com.sg"), new OpeningHours("24hrs"),
                    getCategories("Western", "Fast Food", "$"),
                    getReviewList("Love the waiters. Food looks great too.", "4", "2019-03-04T13:41:20.323")),
            new Restaurant(new Name("NY Night Market"), new Phone("62624078"), new Email("No email added"),
                    new Address("3 Gateway Drive #01-08 Westgate"), new Postal("608532"), getTagSet("Cravings"),
                    new Weblink("No weblink added"), new OpeningHours("1100 to 2200"),
                    getCategories("Korean", "Premium Casual", "$$$$"), getReviewList()),
            new Restaurant(new Name("Creamier"), new Phone("62211076"), new Email("No email added"),
                    new Address("378 Yong Siak Street #01-18"), new Postal("163078"), getTagSet(),
                    new Weblink("No weblink added"), new OpeningHours("1100 to 2200"),
                    getCategories("Desert", "Fast Casual", "$"), getReviewList()),
            new Restaurant(new Name("Summer Hill"), new Phone("62515337"), new Email("No email added"),
                    new Address("106 Clementi Street 12 #01-62"), new Postal("120106"), getTagSet("HiddenGem"),
                    new Weblink("https://summerhill.sg"), new OpeningHours("1130 to 2100"),
                    getCategories("French", "Fine Dining", "$$$$$"),
                    getReviewList("The ambience is simply amazing.",
                    "5.0", "2019-03-15T18:32:20.323", "It is still as good as the first time.",
                            "5.0", "2019-03-22T19:15:33.322")),
            new Restaurant(new Name("The Salted Plum"), new Phone("62600155"), new Email("No email added"),
                    new Address("10 Circlular Road"), new Postal("049366"),
                    getTagSet(), new Weblink("No weblink added"),
                    new OpeningHours("No opening hours added"),
                    getCategories("Chinese", "Family Style", "$$$"),
                    getReviewList("Love their dimsum", "4.2", "2019-03-04T13:32:20.323")),
            new Restaurant(new Name("Park Bench Deli"), new Phone("No phone added"), new Email("No email added"),
                    new Address("179 Telok Ayer Street"), new Postal("068627"),
                    getTagSet(), new Weblink("No weblink added"),
                    new OpeningHours("No opening hours added"),
                    getCategories("Western", "Fast Casual", "$$$"),
                    getReviewList("Decent food. It is a little too expensive",
                            "3.0", "2019-03-01T08:11:20.323")),
            new Restaurant(new Name("Sunday Folks"), new Phone("64799166"), new Email("No email added"),
                    new Address("44 Jalan Merah Saga #01-52 Chip Bee Gardens"), new Postal("278116"),
                    getTagSet(), new Weblink("http://sundayfolks.com"),
                    new OpeningHours("No opening hours added"),
                    getCategories("Desert", "Fast Casual", "$$"),
                    getReviewList("Good desert place!",
                            "4.5", "2019-03-09T22:32:20.323")),
            new Restaurant(new Name("Dumpling Darlings"), new Phone("82239249"), new Email("No email added"),
                    new Address("44 Amoy Street"), new Postal("069870"),
                    getTagSet(), new Weblink("No weblink added"),
                    new OpeningHours("No opening hours added"),
                    getCategories("Taiwanese", "Family Style", "$$"),
                    getReviewList()),
            new Restaurant(new Name("Nipong Naepong"), new Phone("62624078"), new Email("No email added"),
                    new Address("50 Jurong Gateway Road #01-16 Jem"), new Postal("069870"),
                    getTagSet(), new Weblink("No weblink added"),
                    new OpeningHours("No opening hours added"),
                    getCategories("Japanese", "Premium Casual", "$$$"),
                    getReviewList("Their pasta is so creamy!!!!",
                            "4.7", "2019-03-15T18:32:20.323")),
            new Restaurant(new Name("Columbus Coffee Co"), new Phone("62536024"), new Email("No email added"),
                    new Address("220 Upper Thomson Road"), new Postal("574352"),
                    getTagSet("Brunch"), new Weblink("No weblink added"),
                    new OpeningHours("No opening hours added"),
                    getCategories("Western", "Premium Casual", "$$$"),
                    getReviewList("Coffee is above average.",
                            "4.2", "2019-03-07T09:32:20.323")),
            new Restaurant(new Name("The Masses"), new Phone("62660061"), new Email("reservations@themasses.sg"),
                    new Address("85 Beach Road #01-02"), new Postal("574352"),
                    getTagSet(), new Weblink("http://themasses.sg"),
                    new OpeningHours("1200 to 2200"),
                    getCategories("European", "Fine Dining", "$$$$$"),
                    getReviewList("An eye opening experience. First authentic European meal",
                            "4.0", "2019-03-02T19:45:20.323")),
            new Restaurant(new Name("Kra Pow Thai Street Food"), new Phone("97933122"),
                    new Email("No email added"), new Address("14 Scotts Road #03-26/27 Far East Plaza"),
                    new Postal("228123"), getTagSet(), new Weblink("No weblink added"),
                    new OpeningHours("1800 to 2200"),
                    getCategories("Thai", "Casual", "$"), getReviewList()),
            new Restaurant(new Name("Fat Belly"), new Phone("63142247"),
                    new Email("No email added"), new Address("10 Jalan Serene #01-04 Serene Centre"),
                    new Postal("258748"), getTagSet("Date"),
                    new Weblink("No weblink added"), new OpeningHours("1130 to 2100"),
                    getCategories("Western", "Fine Dining", "$$$$$"), getReviewList()),
            new Restaurant(new Name("Artichoke"), new Phone("63366949"),
                    new Email("hello@artichoke.com.sg"), new Address("161 Middle Road"),
                    new Postal("188978"), getTagSet(),
                    new Weblink("http://www.artichoke.com.sg"), new OpeningHours("No opening hours added"),
                    getCategories("MiddleEast", "Fine Dining", "$$$$$"),
                    getReviewList("The staff is very friendly!", "4.2", "2019-03-17T18:01:20.323")),
            new Restaurant(new Name("Kogane Yama"), new Phone("No phone added"),
                    new Email("hello@artichoke.com.sg"), new Address("200 Victoria Street #02-50 Bugis Junction"),
                    new Postal("188021"), getTagSet(),
                    new Weblink("No weblink added"), new OpeningHours("1100 to 2200"),
                    getCategories("Japanese", "Casual", "$$"), getReviewList()),
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
     * Returns a Categories containing the cuisine and occasion given
     */
    public static Categories getCategories(String cuisine, String occasion, String priceRange) {
        return new Categories(new Cuisine(cuisine), new Occasion(occasion), new PriceRange(priceRange));
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
