package seedu.address.logic.commands.sortmethods;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.model.person.Person;
import seedu.address.model.tag.SkillsTag;

//SHOULD ANY OF THESE BE IN THE MODEL/PERSON/[] CLASS?
//TODO: rewrite all those using comparator, to just take in whatever it is to be sorted (skillstag, education etc.)
//Maybe remove the toString method in Person class
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
