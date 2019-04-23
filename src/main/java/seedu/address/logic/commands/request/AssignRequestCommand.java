package seedu.address.logic.commands.request;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HEALTHWORKER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REQUEST;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringJoiner;
import java.util.TreeSet;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
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
public class AssignRequestCommand extends Command implements RequestCommand {

    public static final String COMMAND_WORD = "assign";

    public static final int MIN_REQUEST_DURATION = 2; // buffer between requests should be at
    // least 2 hours

    public static final String MESSAGE_SUCCESS = "Assigned request %1$s successfully to healthworker %2$s";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Assign requests to healthworker. "
        + "Parameters: "
        + PREFIX_HEALTHWORKER + "HEALTHWORKER_ID "
        + PREFIX_REQUEST + "REQUEST_ID \n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_HEALTHWORKER + "1 "
        + PREFIX_REQUEST + "1 " + PREFIX_REQUEST + "3";

    private static final Logger logger = LogsCenter.getLogger(AssignRequestCommand.class);

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
            logger.warning("Invalid healthworker index " + healthworkerId.getOneBased()
                + " accessed for assign");
            throw new CommandException(Messages.MESSAGE_INVALID_HEALTHWORKER_DISPLAYED_INDEX);
        }

        HealthWorker healthWorkerToAssign =
            lastShownHealthworkerList.get(healthworkerId.getZeroBased());

        for (Index i : requestIds) {
            if (i.getZeroBased() >= lastShownRequestList.size()) {
                logger.warning("Invalid request index " + healthworkerId.getOneBased()
                    + " accessed for assign");
                throw new CommandException(Messages.MESSAGE_INVALID_REQUEST_DISPLAYED_INDEX);
            }

            Request request = lastShownRequestList.get(i.getZeroBased());
            if (request.isCompleted()) {
                logger.info("Error: attempted to assign to a completed request at index " + i.getOneBased());
                throw new CommandException(Messages.MESSAGE_REQUEST_COMPLETED_CANNOT_ASSIGN);
            }
            requestsToAdd.add(request);
        }

        HealthWorker assignedHealthWorker = new HealthWorker(healthWorkerToAssign);

        // First, adds all the date and time that the current healthworker is assigned to
        TreeSet<Date> healthWorkerAppointments = new TreeSet<>();

        for (Request req : lastShownRequestList) {
            if (assignedHealthWorker.getNric().toString().equals(req.getHealthStaff()) && req.isOngoingStatus()) {
                healthWorkerAppointments.add(req.getRequestDate().getDate());
            }
        }

        Calendar calendar = Calendar.getInstance();

        for (Request request : requestsToAdd) {

            if (healthWorkerToAssign.getNric().toString().equals(request.getHealthStaff())) {
                throw new CommandException(Messages.MESSAGE_HEALTHWORKER_ALREADY_ASSIGNED);
            }

            Date date = request.getRequestDate().getDate();
            calendar.setTime(date);
            calendar.add(Calendar.HOUR_OF_DAY, -MIN_REQUEST_DURATION);
            Date lowerLimit = calendar.getTime();
            logger.info("Lower limit: " + lowerLimit);
            calendar.add(Calendar.HOUR_OF_DAY, 2 * MIN_REQUEST_DURATION);
            Date upperLimit = calendar.getTime();
            logger.info("Upper limit: " + upperLimit);

            if (healthWorkerAppointments.contains(date) || (healthWorkerAppointments.lower(date) != null
                && healthWorkerAppointments.lower(date).after(lowerLimit))
                || (healthWorkerAppointments.higher(date) != null
                && healthWorkerAppointments.ceiling(date).before(upperLimit))) {
                throw new CommandException(Messages.MESSAGE_HEALTHWORKER_OCCUPIED_CANNOT_ASSIGN);
            }

            healthWorkerAppointments.add(date);

        }

        for (Request request : requestsToAdd) {
            Request updatedRequest = new Request(request);
            updatedRequest.setHealthWorker(assignedHealthWorker);
            model.updateRequest(request, updatedRequest);
        }

        model.updateFilteredRequestList(Model.PREDICATE_SHOW_ALL_REQUESTS);
        commitRequestBook(model);

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
