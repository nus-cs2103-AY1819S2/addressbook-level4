package seedu.address.logic.commands;

import static seedu.address.commons.core.Config.ASSETS_FILEPATH;
import static seedu.address.commons.core.Config.TEMP_FILEPATH;

import java.io.File;
import java.util.OptionalDouble;

import com.sksamuel.scrimage.BufferedOpFilter;
import com.sksamuel.scrimage.Image;
import com.sksamuel.scrimage.filter.ContrastFilter;
import com.sksamuel.scrimage.nio.JpegWriter;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Album;
import seedu.address.model.CurrentEdit;
import seedu.address.model.Model;

/**
 * This command allows users to adjust the contrast of images.
 */
public class ContrastCommand extends Command {

    public static final String COMMAND_WORD = "contrast";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Adjust the contrast of the image according to ratio given.\n"
        + "If ratio is not given, default contrast ratio will be 1.1\n"
        + "Parameters: [CONTRAST RATIO (double)] "
        + "and FILENAME.\n"
        + "Example: " + COMMAND_WORD + " + cutedog.jpg"
        + "Example2: " + COMMAND_WORD + " 0.3 cutedog.jpg";

    private OptionalDouble contrastValue;
    private String fileName;

    /**
     * Creates a ContrastCommand object.
     * @param contrastValue contrast value to put on image
     * @param image is the file name of the image.
     */
    public ContrastCommand(OptionalDouble contrastValue, String image) {
        this.contrastValue = contrastValue;
        this.fileName = image;
    }

    @Override
    public CommandResult execute(CurrentEdit currentEdit, Album album, Model model, CommandHistory history) {
        //seedu.address.model.image.Image initialImage = currentEdit.getTempImage();
        //if (this.contrastValue.isPresent()) {
        //    BufferedOpFilter contrastFilter =
        //        new ContrastFilter(this.contrastValue.getAsDouble());
        //    Image outputImage = Image.fromFile(new File(initialImage.getUrl())).filter(contrastFilter);
        //    currentEdit.setTempImage(outputImage);
        //} else {
        //    BufferedOpFilter contrastFilter =
        //        new ContrastFilter(1.1);
        //    Image outputImage = Image.fromFile(new File(initialImage.getUrl())).filter(contrastFilter);
        //   currentEdit.setTempImage(outputImage);
        //}
        //currentEdit.displayTempImage();
        //currentEdit.addCommand(this);
        //return new CommandResult(Messages.MESSAGE_CONTRAST_SUCCESS);

        if (this.contrastValue.isPresent()) {
            seedu.address.model.image.Image initialImage = new seedu.address
                .model.image.Image(ASSETS_FILEPATH + fileName);
            BufferedOpFilter contrastFilter =
                new ContrastFilter(this.contrastValue.getAsDouble());
            Image.fromFile(new File(ASSETS_FILEPATH
                + fileName)).filter(contrastFilter)
                .output(TEMP_FILEPATH + "sampleContrast.jpg",
                    new JpegWriter(100, true));
        } else {
            BufferedOpFilter contrastFilter =
                new ContrastFilter(1.1);
            Image image = Image.fromFile(new File(ASSETS_FILEPATH
                + fileName)).filter(contrastFilter);
            image.output(TEMP_FILEPATH + "sampleContrast.jpg",
                        new JpegWriter(100, true));
        }
        seedu.address.model.image.Image finalImage = new seedu.address.model
            .image.Image(TEMP_FILEPATH + "sampleContrast.jpg");
        model.displayImage(finalImage);
        return new CommandResult(Messages.MESSAGE_CONTRAST_SUCCESS);
    }
}
