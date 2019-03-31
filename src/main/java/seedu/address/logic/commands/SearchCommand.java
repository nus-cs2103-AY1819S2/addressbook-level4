package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_JOBSAPPLY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_KNOWNPROGLANG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MAJOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASTJOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RACE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SCHOOL;

import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.predicate.PredicateManager;


/**
 * Searches and lists all persons in address book whose information contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class SearchCommand extends Command {

    public static final String COMMAND_WORD = "search";
    public static final String COMMAND_ALIAS = "sh";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Search all persons whose informations contain any of "
        + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
        + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
        + "[" + PREFIX_NAME + "NAME KEYWORD] "
        + "[" + PREFIX_PHONE + "PHONE KEYWORD] "
        + "[" + PREFIX_EMAIL + "EMAIL KEYWORD] "
        + "[" + PREFIX_NRIC + "NRIC KEYWORD] "
        + "[" + PREFIX_GENDER + "GENDER KEYWORD] "
        + "[" + PREFIX_RACE + "RACE KEYWORD] "
        + "[" + PREFIX_ADDRESS + "ADDRESS KEYWORD] "
        + "[" + PREFIX_SCHOOL + "SCHOOL KEYWORD] "
        + "[" + PREFIX_MAJOR + "MAJOR KEYWORD] "
        + "[" + PREFIX_SCHOOL + "SCHOOL KEYWORD] "
        + "[" + PREFIX_KNOWNPROGLANG + "KNOWNPROGLANG KEYWORD] "
        + "[" + PREFIX_PASTJOB + "PASTJOB KEYWORD] "
        + "[" + PREFIX_JOBSAPPLY + "JOBSAPPLY KEYWORD] "
        + "Example: " + COMMAND_WORD
        + PREFIX_PHONE + "91234567 "
        + PREFIX_EMAIL + "johndoe@example.com"
        + PREFIX_NRIC + "S9671597H "
        + PREFIX_GENDER + "Male "
        + PREFIX_RACE + "Indian "
        + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
        + PREFIX_SCHOOL + "NUS "
        + PREFIX_MAJOR + "Computer Science "
        + "The alias \"sh\" can be used instead.\n"
        + "Example: " + COMMAND_ALIAS
        + PREFIX_PHONE + "91234567 "
        + PREFIX_EMAIL + "johndoe@example.com "
        + PREFIX_NRIC + "S9671597H "
        + PREFIX_GENDER + "Male "
        + PREFIX_RACE + "Indian "
        + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
        + PREFIX_SCHOOL + "NUS "
        + PREFIX_MAJOR + "Computer Science"
        + PREFIX_JOBSAPPLY + "Software Engineer ";


    private final Predicate<Person> predicate;
    private final PredicatePersonDescriptor predicatePersonDescriptor;

    public PredicatePersonDescriptor getPredicatePersonDescriptor() {
        return predicatePersonDescriptor;
    }

    public SearchCommand(PredicatePersonDescriptor predicatePersonDescriptor) {
        requireNonNull(predicatePersonDescriptor);
        this.predicatePersonDescriptor = new PredicatePersonDescriptor(predicatePersonDescriptor);
        Predicate<Person> predicator =
            new PredicateManager().translatePredicateDescriptor(this.predicatePersonDescriptor);
        this.predicate = predicator;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        return new CommandResult(
            String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SearchCommand)) {
            return false;
        }

        // state check
        SearchCommand e = (SearchCommand) other;
        return predicatePersonDescriptor.equals(e.predicatePersonDescriptor);
    }
    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class PredicatePersonDescriptor {
        private Set<String> name;
        private Set<String> phone;
        private Set<String> email;
        private Set<String> nric;
        private Set<String> gender;
        private Set<String> race;
        private Set<String> address;
        private Set<String> school;
        private Set<String> major;
        private Set<String> jobsApply;
        private Set<String> knownProgLangs;
        private Set<String> pastJobs;

        public PredicatePersonDescriptor() {
        }

        /**
         * Copy constructor.
         * * A defensive copy of {@code pastjobs} is used internally.
         * A defensive copy of {@code tags} is used internally.
         */
        public PredicatePersonDescriptor(PredicatePersonDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setNric(toCopy.nric);
            setGender(toCopy.gender);
            setRace(toCopy.race);
            setAddress(toCopy.address);
            setSchool(toCopy.school);
            setMajor(toCopy.major);
            setKnownProgLangs(toCopy.knownProgLangs);
            setPastJobs(toCopy.pastJobs);
            setJobsApply(toCopy.jobsApply);
        }

        public void setName(Set<String> name) {
            this.name = name;
        }

        public Optional<Set<String>> getName() {
            return Optional.ofNullable(name);
        }

        public void setPhone(Set<String> phone) {
            this.phone = phone;
        }

        public Optional<Set<String>> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setEmail(Set<String> email) {
            this.email = email;
        }

        public Optional<Set<String>> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setNric(Set<String> nric) {
            this.nric = nric;
        }

        public Optional<Set<String>> getNric() {
            return Optional.ofNullable(nric);
        }

        public void setGender(Set<String> gender) {
            this.gender = gender;
        }

        public Optional<Set<String>> getGender() {
            return Optional.ofNullable(gender);
        }

        public void setRace(Set<String> race) {
            this.race = race;
        }

        public Optional<Set<String>> getRace() {
            return Optional.ofNullable(race);
        }

        public void setAddress(Set<String> address) {
            this.address = address;
        }

        public Optional<Set<String>> getAddress() {
            return Optional.ofNullable(address);
        }

        public void setSchool(Set<String> school) {
            this.school = school;
        }

        public Optional<Set<String>> getSchool() {
            return Optional.ofNullable(school);
        }

        public void setMajor(Set<String> major) {
            this.major = major;
        }

        public Optional<Set<String>> getMajor() {
            return Optional.ofNullable(major);
        }

        public void setKnownProgLangs(Set<String> knownProgLangs) {
            this.knownProgLangs = knownProgLangs;
        }

        public Optional<Set<String>> getKnownProgLangs() {
            return Optional.ofNullable(knownProgLangs);
        }

        public void setPastJobs(Set<String> pastJobs) {
            this.pastJobs = pastJobs;
        }

        public Optional<Set<String>> getPastJobs() {
            return Optional.ofNullable(pastJobs);
        }

        public void setJobsApply(Set<String> jobsApply) {
            this.jobsApply = jobsApply;
        }

        public Optional<Set<String>> getJobsApply() { return Optional.ofNullable(jobsApply); }


    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PredicatePersonDescriptor)) {
            return false;
        }

        // state check
        PredicatePersonDescriptor e = (PredicatePersonDescriptor) other;

        return getName().equals(e.getName())
            && getPhone().equals(e.getPhone())
            && getEmail().equals(e.getEmail())
            && getNric().equals(e.getNric())
            && getGender().equals(e.getGender())
            && getRace().equals(e.getRace())
            && getAddress().equals(e.getAddress())
            && getSchool().equals(e.getSchool())
            && getMajor().equals(e.getMajor())
            && getKnownProgLangs().equals(e.getKnownProgLangs())
            && getPastJobs().equals(e.getPastJobs())
            && getJobsApply().equals(e.getJobsApply());
    }
    }
}
