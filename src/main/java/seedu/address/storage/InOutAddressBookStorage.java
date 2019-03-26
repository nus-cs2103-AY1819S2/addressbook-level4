package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Optional;
import java.util.logging.Logger;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.ReadOnlyAddressBook;

/**
 * A class to read AddressBook data stored as a json file on the hard disk or write AddressBook data to
 * a json file or PDF file.
 */
public class InOutAddressBookStorage implements AddressBookStorage {

    private static final String TITLE = "OurTeeth";
    private static final int TOP_MARGIN = 20;
    private static final int LINE_SPACING = 3;
    private static final String TEETH_IMAGE_PATH = "src\\main\\resources\\images\\tooth.png";

    private static final Logger logger = LogsCenter.getLogger(InOutAddressBookStorage.class);

    private Path filePath;

    public InOutAddressBookStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getAddressBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook() throws DataConversionException {
        return readAddressBook(filePath);
    }

    /**
     * Similar to {@link #readAddressBook()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyAddressBook> readAddressBook(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableAddressBook> jsonAddressBook = JsonUtil.readJsonFile(
                filePath, JsonSerializableAddressBook.class);
        if (!jsonAddressBook.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonAddressBook.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        saveAddressBook(addressBook, filePath);
    }

    /**
     * Similar to {@link #saveAddressBook(ReadOnlyAddressBook)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveAddressBook(ReadOnlyAddressBook addressBook, Path filePath) throws IOException {
        requireNonNull(addressBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableAddressBook(addressBook), filePath);
    }

    @Override
    public void saveAsPdf(ReadOnlyAddressBook addressBook) throws IOException {
        saveAsPdf(addressBook, filePath);
    }

    /**
     * Similar to {@link #saveAsPdf(ReadOnlyAddressBook)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveAsPdf(ReadOnlyAddressBook addressBook, Path filePath) throws IOException {
        requireNonNull(addressBook);
        requireNonNull(filePath);

        PdfSerializableAddressBook toWrite = new PdfSerializableAddressBook(addressBook);

        FileUtil.createIfMissing(filePath);

        try (PDDocument doc = new PDDocument()) {
            PDFont font = PDType1Font.HELVETICA;
            int fontSize = 12;
            PDFont titleFont = PDType1Font.HELVETICA_BOLD;
            int titleFontSize = 20;

            for (PdfAdaptedPerson person : toWrite.getPersons()) {
                writeModelObject(doc, titleFont, titleFontSize, font, fontSize, person, "Patients");
            }

            for (PdfAdaptedTask task : toWrite.getTasks()) {
                writeModelObject(doc, titleFont, titleFontSize, font, fontSize, task, "Tasks");
            }

            doc.save(filePath.toFile());
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
    }

    /**
     * Outputs the PDF adapted class object contents to a PDF page.
     * @param doc The PDF document
     * @param titleFont The font type for the title to be used
     * @param titleFontSize The font size for the title to be used
     * @param font The font type to be used
     * @param fontSize The font size to be used
     * @param pdfAdaptedObj The object to be printed
     * @throws IOException If file cannot be written
     */
    private void writeModelObject(PDDocument doc, PDFont titleFont, int titleFontSize, PDFont font,
                                  int fontSize, PdfAdaptedInterface pdfAdaptedObj, String type) throws IOException {
        PDPage page = new PDPage(PDRectangle.A4);
        doc.addPage(page);
        ArrayList<String> stringArr = pdfAdaptedObj.getStrings();
        float textWidth = font.getStringWidth(TITLE) / 1000 * titleFontSize;
        float textHeight = font.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * titleFontSize;
        float tx;
        float ty;

        try (PDPageContentStream contents = new PDPageContentStream(doc, page)) {
            contents.setFont(titleFont, titleFontSize);
            PDImageXObject pdImage = PDImageXObject.createFromFile(TEETH_IMAGE_PATH, doc);
            tx = ((page.getMediaBox().getWidth() - textWidth) - (pdImage.getWidth() * textHeight / 1000)) / 2;
            ty = page.getMediaBox().getHeight() - TOP_MARGIN - textHeight;
            writeLine(contents, TITLE, tx, ty);
            contents.drawImage(pdImage, tx + textWidth + (pdImage.getWidth() * textHeight / 1000) - 7, ty - 1,
                        pdImage.getWidth() * textHeight / 1000,
                        pdImage.getHeight() * textHeight / 1000);

            int subtitleFontSize = fontSize + Math.abs((titleFontSize - fontSize) / 2);
            contents.setFont(titleFont, subtitleFontSize);
            textWidth = font.getStringWidth(type) / 1000 * subtitleFontSize;
            textHeight = font.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * subtitleFontSize;
            tx = (page.getMediaBox().getWidth() - textWidth) / 2;
            ty = page.getMediaBox().getHeight() - TOP_MARGIN - textHeight - subtitleFontSize - LINE_SPACING * 4;
            writeLine(contents, type, tx, ty);

            contents.moveTo(tx, ty - 1);
            contents.lineTo(tx + textWidth + 4, ty - 1);
            contents.stroke();

            contents.setFont(font, fontSize);
            for (int i = 0; i < stringArr.size(); i++) {
                writeLine(contents, stringArr.get(i), 50, 700 - (i * (fontSize + LINE_SPACING)));
            }
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
    }

    /**
     * Writes a single line of content.
     * @param contents The content stream for writing
     * @param toWrite The String to write
     * @param tx The x coordinates to write at
     * @param ty The y coordinates to write at
     * @throws IOException If file cannot be written
     */
    private void writeLine(PDPageContentStream contents, String toWrite, float tx, float ty) throws IOException {
        try {
            contents.beginText();
            contents.newLineAtOffset(tx, ty);
            contents.showText(toWrite);
            contents.endText();
        } catch (IOException e) {
            throw new IOException("File cannot be written.");
        }
    }
}
