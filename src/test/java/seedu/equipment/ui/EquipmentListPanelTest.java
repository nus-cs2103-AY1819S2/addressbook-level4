package seedu.equipment.ui;

import static java.time.Duration.ofMillis;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static seedu.equipment.testutil.TypicalEquipments.getTypicalPersons;
import static seedu.equipment.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.equipment.ui.testutil.GuiTestAssert.assertCardDisplaysPerson;
import static seedu.equipment.ui.testutil.GuiTestAssert.assertCardEquals;

import java.util.Collections;

import org.junit.Test;

import guitests.guihandles.EquipmentCardHandle;
import guitests.guihandles.EquipmentListPanelHandle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.equipment.model.equipment.Address;
import seedu.equipment.model.equipment.Email;
import seedu.equipment.model.equipment.Equipment;
import seedu.equipment.model.equipment.Name;
import seedu.equipment.model.equipment.Phone;
import seedu.equipment.model.equipment.SerialNumber;
import seedu.equipment.ui.EquipmentListPanel;

public class EquipmentListPanelTest extends GuiUnitTest {
    private static final ObservableList<Equipment> TYPICAL_EQUIPMENTS =
            FXCollections.observableList(getTypicalPersons());

    private static final long CARD_CREATION_AND_DELETION_TIMEOUT = 2500;

    private final SimpleObjectProperty<Equipment> selectedPerson = new SimpleObjectProperty<>();
    private EquipmentListPanelHandle equipmentListPanelHandle;

    @Test
    public void display() {
        initUi(TYPICAL_EQUIPMENTS);

        for (int i = 0; i < TYPICAL_EQUIPMENTS.size(); i++) {
            equipmentListPanelHandle.navigateToCard(TYPICAL_EQUIPMENTS.get(i));
            Equipment expectedEquipment = TYPICAL_EQUIPMENTS.get(i);
            EquipmentCardHandle actualCard = equipmentListPanelHandle.getPersonCardHandle(i);

            assertCardDisplaysPerson(expectedEquipment, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }

    @Test
    public void selection_modelSelectedPersonChanged_selectionChanges() {
        initUi(TYPICAL_EQUIPMENTS);
        Equipment secondEquipment = TYPICAL_EQUIPMENTS.get(INDEX_SECOND_PERSON.getZeroBased());
        guiRobot.interact(() -> selectedPerson.set(secondEquipment));
        guiRobot.pauseForHuman();

        EquipmentCardHandle expectedPerson = equipmentListPanelHandle.getPersonCardHandle(
                INDEX_SECOND_PERSON.getZeroBased());
        EquipmentCardHandle selectedPerson = equipmentListPanelHandle.getHandleToSelectedCard();
        assertCardEquals(expectedPerson, selectedPerson);
    }

    /**
     * Verifies that creating and deleting large number of persons in {@code EquipmentListPanel} requires lesser than
     * {@code CARD_CREATION_AND_DELETION_TIMEOUT} milliseconds to execute.
     */
    @Test
    public void performanceTest() {
        ObservableList<Equipment> backingList = createBackingList(10000);

        assertTimeoutPreemptively(ofMillis(CARD_CREATION_AND_DELETION_TIMEOUT), () -> {
            initUi(backingList);
            guiRobot.interact(backingList::clear);
        }, "Creation and deletion of equipment cards exceeded time limit");
    }

    /**
     * Returns a list of persons containing {@code personCount} persons that is used to populate the
     * {@code EquipmentListPanel}.
     */
    private ObservableList<Equipment> createBackingList(int personCount) {
        ObservableList<Equipment> backingList = FXCollections.observableArrayList();
        for (int i = 0; i < personCount; i++) {
            Name name = new Name(i + "a");
            Phone phone = new Phone("000");
            Email email = new Email("a@aa");
            Address address = new Address("a");
            SerialNumber serialNumber = new SerialNumber("aaaaaa");
            Equipment equipment = new Equipment(name, phone, email, address, serialNumber, Collections.emptySet());
            backingList.add(equipment);
        }
        return backingList;
    }

    /**
     * Initializes {@code equipmentListPanelHandle} with a {@code EquipmentListPanel} backed by {@code backingList}.
     * Also shows the {@code Stage} that displays only {@code EquipmentListPanel}.
     */
    private void initUi(ObservableList<Equipment> backingList) {
        EquipmentListPanel equipmentListPanel =
                new EquipmentListPanel(backingList, selectedPerson, selectedPerson::set);
        uiPartRule.setUiPart(equipmentListPanel);

        equipmentListPanelHandle = new EquipmentListPanelHandle(getChildNode(equipmentListPanel.getRoot(),
                EquipmentListPanelHandle.PERSON_LIST_VIEW_ID));
    }
}
