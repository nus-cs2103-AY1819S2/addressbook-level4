package seedu.address.ui;

import static java.time.Duration.ofMillis;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_BUYER;
import static seedu.address.testutil.TypicalPersons.getTypicalPersons;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysPerson;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardEquals;

import org.junit.Test;

import guitests.guihandles.PersonCardHandle;
import guitests.guihandles.PinListPanelHandle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;

public class PinListPanelTest extends GuiUnitTest {
    private static final ObservableList<Person> TYPICAL_PERSONS =
            FXCollections.observableList(getTypicalPersons());

    private static final long CARD_CREATION_AND_DELETION_TIMEOUT = 2500;

    private final SimpleObjectProperty<Person> selectedPerson = new SimpleObjectProperty<>();
    private PinListPanelHandle pinListPanelHandle;

    @Test
    public void display() {
        initUi(TYPICAL_PERSONS);

        for (int i = 0; i < TYPICAL_PERSONS.size(); i++) {
            pinListPanelHandle.navigateToCard(TYPICAL_PERSONS.get(i));
            Person expectedPerson = TYPICAL_PERSONS.get(i);
            PersonCardHandle actualCard = pinListPanelHandle.getPersonCardHandle(i);

            assertCardDisplaysPerson(expectedPerson, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }

    @Test
    public void selection_modelSelectedPersonChanged_selectionChanges() {
        initUi(TYPICAL_PERSONS);
        Person secondPerson = TYPICAL_PERSONS.get(INDEX_SECOND_BUYER.getZeroBased());
        guiRobot.interact(() -> selectedPerson.set(secondPerson));
        guiRobot.pauseForHuman();

        PersonCardHandle expectedPerson = pinListPanelHandle.getPersonCardHandle(INDEX_SECOND_BUYER.getZeroBased());
        PersonCardHandle selectedPerson = pinListPanelHandle.getHandleToSelectedCard();
        assertCardEquals(expectedPerson, selectedPerson);
    }

    /**
     * Verifies that creating and deleting large number of persons in {@code PinListPanel} requires lesser than
     * {@code CARD_CREATION_AND_DELETION_TIMEOUT} milliseconds to execute.
     */
    @Test
    public void performanceTest() {
        ObservableList<Person> backingList = createBackingList(10000);

        assertTimeoutPreemptively(ofMillis(CARD_CREATION_AND_DELETION_TIMEOUT), () -> {
            initUi(backingList);
            guiRobot.interact(backingList::clear);
        }, "Creation and deletion of person cards exceeded time limit");
    }

    /**
     * Returns a list of persons containing {@code personCount} persons that is used to populate the
     * {@code PinListPanel}.
     */
    private ObservableList<Person> createBackingList(int personCount) {
        ObservableList<Person> backingList = FXCollections.observableArrayList();
        for (int i = 0; i < personCount; i++) {
            Name name = new Name(i + "a");
            Phone phone = new Phone("000");
            Email email = new Email("a@aa.com");
            Remark remark = new Remark("a");
            Person person = new Person(name, phone, email, remark);
            backingList.add(person);
        }
        return backingList;
    }

    /**
     * Initializes {@code pinListPanelHandle} with a {@code PinListPanel} backed by {@code backingList}.
     * Also shows the {@code Stage} that displays only {@code PinListPanel}.
     */
    private void initUi(ObservableList<Person> backingList) {
        PinListPanel pinListPanel =
                new PinListPanel(backingList, selectedPerson, selectedPerson::set);
        uiPartRule.setUiPart(pinListPanel);

        pinListPanelHandle = new PinListPanelHandle(getChildNode(pinListPanel.getRoot(),
                PinListPanelHandle.PIN_LIST_VIEW_ID));
    }
}
