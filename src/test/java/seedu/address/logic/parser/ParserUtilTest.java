package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;

import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_APPAREL;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.options.SortOption;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.apparel.ClothingType;
import seedu.address.model.apparel.Color;
import seedu.address.model.apparel.Name;
import seedu.address.testutil.Assert;

public class ParserUtilTest {
    private static final String INVALID_NAME = "Stripey Shirt!!!!!!";
    private static final String INVALID_COLOR = "Blackkkkk";
    private static final String INVALID_CLOTHING_TYPE = "Bottoms but not really bottoms";
    //private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = "Stripey Shirt";
    private static final String VALID_COLOR = "Black";
    private static final String VALID_CLOTHING_TYPE = "Bottom";
    //private static final String VALID_TAG_1 = "friend";
    //private static final String VALID_TAG_2 = "neighbour";

    private static final String VALID_SORT_OPTION = "name";
    private static final String INVALID_SORT_OPTION = VALID_SORT_OPTION + "lala";

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
        assertEquals(INDEX_FIRST_APPAREL, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_APPAREL, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseName_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseName((String) null));
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
    public void parseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(nameWithWhitespace));
    }

    @Test
    public void parseColor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseColor((String) null));
    }

    @Test
    public void parseColor_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseColor(INVALID_COLOR));
    }

    @Test
    public void parseColor_validValueWithoutWhitespace_returnsColor() throws Exception {
        Color expectedColor = new Color(VALID_COLOR);
        assertEquals(expectedColor, ParserUtil.parseColor(VALID_COLOR));
    }

    @Test
    public void parseColor_validValueWithWhitespace_returnsTrimmedColor() throws Exception {
        String phoneWithWhitespace = WHITESPACE + VALID_COLOR + WHITESPACE;
        Color expectedColor = new Color(VALID_COLOR);
        assertEquals(expectedColor, ParserUtil.parseColor(phoneWithWhitespace));
    }

    @Test
    public void parseClothingType_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseClothingType((String) null));
    }

    @Test
    public void parseClothingType_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseClothingType(INVALID_CLOTHING_TYPE));
    }

    @Test
    public void parseClothingType_validValueWithoutWhitespace_returnsClothingType() throws Exception {
        ClothingType expectedClothingType = new ClothingType(VALID_CLOTHING_TYPE);
        assertEquals(expectedClothingType, ParserUtil.parseClothingType(VALID_CLOTHING_TYPE));
    }

    @Test
    public void parseClothingType_validValueWithWhitespace_returnsTrimmedClothingType() throws Exception {
        String emailWithWhitespace = WHITESPACE + VALID_CLOTHING_TYPE + WHITESPACE;
        ClothingType expectedClothingType = new ClothingType(VALID_CLOTHING_TYPE);
        assertEquals(expectedClothingType, ParserUtil.parseClothingType(emailWithWhitespace));
    }

    @Test
    public void parseSort_validValue_returnsSortOption() throws Exception {
        SortOption expectedSortOption = SortOption.create(VALID_SORT_OPTION);
        assertEquals(expectedSortOption, ParserUtil.parseSortValue(VALID_SORT_OPTION));
    }

    @Test
    public void parseSort_invalidValue_throwParseException() throws Exception {
        /**
        String expectedFailureMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE);
        assertEquals(expectedFailureMessage, ParserUtil.parseSortValue(INVALID_SORT_OPTION));
         **/
    }

    /*@Test not dealing with tags anymore
    public void parseTag_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseTag(null);
    }

    @Test
    public void parseTag_invalidValue_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        ParserUtil.parseTag(INVALID_TAG);
    }

    @Test
    public void parseTag_validValueWithoutWhitespace_returnsTag() throws Exception {
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(VALID_TAG_1));
    }

    @Test
    public void parseTag_validValueWithWhitespace_returnsTrimmedTag() throws Exception {
        String tagWithWhitespace = WHITESPACE + VALID_TAG_1 + WHITESPACE;
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(tagWithWhitespace));
    }

    @Test
    public void parseTags_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseTags(null);
    }

    @Test
    public void parseTags_collectionWithInvalidTags_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, INVALID_TAG));
    }

    @Test
    public void parseTags_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseTags(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseTags_collectionWithValidTags_returnsTagSet() throws Exception {
        Set<Tag> actualTagSet = ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, VALID_TAG_2));
        Set<Tag> expectedTagSet = new HashSet<Tag>(Arrays.asList(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2)));

        assertEquals(expectedTagSet, actualTagSet);
    }*/
}
