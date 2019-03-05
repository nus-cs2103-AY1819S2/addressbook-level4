package seedu.address.model.record;

/**
 * s
 */
public class ConsultationStatistics extends Statistics {
    public ConsultationStatistics(Statistics stats) {
        super(stats);
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Number of consultations: ")
                .append(getNoOfConsultations())
                .append("\n\n");
        return sb.toString();
    }
}
