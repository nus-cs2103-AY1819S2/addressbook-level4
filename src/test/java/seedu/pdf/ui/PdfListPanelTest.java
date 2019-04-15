package seedu.pdf.ui;

import static java.time.Duration.ofMillis;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static seedu.pdf.testutil.TypicalIndexes.INDEX_SECOND_PDF;
import static seedu.pdf.testutil.TypicalPdfs.getTypicalPdfs;
import static seedu.pdf.ui.testutil.GuiTestAssert.assertCardDisplaysPdf;
import static seedu.pdf.ui.testutil.GuiTestAssert.assertCardEquals;

import java.util.Collections;

import org.junit.Test;

import guitests.guihandles.PdfCardHandle;
import guitests.guihandles.PdfListPanelHandle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.pdf.model.pdf.Directory;
import seedu.pdf.model.pdf.Name;
import seedu.pdf.model.pdf.Pdf;
import seedu.pdf.model.pdf.Size;

public class PdfListPanelTest extends GuiUnitTest {
    private static final ObservableList<Pdf> TYPICAL_PDFS =
            FXCollections.observableList(getTypicalPdfs());

    private static final long CARD_CREATION_AND_DELETION_TIMEOUT = 2500;

    private final SimpleObjectProperty<Pdf> selectedPdf = new SimpleObjectProperty<>();
    private PdfListPanelHandle pdfListPanelHandle;

    @Test
    public void display() {
        initUi(TYPICAL_PDFS);

        for (int i = 0; i < TYPICAL_PDFS.size(); i++) {
            pdfListPanelHandle.navigateToCard(TYPICAL_PDFS.get(i));
            Pdf expectedPdf = TYPICAL_PDFS.get(i);
            PdfCardHandle actualCard = pdfListPanelHandle.getPdfCardHandle(i);

            assertCardDisplaysPdf(expectedPdf, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }

    @Test
    public void selection_modelSelectedPdfChanged_selectionChanges() {
        initUi(TYPICAL_PDFS);
        Pdf secondPdf = TYPICAL_PDFS.get(INDEX_SECOND_PDF.getZeroBased());
        guiRobot.interact(() -> selectedPdf.set(secondPdf));
        guiRobot.pauseForHuman();

        PdfCardHandle expectedPdf = pdfListPanelHandle.getPdfCardHandle(INDEX_SECOND_PDF.getZeroBased());
        PdfCardHandle selectedPdf = pdfListPanelHandle.getHandleToSelectedCard();
        assertCardEquals(expectedPdf, selectedPdf);
    }

    /**
     * Verifies that creating and deleting large number of pdfs in {@code PdfListPanel} requires lesser than
     * {@code CARD_CREATION_AND_DELETION_TIMEOUT} milliseconds to execute.
     */
    @Test
    public void performanceTest() {
        ObservableList<Pdf> backingList = createBackingList(10000);

        assertTimeoutPreemptively(ofMillis(CARD_CREATION_AND_DELETION_TIMEOUT), () -> {
            initUi(backingList);
            guiRobot.interact(backingList::clear);
        }, "Creation and deletion of pdf cards exceeded time limit");
    }

    /**
     * Returns a list of pdfs containing {@code pdfCount} pdfs that is used to populate the
     * {@code PdfListPanel}.
     */
    private ObservableList<Pdf> createBackingList(int pdfCount) {
        ObservableList<Pdf> backingList = FXCollections.observableArrayList();
        for (int i = 0; i < pdfCount; i++) {
            Name name = new Name(i + "a.pdf");
            Directory directory = new Directory("C:\\Users");
            Size size = new Size("1024");
            Pdf pdf = new Pdf(name, directory, size, Collections.emptySet());
            backingList.add(pdf);
        }
        return backingList;
    }

    /**
     * Initializes {@code pdfListPanelHandle} with a {@code PdfListPanel} backed by {@code backingList}.
     * Also shows the {@code Stage} that displays only {@code PdfListPanel}.
     */
    private void initUi(ObservableList<Pdf> backingList) {
        PdfListPanel pdfListPanel =
                new PdfListPanel(backingList, selectedPdf, selectedPdf::set);
        uiPartRule.setUiPart(pdfListPanel);

        pdfListPanelHandle = new PdfListPanelHandle(getChildNode(pdfListPanel.getRoot(),
                PdfListPanelHandle.PDF_LIST_VIEW_ID));
    }
}
