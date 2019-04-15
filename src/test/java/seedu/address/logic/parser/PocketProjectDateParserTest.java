package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import static seedu.address.testutil.TypicalFlexibleDateInputs.END_MONTH;
import static seedu.address.testutil.TypicalFlexibleDateInputs.END_WEEK;
import static seedu.address.testutil.TypicalFlexibleDateInputs.INVALID_MISSING_DAY;
import static seedu.address.testutil.TypicalFlexibleDateInputs.INVALID_MISSING_KEYWORD;
import static seedu.address.testutil.TypicalFlexibleDateInputs.INVALID_MISSING_WEEK_MONTH;
import static seedu.address.testutil.TypicalFlexibleDateInputs.LAST_MONTH_END;
import static seedu.address.testutil.TypicalFlexibleDateInputs.LAST_MONTH_MID;
import static seedu.address.testutil.TypicalFlexibleDateInputs.LAST_MONTH_START;
import static seedu.address.testutil.TypicalFlexibleDateInputs.LAST_WEEK_MONDAY;
import static seedu.address.testutil.TypicalFlexibleDateInputs.LAST_WEEK_SUNDAY;
import static seedu.address.testutil.TypicalFlexibleDateInputs.LAST_WEEK_WEDNESDAY;
import static seedu.address.testutil.TypicalFlexibleDateInputs.MID_MONTH;
import static seedu.address.testutil.TypicalFlexibleDateInputs.MID_WEEK;
import static seedu.address.testutil.TypicalFlexibleDateInputs.NEXT;
import static seedu.address.testutil.TypicalFlexibleDateInputs.NEXT_MONTH_END;
import static seedu.address.testutil.TypicalFlexibleDateInputs.NEXT_MONTH_MID;
import static seedu.address.testutil.TypicalFlexibleDateInputs.NEXT_MONTH_START;
import static seedu.address.testutil.TypicalFlexibleDateInputs.NEXT_WEEK_MONDAY;
import static seedu.address.testutil.TypicalFlexibleDateInputs.NEXT_WEEK_SUNDAY;
import static seedu.address.testutil.TypicalFlexibleDateInputs.NEXT_WEEK_WEDNESDAY;
import static seedu.address.testutil.TypicalFlexibleDateInputs.START_MONTH;
import static seedu.address.testutil.TypicalFlexibleDateInputs.START_WEEK;
import static seedu.address.testutil.TypicalFlexibleDateInputs.THIS_MONTH_END;
import static seedu.address.testutil.TypicalFlexibleDateInputs.THIS_MONTH_MID;
import static seedu.address.testutil.TypicalFlexibleDateInputs.THIS_MONTH_START;
import static seedu.address.testutil.TypicalFlexibleDateInputs.THIS_WEEK_MONDAY;
import static seedu.address.testutil.TypicalFlexibleDateInputs.THIS_WEEK_SUNDAY;
import static seedu.address.testutil.TypicalFlexibleDateInputs.THIS_WEEK_WEDNESDAY;
import static seedu.address.testutil.TypicalFlexibleDateInputs.TODAY;
import static seedu.address.testutil.TypicalFlexibleDateInputs.TOMORROW;
import static seedu.address.testutil.TypicalFlexibleDateInputs.YESTERDAY;

import java.time.format.DateTimeFormatter;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.util.PocketProjectDate;

public class PocketProjectDateParserTest {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final PocketProjectDateParser parser = new PocketProjectDateParser();

    @Test
    public void parsePocketProjectDate_today() throws Exception {
        PocketProjectDate currentDate = new PocketProjectDate ();
        String userInput = parser.parsePocketProjectDate(TODAY);

        assertEquals(currentDate.currentDate(), userInput);
    }

    @Test
    public void parsePocketProjectDate_tomorrow() throws Exception {
        PocketProjectDate currentDate = new PocketProjectDate ();
        String userInput = parser.parsePocketProjectDate(TOMORROW);

        assertEquals(currentDate.dateNumDaysLater(NEXT), userInput);
    }

    @Test
    public void parsePocketProjectDate_yesterday() throws Exception {
        PocketProjectDate currentDate = new PocketProjectDate ();
        String userInput = parser.parsePocketProjectDate(YESTERDAY);

        assertEquals(currentDate.dateNumDaysBefore(NEXT), userInput);
    }

    @Test
    public void parsePocketProjectDate_thisWeekStart() throws Exception {
        PocketProjectDate currentDate = new PocketProjectDate ();
        String userInput = parser.parsePocketProjectDate(THIS_WEEK_MONDAY);

        assertEquals(currentDate.thisWeekDate(START_WEEK), userInput);
    }

    @Test
    public void parsePocketProjectDate_thisWeekMid() throws Exception {
        PocketProjectDate currentDate = new PocketProjectDate();
        String userInput = parser.parsePocketProjectDate(THIS_WEEK_WEDNESDAY);

        assertEquals(currentDate.thisWeekDate(MID_WEEK), userInput);
    }

    @Test
    public void parsePocketProjectDate_thisWeekEnd() throws Exception {
        PocketProjectDate currentDate = new PocketProjectDate();
        String userInput = parser.parsePocketProjectDate(THIS_WEEK_SUNDAY);

        assertEquals(currentDate.thisWeekDate(END_WEEK), userInput);
    }

    @Test
    public void parsePocketProjectDate_thisMonthStart() throws Exception {
        PocketProjectDate currentDate = new PocketProjectDate();
        String userInput = parser.parsePocketProjectDate(THIS_MONTH_START);

        assertEquals(currentDate.thisMonthDate(START_MONTH), userInput);
    }

