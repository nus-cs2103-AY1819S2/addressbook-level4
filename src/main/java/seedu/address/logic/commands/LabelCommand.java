package seedu.address.logic.commands;
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
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.medicine.Medicine;

/**
 * Prints a selected medicine identified using it's displayed index from the inventory.
 */
public class LabelCommand extends Command {

    public static final String COMMAND_WORD = "label";
    public static final String DEFAULT_FILENAME = "to_print";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Prints the Medicine name and description in PDF format using its index.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_MEDICINE_SUCCESS = "Successfully printed the medicine at index: %1$s"
            + " in PDF format";

    private final Index targetIndex;
    private final FileName fileName;

    /**
     * Creates an LabelCommand to add the specified {@code Medicine}
     */
    public LabelCommand(Index targetIndex, FileName fileName) {
        this.targetIndex = targetIndex;
        this.fileName = fileName;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        String filename = this.fileName.toString();
        requireNonNull(model);

        Medicine medicineToPrint = getSpecificMedicine(model);
        model.setSelectedMedicine(medicineToPrint);

        String textNextLine = getMedicineInformationToString(medicineToPrint);

        try (PDDocument doc = new PDDocument()) {
            PDPage page = new PDPage();
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
                for (String line: lines) {
                    contents.showText(line);
                    contents.newLineAtOffset(0, -leading);
                }
                contents.endText();
            }

            doc.save(filename);
        } catch (IOException ie) {
            ie.printStackTrace();
        }

        return new CommandResult(String.format(MESSAGE_SELECT_MEDICINE_SUCCESS, targetIndex.getOneBased()));

    }

    private Medicine getSpecificMedicine(Model model) throws CommandException {
        List<Medicine> filteredMedicineList = model.getFilteredMedicineList();

        if (targetIndex.getZeroBased() >= filteredMedicineList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_MEDICINE_DISPLAYED_INDEX);
        }

        return filteredMedicineList.get(targetIndex.getZeroBased());
    }

    private String getMedicineInformationToString(Medicine medicineToPrint) {
        String medicineName = medicineToPrint.getName().toString();
        String medicineExpiry = medicineToPrint.getNextExpiry().toString();
        String medicineCompany = medicineToPrint.getCompany().toString();
        String medicineTags = medicineToPrint.getTags().toString();

        return (medicineName + "\n" + medicineCompany + "\n"
                + medicineExpiry + "\n" + medicineTags);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LabelCommand // instanceof handles nulls
                && targetIndex.equals(((LabelCommand) other).targetIndex)); // state check
    }

}
