package main;

import com.opencsv.CSVReader;
import data.EpsEffort;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class CsvReader {
    private static String filePath = "C:\\Users\\epsadmin\\Documents\\EPSFocusPlugins\\trunk\\Develop\\TimeMinion\\hours-export(9).csv";

    public List<EpsEffort> readCsvFile() {
        List<EpsEffort> epsEffortList = new ArrayList<>();
        List<String[]> errorLines = new ArrayList<>();
        try {
            CSVReader reader = new CSVReader(new FileReader(filePath));
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                try {
                    EpsEffort epsEffort = new EpsEffort(nextLine);
                    epsEffortList.add(epsEffort);
                } catch (Exception e) {
                    e.printStackTrace();
                    errorLines.add(nextLine);
                }
            }
        } catch (Exception e) {}
        if (!errorLines.isEmpty()) {
            //Todo throw exception with information to missing csv - data / incorrect csv - data
            throw new IllegalArgumentException(errorLines.toString());
        }
        return epsEffortList;
    }
}
