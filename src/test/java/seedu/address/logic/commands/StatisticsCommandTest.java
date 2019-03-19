package seedu.address.logic.commands;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.YearMonth;
import java.time.ZoneId;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ModelManager;
import seedu.address.model.consultation.Prescription;
import seedu.address.model.medicine.Medicine;
import seedu.address.model.record.ConsultationRecord;
import seedu.address.model.record.MedicinePurchaseRecord;
import seedu.address.model.record.Record;
import seedu.address.model.record.Statistics;

class StatisticsCommandTest {

    private ModelManager modelManager = new ModelManager();
    private final CommandHistory history = new CommandHistory();
    private YearMonth from = YearMonth.of(2019, 1);
    private YearMonth to = YearMonth.of(2019, 2);
    private StatisticsCommand command1;
    private StatisticsCommand command2;
    private Record record1;
    private Record record2;
    private Clock clock;

    /**
     * Initializer for all the test cases
     */
    @BeforeEach
    void init() {
        String topic = "all";
        Medicine medicine = new Medicine("test");
        record1 = new ConsultationRecord(new Prescription(medicine, 1));
        record2 = new MedicinePurchaseRecord(medicine, 1, BigDecimal.valueOf(10.00));
        command1 = new StatisticsCommand(topic, from, to);
        command2 = new StatisticsCommand(topic, from, to);
        clock = Clock.fixed(Instant.parse("2019-01-01T10:15:30.00Z"), ZoneId.systemDefault());
    }

    @Test
    void execute() {
        modelManager.addRecord(record1, clock);
        clock = Clock.offset(clock, Duration.ofDays((long) 32));
        modelManager.addRecord(record2, clock);
        try {
            CommandResult commandResult = command1.execute(modelManager, history);
            StringBuilder sb = new StringBuilder();
            sb.append("Displaying result for all from 2019-01 to 2019-02.\n")
                    .append("Number of consultations: ")
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
            Assert.assertEquals(commandResult.getFeedbackToUser(), sb.toString());
        } catch (CommandException ce) {
            Assert.fail();
        }
    }

    @Test
    void equals() {
        Assert.assertTrue(command1.equals(command2));
    }
}
