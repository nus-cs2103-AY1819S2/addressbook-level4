package seedu.address.model.record;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

class ConsultationStatisticsTest {
    private Statistics stats = new Statistics(3, BigDecimal.ZERO, BigDecimal.ZERO);
    private Statistics consultStats = new ConsultationStatistics(stats);
    private StringBuilder sb = new StringBuilder();
    @Test
    void toStringTest() {
        sb.append("Number of consultations: ")
                .append(3)
                .append("\n\n");
        Assert.assertEquals(consultStats.toString(), sb.toString());
    }
}
