//package seedu.address.ui;
//
//import guitests.guihandles.BrowserPanelHandle;
//import javafx.beans.property.SimpleObjectProperty;
//import seedu.address.model.person.Doctor;
//import seedu.address.model.person.Patient;
//
//import java.net.URL;
//
//import javax.print.Doc;
//
//import org.junit.Before;
//import org.junit.Test;
//
//import static guitests.guihandles.WebViewUtil.waitUntilBrowserLoaded;
//import static org.junit.Assert.assertEquals;
//import static seedu.address.testutil.TypicalDoctors.ALVINA;
//import static seedu.address.testutil.TypicalPatients.ALICE;
//
//public class DoctorBrowserPanelTest extends GuiUnitTest {
//    private SimpleObjectProperty<Doctor> selectedDoctor = new SimpleObjectProperty<>();
//    private DoctorBrowserPanel doctorBrowserPanel;
//    private BrowserPanelHandle browserPanelHandle;
//
//    @Before
//    public void setUp() {
//        guiRobot.interact(() -> doctorBrowserPanel = new DoctorBrowserPanel(selectedDoctor));
//        uiPartRule.setUiPart(doctorBrowserPanel);
//
//        browserPanelHandle = new BrowserPanelHandle(doctorBrowserPanel.getRoot());
//    }
//
//    @Test
//    public void display() throws Exception {
//        // default web page
//        assertEquals(DoctorBrowserPanel.DEFAULT_DOCTOR_PAGE, "");
//
//        // associated web page of a doctor
//        guiRobot.interact(() -> selectedDoctor.set(ALVINA));
//
//        //        waitUntilBrowserLoaded(browserPanelHandle);
//        //        assertEquals(, browserPanelHandle.getLoadedUrl());
//    }
//}
