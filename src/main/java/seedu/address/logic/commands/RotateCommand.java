package seedu.address.logic.commands;

import static seedu.address.commons.core.Config.ASSETS_FILEPATH;
import static seedu.address.commons.core.Config.TEMP_FILEPATH;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.imgscalr.Scalr;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.image.Image;


/**
 * This command allows users to rotate images.
 */
public class RotateCommand extends Command {

    public static final String COMMAND_WORD = "rotate";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Rotate the image according to the degree of input.\n"
            + "Parameters: DEGREE (a positive degree indicates clockwise, a negative "
            + "degree indicates anticlockwise rotation.)\n"
            + "Example: " + COMMAND_WORD + " 90";

    private static int degree;
    private static Scalr.Rotation rotate;
    private static String fileName;

    /**
     * Creates a RotateCommand object.
     * @param value is the degree of rotation.
     * @param image is the file name of the image.
     */
    public RotateCommand(int value, String image) {
        this.degree = value;
        fileName = image;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {

        if (degree == 90) {
            rotate = Scalr.Rotation.CW_90;
        } else if (degree == 180) {
            rotate = Scalr.Rotation.CW_180;
        } else if (degree == 270) {
            rotate = Scalr.Rotation.CW_270;
        } else {
            throw new CommandException(Messages.MESSAGE_ROTATE_DEGREE_ERROR);
        }

        try {
            File directory = new File(TEMP_FILEPATH);
            Image initialImage = new Image(ASSETS_FILEPATH + fileName);
            BufferedImage bufferImage = Scalr.rotate(initialImage.getBufferedImage(), rotate);
            //hardcoded the result file, have to concatenate in future and have specific ones
            // when we have more than 1 file
            File outputFile = new File(fileName);
            ImageIO.write(bufferImage, initialImage.getFileType(), outputFile);
            FileUtils.copyFileToDirectory(outputFile, directory, false);
        } catch (IOException | IllegalArgumentException x) {
            throw new CommandException(Messages.MESSAGE_FILE_DOES_NOT_EXIST);
        }

        Image finalImage = new Image(TEMP_FILEPATH + fileName);
        model.displayImage(finalImage);

        return new CommandResult(Messages.MESSAGE_ROTATE_SUCCESS);

    }
}
