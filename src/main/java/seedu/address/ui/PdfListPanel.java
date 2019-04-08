package seedu.address.ui;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.logging.Logger;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.pdf.Pdf;

/**
 * Panel containing the list of persons.
 */
public class PdfListPanel extends UiPart<Region> {
    private static final String FXML = "PdfListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PdfListPanel.class);

    @FXML
    private ListView<Pdf> personListView;

    public PdfListPanel(ObservableList<Pdf> pdfList, ObservableValue<Pdf> selectedPerson,
                        Consumer<Pdf> onSelectedPersonChange) {
        super(FXML);
        personListView.setItems(pdfList);
        personListView.setCellFactory(listView -> new PersonListViewCell());
        personListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            logger.fine("Selection in pdf list panel changed to : '" + newValue + "'");
            onSelectedPersonChange.accept(newValue);
        });
        selectedPerson.addListener((observable, oldValue, newValue) -> {
            logger.fine("Selected pdf changed to: " + newValue);

            // Don't modify selection if we are already selecting the selected pdf,
            // otherwise we would have an infinite loop.
            if (Objects.equals(personListView.getSelectionModel().getSelectedItem(), newValue)) {
                return;
            }

            if (newValue == null) {
                personListView.getSelectionModel().clearSelection();
            } else {
                int index = personListView.getItems().indexOf(newValue);
                personListView.scrollTo(index);
                personListView.getSelectionModel().clearAndSelect(index);
            }
        });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Pdf} using a {@code PdfCard}.
     */
    class PersonListViewCell extends ListCell<Pdf> {
        @Override
        protected void updateItem(Pdf pdf, boolean empty) {
            super.updateItem(pdf, empty);

            if (empty || pdf == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new PdfCard(pdf, getIndex() + 1).getRoot());
            }
        }
    }

}
