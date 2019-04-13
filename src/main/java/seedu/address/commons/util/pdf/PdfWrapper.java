package seedu.address.commons.util.pdf;

import static java.util.Objects.requireNonNull;

import java.awt.Color;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.FileName;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.medicine.Medicine;

/**
 * A PdfWrapper class to complement the label command.
 */
public class PdfWrapper {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not export data to pdf file: ";
    private static final String DEFAULT_LABEL_FOLDER_NAME = "PDF";
    private static final Path DEFAULT_LABEL_FOLDER_PATH = Paths.get(DEFAULT_LABEL_FOLDER_NAME);
    private final Index index;
    private String fileName;
    private final Model model;
    private PDPage page = new PDPage();

    public PdfWrapper(Index targetIndex, FileName fileName, Model model) {
        this.index = targetIndex;
        this.fileName = fileName.toString();
        this.model = model;
    }

    /**
     * Label the selected medicine information onto a pdf file.
     *
     * @throws CommandException If there is an error printing the information of the medicine onto a pdf file.
     */
    public void label() throws CommandException {
        createIfLabelDirectoryMissing();
        parseFileName();
        requireNonNull(model);
        Medicine medicineToPrint = getSpecificMedicine(model);
        model.setSelectedMedicine(medicineToPrint);
        String textNextLine = getMedicineInformationToString(medicineToPrint);

        writeToPdf(page, textNextLine);

    }

    /**
     * Creates the export directory if export directory where exported data are stored.
     * @throws CommandException if there are errors creating the export directory.
     */
    private void createIfLabelDirectoryMissing() throws CommandException {
        if (Files.isDirectory(DEFAULT_LABEL_FOLDER_PATH) == false) {
            try {
                Files.createDirectory(DEFAULT_LABEL_FOLDER_PATH);
            } catch (IOException ioe) {
                throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
            }
        }
    }

    private void parseFileName() {
        this.fileName = Paths.get(DEFAULT_LABEL_FOLDER_NAME, fileName + ".pdf").toString().replaceAll("\\s+", "");
    }

    /**
     * @param page The current page that the content will be written on.
     */
    private void writeToPdf(PDPage page, String textNextLine) {
        try (PDDocument doc = new PDDocument()) {
            doc.addPage(page);

            PDFont font = PDType1Font.HELVETICA_BOLD;
            float fontSize = 25;
            float leading = 1.5f * fontSize;

            PDRectangle mediaBox = page.getMediaBox();
            float margin = 72;
            float width = mediaBox.getWidth() - 2 * margin;
            float startX = mediaBox.getLowerLeftX() + margin;
            float startY = mediaBox.getUpperRightY() - margin;

            List<String> lines = new ArrayList<String>();
            for (String text : textNextLine.split("\n")) {
                int lastSpace = -1;
                while (text.length() > 0) {
                    int spaceIndex = text.indexOf(' ', lastSpace + 1);
                    if (spaceIndex < 0) {
                        spaceIndex = text.length();
                    }
                    String subString = text.substring(0, spaceIndex);
                    float size = fontSize * font.getStringWidth(subString) / 1000;
                    if (size > width) {
                        if (lastSpace < 0) {
                            lastSpace = spaceIndex;
                            subString = text.substring(0, lastSpace);
                            lines.add(subString);
                            text = text.substring(lastSpace).trim();
                            lastSpace = -1;
                        }
                    } else if (spaceIndex == text.length()) {
                        lines.add(text);
                        text = "";
                    } else {
                        lastSpace = spaceIndex;
                    }
                }
            }

            try (PDPageContentStream contents = new PDPageContentStream(doc, page)) {
                contents.beginText();
                contents.setFont(font, 12);
                contents.newLineAtOffset(startX, startY);
                for (String line : lines) {
                    contents.showText(line);
                    contents.newLineAtOffset(0, -leading);
                }
                contents.endText();
                contents.setNonStrokingColor(Color.DARK_GRAY);
                contents.addRect(50, 550, 350, 200);
                contents.addRect(40, 540, 370, 220);
                contents.stroke();
            }

            doc.save(fileName);
        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    /**
     * @param model The current model that the medicine information will be retrieved from.
     * @throws CommandException If there is an error cleaning up the empty csv file created.
     */
    private Medicine getSpecificMedicine(Model model) throws CommandException {
        List<Medicine> filteredMedicineList = model.getFilteredMedicineList();

        if (index.getZeroBased() >= filteredMedicineList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_MEDICINE_DISPLAYED_INDEX);
        }

        return filteredMedicineList.get(index.getZeroBased());
    }

    /**
     * Creates and returns a {@code String} with the details of {@code medicineToPrint}.
     */
    private String getMedicineInformationToString(Medicine medicineToPrint) {
        String medicineName = medicineToPrint.getName().toString();
        String medicineExpiry = medicineToPrint.getNextExpiry().toString();
        String medicineQuantity = medicineToPrint.getTotalQuantity().toString();
        String medicineCompany = medicineToPrint.getCompany().toString();
        String medicineTags = medicineToPrint.getTags().toString();

        return ("Name:" + medicineName + "\n" + "Company:" + medicineCompany + "\n" + "Quantity:"
                + medicineQuantity + "\n" + "Expiry:" + medicineExpiry + "\n" + "Tags:" + medicineTags);
    }
}
