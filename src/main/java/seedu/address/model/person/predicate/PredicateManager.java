package seedu.address.model.person.predicate;

import java.util.ArrayList;
import java.util.function.Predicate;

import seedu.address.logic.commands.SearchCommand.PredicatePersonDescriptor;
import seedu.address.model.person.Person;

/**
 * Tests that a {@code Person}'s {@code Phone} matches any of the keywords given.
 */
public class PredicateManager implements Predicate<Person> {

    public Predicate translatePredicateDescriptor(PredicatePersonDescriptor predicatePersonDescriptor) {
        Predicate<Person> predicator = new PredicateManager();
        predicatePersonDescriptor = new PredicatePersonDescriptor(predicatePersonDescriptor);
        if (predicatePersonDescriptor.getName().isPresent()) {
            predicator = predicator.and(new NameContainsKeywordsPredicate(
                new ArrayList<>(predicatePersonDescriptor.getName().get())));
        }
        if (predicatePersonDescriptor.getPhone().isPresent()) {
            predicator = predicator.and(new PhoneContainsKeywordsPredicate(
                new ArrayList<>(predicatePersonDescriptor.getPhone().get())));
        }
        if (predicatePersonDescriptor.getEmail().isPresent()) {
            predicator = predicator.and(new EmailContainsKeywordsPredicate(
                new ArrayList<>(predicatePersonDescriptor.getEmail().get())));
        }
        if (predicatePersonDescriptor.getRace().isPresent()) {
            predicator = predicator.and(new RaceContainsKeywordsPredicate(
                new ArrayList<>(predicatePersonDescriptor.getRace().get())));
        }
        if (predicatePersonDescriptor.getName().isPresent()) {
            predicator = predicator.and(new NameContainsKeywordsPredicate(
                new ArrayList<>(predicatePersonDescriptor.getName().get())));
        }
        if (predicatePersonDescriptor.getAddress().isPresent()) {
            predicator = predicator.and(new AddressContainsKeywordsPredicate(
                new ArrayList<>(predicatePersonDescriptor.getAddress().get())));
        }
        if (predicatePersonDescriptor.getSchool().isPresent()) {
            predicator = predicator.and(new SchoolContainsKeywordsPredicate(
                new ArrayList<>(predicatePersonDescriptor.getSchool().get())));
        }
        if (predicatePersonDescriptor.getMajor().isPresent()) {
            predicator = predicator.and(new MajorContainsKeywordsPredicate(
                new ArrayList<>(predicatePersonDescriptor.getMajor().get())));
        }
        if (predicatePersonDescriptor.getGender().isPresent()) {
            predicator = predicator.and(new GenderContainsKeywordsPredicate(
                new ArrayList<>(predicatePersonDescriptor.getGender().get())));
        }
        if (predicatePersonDescriptor.getNric().isPresent()) {
            predicator = predicator.and(new NricContainsKeywordsPredicate(
                new ArrayList<>(predicatePersonDescriptor.getNric().get())));
        }
        if (predicatePersonDescriptor.getPastJobs().isPresent()) {
            predicator = predicator.and(new PastJobContainsKeywordsPredicate(
                new ArrayList<>(predicatePersonDescriptor.getPastJobs().get())));
        }
        if (predicatePersonDescriptor.getJobsApply().isPresent()) {
            predicator = predicator.and(new JobsApplyContainsKeywordsPredicate(
                new ArrayList<>(predicatePersonDescriptor.getJobsApply().get())));
        }
        if (predicatePersonDescriptor.getKnownProgLangs().isPresent()) {
            predicator = predicator.and(new KnownProgLangContainsKeywordsPredicate(
                new ArrayList<>(predicatePersonDescriptor.getKnownProgLangs().get())));
        }
        return predicator;
    }

    @Override
    public boolean test(Person person) {
        return true;
    }


}
