package seedu.address.model.limits;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalModuleTaken.getTypicalGradTrak;

import org.junit.Before;
import org.junit.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CheckLimitCommand;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserInfo;
import seedu.address.model.UserPrefs;
import seedu.address.model.course.CourseList;
import seedu.address.model.moduleinfo.ModuleInfo;
import seedu.address.model.moduleinfo.ModuleInfoCredits;
import seedu.address.model.moduleinfo.ModuleInfoList;
import seedu.address.model.moduletaken.CapAverage;
import seedu.address.model.moduletaken.ModuleTaken;
import seedu.address.model.moduletaken.Semester;
import seedu.address.model.util.SampleDataUtil;

public class LimitCheckerTest {
    public static final double DEFAULT_MODULE_CREDITS = 4;
    public static final int CAP_TABLE_COL_COUNT = 4;
    public static final int WORKLOAD_TABLE_ROW_COUNT = 5;
    public static final int WORKLOAD_TABLE_COL_COUNT = 3;

    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalGradTrak(), new UserPrefs(),
                new ModuleInfoList(), new CourseList(), new UserInfo());
        expectedModel = new ModelManager(model.getGradTrak(), new UserPrefs(),
                new ModuleInfoList(), new CourseList(), new UserInfo());
    }

    @Test
    public void constructor() {
        Semester validCurrentSemester = SampleDataUtil.getSampleCurrentSemester();
        ObservableList<ModuleTaken> validModulesTaken = FXCollections.observableArrayList(
                SampleDataUtil.getSampleModulesTaken());
        ObservableList<SemesterLimit> validSemesterLimits = FXCollections.observableArrayList(
                SampleDataUtil.getSampleSemesterLimits());
        ModuleInfoList validModuleInfoList = new ModuleInfoList();

        LimitChecker limitChecker = new LimitChecker(validCurrentSemester, validSemesterLimits, validModulesTaken,
                validModuleInfoList);

        CapAverage currentCap = new CapAverage();
        CapAverage cumulativeMinCap = new CapAverage();
        CapAverage cumulativeMaxCap = new CapAverage();
        CapAverage[][] minMaxCapAveragesPerSem = new CapAverage[validSemesterLimits.size()][2];
        for (int i = 0; i < minMaxCapAveragesPerSem.length; i++) {
            for (int j = 0; j < minMaxCapAveragesPerSem[i].length; j++) {
                minMaxCapAveragesPerSem[i][j] = new CapAverage();
            }
        }

        double[][] semesterSums = new double[validSemesterLimits.size()][5];
        for (int mod = 0; mod < validModulesTaken.size(); mod++) {
            ModuleInfoCredits modCredits = new ModuleInfoCredits(DEFAULT_MODULE_CREDITS);
            ModuleInfo moduleInfo = validModuleInfoList.getModule(String.valueOf(
                    validModulesTaken.get(mod).getModuleInfoCode()));
            if (moduleInfo != null) {
                modCredits = new ModuleInfoCredits(moduleInfo.getCredits());
            }

            if (validModulesTaken.get(mod).getExpectedMinGrade().isCountedInCap()) {
                minMaxCapAveragesPerSem[validModulesTaken.get(mod).getSemester().getIndex()][0].addWeightedGrade(
                        validModulesTaken.get(mod).getExpectedMinGrade().getGradePoint(), modCredits);
                cumulativeMinCap.addWeightedGrade(
                        validModulesTaken.get(mod).getExpectedMinGrade().getGradePoint(), modCredits);
            }
            if (validModulesTaken.get(mod).getExpectedMaxGrade().isCountedInCap()) {
                minMaxCapAveragesPerSem[validModulesTaken.get(mod).getSemester().getIndex()][1].addWeightedGrade(
                        validModulesTaken.get(mod).getExpectedMaxGrade().getGradePoint(), modCredits);
                cumulativeMaxCap.addWeightedGrade(
                        validModulesTaken.get(mod).getExpectedMaxGrade().getGradePoint(), modCredits);
            }

            if (validModulesTaken.get(mod).isFinished(validCurrentSemester)
                    && validModulesTaken.get(mod).getExpectedMinGrade().isCountedInCap()) {
                currentCap.addWeightedGrade(
                        validModulesTaken.get(mod).getExpectedMinGrade().getGradePoint(), modCredits);
            }
            semesterSums[validModulesTaken.get(mod).getSemester().getIndex()][0] +=
                    validModulesTaken.get(mod).getLectureHour().getHour();
            semesterSums[validModulesTaken.get(mod).getSemester().getIndex()][1] +=
                    validModulesTaken.get(mod).getTutorialHour().getHour();
            semesterSums[validModulesTaken.get(mod).getSemester().getIndex()][2] +=
                    validModulesTaken.get(mod).getLabHour().getHour();
            semesterSums[validModulesTaken.get(mod).getSemester().getIndex()][3] +=
                    validModulesTaken.get(mod).getProjectHour().getHour();
            semesterSums[validModulesTaken.get(mod).getSemester().getIndex()][4] +=
                    validModulesTaken.get(mod).getPreparationHour().getHour();
        }
        double[] cumulativeMinCurrentMaxCap = new double[3];
        cumulativeMinCurrentMaxCap[0] = cumulativeMinCap.getCapLimit();
        cumulativeMinCurrentMaxCap[1] = currentCap.getCapLimit();
        cumulativeMinCurrentMaxCap[2] = cumulativeMaxCap.getCapLimit();

        double[][] capTable = new double[validSemesterLimits.size()][CAP_TABLE_COL_COUNT];
        for (int sem = 0; sem < validSemesterLimits.size(); sem++) {
            capTable[sem][0] = validSemesterLimits.get(sem).getMinCap().getCapLimit();
            capTable[sem][1] = minMaxCapAveragesPerSem[sem][0].getCapLimit();
            capTable[sem][2] = minMaxCapAveragesPerSem[sem][1].getCapLimit();
            capTable[sem][3] = validSemesterLimits.get(sem).getMaxCap().getCapLimit();
        }

        double[][] workloadTable =
                new double[validSemesterLimits.size() * WORKLOAD_TABLE_ROW_COUNT][WORKLOAD_TABLE_COL_COUNT];
        for (int sem = 0; sem < validSemesterLimits.size(); sem++) {
            int rowIndexOffSet = sem * WORKLOAD_TABLE_ROW_COUNT;

            workloadTable[rowIndexOffSet][0] = validSemesterLimits.get(sem).getMinLectureHour().getHour();
            workloadTable[rowIndexOffSet][1] = semesterSums[sem][0];
            workloadTable[rowIndexOffSet][2] = validSemesterLimits.get(sem).getMaxLectureHour().getHour();

            workloadTable[rowIndexOffSet + 1][0] = validSemesterLimits.get(sem).getMinTutorialHour().getHour();
            workloadTable[rowIndexOffSet + 1][1] = semesterSums[sem][1];
            workloadTable[rowIndexOffSet + 1][2] = validSemesterLimits.get(sem).getMaxTutorialHour().getHour();

            workloadTable[rowIndexOffSet + 2][0] = validSemesterLimits.get(sem).getMinLabHour().getHour();
            workloadTable[rowIndexOffSet + 2][1] = semesterSums[sem][2];
            workloadTable[rowIndexOffSet + 2][2] = validSemesterLimits.get(sem).getMaxLabHour().getHour();

            workloadTable[rowIndexOffSet + 3][0] = validSemesterLimits.get(sem).getMinProjectHour().getHour();
            workloadTable[rowIndexOffSet + 3][1] = semesterSums[sem][3];
            workloadTable[rowIndexOffSet + 3][2] = validSemesterLimits.get(sem).getMaxProjectHour().getHour();

            workloadTable[rowIndexOffSet + 4][0] = validSemesterLimits.get(sem).getMinPreparationHour().getHour();
            workloadTable[rowIndexOffSet + 4][1] = semesterSums[sem][4];
            workloadTable[rowIndexOffSet + 4][2] = validSemesterLimits.get(sem).getMaxPreparationHour().getHour();
        }

        final StringBuilder htmlLimits = new StringBuilder();

        htmlLimits.append("<span>Current Semester: " + validCurrentSemester.toString() + "</span><br>\n");
        htmlLimits.append("<table border='1'>\n");

        htmlLimits.append("<tr>\n");
        htmlLimits.append("<th>Minimum CAP for graduation</th>\n");
        htmlLimits.append("<th>CAP as of current semester</th>\n");
        htmlLimits.append("<th>Maximum CAP for graduation</th>\n");
        htmlLimits.append("</tr>\n");

        htmlLimits.append("<tr>\n");
        htmlLimits.append("<td>" + cumulativeMinCurrentMaxCap[0] + "</td>\n");
        htmlLimits.append("<td>" + cumulativeMinCurrentMaxCap[1] + "</td>\n");
        htmlLimits.append("<td>" + cumulativeMinCurrentMaxCap[2] + "</td>\n");
        htmlLimits.append("</tr>\n");

        htmlLimits.append("</table>\n");
        htmlLimits.append("<br>\n");

        htmlLimits.append("<table border='1'>\n");

        htmlLimits.append("<tr>\n");
        htmlLimits.append("<th></th>\n");
        htmlLimits.append("<th>Minimum semester CAP limit</th>\n");
        htmlLimits.append("<th>Minimum expected semester CAP</th>\n");
        htmlLimits.append("<th>Maximum expected semester CAP</th>\n");
        htmlLimits.append("<th>Maximum semester CAP limit</th>\n");
        htmlLimits.append("</tr>\n");

        for (int sem = 0; sem < capTable.length; sem++) {
            htmlLimits.append("<tr>\n");
            htmlLimits.append("<td>" + Semester.getSemesterByZeroIndex(sem).toString() + "</td>\n");
            htmlLimits.append("<td>" + capTable[sem][0] + "</td>\n");
            htmlLimits.append("<td>" + capTable[sem][1] + "</td>\n");
            htmlLimits.append("<td>" + capTable[sem][2] + "</td>\n");
            htmlLimits.append("<td>" + capTable[sem][3] + "</td>\n");
            htmlLimits.append("</tr>\n");
        }
        htmlLimits.append("</table>\n");
        htmlLimits.append("<br>\n");

        for (int sem = 0; sem < workloadTable.length / WORKLOAD_TABLE_ROW_COUNT; sem++) {
            int rowIndexOffSet = sem * WORKLOAD_TABLE_ROW_COUNT;

            htmlLimits.append("<table border='1'>\n");

            htmlLimits.append("<tr>\n");
            htmlLimits.append("<th>" + Semester.getSemesterByZeroIndex(sem).toString() + "</th>\n");
            htmlLimits.append("<th>Minimum workload hours</th>\n");
            htmlLimits.append("<th>Current workload hours</th>\n");
            htmlLimits.append("<th>Maximum workload hours</th>\n");
            htmlLimits.append("</tr>\n");

            htmlLimits.append("<tr>\n");
            htmlLimits.append("<td>Lecture hours</td>\n");
            htmlLimits.append("<td>" + workloadTable[rowIndexOffSet][0] + "</td>\n");
            htmlLimits.append("<td>" + workloadTable[rowIndexOffSet][1] + "</td>\n");
            htmlLimits.append("<td>" + workloadTable[rowIndexOffSet][2] + "</td>\n");
            htmlLimits.append("</tr>\n");

            htmlLimits.append("<tr>\n");
            htmlLimits.append("<td>Tutorial hours</td>\n");
            htmlLimits.append("<td>" + workloadTable[rowIndexOffSet + 1][0] + "</td>\n");
            htmlLimits.append("<td>" + workloadTable[rowIndexOffSet + 1][1] + "</td>\n");
            htmlLimits.append("<td>" + workloadTable[rowIndexOffSet + 1][2] + "</td>\n");
            htmlLimits.append("</tr>\n");

            htmlLimits.append("<tr>\n");
            htmlLimits.append("<td>Lab hours</td>\n");
            htmlLimits.append("<td>" + workloadTable[rowIndexOffSet + 2][0] + "</td>\n");
            htmlLimits.append("<td>" + workloadTable[rowIndexOffSet + 2][1] + "</td>\n");
            htmlLimits.append("<td>" + workloadTable[rowIndexOffSet + 2][2] + "</td>\n");
            htmlLimits.append("</tr>\n");

            htmlLimits.append("<tr>\n");
            htmlLimits.append("<td>Project hours</td>\n");
            htmlLimits.append("<td>" + workloadTable[rowIndexOffSet + 3][0] + "</td>\n");
            htmlLimits.append("<td>" + workloadTable[rowIndexOffSet + 3][1] + "</td>\n");
            htmlLimits.append("<td>" + workloadTable[rowIndexOffSet + 3][2] + "</td>\n");
            htmlLimits.append("</tr>\n");

            htmlLimits.append("<tr>\n");
            htmlLimits.append("<td>Preparation hours</td>\n");
            htmlLimits.append("<td>" + workloadTable[rowIndexOffSet + 4][0] + "</td>\n");
            htmlLimits.append("<td>" + workloadTable[rowIndexOffSet + 4][1] + "</td>\n");
            htmlLimits.append("<td>" + workloadTable[rowIndexOffSet + 4][2] + "</td>\n");
            htmlLimits.append("</tr>\n");

            htmlLimits.append("</table>\n");
        }
        assertEquals(limitChecker.getPrintable(), htmlLimits.toString());

        CommandResult commandResult = new CheckLimitCommand().execute(model, commandHistory);

        assertEquals(CheckLimitCommand.MESSAGE_SUCCESS, commandResult.getFeedbackToUser());
    }
}
