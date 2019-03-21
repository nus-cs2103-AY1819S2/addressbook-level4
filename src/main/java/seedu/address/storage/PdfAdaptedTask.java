package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.datetime.DateCustom;
import seedu.address.model.task.Task;
import seedu.address.model.task.Title;


/**
 * PDF-friendly version of {@link Task}.
 */
class PdfAdaptedTask {

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
}
