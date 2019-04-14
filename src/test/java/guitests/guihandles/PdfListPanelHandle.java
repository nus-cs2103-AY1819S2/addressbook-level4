package guitests.guihandles;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javafx.scene.Node;
import javafx.scene.control.ListView;
import seedu.pdf.model.pdf.Pdf;

/**
 * Provides a handle for {@code PdfListPanel} containing the list of {@code PdfCard}.
 */
public class PdfListPanelHandle extends NodeHandle<ListView<Pdf>> {
    public static final String PDF_LIST_VIEW_ID = "#pdfListView";

    private static final String CARD_PANE_ID = "#cardPane";

    private Optional<Pdf> lastRememberedSelectedPdfCard;

    public PdfListPanelHandle(ListView<Pdf> pdfListPanelNode) {
        super(pdfListPanelNode);
    }

    /**
     * Returns a handle to the selected {@code PdfCardHandle}.
     * A maximum of 1 item can be selected at any time.
     * @throws AssertionError if no card is selected, or more than 1 card is selected.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public PdfCardHandle getHandleToSelectedCard() {
        List<Pdf> selectedPdfList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedPdfList.size() != 1) {
            throw new AssertionError("Pdf list size expected 1.");
        }

        return getAllCardNodes().stream()
                .map(PdfCardHandle::new)
                .filter(handle -> handle.equals(selectedPdfList.get(0)))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    /**
     * Returns the index of the selected card.
     */
    public int getSelectedCardIndex() {
        return getRootNode().getSelectionModel().getSelectedIndex();
    }

    /**
     * Returns true if a card is currently selected.
     */
    public boolean isAnyCardSelected() {
        List<Pdf> selectedCardsList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedCardsList.size() > 1) {
            throw new AssertionError("Card list size expected 0 or 1.");
        }

        return !selectedCardsList.isEmpty();
    }

    /**
     * Navigates the listview to display {@code pdf}.
     */
    public void navigateToCard(Pdf pdf) {
        if (!getRootNode().getItems().contains(pdf)) {
            throw new IllegalArgumentException("Pdf does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(pdf);
        });
        guiRobot.pauseForHuman();
    }

    /**
     * Navigates the listview to {@code index}.
     */
    public void navigateToCard(int index) {
        if (index < 0 || index >= getRootNode().getItems().size()) {
            throw new IllegalArgumentException("Index is out of bounds.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(index);
        });
        guiRobot.pauseForHuman();
    }

    /**
     * Selects the {@code PdfCard} at {@code index} in the list.
     */
    public void select(int index) {
        getRootNode().getSelectionModel().select(index);
    }

    /**
     * Returns the pdf card handle of a pdf associated with the {@code index} in the list.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public PdfCardHandle getPdfCardHandle(int index) {
        return getAllCardNodes().stream()
                .map(PdfCardHandle::new)
                .filter(handle -> handle.equals(getPdf(index)))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    private Pdf getPdf(int index) {
        return getRootNode().getItems().get(index);
    }

    /**
     * Returns all card nodes in the scene graph.
     * Card nodes that are visible in the listview are definitely in the scene graph, while some nodes that are not
     * visible in the listview may also be in the scene graph.
     */
    private Set<Node> getAllCardNodes() {
        return guiRobot.lookup(CARD_PANE_ID).queryAll();
    }

    /**
     * Remembers the selected {@code PdfCard} in the list.
     */
    public void rememberSelectedPdfCard() {
        List<Pdf> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            lastRememberedSelectedPdfCard = Optional.empty();
        } else {
            lastRememberedSelectedPdfCard = Optional.of(selectedItems.get(0));
        }
    }

    /**
     * Returns true if the selected {@code PdfCard} is different from the value remembered by the most recent
     * {@code rememberSelectedPdfCard()} call.
     */
    public boolean isSelectedPdfCardChanged() {
        List<Pdf> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            return lastRememberedSelectedPdfCard.isPresent();
        } else {
            return !lastRememberedSelectedPdfCard.isPresent()
                    || !lastRememberedSelectedPdfCard.get().equals(selectedItems.get(0));
        }
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }
}
