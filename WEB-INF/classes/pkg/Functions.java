package pkg;

import java.time.LocalTime;
import java.util.ArrayList;

public class Functions {
    public static boolean isOdd(int num){
        if (num % 2 ==0){
            return false;
        }
        else{
            return true;
        }
    }
    public static ArrayList<LocalTime> getTimeIncrementsFrom0To24Hours(){
        ArrayList<LocalTime> timeIncrementsList = new ArrayList<>();
        LocalTime tempLocalTime = LocalTime.of(0,0,0);
        for (int i = 0 ; i < 96; i++){
            timeIncrementsList.add(tempLocalTime);
            LocalTime temp2LocalTime = tempLocalTime.plusMinutes(15);
            tempLocalTime = temp2LocalTime;
        }
        return timeIncrementsList;
    }

    public static ArrayList<String> getDayNames(){
        ArrayList<String> dayNames = new ArrayList<>();
        dayNames.add("Monday");
        dayNames.add("Tuesday");
        dayNames.add("Wednesday");
        dayNames.add("Thursday");
        dayNames.add("Friday");
        dayNames.add("Saturday");
        dayNames.add("Sunday");
        return dayNames;
    }

    public static ArrayList<LocalTime> getTimeIncrementsForDay(){
        ArrayList<LocalTime> timeIncrementsList = new ArrayList<>();
        LocalTime tempLocalTime = LocalTime.of(0,0,0);
        for (int i = 0 ; i < 96; i++){
            timeIncrementsList.add(tempLocalTime);
            LocalTime temp2LocalTime = tempLocalTime.plusMinutes(15);
            tempLocalTime = temp2LocalTime;
        }
        return timeIncrementsList;
    }

}
