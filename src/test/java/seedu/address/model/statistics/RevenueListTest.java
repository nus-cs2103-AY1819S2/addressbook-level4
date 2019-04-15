package seedu.address.model.statistics;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalRestOrRant.DAILY_REVENUE1;
import static seedu.address.testutil.TypicalRestOrRant.DAILY_REVENUE2;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.statistics.exceptions.DuplicateRevenueException;
import seedu.address.model.statistics.exceptions.RevenueNotFoundException;
import seedu.address.testutil.StatisticsBuilder;

public class RevenueListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final RevenueList revenueList = new RevenueList();

    @Test
    public void contains_nullDailyRevenue_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        revenueList.contains(null);
    }

    @Test
    public void contains_dailyRevenueNotInList_returnsFalse() {
        assertFalse(revenueList.contains(DAILY_REVENUE1));
    }

    @Test
    public void contains_dailyRevenueInList_returnsTrue() {
        revenueList.add(DAILY_REVENUE1);
        assertTrue(revenueList.contains(DAILY_REVENUE1));
    }

    @Test
    public void contains_dailyRevenueWithSameIdentityFieldsInList_returnsTrue() {
        revenueList.add(DAILY_REVENUE1);
        Revenue editedRevenue = new StatisticsBuilder(DAILY_REVENUE1).withTotalRevenue("300").build();
        assertTrue(revenueList.contains(editedRevenue));
    }

    @Test
    public void add_nullDailyRevenue_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        revenueList.add(null);
    }

    @Test
    public void add_duplicateDailyRevenue_throwsDuplicateDailyRevenueException() {
        revenueList.add(DAILY_REVENUE1);
        thrown.expect(DuplicateRevenueException.class);
        revenueList.add(DAILY_REVENUE1);
    }
    @Test
    public void setDailyRevenue_nullTargetDailyRevenue_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        revenueList.setRevenue(null, DAILY_REVENUE1);
    }

    @Test
    public void setDailyRevenue_nullEditedDailyRevenue_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        revenueList.setRevenue(DAILY_REVENUE1, null);
    }

    @Test
    public void setDailyRevenue_targetDailyRevenueNotInList_throwsDailyRevenueNotFoundException() {
        thrown.expect(RevenueNotFoundException.class);
        revenueList.setRevenue(DAILY_REVENUE1, DAILY_REVENUE1);
    }

    @Test
    public void setDailyRevenue_editedDailyRevenueIsSameDailyRevenue_success() {
        revenueList.add(DAILY_REVENUE1);
        revenueList.setRevenue(DAILY_REVENUE1, DAILY_REVENUE1);
        RevenueList expectedRevenueList = new RevenueList();
        expectedRevenueList.add(DAILY_REVENUE1);
        assertEquals(expectedRevenueList, revenueList);
    }

    @Test
    public void setDailyRevenue_editedDailyRevenueHasSameIdentity_success() {
        revenueList.add(DAILY_REVENUE1);
        Revenue editedRevenue = new StatisticsBuilder(DAILY_REVENUE1).withTotalRevenue("300").build();
        revenueList.setRevenue(DAILY_REVENUE1, editedRevenue);
        RevenueList expectedRevenueList = new RevenueList();
        expectedRevenueList.add(editedRevenue);
        assertEquals(expectedRevenueList, revenueList);
    }

    @Test
    public void setDailyRevenue_editedDailyRevenueHasDifferentIdentity_success() {
        revenueList.add(DAILY_REVENUE1);
        revenueList.setRevenue(DAILY_REVENUE1, DAILY_REVENUE2);
        RevenueList expectedRevenueList = new RevenueList();
        expectedRevenueList.add(DAILY_REVENUE2);
        assertEquals(expectedRevenueList, revenueList);
    }

    @Test
    public void setDailyRevenue_editedDailyRevenueHasNonUniqueIdentity_throwsDuplicateDailyRevenueException() {
        revenueList.add(DAILY_REVENUE1);
        revenueList.add(DAILY_REVENUE2);
        thrown.expect(DuplicateRevenueException.class);
        revenueList.setRevenue(DAILY_REVENUE1, DAILY_REVENUE2);
    }

    @Test
    public void remove_nullDailyRevenue_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        revenueList.remove(null);
    }

    @Test
    public void remove_dailyRevenueDoesNotExist_throwsDailyRevenueNotFoundException() {
        thrown.expect(RevenueNotFoundException.class);
        revenueList.remove(DAILY_REVENUE1);
    }

    @Test
    public void remove_existingDailyRevenue_removesDailyRevenue() {
        revenueList.add(DAILY_REVENUE1);
        revenueList.remove(DAILY_REVENUE1);
        RevenueList expectedRevenueList = new RevenueList();
        assertEquals(expectedRevenueList, revenueList);
    }

    @Test
    public void setDailyRevenue_nullUniqueDailyRevenueList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        revenueList.setRevenue((RevenueList) null);
    }

    @Test
    public void setDailyRevenue_dailyRevenueList_replacesOwnListWithProvidedDailyRevenueList() {
        revenueList.add(DAILY_REVENUE1);
        RevenueList expectedRevenueList = new RevenueList();
        expectedRevenueList.add(DAILY_REVENUE2);
        revenueList.setRevenue(expectedRevenueList);
        assertEquals(expectedRevenueList, revenueList);
    }

    @Test
    public void setDailyRevenue_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        revenueList.setRevenueList((List<Revenue>) null);
    }

    @Test
    public void setDailyRevenue_list_replacesOwnListWithProvidedList() {
        revenueList.add(DAILY_REVENUE1);
        List<Revenue> dailyRevenueList = Collections.singletonList(DAILY_REVENUE2);
        this.revenueList.setRevenueList(dailyRevenueList);
        RevenueList expectedRevenueList = new RevenueList();
        expectedRevenueList.add(DAILY_REVENUE2);
        assertEquals(expectedRevenueList, this.revenueList);
    }

    @Test
    public void setDailyRevenue_listWithDuplicateDailyRevenues_throwsDuplicateDailyRevenueException() {
        List<Revenue> listWithDuplicateDailyRevenue = Arrays.asList(DAILY_REVENUE1, DAILY_REVENUE1);
        thrown.expect(DuplicateRevenueException.class);
        revenueList.setRevenueList(listWithDuplicateDailyRevenue);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        revenueList.asUnmodifiableObservableList().remove(0);
    }
}
