package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static data.EpsEffort.DATE_FORMAT;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }

    public static void main(String[] args) throws ParseException {


        // Some Test
        String dateInString = "02/09/2017";
        Date date = null;
        try {
            date = new SimpleDateFormat(DATE_FORMAT).parse(dateInString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        EpsEffortContainer epsContainer = new EpsEffortContainer();
        System.out.println(epsContainer.getEpsEfforts());

    }
}
