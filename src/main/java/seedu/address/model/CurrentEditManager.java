package seedu.address.model;

import static seedu.address.commons.core.Config.ASSETS_FILEPATH;
import static seedu.address.commons.core.Config.TEMP_FILE;
import static seedu.address.commons.core.Config.TEMP_FILENAME;
import static seedu.address.commons.core.Config.TEMP_FILEPATH;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import java.util.List;
import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;

import com.sksamuel.scrimage.nio.JpegWriter;

import seedu.address.Notifier;
import seedu.address.logic.commands.Command;
import seedu.address.model.image.Image;


/**
 * Represents the in-memory model of the current image being edited on.
 */
public class CurrentEditManager implements CurrentEdit {
    private Image originalImage;
    private Image tempImage;
    private List<Command> tempList;
    private int tempIndex;

    /* @@author thamsimun */
    public CurrentEditManager() {
        this.originalImage = null;
        this.tempImage = null;
    }

    /**
     * Saves a copy of {@code image} to temp folder as temp_img.png and instantiate it as tempImage .
     */
    public void saveAsTemp(Image image) {
        saveIntoTempFolder(TEMP_FILENAME, image);
        setTempImage();
    }

    /**
     * Saves a copy of {@code image} to temp folder and instantiate it as originalImage.
     */
    public void saveAsOriginal(Image image) {
        saveIntoTempFolder(image.getName().toString(), image);
        setOriginalImage(image);
    }

    /**
     * Overwrites or creates an {@code image} named {@code filename} in temp folder.
     */
    public void saveIntoTempFolder(String filename, Image image) {
        try {
            File outputFile = new File(filename);
            File directory = new File(TEMP_FILEPATH);
            ImageIO.write(image.getBufferedImage(), image.getFileType(), outputFile);
            FileUtils.copyFileToDirectory(outputFile, directory, false);
            outputFile.delete();
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }
    public Image getTempImage() {
        return tempImage;
    }

    /**
     * Creates tempImage instance of temp_img.png located in temp folder.
     */
    public void setTempImage() {
        Image image = new Image(TEMP_FILE);
        this.tempImage = image;
    }

    /* @@author thamsimun */
    /**
     * Update tempImage instance of temp_img.png located in temp folder.
     */
    public void updateTempImage(com.sksamuel.scrimage.Image image) {
        tempList = tempImage.getCommandHistory();
        tempIndex = tempImage.getIndex();
        image.output(tempImage.getUrl(), new JpegWriter(100, true));
        tempImage = new Image(TEMP_FILE);
        tempImage.setIndex(tempIndex);
        tempImage.setHistory(tempList);
    }

    /* @@author kayheen */
    /**
     * Update tempImage instance of temp_img.png located in temp folder.
     */
    public void updateTempImage(BufferedImage bufferedimage) {
        tempList = tempImage.getCommandHistory();
        System.out.println(tempList);
        tempIndex = tempImage.getIndex();
        System.out.println(tempIndex);
        try {
            File outputFile = new File(TEMP_FILENAME);
            File directory = new File(TEMP_FILEPATH);
            ImageIO.write(bufferedimage, tempImage.getFileType(), outputFile);
            FileUtils.copyFileToDirectory(outputFile, directory, false);
            outputFile.delete();
        } catch (IOException e) {
            System.out.println(e.toString());
        }
        tempImage = new Image(TEMP_FILE);
        tempImage.setHistory(tempList);
        tempImage.setIndex(tempIndex);
    }

    /**
     * Creates originalImage instance of {@code image} located in temp_folder.
     */
    public void setOriginalImage(Image image) {
        Image originalImage = new Image(TEMP_FILEPATH + image.getName().toString());
        this.originalImage = originalImage;
    }

    public void displayTempImage() {
        Notifier.firePropertyChangeListener("import", null, tempImage.getUrl());
    }

    public void addCommand(Command command) {
        tempImage.addHistory(command);
    }

    public List<Command> getTempSubHistory() { return tempImage.getSubHistory(); }

    /**
     *
     */
    public void replaceTempWithOriginal() {
        //List<Command> tempList = tempImage.getCommandHistory();
        //int index = tempImage.getIndex();
        try {
            File newTemp = new File(originalImage.getUrl());
            File directory = new File(tempImage.getUrl());
            FileUtils.copyFile(newTemp, directory, false);
            BufferedImage tempBuffer = originalImage.getBufferedImage();
            tempImage.setBufferedImage(tempBuffer);
            //tempImage.setHistory(tempList);
            //tempImage.setIndex(index);
        }
        catch (IOException e) {
            System.out.println(e.toString());
        }
    }

    public boolean canUndoTemp() {
        return tempImage.canUndo();
    }

    public boolean canRedoTemp() {
        return tempImage.canRedo();
    }

    /**
     * Retrieves a list of all filenames in assets folder. Returns the list as String[].
     */
    public String[] getFileNames() {
        File file = new File(ASSETS_FILEPATH);
        return file.list();
    }



    /**
     * Saves tempImage to assetsFolder as {@code name} or original name if not specified.
     */
    public String saveToAssets(String name) {
        try {
            if (name.isEmpty()) {
                name = originalImage.getName().toString();
            }
            File outputFile = new File(name);
            File latestImage = new File(TEMP_FILE);
            File saveDirectory = new File(ASSETS_FILEPATH);
            latestImage.renameTo(outputFile);
            FileUtils.copyFileToDirectory(outputFile, saveDirectory, false);
            saveAsTemp(tempImage);
            saveAsOriginal(tempImage);
            outputFile.delete();
        } catch (IOException e) {
            System.out.println(e.toString());
        }
        return name;
    }


}
