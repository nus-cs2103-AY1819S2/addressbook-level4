package seedu.address.logic.commands.sortmethods;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.model.person.Address;
import seedu.address.model.person.Degree;
import seedu.address.model.person.Education;
import seedu.address.model.person.Email;
import seedu.address.model.person.Gpa;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.SkillsTag;

/**
 * One of the sorting helper commands
 */
public class SortUtil {

    /**
     * Separates the tags
     */
    public static LinkedHashSet<SkillsTag> toTags(List<SkillsTag> tags) {
        // Needs a LinkedHashSet to maintain the order
        final LinkedHashSet<SkillsTag> tagSet = new LinkedHashSet<>();
        if (tags.isEmpty()) {
            return tagSet;
        }
        for (SkillsTag tagName : tags) {
            tagSet.add(tagName);
        }
        return tagSet;
    }

    /**
     * sorts the skills tags
     */
    public static List<SkillsTag> sortSkillTags(List<SkillsTag> skills) {
        //Sorting tags in alphabetical order
        List<SkillsTag> sortedList =
                skills.stream().sorted(Comparator.comparing(SkillsTag::toString)).collect(Collectors.toList());
        return sortedList;
    }

    /**
     * Gets from the list the information of person and processes
     */
    public static List<Person> orderPersonsTags(List<Person> lastShownList, String prefix) {
        List<Person> personsWithCorrectTagOrder = new ArrayList<>();
        for (Person person : lastShownList) {
            // Change Set to List to utilise stream sorting
            List<SkillsTag> individualTags = new ArrayList<>();
            List<SkillsTag> tagsToSort = new ArrayList<>();
            List<SkillsTag> otherTags = new ArrayList<>();
            individualTags.addAll(person.getTags());
            for (SkillsTag tag : individualTags) {
                String tagString = tag.toString();
                // first element of string is "["
                if (tagString.substring(1, 2).equals(prefix)) {
                    tagsToSort.add(tag);
                } else {
                    otherTags.add(tag);
                }
            }
            List<SkillsTag> individualSortedTags = sortSkillTags(tagsToSort);
            individualSortedTags.addAll(otherTags);

            Name name = person.getName();
            Phone phone = person.getPhone();
            Email email = person.getEmail();
            Education education = person.getEducation();
            Gpa gpa = person.getGpa();
            Degree degree = person.getDegree();
            Address address = person.getAddress();
            //change list back to set
            LinkedHashSet<SkillsTag> tagSet = SortUtil.toTags(individualSortedTags);

            Person newPerson = new Person(name, phone, email, education, gpa, degree, address, tagSet);
            personsWithCorrectTagOrder.add(newPerson);
        }
        return personsWithCorrectTagOrder;
    }

    /**
     *  uses the comparator to sort a list of persons
     */
    public static List<Person> sortPersons(List<Person> persons, Comparator<Person> comparator) {
        List<Person> sortedList = persons.stream().sorted(comparator).collect(Collectors.toList());
        return sortedList;
    }

    /**
     * reverses a List of Person
     */
    public static List<Person> reversePersonList(List<Person> persons) {
        //Included for abstraction
        List<Person> tempList = persons;
        Collections.reverse(tempList);
        return tempList;
    }
}
