package seedu.address.logic.commands;

public class StudyDeckCommandTest {

//    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();
//
//    @Rule
//    public ExpectedException thrown = ExpectedException.none();
//
//    private Model modelDeck = new ModelManager(getTypicalTopDeck(), new UserPrefs());
//    private Model modelCard = new ModelManager(getTypicalTopDeck(), new UserPrefs());
//    private Model expectedModelDeck = new ModelManager(getTypicalTopDeck(), new UserPrefs());
//    private Model getExpectedModelCard = new ModelManager(getTypicalTopDeck(), new UserPrefs());
//    private CommandHistory commandHistory = new CommandHistory();
//    //from decks view
//    private DecksView decksView;
//    private Index index;
//    private StudyDeckCommand studyDeckTargetIndex;
//    //from cards view
//    private CardsView cardsView;
//    private StudyDeckCommand studyDeckTargetDeck;
//
//
//    @Before
//    public void initializeDecksView() {
//        assertTrue(modelDeck.isAtDecksView());
//        decksView = (DecksView) modelDeck.getViewState();
//    }
//
//    @Before
//    public void initializeCardsView() {
//        modelCard.changeDeck(getTypicalDeck());
//        assertTrue(modelCard.isAtCardsView());
//        cardsView = (CardsView) modelCard.getViewState();
//        getExpectedModelCard.changeDeck(getTypicalDeck());
//        assertTrue(getExpectedModelCard.isAtCardsView());
//    }
//
//    @Test
//    public void execute_validIndexUnfilteredList_success() {
//        assertExecutionSuccessIndex(INDEX_FIRST_DECK);
//    }
//
//    @Test
//    public void execute_invalidIndexUnfilteredList_failure() {
//        Index outOfBoundsIndex = Index.fromOneBased(decksView.getFilteredList().size() + 1);
//
//        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_DISPLAYED_INDEX);
//    }
//
//    @Test
//    public void execute_emptyDeck_failure() {
//        assertExecutionFailure(INDEX_EMPTY_DECK, Messages.MESSAGE_EMPTY_DECK);
//    }
//
//    @Test
//    public void execute_validIndexFilteredList_success() {
//        showDeckAtIndex(modelDeck, INDEX_FIRST_DECK);
//        showDeckAtIndex(expectedModelDeck, INDEX_FIRST_DECK);
//
//        assertExecutionSuccessIndex(INDEX_FIRST_DECK);
//    }
//
//    @Test
//    public void execute_invalidIndexFilteredList_failure() {
//        showDeckAtIndex(modelDeck, INDEX_FIRST_DECK);
//        showDeckAtIndex(expectedModelDeck, INDEX_FIRST_DECK);
//
//        Index outOfBoundsIndex = INDEX_SECOND_DECK;
//        // ensures that outOfBoundIndex is still in bounds of TopDeck list
//        assertTrue(outOfBoundsIndex.getZeroBased() < modelDeck.getTopDeck().getDeckList().get(0)
//                                                              .getCards().internalList.size());
//
//        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_DISPLAYED_INDEX);
//    }
//
//    @Test
//    public void execute_validDeck_success() {
//        StudyDeckCommand studyDeckCommand = new StudyDeckCommand(getTypicalDeck());
//        String expectedMessage = String
//                .format(StudyDeckCommand.MESSAGE_STUDY_DECK_SUCCESS, getTypicalDeck().getName());
//        CommandResult expectedCommandResult = new StudyPanelCommandResult(expectedMessage);
//        expectedModelDeck.studyDeck(getTypicalDeck());
//        assertCommandSuccess(studyDeckCommand, modelDeck, commandHistory, expectedCommandResult,
//                             expectedModelDeck);
//    }
//
//    @Test
//    public void equals() {
//        StudyDeckCommand studyFirstCommand = new StudyDeckCommand(decksView, INDEX_FIRST_DECK);
//        StudyDeckCommand studySecondCommand = new StudyDeckCommand(decksView, INDEX_SECOND_DECK);
//
//        // same object -> returns true
//        assertTrue(studyFirstCommand.equals(studyFirstCommand));
//
//        // same values -> returns true
//        StudyDeckCommand studyFirstCommandCopy = new StudyDeckCommand(decksView, INDEX_FIRST_DECK);
//        assertTrue(studyFirstCommand.equals(studyFirstCommandCopy));
//
//        // different types -> returns false
//        assertFalse(studyFirstCommand.equals(1));
//
//        // null -> returns false
//        assertFalse(studyFirstCommand.equals(null));
//
//        // different card -> returns false
//        assertFalse(studyFirstCommand.equals(studySecondCommand));
//    }
//
//    /**
//     * Executes a {@code StudyCommand} with the given {@code index},
//     * and checks that the modelDeck's viewstate is changed to studyView with active deck being
//     * the deck with given Index
//     */
//    private void assertExecutionSuccessIndex(Index index) {
//        StudyDeckCommand studyDeckCommand = new StudyDeckCommand(decksView, index);
//        String expectedMessage = String.format(StudyDeckCommand.MESSAGE_STUDY_DECK_SUCCESS,
//                                               decksView.getFilteredList().get(index.getZeroBased())
//                                                        .getName());
//        CommandResult expectedCommandResult = new StudyPanelCommandResult(expectedMessage);
//        expectedModelDeck.studyDeck(decksView.getFilteredList().get(index.getZeroBased()));
//        assertCommandSuccess(studyDeckCommand, modelDeck, commandHistory, expectedCommandResult,
//                             expectedModelDeck);
//    }
//
//    /**
//     * Executes a {@code StudyDeckCommand} with the given {@code index}, and checks that a {@code
//     * CommandException}
//     * is thrown with the {@code expectedMessage}.
//     */
//    private void assertExecutionFailure(Index index, String expectedMessage) {
//        StudyDeckCommand studyDeckCommand = new StudyDeckCommand(decksView, index);
//        assertCommandFailure(studyDeckCommand, modelDeck, commandHistory, expectedMessage);
//    }
}
