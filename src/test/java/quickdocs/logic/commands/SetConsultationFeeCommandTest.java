package quickdocs.logic.commands;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import quickdocs.logic.CommandHistory;
import quickdocs.logic.commands.exceptions.CommandException;
import quickdocs.model.ModelManager;
import quickdocs.model.record.Statistics;

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
