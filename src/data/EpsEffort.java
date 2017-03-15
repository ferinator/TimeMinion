package data;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
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
    public String StartTime;
    public String EndTime;
    public String ExternalComment;
    public double TimeSpan;


    public EpsEffort(String[] nextLine) {
        try {
            Project = nextLine[1];
        } catch (Exception e) {
            throw new RuntimeException("There is no Project Number ");
        }
        Task = nextLine[2];

        try {
//            this.Calendar = new GregorianCalendar();
//            this.Calendar.setTime(sdf.parse(nextLine[3]));
            Calendar = java.util.Calendar.getInstance();
            Calendar.setTime(sdf.parse(nextLine[3]));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        TimeSpan = Double.parseDouble(nextLine[6]);
        Note = nextLine[7];
        setExtractedProjectNumberFromOtherFields();
    }

    public void mergeEpsEfforts(EpsEffort currentEpsEffort) {
        TimeSpan += currentEpsEffort.TimeSpan;
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

        if (projectNumber.find()) {
            return projectNumber.group(0).replaceAll("\\s?-", "");
        }
        return "";
    }
}
