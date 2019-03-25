package seedu.address.model.pdf;

public enum DeadlineStatus {
    REMOVE("REMOVE"),
    READY("READY"),
    COMPLETE("COMPLETE");

    public String status;

    DeadlineStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }
}
