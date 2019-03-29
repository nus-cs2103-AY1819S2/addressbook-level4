package seedu.travel.ui;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.logging.Logger;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import seedu.travel.commons.core.LogsCenter;
import seedu.travel.model.chart.Chart;
import seedu.travel.model.place.Address;
import seedu.travel.model.place.CountryCode;
import seedu.travel.model.place.DateVisited;
import seedu.travel.model.place.Description;
import seedu.travel.model.place.Name;
import seedu.travel.model.place.Place;
import seedu.travel.model.place.Rating;
import seedu.travel.model.tag.Tag;

/**
 * Panel containing the list of places.
 */
public class DisplayListPanel extends UiPart<Region> {
    private static final String FXML = "DisplayListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(DisplayListPanel.class);

    @FXML
    private ListView<Chart> displayListView;

    public DisplayListPanel(ObservableList<Place> placeList, ObservableValue<Place> selectedDisplay,
                          Consumer<Place> onSelectedDisplayChange) {
        super(FXML);

        // creates a dummy Place
        Name name = new Name("Clementi Mall");
        CountryCode countryCode = new CountryCode("SGP");
        DateVisited dateVisited = new DateVisited("10/10/2017");
        Rating rating = new Rating("4");
        Description description = new Description("No description");
        Address address = new Address("Lower Kent Ridge");
        Set<Tag> tag = new HashSet<>();

        ObservableList<Chart> charts = FXCollections.observableArrayList();
        charts.add(new Chart(placeList));
        for (int i = 0; i < charts.size(); i++) {
            Chart chart = charts.get(i);
            System.out.println(chart.getMapCountry() + ", " + chart.getMapRating() + ", " + chart.getMapYear());
        }

        // adds only one dummy Place to the list
        displayListView.setItems(charts);
        displayListView.setCellFactory(listView -> new DisplayListViewCell());


        placeList.addListener(new ListChangeListener<Place>() {
            @Override
            public void onChanged(Change<? extends Place> c) {
                ObservableList<Chart> charts = FXCollections.observableArrayList();
                charts.add(new Chart(placeList));
                // adds only one dummy Place to the list
                displayListView.setItems(charts);
                displayListView.setCellFactory(listView -> new DisplayListViewCell());
            }
        });

        // disables selection in list view
        displayListView.addEventFilter(MouseEvent.ANY, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                mouseEvent.consume();
            }
        });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Place} using a {@code DisplayCard}.
     */
    class DisplayListViewCell extends ListCell<Chart> {
        @Override
        protected void updateItem(Chart chart, boolean empty) {
            super.updateItem(chart, empty);

            if (empty || chart == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new DisplayCard(chart).getRoot());
            }
        }
    }

}
