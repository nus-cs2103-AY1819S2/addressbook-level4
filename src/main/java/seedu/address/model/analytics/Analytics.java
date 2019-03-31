package seedu.address.model.analytics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import seedu.address.model.person.JobsApply;
import seedu.address.model.person.PastJob;
import seedu.address.model.person.Person;

/**
 * Provides analytics data on job applicants, based on their data fields obtained from {@code Person}
 * */

public class Analytics {
    private List<Person> personList;
    private int numPeople;

    public Analytics(List<Person> list) {
        personList = list;
        numPeople = list.size();
    }

    /**
     * Generates Barchart data from job application breakdown list
     * */
    public ObservableList<XYChart.Series<String, Integer>> generateJobApplicationData() {
        return generateBarChartDataFromSet(jobApplicationBreakdown());
    }

    /**
     * Generates Barchart data from major breakdown list
     * */
    public ObservableList<XYChart.Series<String, Integer>> generateMajorData() {
        return generateBarChartDataFromSet(majorBreakdown());
    }

    /**
     * Generates Barchart data from school breakdown list
     * */
    public ObservableList<XYChart.Series<String, Integer>> generateSchoolData() {
        return generateBarChartDataFromSet(schoolBreakdown());
    }

    /**
     * Generates Barchart data from past job breakdown list
     * */
    public ObservableList<XYChart.Series<String, Integer>> generatePastJobData() {
        return generateBarChartDataFromSet(pastJobBreakdown());
    }

    /**
     * Return mean grade data
     * */
    public Float generateMeanGradeData() {
        return meanGrade();
    }

    /**
     * Generates Barchart data from interview scores breakdown list
     * */
    public ObservableList<XYChart.Series<String, Float>> generateInterviewScoresData() {
        ObservableList<XYChart.Series<String, Float>> data = FXCollections.observableArrayList();
        ArrayList<Float> scores = meanInterviewScores();
        XYChart.Series<String, Float> q1 = new XYChart.Series<>();
        XYChart.Series<String, Float> q2 = new XYChart.Series<>();
        XYChart.Series<String, Float> q3 = new XYChart.Series<>();
        XYChart.Series<String, Float> q4 = new XYChart.Series<>();
        XYChart.Series<String, Float> q5 = new XYChart.Series<>();
        q1.setName("Q1");
        q2.setName("Q2");
        q3.setName("Q3");
        q4.setName("Q4");
        q5.setName("Q5");
        q1.getData().add(new XYChart.Data<>("", scores.get(0)));
        q2.getData().add(new XYChart.Data<>("", scores.get(1)));
        q3.getData().add(new XYChart.Data<>("", scores.get(2)));
        q4.getData().add(new XYChart.Data<>("", scores.get(3)));
        q5.getData().add(new XYChart.Data<>("", scores.get(4)));
        data.addAll(q1, q2, q3, q4, q5);

        return data;
    }

    /**
     * Generates Piechart data from race breakdown list
     * */
    public ObservableList<PieChart.Data> generateRaceData() {
        ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
        ArrayList<Integer> race = raceBreakdown();
        data.add(new PieChart.Data("Chinese", race.get(0)));
        data.add(new PieChart.Data("Malay", race.get(1)));
        data.add(new PieChart.Data("Indian", race.get(2)));
        data.add(new PieChart.Data("Others", race.get(3)));

        return data;
    }

    /**
     * Generates Piechart data from gender breakdown list
     * */
    public ObservableList<PieChart.Data> generateGenderData() {
        ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
        ArrayList<Integer> gender = genderBreakdown();
        data.add(new PieChart.Data("Female", gender.get(0)));
        data.add(new PieChart.Data("Male", gender.get(1)));
        data.add(new PieChart.Data("Others", gender.get(2)));

        return data;
    }

    /**
     * Returns the average grade of all applicants in the list
     * */

    private Float meanGrade() {
        Float sumGrade = 0F;

        for (int i = 0; i < numPeople; i++) {
            sumGrade += Float.valueOf(personList.get(i).getGrade().value);
        }

        return sumGrade / numPeople;
    }

    /**
     * Provides the number of applicants for each open job role
     * A {@code Person} with more than one job applied will be counted multiple times, once for each role.
     * */

    private HashMap<String, Integer> jobApplicationBreakdown() {
        HashMap<String, Integer> jobApplicantsCounter = new HashMap<>();

        for (int i = 0; i < numPeople; i++) {
            Iterator<JobsApply> itr = personList.get(i).getJobsApply().iterator();
            while (itr.hasNext()) {
                JobsApply job = itr.next();
                if (!jobApplicantsCounter.containsKey(job.value)) {
                    jobApplicantsCounter.put(job.value, 1);
                } else {
                    jobApplicantsCounter.put(job.value, jobApplicantsCounter.get(job.value) + 1);
                }
            }
        }
        return jobApplicantsCounter;
    }

