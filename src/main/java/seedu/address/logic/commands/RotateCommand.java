package seedu.address.logic.commands;

import static seedu.address.commons.core.Config.ASSETS_FILEPATH;
import static seedu.address.commons.core.Config.TEMP_FILEPATH;

//import java.awt.*;
//import java.awt.geom.AffineTransform;
//import java.awt.image.AffineTransformOp;

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
 * This command allows users to rotate images.
 */
public class RotateCommand extends Command {

    public static final String COMMAND_WORD = "rotate";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Rotate the image according to the degree of input.\n"
            + "Parameters: DEGREE (only 90, 180 and 270 are accepted. \n"
            + "Example: " + COMMAND_WORD + " 90";

    private int degree;
    private Scalr.Rotation rotate;
    private String fileName;
    //private static final Color BACKGROUND = new Color(56, 56, 56, 255);

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
    public CommandResult execute(CurrentEdit currentEdit, Album album,
                                 Model model, CommandHistory history) throws CommandException {

        if (degree == 90) {
            rotate = Scalr.Rotation.CW_90;
        } else if (degree == 180) {
            rotate = Scalr.Rotation.CW_180;
        } else if (degree == 270) {
            rotate = Scalr.Rotation.CW_270;
        } else {
            throw new CommandException(Messages.MESSAGE_ROTATE_DEGREE_ERROR);
        }

        //try {
        //    File directory = new File(TEMP_FILEPATH);
        //    Image initialImage = new Image(ASSETS_FILEPATH + fileName);
        //    BufferedImage initImage = initialImage.getBufferedImage();

        //    int w = initImage.getWidth();
        //    int h = initImage.getHeight();
        //    double rotationRequired = Math.toRadians (degree);
        //    int hPrime = (int) (w * Math.abs(Math.sin(rotationRequired)) + h * Math.abs(Math.cos(rotationRequired)));
        //    int wPrime = (int) (h * Math.abs(Math.sin(rotationRequired)) + w * Math.abs(Math.cos(rotationRequired)));

        //    File outputFile = new File(fileName);
        //    BufferedImage finalImage = new BufferedImage(wPrime, hPrime, initImage.getType());
        //    System.out.println(initImage.getType());


        //    Rotation information
        //    double locationX = initImage.getWidth() / 2;
        //    double locationY = initImage.getHeight() / 2;
        //    AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
        //    AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);

        //    Drawing the rotated image at the required drawing locations
        //    Graphics2D g2d = (Graphics2D) initImage.getGraphics();
        //    g2d.drawImage(op.filter(initImage, finalImage), drawLocationX, drawLocationY, null);
        //    ImageIO.write(finalImage, initialImage.getFileType(), outputFile);
        //    FileUtils.copyFileToDirectory(outputFile, directory, false);
        //    outputFile.delete();

        //    Graphics2D g = finalImage.createGraphics();
        //    g.setColor(BACKGROUND);
        //    g.fillRect(0 ,0 , wPrime, hPrime);
        //    g.translate(wPrime/2, hPrime/2);
        //    g.rotate(rotationRequired);
        //    g.translate(-w / 2, -h / 2);
        //    g.drawImage(initImage, 0, 0, null);
        //    ImageIO.write(finalImage, initialImage.getFileType(), outputFile);
        //    FileUtils.copyFileToDirectory(outputFile, directory, false);
        //    outputFile.delete();
        // } catch (Exception e) {
        //    throw new CommandException(Messages.MESSAGE_FILE_DOES_NOT_EXIST);
        // }
        try {
            File directory = new File(TEMP_FILEPATH);
            Image initialImage = new Image(ASSETS_FILEPATH + fileName);
            BufferedImage bufferImage = Scalr.rotate(initialImage.getBufferedImage(), rotate);
            //hardcoded the result file, have to concatenate in future and have specific ones
            // when we have more than 1 file
            File outputFile = new File(fileName);
            ImageIO.write(bufferImage, initialImage.getFileType(), outputFile);
            FileUtils.copyFileToDirectory(outputFile, directory, false);
            outputFile.delete();
        } catch (IOException | IllegalArgumentException x) {
            throw new CommandException(Messages.MESSAGE_FILE_DOES_NOT_EXIST);
        }

        Image finalImage = new Image(TEMP_FILEPATH + fileName);
        model.displayImage(finalImage);

        return new CommandResult(Messages.MESSAGE_ROTATE_SUCCESS);

    }
}
