package main;

import data.EpsEffort;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.*;


public class EpsEffortContainer {
    private Map<Calendar, List<EpsEffort>> epsEffortByDate;

    public EpsEffortContainer() {
        List<EpsEffort> epsEffortList = new CsvReader().readCsvFile();
        sortByDate(epsEffortList);
    }

    private void sortByDate(List<EpsEffort> epsEffortList) {
        epsEffortByDate = new HashMap<>();
        for (EpsEffort epsEffort : epsEffortList) {
            List<EpsEffort> tempList = epsEffortByDate.get(epsEffort.Calendar);
            if (tempList == null) {
                tempList = new ArrayList<>();
            }
            tempList.add(epsEffort);
            this.epsEffortByDate.put(epsEffort.Calendar, tempList);
        }
    }
    /**
     * Explanation:
     * es geht nur um -> "main map -> epsEffortByDate" !
     * Im Ersten forLoop erstelle ich eine Map und eine Liste (die Liste ist wichtig, da ich die benutzen werde um sie der map "main map -> epsEffortsByDate" hinzufügen)
     * Die "existingEpsEffort" Map habe ich nur um mir meine epsEffort objekte kurzzeitig zu speicher für das jeweilige datum durch das ich im ersten ForLoop iteriere
     * In der If abfrage überprüfe ich ob das "currentEpsEffort" mit der gleichen Projektnummer schon in der Map ist,
     * falls nicht füge ich es der Liste hinzu und gebe die Liste meiner "main map -> epsEffortsByDate" mit.
     * Wenn es in der map existingEpsEffort schon ein currentEpsEffort mit dieser Projektnummer gibt, dann merge ich das Objekt
     * <p>
     * <p>
     * Todo Bitte keine weiteren fragen, falls Kopfschmerzen gehen Sie zum Ihren Arzt oder Apotheker.
     */
    public Map<Calendar, List<EpsEffort>> getEpsEfforts() throws ParseException {
        Map<Calendar, List<EpsEffort>> epsEffortsByDate = new HashMap<>();
        for (Map.Entry<Calendar, List<EpsEffort>> allEfforts : epsEffortByDate.entrySet()) {
            Map<String, EpsEffort> existingEpsEffort = new HashMap<>();
            List<EpsEffort> epsEffortList = new ArrayList<>();
            for (EpsEffort currentEpsEffort : allEfforts.getValue()) {
                EpsEffort epsEffort = existingEpsEffort.get(currentEpsEffort.ProjectNumber);
                existingEpsEffort.put(currentEpsEffort.ProjectNumber, currentEpsEffort);
                if (epsEffort == null) {
                    epsEffortList.add(currentEpsEffort);
                    epsEffortsByDate.put(allEfforts.getKey(), epsEffortList);
                } else {
                    currentEpsEffort.mergeEpsEfforts(epsEffort);
                }
            }
        }
        //Ab hier werden die Zeiten für die Objekte gesetzt
        String eightClock = "8:00";
        for (Map.Entry<Calendar, List<EpsEffort>> allEfforts : epsEffortByDate.entrySet()) {
            String localStartTime = null;
            String localEndTime = null;
            for (EpsEffort epsEffort : allEfforts.getValue()) {
                if (localStartTime == null) {
                    epsEffort.StartTime = eightClock;
                    localStartTime = epsEffort.StartTime;
                    epsEffort.EndTime = calculateTotalTime(epsEffort, localStartTime);
                    localEndTime = epsEffort.EndTime;
                } else {
                    epsEffort.StartTime = localEndTime;
                    epsEffort.EndTime = calculateTotalTime(epsEffort, localEndTime);
                    localEndTime = epsEffort.EndTime;
                }
            }
        }
        return epsEffortsByDate;
    }

    public static String calculateTotalTime(EpsEffort epsEffort, String localStartOrEndTime){
        DecimalFormat df = new DecimalFormat("#.##");
        String[] endTimeArray = String.valueOf(df.format(epsEffort.TimeSpan)).split("\\.");
        String timeSpanAsString = String.valueOf(Integer.parseInt(endTimeArray[0]) + ":" +   String.valueOf(Integer.parseInt(endTimeArray[1])));
        int time1 = getTotalMinutes(timeSpanAsString);
        int time2 = getTotalMinutes(localStartOrEndTime);
        int totalTime = time1 + time2;
        return getResult(totalTime);
    }

    public static int getTotalMinutes(String time) {
        String[] t = time.split(":");
        return Integer.valueOf(t[0]) * 60 + Integer.valueOf(t[1]);
    }

    public static String getResult(int total) {
        int minutes = total % 60;
        int hours = ((total - minutes) / 60) % 24;
        return String.format("%02d:%02d", hours, minutes);
    }
}
