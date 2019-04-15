package seedu.address.model.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.model.util.PocketProjectDate.DAY_FIELD;
import static seedu.address.model.util.PocketProjectDate.MONTH_FIELD;
import static seedu.address.model.util.PocketProjectDate.PADDING;
import static seedu.address.model.util.PocketProjectDate.YEAR_FIELD;

import java.time.LocalDateTime;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class PocketProjectDateTest {

    @Test
    public void constructor_invalidPocketProjectDate_throwsIllegalArgumentException() {
        String invalidDate = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new PocketProjectDate(invalidDate));
    }

    @Test
    public void isEarlierThan() {

        //date field earlier
        assertTrue(PocketProjectDate.isEarlierThan(new PocketProjectDate("23/04/2020"),
            new PocketProjectDate("24/04/2020")));
        assertTrue(PocketProjectDate.isEarlierThan(new PocketProjectDate("02/04/2020"),
            new PocketProjectDate("06/04/2020")));

        //month field earlier
        assertTrue(PocketProjectDate.isEarlierThan(new PocketProjectDate("23/04/2020"),
            new PocketProjectDate("23/05/2020")));
        assertTrue(PocketProjectDate.isEarlierThan(new PocketProjectDate("02/04/2020"),
            new PocketProjectDate("02/05/2020")));

        //year field earlier
        assertTrue(PocketProjectDate.isEarlierThan(new PocketProjectDate("23/04/2020"),
            new PocketProjectDate("23/04/2021")));
        assertTrue(PocketProjectDate.isEarlierThan(new PocketProjectDate("02/04/2006"),
            new PocketProjectDate("02/04/2007")));

        //all fields earlier
        assertTrue(PocketProjectDate.isEarlierThan(new PocketProjectDate("10/03/2009"),
            new PocketProjectDate("26/11/2021")));
        assertTrue(PocketProjectDate.isEarlierThan(new PocketProjectDate("04/04/2014"),
            new PocketProjectDate("22/06/2019")));

        //date field later
        assertFalse(PocketProjectDate.isEarlierThan(new PocketProjectDate("25/04/2020"),
            new PocketProjectDate("24/04/2020")));

        //month field later
        assertFalse(PocketProjectDate.isEarlierThan(new PocketProjectDate("24/04/2020"),
            new PocketProjectDate("24/03/2020")));

        //year field later
        assertFalse(PocketProjectDate.isEarlierThan(new PocketProjectDate("24/04/2020"),
            new PocketProjectDate("24/04/2019")));

        //all fields later
        assertFalse(PocketProjectDate.isEarlierThan(new PocketProjectDate("24/04/2020"),
            new PocketProjectDate("14/07/2008")));
    }

    @Test
    public void isLaterThan() {

        //all fields same
        assertFalse(PocketProjectDate.isLaterThan(new PocketProjectDate("23/01/2010"),
            new PocketProjectDate("23/01/2010")));

        //all field different
        assertTrue(PocketProjectDate.isLaterThan(new PocketProjectDate("23/01/2014"),
            new PocketProjectDate("13/02/2012")));

        //date field later
        assertTrue(PocketProjectDate.isLaterThan(new PocketProjectDate("22/01/2010"),
            new PocketProjectDate("21/01/2010")));

        //month field later
        assertTrue(PocketProjectDate.isLaterThan(new PocketProjectDate("23/03/2010"),
            new PocketProjectDate("23/02/2010")));

        //year field later
        assertTrue(PocketProjectDate.isLaterThan(new PocketProjectDate("23/01/2011"),
            new PocketProjectDate("23/01/2010")));

    }

    @Test
    public void isSameDate() {

        //all fields same
        assertTrue(PocketProjectDate.isSameDate(new PocketProjectDate("23/01/2010"),
            new PocketProjectDate("23/01/2010")));

        //all field different
        assertFalse(PocketProjectDate.isSameDate(new PocketProjectDate("23/01/2010"),
            new PocketProjectDate("13/02/2012")));
    }


    @Test
    public void generateStringFormat() {

        //padding required
        assertEquals("07/11/2017", PocketProjectDate.generateStringDateFormat(7, 11, 2017));
        assertEquals("25/09/2017", PocketProjectDate.generateStringDateFormat(25, 9, 2017));
        assertEquals("06/05/2017", PocketProjectDate.generateStringDateFormat(6, 5, 2017));

        //no padding required
        assertEquals("16/12/2017", PocketProjectDate.generateStringDateFormat(16, 12, 2017));
    }

    @Test
    public void splitComponents() {

        Integer[] dateComponents = new Integer[PocketProjectDate.NUM_COMPONENTS];
        dateComponents[DAY_FIELD] = 4;
        dateComponents[MONTH_FIELD] = 9;
        dateComponents[YEAR_FIELD] = 2012;

        //without leading zeroes
        assertEquals(dateComponents, PocketProjectDate.splitComponents("4/9/2012"));

        //with leading zeroes
        assertEquals(dateComponents, PocketProjectDate.splitComponents("04/09/2012"));
    }

    @Test
    public void convertToLocalDateTimeFormat() {

        LocalDateTime createDate = LocalDateTime.of(2012, 9, 4, PADDING, PADDING, PADDING);

        //without leading zeroes
        assertEquals(createDate, PocketProjectDate.convertToLocalDateTimeFormat("4/9/2012"));

        //with leading zeroes
        assertEquals(createDate, PocketProjectDate.convertToLocalDateTimeFormat("04/09/2012"));
    }

    @Test
    public void isValidDate() {

        // null date
        Assert.assertThrows(NullPointerException.class, () -> PocketProjectDate.isValidDate(null));

        // blank input
        assertFalse(PocketProjectDate.isValidDate("")); // empty string
        assertFalse(PocketProjectDate.isValidDate(" ")); // spaces only

        // missing parts
        assertFalse(PocketProjectDate.isValidDate("11")); // missing both the month/date and the year value
        assertFalse(PocketProjectDate.isValidDate("11/11")); // missing the year value
        assertFalse(PocketProjectDate.isValidDate("11/2022")); // missing the month/date value

        // invalid parts
        assertFalse(PocketProjectDate.isValidDate("10//09/2019")); // wrong input pattern
        assertFalse(PocketProjectDate.isValidDate("10/11/12/2011")); // too many fields
        assertFalse(PocketProjectDate.isValidDate("111/12/2019")); // too many digits in the date field
        assertFalse(PocketProjectDate.isValidDate("11/119/2019")); // too many digits in the month field
        assertFalse(PocketProjectDate.isValidDate("05/10/131111")); // too many digits in the date field
        assertFalse(PocketProjectDate.isValidDate(" 05/12/2009")); // leading space
        assertFalse(PocketProjectDate.isValidDate("01/03/2009 ")); // trailing space
        assertFalse(PocketProjectDate.isValidDate("ab/11/2019")); // alphabetical date
        assertFalse(PocketProjectDate.isValidDate("11/cd/2019")); // alphabetical month
        assertFalse(PocketProjectDate.isValidDate("11/11/fffs")); // alphabetical year
        assertFalse(PocketProjectDate.isValidDate("44/12/2006")); // invalid date
        assertFalse(PocketProjectDate.isValidDate("11/98/aaaa")); // invalid month

        //invalid years -> only dates between 2000-2099 inclusive accepted
        assertFalse(PocketProjectDate.isValidDate("11/11/1750"));
        assertFalse(PocketProjectDate.isValidDate("11/11/1850"));
        assertFalse(PocketProjectDate.isValidDate("11/11/1950"));
        assertFalse(PocketProjectDate.isValidDate("11/11/2100"));
        assertFalse(PocketProjectDate.isValidDate("11/11/1999"));
        //no padding
        assertFalse(PocketProjectDate.isValidDate("01/1/2000"));
        assertFalse(PocketProjectDate.isValidDate("1/01/2000"));
        assertFalse(PocketProjectDate.isValidDate("1/1/2000"));

        // valid deadlines
        assertTrue(PocketProjectDate.isValidDate("01/01/2000"));
        assertTrue(PocketProjectDate.isValidDate("06/07/2011"));

    }
}
