package seedu.address.commons.util;

import static org.junit.Assert.assertNotNull;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class AppUtilTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();



    @Test
    public void getImage_exitingImage() {
<<<<<<< HEAD
        assertNotNull(AppUtil.getImage("/images/MediTabs.png"));
=======
        assertNotNull(AppUtil.getImage("/images/medicine_bottle_32.png"));
>>>>>>> 6840270be8ab553fff9a7c5ae73403276fb54fab
    }


    @Test
    public void getImage_nullGiven_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        AppUtil.getImage(null);
    }

    @Test
    public void checkArgument_true_nothingHappens() {
        AppUtil.checkArgument(true);
        AppUtil.checkArgument(true, "");
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
