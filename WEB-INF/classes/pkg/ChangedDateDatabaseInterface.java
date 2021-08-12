package pkg;
import java.sql.*;
import java.util.ArrayList;

public class ChangedDateDatabaseInterface {
    public static ArrayList<ChangedDateTimes> getChangedDateTimes(int venueID){
        ArrayList<ChangedDateTimes> tempList = new ArrayList<>();
        ChangedDateTimes tempChangedDateTime;
        String query = "SELECT* FROM changedDate WHERE venueID =?";
        try(Connection connection = ConfigBean.getConnection();){

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, venueID);
            ResultSet result = preparedStatement.executeQuery();
            while(result.next()){
                tempChangedDateTime = new ChangedDateTimes();
                tempChangedDateTime.setChangedDate(result.getDate(2).toLocalDate());
                tempChangedDateTime.setChangedOpenTime(result.getTime(3).toLocalTime());
                tempChangedDateTime.setChangedCloseTime(result.getTime(4).toLocalTime());

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

