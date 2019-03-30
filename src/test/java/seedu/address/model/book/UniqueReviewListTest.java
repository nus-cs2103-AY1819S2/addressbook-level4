package seedu.address.model.review;

import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.Test;
import seedu.address.model.book.Review;
import seedu.address.model.book.UniqueReviewList;
import seedu.address.model.book.exceptions.DuplicateReviewException;
import seedu.address.model.book.exceptions.ReviewNotFoundException;
import seedu.address.testutil.ReviewBuilder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ALTERNATIVE_DATE;
import static seedu.address.testutil.TypicalBooks.ALI;
import static seedu.address.testutil.TypicalBooks.ALICE_REVIEW;
import static seedu.address.testutil.TypicalBooks.CS_REVIEW;

public class UniqueReviewListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueReviewList uniqueReviewList = new UniqueReviewList();

    @Test
    public void contains_nullReview_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueReviewList.contains(null);
    }

    @Test
    public void contains_reviewNotInList_returnsFalse() {
        assertFalse(uniqueReviewList.contains(ALICE_REVIEW));
    }

    @Test
    public void contains_reviewInList_returnsTrue() {
        uniqueReviewList.add(ALICE_REVIEW);
        assertTrue(uniqueReviewList.contains(ALICE_REVIEW));
    }

    @Test
    public void contains_reviewWithSameIdentityFieldsInList_returnsTrue() {
        uniqueReviewList.add(ALICE_REVIEW);
        Review editedAlice = new ReviewBuilder(ALICE_REVIEW).withDateCreated(VALID_ALTERNATIVE_DATE).build();
        assertTrue(uniqueReviewList.contains(editedAlice));
    }

    @Test
    public void add_nullReview_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueReviewList.add(null);
    }

    @Test
    public void add_duplicateReview_throwsDuplicateReviewException() {
        uniqueReviewList.add(ALICE_REVIEW);
        thrown.expect(DuplicateReviewException.class);
        uniqueReviewList.add(ALICE_REVIEW);
    }

    @Test
    public void setReview_nullTargetReview_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueReviewList.setReview(null, ALICE_REVIEW);
    }

    @Test
    public void setReview_nullEditedReview_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueReviewList.setReview(ALICE_REVIEW, null);
    }

    @Test
    public void setReview_targetReviewNotInList_throwsReviewNotFoundException() {
        thrown.expect(ReviewNotFoundException.class);
        uniqueReviewList.setReview(ALICE_REVIEW, ALICE_REVIEW);
    }

    @Test
    public void setReview_editedReviewIsSameReview_success() {
        uniqueReviewList.add(ALICE_REVIEW);
        uniqueReviewList.setReview(ALICE_REVIEW, ALICE_REVIEW);
        UniqueReviewList expectedUniqueReviewList = new UniqueReviewList();
        expectedUniqueReviewList.add(ALICE_REVIEW);
        assertEquals(expectedUniqueReviewList, uniqueReviewList);
    }

    @Test
    public void setReview_editedReviewHasSameIdentity_success() {
        uniqueReviewList.add(ALICE_REVIEW);
        Review editedAlice = new ReviewBuilder(ALICE_REVIEW).withDateCreated(VALID_ALTERNATIVE_DATE).build();
        uniqueReviewList.setReview(ALICE_REVIEW, editedAlice);
        UniqueReviewList expectedUniqueReviewList = new UniqueReviewList();
        expectedUniqueReviewList.add(editedAlice);
        assertEquals(expectedUniqueReviewList, uniqueReviewList);
    }

    @Test
    public void setReview_editedReviewHasDifferentIdentity_success() {
        uniqueReviewList.add(ALICE_REVIEW);
        uniqueReviewList.setReview(ALICE_REVIEW, CS_REVIEW);
        UniqueReviewList expectedUniqueReviewList = new UniqueReviewList();
        expectedUniqueReviewList.add(CS_REVIEW);
        assertEquals(expectedUniqueReviewList, uniqueReviewList);
    }

    @Test
    public void setReview_editedReviewHasNonUniqueIdentity_throwsDuplicateReviewException() {
        uniqueReviewList.add(ALICE_REVIEW);
        uniqueReviewList.add(CS_REVIEW);
        thrown.expect(DuplicateReviewException.class);
        uniqueReviewList.setReview(ALICE_REVIEW, CS_REVIEW);
    }

    @Test
    public void remove_nullReview_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueReviewList.remove(null);
    }

    @Test
    public void remove_reviewDoesNotExist_throwsReviewNotFoundException() {
        thrown.expect(ReviewNotFoundException.class);
        uniqueReviewList.remove(ALICE_REVIEW);
    }

    @Test
    public void remove_existingReview_removesReview() {
        uniqueReviewList.add(ALICE_REVIEW);
        uniqueReviewList.remove(ALICE_REVIEW);
        UniqueReviewList expectedUniqueReviewList = new UniqueReviewList();
        assertEquals(expectedUniqueReviewList, uniqueReviewList);
    }

    @Test
    public void setReviews_nullUniqueReviewList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueReviewList.setReviews((UniqueReviewList) null);
    }

    @Test
    public void setReviews_uniqueReviewList_replacesOwnListWithProvidedUniqueReviewList() {
        uniqueReviewList.add(ALICE_REVIEW);
        UniqueReviewList expectedUniqueReviewList = new UniqueReviewList();
        expectedUniqueReviewList.add(CS_REVIEW);
        uniqueReviewList.setReviews(expectedUniqueReviewList);
        assertEquals(expectedUniqueReviewList, uniqueReviewList);
    }

    @Test
    public void setReviews_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueReviewList.setReviews((List<Review>) null);
    }

    @Test
    public void setReviews_list_replacesOwnListWithProvidedList() {
        uniqueReviewList.add(ALICE_REVIEW);
        List<Review> reviewList = Collections.singletonList(CS_REVIEW);
        uniqueReviewList.setReviews(reviewList);
        UniqueReviewList expectedUniqueReviewList = new UniqueReviewList();
        expectedUniqueReviewList.add(CS_REVIEW);
        assertEquals(expectedUniqueReviewList, uniqueReviewList);
    }

    @Test
    public void setReviews_listWithDuplicateReviews_throwsDuplicateReviewException() {
        List<Review> listWithDuplicateReviews = Arrays.asList(ALICE_REVIEW, ALICE_REVIEW);
        thrown.expect(DuplicateReviewException.class);
        uniqueReviewList.setReviews(listWithDuplicateReviews);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueReviewList.asUnmodifiableObservableList().remove(0);
    }
}
