package seedu.address.ui;

import static java.time.Duration.ofMillis;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalModuleTaken.getTypicalModulesTaken;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysPerson;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardEquals;

import java.util.Collections;

import org.junit.Test;

import guitests.guihandles.PersonCardHandle;
import guitests.guihandles.PersonListPanelHandle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.moduleinfo.ModuleInfoCode;
import seedu.address.model.moduletaken.Grade;
import seedu.address.model.moduletaken.Hour;
import seedu.address.model.moduletaken.ModuleTaken;
import seedu.address.model.moduletaken.Semester;

public class ModuleTakenListPanelTest extends GuiUnitTest {
    private static final ObservableList<ModuleTaken> TYPICAL_MODULE_TAKENS =
            FXCollections.observableList(getTypicalModulesTaken());

    private static final long CARD_CREATION_AND_DELETION_TIMEOUT = 2500;

    private final SimpleObjectProperty<ModuleTaken> selectedPerson = new SimpleObjectProperty<>();
    private PersonListPanelHandle personListPanelHandle;

    @Test
    public void display() {
        initUi(TYPICAL_MODULE_TAKENS);

        for (int i = 0; i < TYPICAL_MODULE_TAKENS.size(); i++) {
            personListPanelHandle.navigateToCard(TYPICAL_MODULE_TAKENS.get(i));
            ModuleTaken expectedModuleTaken = TYPICAL_MODULE_TAKENS.get(i);
            PersonCardHandle actualCard = personListPanelHandle.getPersonCardHandle(i);

            assertCardDisplaysPerson(expectedModuleTaken, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }

    @Test
    public void selection_modelSelectedPersonChanged_selectionChanges() {
        initUi(TYPICAL_MODULE_TAKENS);
        ModuleTaken secondModuleTaken = TYPICAL_MODULE_TAKENS.get(INDEX_SECOND_PERSON.getZeroBased());
        guiRobot.interact(() -> selectedPerson.set(secondModuleTaken));
        guiRobot.pauseForHuman();

        PersonCardHandle expectedPerson = personListPanelHandle.getPersonCardHandle(INDEX_SECOND_PERSON.getZeroBased());
        PersonCardHandle selectedPerson = personListPanelHandle.getHandleToSelectedCard();
        assertCardEquals(expectedPerson, selectedPerson);
    }

    /**
     * Verifies that creating and deleting large number of persons in {@code PersonListPanel} requires lesser than
     * {@code CARD_CREATION_AND_DELETION_TIMEOUT} milliseconds to execute.
     */
    @Test
    public void performanceTest() {
        ObservableList<ModuleTaken> backingList = createBackingList(8999);

        assertTimeoutPreemptively(ofMillis(CARD_CREATION_AND_DELETION_TIMEOUT), () -> {
            initUi(backingList);
            guiRobot.interact(backingList::clear);
        }, "Creation and deletion of moduleTaken cards exceeded time limit");
    }

    /**
     * Returns a list of persons containing {@code personCount} persons that is used to populate the
     * {@code PersonListPanel}.
     */
    private ObservableList<ModuleTaken> createBackingList(int personCount) {
        ObservableList<ModuleTaken> backingList = FXCollections.observableArrayList();
        for (int i = 1000; i < personCount; i++) {
            ModuleInfoCode moduleInfoCode = new ModuleInfoCode("CS" + i);
            Semester semester = Semester.valueOf("Y1S1");
            Grade expectedMinGrade = Grade.getGrade("D");
            Grade expectedMaxGrade = Grade.getGrade("A");
            Hour lectureHour = new Hour("3");
            ModuleTaken moduleTaken = new ModuleTaken(moduleInfoCode, semester,
                    expectedMinGrade, expectedMaxGrade, lectureHour, Collections.emptySet());
            backingList.add(moduleTaken);
        }
        return backingList;
    }

    /**
     * Initializes {@code personListPanelHandle} with a {@code PersonListPanel} backed by {@code backingList}.
     * Also shows the {@code Stage} that displays only {@code PersonListPanel}.
     */
    private void initUi(ObservableList<ModuleTaken> backingList) {
        PersonListPanel personListPanel =
                new PersonListPanel(backingList, selectedPerson, selectedPerson::set);
        uiPartRule.setUiPart(personListPanel);

        personListPanelHandle = new PersonListPanelHandle(getChildNode(personListPanel.getRoot(),
                PersonListPanelHandle.PERSON_LIST_VIEW_ID));
    }
}
