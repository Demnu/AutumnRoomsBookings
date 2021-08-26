package pkg;
import java.sql.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;


public class VenueDetailsDatabaseInterface {

    public static Venue getOpenCloseTimes(int venueID){
        Venue tempVenue = new Venue();
        ArrayList<LocalTime> openTimes = new ArrayList<>();
        ArrayList<LocalTime> closeTimes = new ArrayList<>();
        ChangedDateTimes changedDateTimes = ChangedDateDatabaseInterface.getChangedDateGivenDate(LocalDate.now());
        String query = "SELECT* FROM VenueDetails WHERE venueID =?";
        try(Connection connection = ConfigBean.getConnection();){

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, venueID);
            ResultSet result = preparedStatement.executeQuery();
            while(result.next()){
                tempVenue.setVenueID(1);

                tempVenue.setVenueName(result.getString(2));
                openTimes.add(result.getTime(3).toLocalTime());
                closeTimes.add(result.getTime(4).toLocalTime());
                openTimes.add(result.getTime(5).toLocalTime());
                closeTimes.add(result.getTime(6).toLocalTime());
                openTimes.add(result.getTime(7).toLocalTime());
                closeTimes.add(result.getTime(8).toLocalTime());
                openTimes.add(result.getTime(9).toLocalTime());
                closeTimes.add(result.getTime(10).toLocalTime());
                openTimes.add(result.getTime(11).toLocalTime());
                closeTimes.add(result.getTime(12).toLocalTime());
                openTimes.add(result.getTime(13).toLocalTime());
                closeTimes.add(result.getTime(14).toLocalTime());
                openTimes.add(result.getTime(15).toLocalTime());
                closeTimes.add(result.getTime(16).toLocalTime());
                tempVenue.setOpenTimes(openTimes);
                tempVenue.setCloseTimes(closeTimes);
                tempVenue.setMaxCovers(result.getInt(17));

                DayOfWeek dayOfWeekDayOfWeek = LocalDate.now().getDayOfWeek();
                int dayOfWeek = dayOfWeekDayOfWeek.getValue()-1;
                //get open and close time for specific date
                tempVenue.setTimeVenueOpens(openTimes.get(dayOfWeek));
                tempVenue.setTimeVenueCloses(closeTimes.get(dayOfWeek));

            }
            result.close();
            preparedStatement.close();
            connection.close();
            if (changedDateTimes!=null){
                System.out.println("Changed Date!");
                tempVenue.setTimeVenueOpens(changedDateTimes.getChangedOpenTime());
                tempVenue.setTimeVenueCloses(changedDateTimes.getChangedCloseTime());
            }
            return tempVenue;
        }
        catch(SQLException e){
            System.err.println(e.getMessage());
            System.err.println(e.getStackTrace());
        }
        return null;
    }
    public static Venue getOpenCloseTimesInputDate(int venueID, LocalDate date){
        Venue tempVenue = new Venue();
        ArrayList<LocalTime> openTimes = new ArrayList<>();
        ArrayList<LocalTime> closeTimes = new ArrayList<>();
        ChangedDateTimes changedDateTimes = ChangedDateDatabaseInterface.getChangedDateGivenDate(date);
        String query = "SELECT* FROM VenueDetails WHERE venueID =?";
        try(Connection connection = ConfigBean.getConnection();){

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, venueID);
            ResultSet result = preparedStatement.executeQuery();
            while(result.next()){
                tempVenue.setVenueID(1);

                tempVenue.setVenueName(result.getString(2));
                openTimes.add(result.getTime(3).toLocalTime());
                closeTimes.add(result.getTime(4).toLocalTime());
                openTimes.add(result.getTime(5).toLocalTime());
                closeTimes.add(result.getTime(6).toLocalTime());
                openTimes.add(result.getTime(7).toLocalTime());
                closeTimes.add(result.getTime(8).toLocalTime());
                openTimes.add(result.getTime(9).toLocalTime());
                closeTimes.add(result.getTime(10).toLocalTime());
                openTimes.add(result.getTime(11).toLocalTime());
                closeTimes.add(result.getTime(12).toLocalTime());
                openTimes.add(result.getTime(13).toLocalTime());
                closeTimes.add(result.getTime(14).toLocalTime());
                openTimes.add(result.getTime(15).toLocalTime());
                closeTimes.add(result.getTime(16).toLocalTime());
                tempVenue.setOpenTimes(openTimes);
                tempVenue.setCloseTimes(closeTimes);
                tempVenue.setMaxCovers(result.getInt(17));

                DayOfWeek dayOfWeekDayOfWeek = date.getDayOfWeek();
                int dayOfWeek = dayOfWeekDayOfWeek.getValue()-1;
                //get open and close time for specific date
                tempVenue.setTimeVenueOpens(openTimes.get(dayOfWeek));
                tempVenue.setTimeVenueCloses(closeTimes.get(dayOfWeek));

            }
            result.close();
            preparedStatement.close();
            connection.close();
            if (changedDateTimes!=null){
                System.out.println("Changed Date!");
                tempVenue.setTimeVenueOpens(changedDateTimes.getChangedOpenTime());
                tempVenue.setTimeVenueCloses(changedDateTimes.getChangedCloseTime());
            }
            return tempVenue;
        }
        catch(SQLException e){
            System.err.println(e.getMessage());
            System.err.println(e.getStackTrace());
        }
        return null;
    }

    public static Venue getVenueDetails(int venueID){
        Venue tempVenue = new Venue();
        String query = "SELECT* FROM VenueDetails WHERE venueID =?";
        try(Connection connection = ConfigBean.getConnection();){

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, venueID);
            ResultSet result = preparedStatement.executeQuery();

            while(result.next()){
                tempVenue.setVenueID(1);
                tempVenue.setVenueName(result.getString(2));
                tempVenue.setMaxCovers(result.getInt(17));
            }
            result.close();
            preparedStatement.close();
            connection.close();
            return tempVenue;
        }
        catch(SQLException e){
            System.err.println(e.getMessage());
            System.err.println(e.getStackTrace());
        }
        return null;
    }


    public static void editVenueTimes(int venueID, Time mondayOpenTime, Time mondayCloseTime, Time tuesdayOpenTime, Time tuesdayCloseTime, Time wednesdayOpenTime, Time wednesdayCloseTime, Time thursdayOpenTime, Time thursdayCloseTime, Time fridayOpenTime, Time fridayCloseTime, Time saturdayOpenTime, Time saturdayCloseTime, Time sundayOpenTime, Time sundayCloseTime) {
        try {
            // creates prepared statement and sets its values
            String query = "UPDATE VenueDetails SET openTimeMonday=?, closeTimeMonday=?, openTimeTuesday=?, closeTimeTuesday=?, openTimeWednesday=?, closeTimeWednesday=?, openTimeThursday=?, closeTimeThursday=?, openTimeFriday=?, closeTimeFriday=?, openTimeSaturday=?, closeTimeSaturday=?, openTimeSunday=?, closeTimeSunday=? WHERE venueID=?";
            Connection connection = ConfigBean.getConnection();
            PreparedStatement s = connection.prepareStatement(query);
            s.setTime(1, mondayOpenTime);
            s.setTime(2, mondayCloseTime);
            s.setTime(3, tuesdayOpenTime);
            s.setTime(4, tuesdayCloseTime);
            s.setTime(5, wednesdayOpenTime);
            s.setTime(6, wednesdayCloseTime);
            s.setTime(7, thursdayOpenTime);
            s.setTime(8, thursdayCloseTime);
            s.setTime(9, fridayOpenTime);
            s.setTime(10, fridayCloseTime);
            s.setTime(11, saturdayOpenTime);
            s.setTime(12, saturdayCloseTime);
            s.setTime(13, sundayOpenTime);
            s.setTime(14, sundayCloseTime);
            s.setInt(15,venueID);
            ArrayList<Section> sections = SectionDatabaseInterface.getAllSections();
            for (Section section : sections){
                if (!section.isTimeConstrained()){
                    SectionDatabaseInterface.editStartEndTimes(section.getSectionID(),mondayOpenTime,  mondayCloseTime,  tuesdayOpenTime,  tuesdayCloseTime,  wednesdayOpenTime,  wednesdayCloseTime,  thursdayOpenTime,  thursdayCloseTime,  fridayOpenTime,  fridayCloseTime,  saturdayOpenTime,  saturdayCloseTime,  sundayOpenTime,  sundayCloseTime);
                }
                else{

                }
            }
            // executes the statement and closes statement and connection
            s.executeUpdate();
            s.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void editMaxCovers(int venueID, Integer maxCovers) {
        try {
            // creates prepared statement and sets its values
            String query = "UPDATE VenueDetails SET maximumCovers=? WHERE venueID=?";
            Connection connection = ConfigBean.getConnection();
            PreparedStatement s = connection.prepareStatement(query);
            s.setInt(1, maxCovers);
            s.setInt(2, venueID);
            // executes the statement and closes statement and connection
            s.executeUpdate();
            s.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

