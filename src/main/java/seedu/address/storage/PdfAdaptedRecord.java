package seedu.address.storage;

import static seedu.address.storage.StorageConstants.DIVIDER;

import seedu.address.model.record.Record;
import seedu.address.model.record.exceptions.BadRecordFormatException;

/**
 * PDF-friendly version of {@link Record}.
 */
class PdfAdaptedRecord {

    private final String doctorName;

    private final String description;

    private final String recordDate;

    /**
     * Constructs a {@code PdfAdaptedRecord} with the given {@code recordLine}.
     */
    public PdfAdaptedRecord(String recordLine) {
        String[] sb = recordLine.split(DIVIDER);

        if (sb.length == 3) {
            doctorName = sb[0];
            description = sb[1];
            recordDate = sb[2];
        } else {
            throw new BadRecordFormatException();
        }
    }

    /**
     * Converts a given {@code Record} into this class for PDF use.
     */
    public PdfAdaptedRecord(Record record) {
        String recordLine = record.toString();
        String[] sb = recordLine.split(DIVIDER);

        if (sb.length == 3) {
            doctorName = sb[0];
            description = sb[1];
            recordDate = sb[2];
        } else {
            throw new BadRecordFormatException();
        }
    }

    public String getDoctorName() {
        return doctorName;
    }

    public String getDescription() {
        return description;
    }

    public String getRecordDate() {
        return recordDate;
    }
}
