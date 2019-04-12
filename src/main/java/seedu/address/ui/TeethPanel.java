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

    public TeethPanel(ObservableValue<Person> givenPerson, boolean isStat, Double size) {
        super(FXML);
        getRoot();
        try {
            loadTeeth(givenPerson.getValue());
            ImageView temp = (ImageView) space.getChildren().get(space.getChildren().size() - 1);
            temp.xProperty().unbind();
            temp.fitHeightProperty().unbind();
            temp.setFitHeight(size);
        } catch (IOException e) {
            logger.info(e.getMessage());
        }
    }

    /**
     * Uses patient information to load teeth image
     */
    private void loadTeeth(Person person) throws IOException {
        try {
            BufferedImage main = DrawTeethUtil.drawTeeth(((Patient) person).getTeeth().exportTeeth());
            Image fin = SwingFXUtils.toFXImage(main, null);
            ImageView teethImage = new ImageView(fin);
            teethImage.xProperty().bind(space.widthProperty().subtract(teethImage.fitWidthProperty()).divide(2));
            teethImage.setPreserveRatio(true);
            teethImage.setFitWidth(space.getWidth() / 1.35);
            teethImage.fitHeightProperty().bind(space.heightProperty());
            space.getChildren().add(teethImage);

        } catch (IOException e) {
            logger.info("Error opening image file");
        }
    }

    private void clearTeeth() {
        space.getChildren().clear();
    }
}

