package seedu.address.ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.Event;
import javafx.fxml.FXML;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.patient.Patient;
import seedu.address.model.person.Person;

/**
 * Panel containing an image representation of a patient's teeth
 */
public class TeethPanel extends UiPart<Region> {
    private static final String FXML = "TeethPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(TeethPanel.class);

    @FXML
    private AnchorPane space;

    public TeethPanel(ObservableValue<Person> selectedPerson) {
        super(FXML);
        getRoot().setOnKeyPressed(Event::consume);
        // Load patient's teeth when selected person changes.
        selectedPerson.addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                clearTeeth();
                return;
            }
            clearTeeth();
            loadTeeth(newValue);
        });
    }

    /**
     * Test
     */
    private void loadTeeth(Person person) {
        String type;
        StackPane stack = new StackPane();
        stack.setMaxWidth(Double.MAX_VALUE);
        stack.setMaxHeight(Double.MAX_VALUE);
        String basepath = System.getProperty("user.dir");
        int[] teethList = ((Patient) person).getTeeth().exportTeeth();
        File imgFile = new File(basepath + "/src/main/resources/images/teeth/BaseLayer.png");
        try {
            BufferedImage main = ImageIO.read(imgFile);
            for (int i = 0; i < teethList.length; i++) {
                if (teethList[i] > 0) {
                    if (teethList[i] == 1) {
                        type = "A";
                    } else {
                        type = "P";
                    }
                    String filepath = "/src/main/resources/images/teeth/" + type + "_" + (i + 1) + ".png";
                    String path = basepath + filepath;
                    File imgFile2 = new File(path);
                    BufferedImage layer = ImageIO.read(imgFile2);
                    Graphics g = main.getGraphics();
                    g.drawImage(layer, 0, 0, null);
                }
            }
            Image fin = SwingFXUtils.toFXImage(main, null);
            ImageView test = new ImageView(fin);
            test.setPreserveRatio(true);
            test.setFitHeight(500);
            stack.getChildren().add(test);
            space.getChildren().add(stack);
        } catch (IOException e) {
            new IOException("Error opening image file");
        }
    }
    private void clearTeeth() {
        space.getChildren().clear();
    }
}

