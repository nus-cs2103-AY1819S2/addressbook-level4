package seedu.address.ui;

import java.awt.image.BufferedImage;

import java.io.IOException;
import java.util.logging.Logger;

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
import seedu.address.commons.util.DrawTeethUtil;
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
            try {
                loadTeeth(newValue);
            } catch (IOException e) {
                logger.info(e.getMessage());
            }
        });
    }

    /**
     * Uses patient information to load teeth image
     */
    private void loadTeeth(Person person) throws IOException {
        StackPane stack = new StackPane();
        stack.setMaxWidth(Double.MAX_VALUE);
        stack.setMaxHeight(Double.MAX_VALUE);
        try {
            BufferedImage main = DrawTeethUtil.drawTeeth(((Patient) person).getTeeth().exportTeeth());
            Image fin = SwingFXUtils.toFXImage(main, null);
            ImageView test = new ImageView(fin);
            test.setPreserveRatio(true);
            test.setFitHeight(500);
            test.setX(80);
            test.setY(50);
            space.getChildren().add(test);

        } catch (IOException e) {
            logger.info("Error opening image file");
        }
    }

    private void clearTeeth() {
        space.getChildren().clear();
    }
}

