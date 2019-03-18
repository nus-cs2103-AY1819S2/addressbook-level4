package seedu.address.logic.commands;

import static seedu.address.commons.core.Config.ASSETS_FILEPATH;
import static seedu.address.commons.core.Config.TEMP_FILEPATH;

import java.io.File;
import java.util.OptionalInt;

import com.sksamuel.scrimage.BufferedOpFilter;
import com.sksamuel.scrimage.Image;
import com.sksamuel.scrimage.filter.BrightnessFilter;
import com.sksamuel.scrimage.filter.ContrastFilter;
import com.sksamuel.scrimage.nio.JpegWriter;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

public class BrightnessCommand extends Command {

    public static final String COMMAND_WORD = "brightness";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Adjust the brightness of the image according to the operators.\n"
        + "Parameters: OPERATORS ('add' indicates an increase of brightness"
        + "and 'subtract' indicates a decrease in brightness) "
        + "[positive BRIGHTNESSVALUE] "
        + "and FILENAME.\n"
        + "Example: " + COMMAND_WORD + " add + cutedog.jpg"
        + "Example2: " + COMMAND_WORD + " subtract 2 cutedog.jpg";

    private String operator;
    private OptionalInt brightnessValue;
    private String fileName;

    public BrightnessCommand(String operator, OptionalInt brightnessValue, String fileName) {
        this.operator = operator;
        this.brightnessValue = brightnessValue;
        this.fileName = fileName;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        int brightnessSign = 1;
        if (this.operator.equals("add")) {
            brightnessSign = 1;
        } else if (this.operator.equals("subtract")) {
            brightnessSign = -1;
        }
        if (this.brightnessValue.isPresent()) {
            seedu.address.model.image.Image initialImage = new seedu.address.model.image.Image(ASSETS_FILEPATH + fileName);
            BufferedOpFilter brightnessFilter =
                new BrightnessFilter(brightnessSign * (double) this.brightnessValue.getAsInt());
            Image.fromFile(new File(ASSETS_FILEPATH
                + fileName)).filter(brightnessFilter)
                .output(TEMP_FILEPATH + "sampleContrast.jpg",
                    new JpegWriter(0, true));
        } else {
            seedu.address.model.image.Image initialImage = new seedu.address.model.image.Image(ASSETS_FILEPATH + fileName);
            BufferedOpFilter brightnessFilter =
                new BrightnessFilter(brightnessSign * 1.0);
            Image.fromFile(new File(ASSETS_FILEPATH
                + fileName)).filter(brightnessFilter)
                .output(TEMP_FILEPATH + "sampleContrast.jpg",
                    new JpegWriter(0, true));
        }

        return new CommandResult(Messages.MESSAGE_BRIGHTNESS_SUCCESS);
    }

}
