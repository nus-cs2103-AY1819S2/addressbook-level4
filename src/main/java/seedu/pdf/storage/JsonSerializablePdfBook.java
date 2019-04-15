package seedu.pdf.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.pdf.commons.exceptions.IllegalValueException;
import seedu.pdf.model.PdfBook;
import seedu.pdf.model.ReadOnlyPdfBook;
import seedu.pdf.model.pdf.Pdf;

/**
 * An Immutable PdfBook that is serializable to JSON format.
 */
@JsonRootName(value = "pdfbook")
class JsonSerializablePdfBook {

    private static final String MESSAGE_DUPLICATE_PDF = "Pdf list contains duplicate pdf(s).";

    private final List<JsonAdaptedPdf> pdfs = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializablePdfBook} with the given pdfs.
     */
    @JsonCreator
    public JsonSerializablePdfBook(@JsonProperty("pdfs") List<JsonAdaptedPdf> pdfs) {
        this.pdfs.addAll(pdfs);
    }

    /**
     * Converts a given {@code ReadOnlyPdfBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializablePdfBook}.
     */
    JsonSerializablePdfBook(ReadOnlyPdfBook source) {
        pdfs.addAll(source.getPdfList().stream().map(JsonAdaptedPdf::new).collect(Collectors.toList()));
    }

    /**
     * Converts this pdf book into the model's {@code PdfBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    PdfBook toModelType() throws IllegalValueException {
        PdfBook pdfBook = new PdfBook();
        for (JsonAdaptedPdf jsonAdaptedPdf : pdfs) {
            Pdf pdf = jsonAdaptedPdf.toModelType();
            if (pdfBook.hasPdf(pdf)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PDF);
            }
            pdfBook.addPdf(pdf);
        }
        return pdfBook;
    }

}
