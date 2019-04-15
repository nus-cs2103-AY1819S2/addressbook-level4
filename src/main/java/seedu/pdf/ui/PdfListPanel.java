package seedu.pdf.ui;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.logging.Logger;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.pdf.commons.core.LogsCenter;
import seedu.pdf.model.pdf.Pdf;

/**
 * Panel containing the list of pdfs.
 */
public class PdfListPanel extends UiPart<Region> {
    private static final String FXML = "PdfListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PdfListPanel.class);

    @FXML
    private ListView<Pdf> pdfListView;

    public PdfListPanel(ObservableList<Pdf> pdfList, ObservableValue<Pdf> selectedPdf,
                        Consumer<Pdf> onSelectedPdfChange) {
        super(FXML);
        pdfListView.setItems(pdfList);
        pdfListView.setCellFactory(listView -> new PdfListViewCell());
        pdfListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            logger.fine("Selection in pdf list panel changed to : '" + newValue + "'");
            onSelectedPdfChange.accept(newValue);
        });
        selectedPdf.addListener((observable, oldValue, newValue) -> {
            logger.fine("Selected pdf changed to: " + newValue);

            // Don't modify selection if we are already selecting the selected pdf,
            // otherwise we would have an infinite loop.
            if (Objects.equals(pdfListView.getSelectionModel().getSelectedItem(), newValue)) {
                return;
            }

            if (newValue == null) {
                pdfListView.getSelectionModel().clearSelection();
            } else {
                int index = pdfListView.getItems().indexOf(newValue);
                pdfListView.scrollTo(index);
                pdfListView.getSelectionModel().clearAndSelect(index);
            }
        });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Pdf} using a {@code PdfCard}.
     */
    class PdfListViewCell extends ListCell<Pdf> {
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
