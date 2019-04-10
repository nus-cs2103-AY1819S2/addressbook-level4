/* @@author kayheen */
package seedu.address.logic.commands;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.CurrentEdit;
import seedu.address.model.Model;
import seedu.address.model.image.Image;

/**
 * This command allows users to add a watermark to an image.
 */
public class WaterMarkCommand extends Command {
    public static final String COMMAND_WORD = "wm";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Include a text watermark on the image currently editing. \n"
            + "Parameters: WATERMARK \n"
            + "Example: " + COMMAND_WORD + " FomoFoto";
    private String text;
    private boolean isNewCommand;

    public WaterMarkCommand(String words) {
        setCommandName(COMMAND_WORD);
        setArguments(words);
        this.text = "\u00a9 " + words;
        this.isNewCommand = true;
    }

    @Override
    public CommandResult execute(CurrentEdit currentEdit,
                                 Model model, CommandHistory history) throws CommandException {
        Image initialImage = currentEdit.getTempImage();
        if (initialImage == null) {
            throw new CommandException(Messages.MESSAGE_DID_NOT_OPEN);
        }
        BufferedImage bufferedImage = initialImage.getBufferedImage();
        String type = initialImage.getFileType();

        // determine image type and handle correct transparency
        int imageType = "png".equalsIgnoreCase(type) ? BufferedImage.TYPE_INT_ARGB : BufferedImage.TYPE_INT_RGB;
        BufferedImage watermarked = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), imageType);

        // initializes necessary graphic properties
        Graphics2D w = (Graphics2D) watermarked.getGraphics();
        w.drawImage(bufferedImage, 0, 0, null);
        AlphaComposite alphaChannel = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f);
        w.setComposite(alphaChannel);
        w.setColor(Color.GRAY);
        w.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
        FontMetrics fontMetrics = w.getFontMetrics();
        Rectangle2D rect = fontMetrics.getStringBounds(text, w);

        // calculate center of the image
        int centerX = (bufferedImage.getWidth() - (int) rect.getWidth()) / 2;
        int centerY = bufferedImage.getHeight() / 2;

        // add text overlay to the image
        w.drawString(text, centerX, centerY);
        w.dispose();



        if (initialImage.hasWaterMark()) {
            throw new CommandException(Messages.MESSAGE_HAS_WATERMARK);
        }

        initialImage.setWaterMark(true);

        if (this.isNewCommand) {
            this.isNewCommand = false;
            currentEdit.updateTempImage(watermarked);
            currentEdit.addCommand(this);
            currentEdit.displayTempImage();
        } else {
            currentEdit.updateTempImage(watermarked);
            initialImage.setWaterMark(true);
        }
        return new CommandResult(Messages.MESSAGE_WATERMARK_SUCCESS);
    }
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof WaterMarkCommand // instanceof handles nulls
                && (this.text.equals(((WaterMarkCommand) other).text)));
    }

    public String toString() {
        return "wm " + text;
    }
}
