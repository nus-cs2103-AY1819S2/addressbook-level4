package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.moduleinfo.ModuleInfoCredits;
import seedu.address.model.moduletaken.CapAverage;
import seedu.address.model.moduletaken.ModuleTaken;
import seedu.address.model.moduletaken.Semester;

/**
 * Checks the CAP and Workload limits set by the user for every semester against the modules the user plans to take.
 */
public class LimitChecker {
    private static final int CAP_TABLE_COL_COUNT = 4;
    private static final int WORKLOAD_TABLE_ROW_COUNT = 5;
    private static final int WORKLOAD_TABLE_COL_COUNT = 3;

    /**
     * Returns a generated html string that shows where their CAP and workload limits are violated.
     */
    public static String checkLimit(Semester currentSemester, ObservableList<SemLimit> semLimits,
                                    ObservableList<ModuleTaken> modulesTaken) {
        //TODO refactor by making a limit checker class with these variables
        currentSemester = Semester.valueOf("Y2S2"); //temp

        CapAverage currentCap = new CapAverage();
        CapAverage cumulativeMinCap = new CapAverage();
        CapAverage cumulativeMaxCap = new CapAverage();
        CapAverage[][] minMaxCapAveragesPerSem = new CapAverage[semLimits.size()][2];
        for (int i = 0; i < minMaxCapAveragesPerSem.length; i++) {
            for (int j = 0; j < minMaxCapAveragesPerSem[i].length; j++) {
                minMaxCapAveragesPerSem[i][j] = new CapAverage();
            }
        }

        double[][] semesterSums = new double[semLimits.size()][5];
        for (int mod = 0; mod < modulesTaken.size(); mod++) {
            //TODO get credits info from module info
            ModuleInfoCredits modCredits = new ModuleInfoCredits(4);

            minMaxCapAveragesPerSem[modulesTaken.get(mod).getSemester().getIndex() - 1][0].addWeightedGrade(
                    modulesTaken.get(mod).getExpectedMinGrade().getGradePoint(), modCredits);
            minMaxCapAveragesPerSem[modulesTaken.get(mod).getSemester().getIndex() - 1][1].addWeightedGrade(
                    modulesTaken.get(mod).getExpectedMaxGrade().getGradePoint(), modCredits);

            cumulativeMinCap.addWeightedGrade(
                    modulesTaken.get(mod).getExpectedMinGrade().getGradePoint(), modCredits);
            cumulativeMaxCap.addWeightedGrade(
                    modulesTaken.get(mod).getExpectedMaxGrade().getGradePoint(), modCredits);

            if (modulesTaken.get(mod).getSemester().getIndex() < currentSemester.getIndex()) {
                currentCap.addWeightedGrade(modulesTaken.get(mod).getExpectedMinGrade().getGradePoint(), modCredits);
            }
            semesterSums[modulesTaken.get(mod).getSemester().getIndex() - 1][0] +=
                    modulesTaken.get(mod).getLectureHour().getHour();
            semesterSums[modulesTaken.get(mod).getSemester().getIndex() - 1][1] +=
                    modulesTaken.get(mod).getLectureHour().getHour();
            semesterSums[modulesTaken.get(mod).getSemester().getIndex() - 1][2] +=
                    modulesTaken.get(mod).getLectureHour().getHour();
            semesterSums[modulesTaken.get(mod).getSemester().getIndex() - 1][3] +=
                    modulesTaken.get(mod).getLectureHour().getHour();
            semesterSums[modulesTaken.get(mod).getSemester().getIndex() - 1][4] +=
                    modulesTaken.get(mod).getLectureHour().getHour();
        }
        double[] cumulativeMinCurrentMaxCap = new double[3];
        cumulativeMinCurrentMaxCap[0] = cumulativeMinCap.getCapLimit();
        cumulativeMinCurrentMaxCap[1] = currentCap.getCapLimit();
        cumulativeMinCurrentMaxCap[2] = cumulativeMaxCap.getCapLimit();

        double[][] capTable = new double[semLimits.size()][CAP_TABLE_COL_COUNT];
        for (int sem = 0; sem < semLimits.size(); sem++) {
            capTable[sem][0] = semLimits.get(sem).getMinCap().getCapLimit();
            capTable[sem][1] = minMaxCapAveragesPerSem[sem][0].getCapLimit();
            capTable[sem][2] = minMaxCapAveragesPerSem[sem][1].getCapLimit();
            capTable[sem][3] = semLimits.get(sem).getMaxCap().getCapLimit();
        }

        double[][] workloadTable =
                new double[semLimits.size() * WORKLOAD_TABLE_ROW_COUNT][WORKLOAD_TABLE_COL_COUNT];
        for (int sem = 0; sem < semLimits.size(); sem++) {
            int rowIndexOffSet = sem * WORKLOAD_TABLE_ROW_COUNT;

            workloadTable[rowIndexOffSet][0] = semLimits.get(sem).getMinLectureHour().getHour();
            workloadTable[rowIndexOffSet][1] = semesterSums[sem][0];
            workloadTable[rowIndexOffSet][2] = semLimits.get(sem).getMaxLectureHour().getHour();

            workloadTable[rowIndexOffSet + 1][0] = semLimits.get(sem).getMinTutorialHour().getHour();
            workloadTable[rowIndexOffSet + 1][1] = semesterSums[sem][1];
            workloadTable[rowIndexOffSet + 1][2] = semLimits.get(sem).getMaxTutorialHour().getHour();

            workloadTable[rowIndexOffSet + 2][0] = semLimits.get(sem).getMinLabHour().getHour();
            workloadTable[rowIndexOffSet + 2][1] = semesterSums[sem][2];
            workloadTable[rowIndexOffSet + 2][2] = semLimits.get(sem).getMaxLabHour().getHour();

            workloadTable[rowIndexOffSet + 3][0] = semLimits.get(sem).getMinProjectHour().getHour();
            workloadTable[rowIndexOffSet + 3][1] = semesterSums[sem][3];
            workloadTable[rowIndexOffSet + 3][2] = semLimits.get(sem).getMaxProjectHour().getHour();

            workloadTable[rowIndexOffSet + 4][0] = semLimits.get(sem).getMinPreparationHour().getHour();
            workloadTable[rowIndexOffSet + 4][1] = semesterSums[sem][4];
            workloadTable[rowIndexOffSet + 4][2] = semLimits.get(sem).getMaxPreparationHour().getHour();
        }
        return generateHtmlLimits(cumulativeMinCurrentMaxCap, capTable, workloadTable);
    }

