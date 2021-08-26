package pkg;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;

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
                tempChangedDateTime.setChangedDateID(result.getInt(1));
                tempChangedDateTime.setChangedDate(result.getDate(2).toLocalDate());
                tempChangedDateTime.setChangedOpenTime(result.getTime(3).toLocalTime());
                tempChangedDateTime.setChangedCloseTime(result.getTime(4).toLocalTime());
                tempChangedDateTime.setDescription(result.getString(6));
                tempList.add(tempChangedDateTime);
            }
            result.close();
            preparedStatement.close();
            Collections.sort(tempList);
            return tempList;
        }
        catch(SQLException e){
            System.err.println(e.getMessage());
            System.err.println(e.getStackTrace());
        }
        return null;
    }

    public static void saveChangedDate(LocalDate changedDate, LocalTime openTime, LocalTime endTime, String description, int venueID) {
        try {
            // creates prepared statement and sets its values
            String query = "INSERT INTO changedDate (changedDate,changedOpenTime,changedCloseTime,venueID,description) VALUES (?,?,?,?,?) ";
            Connection connection = ConfigBean.getConnection();
            PreparedStatement s = connection.prepareStatement(query);
            s.setDate(1, Date.valueOf(changedDate));
            s.setTime(2, Time.valueOf(openTime));
            s.setTime(3,Time.valueOf(endTime));
            s.setInt(4,venueID);
            s.setString(5,description);
            // executes the statement and closes statement and connection
            s.executeUpdate();

            s.close();
            connection.close();
            //Create entry in TableBooking
            //Get generated bookingID
        } catch (Exception e) {
            e.printStackTrace();

        }

    }
    public static ChangedDateTimes getChangedDateGivenDate(LocalDate date){
        ChangedDateTimes tempChangedDateTime;
        String query = "SELECT* FROM changedDate WHERE venueID =? AND changedDate=?";
        try(Connection connection = ConfigBean.getConnection();){

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, 1);
            preparedStatement.setDate(2, Date.valueOf(date));
            ResultSet result = preparedStatement.executeQuery();
            while(result.next()){
                tempChangedDateTime = new ChangedDateTimes();
                tempChangedDateTime.setChangedDateID(result.getInt(1));
                tempChangedDateTime.setChangedDate(result.getDate(2).toLocalDate());
                tempChangedDateTime.setChangedOpenTime(result.getTime(3).toLocalTime());
                tempChangedDateTime.setChangedCloseTime(result.getTime(4).toLocalTime());
                tempChangedDateTime.setDescription(result.getString(6));
                return tempChangedDateTime;
            }
            result.close();
            preparedStatement.close();
        }
        catch(SQLException e){
            System.err.println(e.getMessage());
            System.err.println(e.getStackTrace());
        }
        return null;
    }


    public static void deleteChangedDate(int changedDateID) {
        String query = "DELETE FROM changedDate WHERE changedDateID=?";
        try (Connection connection = ConfigBean.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query); //step 2
            preparedStatement.setInt(1, changedDateID);
            preparedStatement.execute();
            preparedStatement.close();
            connection.close();
        } //step 1

        catch (SQLException e) {
            System.err.println(e.getMessage());
            System.err.println(e.getStackTrace());

        }
    }
}

