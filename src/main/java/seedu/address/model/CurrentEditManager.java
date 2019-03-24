package seedu.address.model;

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

    public void addCommand(Command command) { }

    public void replaceTempWithOriginal() { }


}
