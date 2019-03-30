/* @@author thamsimun */
package seedu.address.model;

import java.awt.image.BufferedImage;

import seedu.address.logic.commands.Command;
import seedu.address.model.image.Image;

/**
 * The API of the CurrentEdit component.
 */
public interface CurrentEdit {

    void saveAsTemp(Image image);

    void saveAsOriginal(Image image);

    void overwriteOriginal(String name);

    void saveIntoTempFolder(String filename, Image image);

    Image getTempImage();

    void setTempImage();

    void updateTempImage(com.sksamuel.scrimage.Image image);

    void updateTempImage(BufferedImage image);

    void setOriginalImage(Image image);

    void displayTempImage();

    void addCommand(Command command);

    void replaceTempWithOriginal();

    String[] getFileNames();

    String saveToAssets(String name);
}
