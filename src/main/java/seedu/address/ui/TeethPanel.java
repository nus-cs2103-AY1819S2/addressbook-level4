package seedu.address.ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;
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
import seedu.address.model.person.Person;

/**
 * Panel containing an image representation of a patient's teeth
 */
public class TeethPanel extends UiPart<Region> {
    private static final String FXML = "TeethPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(TaskListPanel.class);

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
    }
    private void clearTeeth() {
    }
}

