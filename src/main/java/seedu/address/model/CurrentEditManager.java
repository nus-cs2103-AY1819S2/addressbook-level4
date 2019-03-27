package seedu.address.model;

import static seedu.address.commons.core.Config.ASSETS_FILEPATH;
import static seedu.address.commons.core.Config.TEMP_FILE;
import static seedu.address.commons.core.Config.TEMP_FILENAME;
import static seedu.address.commons.core.Config.TEMP_FILEPATH;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;

/*
import com.sksamuel.scrimage.nio.JpegWriter;
*/

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

    /**
     * Saves a copy of tempImage to temp folder.
     */
    public void saveTemp() {
        saveIntoTempFolder(TEMP_FILENAME, tempImage);
    }

    /**
     * Saves a copy to originalImage to temp folder.
     */
    public void saveOriginal() {
        saveIntoTempFolder(originalImage.getName().toString(), originalImage);
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
     * Creates tempImage instance of image and saves a copy to temp folder.
     */
    public void setTempImage(Image image) {
        this.tempImage = image;
        saveTemp();
    }

    public void setTempImage(com.sksamuel.scrimage.Image image) {
        image.output(tempImage.getUrl(),
            new JpegWriter(100, true));
    }

    /**
     * Creates originalImage instance of {@code image} and saves a copy to temp folder.
     */
    public void setOriginalImage(Image image) {
        this.originalImage = image;
        saveOriginal();
    }

    public void displayTempImage() {
        Notifier.firePropertyChangeListener("import", null, tempImage.getUrl());
    }

    public void addCommand(Command command) { }

    public void replaceTempWithOriginal() { }

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
            setTempImage(tempImage);
            setOriginalImage(tempImage);
            outputFile.delete();
        } catch (IOException e) {
            System.out.println(e.toString());
        }
        return name;
    }


}
