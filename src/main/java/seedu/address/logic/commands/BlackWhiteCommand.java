package seedu.address.logic.commands;

import static seedu.address.commons.core.Config.ASSETS_FILEPATH;
import static seedu.address.commons.core.Config.TEMP_FILEPATH;

import java.io.File;
import java.util.OptionalInt;

import com.sksamuel.scrimage.BufferedOpFilter;
import com.sksamuel.scrimage.Image;
import com.sksamuel.scrimage.filter.ThresholdFilter;
import com.sksamuel.scrimage.nio.JpegWriter;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Album;
import seedu.address.model.CurrentEdit;
import seedu.address.model.Model;

/**
 * This command allows users to apply the black/white filter on the image.
 */
public class BlackWhiteCommand extends Command {

    public static final String COMMAND_WORD = "bw";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Apply the black and white filter on the image with threshold given.\n"
        + "If threshold is not given, default threshold value is 127.\n"
        + "[THRESHOLD VALUE (int)] "
        + "and FILENAME.\n"
        + "Example: " + COMMAND_WORD + " cutedog.jpg"
        + "Example2: " + COMMAND_WORD + " 130 cutedog.jpg";
    private OptionalInt threshold;
    private String fileName;

    /**
     * Creates a ContrastCommand object.
     * @param threshold pixels lighter than this threshold becomes white and pixels darker than it becomes black.
     * @param fileName file name of the image
     */
    public BlackWhiteCommand(OptionalInt threshold, String fileName) {
        this.threshold = threshold;
        this.fileName = fileName;
    }

    @Override
    public CommandResult execute(CurrentEdit currentEdit, Album album, Model model, CommandHistory history) {
        if (threshold.isPresent()) {
            seedu.address.model.image.Image initialImage = new seedu.address
                .model.image.Image(ASSETS_FILEPATH + fileName);
            BufferedOpFilter bwFilter =
                new ThresholdFilter(threshold.getAsInt(), 0xffffff, 0x000000);
            Image.fromFile(new File(ASSETS_FILEPATH
                + fileName)).filter(bwFilter)
                .output(TEMP_FILEPATH + "sampleBlackWhite.jpg",
                    new JpegWriter(100, true));
        } else {
            seedu.address.model.image.Image initialImage = new seedu.address
                .model.image.Image(ASSETS_FILEPATH + fileName);
            BufferedOpFilter bwFilter =
                new ThresholdFilter(127, 0xffffff, 0x000000);
            Image.fromFile(new File(ASSETS_FILEPATH
                + fileName)).filter(bwFilter)
                .output(TEMP_FILEPATH + "sampleBlackWhite.jpg",
                    new JpegWriter(100, true));
        }
        return new CommandResult(Messages.MESSAGE_BLACKWHITE_SUCCESS);
    }
}
