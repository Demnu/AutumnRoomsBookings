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
                if (result.getTime(3)!=null){
                    openTimes.add(result.getTime(3).toLocalTime());
                }
                openTimes.add(null);
                if (result.getTime(4)!=null){
                    closeTimes.add(result.getTime(4).toLocalTime());
                }
                closeTimes.add(null);
                if (result.getTime(5)!=null){
                    openTimes.add(result.getTime(5).toLocalTime());
                }
                openTimes.add(null);
                if (result.getTime(6)!=null){
                    closeTimes.add(result.getTime(6).toLocalTime());
                }
                closeTimes.add(null);
                if (result.getTime(7)!=null){
                    openTimes.add(result.getTime(7).toLocalTime());
                }
                openTimes.add(null);
                if (result.getTime(8)!=null){
                    closeTimes.add(result.getTime(8).toLocalTime());
                }
                closeTimes.add(null);
                if (result.getTime(9)!=null){
                    openTimes.add(result.getTime(9).toLocalTime());
                }
                openTimes.add(null);
                if (result.getTime(10)!=null){
                    closeTimes.add(result.getTime(10).toLocalTime());
                }
                closeTimes.add(null);
                if (result.getTime(11)!=null){
                    openTimes.add(result.getTime(11).toLocalTime());
                }
                openTimes.add(null);
                if (result.getTime(12)!=null){
                    closeTimes.add(result.getTime(12).toLocalTime());
                }
                closeTimes.add(null);
                if (result.getTime(13)!=null){
                    openTimes.add(result.getTime(13).toLocalTime());
                }
                openTimes.add(null);
                if (result.getTime(14)!=null){
                    closeTimes.add(result.getTime(14).toLocalTime());
                }
                closeTimes.add(null);
                if (result.getTime(15)!=null){
                    openTimes.add(result.getTime(15).toLocalTime());
                }
                openTimes.add(null);
                if (result.getTime(16)!=null){
                    closeTimes.add(result.getTime(16).toLocalTime());
                }
                closeTimes.add(null);
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

    public static Integer getStaffID(String username, Integer password) {
        String query = "SELECT staffID FROM Staff WHERE username =? AND password=?";
        try(Connection connection = ConfigBean.getConnection();){

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setInt(2, password);
            ResultSet result = preparedStatement.executeQuery();
            while(result.next()){
                return result.getInt(1);
            }
            result.close();
            preparedStatement.close();
            connection.close();
        }
        catch(SQLException e){
            System.err.println(e.getMessage());
            System.err.println(e.getStackTrace());
        }
        return null;
    }

    public static String getStaffNameInputtedStaffID(int staffID) {
        String query = "SELECT username FROM Staff WHERE staffID =?";
        try(Connection connection = ConfigBean.getConnection();){

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, staffID);
            ResultSet result = preparedStatement.executeQuery();
            while(result.next()){
                return result.getString(1);
            }
            result.close();
            preparedStatement.close();
            connection.close();
        }
        catch(SQLException e){
            System.err.println(e.getMessage());
            System.err.println(e.getStackTrace());
        }
        return null;
    }
}

