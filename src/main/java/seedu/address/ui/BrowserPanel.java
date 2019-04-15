package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;

/**
 * The Browser Panel of the App.
 */
public class BrowserPanel extends UiPart<Region> {

    public static final URL DEFAULT_PAGE =
            requireNonNull(MainApp.class.getResource(FXML_FILE_FOLDER + "default.html"));
    public static final URL PLACEHOLDER_PAGE = requireNonNull(MainApp.class.getResource(FXML_FILE_FOLDER
            + "html/placeholder.html"));

    // for testing purposes
    public static final URL BENSONMEIER_PAGE = requireNonNull(MainApp.class.getResource(FXML_FILE_FOLDER
            + "html/bensonmeier.html"));

    private static final String FXML = "BrowserPanel.fxml";
    private static final String DELIM = "%20";

    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private WebView browser;

    public BrowserPanel(ObservableValue<Person> selectedPerson) {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);

        // Load person page when selected person changes.
        selectedPerson.addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                loadDefaultPage();
                return;
            }

            Path htmlPath = Paths.get("data/html").toAbsolutePath();
            Path cvImgPath = Paths.get("data/html/cv").toAbsolutePath();
            String fileName = selectedPerson.getValue().getName().toString().trim().toLowerCase()
                    .replace(" ", "") + ".html";
            String url = "data/html/" + fileName;

            if (Files.exists(htmlPath)) {
                if (Files.exists(cvImgPath)) {
                    if (Files.exists(Paths.get(url))) {
                        loadPage("file:/" + Paths.get(url).toAbsolutePath().toString()
                                .replace("\\", "/"));
                    } else {
                        loadCvPage(fileName);
                    }
                } else {
                    new File("./data/html/cv").mkdirs();
                    loadCvPage(fileName);
                }
            } else {
                new File("./data/html").mkdirs();
                new File("./data/html/cv").mkdirs();
                loadCvPage(fileName);
            }
        });

        loadDefaultPage();
    }

    public void loadPage(String url) {
        Platform.runLater(() -> browser.getEngine().load(url));
    }

    /**
     * Loads a default HTML file with a background that matches the general theme.
     */
    private void loadDefaultPage() {
        loadPage(DEFAULT_PAGE.toExternalForm());
    }

    /**
     * removes p:, [, ] and , from string and replaces spaces with a certain delimiter
     */
    private String trimToSearchFormat(String input) {
        String output = input.replace("p:", "")
                .replace("[", "")
                .replace("]", "")
                .replace(",", "")
                .replace(" ", DELIM);
        return output;
    }

    /**
     * checks the files in default to see if a Cv exists, if it exist then it shows that, if not, it shows the
     * place holder page
     */
    private void loadCvPage(String fileName) {
        try {
            loadPage(MainApp.class.getResource(FXML_FILE_FOLDER + "html/" + fileName).toString());
        } catch (Exception e) {
            loadPage(MainApp.class.getResource(FXML_FILE_FOLDER + "html/placeholder.html").toString());
        }
    }
}
