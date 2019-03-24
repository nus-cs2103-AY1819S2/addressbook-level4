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
import seedu.address.model.Album;
import seedu.address.model.CurrentEdit;
import seedu.address.model.Model;
import seedu.address.model.image.Image;

/**
 * This command allows the user to crop an image.
 */
public class CropCommand extends Command {

    public static final String COMMAND_WORD = "crop";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Crop the image according to the coordinates given.\n"
            + "Parameters: X-COORDINATE, Y-COORDINATE, WIDTH, HEIGHT \n"
            + "All values to be integer/whole numbers only. \n"
            + "Example: " + COMMAND_WORD + " 0 0 200 200 sample.png";
    private int xCoord;
    private int yCoord;
    private int width;
    private int height;
    private String fileName;

    public CropCommand(int x, int y, int width, int height, String image) {
        xCoord = x;
        yCoord = y;
        this.width = width;
        this.height = height;
        fileName = image;
    }

    @Override
    public CommandResult execute(CurrentEdit currentEdit, Album album,
                                 CommandHistory history, Model model) throws CommandException {
        Image initialImage = new Image(ASSETS_FILEPATH + fileName);
        try {
            File directory = new File(TEMP_FILEPATH);
            BufferedImage initImage = initialImage.getBufferedImage();
            BufferedImage finalImage = Scalr.crop(initImage, xCoord, yCoord, width, height);

            File outputFile = new File(fileName);
            ImageIO.write(finalImage, initialImage.getFileType(), outputFile);
            FileUtils.copyFileToDirectory(outputFile, directory);
            outputFile.delete();

        } catch (IOException e) {
            throw new CommandException(Messages.MESSAGE_FILE_DOES_NOT_EXIST);

        } catch (IllegalArgumentException e) {
            throw new CommandException("Invalid crop bounds, bounds for the inputs as follows:\n"
                    + "0 <= x-coordinate <= " + initialImage.getWidth()
                    + ", 0 <= y-coordinate <= " + initialImage.getHeight() + ",\n"
                    + "0 <= x + width <= " + initialImage.getWidth()
                    + ", 0 <= y + height <= " + initialImage.getHeight());
        }

        Image finalImage = new Image(TEMP_FILEPATH + fileName);
        model.displayImage(finalImage);

        return new CommandResult(Messages.MESSAGE_CROP_SUCCESS);
    }
}
