package seedu.finance.ui.testutil;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import guitests.guihandles.RecordCardHandle;
import guitests.guihandles.RecordListPanelHandle;
import guitests.guihandles.ResultDisplayHandle;
import seedu.finance.model.record.Record;


/**
 * A set of assertion methods useful for writing GUI tests.
 */
public class GuiTestAssert {
    private static final String LABEL_DEFAULT_STYLE = "label";

    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertCardEquals(RecordCardHandle expectedCard, RecordCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getAmount(), actualCard.getAmount());
        assertEquals(expectedCard.getDate(), actualCard.getDate());
        assertEquals(expectedCard.getName(), actualCard.getName());
        assertEquals(expectedCard.getCategories(), actualCard.getCategories());

        expectedCard.getCategories().forEach(category -> assertEquals(expectedCard.getCategoryStyleClasses(category),
                actualCard.getCategoryStyleClasses(category)));
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedRecord}.
     */
    public static void assertCardDisplaysRecord(Record expectedRecord, RecordCardHandle actualCard) {
        assertEquals(expectedRecord.getName().fullName, actualCard.getName());
        assertEquals(expectedRecord.getAmount().value, actualCard.getAmount());
        assertEquals(expectedRecord.getDate().value, actualCard.getDate());
        assertEquals(expectedRecord.getDescription().value, actualCard.getDescription());
        assertCategoriesEqual(expectedRecord, actualCard);
    }

    /**
     * Returns the color style for {@code categoryName}'s label.
     * The category's color is determined by looking up the color in {@code RecordCard#CATEGORY_COLOR_STYLES},
     * using an index generated by the hash code of the category's content.
     *
     *
     */
    //@@author geezlouisee-reused
    //Reused from https://github.com/se-edu/addressbook-level4/pull/798/commits/1ac2e7c5597cf328cc9c28d5d8e18db8dc1fc5a0
    // with minor modifications
    private static String getCategoryColorStyleFor(String categoryName) {
        switch (categoryName.toLowerCase()) {
        case "owesmoney":
            return "yellow";

        case "transportation":
            return "blue";

        case "dining":
            return "teal";

        case "family":
            return "black";

        case "vices":
        case "food":
            return "orange";

        case "entertainment":
        case "colleagues":
            return "purple";

        case "classmates":
            return "brown";

        case"neighbours":
            return "green";

        case"friend":
            return "grey";

        case"friends":
            return "pink";

        case "shopping":
        case"husband":
            return "red";

        default:
            throw new AssertionError(categoryName + " does not have a color assigned.");
        }
    }
    //@@author

    /**
     * Asserts that the categories in {@code actualCard} matches all the categories in {@code expectedRecord}
     * with the correct color.
     */
    private static void assertCategoriesEqual(Record expectedRecord, RecordCardHandle actualCard) {
        List<String> expectedCategories = expectedRecord.getCategories().stream()
                .map(category -> category.categoryName).collect(Collectors.toList());
        assertEquals(expectedCategories, actualCard.getCategories());
        expectedCategories.forEach(category ->
                assertEquals(Arrays.asList(LABEL_DEFAULT_STYLE, getCategoryColorStyleFor(category)),
                        actualCard.getCategoryStyleClasses(category)));
    }

    /**
     * Asserts that the list in {@code recordListPanelHandle} displays the details of {@code records} correctly and
     * in the correct order.
     */
    public static void assertListMatching(RecordListPanelHandle recordListPanelHandle, Record... records) {
        for (int i = 0; i < records.length; i++) {
            recordListPanelHandle.navigateToCard(i);
            assertCardDisplaysRecord(records[i], recordListPanelHandle.getRecordCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code recordListPanelHandle} displays the details of {@code records} correctly and
     * in the correct order.
     */
    public static void assertListMatching(RecordListPanelHandle recordListPanelHandle, List<Record> records) {
        assertListMatching(recordListPanelHandle, records.toArray(new Record[0]));
    }

    /**
     * Asserts the size of the list in {@code recordListPanelHandle} equals to {@code size}.
     */
    public static void assertListSize(RecordListPanelHandle recordListPanelHandle, int size) {
        int numberOfPeople = recordListPanelHandle.getListSize();
        assertEquals(size, numberOfPeople);
    }

    /**
     * Asserts the message shown in {@code resultDisplayHandle} equals to {@code expected}.
     */
    public static void assertResultMessage(ResultDisplayHandle resultDisplayHandle, String expected) {
        assertEquals(expected, resultDisplayHandle.getText());
    }
}
