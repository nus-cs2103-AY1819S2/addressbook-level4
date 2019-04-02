package seedu.address.model;

import static seedu.address.commons.core.Config.ASSETS_FILEPATH;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
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

    private final String tempFilePath = "src/main/resources/temp/";
    private final String tempFileName = "src/main/resources/temp/temp_img.png";
    private final String originalFileName = "src/main/resources/temp/ori_img.png";
    private final String originalName = "ori_img.png";
    private final File directoryTo = new File(tempFilePath);

    private Image originalImage;
    private Image tempImage;
    private List<Command> tempList;
    private int tempIndex;
    private String originalImageName;

    /* @@author thamsimun */
    public CurrentEditManager() {
        tempExist();
        this.originalImage = null;
        this.tempImage = null;
        this.originalImageName = null;
    }

    /* @@author itszp */

    /**
     * Opens an image in FomoFoto.
     * This method makes two copies of the original image in temp folder.
     *
     * @param image Image to be edited.
     */
    public void openImage(Image image) {
        this.originalImageName = image.getName().getFullName();
        try {
            FileUtils.cleanDirectory(new File(tempFilePath));
            File file = new File(image.getUrl());
            FileUtils.copyFileToDirectory(file, directoryTo);
            File currentFile = new File(tempFilePath + this.originalImageName);
            File tempFile = new File(tempFileName);
            FileUtils.moveFile(currentFile, tempFile);
            FileUtils.copyFileToDirectory(file, directoryTo);
            File originalFile = new File(originalFileName);
            FileUtils.moveFile(currentFile, originalFile);

            this.originalImage = new Image(originalFile.getAbsolutePath());
            this.tempImage = new Image(tempFile.getAbsolutePath());
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Saves a copy of {@code image} to temp folder as temp_img.png and instantiate it as tempImage .
     */
    public void saveAsTemp(Image image) {
        saveIntoTempFolder(tempFileName, image);
        setTempImage();
    }

    /**
     * Saves a copy of {@code image} to temp folder as ori_img.png and instantiate it as originalImage.
     * Stores original name is originalImageName.
     */
    public void saveAsOriginal(Image image) {
        saveIntoTempFolder(originalName, image);
        this.originalImageName = image.getName().getFullName();
        setOriginalImage(image);
    }

    /**
     * Overwrites or creates an {@code image} named {@code filename} in temp folder.
     */
    public void saveIntoTempFolder(String filename, Image image) {
        try {
            File outputFile = new File(filename);
            File directory = new File(tempFilePath);
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
        Image image = new Image(tempFileName);
        this.tempImage = image;
    }
    /* @@author*/

    /* @@author thamsimun */

    /**
     * Update tempImage instance of temp_img.png located in temp folder.
     */
    public void updateTempImage(com.sksamuel.scrimage.Image image) {
        tempList = tempImage.getCommandHistory();
        tempIndex = tempImage.getIndex();
        image.output(tempImage.getUrl(), new JpegWriter(100, true));
        tempImage = new Image(tempFileName);
        tempImage.setIndex(tempIndex);
        tempImage.setHistory(tempList);
    }
    /* @@author*/

    /* @@author kayheen */

    /**
     * Update tempImage instance of temp_img.png located in temp folder.
     */
    public void updateTempImage(BufferedImage bufferedimage) {
        tempList = tempImage.getCommandHistory();
        tempIndex = tempImage.getIndex();
        try {
            File outputFile = new File(tempFileName);
            ImageIO.write(bufferedimage, tempImage.getFileType(), outputFile);
        } catch (IOException e) {
            System.out.println(e.toString());
        }
        tempImage = new Image(tempFileName);
        tempImage.setHistory(tempList);
        tempImage.setIndex(tempIndex);
    }

    /* @@author itszp */

    /**
     * Creates originalImage instance of {@code image} located in temp_folder.
     */
    public void setOriginalImage(Image image) {
        this.originalImage = new Image(tempFileName);
    }
    /* @@author*/

    public void displayTempImage() {
        Notifier.firePropertyChangeListener("import", null, tempImage.getUrl());
    }

    public void addCommand(Command command) {
        tempImage.addHistory(command);
    }

    public List<Command> getTempSubHistory() {
        return tempImage.getSubHistory();
    }

    /**
     *
     */
    public void replaceTempWithOriginal() {
        //List<Command> tempList = tempImage.getCommandHistory();
        //int index = tempImage.getIndex();
        try {
            File newTemp = new File(tempFilePath + originalName);
            File directory = new File(tempImage.getUrl());
            FileUtils.copyFile(newTemp, directory, false);
            BufferedImage tempBuffer = originalImage.getBufferedImage();
            tempImage.setBufferedImage(tempBuffer);
            //tempImage.setHistory(tempList);
            //tempImage.setIndex(index);
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }

    public boolean canUndoTemp() {
        return tempImage.canUndo();
    }

    public boolean canRedoTemp() {
        return tempImage.canRedo();
    }

    public void setUndoTemp() {
        tempImage.setUndo();
    }

    public void setRedoTemp() {
        tempImage.setRedo();
    }

    public Command getCommandTemp() {
        return tempImage.getCommand();
    }

    /**
     * Retrieves a list of all filenames in assets folder. Returns the list as String[].
     */
    public String[] getFileNames() {
        File file = new File(ASSETS_FILEPATH);
        return file.list();
    }
    public List<Command> getSubHistoryTemp() {
        return tempImage.getSubHistory();
    }

    /* @@author itszp */

    /**
     * Overwrites ori_img.png with tempImage. Sets originalImageName as {@code name}.
     */
    public void overwriteOriginal(String name) {
        saveIntoTempFolder("ori_img.png", tempImage);
        this.originalImageName = name;
        this.originalImage = new Image(tempFileName);
    }

    /**
     * Saves tempImage to assetsFolder as {@code name} or original name if not specified.
     */
    public String saveToAssets(String name) {
        try {
            if (name.isEmpty()) {
                name = this.originalImageName;
            }
            File outputFile = new File(name);
            File saveDirectory = new File(ASSETS_FILEPATH);
            ImageIO.write(tempImage.getBufferedImage(), tempImage.getFileType(), outputFile);
            FileUtils.copyFileToDirectory(outputFile, saveDirectory, false);
            outputFile.delete();
            tempImage.setHistory(new ArrayList<Command>());
            tempImage.setIndex(0);
        } catch (IOException e) {
            System.out.println(e.toString());
        }
        overwriteOriginal(name);
        return name;
    }
    public String getOriginalImageName() {
        return this.originalImageName;
    }
    /* @@author*/

    /* @@author Carrein */

    /**
     * Fires a notifier to update the EXIF pane of the Information Panel.
     */
    public void updateExif() {
        Notifier.firePropertyChangeListener("refreshDetails", null, this.tempImage);
    }

    /**
     * Helper method to clean up temp folder on application exit.
     */
    public void clearTemp() {
        File dir = new File(tempFilePath);
        for (File file : dir.listFiles()) {
            file.delete();
        }
        // Create a placeholder file so git can track the folder.
        try {
            File placeholder = new File(tempFilePath + "README.adoc");
            placeholder.getParentFile().mkdir();
            placeholder.createNewFile();

        } catch (IOException e) {
            System.out.println(e);
        }

    }

    /**
     * Checks if an asset folder exists on Album construction.
     * Creates a new asset folder if one does not exist.
     */
    public void tempExist() {
        File tempFolder = new File(tempFilePath);
        if (!tempFolder.exists()) {
            tempFolder.mkdir();
        }
    }

    public boolean tempImageExist() {
        return tempImage == null;
    }

    /* @@author*/
}
