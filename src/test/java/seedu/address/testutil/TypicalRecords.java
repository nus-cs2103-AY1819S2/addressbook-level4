package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;

import seedu.address.model.record.Record;
/**
 * A utility class containing a list of {@code Record} objects to be used in tests.
 */
public class TypicalRecords {

    public static final Record FIRST = new RecordBuilder().withProcedure("Consultation-first visit").withDescription(
        "New patient came for a consultation today, no abnormalities so far").build();
    public static final Record SECOND = new RecordBuilder().withProcedure("Preventive-basic cleaning")
        .withDescription("Minor build up of plague that I've since removed").withDate("13-11-2010").build();
    public static final Record THIRD = new RecordBuilder().withProcedure("Filling-Standard")
        .withDescription("Filled up 2 front teeth with clear fillings").withDate("03-02-2011").build();
    public static final Record FOURTH = new RecordBuilder().withProcedure("Crown-Recrowning")
        .withDescription("Recrowned back molar.").withDate("11-02-2011").build();
    public static final Record FIFTH = new RecordBuilder().withProcedure("Gum-cleaning")
        .withDescription("Small gum infection detected, cleaned and given medicine.").withDate("22-08-2011").build();
    public static final Record SIXTH = new RecordBuilder().withProcedure("Extraction-Incisors")
        .withDescription("Removed rotten top incisors.").withDate("06-03-2012").build();
    public static final Record SEVENTH = new RecordBuilder().withProcedure("Replacement-Full")
        .withDescription("Full replacement of missing teeth").withDate("18-03-2012").build();
    public static final Record EIGHTH = new RecordBuilder().withProcedure("Braces-Temporary")
        .withDescription("Temporary braces, can be removed in a few months.").withDate("14-09-2012").build();
    public static final Record NINTH = new RecordBuilder().withProcedure("Aesthetic-whitening")
        .withDescription("Full mouth whitening").withDate("15-05-2014").build();
    public static final Record TENTH = new RecordBuilder().withProcedure("Implant-Metal")
        .withDescription("Temporary implant for future braces.").withDate("29-11-2015").build();

    private TypicalRecords() {} //prevents instantiation

    /**
     * Returns a list of typical records
     */
    public static ArrayList<Record> getTypicalRecords() {
        return new ArrayList<>(Arrays.asList(FIRST, SECOND, THIRD, FOURTH, FIFTH,
            SIXTH, SEVENTH, EIGHTH, NINTH, TENTH));
    }
}
