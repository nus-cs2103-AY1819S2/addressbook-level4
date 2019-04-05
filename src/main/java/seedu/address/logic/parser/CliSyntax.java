package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_GENDER = new Prefix("g/");
    public static final Prefix PREFIX_AGE = new Prefix("a/");
    public static final Prefix PREFIX_YEAR = new Prefix("y/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("adr/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_SPECIALISATION = new Prefix("s/");
    public static final Prefix PREFIX_WRITEUP = new Prefix("sw/");
    public static final Prefix PREFIX_DESCRIPTION = new Prefix("d/");
    public static final Prefix PREFIX_MEDICINE_NAME = new Prefix("mn/");


    // prefixes for Appointment
    public static final Prefix PREFIX_PATIENT_ID = new Prefix("pid/");
    public static final Prefix PREFIX_DOCTOR_ID = new Prefix("did/");
    public static final Prefix PREFIX_DATE_OF_APPT = new Prefix("d/");
    public static final Prefix PREFIX_START_TIME = new Prefix("t/");

    public static final Prefix PREFIX_DATE_OF_MEDHIST = new Prefix("d/");
}
