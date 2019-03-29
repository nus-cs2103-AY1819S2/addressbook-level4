package seedu.address.logic.commands;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ModelManager;
import seedu.address.model.record.Statistics;

class SetConsultationFeeCommandTest {

    private ModelManager modelManager = new ModelManager();
    private final CommandHistory history = new CommandHistory();
    private SetConsultationFeeCommand command1;
    private SetConsultationFeeCommand command2;
    private BigDecimal fee;

    /**
     * Initializer for all the test cases
     */
    @BeforeEach
    void init() {
        fee = BigDecimal.valueOf(30.00);
        command1 = new SetConsultationFeeCommand(fee);
        command2 = new SetConsultationFeeCommand(fee);
    }

    @Test
    void execute() {
        try {
            CommandResult commandResult = command1.execute(modelManager, history);
            StringBuilder sb = new StringBuilder();
            sb.append("Consultation fee has been successfully changed to " + Statistics.currencyFormat(fee));
            sb.append("\n\n");
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
