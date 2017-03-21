package data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class EpsEffort {

    public final static String DATE_FORMAT = "MM/dd/yyyy";
    private SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, Locale.GERMAN);
    private final static Pattern projectNumberPattern = Pattern.compile("[0-9]{3,5}\\s?-");

    public String Project;
    public String ProjectNumber;
    public String Task;
    public String Note;
    public Calendar Calendar;
    public Calendar StartTime;
    public Calendar EndTime;
    public String ExternalComment;
    public double TimeSpan;
    private int _timeSpanInMinutes;


    public EpsEffort(String[] nextLine) {
        try {
            Project = nextLine[1];
        } catch (Exception e) {
            throw new RuntimeException("There is no Project Number ");
        }
        Task = nextLine[2];
        try {
            Calendar = java.util.Calendar.getInstance();
            Calendar.setTime(sdf.parse(nextLine[3]));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        TimeSpan = Double.parseDouble(nextLine[6]);
        Note = nextLine[7];
        setExtractedProjectNumberFromOtherFields();
        setExternalComment();
    }

    public void setTimeSpan(String hoursCommaSeparated) {
        double hours = Double.parseDouble(hoursCommaSeparated);
        _timeSpanInMinutes = (int) Math.round(hours * 60);
    }

    public int getTimeSpanInMinutes() {
        return _timeSpanInMinutes;
    }

    public void addExternalComment(String externalComment) {
        if (!ExternalComment.contains(externalComment)) {
            ExternalComment = ExternalComment + " " + externalComment;
        }
    }

    public void addTimeSpan(double timeSpan) {
        TimeSpan += timeSpan;
    }

    public void setExternalComment() {
        ExternalComment = Note.replaceAll("\"", "");

        if (ExternalComment.isEmpty()) {
            ExternalComment = Task.replaceAll("\"", "");
        }
    }

    private void setExtractedProjectNumberFromOtherFields() {
        ProjectNumber = extractProjectNumberByRegEx(Note);

        if (ProjectNumber.isEmpty()) {
            ProjectNumber = extractProjectNumberByRegEx(Task);
        }
        if (ProjectNumber.isEmpty()) {
            ProjectNumber = extractProjectNumberByRegEx(Project);
        }
        if (ProjectNumber.isEmpty()) {
            throw new RuntimeException("ProjectNumber number is missing for project: " + Project + ", task: " + Task + ", note: " + Note + "!");
        }
    }

    private String extractProjectNumberByRegEx(String text) {
        Matcher projectNumber = projectNumberPattern.matcher(text);
        String extractedProjectNbmr = "";
        if (projectNumber.find()) {
            extractedProjectNbmr = projectNumber.group(0).replaceAll("\\s?-", "");
        }
        if (extractedProjectNbmr.length() <= 3 && !extractedProjectNbmr.isEmpty()) {
            extractedProjectNbmr = "Kostenstelle: Project: " + extractedProjectNbmr;
            return extractedProjectNbmr;
        }
        if (extractedProjectNbmr.length() > 3) {
            extractedProjectNbmr = "Project: " + extractedProjectNbmr;
            return extractedProjectNbmr;
        }
        return "";
    }
}
