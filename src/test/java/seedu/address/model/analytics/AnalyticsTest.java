package seedu.address.model.analytics;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalObjects.ALICE;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import seedu.address.model.person.Person;

public class AnalyticsTest {

    /* -------------------Performs analytics on empty person list------------------------------------------*/
    private List<Person> emptyList = Collections.emptyList();
    private Analytics emptyAnalytics = new Analytics(emptyList);

    @Test
    public void generateMeanGradeData_withEmptyPersonList_generatesEmptyData() {
        String noRecord = "No Record";
        assertEquals(noRecord, emptyAnalytics.generateMeanGradeData());
    }

    @Test
    public void generateInterviewScoresData_withEmptyPersonList_generatesEmptyData() {
        Float emptyData = Float.NaN; // 0 average score divide 0 people
        for (int i = 0; i < 5; i++) {
            assertEquals(emptyData,
                    emptyAnalytics.generateInterviewScoresData().get(i).getData().get(0).getYValue());
        }
    }

    @Test
    public void generateRaceData_withEmptyPersonList_generatesEmptyData() {
        Double emptyData = 0.0; // 0 people (hence 0 race)
        for (int i = 0; i < 4; i++) {
            assertEquals(emptyData, (Double) emptyAnalytics.generateRaceData().get(i).getPieValue());
        }
    }

    @Test
    public void generateGenderData_withEmptyPersonList_generatesEmptyData() {
        Double emptyData = 0.0; // 0 people (hence 0 gender)
        for (int i = 0; i < 3; i++) {
            assertEquals(emptyData, (Double) emptyAnalytics.generateGenderData().get(i).getPieValue());
        }
    }

    @Test
    public void generateJobApplicationData_withEmptyPersonList_generatesEmptyData() {
        List<XYChart.Series<String, Integer>> emptyData = Collections.emptyList();
        assertEquals(emptyData, emptyAnalytics.generateJobApplicationData());
    }

    @Test
    public void generatePastJobData_withEmptyPersonList_generatesEmptyData() {
        List<XYChart.Series<String, Integer>> emptyData = Collections.emptyList();
        assertEquals(emptyData, emptyAnalytics.generatePastJobData());
    }

    @Test
    public void generateMajorData_withEmptyPersonList_generatesEmptyData() {
        List<XYChart.Series<String, Integer>> emptyData = Collections.emptyList();
        assertEquals(emptyData, emptyAnalytics.generateMajorData());
    }

    @Test
    public void generateSchoolData_withEmptyPersonList_generatesEmptyData() {
        List<XYChart.Series<String, Integer>> emptyData = Collections.emptyList();
        assertEquals(emptyData, emptyAnalytics.generateSchoolData());
    }

    /*------------------------Perform analytics on person list with person----------------------------------*/
    private List<Person> personList = new ArrayList<>();

    @Test
    public void generateMeanGradeData_withPerson_generatesCorrectPersonData() {
        personList.add(ALICE);
        Analytics personAnalytics = new Analytics(personList);
        String grade = ALICE.getGrade().value;
        String analyticsGrade = personAnalytics.generateMeanGradeData();
        assertEquals(grade, analyticsGrade);
    }

    @Test
    public void generateInterviewScoresData_withPerson_generatesCorrectPersonData() {
        personList.add(ALICE);
        Analytics personAnalytics = new Analytics(personList);
        for (int i = 0; i < 5; i++) {
            Float score = Float.parseFloat(ALICE.getInterviewScores().getInterviewScore(i+1));
            Float analyticsScore = personAnalytics.generateInterviewScoresData().get(i).getData().get(0).getYValue();
            assertEquals(score, analyticsScore);
        }
    }

    @Test
    public void generateRaceData_withPerson_generatesCorrectPersonData() {
        personList.add(ALICE);
        Analytics personAnalytics = new Analytics(personList);
        String personRace = ALICE.getRace().value;
        for (int i = 0; i < 4; i++) {
            PieChart.Data analyticsData = personAnalytics.generateRaceData().get(i);
            String analyticsRace = analyticsData.getName();
            if (personRace.equals(analyticsRace)) {
                assertEquals(1.0, analyticsData.getPieValue(), 0.001);
            } else {
                assertEquals(0.0, analyticsData.getPieValue(), 0.001);
            }
        }
    }

    @Test
    public void generateGenderData_withPerson_generatesCorrectPersonData() {
        personList.add(ALICE);
        Analytics personAnalytics = new Analytics(personList);
        String personGender = ALICE.getGender().value;
        for (int i = 0; i < 3; i++) {
            PieChart.Data analyticsData = personAnalytics.generateGenderData().get(i);
            String analyticsGender = analyticsData.getName();
            if (personGender.equals(analyticsGender)) {
                assertEquals(1.0, analyticsData.getPieValue(), 0.001);
            } else {
                assertEquals(0.0, analyticsData.getPieValue(), 0.001);
            }
        }
    }

    @Test
    public void generateJobApplicationData_withPerson_generatesCorrectPersonData() {
        personList.add(ALICE);
        Analytics personAnalytics = new Analytics(personList);
        String personJobApply = ALICE.getJobsApply().iterator().next().value;
        String analyticsJobApply =
                personAnalytics.generateJobApplicationData().get(0).getName();
        assertEquals(personJobApply, analyticsJobApply);
    }

    @Test
    public void generatePastJobData_withPerson_generatesCorrectPersonData() {
        personList.add(ALICE);
        Analytics personAnalytics = new Analytics(personList);
        String personPastJob = ALICE.getPastJobs().iterator().next().value;
        String analyticsPastJob =
                personAnalytics.generatePastJobData().get(0).getName();
        assertEquals(personPastJob, analyticsPastJob);
    }

    @Test
    public void generateMajorData_withPerson_generatesCorrectPersonData() {
        personList.add(ALICE);
        Analytics personAnalytics = new Analytics(personList);
        String personMajor = ALICE.getMajor().value;
        String analyticsMajor =
                personAnalytics.generateMajorData().get(0).getName();
        assertEquals(personMajor, analyticsMajor);
    }

    @Test
    public void generateSchoolData_withPerson_generatesCorrectPersonData() {
        personList.add(ALICE);
        Analytics personAnalytics = new Analytics(personList);
        String personSchool = ALICE.getSchool().value;
        String analyticsSchool =
                personAnalytics.generateSchoolData().get(0).getName();
        assertEquals(personSchool, analyticsSchool);
    }
}
