package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalHealthWorkers.ANDY;
import static seedu.address.testutil.TypicalHealthWorkers.BETTY;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.HealthWorker;
import seedu.address.testutil.Assert;
import seedu.address.testutil.HealthWorkerBuilder;

public class AddHealthWorkerCommandTest extends AddCommandTest {

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new
                AddHealthWorkerCommand(null));
    }

    @Test
    public void execute() throws CommandException {
        ModelStubAcceptingHealthWorkerAdded modelStub = new
                ModelStubAcceptingHealthWorkerAdded();
        HealthWorker validWorker = new HealthWorkerBuilder().build();

        CommandResult commandResult = new AddHealthWorkerCommand(validWorker)
                .execute(modelStub, commandHistory);

        assertEquals(String.format(AddHealthWorkerCommand.MESSAGE_SUCCESS, validWorker),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validWorker), modelStub.healthWorkersAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);

        // Duplicate health worker
        HealthWorker newValidWorker = new HealthWorkerBuilder().build();
        thrown.expectMessage(AddHealthWorkerCommand.DUPLICATE_HEALTH_WORKER);
        thrown.expect(CommandException.class);
        new AddHealthWorkerCommand(newValidWorker).execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        AddHealthWorkerCommand addAndyCommand = new AddHealthWorkerCommand(ANDY);
        AddHealthWorkerCommand addBettyCommand = new AddHealthWorkerCommand(BETTY);

        // same object -> return true
        assertTrue(addAndyCommand.equals(addAndyCommand));

        // same values -> return true
        assertTrue(addBettyCommand.equals(new AddHealthWorkerCommand(BETTY)));

        // different types -> return false
        assertFalse(addAndyCommand.equals(1));

        // null command -> return false
        assertFalse(addAndyCommand.equals(null));

        // different health workers -> return false
        assertFalse(addAndyCommand.equals(addBettyCommand));
    }

    /**
     * Model Stub class for accepting HealthWorker objects.
     */
    private class ModelStubAcceptingHealthWorkerAdded extends ModelStub {
        private ArrayList<HealthWorker> healthWorkersAdded = new ArrayList<>();

        @Override
        public void addHealthWorker(HealthWorker healthWorker) {
            requireNonNull(healthWorker);
            this.healthWorkersAdded.add(healthWorker);
        }

        @Override
        public boolean hasHealthWorker(HealthWorker healthWorker) {
            requireNonNull(healthWorker);
            return this.healthWorkersAdded.stream().anyMatch(
                    healthWorker::isSameHealthWorker);
        }

        @Override
        public void commitAddressBook() {
            // called by {@code AddHealthWorkerCommand#execute()}
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
