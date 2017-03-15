//package main;
//
//import data.EpsEffort;
//import org.junit.Test;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Arrays;
//import java.util.List;
//
//import static data.EpsEffort.DATE_FORMAT;
//import static org.mockito.Mockito.*;
//
//
//public class EpsEffortContainerTest {
//
//    @Test
//    public void createCsvDataContainerTest() throws ParseException {
//        EpsEffort epsEffort = mock(EpsEffort.class);
//        epsEffort.Calendar = new SimpleDateFormat(DATE_FORMAT).parse("01/06/1999");
//
//
//        List<EpsEffort> epsEffortList = Arrays.asList(epsEffort);
////        EpsEffortContainer epsEffortContainer = new EpsEffortContainer(epsEffortList);
////        Assert.assertNotEquals(null , epsEffortContainer);
//
//    }
//}