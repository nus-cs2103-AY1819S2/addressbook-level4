package seedu.address.commons.util;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import seedu.address.MainApp;

/**
 * A container for teeth specific utility functions.
 */
public class DrawTeethUtil {
    /**
     * Draws the teeth image using a patient's teeth information.
     * @param teeth The teeth list of a patient
     * @return The teeth image of a patient
     * @throws IOException if reading fails
     */
    public static BufferedImage drawTeeth(int[] teeth) throws IOException {
        String type;
        String basepath = System.getProperty("user.dir");
        try {
            InputStream imageFile = MainApp.class.getClassLoader()
                    .getResourceAsStream("images/teeth/BaseLayer.png");
            if (imageFile == null) {
                throw new IOException();
            }
            BufferedImage main = ImageIO.read(imageFile);
            for (int i = 0; i < teeth.length; i++) {
                if (teeth[i] > 0) {
                    if (teeth[i] == 1) {
                        type = "P_";
                    } else {
                        type = "A_";
                    }
                    InputStream layerFile = MainApp.class.getClassLoader()
                            .getResourceAsStream("images/teeth/" + type + (i + 1) + ".png");
                    if (layerFile == null) {
                        throw new IOException();
                    }
                    BufferedImage layer = ImageIO.read(layerFile);
                    Graphics g = main.getGraphics();
                    g.drawImage(layer, 0, 0, null);
                }
            }
            return main;
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
    }
}
