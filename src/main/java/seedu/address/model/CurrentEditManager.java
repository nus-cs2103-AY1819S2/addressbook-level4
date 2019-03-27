package seedu.address.model;

import com.sksamuel.scrimage.nio.JpegWriter;

import seedu.address.Notifier;
import seedu.address.logic.commands.Command;
import seedu.address.model.image.Image;
import static seedu.address.commons.core.Config.TEMP_FILEPATH;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;

/**
 * Represents the in-memory model of the current image being edited on.
 */
public class CurrentEditManager implements CurrentEdit {
    private Image originalImage;
    private Image tempImage;

    public void createTempinTempFolder() { }

    public void saveOriginalinTempFolder() { }

    public Image getTempImage() {
        return tempImage;
    }

    public void setTempImage(Image image) { }

    public void setTempImage(com.sksamuel.scrimage.Image image) {
        image.output(tempImage.getUrl(),
            new JpegWriter(100, true));
    }

    public void displayTempImage() {
        Notifier.firePropertyChangeListener("import", null, tempImage.getUrl());
    }

    public void addCommand(Command command) {
        tempImage.addHistory(command);
    }

    /**
     *
     */
    public void replaceTempWithOriginal() {
        List<Command> tempList = tempImage.getCommandHistory();
        int index = tempImage.getIndex();
        try {
            File newTemp = new File(originalImage.getUrl());
            File directory = new File(TEMP_FILEPATH);
            FileUtils.copyFileToDirectory(newTemp, directory, false);
            tempImage = originalImage;
            tempImage.setHistory(tempList);
            tempImage.setIndex(index);
        }
        catch (IOException e) {
            System.out.println(e.toString());
        }
    }


}
