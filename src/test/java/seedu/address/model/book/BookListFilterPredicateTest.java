package seedu.address.model.book;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Collections;
import java.util.List;

import org.junit.Test;

public class BookListFilterPredicateTest {
    @Test
    public void equal() {
        List<String> namePredicateFirst = Collections.singletonList("first");
        List<String> authorPredicateFirst = Collections.singletonList("first");
        List<String> tagPredicateFirst = Collections.singletonList("first");
        List<String> ratingPredicateFirst = Collections.singletonList("1");

        List<String> namePredicateSecond = Collections.singletonList("second");
        List<String> authorPredicateSecond = Collections.singletonList("second");
        List<String> tagPredicateSecond = Collections.singletonList("second");
        List<String> ratingPredicateSecond = Collections.singletonList("2");

        BookListFilterPredicate firstPredicate = new BookListFilterPredicate(namePredicateFirst, authorPredicateFirst,
                tagPredicateFirst, ratingPredicateFirst);
        BookListFilterPredicate secondPredicate = new BookListFilterPredicate(namePredicateSecond,
                authorPredicateSecond, tagPredicateSecond, ratingPredicateSecond);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        BookListFilterPredicate firstPredicateCopy =
                new BookListFilterPredicate(namePredicateFirst, authorPredicateFirst,
                        tagPredicateFirst, ratingPredicateFirst);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }
}
