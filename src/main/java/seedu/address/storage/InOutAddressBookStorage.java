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
    private static final int TOP_DOWN_MARGIN = 35;
    private static final int SIDE_MARGIN = 50;
    private static final int LINE_SIDE_MARGIN = 10;
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
     * @param filePath location of the data. Cannot be null.
     */
    public void saveAsPdf(ReadOnlyAddressBook addressBook, Path filePath) throws IOException {
        requireNonNull(addressBook);
        requireNonNull(filePath);

        PdfSerializableAddressBook toWrite = new PdfSerializableAddressBook(addressBook);

        FileUtil.createIfMissing(filePath);

        try (PDDocument doc = new PDDocument()) {
            // This tests immediately if file is currently in use
            doc.save(filePath.toFile());

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
     * @param type The PdfAdapted object type
     * @throws IOException If file cannot be written
     */
    private void writeModelObject(PDDocument doc, PdfAdaptedInterface pdfAdaptedObj,
                                  String type) throws IOException {
        PDPage page = new PDPage(PDRectangle.A4);
        doc.addPage(page);
        ArrayList<String> stringArr = pdfAdaptedObj.getStrings();

        float ty = page.getMediaBox().getHeight() - TOP_DOWN_MARGIN;

        try {
            PDPageContentStream[] contents = new PDPageContentStream[1];
            contents[0] = new PDPageContentStream(doc, page);

            PDImageXObject pdImage = PDImageXObject.createFromFile(TEETH_IMAGE_PATH, doc);
            contents[0].setFont(TITLE_FONT, TITLE_FONT_SIZE);
            ty = writeTitle(contents[0], page, pdImage, ty);

            contents[0].setFont(DATE_TIME_FONT, DATE_TIME_FONT_SIZE);
            writeDateTime(contents[0], page);

            contents[0].setFont(TITLE_FONT, SUBTITLE_FONT_SIZE);
            ty = writeSubtitle(contents[0], page, type, ty);

            ty = drawLine(contents[0], LINE_SIDE_MARGIN, page.getMediaBox().getWidth() - LINE_SIDE_MARGIN, ty);

            contents[0].setFont(FONT, FONT_SIZE);
            for (String toWrite : stringArr) {
                ty = splitString(doc, page, contents, toWrite, SIDE_MARGIN, ty);
            }

            drawLine(contents[0], LINE_SIDE_MARGIN, page.getMediaBox().getWidth() - LINE_SIDE_MARGIN, ty);

            contents[0].close();
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
     * @param size The font size to write with
     * @throws IOException If file cannot be written
     */
    private float writeString(PDPageContentStream contents, String toWrite, float tx, float ty,
                              float size) throws IOException {
        try {
            contents.beginText();
            contents.newLineAtOffset(tx, ty);
            contents.showText(toWrite);
            contents.endText();
            return ty - size - LINE_SPACING;
        } catch (IOException e) {
            throw new IOException("File cannot be written.");
        }
    }

    /**
     * Splits a String if the writing would exceed the page side margin.
     * @param doc The document to write to
     * @param contents The "pointer" to the content stream for writing
     * @param page The page to write to
     * @param toWrite The String to write
     * @param tx The x coordinate to write at
     * @param ty The y coordinate to write at
     * @throws IOException If file cannot be written
     */
    private float splitString(PDDocument doc, PDPage page, PDPageContentStream[] contents, String toWrite,
                              float tx, float ty) throws IOException {
        try {
            float textWidth = FONT.getStringWidth(toWrite) / 1000 * FONT_SIZE;
            if (textWidth + 2 * tx > page.getMediaBox().getWidth()) {
                int index = 0;
                int limit = 0;
                String subString;
                do {
                    do {
                        subString = toWrite.substring(index, toWrite.length() - limit);
                        textWidth = FONT.getStringWidth(subString) / 1000 * FONT_SIZE;
                        limit++;
                    } while (textWidth + 2 * tx > page.getMediaBox().getWidth());
                    ty = addPageIfNeeded(doc, contents, ty);
                    ty = writeString(contents[0], subString, tx, ty, FONT_SIZE);
                    index = toWrite.length() - limit;
                    limit = 0;
                } while (index < toWrite.length() - 1);
                return ty;
            } else {
                ty = writeString(contents[0], toWrite, tx, ty, FONT_SIZE);
                return ty;
            }
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
    }

    /**
     * Creates a new page if the new line exceeds the page top down margin.
     * @param doc The pointer to the document to write to
     * @param contents The "pointer" to the content stream for writing
     * @param ty The y coordinate to write at
     */
    private float addPageIfNeeded(PDDocument doc, PDPageContentStream[] contents, float ty) throws IOException {
        if (ty < TOP_DOWN_MARGIN) {
            PDPage page = new PDPage(PDRectangle.A4);
            doc.addPage(page);
            try {
                contents[0].close();
                contents[0] = new PDPageContentStream(doc, page);
            } catch (IOException e) {
                throw new IOException(e.getMessage());
            }
            contents[0].setFont(FONT, FONT_SIZE);
            return page.getMediaBox().getHeight() - TOP_DOWN_MARGIN;
        }
        return ty;
    }

    /**
     * Writes a single line of content followed by an image.
     * @param contents The content stream for writing
     * @param page The page to write to
     * @param pdImage The Image to draw
     * @param ty The y coordinate to write at
     * @throws IOException If file cannot be written
     */
    private float writeTitle(PDPageContentStream contents, PDPage page, PDImageXObject pdImage,
                             float ty) throws IOException {
        try {
            float textWidth = TITLE_FONT.getStringWidth(TITLE) / 1000 * TITLE_FONT_SIZE;
            float textHeight = TITLE_FONT.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * TITLE_FONT_SIZE;
            float tx = ((page.getMediaBox().getWidth() - textWidth - (pdImage.getWidth() * textHeight / 1000)) / 2) - 3;
            writeString(contents, TITLE, tx, ty, TITLE_FONT_SIZE);
            contents.drawImage(pdImage, tx + textWidth + 5, ty - 1,
                pdImage.getHeight() * textHeight / 1000, pdImage.getHeight() * textHeight / 1000);
            return ty - TITLE_FONT_SIZE - LINE_SPACING;
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
    }

    /**
     * Writes the date and time. Writes 3 lines.
     * @param contents The content stream for writing
     * @param page The page to write to
     * @throws IOException If file cannot be written
     */
    private void writeDateTime(PDPageContentStream contents, PDPage page) throws IOException {
        try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/mm/yyyy");
            LocalDateTime now = LocalDateTime.now();
            String toWrite = "Date saved/exported:";
            float textWidth = DATE_TIME_FONT.getStringWidth(toWrite) / 1000 * DATE_TIME_FONT_SIZE;
            float textHeight = DATE_TIME_FONT.getFontDescriptor().getFontBoundingBox().getHeight()
                                / 1000 * DATE_TIME_FONT_SIZE;
            float tx = page.getMediaBox().getWidth() - textWidth - 20;
            float ty = page.getMediaBox().getHeight() - TOP_DOWN_MARGIN + textHeight - LINE_SPACING;
            ty = writeString(contents, toWrite, tx, ty, DATE_TIME_FONT_SIZE);

            textWidth = DATE_TIME_FONT.getStringWidth(dtf.format(now)) / 1000 * DATE_TIME_FONT_SIZE;
            tx = page.getMediaBox().getWidth() - textWidth - 20;
            ty = writeString(contents, dtf.format(now), tx, ty, DATE_TIME_FONT_SIZE);

            dtf = DateTimeFormatter.ofPattern("hh:mm:ss a");
            textWidth = DATE_TIME_FONT.getStringWidth(dtf.format(now)) / 1000 * DATE_TIME_FONT_SIZE;
            tx = page.getMediaBox().getWidth() - textWidth - 20;
            writeString(contents, dtf.format(now), tx, ty, DATE_TIME_FONT_SIZE);
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
            writeString(contents, toWrite, tx, ty, SUBTITLE_FONT_SIZE);
            drawLine(contents, tx, tx + textWidth, ty);
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
     * Draws an image.
     * @param contents The content stream for writing
     * @param image The image to draw
     * @param tx The x coordinates to draw at
     * @param ty The y coordinates to draw at
     * @throws IOException If file cannot be written
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