    @Test
    public void parsePocketProjectDate_thisMonthMid() throws Exception {
        PocketProjectDate currentDate = new PocketProjectDate();
        String userInput = parser.parsePocketProjectDate(THIS_MONTH_MID);

        assertEquals(currentDate.thisMonthDate(MID_MONTH), userInput);
    }

    @Test
    public void parsePocketProjectDate_thisMonthEnd() throws Exception {
        PocketProjectDate currentDate = new PocketProjectDate();
        String userInput = parser.parsePocketProjectDate(THIS_MONTH_END);

        assertEquals(currentDate.thisMonthDate(END_MONTH), userInput);
    }

    @Test
    public void parsePocketProjectDate_nextWeekStart() throws Exception {
        PocketProjectDate currentDate = new PocketProjectDate();
        String userInput = parser.parsePocketProjectDate(NEXT_WEEK_MONDAY);

        assertEquals(currentDate.nextWeekDate(START_WEEK), userInput);
    }

    @Test
    public void parsePocketProjectDate_nextWeekMid() throws Exception {
        PocketProjectDate currentDate = new PocketProjectDate();
        String userInput = parser.parsePocketProjectDate(NEXT_WEEK_WEDNESDAY);

        assertEquals(currentDate.nextWeekDate(MID_WEEK), userInput);
    }

    @Test
    public void parsePocketProjectDate_nextWeekEnd() throws Exception {
        PocketProjectDate currentDate = new PocketProjectDate();
        String userInput = parser.parsePocketProjectDate(NEXT_WEEK_SUNDAY);

        assertEquals(currentDate.nextWeekDate(END_WEEK), userInput);
    }

    @Test
    public void parsePocketProjectDate_nextMonthStart() throws Exception {
        PocketProjectDate currentDate = new PocketProjectDate();
        String userInput = parser.parsePocketProjectDate(NEXT_MONTH_START);

        assertEquals(currentDate.nextMonthDate(START_MONTH), userInput);
    }

    @Test
    public void parsePocketProjectDate_nextMonthMid() throws Exception {
        PocketProjectDate currentDate = new PocketProjectDate();
        String userInput = parser.parsePocketProjectDate(NEXT_MONTH_MID);

        assertEquals(currentDate.nextMonthDate(MID_MONTH), userInput);
    }

    @Test
    public void parsePocketProjectDate_nextMonthEnd() throws Exception {
        PocketProjectDate currentDate = new PocketProjectDate();
        String userInput = parser.parsePocketProjectDate(NEXT_MONTH_END);

        assertEquals(currentDate.nextMonthDate(END_MONTH), userInput);
    }

    @Test
    public void parsePocketProjectDate_lastWeekStart() throws Exception {
        PocketProjectDate currentDate = new PocketProjectDate();
        String userInput = parser.parsePocketProjectDate(LAST_WEEK_MONDAY);

        assertEquals(currentDate.lastWeekDate(START_WEEK), userInput);
    }

    @Test
    public void parsePocketProjectDate_lastWeekMid() throws Exception {
        PocketProjectDate currentDate = new PocketProjectDate();
        String userInput = parser.parsePocketProjectDate(LAST_WEEK_WEDNESDAY);

        assertEquals(currentDate.lastWeekDate(MID_WEEK), userInput);
    }

    @Test
    public void parsePocketProjectDate_lastWeekEnd() throws Exception {
        PocketProjectDate currentDate = new PocketProjectDate();
        String userInput = parser.parsePocketProjectDate(LAST_WEEK_SUNDAY);

        assertEquals(currentDate.lastWeekDate(END_WEEK), userInput);
    }

    @Test
    public void parsePocketProjectDate_lastMonthStart() throws Exception {
        PocketProjectDate currentDate = new PocketProjectDate();
        String userInput = parser.parsePocketProjectDate(LAST_MONTH_START);

        assertEquals(currentDate.lastMonthDate(START_MONTH), userInput);
    }

    @Test
    public void parsePocketProjectDate_lastMonthMid() throws Exception {
        PocketProjectDate currentDate = new PocketProjectDate();
        String userInput = parser.parsePocketProjectDate(LAST_MONTH_MID);

        assertEquals(currentDate.lastMonthDate(MID_MONTH), userInput);
    }

    @Test
    public void parsePocketProjectDate_lastMonthEnd() throws Exception {
        PocketProjectDate currentDate = new PocketProjectDate();
        String userInput = parser.parsePocketProjectDate(LAST_MONTH_END);

        assertEquals(currentDate.lastMonthDate(END_MONTH), userInput);
    }

    @Test
    public void parsePocketProjectDate_keywordMissing_failure() throws Exception {

        String expectedMessage = String.format(PocketProjectDate.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, INVALID_MISSING_KEYWORD, expectedMessage);
    }

    @Test
    public void parsePocketProjectDate_weekMonthMissing_failure() throws Exception {

        String expectedMessage = String.format(PocketProjectDate.FLEXI_DATE_MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, INVALID_MISSING_WEEK_MONTH, expectedMessage);
    }

    @Test
    public void parsePocketProjectDate_targetDayMissing_failure() throws Exception {

        String expectedMessage = String.format(PocketProjectDate.DAY_OF_WEEK_MONTH_CONSTRAINTS);
        assertParseFailure(parser, INVALID_MISSING_DAY, expectedMessage);
    }
}
