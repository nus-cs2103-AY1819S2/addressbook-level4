package seedu.address.logic.commands;

import java.io.File;

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
        + "and 'subtract' indicates a decrease in contrast) and FILENAME.\n"
        + "Example: " + COMMAND_WORD + " add" + " cutedog.jpg";

    private String operator;
    private String fileName;


    /**
     * Creates a ContrastCommand object.
     * @param operator is add/subtract
     * @param image is the file name of the image.
     */
    public ContrastCommand(String operator, String image) {
        this.operator = operator;
        fileName = image;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history)
        throws CommandException {
        double contrastAmount = 0.0;

        if (this.operator.equals("add")) {
            contrastAmount = 20.0;
        } else if (this.operator.equals("subtract")) {
            contrastAmount = -20.0;
        }

        try {
            seedu.address.model.image.Image image = new seedu.address.model
                .image.Image("src/main/resources/assets/" + fileName);
            BufferedOpFilter contrastFilter =
                new ContrastFilter(contrastAmount);
            Image.fromFile(new File("src/main/resources/assets/"
                + fileName)).filter(contrastFilter)
                .output("src/main/resources/assets/sampleContrast.jpg",
                    new JpegWriter(0, true));

        } catch (IllegalArgumentException x) {
            throw new CommandException(Messages.MESSAGE_FILE_DOES_NOT_EXIST);
        }

        seedu.address.model.image.Image finalImage = new seedu.address.model
            .image.Image("src/main/resources/assets/sampleContrast.jpg");
        model.displayImage(finalImage);
        return new CommandResult(Messages.MESSAGE_CONTRAST_SUCCESS);
    }
}
