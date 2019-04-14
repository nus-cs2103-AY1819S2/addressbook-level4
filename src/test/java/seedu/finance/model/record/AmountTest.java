package seedu.finance.model.record;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import seedu.finance.testutil.Assert;

public class AmountTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Amount(null));
    }

    @Test
    public void constructor_invalidAmount_throwsIllegalArgumentException() {
        String invalidAmount = "$1";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Amount(invalidAmount));
    }

    @Test
    public void isValidAmount() {
        // null amount
        Assert.assertThrows(NullPointerException.class, () -> Amount.isValidAmount(null));

        // invalid amounts
        assertFalse(Amount.isValidAmount("")); // empty string
        assertFalse(Amount.isValidAmount(" ")); // spaces only
        assertFalse(Amount.isValidAmount("123.3435")); // 2 dp only
        assertFalse(Amount.isValidAmount("0")); // Non-Positive number
        assertFalse(Amount.isValidAmount("100000000.01")); // larger than 100 000 000

        // valid amounts
        assertTrue(Amount.isValidAmount("123"));
        assertTrue(Amount.isValidAmount("123.20")); // 2 dp only
        assertTrue(Amount.isValidAmount("1")); // one character
        assertTrue(Amount.isValidAmount("100000000")); // max amount allowed
    }

    @Test
    public void hashCode_sameAmountInDouble() {
        Amount amountOne = new Amount("50");
        Amount amountTwo = new Amount("50.00");
        assertEquals(amountOne.hashCode(), amountTwo.hashCode());
    }
}
