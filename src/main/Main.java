package main;

import data.EpsEffort;
import java.text.ParseException;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import static main.EpsEffortContainer.convertCalendarDateToString;
import static main.EpsEffortContainer.convertCalendarStartEndTimeToSting;


public class Main{

    public static void main(String[] args) throws ParseException {
        CsvReader csvReader = new CsvReader();
        List<EpsEffort> csvData = csvReader.readCsvFile();
        EpsEffortContainer epsContainer = new EpsEffortContainer(csvData);
        SapNavigator.startSelenium();
        for (Map.Entry<Calendar, List<EpsEffort>> allEfforts : epsContainer.getEpsEfforts().entrySet()) {
            for (EpsEffort epsEffort : allEfforts.getValue()) {
                SapNavigator.fillInData(epsEffort.ProjectNumber,
                                        convertCalendarDateToString(epsEffort.Calendar),
                                        convertCalendarStartEndTimeToSting(epsEffort.StartTime),
                                        convertCalendarStartEndTimeToSting(epsEffort.EndTime),
                                        epsEffort.ExternalComment);
            }
        }
        SapNavigator.killDriver();
    }
}
