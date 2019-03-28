package seedu.address.testutil;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.review.Review;

/**
 * A utility class containing a list of {@code Review} objects to be used in tests.
 */
public class TypicalReviews {

    public static final Review ASTONS = new ReviewBuilder().withEntry("Fresh and fast!")
            .withRating("4.8").withTimestamp(Timestamp.valueOf("2019-02-20 09:10:12")).build();
    public static final Review BALITHAI = new ReviewBuilder().withEntry("Not authentic enough :(")
            .withRating("2.5").withTimestamp(Timestamp.valueOf("2019-02-28 16:16:16")).build();
    public static final Review CARLSJR = new ReviewBuilder().withEntry("Such a big serving! I love it!")
            .withRating("5").withTimestamp(Timestamp.valueOf("2019-03-14 12:10:01")).build();
    public static final Review DELIFRANCE = new ReviewBuilder().withEntry("Smells better than it taste.")
            .withRating("3.6").withTimestamp(Timestamp.valueOf("2019-03-18 16:01:16")).build();
    public static final Review EAT = new ReviewBuilder().withEntry("Bang for bucks!")
            .withRating("4").withTimestamp(Timestamp.valueOf("2019-03-21 19:19:19")).build();
    public static final Review FISHANDCO = new ReviewBuilder().withEntry("Poor service and no chicken??")
            .withRating("3").withTimestamp(Timestamp.valueOf("2019-03-23 18:18:18")).build();
    public static final Review GREENDOT = new ReviewBuilder().withEntry("Limited selection and so bland!")
            .withRating("1.5").withTimestamp(Timestamp.valueOf("2019-03-25 21:12:12")).build();

    private TypicalReviews() {} // prevents instantiation

    public static List<Review> getTypicalReviews() {
        return new ArrayList<>(Arrays.asList(ASTONS, BALITHAI, CARLSJR, DELIFRANCE, EAT, FISHANDCO, GREENDOT));
    }
}
