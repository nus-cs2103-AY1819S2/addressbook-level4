package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.TypicalRestaurants.ALICE;
import static seedu.address.testutil.TypicalRestaurants.getTypicalFoodDiary;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.restaurant.Restaurant;
import seedu.address.model.restaurant.exceptions.DuplicateRestaurantException;
import seedu.address.testutil.RestaurantBuilder;

public class FoodDiaryTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final FoodDiary foodDiary = new FoodDiary();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), foodDiary.getRestaurantList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        foodDiary.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyFoodDiary_replacesData() {
        FoodDiary newData = getTypicalFoodDiary();
        foodDiary.resetData(newData);
        assertEquals(newData, foodDiary);
    }

    @Test
    public void resetData_withDuplicateRestaurants_throwsDuplicateRestaurantException() {
        // Two restaurants with the same identity fields
        Restaurant editedAlice = new RestaurantBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Restaurant> newRestaurants = Arrays.asList(ALICE, editedAlice);
        FoodDiaryStub newData = new FoodDiaryStub(newRestaurants);

        thrown.expect(DuplicateRestaurantException.class);
        foodDiary.resetData(newData);
    }

    @Test
    public void hasRestaurant_nullRestaurant_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        foodDiary.hasRestaurant(null);
    }

    @Test
    public void hasRestaurant_restaurantNotInFoodDiary_returnsFalse() {
        assertFalse(foodDiary.hasRestaurant(ALICE));
    }

    @Test
    public void hasRestaurant_restaurantInFoodDiary_returnsTrue() {
        foodDiary.addRestaurant(ALICE);
        assertTrue(foodDiary.hasRestaurant(ALICE));
    }

    @Test
    public void hasRestaurant_restaurantWithSameIdentityFieldsInFoodDiary_returnsTrue() {
        foodDiary.addRestaurant(ALICE);
        Restaurant editedAlice = new RestaurantBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(foodDiary.hasRestaurant(editedAlice));
    }

    @Test
    public void getRestaurantList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        foodDiary.getRestaurantList().remove(0);
    }

    @Test
    public void addListener_withInvalidationListener_listenerAdded() {
        SimpleIntegerProperty counter = new SimpleIntegerProperty();
        InvalidationListener listener = observable -> counter.set(counter.get() + 1);
        foodDiary.addListener(listener);
        foodDiary.addRestaurant(ALICE);
        assertEquals(1, counter.get());
    }

    @Test
    public void removeListener_withInvalidationListener_listenerRemoved() {
        SimpleIntegerProperty counter = new SimpleIntegerProperty();
        InvalidationListener listener = observable -> counter.set(counter.get() + 1);
        foodDiary.addListener(listener);
        foodDiary.removeListener(listener);
        foodDiary.addRestaurant(ALICE);
        assertEquals(0, counter.get());
    }

    /**
     * A stub ReadOnlyFoodDiary whose restaurants list can violate interface constraints.
     */
    private static class FoodDiaryStub implements ReadOnlyFoodDiary {
        private final ObservableList<Restaurant> restaurants = FXCollections.observableArrayList();

        FoodDiaryStub(Collection<Restaurant> restaurants) {
            this.restaurants.setAll(restaurants);
        }

        @Override
        public ObservableList<Restaurant> getRestaurantList() {
            return restaurants;
        }

        @Override
        public void addListener(InvalidationListener listener) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void removeListener(InvalidationListener listener) {
            throw new AssertionError("This method should not be called.");
        }
    }

}
