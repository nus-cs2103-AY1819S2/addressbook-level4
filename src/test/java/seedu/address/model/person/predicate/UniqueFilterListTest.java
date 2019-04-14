package seedu.address.model.person.predicate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalObjects.FILTER;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.person.exceptions.DuplicateFilterException;
import seedu.address.model.person.exceptions.FilterNotFoundException;

public class UniqueFilterListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueFilterList uniqueFilterList = new UniqueFilterList();

    @Test
    public void contains_nullFilter_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueFilterList.contains(null);
    }

    @Test
    public void contains_personNotInList_returnsFalse() {
        assertFalse(uniqueFilterList.contains(FILTER));
    }

    @Test
    public void contains_personInList_returnsTrue() {
        uniqueFilterList.add(FILTER);
        assertTrue(uniqueFilterList.contains(FILTER));
    }

    @Test
    public void add_nullFilter_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueFilterList.add(null);
    }

    @Test
    public void add_duplicateFilter_throwsDuplicateFilterException() {
        uniqueFilterList.add(FILTER);
        thrown.expect(DuplicateFilterException.class);
        uniqueFilterList.add(FILTER);
    }

    @Test
    public void remove_nullFilter_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueFilterList.remove(null);
    }

    @Test
    public void remove_personDoesNotExist_throwsFilterNotFoundException() {
        thrown.expect(FilterNotFoundException.class);
        uniqueFilterList.remove(FILTER);
    }

    @Test
    public void remove_existingFilter_removesFilter() {
        uniqueFilterList.add(FILTER);
        uniqueFilterList.remove(FILTER);
        UniqueFilterList expectedUniqueFilterList = new UniqueFilterList();
        assertEquals(expectedUniqueFilterList, uniqueFilterList);
    }
}
