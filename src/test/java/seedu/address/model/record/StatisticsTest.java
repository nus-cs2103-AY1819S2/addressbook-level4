package seedu.address.model.record;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;

import seedu.address.model.consultation.Prescription;
import seedu.address.model.medicine.Medicine;

class StatisticsTest {
    private Statistics stats;
    private Record record1;
    private Record record2;

    /**
     * Setup before each test
     */
    @BeforeEach
    public void init() {
        Medicine medicine = new Medicine("test");
        record1 = new ConsultationRecord(new Prescription(medicine, 1));
        record2 = new MedicinePurchaseRecord(medicine, 1, BigDecimal.valueOf(10.00));
        stats = new Statistics(1, BigDecimal.valueOf(30.00), BigDecimal.valueOf(10.00));
    }

    @Test
    void merge() {
        Statistics testStats = record1.toStatistics().merge(record2.toStatistics());
        Assert.assertEquals(testStats, stats);
    }

    @Test
    void fromRecordList() {
        List<Record> recordList = new ArrayList<>();
        recordList.add(record1);
        recordList.add(record2);
        Statistics testStats = Statistics.fromRecordList(recordList);
        Assert.assertEquals(testStats, stats);
    }

    @Test
    void toStringTest() {
        StringBuilder sb = new StringBuilder();
        sb.append("Number of consultations: ")
                .append(1)

                .append("\n")
                .append("Revenue: ")
                .append(Statistics.currencyFormat(BigDecimal.valueOf(30.00)))
                .append("\n")
                .append("Expenditure: ")
                .append(Statistics.currencyFormat(BigDecimal.valueOf(10.00)))
                .append("\n")
                .append("Profit: ")
                .append(Statistics.currencyFormat(BigDecimal.valueOf(20.00)))
                .append("\n\n");
        Assert.assertEquals(stats.toString(), sb.toString());
    }
}
