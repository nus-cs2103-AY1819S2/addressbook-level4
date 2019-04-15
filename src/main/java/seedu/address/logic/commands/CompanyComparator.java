package seedu.address.logic.commands;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Company;

import java.util.Comparator;

public class CompanyComparator {
    private static Comparator CompanyName = new Comparator<Company> () {
        @Override
        public int compare(Company o1, Company o2) {

            return o1.getName().toString().compareTo(o2.getName().toString());
        }
    };

    private static Comparator CompanySalary = new Comparator<Company> () {
        @Override
        public int compare(Company o1, Company o2) {
            Integer sa1 = Integer.parseInt(String.valueOf(o1.getSalary()));
            Integer sa2 = Integer.parseInt(String.valueOf(o2.getSalary()));
            return sa1.compareTo(sa2);
        }
    };

    private static Comparator CompanyPhone = new Comparator<Company>() {
        @Override
        public int compare(Company o1, Company o2) {
            Integer sa1 = Integer.parseInt(String.valueOf(o1.getPhone()));
            Integer sa2 = Integer.parseInt(String.valueOf(o2.getPhone()));
            return sa1.compareTo(sa2);
        }
    };

    private static Comparator CompanyAddress = new Comparator<Company>() {
        @Override
        public int compare(Company o1, Company o2) {
            return o1.getAddress().toString().compareTo(o2.getAddress().toString());
        }
    };

    private static Comparator CompanyEmail = new Comparator<Company>() {
        @Override
        public int compare(Company o1, Company o2) {
            return o1.getEmail().toString().compareTo(o2.getEmail().toString());
        }
    };

    private static Comparator CompanyOccupation = new Comparator<Company>() {
        @Override
        public int compare(Company o1, Company o2) {
            return o1.getOccupation().toString().compareTo(o2.getOccupation().toString());
        }
    };

    private static Comparator CompanyRelationship = new Comparator<Company>() {
        @Override
        public int compare(Company o1, Company o2) {
            return o1.getRelationship().toString().compareTo(o2.getRelationship().toString());
        }
    };


    public static Comparator<Company> GetFunction(String parameter) throws ParseException {
        Comparator<Company> compare_function;

        switch (parameter.trim())
        {
            case "name":
                compare_function = CompanyName;
                break;

            case "revenue":
                compare_function = CompanySalary;
                break;

            case "phone":
                compare_function = CompanyPhone;
                break;

            case "address":
                compare_function = CompanyAddress;
                break;

            case "email":
                compare_function = CompanyEmail;
                break;

            case "occupation":
                compare_function = CompanyOccupation;
                break;

            case "relationship":
                compare_function = CompanyRelationship;
                break;

            default:
                throw new ParseException("Error");
        }

        return compare_function;
    }
}
