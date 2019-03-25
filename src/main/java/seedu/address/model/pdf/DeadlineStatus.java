package seedu.address.model.pdf;

/**
 * Represents a Pdf deadline's  status in the pdf book.
 * Guarantees: immutable;
 */
public enum DeadlineStatus {
    REMOVE("REMOVE"),
    READY("READY"),
    COMPLETE("COMPLETE");

    private String status;

    DeadlineStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }

    @Override
    public String toString() {
        return status;
    }
}
