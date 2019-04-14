package seedu.address.ui;

import static java.time.Duration.ofMillis;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

import static seedu.address.testutil.TypicalApparels.getTypicalApparels;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_APPAREL;

import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysPerson;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardEquals;

import org.junit.Test;

import guitests.guihandles.PersonCardHandle;
import guitests.guihandles.PersonListPanelHandle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.apparel.Address;
import seedu.address.model.apparel.Apparel;
import seedu.address.model.apparel.ClothingType;
import seedu.address.model.apparel.Color;
import seedu.address.model.apparel.Name;

public class ApparelListPanelTest extends GuiUnitTest {
    private static final ObservableList<Apparel> TYPICAL_APPARELS =
            FXCollections.observableList(getTypicalApparels());

    private static final long CARD_CREATION_AND_DELETION_TIMEOUT = 2500;

    private final SimpleObjectProperty<Apparel> selectedPerson = new SimpleObjectProperty<>();
    private PersonListPanelHandle personListPanelHandle;

    @Test
    public void display() {
        initUi(TYPICAL_APPARELS);

        for (int i = 0; i < TYPICAL_APPARELS.size(); i++) {
            personListPanelHandle.navigateToCard(TYPICAL_APPARELS.get(i));
            Apparel expectedApparel = TYPICAL_APPARELS.get(i);
            PersonCardHandle actualCard = personListPanelHandle.getPersonCardHandle(i);

            assertCardDisplaysPerson(expectedApparel, actualCard);
            String x = actualCard.getId();
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }

    @Test
    public void selection_modelSelectedPersonChanged_selectionChanges() {
        initUi(TYPICAL_APPARELS);
        Apparel secondApparel = TYPICAL_APPARELS.get(INDEX_SECOND_APPAREL.getZeroBased());
        guiRobot.interact(() -> selectedPerson.set(secondApparel));
        guiRobot.pauseForHuman();

        PersonCardHandle expectedPerson = personListPanelHandle
                .getPersonCardHandle(INDEX_SECOND_APPAREL.getZeroBased());
        PersonCardHandle selectedPerson = personListPanelHandle.getHandleToSelectedCard();
        assertCardEquals(expectedPerson, selectedPerson);
    }

    /**
     * Verifies that creating and deleting large number of persons in {@code PersonListPanel} requires lesser than
     * {@code CARD_CREATION_AND_DELETION_TIMEOUT} milliseconds to execute.
     */
    @Test
    public void performanceTest() {
        ObservableList<Apparel> backingList = createBackingList(10000);

        assertTimeoutPreemptively(ofMillis(CARD_CREATION_AND_DELETION_TIMEOUT), () -> {
            initUi(backingList);
            guiRobot.interact(backingList::clear);
        }, "Creation and deletion of apparel cards exceeded time limit");
    }

    /**
     * Returns a list of persons containing {@code personCount} persons that is used to populate the
     * {@code PersonListPanel}.
     */
    private ObservableList<Apparel> createBackingList(int personCount) {
        ObservableList<Apparel> backingList = FXCollections.observableArrayList();
        for (int i = 0; i < personCount; i++) {
            Name name = new Name(i + "a");
            Color color = new Color("BLUE");
            ClothingType clothingType = new ClothingType("TOP");
            Address address = new Address("a");
            Apparel apparel = new Apparel(name, color, clothingType);
            backingList.add(apparel);
        }
        return backingList;
    }

    /**
     * Initializes {@code personListPanelHandle} with a {@code PersonListPanel} backed by {@code backingList}.
     * Also shows the {@code Stage} that displays only {@code PersonListPanel}.
     */
    private void initUi(ObservableList<Apparel> backingList) {
        PersonListPanel personListPanel =
                new PersonListPanel(backingList, selectedPerson, selectedPerson::set);
        uiPartRule.setUiPart(personListPanel);

        personListPanelHandle = new PersonListPanelHandle(getChildNode(personListPanel.getRoot(),
                PersonListPanelHandle.PERSON_LIST_VIEW_ID));
    }
}
