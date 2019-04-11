package seedu.address.testutil;

import seedu.address.model.PdfBook;
import seedu.address.model.pdf.Pdf;

/**
 * A utility class to help with building Pdfbook objects.
 * Example usage: <br>
 *     {@code PdfBook ab = new PdfBookBuilder().withPerson("John", "Doe").build();}
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
