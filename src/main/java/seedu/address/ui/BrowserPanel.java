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
    public static final String SEARCH_PAGE_URL = "https://se-education.org/dummy-search-page/?name=";
    public static final String SEARCH_PAGE_URL_1 = "https://www.google.com/search?q=";
    public static final String SEARCH_PAGE_URL_2 = "https://sg.linkedin.com/pub/dir/";
    public static final String SEARCH_PAGE_URL_3 = "https://www.linkedin.com/search/results/all/?keywords=";
    public static final String LINKED_IN = "linkedin";

    private static final String FXML = "BrowserPanel.fxml";
    private static final String DELIM_1 = "+";
    private static final String DELIM_2 = "/";
    private static final String DELIM_3 = "%20";
    private static final String MUST_INCLUDE = "%22";
    private static final String FIRST_RESULT = "&btnI";

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

    private void loadPersonPage(Person person) {
        String searchString = SEARCH_PAGE_URL + person.getName().fullName;
        List<String> positions = person.getPositionString();
        String positionString = "";
        for (String position : positions) {
            positionString += " " + position + ",";
        }
        String trimmedPositionString_1 = trimToSearchFormat_1(positionString.substring(1, positionString.length() - 1)); //remove final comma
        String searchString1 = SEARCH_PAGE_URL_1 + MUST_INCLUDE + person.firstNameToString() + MUST_INCLUDE + DELIM_1
                + MUST_INCLUDE + person.surnameToString() + MUST_INCLUDE + DELIM_1 + LINKED_IN + DELIM_1 +
                trimmedPositionString_1 + FIRST_RESULT;
        String searchString2 = SEARCH_PAGE_URL_2 + person.firstNameToString() + DELIM_2 + person.surnameToString();
        String searchString3 = SEARCH_PAGE_URL_3 + person.firstNameToString() + DELIM_3 + person.surnameToString() + DELIM_3 + trimToSearchFormat_2(positionString.substring(1, positionString.length() - 1));
        String searchString4 = SEARCH_PAGE_URL_1 + MUST_INCLUDE + person.firstNameToString() + MUST_INCLUDE + DELIM_1
                + MUST_INCLUDE + person.surnameToString() + MUST_INCLUDE + DELIM_1 + LINKED_IN + DELIM_1 +
                trimToSearchFormat_1(positions.get(1) + FIRST_RESULT);
        String searchString5 = SEARCH_PAGE_URL_3 + person.firstNameToString() + DELIM_3 + person.surnameToString() + DELIM_3 + trimToSearchFormat_2(positions.get(1));
        System.out.println(searchString);
        System.out.println(searchString1);
        System.out.println(searchString2);
        System.out.println(searchString3);
        System.out.println(searchString4);
        System.out.println(searchString5);
        loadPage(searchString);
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

    private String trimToSearchFormat_1(String input) {
        String output = input.replace("p:", "")
                .replace("[", "")
                .replace("]", "")
                .replace(",", "")
                .replace(" ", DELIM_1);
        return output;
    }

    private String trimToSearchFormat_2(String input) {
        String output = input.replace("p:", "")
                .replace("[", "")
                .replace("]", "")
                .replace(",", "")
                .replace(" ", DELIM_3);
        return output;
    }

}
