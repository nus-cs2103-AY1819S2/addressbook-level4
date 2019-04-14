package seedu.address.model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;

import com.google.common.io.Files;
import com.sksamuel.scrimage.nio.JpegWriter;

import seedu.address.logic.commands.Command;
import seedu.address.model.image.Image;
import seedu.address.ui.Notifier;


/**
 * Represents the in-memory model of the current image being edited on.
 */
public class CurrentEditManager implements CurrentEdit {

    private final String editName = "temp_img.png";
    private final String originalName = "ori_img.png";
    private final String editFilePath;
    private final String editFileName;
    private final String originalFileName;
    private final File directoryTo;

    private Image originalImage;
    private Image tempImage;
    private List<Command> tempList;
    private int tempIndex;
    private String originalImageName;

    /* @@author thamsimun */
    public CurrentEditManager() {
        this.editFilePath = generateEdit();
        this.editFileName = editFilePath + editName;
        this.originalFileName = editFilePath + originalName;
        this.directoryTo = new File(editFilePath);
        this.originalImage = null;
        this.tempImage = null;
        this.originalImageName = null;
    }
    /* @@author*/

    /* @@author itszp */
    /**
     * Opens an image in FomoFoto.
     * This method creates two copies of the original image in temp folder and instantiates them.
     *
     * @param image is the image to be opened.
     */
    public void openImage(Image image) {
        this.originalImageName = image.getName().getFullName();
        try {
            FileUtils.cleanDirectory(new File(editFilePath));
            File file = new File(image.getUrl());
            FileUtils.copyFileToDirectory(file, directoryTo);
            File currentFile = new File(editFilePath + this.originalImageName);
            File tempFile = new File(editFileName);
            FileUtils.moveFile(currentFile, tempFile);
            FileUtils.copyFileToDirectory(file, directoryTo);
            File originalFile = new File(originalFileName);
            FileUtils.moveFile(currentFile, originalFile);

            this.originalImage = new Image(originalFile.getAbsolutePath());
            this.tempImage = new Image(tempFile.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves the image into tempFolder.
     *
     * @param image is the new image.
     * @param filename is the name of the new image file.
     */
    public void saveIntoTempFolder(String filename, Image image) {
        try {
            File outputFile = new File(filename);
            File directory = new File(editFilePath);
            ImageIO.write(image.getBufferedImage(), image.getFileType(), outputFile);
            FileUtils.copyFileToDirectory(outputFile, directory, false);
            outputFile.delete();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the instance of the tempImage.
     */
    public Image getTempImage() {
        return tempImage;
    }

    /**
     * Creates tempImage instance of temp_img.png located in temp folder.
     */
    public void setTempImage() {
        Image image = new Image(editFileName);
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
        boolean hasWaterMark = tempImage.hasWaterMark();
        image.output(tempImage.getUrl(), new JpegWriter(100, true));
        tempImage = new Image(editFileName);
        tempImage.setIndex(tempIndex);
        tempImage.setHistory(tempList);
        tempImage.setWaterMark(hasWaterMark);
    }
    /* @@author*/

    /* @@author kayheen */

    /**
     * Update tempImage instance of temp_img.png located in temp folder.
     */
    public void updateTempImage(BufferedImage bufferedimage) {
        tempList = tempImage.getCommandHistory();
        tempIndex = tempImage.getIndex();
        boolean hasWaterMark = tempImage.hasWaterMark();
        try {
            File outputFile = new File(editFileName);
            ImageIO.write(bufferedimage, tempImage.getFileType(), outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        tempImage = new Image(editFileName);
        tempImage.setHistory(tempList);
        tempImage.setIndex(tempIndex);
        tempImage.setWaterMark(hasWaterMark);
    }
    /* @@author */
    public void displayTempImage() {
        Notifier.firePropertyChangeListener("import", null, tempImage.getUrl());
    }

    /* @@author randytqw */
    /**
     * Adds an executed command into Image history.
     * @param command Command to be added.
     */
    public void addCommand(Command command) {
        tempImage.addHistory(command);
        updateHistory();
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
            File newTemp = new File(editFilePath + originalName);
            File directory = new File(tempImage.getUrl());
            FileUtils.copyFile(newTemp, directory, false);
            BufferedImage tempBuffer = originalImage.getBufferedImage();
            tempImage.setBufferedImage(tempBuffer);
            //tempImage.setHistory(tempList);
            //tempImage.setIndex(index);
        } catch (IOException e) {
            e.printStackTrace();
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

    /* @@author */
    /* @@author itszp */
    /**
     * Creates originalImage instance of {@code image} located in temp_folder.
     */
    public void setOriginalImage(Image image) {
        this.originalImage = new Image(editFileName);
    }

    /**
     * Overwrites ori_img.png with tempImage. Sets originalImageName as {@code name}.
     */
    public void overwriteOriginal(String name) {
        saveIntoTempFolder("ori_img.png", tempImage);
        this.originalImageName = name;
        this.originalImage = new Image(editFileName);
    }

    /**
     * Resets tempImage history.
     */
    public void deleteHistory() {
        tempImage.setHistory(new ArrayList<Command>());
        tempImage.setIndex(0);
    }

    /**
     * Returns the original name of the image.
     */
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
     * Fires a notifier to update the EXIF pane of the Information Panel.
     */
    public void updateHistory() {
        Notifier.firePropertyChangeListener("refreshHistory", null, tempImage.getSubHistory());
    }

    /**
     * Helper method to clean up temp folder on application exit.
     */
    public void clearTemp() {
        File dir = new File(editFilePath);
        for (File file : dir.listFiles()) {
            file.delete();
        }
    }

    /**
     * Generates a temp edit folder in the system to store edited images.
     * Temp edit folder is deleted on termination of the program by means of shutdown hook.
     *
     * @return Absolute path to generated temp folder.
     */
    public String generateEdit() {
        String tempPath = null;
        try {
            File editFolder = Files.createTempDir();
            editFolder.deleteOnExit();
            tempPath = editFolder.getAbsolutePath() + File.separator;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tempPath;
    }

    public boolean tempImageDoNotExist() {
        return tempImage == null;
    }
    /* @@author*/
}
