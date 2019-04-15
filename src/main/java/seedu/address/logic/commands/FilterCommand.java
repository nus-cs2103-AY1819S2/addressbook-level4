package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILTERNAME;
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
import static seedu.address.model.job.JobListName.APPLICANT;
import static seedu.address.model.job.JobListName.EMPTY;
import static seedu.address.model.job.JobListName.INTERVIEW;
import static seedu.address.model.job.JobListName.KIV;
import static seedu.address.model.job.JobListName.SHORTLIST;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.job.JobListName;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicateFilterException;
import seedu.address.model.person.predicate.AddressContainsKeywordsPredicate;
import seedu.address.model.person.predicate.EmailContainsKeywordsPredicate;
import seedu.address.model.person.predicate.GenderContainsKeywordsPredicate;
import seedu.address.model.person.predicate.GradeContainsKeywordsPredicate;
import seedu.address.model.person.predicate.InterviewScoreContainsKeywordsPredicate;
import seedu.address.model.person.predicate.JobsApplyContainsKeywordsPredicate;
import seedu.address.model.person.predicate.KnownProgLangContainsKeywordsPredicate;
import seedu.address.model.person.predicate.MajorContainsKeywordsPredicate;
import seedu.address.model.person.predicate.NameContainsKeywordsPredicate;
import seedu.address.model.person.predicate.NricContainsKeywordsPredicate;
import seedu.address.model.person.predicate.PastJobContainsKeywordsPredicate;
import seedu.address.model.person.predicate.PhoneContainsKeywordsPredicate;
import seedu.address.model.person.predicate.PredicateManager;
import seedu.address.model.person.predicate.RaceContainsKeywordsPredicate;
import seedu.address.model.person.predicate.SchoolContainsKeywordsPredicate;
import seedu.address.model.person.predicate.UniqueFilterList;


