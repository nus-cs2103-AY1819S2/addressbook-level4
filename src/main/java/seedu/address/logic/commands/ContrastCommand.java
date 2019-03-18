package seedu.address.logic.commands;

import static seedu.address.commons.core.Config.ASSETS_FILEPATH;
import static seedu.address.commons.core.Config.TEMP_FILEPATH;

import java.io.File;
import java.util.OptionalInt;

import com.sksamuel.scrimage.BufferedOpFilter;
import com.sksamuel.scrimage.Image;
import com.sksamuel.scrimage.filter.ContrastFilter;
import com.sksamuel.scrimage.nio.JpegWriter;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * This command allows users to adjust the contrast of images.
 */
public class ContrastCommand extends Command {

    public static final String COMMAND_WORD = "contrast";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Adjust the contrast of the image according to the operators.\n"
        + "Parameters: OPERATORS ('add' indicates an increase of contrast"
        + "and 'subtract' indicates a decrease in contrast) "
        + "[positive CONTRASTVALUE] "
        + "and FILENAME.\n"
        + "Example: " + COMMAND_WORD + " add + cutedog.jpg"
        + "Example2: " + COMMAND_WORD + " subtract 2 cutedog.jpg";

    private String operator;
    private OptionalInt contrastValue;
    private String fileName;

    /**
     * Creates a ContrastCommand object.
     * @param operator is add/subtract
     * @param image is the file name of the image.
     */
    public ContrastCommand(String operator, OptionalInt contrastValue, String image) {
        this.operator = operator;
        this.contrastValue = contrastValue;
        this.fileName = image;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        int contrastSign = 1;
        if (this.operator.equals("add")) {
            contrastSign = 1;
        } else if (this.operator.equals("subtract")) {
            contrastSign = -1;
        }
        if (this.contrastValue.isPresent()) {
            seedu.address.model.image.Image initialImage = new seedu.address.model.image.Image(ASSETS_FILEPATH + fileName);
            BufferedOpFilter contrastFilter =
                new ContrastFilter(contrastSign * (double) this.contrastValue.getAsInt());
            Image.fromFile(new File(ASSETS_FILEPATH
                + fileName)).filter(contrastFilter)
                .output(TEMP_FILEPATH + "sampleContrast.jpg",
                    new JpegWriter(0, true));
        } else {
            BufferedOpFilter contrastFilter =
                new ContrastFilter(contrastSign * 1.0);
            Image.fromFile(new File(ASSETS_FILEPATH
                + fileName)).filter(contrastFilter)
                .output(TEMP_FILEPATH + "sampleContrast.jpg",
                        new JpegWriter(0, true));
        }
        seedu.address.model.image.Image finalImage = new seedu.address.model
            .image.Image(TEMP_FILEPATH + "sampleContrast.jpg");
        model.displayImage(finalImage);
        return new CommandResult(Messages.MESSAGE_CONTRAST_SUCCESS);
    }
}
