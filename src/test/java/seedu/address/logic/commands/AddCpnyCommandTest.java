package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.person.Company;
import seedu.address.model.person.Person;
import seedu.address.testutil.CompanyBuilder;

public class AddCpnyCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullCompany_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddCommand(null);
    }

    @Test
    public void execute_companyAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingCompanyAdded modelStub = new ModelStubAcceptingCompanyAdded();
        Company validCompany = new CompanyBuilder().build();

        CommandResult commandResult = new AddCpnyCommand(validCompany).execute(modelStub, commandHistory);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validCompany), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validCompany), modelStub.companiesAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_duplicateCompany_throwsCommandException() throws Exception {
        Company validCompany = new CompanyBuilder().build();
        AddCpnyCommand addCpnyCommand = new AddCpnyCommand(validCompany);
        ModelStub modelStub = new ModelStubWithCompany(validCompany);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddCpnyCommand.MESSAGE_DUPLICATE_COMPANY);
        addCpnyCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        Company burger = new CompanyBuilder().withName("Burger").build();
        Company hardy = new CompanyBuilder().withName("Hardy").build();
        AddCpnyCommand addBurgerCommand = new AddCpnyCommand(burger);
        AddCpnyCommand addHardyCommand = new AddCpnyCommand(hardy);

        // same object -> returns true
        assertTrue(addBurgerCommand.equals(addBurgerCommand));


        // different types -> returns false
        assertFalse(addBurgerCommand.equals(1));

        // null -> returns false
        assertFalse(addBurgerCommand.equals(null));

        // different company -> returns false
        assertFalse(addBurgerCommand.equals(addHardyCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should" +
                    " not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be" +
                    " called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be" +
                    " called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be" +
                    " called.");
        }

        /**
         * Deletes a given company
         * The company must exist in the address book.
         *
         * @param target
         */
        @Override
        public void deleteCompany(Company target) {
            throw new AssertionError("This method should not be" +
                    " called.");
        }

        @Override
        public void addCompany(Company company) {
            throw new AssertionError("This method should not be" +
                    " called.");
        }

        @Override
        public void addFavorites(Person person) {
            throw new AssertionError("This method should not be" +
                    " called.");
        }

        @Override
        public void removeFavorite(Person person) {
            throw new AssertionError("This method should not be" +
                    " called.");
        }

        @Override
        public ObservableList<Person> getFavoritesList() {
            return null;
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be" +
                    " called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be" +
                    " called.");
        }

        @Override
        public boolean hasCompany(Company company) {
            /*
            throw new AssertionError("This method should not be" +
                    " called.");
                    */
            return true;
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be" +
                    " called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setCompany(Company target, Company editedCompany) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Company> getFilteredCompanyList() {
            return null;
        }

        @Override
        public void updateFilteredCompanyList(Predicate<Company> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canUndoAddressBook() {
            throw new AssertionError("This method should not be" +
                    " called.");
        }

        @Override
        public boolean canRedoAddressBook() {
            throw new AssertionError("This method should not be" +
                    " called.");
        }

        @Override
        public void undoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyProperty<Person> selectedPersonProperty() {
            throw new AssertionError("This method should not be called.");
        }

        /**
         * Selected company in the filtered company list.
         * null if no company is selected.
         */
        @Override
        public ReadOnlyProperty<Company> selectedCompanyProperty() {
            return null;
        }

        @Override
        public Person getSelectedPerson() {
            throw new AssertionError("This method should not be" +
                    " called.");
        }

        /**
         * Returns the selected company in the filtered company list.
         * null if no company is selected.
         */
        @Override
        public Company getSelectedCompany() {
            return null;
        }

        @Override
        public void setSelectedPerson(Person person) {
            throw new AssertionError("This method should not be" +
                    " called.");
        }

        /**
         * Sets the selected company in the filtered company list.
         *
         * @param company
         */
        @Override
        public void setSelectedCompany(Company company) {
            throw new AssertionError("This method should not be called.");
        }

        /**
         * Sort person by specific parameter.
         *
         * @param comPerson
         * @param sequence
         */
        @Override
        public void sortPerson(Comparator comPerson, String sequence) {
            throw new AssertionError("This method should not be called.");
        }

        /**
         * Sort company by specific parameter.
         *
         * @param comCompany
         * @param sequence
         */
        @Override
        public void sortCompany(Comparator comCompany, String sequence) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single person.
     */
    private class ModelStubWithCompany extends ModelStub {
        private final Company company;

        ModelStubWithCompany(Company company) {
            requireNonNull(company);
            this.company = company;
        }

        @Override
        public boolean hasCompany(Company company) {
            requireNonNull(company);
            return this.company.isSameCompany(company);
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingCompanyAdded extends ModelStub {
        final ArrayList<Person> personsAdded = new ArrayList<>();
        final ArrayList<Person> companiesAdded = new ArrayList<>();


        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return personsAdded.stream().anyMatch(person::isSamePerson);
        }

        @Override
        public void addPerson(Person person) {
            requireNonNull(person);
            personsAdded.add(person);
        }
        /*
        public boolean hasCompany(Company company) {
            requireNonNull(company);
            return companiesAdded.stream().anyMatch(company::isSameCompany);
        }
        */
        public void addCompany(Person company) {
            requireNonNull(company);
            companiesAdded.add(company);
        }

        @Override
        public void commitAddressBook() {
            // called by {@code AddCommand#execute()}
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}

