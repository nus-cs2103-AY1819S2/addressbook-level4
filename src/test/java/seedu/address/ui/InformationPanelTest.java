package seedu.address.ui;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalMedicines.PARACETAMOL;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.InformationPanelHandle;
import javafx.beans.property.SimpleObjectProperty;
import seedu.address.model.medicine.Medicine;

public class InformationPanelTest extends GuiUnitTest {
    private SimpleObjectProperty<Medicine> selectedMedicine = new SimpleObjectProperty<>();
    private InformationPanel informationPanel;
    private InformationPanelHandle informationPanelHandle;

    @Before
    public void setUp() {
        guiRobot.interact(() -> informationPanel = new InformationPanel(selectedMedicine));
        uiPartRule.setUiPart(informationPanel);

        informationPanelHandle = new InformationPanelHandle(informationPanel.getRoot());
    }

    @Test
    public void display() {
        // default
        assertFalse(informationPanelHandle.isBatchTableLoaded());

        // load table of selected medicine
        guiRobot.interact(() -> selectedMedicine.set(PARACETAMOL));
        assertTrue(informationPanelHandle.isBatchTableLoaded());

        // deselect medicine
        guiRobot.interact(() -> selectedMedicine.set(null));
        assertFalse(informationPanelHandle.isBatchTableLoaded());
    }
}
