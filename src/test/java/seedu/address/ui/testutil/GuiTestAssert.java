package seedu.address.ui.testutil;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import guitests.guihandles.PersonCardHandle;
import guitests.guihandles.PersonListPanelHandle;
import guitests.guihandles.ResultDisplayHandle;
import seedu.address.model.moduletaken.ModuleTaken;

/**
 * A set of assertion methods useful for writing GUI tests.
 */
public class GuiTestAssert {
    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertCardEquals(PersonCardHandle expectedCard, PersonCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getExpectedMaxGrade(), actualCard.getExpectedMaxGrade());
        assertEquals(expectedCard.getExpectedMinGrade(), actualCard.getExpectedMinGrade());
        assertEquals(expectedCard.getModuleInfoCode(), actualCard.getModuleInfoCode());
        assertEquals(expectedCard.getSemester(), actualCard.getSemester());
        assertEquals(expectedCard.getTags(), actualCard.getTags());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedModuleTaken}.
     */
    public static void assertCardDisplaysPerson(ModuleTaken expectedModuleTaken, PersonCardHandle actualCard) {
        assertEquals(expectedModuleTaken.getModuleInfo().toString(), actualCard.getModuleInfoCode());
        assertEquals(expectedModuleTaken.getSemester().toString(), actualCard.getSemester());
        assertEquals(expectedModuleTaken.getExpectedMinGrade().toString(), actualCard.getExpectedMinGrade());
        assertEquals(expectedModuleTaken.getExpectedMaxGrade().toString(), actualCard.getExpectedMaxGrade());
        assertEquals(expectedModuleTaken.getTags().stream().map(tag -> tag.tagName).collect(Collectors.toList()),
                actualCard.getTags());
    }

    /**
     * Asserts that the list in {@code personListPanelHandle} displays the details of {@code moduleTakens} correctly and
     * in the correct order.
     */
    public static void assertListMatching(PersonListPanelHandle personListPanelHandle, ModuleTaken... moduleTakens) {
        for (int i = 0; i < moduleTakens.length; i++) {
            personListPanelHandle.navigateToCard(i);
            assertCardDisplaysPerson(moduleTakens[i], personListPanelHandle.getPersonCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code personListPanelHandle} displays the details of {@code moduleTakens} correctly and
     * in the correct order.
     */
    public static void assertListMatching(PersonListPanelHandle personListPanelHandle, List<ModuleTaken> moduleTakens) {
        assertListMatching(personListPanelHandle, moduleTakens.toArray(new ModuleTaken[0]));
    }

    /**
     * Asserts the size of the list in {@code personListPanelHandle} equals to {@code size}.
     */
    public static void assertListSize(PersonListPanelHandle personListPanelHandle, int size) {
        int numberOfPeople = personListPanelHandle.getListSize();
        assertEquals(size, numberOfPeople);
    }

    /**
     * Asserts the message shown in {@code resultDisplayHandle} equals to {@code expected}.
     */
    public static void assertResultMessage(ResultDisplayHandle resultDisplayHandle, String expected) {
        assertEquals(expected, resultDisplayHandle.getText());
    }
}
