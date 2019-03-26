package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ITEM;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.menu.Code;
import seedu.address.model.menu.Name;
import seedu.address.model.menu.Price;
import seedu.address.model.statistics.Day;
import seedu.address.model.statistics.Month;
import seedu.address.model.statistics.Year;
import seedu.address.model.table.TableNumber;
import seedu.address.testutil.Assert;

public class ParserUtilTest {

    private static final String INVALID_NAME = " @3$";
    private static final String INVALID_CODE = "09W";
    private static final String INVALID_PRICE = "1.3a";
    private static final String INVALID_DAY = "35";
    private static final String INVALID_MONTH = "13";
    private static final String INVALID_YEAR = "2103";
    private static final String INVALID_TABLE_NUMBER = "abc";

    private static final String VALID_NAME = "McSpicy Burger";
    private static final String VALID_CODE = "A11";
    private static final String VALID_PRICE = "4.70";
    private static final String VALID_DAY = "29";
    private static final String VALID_MONTH = "10";
    private static final String VALID_YEAR = "2019";
    private static final String VALID_TABLE_NUMBER = "4";

    private static final String WHITESPACE = " \t\r\n";

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void parseIndex_invalidInput_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        ParserUtil.parseIndex("10 a");
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_INVALID_INDEX);
        ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST_ITEM, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_ITEM, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseName_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseName(null));
    }

    @Test
    public void parseName_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseName(INVALID_NAME));
    }

    @Test
    public void parseName_validValueWithoutWhitespace_returnsName() throws Exception {
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(VALID_NAME));
    }

    @Test
    public void parseCode_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseCode(null));
    }

    @Test
    public void parseCode_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseCode(INVALID_CODE));
    }

    @Test
    public void parseCode_validValueWithoutWhiteSpace_returnsCode() throws Exception {
        Code expectedCode = new Code(VALID_CODE);
        assertEquals(expectedCode, ParserUtil.parseCode(VALID_CODE));
    }

    @Test
    public void parseCode_validValueWithWhitespace_returnsTrimmedCode() throws Exception {
        String codeWithWhitespace = WHITESPACE + VALID_CODE + WHITESPACE;
        Code expectedCode = new Code(VALID_CODE);
        assertEquals(expectedCode, ParserUtil.parseCode(codeWithWhitespace));
    }

    @Test
    public void parsePrice_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseCode(null));
    }

    @Test
    public void parsePrice_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseCode(INVALID_PRICE));
    }

    @Test
    public void parsePrice_validValueWithoutWhiteSpace_returnsCode() throws Exception {
        Price expectedPrice = new Price(VALID_PRICE);
        assertEquals(expectedPrice, ParserUtil.parsePrice(VALID_PRICE));
    }

    @Test
    public void parsePrice_validValueWithWhitespace_returnsTrimmedCode() throws Exception {
        String priceWithWhitespace = WHITESPACE + VALID_PRICE + WHITESPACE;
        Price expectedPrice = new Price(VALID_PRICE);
        assertEquals(expectedPrice, ParserUtil.parsePrice(priceWithWhitespace));
    }

    @Test
    public void parseDay_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseCode(null));
    }

    @Test
    public void parseDay_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseCode(INVALID_DAY));
    }

    @Test
    public void parseDay_validValueWithoutWhiteSpace_returnsCode() throws Exception {
        Day expectedDay = new Day(VALID_DAY);
        assertEquals(expectedDay, ParserUtil.parseDay(VALID_DAY));
    }

    @Test
    public void parseDay_validValueWithWhitespace_returnsTrimmedCode() throws Exception {
        String dayWithWhitespace = WHITESPACE + VALID_DAY + WHITESPACE;
        Day expectedDay = new Day(VALID_DAY);
        assertEquals(expectedDay, ParserUtil.parseDay(dayWithWhitespace));
    }

    @Test
    public void parseMonth_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseCode(null));
    }

    @Test
    public void parseMonth_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseCode(INVALID_MONTH));
    }

    @Test
    public void parseMonth_validValueWithoutWhiteSpace_returnsCode() throws Exception {
        Month expectedMonth = new Month(VALID_MONTH);
        assertEquals(expectedMonth, ParserUtil.parseMonth(VALID_MONTH));
    }

    @Test
    public void parseMonth_validValueWithWhitespace_returnsTrimmedCode() throws Exception {
        String monthWithWhitespace = WHITESPACE + VALID_MONTH + WHITESPACE;
        Month expectedMonth = new Month(VALID_MONTH);
        assertEquals(expectedMonth, ParserUtil.parseMonth(monthWithWhitespace));
    }

    @Test
    public void parseYear_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseCode(null));
    }

    @Test
    public void parseYear_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseCode(INVALID_YEAR));
    }

    @Test
    public void parseYear_validValueWithoutWhiteSpace_returnsCode() throws Exception {
        Year expectedYear = new Year(VALID_YEAR);
        assertEquals(expectedYear, ParserUtil.parseYear(VALID_YEAR));
    }

    @Test
    public void parseYear_validValueWithWhitespace_returnsTrimmedCode() throws Exception {
        String monthWithWhitespace = WHITESPACE + VALID_YEAR + WHITESPACE;
        Year expectedYear = new Year(VALID_YEAR);
        assertEquals(expectedYear, ParserUtil.parseYear(monthWithWhitespace));
    }

    @Test
    public void parseTableNumber_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseCode(null));
    }

    @Test
    public void parseTableNumber_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseCode(INVALID_TABLE_NUMBER));
    }

    @Test
    public void parseTableNumber_validValueWithoutWhiteSpace_returnsCode() throws Exception {
        TableNumber expectedTableNumber = new TableNumber(VALID_TABLE_NUMBER);
        assertEquals(expectedTableNumber, ParserUtil.parseTableNumber(VALID_TABLE_NUMBER));
    }

    @Test
    public void parseTableNumber_validValueWithWhitespace_returnsTrimmedCode() throws Exception {
        String tableNumberWithWhitespace = WHITESPACE + VALID_TABLE_NUMBER + WHITESPACE;
        TableNumber expectedTableNumber = new TableNumber(VALID_TABLE_NUMBER);
        assertEquals(expectedTableNumber, ParserUtil.parseTableNumber(tableNumberWithWhitespace));
    }
}
