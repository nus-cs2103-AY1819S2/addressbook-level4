package seedu.address.storage;

import java.util.ArrayList;

import seedu.address.model.task.Task;


/**
 * PDF-friendly version of {@link Task}.
 */
class PdfAdaptedTask implements PdfAdaptedInterface {

    public static final int ATTRIBUTES = 3;
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Task's %s field is missing!";

    private final String title;
    private final String startdate;
    private final String enddate;

    /**
     * Constructs a {@code PdfAdaptedTask} with the given task details.
     */
    public PdfAdaptedTask(String title, String startdate, String enddate) {
        this.title = title;
        this.startdate = startdate;
        this.enddate = enddate;

    }

    /**
     * Converts a given {@code Task} into this class for PDF use.
     */
    public PdfAdaptedTask(Task source) {
        title = source.getTitle().toString();
        startdate = source.getStartDate().toString();
        enddate = source.getEndDate().toString();
    }

    /**
     * Creates a {@code String[]} for PDF exporting.
     * @return the attributes of a PdfAdaptedTask
     */
    @Override
    public ArrayList<String> getStrings() {
        ArrayList<String> stringArray = new ArrayList<>(ATTRIBUTES);
        stringArray.add("Title: " + title);
        stringArray.add("Start date: " + startdate);
        stringArray.add("End date: " + enddate);

        return stringArray;
    }
}
