package seedu.address.ui;

import org.junit.Before;

import guitests.guihandles.BrowserPanelHandle;
import javafx.beans.property.SimpleObjectProperty;
import seedu.address.model.person.patient.Patient;
import seedu.address.ui.patient.PatientInfoPanel;

public class PatientInfoPanelTest extends GuiUnitTest {
    private SimpleObjectProperty<Patient> selectedPatient = new SimpleObjectProperty<>();
    private PatientInfoPanel patientInfoPanel;
    private BrowserPanelHandle browserPanelHandle;

    @Before
    public void setUp() {
        guiRobot.interact(() -> patientInfoPanel = new PatientInfoPanel(selectedPatient));
        uiPartRule.setUiPart(patientInfoPanel);

        browserPanelHandle = new BrowserPanelHandle(patientInfoPanel.getRoot());
    }


    /*
    @Test
    public void display() throws Exception {
        // default web page
        assertEquals(PatientInfoPanel.DEFAULT_PAGE, browserPanelHandle.getLoadedUrl());

        // associated web page of a patient
        guiRobot.interact(() -> selectedPatient.set(ALICE));
        URL expectedPatientUrl =
                new URL(PatientInfoPanel.SEARCH_PAGE_URL + ALICE.getName().fullName.replaceAll(" ", "%20"));


        waitUntilBrowserLoaded(browserPanelHandle);
        assertEquals(expectedPatientUrl, browserPanelHandle.getLoadedUrl());
    }
    */
}
