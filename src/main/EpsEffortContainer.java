package main;

import data.EpsEffort;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class EpsEffortContainer {
    private Map<Calendar, List<EpsEffort>> epsEffortByDate;

    public EpsEffortContainer(List<EpsEffort> csvData) {
        epsEffortByDate = new HashMap<>();
        sortByDate(csvData);
    }

    private void sortByDate(List<EpsEffort> epsEffortList) {
        for (EpsEffort epsEffort : epsEffortList) {
            List<EpsEffort> tempList = epsEffortByDate.get(epsEffort.Calendar);
            if (tempList == null) {
                tempList = new ArrayList<>();
            }
            tempList.add(epsEffort);
            this.epsEffortByDate.put(epsEffort.Calendar, tempList);
        }
    }

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
                    epsEffort.addTimeSpan(currentEpsEffort.TimeSpan);
                    epsEffort.addExternalComment(currentEpsEffort.ExternalComment);
                    existingEpsEffort.put(epsEffort.ProjectNumber, epsEffort);
                }
            }
        }
        setEpsEffortsTimes(epsEffortsByDate);
        return epsEffortsByDate;
    }

    public void setEpsEffortsTimes( Map<Calendar, List<EpsEffort>> epsEffortsByDate) throws ParseException {
        for (Map.Entry<Calendar, List<EpsEffort>> allEfforts : epsEffortsByDate.entrySet()) {
            Calendar localStartTime = null;
            Calendar localEndTime = null;
            for (EpsEffort epsEffort : allEfforts.getValue()) {
                if (localStartTime == null) {
                    epsEffort.StartTime = epsEffort.Calendar;
                    epsEffort.StartTime.set(Calendar.HOUR, 8);
                    localStartTime = epsEffort.StartTime;
                    epsEffort.setTimeSpan(String.valueOf(epsEffort.TimeSpan));
                    epsEffort.EndTime = calculateTotalTimeDorian(epsEffort.getTimeSpanInMinutes(), localStartTime);
                    localEndTime = epsEffort.EndTime;
                } else {
                    epsEffort.StartTime = localEndTime;
                    epsEffort.setTimeSpan(String.valueOf(epsEffort.TimeSpan));
                    epsEffort.EndTime = calculateTotalTimeDorian(epsEffort.getTimeSpanInMinutes(), localEndTime);
                    localEndTime = epsEffort.EndTime;
                }
            }
        }
    }

    public static Calendar calculateTotalTimeDorian(int timeSpan, Calendar previousEndTime){
        Calendar newEndTime = Calendar.getInstance();
        newEndTime.setTime(previousEndTime.getTime());
        newEndTime.add(Calendar.MINUTE, timeSpan);
        return newEndTime;
    }

    public static String convertCalendarDateToString(Calendar calendar) {
        Date date = calendar.getTime();
        SimpleDateFormat format1 = new SimpleDateFormat("dd.MM.yyyy");
        String inActiveDate = null;
        try {
            inActiveDate = format1.format(date);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return inActiveDate;
    }

    public static String convertCalendarStartEndTimeToSting(Calendar calendar) {
        calendar.add(Calendar.DATE, 1);
        Date date = calendar.getTime();
        SimpleDateFormat format1 = new SimpleDateFormat("HH:mm");
        String startEndTime = null;
        try {
            startEndTime = format1.format(date);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return startEndTime;
    }
}
