package braintrain.commons.util;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import braintrain.testutil.Assert;

public class AppUtilTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void checkArgument_true_nothingHappens() {
        AppUtil.checkArgument(true);
        AppUtil.checkArgument(true, "");
        Assert.assertThrows(IllegalArgumentException.class, () ->
            AppUtil.checkArgument(false));
    }

    @Test
    public void checkArgument_falseWithoutErrorMessage_throwsIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        AppUtil.checkArgument(false);
    }

    @Test
    public void checkArgument_falseWithErrorMessage_throwsIllegalArgumentException() {
        String errorMessage = "error message";
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(errorMessage);
        AppUtil.checkArgument(false, errorMessage);
    }
}
