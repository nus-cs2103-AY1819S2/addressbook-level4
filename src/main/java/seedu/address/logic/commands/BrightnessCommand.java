package seedu.address.logic.commands;

import static seedu.address.commons.core.Config.ASSETS_FILEPATH;
import static seedu.address.commons.core.Config.TEMP_FILEPATH;

import java.io.File;
import java.util.OptionalDouble;

import com.sksamuel.scrimage.BufferedOpFilter;
import com.sksamuel.scrimage.Image;
import com.sksamuel.scrimage.filter.BrightnessFilter;
import com.sksamuel.scrimage.nio.JpegWriter;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Album;
import seedu.address.model.CurrentEdit;
import seedu.address.model.Model;

/**
 * This command allows users to adjust the brightness of images.
 */
public class BrightnessCommand extends Command {

    public static final String COMMAND_WORD = "brightness";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Adjust the brightness of the image according to the operators.\n"
        + "If ratio is not given, default brightness ratio will be 1.1\n"
        + "[BRIGHTNESS RATIO (double)] "
        + "and FILENAME.\n"
        + "Example: " + COMMAND_WORD + " cutedog.jpg"
        + "Example2: " + COMMAND_WORD + " 0.3 cutedog.jpg";
    private OptionalDouble brightnessValue;
    private String fileName;

    /**
     * Creates a ContrastCommand object.
     * @param brightnessValue brightness value to add on image
     * @param fileName is the file name of the image.
     */
    public BrightnessCommand(OptionalDouble brightnessValue, String fileName) {
        this.brightnessValue = brightnessValue;
        this.fileName = fileName;
    }
    @Override
    public CommandResult execute(CurrentEdit currentEdit, Album album, CommandHistory history, Model model) {
        //seedu.address.model.image.Image initialImage = currentEdit.getTempImage();
        //if (this.brightnessValue.isPresent()) {
        //    BufferedOpFilter brightnessFilter =
        //        new BrightnessFilter(this.brightnessValue.getAsDouble());
        //    Image outputImage = Image.fromFile(new File(initialImage.getUrl())).filter(brightnessFilter);
        //    currentEdit.setTempImage(outputImage);
        //} else {
        //    BufferedOpFilter brightnessFilter =
        //        new BrightnessFilter(1.1);
        //    Image outputImage = Image.fromFile(new File(initialImage.getUrl())).filter(brightnessFilter);
        //   currentEdit.setTempImage(outputImage);
        //}
        //currentEdit.displayTempImage();
        //currentEdit.addCommand(this);
        //return new CommandResult(Messages.MESSAGE_CONTRAST_SUCCESS);
        if (this.brightnessValue.isPresent()) {
            seedu.address.model.image.Image initialImage = new seedu.address
                .model.image.Image(ASSETS_FILEPATH + fileName);
            BufferedOpFilter brightnessFilter =
                new BrightnessFilter(this.brightnessValue.getAsDouble());
            Image.fromFile(new File(ASSETS_FILEPATH
                + fileName)).filter(brightnessFilter)
                .output(TEMP_FILEPATH + "sampleBrightness.jpg",
                    new JpegWriter(100 , true));
        } else {
            seedu.address.model.image.Image initialImage = new seedu.address
                .model.image.Image(ASSETS_FILEPATH + fileName);
            BufferedOpFilter brightnessFilter =
                new BrightnessFilter(1.1);
            Image.fromFile(new File(ASSETS_FILEPATH
                + fileName)).filter(brightnessFilter)
                .output(TEMP_FILEPATH + "sampleBrightness.jpg",
                    new JpegWriter(100, true));
        }
        seedu.address.model.image.Image finalImage = new seedu.address.model
            .image.Image(TEMP_FILEPATH + "sampleBrightness.jpg");
        model.displayImage(finalImage);

        return new CommandResult(Messages.MESSAGE_BRIGHTNESS_SUCCESS);
    }

}
