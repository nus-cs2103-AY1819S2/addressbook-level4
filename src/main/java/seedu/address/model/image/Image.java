/* @@author Carrein */
package seedu.address.model.image;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.SetPresetCommand;
import seedu.address.logic.commands.WaterMarkCommand;

/**
 * Represents an Image in FomoFoto.
 */
public class Image {

    /**
     * Data fields.
     */
    private Name name;
    private Height height;
    private Width width;
    private Size size;
    private BufferedImage buffer;
    private String url;
    private String fileType;
    private Metadata metadata;
    private List<Command> commandHistory;
    private int index;
    private boolean hasWaterMark;

    /**
     * Every field must be present and not null.
     */
    public Image(String url) {
        requireAllNonNull(url);
        File file = new File(url);
        try {
            this.buffer = ImageIO.read(file);
            this.metadata = ImageMetadataReader.readMetadata(file);
            buffer = ImageIO.read(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.url = url;
        this.fileType = FilenameUtils.getExtension(url);
        this.size = new Size(String.valueOf(file.length()));
        this.name = new Name(FilenameUtils.getBaseName(url),
                FilenameUtils.getExtension(url), FilenameUtils.getName(url));
        this.width = new Width(String.valueOf(buffer.getWidth()));
        this.height = new Height(String.valueOf(buffer.getHeight()));
        commandHistory = new ArrayList<>();
        index = 0;
        this.hasWaterMark = false;
    }

    public Image(File file) {
        requireAllNonNull(file);
        try {
            this.buffer = ImageIO.read(file);
            this.metadata = ImageMetadataReader.readMetadata(file);
            buffer = ImageIO.read(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.url = file.getAbsolutePath();
        this.fileType = FilenameUtils.getExtension(file.getName());
        this.size = new Size(String.valueOf(file.length()));
        this.name = new Name(FilenameUtils.getBaseName(url),
                FilenameUtils.getExtension(url), FilenameUtils.getName(url));
        this.width = new Width(String.valueOf(buffer.getWidth()));
        this.height = new Height(String.valueOf(buffer.getHeight()));
        commandHistory = new ArrayList<>();
        index = 0;
        this.hasWaterMark = false;
    }

    public Height getHeight() {
        return height;
    }

    public Width getWidth() {
        return width;
    }

    public Name getName() {
        return name;
    }

    public BufferedImage getBufferedImage() {
        return buffer;
    }

    public String getUrl() {
        return url;
    }

    public String getFileType() {
        return fileType;
    }

    public Size getSize() {
        return size;
    }

    public boolean hasWaterMark() {
        return hasWaterMark;
    }

    /**
     * This method changes the hasWaterMark field so that it reflects the current state of the tempImage.
     *
     * @param x the new value to set
     */
    public void setWaterMark(boolean x) {
        hasWaterMark = x;
    }
    /* @@author randytqw */

    public List<Command> getCommandHistory() {
        return commandHistory;
    }

    public List<Command> getSubHistory() {
        return commandHistory.subList(0, index);
    }

    /**
     * Adds a new transformation command into commandHistory
     * if Undo command was last called command, remove all edits after previous undo.
     *
     * @param command command to be added
     */
    public void addHistory(Command command) {
        if (index < commandHistory.size()) {
            commandHistory = commandHistory.subList(0, index);
        }
        commandHistory.add(command);
        index++;
    }

    public void setBufferedImage(BufferedImage buffer) {
        this.buffer = buffer;
    }

    public Command getCommand() {
        return commandHistory.get(index);
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setHistory(List history) {
        this.commandHistory = history;
    }

    public void setUndo() {
        index--;
        if (commandHistory.get(index) instanceof WaterMarkCommand) {
            setWaterMark(false);
        }

        if (commandHistory.get(index) instanceof SetPresetCommand) {
            SetPresetCommand presetCommand = (SetPresetCommand) (commandHistory.get(index));
            if (presetCommand.hasWaterMarkCommand()) {
                setWaterMark(false);
            }
        }
    }

    public void setRedo() {
        if (commandHistory.get(index) instanceof WaterMarkCommand) {
            setWaterMark(true);
        }

        if (commandHistory.get(index) instanceof SetPresetCommand) {
            SetPresetCommand presetCommand = (SetPresetCommand) (commandHistory.get(index));
            if (presetCommand.hasWaterMarkCommand()) {
                setWaterMark(true);
            }
        }
        index++;
    }

    public boolean canUndo() {
        return index > 0;
    }

    public boolean canRedo() {
        return index < commandHistory.size();
    }

    /* @@author */

    /**
     * A basic representation of the Image's fields.
     * EG:
     * Name: sample.png
     * Height: 1600
     * Width: 1600
     *
     * @return the fields of the image.
     */
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("====================")
                .append("\nName: ")
                .append(getName())
                .append("\nHeight: ")
                .append(getHeight())
                .append("\nWidth: ")
                .append(getWidth())
                .append("\nFormat: ")
                .append(getFileType())
                .append("\n====================");
        return builder.toString();
    }

    /**
     * Returns metadata for image in an ArrayList of Strings.
     *
     * @return list of meta data tags.
     */
    public List<String> getMetadataList() {
        List<String> tempList = new ArrayList<>();
        for (Directory directory : metadata.getDirectories()) {
            for (Tag tag : directory.getTags()) {
                tempList.add(tag.toString());
            }
        }
        return tempList;
    }
}
