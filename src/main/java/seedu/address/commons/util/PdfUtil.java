package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import seedu.address.MainApp;
import seedu.address.model.patient.Patient;
import seedu.address.model.record.Record;
import seedu.address.storage.PdfAdaptedInterface;
import seedu.address.storage.PdfAdaptedPerson;
import seedu.address.storage.PdfAdaptedTask;

/**
 * Converts a Pdf object instance to PDF
 */
public class PdfUtil {

    private static final String TITLE = "TeethHub";
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
    private static final PDFont MCFONT = PDType1Font.TIMES_ROMAN;
    private static final PDFont MCTITLE_FONT = PDType1Font.TIMES_BOLD;
    private static final PDFont MCITALIC_FONT = PDType1Font.TIMES_ITALIC;
    private static final int FONT_SIZE = 12;
    private static final int LINE_SPACING = 3;
    private static final String TEETH_IMAGE_PATH = "images/tooth.png";

    /**
     * Saves the PdfAdaptedPerson and PdfAdaptedTask objects to the specified PDF file.
     * Overwrites existing file if it exists, creates a new file if it doesn't.
     * @param persons cannot be null
     * @param tasks cannot be null
     * @param filePath cannot be null
     * @throws IOException if there was an error during writing to the file
     */
    public static void savePdfFile(List<PdfAdaptedPerson> persons,
                                   List<PdfAdaptedTask> tasks, Path filePath) throws IOException {

        requireNonNull(filePath);
        requireNonNull(persons);
        requireNonNull(tasks);

        FileUtil.createIfMissing(filePath);

        try (PDDocument doc = new PDDocument()) {
            // This tests immediately if file is currently in use
            doc.save(filePath.toFile());

            for (PdfAdaptedPerson person : persons) {
                writeModelObject(doc, person, "Patients");
            }

            for (PdfAdaptedTask task : tasks) {
                writeModelObject(doc, task, "Tasks");
            }

            doc.save(filePath.toFile());
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
    }

    /**
     * Creates a pdf MC file and extract information elements
     * @param record
     * @param patient
     * @param daysToRest number of days to rest
     * @param mcNo serial number of MC
     * @param filePath
     * @throws IOException if the filepath is not available
     */
    public static void saveMcPdfFile(Record record, Patient patient, String daysToRest,
                                     String mcNo, Path filePath) throws IOException {

        requireNonNull(record);
        requireNonNull(patient);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        PDDocument doc = new PDDocument();
        doc.save(filePath.toFile());

        String doctor = record.getDoctorName().toString();
        String recordDate = record.getRecordDate().toString();
        String procedure = record.getProcedure().toString();
        String description = record.getDescription().toString();

        String patientName = patient.getName().toString();
        String patientNric = patient.getNric().toString();

        String[] inputs = new String[]{doctor, description, recordDate, procedure,
                                       patientName, patientNric, daysToRest, mcNo};
        writeMcObject(doc, inputs);
        doc.save(filePath.toFile());
        doc.close();
    }

    /**
     * Write pdf file based on mc elements
     * @param doc PDDocument element, the opened pdf file
     * @param inputs mc elements
     * @throws IOException
     */
    private static void writeMcObject(PDDocument doc, String[] inputs) throws IOException {
        String doctor = inputs[0];
        String description = inputs[1];
        String recordDate = inputs[2];
        String procedure = inputs[3];
        String patientName = inputs[4];
        String patientNric = inputs[5];
        String daysToRest = inputs[6];
        String mcNo = inputs[7];

        String[] descriptionDiplay = new String[2];
        boolean isSpaceSplit = true;

        if (description.length() <= 90) {
            descriptionDiplay[0] = description;
            descriptionDiplay[1] = "";
        } else {
            int line1EndIndex = description.substring(75, 90).lastIndexOf(" ") + 75;

            if (line1EndIndex == 74) {
                line1EndIndex = 83;
                isSpaceSplit = true;
            }

            int line2EndIndex;
            if (description.length() <= 190) {
                line2EndIndex = description.length();
            } else {
                line2EndIndex = description.substring(170, 190).lastIndexOf(" ") + 170;
                if (line2EndIndex == 169) {
                    line2EndIndex = 191;
                }
            }

            descriptionDiplay[0] = description.substring(0, line1EndIndex) + (isSpaceSplit ? "" : "-");
            line1EndIndex += isSpaceSplit ? 1 : 0;
            descriptionDiplay[1] = description.substring(line1EndIndex, line2EndIndex)
                    + (line2EndIndex == description.length() ? "" : "...");
        }

        String[] lines = new String[] {
            "Some Clinic",
            "Medical Certificate",
            "MC No. " + mcNo,
            "",
            "This is to certify that : " + patientName,
            "NRIC/FIN No. : " + patientNric,
            String.format("is unfit for duty/classes for a period of %s days", daysToRest),
            String.format("from %s (inclusive).", recordDate),
            "",
            "The patient has gone through the following procedure : " + procedure,
            "Description : " + descriptionDiplay[0],
            descriptionDiplay[1],
            "Remarks:_______________________________________________________________________",
            "_______________________________________________________________________________",
            "",
            "Note: This certificate is not valid for absence from court or other judicial",
            "           proceedings unless specially stated",
            "",
            "",
            "",
            "",
            "(Doctor's signature/stamp)",
            "______________________________",
            String.format("Dr. %s", doctor),
            String.format("%s", recordDate)
        };

        int[] styles = new int[lines.length];
        styles[0] = 1;
        styles[1] = 2;
        styles[15] = 3;
        styles[16] = 3;
        styles[21] = 4;

        PDPage page = new PDPage(PDRectangle.A4);
        doc.addPage(page);
        PDPageContentStream contents = new PDPageContentStream(doc, page);
        float ty = 700;

        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];

            contents.beginText();

            float textWidth;
            float tx = 50;

            switch (styles[i]) {
            case 0 : default:
                contents.setFont(MCFONT, FONT_SIZE);
                contents.newLineAtOffset(tx, ty);
                ty -= FONT_SIZE * 2;
                break;
            case 1:
                contents.setFont(MCTITLE_FONT, TITLE_FONT_SIZE);
                textWidth = MCTITLE_FONT.getStringWidth(line) / 1000 * TITLE_FONT_SIZE;
                tx = (page.getMediaBox().getWidth() - textWidth) / 2;
                contents.newLineAtOffset(tx, ty);
                ty -= TITLE_FONT_SIZE * 2;
                break;
            case 2:
                contents.setFont(MCFONT, SUBTITLE_FONT_SIZE);
                textWidth = MCFONT.getStringWidth(line) / 1000 * SUBTITLE_FONT_SIZE;
                tx = (page.getMediaBox().getWidth() - textWidth) / 2;
                contents.newLineAtOffset(tx, ty);
                ty -= SUBTITLE_FONT_SIZE * 2;
                break;
            case 3:
                contents.setFont(MCITALIC_FONT, FONT_SIZE);
                contents.newLineAtOffset(tx, ty);
                ty -= FONT_SIZE * 2;
                break;
            case 4:
                contents.setFont(MCITALIC_FONT, FONT_SIZE);
                contents.newLineAtOffset(tx, ty);
                ty -= FONT_SIZE;
                break;
            }

            contents.showText(line);
            contents.endText();
        }
        contents.close();
    }



    /**
     * Outputs the PDF adapted class object contents to a PDF page.
     * @param doc The PDF document
     * @param pdfAdaptedObj The object to be printed
     * @param type The PdfAdapted object type
     * @throws IOException If file cannot be written
     */
    private static void writeModelObject(PDDocument doc, PdfAdaptedInterface pdfAdaptedObj,
                                        String type) throws IOException {
        PDPage page = new PDPage(PDRectangle.A4);
        doc.addPage(page);
        ArrayList<String> stringArr = pdfAdaptedObj.getStrings();

        float ty = page.getMediaBox().getHeight() - TOP_DOWN_MARGIN;

        try {
            PDPageContentStream[] contents = new PDPageContentStream[1];
            contents[0] = new PDPageContentStream(doc, page);

            InputStream loadImage = MainApp.class.getClassLoader().getResourceAsStream(TEETH_IMAGE_PATH);
            if (loadImage == null) {
                throw new IOException();
            }
            BufferedImage titleImageLoader = ImageIO.read(loadImage);
            PDImageXObject titleImage = LosslessFactory.createFromImage(doc, titleImageLoader);
            contents[0].setFont(TITLE_FONT, TITLE_FONT_SIZE);
            ty = writeTitle(contents[0], page, titleImage, ty);

            contents[0].setFont(DATE_TIME_FONT, DATE_TIME_FONT_SIZE);
            writeDateTime(contents[0], page);

            contents[0].setFont(TITLE_FONT, SUBTITLE_FONT_SIZE);
            ty = writeSubtitle(contents[0], page, type, ty);

            ty = drawLine(contents[0], LINE_SIDE_MARGIN, page.getMediaBox().getWidth() - LINE_SIDE_MARGIN, ty);

            if (type.equals("Patients")) {
                PDImageXObject teethImage =
                        LosslessFactory.createFromImage(doc, ((PdfAdaptedPerson) pdfAdaptedObj).getTeethImage());
                ty = drawImage(contents[0], teethImage, SIDE_MARGIN, ty);
            }

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
    private static float writeString(PDPageContentStream contents, String toWrite, float tx, float ty,
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
    private static float splitString(PDDocument doc, PDPage page, PDPageContentStream[] contents, String toWrite,
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
    private static float addPageIfNeeded(PDDocument doc, PDPageContentStream[] contents, float ty) throws IOException {
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
     * @param titleImage The Image to draw
     * @param ty The y coordinate to write at
     * @throws IOException If file cannot be written
     */
    private static float writeTitle(PDPageContentStream contents, PDPage page, PDImageXObject titleImage,
                             float ty) throws IOException {
        try {
            float textWidth = TITLE_FONT.getStringWidth(TITLE) / 1000 * TITLE_FONT_SIZE;
            float textHeight = TITLE_FONT.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * TITLE_FONT_SIZE;
            float tx = ((page.getMediaBox().getWidth() - textWidth - (titleImage.getWidth() * textHeight / 1000))
                    / 2) - 3;
            writeString(contents, TITLE, tx, ty, TITLE_FONT_SIZE);
            contents.drawImage(titleImage, tx + textWidth + 5, ty - 1,
                    titleImage.getHeight() * textHeight / 1000, titleImage.getHeight() * textHeight / 1000);
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
    private static void writeDateTime(PDPageContentStream contents, PDPage page) throws IOException {
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
    private static float writeSubtitle(PDPageContentStream contents, PDPage page, String toWrite,
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
    private static float drawLine(PDPageContentStream contents, float start, float end, float ty) throws IOException {
        try {
            contents.moveTo(start, ty - LINE_SPACING);
            contents.lineTo(end, ty - LINE_SPACING);
            contents.stroke();
            return ty - (5 * LINE_SPACING);
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
    private static float drawImage(PDPageContentStream contents, PDImageXObject image,
                                   float tx, float ty) throws IOException {
        float imageWidth = (float) image.getWidth();
        float imageHeight = (float) image.getHeight();
        float limit = 200;

        if (imageHeight > limit || imageWidth > limit) {
            imageHeight = imageHeight / imageWidth * limit;
            imageWidth = limit;
        }

        try {
            contents.drawImage(image, tx, ty - imageHeight, imageWidth, imageHeight);
            return ty - imageHeight - (5 * LINE_SPACING);
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
    }
}
