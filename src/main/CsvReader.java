package main;

import com.opencsv.CSVReader;
import data.EpsEffort;
import javax.swing.*;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;


public class CsvReader {

    public List<EpsEffort> readCsvFile() {
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle("choosertitle");
        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        chooser.setAcceptAllFileFilterUsed(false);
        File file = null;
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            file = chooser.getSelectedFile();
        }
        List<EpsEffort> epsEffortList = new ArrayList<>();
        List<String[]> errorLines = new ArrayList<>();
        try {
            CSVReader reader = new CSVReader(new FileReader(file));
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
            throw new IllegalArgumentException(errorLines.toString());
        }
        return epsEffortList;
    }
}
