package seedu.address.logic.commands;

import java.util.Comparator;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Person;

public class PersonComparator {

    private static Comparator PersonName = new Comparator<Person> () {
        @Override
        public int compare(Person o1, Person o2) {

            return o1.getName().toString().compareTo(o2.getName().toString());
        }
    };

    private static Comparator PersonSalary = new Comparator<Person> () {
        @Override
        public int compare(Person o1, Person o2) {
            Integer sa1 = Integer.parseInt(String.valueOf(o1.getSalary()));
            Integer sa2 = Integer.parseInt(String.valueOf(o2.getSalary()));
            return sa1.compareTo(sa2);
        }
    };

    private static Comparator PersonPhone = new Comparator<Person>() {
        @Override
        public int compare(Person o1, Person o2) {
            Integer sa1 = Integer.parseInt(String.valueOf(o1.getPhone()));
            Integer sa2 = Integer.parseInt(String.valueOf(o2.getPhone()));
            return sa1.compareTo(sa2);
        }
    };

    private static Comparator PersonAddress = new Comparator<Person>() {
        @Override
        public int compare(Person o1, Person o2) {
            return o1.getAddress().toString().compareTo(o2.getAddress().toString());
        }
    };

    private static Comparator PersonEmail = new Comparator<Person>() {
        @Override
        public int compare(Person o1, Person o2) {
            return o1.getEmail().toString().compareTo(o2.getEmail().toString());
        }
    };

    private static Comparator PersonOccupation = new Comparator<Person>() {
        @Override
        public int compare(Person o1, Person o2) {
            return o1.getOccupation().toString().compareTo(o2.getOccupation().toString());
        }
    };

    private static Comparator PersonRelationship = new Comparator<Person>() {
        @Override
        public int compare(Person o1, Person o2) {
            return o1.getRelationship().toString().compareTo(o2.getRelationship().toString());
        }
    };


    public static Comparator<Person> GetFunction(String parameter) throws ParseException {
        Comparator<Person> compare_function;

    switch (parameter.trim())
        {
            case "name":
                compare_function = PersonName;
                break;

            case "salary":
                compare_function = PersonSalary;
                break;

            case "phone":
                compare_function = PersonPhone;
                break;

            case "address":
                compare_function = PersonAddress;
                break;

            case "email":
                compare_function = PersonEmail;
                break;

            case "occupation":
                compare_function = PersonOccupation;
                break;

            case "relationship":
                compare_function = PersonRelationship;
                break;

            default:
                throw new ParseException("Error");
        }

        return compare_function;
    }





}
