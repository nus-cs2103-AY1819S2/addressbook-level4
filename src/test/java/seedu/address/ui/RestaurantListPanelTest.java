package seedu.address.ui;

import static java.time.Duration.ofMillis;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_RESTAURANT;
import static seedu.address.testutil.TypicalRestaurants.getTypicalRestaurants;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysRestaurant;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardEquals;

import java.util.Collections;

import org.junit.Test;

import guitests.guihandles.RestaurantCardHandle;
import guitests.guihandles.RestaurantListPanelHandle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.restaurant.Address;
import seedu.address.model.restaurant.Email;
import seedu.address.model.restaurant.Name;
import seedu.address.model.restaurant.OpeningHours;
import seedu.address.model.restaurant.Phone;
import seedu.address.model.restaurant.Restaurant;
import seedu.address.model.restaurant.Weblink;

public class RestaurantListPanelTest extends GuiUnitTest {
    private static final ObservableList<Restaurant> TYPICAL_RESTAURANTS =
            FXCollections.observableList(getTypicalRestaurants());

    private static final long CARD_CREATION_AND_DELETION_TIMEOUT = 1000;

    private final SimpleObjectProperty<Restaurant> selectedRestaurant = new SimpleObjectProperty<>();
    private RestaurantListPanelHandle restaurantListPanelHandle;

    @Test
    public void display() {
        initUi(TYPICAL_RESTAURANTS);

        for (int i = 0; i < TYPICAL_RESTAURANTS.size(); i++) {
            restaurantListPanelHandle.navigateToCard(TYPICAL_RESTAURANTS.get(i));
            Restaurant expectedRestaurant = TYPICAL_RESTAURANTS.get(i);
            RestaurantCardHandle actualCard = restaurantListPanelHandle.getRestaurantCardHandle(i);

            assertCardDisplaysRestaurant(expectedRestaurant, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }

    @Test
    public void selection_modelSelectedRestaurantChanged_selectionChanges() {
        initUi(TYPICAL_RESTAURANTS);
        Restaurant secondRestaurant = TYPICAL_RESTAURANTS.get(INDEX_SECOND_RESTAURANT.getZeroBased());
        guiRobot.interact(() -> selectedRestaurant.set(secondRestaurant));
        guiRobot.pauseForHuman();

        RestaurantCardHandle expectedRestaurant = restaurantListPanelHandle
                .getRestaurantCardHandle(INDEX_SECOND_RESTAURANT.getZeroBased());
        RestaurantCardHandle selectedRestaurant = restaurantListPanelHandle.getHandleToSelectedCard();
        assertCardEquals(expectedRestaurant, selectedRestaurant);
    }

    /**
     * Verifies that creating and deleting large number of restaurants in {@code RestaurantListPanel} requires lesser
     * than {@code CARD_CREATION_AND_DELETION_TIMEOUT} milliseconds to execute.
     */
    @Test
    public void performanceTest() {
        ObservableList<Restaurant> backingList = createBackingList(10000);

        assertTimeoutPreemptively(ofMillis(CARD_CREATION_AND_DELETION_TIMEOUT), () -> {
            initUi(backingList);
            guiRobot.interact(backingList::clear);
        }, "Creation and deletion of restaurant cards exceeded time limit");
    }

    /**
     * Returns a list of restaurants containing {@code restaurantCount} restaurants that is used to populate the
     * {@code RestaurantListPanel}.
     */
    private ObservableList<Restaurant> createBackingList(int restaurantCount) {
        ObservableList<Restaurant> backingList = FXCollections.observableArrayList();
        for (int i = 0; i < restaurantCount; i++) {
            Name name = new Name(i + "a");
            Phone phone = new Phone("000");
            Email email = new Email("a@aa");
            Address address = new Address("a");
            Weblink weblink = new Weblink("https://a.com");
            OpeningHours openingHours = new OpeningHours("24hrs");
            Restaurant restaurant = new Restaurant(name, phone, email, address, Collections.emptySet(),
                    weblink, openingHours);
            backingList.add(restaurant);
        }
        return backingList;
    }

    /**
     * Initializes {@code restaurantListPanelHandle} with a {@code RestaurantListPanel} backed by {@code backingList}.
     * Also shows the {@code Stage} that displays only {@code RestaurantListPanel}.
     */
    private void initUi(ObservableList<Restaurant> backingList) {
        RestaurantListPanel restaurantListPanel =
                new RestaurantListPanel(backingList, selectedRestaurant, selectedRestaurant::set);
        uiPartRule.setUiPart(restaurantListPanel);

        restaurantListPanelHandle = new RestaurantListPanelHandle(getChildNode(restaurantListPanel.getRoot(),
                RestaurantListPanelHandle.RESTAURANT_LIST_VIEW_ID));
    }
}
