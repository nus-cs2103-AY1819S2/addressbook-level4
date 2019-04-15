package seedu.address.model.limits;

import javafx.collections.ObservableList;
import seedu.address.model.ClassForPrinting;
import seedu.address.model.moduleinfo.ModuleInfo;
import seedu.address.model.moduleinfo.ModuleInfoCredits;
import seedu.address.model.moduleinfo.ModuleInfoList;
import seedu.address.model.moduletaken.CapAverage;
import seedu.address.model.moduletaken.ModuleTaken;
import seedu.address.model.moduletaken.Semester;

/**
 * Checks the CAP and Workload limits set by the user for every semester against the modules the user plans to take.
 */
public class LimitChecker implements ClassForPrinting {
    private static final double DEFAULT_MODULE_CREDITS = 4;
    private static final int CAP_TABLE_COL_COUNT = 4;
    private static final int WORKLOAD_TABLE_ROW_COUNT = 5;
    private static final int WORKLOAD_TABLE_COL_COUNT = 3;

    private final String checkedReport;

    /**
     * Returns a generated html string that shows where their CAP and workload limits are violated.
     *
     * @param currentSemester the current semester when running the limit checker.
     * @param semesterLimits the current semester limits of the user when running the limit checker.
     * @param modulesTaken the current modules taken of the user when running the limit checker.
     * @param moduleInfoList the full list of ModuleInfo for checking the modules credits of each module
     */
    public LimitChecker(Semester currentSemester, ObservableList<SemesterLimit> semesterLimits,
                                    ObservableList<ModuleTaken> modulesTaken, ModuleInfoList moduleInfoList) {
        CapAverage currentCap = new CapAverage();
        CapAverage cumulativeMinCap = new CapAverage();
        CapAverage cumulativeMaxCap = new CapAverage();
        CapAverage[][] minMaxCapAveragesPerSem = new CapAverage[semesterLimits.size()][2];
        for (int i = 0; i < minMaxCapAveragesPerSem.length; i++) {
            for (int j = 0; j < minMaxCapAveragesPerSem[i].length; j++) {
                minMaxCapAveragesPerSem[i][j] = new CapAverage();
            }
        }

        double[][] semesterSums = new double[semesterLimits.size()][5];
        for (int mod = 0; mod < modulesTaken.size(); mod++) {
            ModuleInfoCredits modCredits = new ModuleInfoCredits(DEFAULT_MODULE_CREDITS);
            ModuleInfo moduleInfo = moduleInfoList.getModule(String.valueOf(modulesTaken.get(mod).getModuleInfoCode()));
            if (moduleInfo != null) {
                modCredits = new ModuleInfoCredits(moduleInfo.getCredits());
            }

            if (modulesTaken.get(mod).getExpectedMinGrade().isCountedInCap()) {
                minMaxCapAveragesPerSem[modulesTaken.get(mod).getSemester().getIndex()][0].addWeightedGrade(
                        modulesTaken.get(mod).getExpectedMinGrade().getGradePoint(), modCredits);
                cumulativeMinCap.addWeightedGrade(
                        modulesTaken.get(mod).getExpectedMinGrade().getGradePoint(), modCredits);
            }
            if (modulesTaken.get(mod).getExpectedMaxGrade().isCountedInCap()) {
                minMaxCapAveragesPerSem[modulesTaken.get(mod).getSemester().getIndex()][1].addWeightedGrade(
                        modulesTaken.get(mod).getExpectedMaxGrade().getGradePoint(), modCredits);
                cumulativeMaxCap.addWeightedGrade(
                        modulesTaken.get(mod).getExpectedMaxGrade().getGradePoint(), modCredits);
            }

            if (modulesTaken.get(mod).isFinished(currentSemester)
                    && modulesTaken.get(mod).getExpectedMinGrade().isCountedInCap()) {
                currentCap.addWeightedGrade(modulesTaken.get(mod).getExpectedMinGrade().getGradePoint(), modCredits);
            }
            semesterSums[modulesTaken.get(mod).getSemester().getIndex()][0] +=
                    modulesTaken.get(mod).getLectureHour().getHour();
            semesterSums[modulesTaken.get(mod).getSemester().getIndex()][1] +=
                    modulesTaken.get(mod).getTutorialHour().getHour();
            semesterSums[modulesTaken.get(mod).getSemester().getIndex()][2] +=
                    modulesTaken.get(mod).getLabHour().getHour();
            semesterSums[modulesTaken.get(mod).getSemester().getIndex()][3] +=
                    modulesTaken.get(mod).getProjectHour().getHour();
            semesterSums[modulesTaken.get(mod).getSemester().getIndex()][4] +=
                    modulesTaken.get(mod).getPreparationHour().getHour();
        }
        double[] cumulativeMinCurrentMaxCap = new double[3];
        cumulativeMinCurrentMaxCap[0] = cumulativeMinCap.getCapLimit();
        cumulativeMinCurrentMaxCap[1] = currentCap.getCapLimit();
        cumulativeMinCurrentMaxCap[2] = cumulativeMaxCap.getCapLimit();

        double[][] capTable = new double[semesterLimits.size()][CAP_TABLE_COL_COUNT];
        for (int sem = 0; sem < semesterLimits.size(); sem++) {
            capTable[sem][0] = semesterLimits.get(sem).getMinCap().getCapLimit();
            capTable[sem][1] = minMaxCapAveragesPerSem[sem][0].getCapLimit();
            capTable[sem][2] = minMaxCapAveragesPerSem[sem][1].getCapLimit();
            capTable[sem][3] = semesterLimits.get(sem).getMaxCap().getCapLimit();
        }

        double[][] workloadTable =
                new double[semesterLimits.size() * WORKLOAD_TABLE_ROW_COUNT][WORKLOAD_TABLE_COL_COUNT];
        for (int sem = 0; sem < semesterLimits.size(); sem++) {
            int rowIndexOffSet = sem * WORKLOAD_TABLE_ROW_COUNT;

            workloadTable[rowIndexOffSet][0] = semesterLimits.get(sem).getMinLectureHour().getHour();
            workloadTable[rowIndexOffSet][1] = semesterSums[sem][0];
            workloadTable[rowIndexOffSet][2] = semesterLimits.get(sem).getMaxLectureHour().getHour();

            workloadTable[rowIndexOffSet + 1][0] = semesterLimits.get(sem).getMinTutorialHour().getHour();
            workloadTable[rowIndexOffSet + 1][1] = semesterSums[sem][1];
            workloadTable[rowIndexOffSet + 1][2] = semesterLimits.get(sem).getMaxTutorialHour().getHour();

            workloadTable[rowIndexOffSet + 2][0] = semesterLimits.get(sem).getMinLabHour().getHour();
            workloadTable[rowIndexOffSet + 2][1] = semesterSums[sem][2];
            workloadTable[rowIndexOffSet + 2][2] = semesterLimits.get(sem).getMaxLabHour().getHour();

            workloadTable[rowIndexOffSet + 3][0] = semesterLimits.get(sem).getMinProjectHour().getHour();
            workloadTable[rowIndexOffSet + 3][1] = semesterSums[sem][3];
            workloadTable[rowIndexOffSet + 3][2] = semesterLimits.get(sem).getMaxProjectHour().getHour();

            workloadTable[rowIndexOffSet + 4][0] = semesterLimits.get(sem).getMinPreparationHour().getHour();
            workloadTable[rowIndexOffSet + 4][1] = semesterSums[sem][4];
            workloadTable[rowIndexOffSet + 4][2] = semesterLimits.get(sem).getMaxPreparationHour().getHour();
        }
        this.checkedReport = generateHtmlLimits(currentSemester, cumulativeMinCurrentMaxCap, capTable, workloadTable);
    }

    /**
     * Puts the computed cap and workload limits together with the current module plan expectations
     * into a table for display
     *
     * @param currentSemester the current semester set by the user
     * @param cumulativeMinCurrentMaxCap a table with the current CAP and the minimum and maximum expected CAP
     * @param capTable a table with the minimum and maximum CAP limits and the minimum and maximum expected CAP
     *                 for each semester
     * @param workloadTable a table with the minimum and maximum workload limits and the expected workload
     *                      for each semester
     * @return a html string that shows where their CAP and workload limits are violated.
     */
    private static String generateHtmlLimits(Semester currentSemester, double[] cumulativeMinCurrentMaxCap,
                                     double[][] capTable, double[][] workloadTable) {

        final StringBuilder htmlLimits = new StringBuilder();

        htmlLimits.append("<span>Current Semester: " + currentSemester.toString() + "</span><br>\n");
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
        return htmlLimits.toString();
    }

    @Override
    public String getPrintable() {
        return checkedReport;
    }
}
