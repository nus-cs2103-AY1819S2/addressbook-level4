package seedu.equipment.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import seedu.equipment.model.equipment.Address;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    /** The application should show list of client's equipment in the equipment details panel. */
    private final boolean selectClient;

    /** Help information should be shown to the user. */
    private final boolean showHelp;

    /** The application should exit. */
    private final boolean exit;

    /** The application should show map on right hand side with equipment locations. */
    private final boolean displayMap;

    /** The application should show map on right hand side with route to equipments. */
    private final boolean route;

    /** The address send back to UI to display route. */
    private final Address routeAddress;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit) {
        this(feedbackToUser, showHelp, exit, false);
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false);
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit, boolean displayMap) {
        this(feedbackToUser, showHelp, exit, displayMap, false, null);
    }

    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit, boolean displayMap,
                         boolean route, Address routeAddress) {
        this(feedbackToUser, showHelp, exit, displayMap, route, routeAddress, false);
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit, boolean displayMap,
                         boolean route, Address routeAddress, boolean selectClient) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
        this.displayMap = displayMap;
        this.route = route;
        this.routeAddress = routeAddress;
        this.selectClient = selectClient;
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public boolean isShowHelp() {
        return showHelp;
    }

    public boolean isExit() {
        return exit;
    }

    public boolean isDisplayMap() {
        return displayMap;
    }

    public boolean isRoute() {
        return route;
    }

    public Address getRouteAddress() {
        return routeAddress;
    }

    public boolean isSelectClient() {
        return selectClient;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CommandResult)) {
            return false;
        }

        CommandResult otherCommandResult = (CommandResult) other;
        return feedbackToUser.equals(otherCommandResult.feedbackToUser)
                && showHelp == otherCommandResult.showHelp
                && exit == otherCommandResult.exit
                && displayMap == otherCommandResult.displayMap
                && route == otherCommandResult.route
                && ((routeAddress == null && otherCommandResult.routeAddress == null)
                    || (routeAddress != null && routeAddress.equals(otherCommandResult.routeAddress)))
                && selectClient == otherCommandResult.selectClient;

    }

    /**
     * Hashcode of the object
     * @return the hashcode of the object
     */
    public int hashCode() {
        return Objects.hash(feedbackToUser, showHelp, exit, displayMap, route, routeAddress,
                selectClient);
    }

}
