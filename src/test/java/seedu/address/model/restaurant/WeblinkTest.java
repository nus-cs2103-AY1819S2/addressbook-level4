package seedu.address.model.restaurant;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class WeblinkTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Weblink(null));
    }

    @Test
    public void constructor_invalidWeblink_throwsIllegalArgumentException() {
        String invalidWeblink = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Weblink(invalidWeblink));
    }

    @Test
    public void isValidWeblinkString() {
        // null Weblink number
        Assert.assertThrows(NullPointerException.class, () -> Weblink.isValidWeblinkString(null));

        // missing parts
        assertFalse(Weblink.isValidWeblinkString(".com")); // missing local part
        assertFalse(Weblink.isValidWeblinkString("peterjackexamplecom")); // missing domain
        assertFalse(Weblink.isValidWeblinkString("peterjack.")); // missing domain name

        // extra protocol
        assertFalse(Weblink.isValidWeblinkString("https://http://PeterJack_1190.example.com"));

        // invalid parts
        assertFalse(Weblink.isValidWeblinkString("peterjack.-")); // invalid domain name
        assertFalse(Weblink.isValidWeblinkString("peterjack.exam_ple.com")); // underscore in domain name
        assertFalse(Weblink.isValidWeblinkString("peter jack.example.com")); // spaces in local part
        assertFalse(Weblink.isValidWeblinkString("peterjack.exam ple.com")); // spaces in domain name
        assertFalse(Weblink.isValidWeblinkString(" peterjack.example.com")); // leading space
        assertFalse(Weblink.isValidWeblinkString("peterjack.example.com ")); // trailing space
        assertFalse(Weblink.isValidWeblinkString("peterjack..example.com")); // double '@' symbol
        assertFalse(Weblink.isValidWeblinkString("peterjack.example.com.")); // domain name ends with a period
        assertFalse(Weblink.isValidWeblinkString("peterjack.-example.com")); // domain name starts with a hyphen
        assertFalse(Weblink.isValidWeblinkString("peterjack.example.com-")); // domain name ends with a hyphen

        // valid Weblink
        assertTrue(Weblink.isValidWeblinkString("PeterJack_1190.example.com"));
        assertTrue(Weblink.isValidWeblinkString("a.bc")); // minimal
        assertTrue(Weblink.isValidWeblinkString("test.localhost")); // alphabets only
        assertTrue(Weblink.isValidWeblinkString("!#$%&'*+/=?`{|}~^.example.org")); // special characters local part
        assertTrue(Weblink.isValidWeblinkString("123.145")); // numeric local part and domain name
        assertTrue(Weblink
                .isValidWeblinkString("a1+be!.example1.com")); // mixture of alphanumeric and special characters
        assertTrue(Weblink.isValidWeblinkString("peter_jack.veryveryverylongexample.com")); // long domain name
        assertTrue(Weblink.isValidWeblinkString("if.you.dream.it.you.can.do.it.example.com")); // long local part

        // valid Weblink with protocol
        assertTrue(Weblink.isValidWeblinkString("https://PeterJack_1190.example.com"));
        assertTrue(Weblink.isValidWeblinkString("http://PeterJack_1190.example.com"));

        // default Weblink
        assertTrue(Weblink.isValidWeblinkString("No weblink added"));

    }
}
