package seedu.address.ui;

import static java.time.Duration.ofMillis;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPlaces.getTypicalPlaces;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysPlace;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardEquals;

import java.util.Collections;

import org.junit.Test;

import guitests.guihandles.PlaceCardHandle;
import guitests.guihandles.PlaceListPanelHandle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.place.Address;
import seedu.address.model.place.CountryCode;
import seedu.address.model.place.Description;
import seedu.address.model.place.Name;
import seedu.address.model.place.Place;
import seedu.address.model.place.Rating;

public class PlaceListPanelTest extends GuiUnitTest {
    private static final ObservableList<Place> TYPICAL_PERSONS =
            FXCollections.observableList(getTypicalPlaces());

    private static final long CARD_CREATION_AND_DELETION_TIMEOUT = 2500;

    private final SimpleObjectProperty<Place> selectedPlace = new SimpleObjectProperty<>();
    private PlaceListPanelHandle placeListPanelHandle;

    @Test
    public void display() {
        initUi(TYPICAL_PERSONS);

        for (int i = 0; i < TYPICAL_PERSONS.size(); i++) {
            placeListPanelHandle.navigateToCard(TYPICAL_PERSONS.get(i));
            Place expectedPlace = TYPICAL_PERSONS.get(i);
            PlaceCardHandle actualCard = placeListPanelHandle.getPlaceCardHandle(i);

            assertCardDisplaysPlace(expectedPlace, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }

    @Test
    public void selection_modelSelectedPlaceChanged_selectionChanges() {
        initUi(TYPICAL_PERSONS);
        Place secondPlace = TYPICAL_PERSONS.get(INDEX_SECOND_PERSON.getZeroBased());
        guiRobot.interact(() -> selectedPlace.set(secondPlace));
        guiRobot.pauseForHuman();

        PlaceCardHandle expectedPlace = placeListPanelHandle.getPlaceCardHandle(INDEX_SECOND_PERSON.getZeroBased());
        PlaceCardHandle selectedPlace = placeListPanelHandle.getHandleToSelectedCard();
        assertCardEquals(expectedPlace, selectedPlace);
    }

    /**
     * Verifies that creating and deleting large number of places in {@code PlaceListPanel} requires lesser than
     * {@code CARD_CREATION_AND_DELETION_TIMEOUT} milliseconds to execute.
     */
    @Test
    public void performanceTest() {
        ObservableList<Place> backingList = createBackingList(10000);

        assertTimeoutPreemptively(ofMillis(CARD_CREATION_AND_DELETION_TIMEOUT), () -> {
            initUi(backingList);
            guiRobot.interact(backingList::clear);
        }, "Creation and deletion of place cards exceeded time limit");
    }

    /**
     * Returns a list of places containing {@code placeCount} places that is used to populate the
     * {@code PlaceListPanel}.
     */
    private ObservableList<Place> createBackingList(int placeCount) {
        ObservableList<Place> backingList = FXCollections.observableArrayList();
        for (int i = 0; i < placeCount; i++) {
            Name name = new Name(i + "a");
            CountryCode countryCode = new CountryCode("SGP");
            Rating rating = new Rating("1");
            Description description = new Description("aaa");
            Address address = new Address("a");
            Place place = new Place(name, countryCode , rating, description, address, Collections.emptySet());
            backingList.add(place);
        }
        return backingList;
    }

    /**
     * Initializes {@code placeListPanelHandle} with a {@code PlaceListPanel} backed by {@code backingList}.
     * Also shows the {@code Stage} that displays only {@code PlaceListPanel}.
     */
    private void initUi(ObservableList<Place> backingList) {
        PlaceListPanel placeListPanel =
                new PlaceListPanel(backingList, selectedPlace, selectedPlace::set);
        uiPartRule.setUiPart(placeListPanel);

        placeListPanelHandle = new PlaceListPanelHandle(getChildNode(placeListPanel.getRoot(),
                PlaceListPanelHandle.PERSON_LIST_VIEW_ID));
    }
}
