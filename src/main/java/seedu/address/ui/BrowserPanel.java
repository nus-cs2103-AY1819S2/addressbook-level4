package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import java.net.URL;
import java.util.List;
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
    public static final String SEARCH_PAGE_URL = "https://www.linkedin.com/search/results/all/?keywords=";

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
            loadPersonPage(newValue);
        });

        loadDefaultPage();
    }

    /**
     * Loads the linked in page, making a search for the person with their name and one of their positions.
     * If no positions are held then just the name is searched for
     */
    private void loadPersonPage(Person person) {
        String personsPosition;
        String personsName = person.nameToString();
        String personsModifiedName = trimToSearchFormat(personsName);
        List<String> positions = person.getPositionString();
        if (positions.isEmpty()) {
            personsPosition = "";
        } else {
            personsPosition = trimToSearchFormat(positions.get(0));
        }
        String searchString1 = SEARCH_PAGE_URL + personsModifiedName + DELIM + personsPosition;
        loadPage(searchString1);
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

}
