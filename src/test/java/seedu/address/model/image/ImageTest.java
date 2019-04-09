package seedu.address.model.image;

import java.util.OptionalDouble;

import org.junit.Test;

import seedu.address.logic.commands.BrightnessCommand;
import seedu.address.logic.commands.Command;

public class ImageTest {
    private Image image;
    private Command brightnessCommand = new BrightnessCommand(OptionalDouble.empty());

    @Test
    public void test_constructor_success() {
        image = new Image("src/main/resources/imageTest/valid/validPNGTest.png");
    }
}
