package seedu.travel.commons.util;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.util.Optional;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.travel.model.tag.Tag;

public class StringUtilTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    //---------------- Tests for isUnsignedPositiveInteger --------------------------------------

    @Test
    public void isUnsignedPositiveInteger() {

        // EP: empty strings
        assertFalse(StringUtil.isNonZeroUnsignedInteger("")); // Boundary value
        assertFalse(StringUtil.isNonZeroUnsignedInteger("  "));

        // EP: not a number
        assertFalse(StringUtil.isNonZeroUnsignedInteger("a"));
        assertFalse(StringUtil.isNonZeroUnsignedInteger("aaa"));

        // EP: zero
        assertFalse(StringUtil.isNonZeroUnsignedInteger("0"));

        // EP: zero as prefix
        assertTrue(StringUtil.isNonZeroUnsignedInteger("01"));

        // EP: signed numbers
        assertFalse(StringUtil.isNonZeroUnsignedInteger("-1"));
        assertFalse(StringUtil.isNonZeroUnsignedInteger("+1"));

        // EP: numbers with white space
        assertFalse(StringUtil.isNonZeroUnsignedInteger(" 10 ")); // Leading/trailing spaces
        assertFalse(StringUtil.isNonZeroUnsignedInteger("1 0")); // Spaces in the middle

        // EP: number larger than Integer.MAX_VALUE
        assertFalse(StringUtil.isNonZeroUnsignedInteger(Long.toString(Integer.MAX_VALUE + 1)));

        // EP: valid numbers, should return true
        assertTrue(StringUtil.isNonZeroUnsignedInteger("1")); // Boundary value
        assertTrue(StringUtil.isNonZeroUnsignedInteger("10"));
    }

    //---------------- Tests for containsWordIgnoreCase --------------------------------------

    /*
     * Invalid equivalence partitions for word: null, empty, multiple words
     * Invalid equivalence partitions for sentence: null
     * The four test cases below test one invalid input at a time.
     */

    @Test
    public void containsWordIgnoreCase_nullWord_throwsNullPointerException() {
        assertExceptionThrown(NullPointerException.class, "typical sentence", null, Optional.empty());
    }

    private void assertExceptionThrown(Class<? extends Throwable> exceptionClass, String sentence, String word,
            Optional<String> errorMessage) {
        thrown.expect(exceptionClass);
        errorMessage.ifPresent(message -> thrown.expectMessage(message));
        StringUtil.containsWordIgnoreCase(sentence, word);
    }

    @Test
    public void containsWordIgnoreCase_emptyWord_throwsIllegalArgumentException() {
        assertExceptionThrown(IllegalArgumentException.class, "typical sentence", "  ",
                Optional.of("Word parameter cannot be empty"));
    }

    @Test
    public void containsWordIgnoreCase_multipleWords_throwsIllegalArgumentException() {
        assertExceptionThrown(IllegalArgumentException.class, "typical sentence", "aaa BBB",
                Optional.of("Word parameter should be a single word"));
    }

    @Test
    public void containsWordIgnoreCase_nullSentence_throwsNullPointerException() {
        assertExceptionThrown(NullPointerException.class, null, "abc", Optional.empty());
    }

    /*
     * Valid equivalence partitions for word:
     *   - any word
     *   - word containing symbols/numbers
     *   - word with leading/trailing spaces
     *
     * Valid equivalence partitions for sentence:
     *   - empty string
     *   - one word
     *   - multiple words
     *   - sentence with extra spaces
     *
     * Possible scenarios returning true:
     *   - matches first word in sentence
     *   - last word in sentence
     *   - middle word in sentence
     *   - matches multiple words
     *
     * Possible scenarios returning false:
     *   - query word matches part of a sentence word
     *   - sentence word matches part of the query word
     *
     * The test method below tries to verify all above with a reasonably low number of test cases.
     */

    @Test
    public void containsWordIgnoreCase_validInputs_correctResult() {

        // Empty sentence
        assertFalse(StringUtil.containsWordIgnoreCase("", "abc")); // Boundary case
        assertFalse(StringUtil.containsWordIgnoreCase("    ", "123"));

        // Matches a partial word only
        assertFalse(StringUtil.containsWordIgnoreCase("aaa bbb ccc", "bb")); // Sentence word bigger than query word
        assertFalse(StringUtil.containsWordIgnoreCase("aaa bbb ccc", "bbbb")); // Query word bigger than sentence word

        // Matches word in the sentence, different upper/lower case letters
        assertTrue(StringUtil.containsWordIgnoreCase("aaa bBb ccc", "Bbb")); // First word (boundary case)
        assertTrue(StringUtil.containsWordIgnoreCase("aaa bBb ccc@1", "CCc@1")); // Last word (boundary case)
        assertTrue(StringUtil.containsWordIgnoreCase("  AAA   bBb   ccc  ", "aaa")); // Sentence has extra spaces
        assertTrue(StringUtil.containsWordIgnoreCase("Aaa", "aaa")); // Only one word in sentence (boundary case)
        assertTrue(StringUtil.containsWordIgnoreCase("aaa bbb ccc", "  ccc  ")); // Leading/trailing spaces

        // Matches multiple words in sentence
        assertTrue(StringUtil.containsWordIgnoreCase("AAA bBb ccc  bbb", "bbB"));
    }

    //---------------- Tests for containsTagIgnoreCase --------------------------------------

    /*
     * Invalid equivalence partitions for tag: null, empty, multiple tags
     * Invalid equivalence partitions for tagsList: null
     * The four test cases below test one invalid input at a time.
     */

    @Test
    public void containsTagIgnoreCase_nullWord_throwsNullPointerException() {
        assertExceptionForTagsThrown(NullPointerException.class,
                Set.of(new Tag("school"), new Tag("temple")), null, Optional.empty());
    }

    private void assertExceptionForTagsThrown(Class<? extends Throwable> exceptionClass, Set<Tag> tagsList, String tag,
                                              Optional<String> errorMessage) {
        thrown.expect(exceptionClass);
        errorMessage.ifPresent(message -> thrown.expectMessage(message));
        StringUtil.containsTagIgnoreCase(tagsList, tag);
    }

    @Test
    public void containsTagIgnoreCase_emptyWord_throwsIllegalArgumentException() {
        assertExceptionForTagsThrown(IllegalArgumentException.class,
                Set.of(new Tag("school"), new Tag("temple")),
                "  ", Optional.of("Tag parameter cannot be empty"));
    }

    @Test
    public void containsTagIgnoreCase_multipleWords_throwsIllegalArgumentException() {
        assertExceptionForTagsThrown(IllegalArgumentException.class,
                Set.of(new Tag("school"), new Tag("temple")), "aaa BBB",
                Optional.of("Tag parameter should be a single word"));
    }

    @Test
    public void containsTagIgnoreCase_nullSentence_throwsNullPointerException() {
        assertExceptionForTagsThrown(NullPointerException.class, null, "abc", Optional.empty());
    }

    /*
     * Valid equivalence partitions for tag:
     *   - any tag
     *   - word with leading/trailing spaces
     *
     * Valid equivalence partitions for tagsList:
     *   - empty string
     *   - one tag
     *   - multiple tags
     *
     * Possible scenarios returning true:
     *   - matches first word in sentence
     *   - last word in sentence
     *   - middle word in sentence
     *   - matches multiple words
     *
     * Possible scenarios returning false:
     *   - query word matches part of a sentence word
     *   - sentence word matches part of the query word
     *
     * The test method below tries to verify all above with a reasonably low number of test cases.
     */

    @Test
    public void containsTagIgnoreCase_validInputs_correctResult() {

        // Empty tagsList
        assertFalse(StringUtil.containsTagIgnoreCase(Set.of(), "abc")); // Boundary case

        // Matches a partial tag only
        assertFalse(StringUtil.containsTagIgnoreCase(Set.of(new Tag("school"), new Tag("temple"),
                new Tag("zoo")), "temp")); // tagsList tag bigger than query tag
        assertFalse(StringUtil.containsTagIgnoreCase(Set.of(new Tag("school"), new Tag("temple"),
                new Tag("zoo")), "temples")); // Query word bigger than sentence word

        // Matches word in the sentence, different upper/lower case letters
        assertTrue(StringUtil.containsTagIgnoreCase(Set.of(new Tag("school"), new Tag("temple"),
                new Tag("zoo")), "School")); // First word (boundary case)
        assertTrue(StringUtil.containsTagIgnoreCase(Set.of(new Tag("school"), new Tag("temple"),
                new Tag("zoo")), "ZoO")); // Last word (boundary case)
        assertTrue(StringUtil.containsTagIgnoreCase(Set.of(new Tag("school")),
                "sChoOl")); // Only one word in sentence (boundary case)
        assertTrue(StringUtil.containsTagIgnoreCase(Set.of(new Tag("school"), new Tag("temple"),
                new Tag("zoo")), "   scHoOL   ")); // Leading/trailing spaces
    }

    //---------------- Tests for containsRating --------------------------------------

    /*
     * Invalid equivalence partitions for rating: null, empty, multiple ratings, characters outside range of 1 to 5
     * Invalid equivalence partitions for ratingsList: null
     * The four test cases below test one invalid input at a time.
     */

    @Test
    public void containsRating_nullWord_throwsNullPointerException() {
        assertExceptionForRatingThrown(NullPointerException.class, "4 5", null, Optional.empty());
    }

    private void assertExceptionForRatingThrown(Class<? extends Throwable> exceptionClass, String ratingsList,
                                                String rating, Optional<String> errorMessage) {
        thrown.expect(exceptionClass);
        errorMessage.ifPresent(message -> thrown.expectMessage(message));
        StringUtil.containsRating(ratingsList, rating);
    }

    @Test
    public void containsRating_emptyWord_throwsIllegalArgumentException() {
        assertExceptionForRatingThrown(IllegalArgumentException.class, "4 5", "  ",
                Optional.of("Rating parameter cannot be empty"));
    }

    @Test
    public void containsRating_multipleRatings_throwsIllegalArgumentException() {
        assertExceptionForRatingThrown(IllegalArgumentException.class, "4 5", "2 5",
                Optional.of("Rating parameter should be a single value from 1 to 5"));
    }

    @Test
    public void containsRating_charOutsideRange_throwsIllegalArgumentException() {
        assertExceptionForRatingThrown(IllegalArgumentException.class, "4 5", "7",
                Optional.of("Rating parameter should be a single value from 1 to 5"));
    }

    @Test
    public void containsRating_nullSentence_throwsNullPointerException() {
        assertExceptionForRatingThrown(NullPointerException.class, null, "2", Optional.empty());
    }
    /*
     * Valid equivalence partitions for rating:
     *   - any value between 1 to 5 inclusive
     *   - value from 1 to 5 inclusive with leading/trailing spaces
     *
     * Valid equivalence partitions for ratingsList:
     *   - empty string
     *   - one rating
     *   - multiple ratings
     *   - ratingsList with extra spaces
     *
     * Possible scenarios returning true:
     *   - matches first rating in ratingsList
     *   - last rating in ratingsList
     *   - middle rating in ratingsList
     *   - matches multiple ratings
     *
     * Possible scenarios returning false:
     *   - query rating not found in ratingsList
     *
     * The test method below tries to verify all above with a reasonably low number of test cases.
     */

    @Test
    public void containsRating_validInputs_correctResult() {

        // Empty ratingsList
        assertFalse(StringUtil.containsRating("", "4")); // Boundary case
        assertFalse(StringUtil.containsRating("    ", "5"));

        // Query rating not in ratingsList
        assertFalse(StringUtil.containsRating("3 4 5", "2"));
        assertFalse(StringUtil.containsRating("1 2 3", "5"));

        // Matches rating in the ratingsList
        assertTrue(StringUtil.containsRating("1 2 3", "1")); // First rating (boundary case)
        assertTrue(StringUtil.containsRating("2 3 4", "4")); // Last rating (boundary case)
        assertTrue(StringUtil.containsRating("  1   3   4  ", "1")); // ratingsList has extra spaces
        assertTrue(StringUtil.containsRating("2", "2")); // One rating in ratingsList (boundary case)
        assertTrue(StringUtil.containsRating("1 4 5", "  5  ")); // Leading/trailing spaces in rating

        // Matches multiple ratings in ratingsList
        assertTrue(StringUtil.containsRating("1 2 3  2", "2"));
    }

    //---------------- Tests for containsCountryCode --------------------------------------

    /*
     * Invalid equivalence partitions for countryCode: null, empty, multiple countryCode,
     * keywords longer or shorter than 3 characters
     * Invalid equivalence partitions for countryCodeList: null
     * 3 character words that do not correspond to ISO-3166 country codes
     * The four test cases below test one invalid input at a time.
     */

    @Test
    public void containsCountryCode_nullWord_throwsNullPointerException() {
        assertExceptionForCountryCodeThrown(NullPointerException.class, "SGP JPN", null, Optional.empty());
    }

    private void assertExceptionForCountryCodeThrown(Class<? extends Throwable> exceptionClass, String countryCodeList,
                                                String countryCode, Optional<String> errorMessage) {
        thrown.expect(exceptionClass);
        errorMessage.ifPresent(message -> thrown.expectMessage(message));
        StringUtil.containsCountryCode(countryCodeList, countryCode);
    }

    @Test
    public void containsCountryCode_emptyWord_throwsIllegalArgumentException() {
        assertExceptionForCountryCodeThrown(IllegalArgumentException.class, "SGP JPN", "  ",
                Optional.of("Country Code parameter cannot be empty"));
    }

    @Test
    public void containsCountryCode_multipleCountryCode_throwsIllegalArgumentException() {
        assertExceptionForCountryCodeThrown(IllegalArgumentException.class, "SGP JPN CHN",
                "SGP JPN", Optional.of("Country Code parameter should be a single word"));
    }

    @Test
    public void containsCountryCode_countryCodeLongerThanThreeLetter_throwsIllegalArgumentException() {
        assertExceptionForCountryCodeThrown(IllegalArgumentException.class, "SGP JPN USA",
                "SGXX", Optional.of("Country codes should only contain a three-letter alphabets"));
    }

    @Test
    public void containsCountryCode_countryCodeShorterThanThreeLetter_throwsIllegalArgumentException() {
        assertExceptionForCountryCodeThrown(IllegalArgumentException.class, "SGP JPN USA",
                "SG", Optional.of("Country codes should only contain a three-letter alphabets"));
    }

    @Test
    public void containsCountryCodeIgnoreCase_nullSentence_throwsNullPointerException() {
        assertExceptionForCountryCodeThrown(NullPointerException.class, null,
                "SGP", Optional.empty());
    }

    /*
     * Valid equivalence partitions for countryCode:
     *   - any 3 letter keyword that matches ISO-3166 country code
     *   - 3 letter keyword that matches ISO-3166 country code with leading/trailing spaces
     *
     * Valid equivalence partitions for countryCodeList:
     *   - empty string
     *   - one countryCode
     *   - multiple countryCode
     *   - countryCodeList with extra spaces
     *
     * Possible scenarios returning true:
     *   - matches first countryCode in countryCodeList
     *   - last countryCode in countryCodeList
     *   - middle countryCode in countryCodeList
     *   - matches multiple countryCode
     *
     * Possible scenarios returning false:
     *   - query rating not found in countryCodeList
     *
     * The test method below tries to verify all above with a reasonably low number of test cases.
     */

    @Test
    public void containsCountryCode_validInputs_correctResult() {

        // Empty countryCodeList
        assertFalse(StringUtil.containsCountryCode("", "SGP")); // Boundary case
        assertFalse(StringUtil.containsCountryCode("    ", "JPN"));

        // Query countryCode not in countryCodeList
        assertFalse(StringUtil.containsCountryCode("SGP JPN USA", "KOR"));
        assertFalse(StringUtil.containsCountryCode("DEU CHN IND", "SGP"));

        // Matches countryCode in the countryCodeList
        assertTrue(StringUtil.containsCountryCode("DEU JPN CHN", "DEU")); // First rating (boundary case)
        assertTrue(StringUtil.containsCountryCode("CHN SGP USA", "USA")); // Last rating (boundary case)
        assertTrue(StringUtil.containsCountryCode("  FRA   CHN   ITA  ", "FRA")); // countryCodeList has extra spaces
        assertTrue(StringUtil.containsCountryCode("SGP", "SGP")); // One countryCode in countryCodeList (boundary case)
        assertTrue(StringUtil.containsCountryCode("SGP DEU IND", "  IND  ")); // Leading/trailing spaces in countryCode

        // Matches multiple countryCode in countryCodeList
        assertTrue(StringUtil.containsCountryCode("DEU JPN USA  JPN", "JPN"));
    }

    //---------------- Tests for containsYear --------------------------------------

    /*
     * Invalid equivalence partitions for year: null, empty, multiple years, years outside the range of 1900-current
     * Invalid equivalence partitions for yearList: null
     * The four test cases below test one invalid input at a time.
     */

    @Test
    public void containsYear_nullWord_throwsNullPointerException() {
        assertExceptionForYearThrown(NullPointerException.class, "2018 2019", null, Optional.empty());
    }

    private void assertExceptionForYearThrown(Class<? extends Throwable> exceptionClass, String yearList,
                                                String year, Optional<String> errorMessage) {
        thrown.expect(exceptionClass);
        errorMessage.ifPresent(message -> thrown.expectMessage(message));
        StringUtil.containsYear(yearList, year);
    }

    @Test
    public void containsYear_emptyWord_throwsIllegalArgumentException() {
        assertExceptionForYearThrown(IllegalArgumentException.class, "2017 2019", "  ",
                Optional.of("Year parameter cannot be empty"));
    }

    @Test
    public void containsYear_multipleYears_throwsIllegalArgumentException() {
        assertExceptionForYearThrown(IllegalArgumentException.class, "2018 2019", "2018 2017",
                Optional.of("Years should only contain a year from 1900 to the current year"));
    }

    @Test
    public void containsYear_charOutsideRange_throwsIllegalArgumentException() {
        assertExceptionForYearThrown(IllegalArgumentException.class, "2018 2019", "1805",
                Optional.of("Years should only contain a year from 1900 to the current year"));
    }

    @Test
    public void containsYear_nullSentence_throwsNullPointerException() {
        assertExceptionForYearThrown(NullPointerException.class, null, "2018", Optional.empty());
    }
    /*
     * Valid equivalence partitions for year:
     *   - any year value between 1900 - current year
     *   - any year value between 1900 - current year with leading/trailing spaces
     *
     * Valid equivalence partitions for yearList:
     *   - empty string
     *   - one year
     *   - multiple years
     *   - yearList with extra spaces
     *
     * Possible scenarios returning true:
     *   - matches first year in yearList
     *   - last year in yearList
     *   - middle year in yearList
     *   - matches multiple years
     *
     * Possible scenarios returning false:
     *   - query year not found in yearList
     *
     * The test method below tries to verify all above with a reasonably low number of test cases.
     */

    @Test
    public void containsYear_validInputs_correctResult() {

        // Empty yearList
        assertFalse(StringUtil.containsYear("", "2018")); // Boundary case
        assertFalse(StringUtil.containsYear("    ", "2019"));

        // Query year not in yearList
        assertFalse(StringUtil.containsYear("2017 2018 2019", "2016"));
        assertFalse(StringUtil.containsYear("2015 2017 2018", "2019"));

        // Matches year in the yearList
        assertTrue(StringUtil.containsYear("2016 2018 2019", "2016")); // First rating (boundary case)
        assertTrue(StringUtil.containsYear("2016 2018 2019", "2019")); // Last rating (boundary case)
        assertTrue(StringUtil.containsYear("  2013   2017   2018  ", "2013")); // ratingsList has extra spaces
        assertTrue(StringUtil.containsYear("2018", "2018")); // One rating in ratingsList (boundary case)
        assertTrue(StringUtil.containsYear("2011 2012 2019", "  2019  ")); // Leading/trailing spaces in rating

        // Matches multiple years in yearList
        assertTrue(StringUtil.containsYear("2011 2014 2017  2014", "2014"));
    }

    //---------------- Tests for getDetails --------------------------------------

    /*
     * Equivalence Partitions: null, valid throwable object
     */

    @Test
    public void getDetails_exceptionGiven() {
        assertThat(StringUtil.getDetails(new FileNotFoundException("file not found")),
                   containsString("java.io.FileNotFoundException: file not found"));
    }

    @Test
    public void getDetails_nullGiven_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        StringUtil.getDetails(null);
    }


}
