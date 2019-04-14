package seedu.pdf.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.logging.Logger;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.pdf.commons.core.GuiSettings;
import seedu.pdf.commons.core.LogsCenter;
import seedu.pdf.logic.commands.Command;
import seedu.pdf.logic.commands.CommandResult;
import seedu.pdf.logic.commands.exceptions.CommandException;
import seedu.pdf.logic.parser.PdfBookParser;
import seedu.pdf.logic.parser.exceptions.ParseException;
import seedu.pdf.model.Model;
import seedu.pdf.model.ReadOnlyPdfBook;
import seedu.pdf.model.pdf.Pdf;
import seedu.pdf.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final CommandHistory history;
    private final PdfBookParser pdfBookParser;
    private boolean pdfBookModified;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        history = new CommandHistory();
        pdfBookParser = new PdfBookParser();

        // Set pdfBookModified to true whenever the models' pdf book is modified.
        model.getPdfBook().addListener(observable -> pdfBookModified = true);
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        pdfBookModified = false;

        CommandResult commandResult;
        try {
            Command command = pdfBookParser.parseCommand(commandText);
            commandResult = command.execute(model, history);
        } finally {
            history.add(commandText);
        }

        if (pdfBookModified) {
            logger.info("Pdf book modified, saving to file.");
            try {
                storage.savePdfBook(model.getPdfBook());
            } catch (IOException ioe) {
                throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
            }
        }

        return commandResult;
    }

    @Override
    public ReadOnlyPdfBook getPdfBook() {
        return model.getPdfBook();
    }

    @Override
    public ObservableList<Pdf> getFilteredPdfList() {
        return model.getFilteredPdfList();
    }

    @Override
    public ObservableList<String> getHistory() {
        return history.getHistory();
    }

    @Override
    public Path getPdfBookFilePath() {
        return model.getPdfBookFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }

    @Override
    public ReadOnlyProperty<Pdf> selectedPdfProperty() {
        return model.selectedPdfProperty();
    }

    @Override
    public void setSelectedPdf(Pdf pdf) {
        model.setSelectedPdf(pdf);
    }

    @Override
    public List<Pdf> getDuePdfs() {
        ArrayList<Pdf> list = new ArrayList<>();
        PriorityQueue<Pdf> pq = new PriorityQueue<>(new Comparator<Pdf>() {
            @Override
            public int compare(Pdf o1, Pdf o2) {
                return Long.compare(o1.getDeadline().getDaysToDeadline(), o2.getDeadline().getDaysToDeadline());
            }
        });

        for (Pdf pdf : this.model.getPdfBook().getPdfList()) {
            if (pdf.getDeadline().exists() && !pdf.getDeadline().isDone()) {
                pq.add(pdf);
            }
        }

        while (list.size() < 4 && pq.size() > 0) {
            list.add(pq.poll());
        }

        return list;
    }
}
