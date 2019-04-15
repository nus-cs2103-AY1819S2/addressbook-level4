package seedu.hms.ui;

import java.util.ArrayList;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.hms.commons.core.GuiSettings;
import seedu.hms.commons.core.LogsCenter;
import seedu.hms.commons.core.index.Index;
import seedu.hms.logic.Logic;
import seedu.hms.logic.LogicManager;
import seedu.hms.logic.commands.CommandResult;
import seedu.hms.logic.commands.exceptions.CommandException;
import seedu.hms.logic.parser.exceptions.ParseException;
import seedu.hms.logic.stats.Stats;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private CustomerListPanel customerListPanel;
    private BookingListPanel bookingListPanel;
    private ReservationListPanel reservationListPanel;
    private BillPanel billPanel;
    private BookingAndReservationPanel bookingAndReservationPanel;
    private ServiceTypeListPanel serviceTypeListPanel;
    private RoomTypeListPanel roomTypeListPanel;
    private ServiceTypeAndRoomTypePanel serviceTypeAndRoomTypePanel;
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;
    private StatsWindow statsWindow;

    @FXML
    private StackPane serviceTypeAndRoomTypePlaceholder;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane customerListPanelPlaceholder;

    @FXML
    private StackPane bookingAndReservationPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        setAccelerators();

        helpWindow = new HelpWindow();
        statsWindow = new StatsWindow(new Stats(this.logic.getHotelManagementSystem()));
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
    }

    /**
     * Sets the accelerator of a MenuItem.
     *
     * @param keyCombination the KeyCombination value of the accelerator
     */
    private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination) {
        menuItem.setAccelerator(keyCombination);

        /*
         * TODO: the code below can be removed once the bug reported here
         * https://bugs.openjdk.java.net/browse/JDK-8131666
         * is fixed in later version of SDK.
         *
         * According to the bug report, TextInputControl (TextField, TextArea) will
         * consume function-key events. Because CommandBox contains a TextField, and
         * ResultDisplay contains a TextArea, thus some accelerators (e.g F1) will
         * not work when the focus is in them because the key event is consumed by
         * the TextInputControl(s).
         *
         * For now, we add following event filter to capture such key events and open
         * help window purposely so to support accelerators even when focus is
         * in CommandBox or ResultDisplay.
         */
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                menuItem.getOnAction().handle(new ActionEvent());
                event.consume();
            }
        });
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        customerListPanel = new CustomerListPanel(logic.getFilteredCustomerList(), logic.selectedCustomerProperty(),
            logic::setSelectedCustomer);
        customerListPanelPlaceholder.getChildren().add(customerListPanel.getRoot());

        bookingListPanel = new BookingListPanel(logic.getFilteredBookingList(),
                logic.selectedBookingProperty(), logic::setSelectedBooking, //logic.selectedCustomerProperty(),
                logic.selectedServiceTypeProperty(), this::executeCommand);

        reservationListPanel = new ReservationListPanel(logic.getFilteredReservationList(),
                logic.selectedReservationProperty(), logic::setSelectedReservation, //logic.selectedCustomerProperty(),
                logic.selectedRoomTypeProperty(), this::executeCommand);

        billPanel = new BillPanel(logic.getBillModel());

        bookingAndReservationPanel = new BookingAndReservationPanel(bookingListPanel, reservationListPanel, billPanel,
                LogicManager.selectedPanelOneTabIndexProperty(), LogicManager::setSelectedPanelOneTabIndex);
        bookingAndReservationPanelPlaceholder.getChildren().add(bookingAndReservationPanel.getRoot());

        serviceTypeListPanel = new ServiceTypeListPanel(logic.getServiceTypeList(), logic.selectedServiceTypeProperty(),
                logic::setSelectedServiceType);

        roomTypeListPanel = new RoomTypeListPanel(logic.getRoomTypeList(), logic.selectedRoomTypeProperty(),
                logic::setSelectedRoomType);

        serviceTypeAndRoomTypePanel = new ServiceTypeAndRoomTypePanel(serviceTypeListPanel, roomTypeListPanel,
                LogicManager.selectedPanelTwoTabIndexProperty(), LogicManager::setSelectedPanelTwoTabIndex);
        serviceTypeAndRoomTypePlaceholder.getChildren().add(serviceTypeAndRoomTypePanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getHotelManagementSystemFilePath(),
            logic.getHotelManagementSystem());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand, logic.getHistory());
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }

    /**
     * Sets the default size based on {@code guiSettings}.
     */
    private void setWindowDefaultSize(GuiSettings guiSettings) {
        primaryStage.setHeight(guiSettings.getWindowHeight());
        primaryStage.setWidth(guiSettings.getWindowWidth());
        if (guiSettings.getWindowCoordinates() != null) {
            primaryStage.setX(guiSettings.getWindowCoordinates().getX());
            primaryStage.setY(guiSettings.getWindowCoordinates().getY());
        }
    }

    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    @FXML
    public void handleHelp() {
        if (!helpWindow.isShowing()) {
            helpWindow.show();
        } else {
            helpWindow.focus();
        }
    }

    /**
     * Opens the stats window or focuses on it if it's already opened.
     */
    @FXML
    public void handleStats(Optional<ArrayList<Index>> optionalIndexList) {
        statsWindow.update(optionalIndexList);
        if (!statsWindow.isShowing()) {
            statsWindow.show();
        } else {
            statsWindow.focus();
        }
    }

    void show() {
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
            (int) primaryStage.getX(), (int) primaryStage.getY());
        logic.setGuiSettings(guiSettings);
        helpWindow.hide();
        statsWindow.hide();
        primaryStage.hide();
    }


    public CustomerListPanel getCustomerListPanel() {
        return customerListPanel;
    }

    public BookingAndReservationPanel getBookingAndReservationPanel() {
        return bookingAndReservationPanel;
    }

    public BookingListPanel getBookingListPanel() {
        return bookingListPanel;
    }

    public ReservationListPanel getReservationListPanel() {
        return reservationListPanel;
    }

    public ServiceTypeListPanel getServiceTypeListPanel() {
        return serviceTypeListPanel;
    }

    public RoomTypeListPanel getRoomTypeListPanel() {
        return roomTypeListPanel;
    }

    public ServiceTypeAndRoomTypePanel getServiceTypeAndRoomTypePanel() {
        return serviceTypeAndRoomTypePanel;
    }

    public BillPanel getBillPanel() {
        return billPanel;
    }

    /**
     * Executes the command and returns the result.
     *
     * @see Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            if (commandResult.isShowHelp()) {
                handleHelp();
            }

            if (commandResult.isShowStats()) {
                handleStats(commandResult.getIndexList());
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }
}
