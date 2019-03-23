/* @@author Carrein */

package seedu.address.ui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.text.TextFlow;
import seedu.address.Notifier;

/**
 * The initial text panel for FomoFoto.
 */
public class InitPanel extends UiPart<Region> implements PropertyChangeListener {

    private static final String FXML = "InitPanel.fxml";
    private List<Node> nodeList;

    @FXML
    private HBox initView;

    @FXML
    private TextFlow initText;

    public InitPanel() {
        super(FXML);
        nodeList = initText.getChildren();
        Notifier.addPropertyChangeListener(this);
    }

    /**
     * Hide the initText if an image is being displayed.
     *
     * @param event
     */
    public void propertyChange(PropertyChangeEvent event) {
        if (event.getPropertyName().equals("import")) {
            for (Node n : nodeList) {
                n.setVisible(false);
            }
        }
    }

    //TODO method to display initText if clear command is called
}
