package seedu.address.logic.commands.request;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HEALTHWORKER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REQUEST;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringJoiner;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.healthworker.HealthWorker;
import seedu.address.model.request.Request;

/**
 * Assigns multiple requests to a HealthWorker with two way association.
 */
public class AssignRequestCommand extends Command {

    public static final String COMMAND_WORD = "assign";

    public static final String MESSAGE_SUCCESS = "Assigned request %1$s successfully to healthworker %2$s";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Assign requests to healthworker. "
        + "Parameters: "
        + PREFIX_HEALTHWORKER + "HEALTHWORKER_ID "
        + PREFIX_REQUEST + "REQUEST_ID \n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_HEALTHWORKER + "1 "
        + PREFIX_REQUEST + "1 " + PREFIX_REQUEST + "3";

    private final Index healthworkerId;
    private final Set<Index> requestIds;

    /**
     * Creates an assign request command to assign requests to a {@code HealthWorker}
     */
    public AssignRequestCommand(Index healthworkerId, Set<Index> requestIds) {
        requireAllNonNull(healthworkerId, requestIds);
        this.healthworkerId = healthworkerId;
        this.requestIds = requestIds;
    }


    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<HealthWorker> lastShownHealthworkerList = model.getFilteredHealthWorkerList();
        List<Request> lastShownRequestList = model.getFilteredRequestList();
        Set<Request> requestsToAdd = new HashSet<>();

        if (healthworkerId.getZeroBased() >= lastShownHealthworkerList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_HEALTHWORKER_DISPLAYED_INDEX);
        }

        HealthWorker healthWorkerToAssign =
            lastShownHealthworkerList.get(healthworkerId.getZeroBased());

        for (Index i : requestIds) {
            if (i.getZeroBased() >= lastShownRequestList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_REQUEST_DISPLAYED_INDEX);
            }

            Request request = lastShownRequestList.get(i.getZeroBased());
            requestsToAdd.add(request);
        }

        HealthWorker assignedHealthWorker = new HealthWorker(healthWorkerToAssign);
        for (Request request : requestsToAdd) {
            Request updatedRequest = new Request(request);
            updatedRequest.setHealthWorker(assignedHealthWorker);
            model.updateRequest(request, updatedRequest);
        }

        model.updateFilteredRequestList(Model.PREDICATE_SHOW_ALL_REQUESTS);
        model.commitRequestBook();

        StringJoiner sj = new StringJoiner(", ");
        for (Index i : requestIds) {
            sj.add(Integer.toString(i.getOneBased()));
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, sj.toString(),
            assignedHealthWorker));

    }

    @Override
    public boolean equals(Object other) {
        return other == this
            || (other instanceof AssignRequestCommand
            && healthworkerId.equals(((AssignRequestCommand) other).healthworkerId)
            && requestIds.equals(((AssignRequestCommand) other).requestIds));
    }
}
