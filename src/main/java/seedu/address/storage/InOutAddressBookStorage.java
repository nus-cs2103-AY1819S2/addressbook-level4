package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    private static final PDFont TITLE_FONT = PDType1Font.HELVETICA_BOLD;
    private static final int TITLE_FONT_SIZE = 20;
    private static final int TOP_MARGIN = 20;
    private static final PDFont DATE_TIME_FONT = PDType1Font.HELVETICA;
    private static final int DATE_TIME_FONT_SIZE = 8;
    private static final PDFont SUBTITLE_FONT = PDType1Font.HELVETICA_BOLD;
    private static final int SUBTITLE_FONT_SIZE = 16;
    private static final PDFont FONT = PDType1Font.HELVETICA;
    private static final int FONT_SIZE = 12;
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

            for (PdfAdaptedPerson person : toWrite.getPersons()) {
                writeModelObject(doc, person, "Patients");
            }

            for (PdfAdaptedTask task : toWrite.getTasks()) {
                writeModelObject(doc, task, "Tasks");
            }

            doc.save(filePath.toFile());
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
    }

    /**
     * Outputs the PDF adapted class object contents to a PDF page.
     * @param doc The PDF document
     * @param pdfAdaptedObj The object to be printed
     * @throws IOException If file cannot be written
     */
    private void writeModelObject(PDDocument doc, PdfAdaptedInterface pdfAdaptedObj, String type) throws IOException {
        PDPage page = new PDPage(PDRectangle.A4);
        doc.addPage(page);
        ArrayList<String> stringArr = pdfAdaptedObj.getStrings();
        float ty = TOP_MARGIN;

        try (PDPageContentStream contents = new PDPageContentStream(doc, page)) {
            PDImageXObject pdImage = PDImageXObject.createFromFile(TEETH_IMAGE_PATH, doc);
            contents.setFont(TITLE_FONT, TITLE_FONT_SIZE);
            ty = writeTitle(contents, page, pdImage);

            contents.setFont(DATE_TIME_FONT, DATE_TIME_FONT_SIZE);
            writeDateTime(contents, page);

            contents.setFont(TITLE_FONT, SUBTITLE_FONT_SIZE);
            ty = writeSubtitle(contents, page, type, ty);

            ty = drawLine(contents, 10, page.getMediaBox().getWidth() - 10, ty);

            contents.setFont(FONT, FONT_SIZE);
            for (String toWrite : stringArr) {
                ty = writeString(contents, toWrite, 50, ty);
            }
            drawLine(contents, 10, page.getMediaBox().getWidth() - 10, ty);
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
    }

    /**
     * Writes a single line of content.
     * @param contents The content stream for writing
     * @param toWrite The String to write
     * @param tx The x coordinate to write at
     * @param ty The y coordinate to write at
     * @throws IOException If file cannot be written
     */
    private float writeString(PDPageContentStream contents, String toWrite, float tx, float ty) throws IOException {
        try {
            contents.beginText();
            contents.newLineAtOffset(tx, ty);
            contents.showText(toWrite);
            contents.endText();
            return ty - FONT_SIZE - LINE_SPACING;
        } catch (IOException e) {
            throw new IOException("File cannot be written.");
        }
    }

    /**
     * Writes a single line of content followed by an image.
     * @param contents The content stream for writing
     * @param page The page to write to
     * @param pdImage The Image to draw
     * @throws IOException If file cannot be written
     */
    private float writeTitle(PDPageContentStream contents, PDPage page, PDImageXObject pdImage) throws IOException {
        try {
            float textWidth = TITLE_FONT.getStringWidth(TITLE) / 1000 * TITLE_FONT_SIZE;
            float textHeight = TITLE_FONT.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * TITLE_FONT_SIZE;
            float tx = (page.getMediaBox().getWidth() - textWidth - (pdImage.getWidth() * textHeight / 1000)) / 2;
            float ty = page.getMediaBox().getHeight() - TOP_MARGIN - textHeight;
            contents.setFont(TITLE_FONT, TITLE_FONT_SIZE);
            writeString(contents, TITLE, tx, ty);
            contents.drawImage(pdImage, tx + textWidth, ty - 1,
                pdImage.getWidth() * textHeight / 1000, pdImage.getHeight() * textHeight / 1000);
            return ty - TITLE_FONT_SIZE - LINE_SPACING;
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
    }

    private void writeDateTime(PDPageContentStream contents, PDPage page) throws IOException {
        try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/mm/yyyy hh:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            String toWrite = "Saved/Exported on:\n" + dtf.format(now);
            float textWidth = DATE_TIME_FONT.getStringWidth(toWrite) / 1000 * DATE_TIME_FONT_SIZE;
            float textHeight = DATE_TIME_FONT.getFontDescriptor().getFontBoundingBox().getHeight()
                                / 1000 * DATE_TIME_FONT_SIZE;
            float tx = page.getMediaBox().getWidth() - textWidth - 20;
            float ty = page.getMediaBox().getHeight() - TOP_MARGIN - textHeight;
            writeString(contents, toWrite, tx, ty);
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
    }

    /**
     * Writes a single line of content that is underlined.
     * @param contents The content stream for writing
     * @param page The page to write to
     * @param toWrite The String to write
     * @param ty The y coordinate to write at
     * @return The next ty
     * @throws IOException If file cannot be written
     */
    private float writeSubtitle(PDPageContentStream contents, PDPage page, String toWrite,
                                float ty) throws IOException {
        try {
            float textWidth = SUBTITLE_FONT.getStringWidth(toWrite) / 1000 * SUBTITLE_FONT_SIZE;
            float tx = (page.getMediaBox().getWidth() - textWidth) / 2;
            writeString(contents, toWrite, tx, ty - 1);
            contents.moveTo(tx, ty - LINE_SPACING);
            contents.lineTo(tx + textWidth, ty - LINE_SPACING);
            contents.stroke();
            return ty - SUBTITLE_FONT_SIZE - LINE_SPACING;
        } catch (IOException e) {
            throw new IOException("File cannot be written.");
        }
    }

    /**
     * Draws a single line.
     * @param contents The content stream for writing
     * @param start The start x coordinate of the line
     * @param end The end x coordinate of the line
     * @param ty The y coordinate to write at
     * @return The next ty
     * @throws IOException If file cannot be written
     */
    private float drawLine(PDPageContentStream contents, float start, float end, float ty) throws IOException {
        try {
            contents.moveTo(start, ty - LINE_SPACING);
            contents.lineTo(end, ty - LINE_SPACING);
            contents.stroke();
            return ty - (10 * LINE_SPACING);
        } catch (IOException e) {
            throw new IOException("File cannot be written.");
        }
    }


    /**
     * Writes a single line of content followed by an image.
     * @param contents The content stream for writing
     * @param page The page to write to
     * @param toWrite The String to write
     * @param pdImage The Image to draw
     * @param ty The y coordinate to write at
     * @return The next ty
     * @throws IOException If file cannot be written
     */
    private float writeStringWithIcon(PDPageContentStream contents, PDPage page, String toWrite, PDImageXObject pdImage,
                                    float ty) throws IOException {
        try {
            float textWidth = FONT.getStringWidth(toWrite) / 1000 * FONT_SIZE;
            float textHeight = FONT.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * FONT_SIZE;
            float tx = (page.getMediaBox().getWidth() - textWidth - (pdImage.getWidth() * textHeight / 1000)) / 2;
            contents.setFont(FONT, FONT_SIZE);
            writeString(contents, toWrite, tx, ty);
            contents.drawImage(pdImage, tx + textWidth, ty - LINE_SPACING,
                pdImage.getWidth() * textHeight / 1000, pdImage.getHeight() * textHeight / 1000);
            return ty - FONT_SIZE - LINE_SPACING;
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
    }

    /**
     * Writes a single line of content that is underlined.
     * @param contents The content stream for writing
     * @param toWrite The String to write
     * @param tx The x coordinate to write at
     * @param ty The y coordinate to write at
     * @return The next ty
     * @throws IOException If file cannot be written
     */
    private float writeStringWithUnderline(PDPageContentStream contents, String toWrite, float tx,
                                         float ty) throws IOException {
        try {
            float textWidth = FONT.getStringWidth(toWrite) / 1000 * FONT_SIZE;
            contents.setFont(FONT, FONT_SIZE);
            writeString(contents, toWrite, tx, ty);
            contents.moveTo(tx, ty - 1);
            contents.lineTo(tx + textWidth, ty - 1);
            contents.stroke();
            return ty - TITLE_FONT_SIZE - LINE_SPACING;
        } catch (IOException e) {
            throw new IOException("File cannot be written.");
        }
    }

    /**
     * Draws an image.
     * @param contents The content stream for writing
     * @param image The image to draw
     * @param tx The x coordinates to draw at
     * @param ty The y coordinates to draw at
     * @throws IOException
     */
    private void drawImage(PDPageContentStream contents, PDImageXObject image, float tx, float ty) throws IOException {
        try {
            contents.drawImage(image, tx + ((float) image.getWidth() / 1000) - 7,
                            ty - 1, (float) image.getWidth() / 1000, (float) image.getHeight() / 1000);
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
    }
}