    /**
     * Puts the computed cap and workload limits together with the current module plan expectations
     * into a table for display
     *
     * @param cumulativeMinCurrentMaxCap a table with the current CAP and the minimum and maximum expected CAP
     * @param capTable a table with the minimum and maximum CAP limits and the minimum and maximum expected CAP
     *                 for each semester
     * @param workloadTable a table with the minimum and maximum workload limits and the expected workload
     *                      for each semester
     * @return a html string that shows where their CAP and workload limits are violated.
     */
    private static String generateHtmlLimits(double[] cumulativeMinCurrentMaxCap,
                                     double[][] capTable, double[][] workloadTable) {
        String htmlLimits = "";
        htmlLimits += "<html>\n";

        htmlLimits += "<table>\n";

        htmlLimits += "<tr>\n";
        htmlLimits += "<th>Minimum Cap</th>\n";
        htmlLimits += "<th>Current Cap</th>\n";
        htmlLimits += "<th>Maximum Cap</th>\n";
        htmlLimits += "</tr>\n";

        htmlLimits += "<tr>\n";
        htmlLimits += "<td>" + cumulativeMinCurrentMaxCap[0] + "</td>\n";
        htmlLimits += "<td>" + cumulativeMinCurrentMaxCap[1] + "</td>\n";
        htmlLimits += "<td>" + cumulativeMinCurrentMaxCap[2] + "</td>\n";
        htmlLimits += "</tr>\n";

        htmlLimits += "</table>\n";
        htmlLimits += "<br><br>\n";

        htmlLimits += "<table>\n";

        htmlLimits += "<tr>\n";
        htmlLimits += "<th></th>\n";
        htmlLimits += "<th>Minimum semester CAP limit</th>\n";
        htmlLimits += "<th>Minimum expected semester CAP</th>\n";
        htmlLimits += "<th>Maximum expected semester CAP</th>\n";
        htmlLimits += "<th>Maximum semester CAP limit</th>\n";
        htmlLimits += "</tr>\n";

        for (int sem = 0; sem < capTable.length; sem++) {
            htmlLimits += "<tr>\n";
            htmlLimits += "<td>" + Semester.getSemesterByZeroIndex(sem).toString() + "</td>\n";
            htmlLimits += "<td>" + capTable[sem][0] + "</td>\n";
            htmlLimits += "<td>" + capTable[sem][1] + "</td>\n";
            htmlLimits += "<td>" + capTable[sem][2] + "</td>\n";
            htmlLimits += "<td>" + capTable[sem][3] + "</td>\n";
            htmlLimits += "</tr>\n";
        }
        htmlLimits += "</table>\n";
        htmlLimits += "<br><br>\n";

        for (int sem = 0; sem < WORKLOAD_TABLE_ROW_COUNT; sem++) {
            int rowIndexOffSet = sem * WORKLOAD_TABLE_ROW_COUNT;

            htmlLimits += "<table>\n";

            htmlLimits += "<tr>\n";
            htmlLimits += "<th>" + Semester.getSemesterByZeroIndex(sem).toString() + "</th>\n";
            htmlLimits += "<th>Minimum workload hours</th>\n";
            htmlLimits += "<th>Current workload hours</th>\n";
            htmlLimits += "<th>Maximum workload hours</th>\n";
            htmlLimits += "</tr>\n";

            htmlLimits += "<tr>\n";
            htmlLimits += "<td>Lecture hours</td>\n";
            htmlLimits += "<td>" + workloadTable[rowIndexOffSet][0] + "</td>\n";
            htmlLimits += "<td>" + workloadTable[rowIndexOffSet][1] + "</td>\n";
            htmlLimits += "<td>" + workloadTable[rowIndexOffSet][2] + "</td>\n";
            htmlLimits += "</tr>\n";

            htmlLimits += "<tr>\n";
            htmlLimits += "<td>Tutorial hours</td>\n";
            htmlLimits += "<td>" + workloadTable[rowIndexOffSet + 1][0] + "</td>\n";
            htmlLimits += "<td>" + workloadTable[rowIndexOffSet + 1][1] + "</td>\n";
            htmlLimits += "<td>" + workloadTable[rowIndexOffSet + 1][2] + "</td>\n";
            htmlLimits += "</tr>\n";

            htmlLimits += "<tr>\n";
            htmlLimits += "<td>Lab hours</td>\n";
            htmlLimits += "<td>" + workloadTable[rowIndexOffSet + 2][0] + "</td>\n";
            htmlLimits += "<td>" + workloadTable[rowIndexOffSet + 2][1] + "</td>\n";
            htmlLimits += "<td>" + workloadTable[rowIndexOffSet + 2][2] + "</td>\n";
            htmlLimits += "</tr>\n";

            htmlLimits += "<tr>\n";
            htmlLimits += "<td>Project hours</td>\n";
            htmlLimits += "<td>" + workloadTable[rowIndexOffSet + 3][0] + "</td>\n";
            htmlLimits += "<td>" + workloadTable[rowIndexOffSet + 3][1] + "</td>\n";
            htmlLimits += "<td>" + workloadTable[rowIndexOffSet + 3][2] + "</td>\n";
            htmlLimits += "</tr>\n";

            htmlLimits += "<tr>\n";
            htmlLimits += "<td>Preparation hours</td>\n";
            htmlLimits += "<td>" + workloadTable[rowIndexOffSet + 4][0] + "</td>\n";
            htmlLimits += "<td>" + workloadTable[rowIndexOffSet + 4][1] + "</td>\n";
            htmlLimits += "<td>" + workloadTable[rowIndexOffSet + 4][2] + "</td>\n";
            htmlLimits += "</tr>\n";

            htmlLimits += "</table>\n";
            htmlLimits += "<br><br>\n";
        }

        htmlLimits += "</html>\n";
        return htmlLimits;
    }
}
