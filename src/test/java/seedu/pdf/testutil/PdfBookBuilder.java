package seedu.pdf.testutil;

import seedu.pdf.model.PdfBook;
import seedu.pdf.model.pdf.Pdf;

/**
 * A utility class to help with building Pdfbook objects.
 * Example usage: <br>
 *     {@code PdfBook ab = new PdfBookBuilder().withPdf(SAMPLE_PDF_1).build();}
 */
public class PdfBookBuilder {

    private PdfBook pdfBook;

    public PdfBookBuilder() {
        pdfBook = new PdfBook();
    }

    public PdfBookBuilder(PdfBook pdfBook) {
        this.pdfBook = pdfBook;
    }

    /**
     * Adds a new {@code Pdf} to the {@code PdfBook} that we are building.
     */
    public PdfBookBuilder withPdf(Pdf pdf) {
        pdfBook.addPdf(pdf);
        return this;
    }

    public PdfBook build() {
        return pdfBook;
    }
}
