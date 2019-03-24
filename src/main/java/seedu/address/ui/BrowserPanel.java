package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import java.net.URL;
import java.util.List;
import java.util.logging.Logger;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.pdf.Pdf;

/**
 * The Browser Panel of the App.
 */
public class BrowserPanel extends UiPart<Region> {

    public static final URL DEFAULT_PAGE =
            requireNonNull(MainApp.class.getResource(FXML_FILE_FOLDER + "default.html"));
    public static final String SEARCH_PAGE_URL = "https://se-education.org/dummy-search-page/?name=";

    private static final String FXML = "BrowserPanel.fxml";

    @FXML
    private VBox initial;

    @FXML
    private Label deadlines;

    @FXML
    private VBox selected;

    private final Logger logger = LogsCenter.getLogger(getClass());

//    @FXML
//    private WebView browser;

    public BrowserPanel(ObservableValue<Pdf> selectedPerson, List<Pdf> duePdfs) {
        super(FXML);
//        welcome.setStyle("-fx-text-fill: white;"
//                + "-fx-padding: 1;"
//                + "-fx-label-padding: 0;"
//                + "-fx-font-size: 25");

        // To prevent triggering events for typing inside the loaded Web page.
        /*getRoot().setOnKeyPressed(Event::consume);*/
        // Load pdf page when selected pdf changes.
        this.updateDefaultPage(duePdfs);

        selectedPerson.addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                this.updateDefaultPage(duePdfs);
                initial.setVisible(true);
                selected.setVisible(false);
                //loadDefaultPage();
                return;
            }

            initial.setVisible(false);
            selected.setVisible(true);
            //loadPersonPage(newValue);
        });

        //loadDefaultPage();
    }

    public void updateDefaultPage(List<Pdf> duePdfs) {
        StringBuilder sb = new StringBuilder();

        for (Pdf pdf : duePdfs) {
            sb.append("\u2022 ");
            sb.append(pdf.getName().getFullName());
            sb.append("\r");
        }

        deadlines.setText(sb.toString());
    }

    /*private void loadPersonPage(Pdf pdf) {
        loadPage(SEARCH_PAGE_URL + pdf.getName().getFullName());
    }

    public void loadPage(String url) {
        Platform.runLater(() -> browser.getEngine().load(url));
    }

    *//**
     * Loads a default HTML file with a background that matches the general theme.
     *//*
    private void loadDefaultPage() {
        loadPage(DEFAULT_PAGE.toExternalForm());
    }*/

}
