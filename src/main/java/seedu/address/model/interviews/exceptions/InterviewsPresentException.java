package seedu.address.model.job.exceptions;

public class InterviewsPresentException extends RuntimeException {
    public InterviewsPresentException() {
        super("Interviews already present");
    }
}
