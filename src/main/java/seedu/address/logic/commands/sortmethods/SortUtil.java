package seedu.address.logic.commands.sortmethods;

import java.util.ArrayList;
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
     * sorts the persons by their tags
     */
    public static List<Person> sortPersonsByTags(List<Person> persons) {
        //Sort persons by tags
        List<Person> sortedList =
                persons.stream().sorted(Comparator.comparing(Person::tagsToString)).collect(Collectors.toList());
        return sortedList;
    }

    /**
     * sorts the persons by their name
     */
    public static List<Person> sortPersonsByNames(List<Person> persons) {
        //Sort persons alphabetically by name
        List<Person> sortedList =
                persons.stream().sorted(Comparator.comparing(Person::namesToString)).collect(Collectors.toList());
        return sortedList;
    }

    /**
     * sorts the persons by their gpa
     */
    public static List<Person> sortPersonsByGpa(List<Person> persons) {
        List<Person> sortedList =
                persons.stream().sorted(Comparator.comparing(Person::gpaToString)).collect(Collectors.toList());
        return sortedList;
    }

    /**
     * sorts the persons by their education
     */
    public static List<Person> sortPersonsByEducation(List<Person> persons) {
        List<Person> sortedList =
                persons.stream().sorted(Comparator.comparing(Person::educationToString)).collect(Collectors.toList());
        return sortedList;
    }

    /**
     * reverses a List<Person>
     */
    public static List<Person> reversePersonList(List<Person> persons) {
        //Included for abstraction
        List<Person> tempList = persons;
        Collections.reverse(tempList);
        return tempList;
    }
}