/**
 * Searches and lists all persons in address book whose information contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FilterCommand extends Command {

    public static final String COMMAND_WORD = "filter";
    public static final String COMMAND_ALIAS = "f";
    public static final String MESSAGE_USAGE_PARAMETERS =
        "[" + PREFIX_FILTERNAME + "FILTERNAME] "
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
            + PREFIX_EMAIL + "johndoe@example.com "
            + PREFIX_NRIC + "S9671597H "
            + PREFIX_GENDER + "Male "
            + PREFIX_RACE + "Indian "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_SCHOOL + "NUS "
            + PREFIX_MAJOR + "Computer Science "
            + "The alias \"sh \" can be used instead.\n"
            + "Example: " + COMMAND_ALIAS
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com "
            + PREFIX_NRIC + "S9671597H "
            + PREFIX_GENDER + "Male "
            + PREFIX_RACE + "Indian "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_SCHOOL + "NUS "
            + PREFIX_MAJOR + "Computer Science "
            + PREFIX_JOBSAPPLY + "Software Engineer \n";

    public static final String MESSAGE_USAGE_ALLJOB_SCREEN = COMMAND_WORD + ": (In All Jobs shows mode)\n"
        + "Filter all persons whose informations contain any of the specified keywords (case-insensitive) "
        + "and displays them as a list with index numbers.\n"
        + "Parameters: FILTERNAME [KEYWORDS]...\n" + MESSAGE_USAGE_PARAMETERS;


    public static final String MESSAGE_USAGE_JOB_DETAIL_SCREEN = COMMAND_WORD + ": (In Job Detail mode)\n"
        + "Filter indicated job list whose informations contain any of the specified keywords (case-insensitive) "
        + "and displays them as a list with index numbers.\n"
        + "Parameters: LISTNAME FILTERNAME [KEYWORDS]...\n"
        + "LISTNAME " + MESSAGE_USAGE_PARAMETERS;

    public static final String MESSAGE_LACK_FILTERNAME = "Filter Command need a name\n%1$s";
    public static final String MESSAGE_LACK_LISTNAME =
        "Filter Command in Display Job page need indicate job list\n%1$s";
    public static final String MESSAGE_REDUNDANT_LISTNAME =
        "Filter Command in All Jobs page no need indicate job list\n%1$s";
    public static final String MESSAGE_INVALID_RANGE =
        "Not a valid range, the right format should be value-value;value-value..." + "\n"
            + "For example: 1.2-1.3; 1.3-1.4";
    public static final String MESSAGE_REDUNDANT_FILTERNAME = "Filter name has already been used." + "\n"
        + "Filter Command need a unique name";
    private final Predicate<Person> predicate;
    private final PredicatePersonDescriptor predicatePersonDescriptor;
    private final JobListName listName;
    private final String commandName;

    /**
     * @param commandName               command name
     * @param listName                  which job list to predicate the person with
     * @param predicatePersonDescriptor details to predicate the person with
     */
    public FilterCommand(String commandName, JobListName listName,
                         PredicatePersonDescriptor predicatePersonDescriptor) {
        requireNonNull(commandName);
        requireNonNull(listName);
        requireNonNull(predicatePersonDescriptor);
        this.predicatePersonDescriptor = new PredicatePersonDescriptor(predicatePersonDescriptor);
        this.predicate = this.predicatePersonDescriptor.toPredicate();
        this.listName = listName;
        this.commandName = commandName;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        UniqueFilterList predicateList;
        boolean isAllJobScreen = model.getIsAllJobScreen();
        boolean hasListName = listName != EMPTY;
        checkException(isAllJobScreen, hasListName);

        switch (listName) {
        case APPLICANT:
            try {
                model.addPredicateJobAllApplicants(commandName, predicate);
            } catch (DuplicateFilterException ex) {
                throw new CommandException(MESSAGE_REDUNDANT_FILTERNAME);
            }
            model.updateJobAllApplicantsFilteredPersonList();
            predicateList = model.getPredicateLists(APPLICANT);
            break;
        case KIV:
            try {
                model.addPredicateJobKiv(commandName, predicate);
            } catch (DuplicateFilterException ex) {
                throw new CommandException(MESSAGE_REDUNDANT_FILTERNAME);
            }
            model.updateJobKivFilteredPersonList();
            predicateList = model.getPredicateLists(KIV);
            break;
        case INTERVIEW:
            try {
                model.addPredicateJobInterview(commandName, predicate);
            } catch (DuplicateFilterException ex) {
                throw new CommandException(MESSAGE_REDUNDANT_FILTERNAME);
            }
            model.updateJobInterviewFilteredPersonList();
            predicateList = model.getPredicateLists(INTERVIEW);
            break;
        case SHORTLIST:
            try {
                model.addPredicateJobShortlist(commandName, predicate);
            } catch (DuplicateFilterException ex) {
                throw new CommandException(MESSAGE_REDUNDANT_FILTERNAME);
            }
            model.updateJobShortlistFilteredPersonList();
            predicateList = model.getPredicateLists(SHORTLIST);
            break;
        default:
            try {
                model.addPredicateAllPersons(commandName, predicate);
            } catch (DuplicateFilterException ex) {
                throw new CommandException(MESSAGE_REDUNDANT_FILTERNAME);
            }
            model.updateFilteredPersonList();
            predicateList = model.getPredicateLists(EMPTY);
        }
        int size = model.getJobsList(listName).size();
        return new CommandResult(
            String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, size), listName,
            predicateList);
    }

    /**
     * @param isAllJobScreen Indicate the current screen, true if screen on all jobs screen
     * @param hasListName    Indicate whether command parser parse the List name
     * @throws CommandException throw exception and catch by function excute()
     */
    private void checkException(boolean isAllJobScreen, boolean hasListName)
        throws CommandException {
        String showMessage = isAllJobScreen ? MESSAGE_USAGE_ALLJOB_SCREEN : MESSAGE_USAGE_JOB_DETAIL_SCREEN;
        if (!isAllJobScreen && !hasListName) {
            throw new CommandException(String.format(MESSAGE_LACK_LISTNAME, showMessage));
        } else if (isAllJobScreen && hasListName) {
            throw new CommandException(String.format(MESSAGE_REDUNDANT_LISTNAME, showMessage));
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FilterCommand)) {
            return false;
        }

        // state check
        FilterCommand e = (FilterCommand) other;
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
        private Set<String> interviewScoreQ1;
        private Set<String> interviewScoreQ2;
        private Set<String> interviewScoreQ3;
        private Set<String> interviewScoreQ4;
        private Set<String> interviewScoreQ5;
        private Set<String> grade;
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
         * A defensive copy
         */
        public PredicatePersonDescriptor(PredicatePersonDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setNric(toCopy.nric);
            setGender(toCopy.gender);
            setRace(toCopy.race);
            setInterviewScoreQ1(toCopy.interviewScoreQ1);
            setInterviewScoreQ2(toCopy.interviewScoreQ2);
            setInterviewScoreQ3(toCopy.interviewScoreQ3);
            setInterviewScoreQ4(toCopy.interviewScoreQ4);
            setInterviewScoreQ5(toCopy.interviewScoreQ5);
            setGrade(toCopy.grade);
            setAddress(toCopy.address);
            setSchool(toCopy.school);
            setMajor(toCopy.major);
            setKnownProgLangs(toCopy.knownProgLangs);
            setPastJobs(toCopy.pastJobs);
            setJobsApply(toCopy.jobsApply);
        }

        /**
         * Translate and returns a Predicate object for name
         */
        private Predicate<Person> nameToPredicate() {
            Predicate<Person> predicator = new PredicateManager();
            if (this.getName().isPresent()) {
                predicator = predicator.and(new NameContainsKeywordsPredicate(
                    new ArrayList<>(this.getName().get())));
            }
            return predicator;
        }

        /**
         * Translate and returns a Predicate object for phone
         */
        private Predicate<Person> phoneToPredicate() {
            Predicate<Person> predicator = new PredicateManager();
            if (this.getPhone().isPresent()) {
                predicator = predicator.and(new PhoneContainsKeywordsPredicate(
                    new ArrayList<>(this.getPhone().get())));
            }
            return predicator;
        }

        /**
         * Translate and returns a Predicate object for email
         */
        private Predicate<Person> emailToPredicate() {
            Predicate<Person> predicator = new PredicateManager();
            if (this.getEmail().isPresent()) {
                predicator = predicator.and(new EmailContainsKeywordsPredicate(
                    new ArrayList<>(this.getEmail().get())));
            }
            return predicator;
        }

        /**
         * Translate and returns a Predicate object for race
         */
        private Predicate<Person> raceToPredicate() {
            Predicate<Person> predicator = new PredicateManager();
            if (this.getRace().isPresent()) {
                predicator = predicator.and(new RaceContainsKeywordsPredicate(
                    new ArrayList<>(this.getRace().get())));
            }
            return predicator;
        }

        /**
         * Translate and returns a Predicate object for address
         */
        private Predicate<Person> addressToPredicate() {
            Predicate<Person> predicator = new PredicateManager();
            if (this.getAddress().isPresent()) {
                predicator = predicator.and(new AddressContainsKeywordsPredicate(
                    new ArrayList<>(this.getAddress().get())));
            }
            return predicator;
        }

        /**
         * Translate and returns a Predicate object for school
         */
        private Predicate<Person> schoolToPredicate() {
            Predicate<Person> predicator = new PredicateManager();
            if (this.getSchool().isPresent()) {
                predicator = predicator.and(new SchoolContainsKeywordsPredicate(
                    new ArrayList<>(this.getSchool().get())));
            }
            return predicator;
        }

        /**
         * Translate and returns a Predicate object for major
         */
        private Predicate<Person> majorToPredicate() {
            Predicate<Person> predicator = new PredicateManager();
            if (this.getMajor().isPresent()) {
                predicator = predicator.and(new MajorContainsKeywordsPredicate(
                    new ArrayList<>(this.getMajor().get())));
            }
            return predicator;
        }

        /**
         * Translate and returns a Predicate object for gender
         */
        private Predicate<Person> genderToPredicate() {
            Predicate<Person> predicator = new PredicateManager();
            if (this.getGender().isPresent()) {
                predicator = predicator.and(new GenderContainsKeywordsPredicate(
                    new ArrayList<>(this.getGender().get())));
            }
            return predicator;
        }

        /**
         * Translate and returns a Predicate object for grade
         */
        private Predicate<Person> gradeToPredicate() {
            Predicate<Person> predicator = new PredicateManager();
            if (this.getGrade().isPresent()) {
                predicator = predicator.and(new GradeContainsKeywordsPredicate(
                    new ArrayList<>(this.getGrade().get())));
            }
            return predicator;
        }

        /**
         * Translate and returns a Predicate object for iq1
         */
        private Predicate<Person> interviewQ1ToPredicate() {
            Predicate<Person> predicator = new PredicateManager();
            if (this.getInterviewScoreQ1().isPresent()) {
                predicator = predicator.and(new InterviewScoreContainsKeywordsPredicate(1,
                    new ArrayList<>(this.getInterviewScoreQ1().get())));
            }
            return predicator;
        }

        /**
         * Translate and returns a Predicate object for iq2
         */
        private Predicate<Person> interviewQ2ToPredicate() {
            Predicate<Person> predicator = new PredicateManager();
            if (this.getInterviewScoreQ2().isPresent()) {
                predicator = predicator.and(new InterviewScoreContainsKeywordsPredicate(2,
                    new ArrayList<>(this.getInterviewScoreQ2().get())));
            }
            return predicator;
        }

        /**
         * Translate and returns a Predicate object for iq3
         */
        private Predicate<Person> interviewQ3ToPredicate() {
            Predicate<Person> predicator = new PredicateManager();
            if (this.getInterviewScoreQ3().isPresent()) {
                predicator = predicator.and(new InterviewScoreContainsKeywordsPredicate(3,
                    new ArrayList<>(this.getInterviewScoreQ3().get())));
            }
            return predicator;
        }

        /**
         * Translate and returns a Predicate object for iq4
         */
        private Predicate<Person> interviewQ4ToPredicate() {
            Predicate<Person> predicator = new PredicateManager();
            if (this.getInterviewScoreQ4().isPresent()) {
                predicator = predicator.and(new InterviewScoreContainsKeywordsPredicate(4,
                    new ArrayList<>(this.getInterviewScoreQ4().get())));
            }
            return predicator;
        }

        /**
         * Translate and returns a Predicate object for iq5
         */
        private Predicate<Person> interviewQ5ToPredicate() {
            Predicate<Person> predicator = new PredicateManager();
            if (this.getInterviewScoreQ5().isPresent()) {
                predicator = predicator.and(new InterviewScoreContainsKeywordsPredicate(5,
                    new ArrayList<>(this.getInterviewScoreQ5().get())));
            }
            return predicator;
        }

        /**
         * Translate and returns a Predicate object for nric
         */
        private Predicate<Person> nricToPredicate() {
            Predicate<Person> predicator = new PredicateManager();
            if (this.getNric().isPresent()) {
                predicator = predicator.and(new NricContainsKeywordsPredicate(
                    new ArrayList<>(this.getNric().get())));
            }
            return predicator;
        }

        /**
         * Translate and returns a Predicate object for pastJob
         */
        private Predicate<Person> pastJobToPredicate() {
            Predicate<Person> predicator = new PredicateManager();
            if (this.getPastJobs().isPresent()) {
                predicator = predicator.and(new PastJobContainsKeywordsPredicate(
                    new ArrayList<>(this.getPastJobs().get())));
            }
            return predicator;
        }

        /**
         * Translate and returns a Predicate object for jobsApply
         */
        private Predicate<Person> jobsApplyToPredicate() {
            Predicate<Person> predicator = new PredicateManager();
            if (this.getJobsApply().isPresent()) {
                predicator = predicator.and(new JobsApplyContainsKeywordsPredicate(
                    new ArrayList<>(this.getJobsApply().get())));
            }
            return predicator;
        }

        /**
         * Translate and returns a Predicate object for knownProgLang
         */
        private Predicate<Person> knownProgLangToPredicate() {
            Predicate<Person> predicator = new PredicateManager();
            if (this.getKnownProgLangs().isPresent()) {
                predicator = predicator.and(new KnownProgLangContainsKeywordsPredicate(
                    new ArrayList<>(this.getKnownProgLangs().get())));
            }
            return predicator;
        }

        /**
         * Translate and returns a Predicate object for search command
         */
        public Predicate<Person> toPredicate() {
            Predicate<Person> predicator = new PredicateManager();
            predicator = predicator.and(nameToPredicate().and(phoneToPredicate()
                .and(emailToPredicate().and(raceToPredicate().and(addressToPredicate()
                    .and(majorToPredicate().and(genderToPredicate().and(schoolToPredicate()
                        .and(gradeToPredicate().and(interviewQ1ToPredicate().and(interviewQ2ToPredicate()
                            .and(interviewQ3ToPredicate().and(interviewQ4ToPredicate().and(interviewQ5ToPredicate()
                                .and(nricToPredicate().and(pastJobToPredicate().and(jobsApplyToPredicate()
                                    .and(knownProgLangToPredicate()))))))))))))))))));
            return predicator;
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

        public void setGrade(Set<String> grade) {
            this.grade = grade;
        }

        public Optional<Set<String>> getGrade() {
            return Optional.ofNullable(grade);
        }

        public void setInterviewScoreQ1(Set<String> interviewScoreQ1) {
            this.interviewScoreQ1 = interviewScoreQ1;
        }

        public Optional<Set<String>> getInterviewScoreQ1() {
            return Optional.ofNullable(interviewScoreQ1);
        }

        public void setInterviewScoreQ2(Set<String> interviewScoreQ2) {
            this.interviewScoreQ2 = interviewScoreQ2;
        }

        public Optional<Set<String>> getInterviewScoreQ2() {
            return Optional.ofNullable(interviewScoreQ2);
        }

        public void setInterviewScoreQ3(Set<String> interviewScoreQ3) {
            this.interviewScoreQ3 = interviewScoreQ3;
        }

        public Optional<Set<String>> getInterviewScoreQ3() {
            return Optional.ofNullable(interviewScoreQ3);
        }

        public void setInterviewScoreQ4(Set<String> interviewScoreQ4) {
            this.interviewScoreQ4 = interviewScoreQ4;
        }

        public Optional<Set<String>> getInterviewScoreQ4() {
            return Optional.ofNullable(interviewScoreQ4);
        }

        public void setInterviewScoreQ5(Set<String> interviewScoreQ5) {
            this.interviewScoreQ5 = interviewScoreQ5;
        }

        public Optional<Set<String>> getInterviewScoreQ5() {
            return Optional.ofNullable(interviewScoreQ5);
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

        public Optional<Set<String>> getJobsApply() {
            return Optional.ofNullable(jobsApply);
        }


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
                && getGrade().equals(e.getGrade())
                && getInterviewScoreQ1().equals(e.getInterviewScoreQ1())
                && getInterviewScoreQ2().equals(e.getInterviewScoreQ2())
                && getInterviewScoreQ3().equals(e.getInterviewScoreQ3())
                && getInterviewScoreQ4().equals(e.getInterviewScoreQ4())
                && getInterviewScoreQ5().equals(e.getInterviewScoreQ5())
                && getAddress().equals(e.getAddress())
                && getSchool().equals(e.getSchool())
                && getMajor().equals(e.getMajor())
                && getKnownProgLangs().equals(e.getKnownProgLangs())
                && getPastJobs().equals(e.getPastJobs())
                && getJobsApply().equals(e.getJobsApply());
        }
    }
}
