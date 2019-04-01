package seedu.address.commons.util;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

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
            File imgFile = new File(basepath + "/src/main/resources/images/teeth/BaseLayer.png");
            BufferedImage main = ImageIO.read(imgFile);
            for (int i = 0; i < teeth.length; i++) {
                if (teeth[i] > 0) {
                    if (teeth[i] == 1) {
                        type = "P";
                    } else {
                        type = "A";
                    }
                    String filepath = "/src/main/resources/images/teeth/" + type + "_" + (i + 1) + ".png";
                    String path = basepath + filepath;
                    File imgFile2 = new File(path);
                    BufferedImage layer = ImageIO.read(imgFile2);
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
