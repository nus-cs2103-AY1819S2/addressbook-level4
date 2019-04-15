package seedu.address.ui;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalMedicines.LEVOTHYROXINE;
import static seedu.address.testutil.TypicalMedicines.PARACETAMOL;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.InformationPanelHandle;
import javafx.beans.property.SimpleObjectProperty;
import seedu.address.commons.core.InformationPanelSettings;
import seedu.address.commons.core.InformationPanelSettings.SortDirection;
import seedu.address.commons.core.InformationPanelSettings.SortProperty;
import seedu.address.model.medicine.Medicine;

public class InformationPanelTest extends GuiUnitTest {
    private SimpleObjectProperty<Medicine> selectedMedicine = new SimpleObjectProperty<>();
    private SimpleObjectProperty<InformationPanelSettings> informationPanelSettings = new SimpleObjectProperty<>();
    private InformationPanel informationPanel;
    private InformationPanelHandle informationPanelHandle;

    @Before
    public void setUp() {
        informationPanelSettings.setValue(new InformationPanelSettings());
        guiRobot.interact(() -> informationPanel = new InformationPanel(selectedMedicine, informationPanelSettings));
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
        informationPanelHandle.assertTableCorrect(selectedMedicine.get(), informationPanelSettings.getValue());

        // deselect medicine
        guiRobot.interact(() -> selectedMedicine.set(null));
        assertFalse(informationPanelHandle.isBatchTableLoaded());

        // change settings without loading the table
        guiRobot.interact(() -> informationPanelSettings.set(new InformationPanelSettings(SortProperty.EXPIRY,
                SortDirection.DESCENDING)));
        assertFalse(informationPanelHandle.isBatchTableLoaded());

        // Select another medicine
        guiRobot.interact(() -> selectedMedicine.set(LEVOTHYROXINE));
        assertTrue(informationPanelHandle.isBatchTableLoaded());
        informationPanelHandle.assertTableCorrect(selectedMedicine.get(), informationPanelSettings.getValue());

        // Select same medicine and change settings without deselecting
        guiRobot.interact(() -> selectedMedicine.set(LEVOTHYROXINE));
        assertTrue(informationPanelHandle.isBatchTableLoaded());
        guiRobot.interact(() -> informationPanelSettings.set(new InformationPanelSettings(SortProperty.QUANTITY,
                SortDirection.DESCENDING)));
        informationPanelHandle.assertTableCorrect(selectedMedicine.get(), informationPanelSettings.getValue());
    }
}
