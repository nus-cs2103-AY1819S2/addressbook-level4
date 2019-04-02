package seedu.address.model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;

import com.google.common.io.Files;
import com.sksamuel.scrimage.nio.JpegWriter;

import seedu.address.Notifier;
import seedu.address.logic.commands.Command;
import seedu.address.model.image.Image;

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
        this.originalFileName = editFileName + originalName;
        this.directoryTo = new File(editFilePath);
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
            System.out.println(e);
        }
    }

    /**
     * Saves a copy of {@code image} to temp folder as temp_img.png and instantiate it as tempImage .
     */
    public void saveAsTemp(Image image) {
        saveIntoTempFolder(editFileName, image);
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
            File directory = new File(editFilePath);
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
        image.output(tempImage.getUrl(), new JpegWriter(100, true));
        tempImage = new Image(editFileName);
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
            File outputFile = new File(editFileName);
            ImageIO.write(bufferedimage, tempImage.getFileType(), outputFile);
        } catch (IOException e) {
            System.out.println(e.toString());
        }
        tempImage = new Image(editFileName);
        tempImage.setHistory(tempList);
        tempImage.setIndex(tempIndex);
    }

    /* @@author itszp */

    /**
     * Creates originalImage instance of {@code image} located in temp_folder.
     */
    public void setOriginalImage(Image image) {
        this.originalImage = new Image(editFileName);
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
            File newTemp = new File(editFilePath + originalName);
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
        this.originalImage = new Image(editFileName);
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
        File dir = new File(editFilePath);
        for (File file : dir.listFiles()) {
            file.delete();
        }
        // Create a placeholder file so git can track the folder.
        try {
            File placeholder = new File(editFilePath + "README.adoc");
            placeholder.getParentFile().mkdir();
            placeholder.createNewFile();
        } catch (IOException e) {
            System.out.println(e);
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
            System.out.println(e);
        }
        return tempPath;
    }

    public boolean tempImageExist() {
        return tempImage == null;
    }
    /* @@author*/
}
