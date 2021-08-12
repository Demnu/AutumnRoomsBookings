package pkg;
import java.sql.*;
import java.time.LocalTime;
import java.util.ArrayList;


public class VenueDetailsDatabaseInterface {

    public static VenueDetails getVenueDetails(String venueName){
        VenueDetails tempVenue = new VenueDetails();
        ArrayList<LocalTime> openTimes = new ArrayList<>();
        ArrayList<LocalTime> closeTimes = new ArrayList<>();
        String query = "SELECT* FROM VenueDetails WHERE venueName =?";
        try(Connection connection = ConfigBean.getConnection();){

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, venueName);
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
            // executes the statement and closes statement and connection
            s.executeUpdate();
            s.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

