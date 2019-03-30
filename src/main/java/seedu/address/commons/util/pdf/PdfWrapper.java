package seedu.address.commons.util.pdf;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
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

    private final Index index;
    private final FileName fileName;
    private final Model model;
    private final PDFont pdfFont;

    private PDPage page = new PDPage();

    public PdfWrapper(Index targetIndex, FileName fileName, Model model) {
        this.index = targetIndex;
        this.fileName = fileName;
        this.model = model;

        this.pdfFont = PDType1Font.HELVETICA_BOLD;
    }

    /**
     * Label the selected medicine information onto a pdf file.
     * @throws CommandException If there is an error printing the information of the medicine onto a pdf file.
     */
    public void label() throws CommandException {
        try {
            String fileName = ("./PDF/" + this.fileName.toString() + ".pdf").replaceAll("\\s+", "");

            requireNonNull(model);
            Medicine medicineToPrint = getSpecificMedicine(model);
            model.setSelectedMedicine(medicineToPrint);

            String textNextLine = getMedicineInformationToString(medicineToPrint);

            PDDocument doc = new PDDocument();

            List<String> contents = initialisePDF(textNextLine, doc);

            writeToPdf(contents, doc, page);

            doc.save(fileName);
        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    /**
     * @param contents The dissected contents of text that is going to be written to the pdf file.
     * @param doc The current document that the string of text will be written on.
     * @param page The current page that the content will be written on.
     */
    private void writeToPdf(List<String> contents, PDDocument doc, PDPage page) {
        PDRectangle mediaBox = page.getMediaBox();
        try (PDPageContentStream stream = new PDPageContentStream(doc, page)) {
            stream.beginText();
            stream.setFont(pdfFont, 12);
            stream.newLineAtOffset(mediaBox.getLowerLeftX() + 72, mediaBox.getUpperRightY() - 72);
            for (String line : contents) {
                stream.showText(line);
                stream.newLineAtOffset(0, -1.5f * 25);
            }
            stream.endText();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param textNextLine The string of text that is going to be written to the pdf file.
     * @param doc The current document that the string of text will be written on.
     * @return The list of string that will be written on the pdf file.
     */
    private List<String> initialisePDF(String textNextLine, PDDocument doc) {
        List<String> lines = new ArrayList<>();
        try (doc) {
            doc.addPage(page);

            PDRectangle mediaBox = page.getMediaBox();
            float margin = 72;
            float width = mediaBox.getWidth() - 2 * margin;

            for (String text : textNextLine.split("\n")) {
                int lastSpace = -1;
                while (text.length() > 0) {
                    int spaceIndex = text.indexOf(' ', lastSpace + 1);
                    if (spaceIndex < 0) {
                        spaceIndex = text.length();
                    }
                    String subString = text.substring(0, spaceIndex);
                    float FONT_SIZE = 25 * 1.5f;
                    float size = FONT_SIZE * pdfFont.getStringWidth(subString) / 1000;
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
        } catch (IOException ie) {
            ie.printStackTrace();
        }
        return lines;
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
        String medicineCompany = medicineToPrint.getCompany().toString();
        String medicineTags = medicineToPrint.getTags().toString();

        return (medicineName + "\n" + medicineCompany + "\n"
                + medicineExpiry + "\n" + medicineTags);
    }
}
