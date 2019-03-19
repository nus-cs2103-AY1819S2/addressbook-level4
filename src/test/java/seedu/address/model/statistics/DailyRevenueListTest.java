package seedu.address.model.statistics;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalRestOrRant.DAILY_REVENUE1;
import static seedu.address.testutil.TypicalRestOrRant.DAILY_REVENUE2;

import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.statistics.exceptions.DailyRevenueNotFoundException;
import seedu.address.testutil.StatisticsBuilder;

public class DailyRevenueListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final DailyRevenueList dailyRevenueList = new DailyRevenueList();

    @Test
    public void contains_nullDailyRevenue_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        dailyRevenueList.contains(null);
    }

    @Test
    public void contains_dailyRevenueNotInList_returnsFalse() {
        assertFalse(dailyRevenueList.contains(DAILY_REVENUE1));
    }

    @Test
    public void contains_dailyRevenueInList_returnsTrue() {
        dailyRevenueList.add(DAILY_REVENUE1);
        assertTrue(dailyRevenueList.contains(DAILY_REVENUE1));
    }

    @Test
    public void contains_dailyRevenueWithSameIdentityFieldsInList_returnsTrue() {
        dailyRevenueList.add(DAILY_REVENUE1);
        DailyRevenue editedDailyRevenue = new StatisticsBuilder(DAILY_REVENUE1).withTotalDailyRevenue("300").build();
        assertTrue(dailyRevenueList.contains(editedDailyRevenue));
    }

    @Test
    public void add_nullDailyRevenue_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        dailyRevenueList.add(null);
    }

    @Test
    public void setDailyRevenue_nullTargetDailyRevenue_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        dailyRevenueList.setDailyRevenue(null, DAILY_REVENUE1);
    }

    @Test
    public void setDailyRevenue_nullEditedDailyRevenue_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        dailyRevenueList.setDailyRevenue(DAILY_REVENUE1, null);
    }

    @Test
    public void setDailyRevenue_targetDailyRevenueNotInList_throwsDailyRevenueNotFoundException() {
        thrown.expect(DailyRevenueNotFoundException.class);
        dailyRevenueList.setDailyRevenue(DAILY_REVENUE1, DAILY_REVENUE1);
    }

    @Test
    public void setDailyRevenue_editedDailyRevenueIsSameDailyRevenue_success() {
        dailyRevenueList.add(DAILY_REVENUE1);
        dailyRevenueList.setDailyRevenue(DAILY_REVENUE1, DAILY_REVENUE1);
        DailyRevenueList expectedDailyRevenueList = new DailyRevenueList();
        expectedDailyRevenueList.add(DAILY_REVENUE1);
        assertEquals(expectedDailyRevenueList, dailyRevenueList);
    }

    @Test
    public void setDailyRevenue_editedDailyRevenueHasSameIdentity_success() {
        dailyRevenueList.add(DAILY_REVENUE1);
        DailyRevenue editedDailyRevenue = new StatisticsBuilder(DAILY_REVENUE1).withTotalDailyRevenue("300").build();
        dailyRevenueList.setDailyRevenue(DAILY_REVENUE1, editedDailyRevenue);
        DailyRevenueList expectedDailyRevenueList = new DailyRevenueList();
        expectedDailyRevenueList.add(editedDailyRevenue);
        assertEquals(expectedDailyRevenueList, dailyRevenueList);
    }

    @Test
    public void setDailyRevenue_editedDailyRevenueHasDifferentIdentity_success() {
        dailyRevenueList.add(DAILY_REVENUE1);
        dailyRevenueList.setDailyRevenue(DAILY_REVENUE1, DAILY_REVENUE2);
        DailyRevenueList expectedDailyRevenueList = new DailyRevenueList();
        expectedDailyRevenueList.add(DAILY_REVENUE2);
        assertEquals(expectedDailyRevenueList, dailyRevenueList);
    }

    @Test
    public void remove_nullDailyRevenue_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        dailyRevenueList.remove(null);
    }

    @Test
    public void remove_dailyRevenueDoesNotExist_throwsDailyRevenueNotFoundException() {
        thrown.expect(DailyRevenueNotFoundException.class);
        dailyRevenueList.remove(DAILY_REVENUE1);
    }

    @Test
    public void remove_existingDailyRevenue_removesDailyRevenue() {
        dailyRevenueList.add(DAILY_REVENUE1);
        dailyRevenueList.remove(DAILY_REVENUE1);
        DailyRevenueList expectedDailyRevenueList = new DailyRevenueList();
        assertEquals(expectedDailyRevenueList, dailyRevenueList);
    }

    @Test
    public void setDailyRevenue_nullUniqueDailyRevenueList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        dailyRevenueList.setDailyRevenue((DailyRevenueList) null);
    }

    @Test
    public void setDailyRevenue_dailyRevenueList_replacesOwnListWithProvidedDailyRevenueList() {
        dailyRevenueList.add(DAILY_REVENUE1);
        DailyRevenueList expectedDailyRevenueList = new DailyRevenueList();
        expectedDailyRevenueList.add(DAILY_REVENUE2);
        dailyRevenueList.setDailyRevenue(expectedDailyRevenueList);
        assertEquals(expectedDailyRevenueList, dailyRevenueList);
    }

    @Test
    public void setDailyRevenue_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        dailyRevenueList.setDailyRevenueList((List<DailyRevenue>) null);
    }

    @Test
    public void setDailyRevenue_list_replacesOwnListWithProvidedList() {
        dailyRevenueList.add(DAILY_REVENUE1);
        List<DailyRevenue> dailyRevenueList = Collections.singletonList(DAILY_REVENUE2);
        this.dailyRevenueList.setDailyRevenueList(dailyRevenueList);
        DailyRevenueList expectedDailyRevenueList = new DailyRevenueList();
        expectedDailyRevenueList.add(DAILY_REVENUE2);
        assertEquals(expectedDailyRevenueList, this.dailyRevenueList);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        dailyRevenueList.asUnmodifiableObservableList().remove(0);
    }
}
