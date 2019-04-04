package seedu.travel.model;

import static org.junit.Assert.assertEquals;
import static seedu.travel.testutil.TypicalCountryChart.ARGENTINA;
import static seedu.travel.testutil.TypicalRatingChart.ONE_STAR;
import static seedu.travel.testutil.TypicalYearChart.TWO_ZERO_ONE_ZERO;

import java.util.Collections;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleIntegerProperty;

public class ChartBookTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final ChartBook chartBook = new ChartBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), chartBook.getCountryList());
        assertEquals(Collections.emptyList(), chartBook.getRatingList());
        assertEquals(Collections.emptyList(), chartBook.getYearList());
    }

    @Test
    public void getCountryList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        chartBook.getCountryList().remove(0);
    }

    @Test
    public void getRatingList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        chartBook.getRatingList().remove(0);
    }

    @Test
    public void getYearList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        chartBook.getYearList().remove(0);
    }

    @Test
    public void addListenerCountry_withInvalidationListener_listenerAdded() {
        SimpleIntegerProperty counter = new SimpleIntegerProperty();
        InvalidationListener listener = observable -> counter.set(counter.get() + 1);
        chartBook.addListener(listener);
        chartBook.addCountryChart(ARGENTINA);
        assertEquals(1, counter.get());
    }

    @Test
    public void addListenerRating_withInvalidationListener_listenerAdded() {
        SimpleIntegerProperty counter = new SimpleIntegerProperty();
        InvalidationListener listener = observable -> counter.set(counter.get() + 1);
        chartBook.addListener(listener);
        chartBook.addRatingChart(ONE_STAR);
        assertEquals(1, counter.get());
    }

    @Test
    public void addListenerYear_withInvalidationListener_listenerAdded() {
        SimpleIntegerProperty counter = new SimpleIntegerProperty();
        InvalidationListener listener = observable -> counter.set(counter.get() + 1);
        chartBook.addListener(listener);
        chartBook.addYearChart(TWO_ZERO_ONE_ZERO);
        assertEquals(1, counter.get());
    }

    @Test
    public void removeListenerCountry_withInvalidationListener_listenerRemoved() {
        SimpleIntegerProperty counter = new SimpleIntegerProperty();
        InvalidationListener listener = observable -> counter.set(counter.get() + 1);
        chartBook.addListener(listener);
        chartBook.removeListener(listener);
        chartBook.addCountryChart(ARGENTINA);
        assertEquals(0, counter.get());
    }

    @Test
    public void removeListenerRating_withInvalidationListener_listenerRemoved() {
        SimpleIntegerProperty counter = new SimpleIntegerProperty();
        InvalidationListener listener = observable -> counter.set(counter.get() + 1);
        chartBook.addListener(listener);
        chartBook.removeListener(listener);
        chartBook.addRatingChart(ONE_STAR);
        assertEquals(0, counter.get());
    }

    @Test
    public void removeListenerYear_withInvalidationListener_listenerRemoved() {
        SimpleIntegerProperty counter = new SimpleIntegerProperty();
        InvalidationListener listener = observable -> counter.set(counter.get() + 1);
        chartBook.addListener(listener);
        chartBook.removeListener(listener);
        chartBook.addYearChart(TWO_ZERO_ONE_ZERO);
        assertEquals(0, counter.get());
    }
}
