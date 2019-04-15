package seedu.address.model.medicine;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.model.medicine.predicates.NameContainsKeywordsPredicate;
import seedu.address.testutil.MedicineBuilder;

public class NameContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        NameContainsKeywordsPredicate firstPredicate = new NameContainsKeywordsPredicate(firstPredicateKeywordList);
        NameContainsKeywordsPredicate secondPredicate = new NameContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        NameContainsKeywordsPredicate firstPredicateCopy = new NameContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different medicine -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(Collections.singletonList(
                "Paracetamol"));
        assertTrue(predicate.test(new MedicineBuilder().withName("Paracetamol Gabapentin").build()));

        // Multiple keywords
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("Paracetamol", "Gabapentin"));
        assertTrue(predicate.test(new MedicineBuilder().withName("Paracetamol Gabapentin").build()));

        // Only one matching keyword
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("Gabapentin", "Calcipotriene"));
        assertTrue(predicate.test(new MedicineBuilder().withName("Paracetamol Calcipotriene").build()));

        // Mixed-case keywords
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("Paracetamol", "Gabapentin"));
        assertTrue(predicate.test(new MedicineBuilder().withName("Paracetamol Gabapentin    ").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new MedicineBuilder().withName("Paracetamol").build()));

        // Non-matching keyword
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("Calcipotriene"));
        assertFalse(predicate.test(new MedicineBuilder().withName("Paracetamol Gabapentin").build()));

        // Keywords match quantity, expiry date and company, but does not match name
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("123", "06/10/2019", "Adcock", "Ingram"));
        assertFalse(predicate.test(new MedicineBuilder().withName("Paracetamol").withQuantity("123")
                .withExpiry("06/10/2019").withCompany("Adcock Ingram").build()));
    }
}