    /**
     * Provides an average score for each interview question from selected list of applicants
     * Applicants not required to all have interview scores
     * */

    private ArrayList<Float> meanInterviewScores() {
        ArrayList<Float> averageScores = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            averageScores.add(0F);
        }
        int interviewed = numPeople;

        for (int i = 0; i < numPeople; i++) {
            Person curr = personList.get(i);
            if (curr.getInterviewScores().value == "No Record") {
                interviewed--;
            } else {
                String[] personScores;
                personScores = curr.getInterviewScores().value.split(",");
                for (int j = 0; j < 5; j++) {
                    averageScores.set(j, averageScores.get(j) + Integer.valueOf(personScores[j]));
                }
            }
        }
        for (int i = 0; i < 5; i++) {
            averageScores.set(i, averageScores.get(i) / interviewed);
        }
        return averageScores;
    }

    /**
     * Returns the number of applicants for each gender category
     * */

    private ArrayList<Integer> genderBreakdown() {
        ArrayList<Integer> genderCount = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            genderCount.add(0);
        }

        for (int i = 0; i < 3; i++) {
            genderCount.set(i, 0);
        }

        for (int i = 0; i < numPeople; i++) {
            String currGender = personList.get(i).getGender().value;

            if (currGender.equals("Female")) {
                genderCount.set(0, genderCount.get(0) + 1);
            } else if (currGender.equals("Male")) {
                genderCount.set(1, genderCount.get(1) + 1);
            } else {
                genderCount.set(2, genderCount.get(2) + 1);
            }
        }
        return genderCount;
    }

    /**
     * Returns the number of applicants for each race category
     * */

    private ArrayList<Integer> raceBreakdown() {
        ArrayList<Integer> raceCount = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            raceCount.add(0);
        }

        for (int i = 0; i < numPeople; i++) {
            String currRace = personList.get(i).getRace().value;

            if (currRace.equals("Chinese")) {
                raceCount.set(0, raceCount.get(0) + 1);
            } else if (currRace.equals("Malay")) {
                raceCount.set(1, raceCount.get(1) + 1);
            } else if (currRace.equals("Indian")) {
                raceCount.set(2, raceCount.get(2) + 1);
            } else {
                raceCount.set(3, raceCount.get(3) + 1);
            }
        }
        return raceCount;
    }

    /**
     * Provides the number of applicants studying in each major
     * */

    private HashMap<String, Integer> majorBreakdown() {
        HashMap<String, Integer> majorCounter = new HashMap<>();

        for (int i = 0; i < numPeople; i++) {
            String currMajor = personList.get(i).getMajor().value;
            if (!majorCounter.containsKey(currMajor)) {
                majorCounter.put(currMajor, 1);
            } else {
                majorCounter.put(currMajor, majorCounter.get(currMajor) + 1);
            }
        }
        return majorCounter;
    }

    /**
     * Provides the number of applicants studying in each school
     * */

    private HashMap<String, Integer> schoolBreakdown() {
        HashMap<String, Integer> schoolCounter = new HashMap<>();

        for (int i = 0; i < numPeople; i++) {
            String currSchool = personList.get(i).getSchool().value;
            if (!schoolCounter.containsKey(currSchool)) {
                schoolCounter.put(currSchool, 1);
            } else {
                schoolCounter.put(currSchool, schoolCounter.get(currSchool) + 1);
            }
        }
        return schoolCounter;
    }

    /**
     * Provides the number of applicants for each past job
     * A {@code Person} with more than one past job applied will be counted multiple times, once for each job.
     * */

    private HashMap<String, Integer> pastJobBreakdown() {
        HashMap<String, Integer> pastJobCounter = new HashMap<>();

        for (int i = 0; i < numPeople; i++) {
            Iterator<PastJob> itr = personList.get(i).getPastJobs().iterator();
            while (itr.hasNext()) {
                PastJob job = itr.next();
                if (!pastJobCounter.containsKey(job.value)) {
                    pastJobCounter.put(job.value, 1);
                } else {
                    pastJobCounter.put(job.value, pastJobCounter.get(job.value) + 1);
                }
            }
        }
        return pastJobCounter;
    }

    /**
     * Generates the dataset to be put into a Barchart from any Hashmap with a String key and Integer value
     * */

    private ObservableList<XYChart.Series<String, Integer>> generateBarChartDataFromSet(HashMap<String, Integer> map) {
        ObservableList<XYChart.Series<String, Integer>> output = FXCollections.observableArrayList();
        Iterator<String> itr = map.keySet().iterator();
        while (itr.hasNext()) {
            String curr = itr.next();
            XYChart.Series<String, Integer> series = new XYChart.Series<>();
            series.setName(curr);
            series.getData().add(new XYChart.Data<>("", map.get(curr)));
            output.add(series);
        }
        return output;
    }
}
