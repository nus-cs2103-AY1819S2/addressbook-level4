package seedu.address.model.record;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

class FinancesStatisticsTest {
    private Statistics stats = new Statistics(3, BigDecimal.valueOf(39.20), BigDecimal.valueOf(10.10));
    private Statistics financesStats = new FinancesStatistics(stats);
    private StringBuilder sb = new StringBuilder();
    @Test
    void toStringTest() {
        sb.append("Revenue: ")
                .append(Statistics.currencyFormat(BigDecimal.valueOf(39.20)))
                .append("\n")
                .append("Expenditure: ")
                .append(Statistics.currencyFormat(BigDecimal.valueOf(10.10)))
                .append("\n")
                .append("Profit: ")
                .append(Statistics.currencyFormat(BigDecimal.valueOf(29.10)))
                .append("\n\n");
        Assert.assertEquals(financesStats.toString(), sb.toString());
    }
}
