package pkg;
import java.sql.*;
import java.sql.Date;
import java.util.*;
public class TableBookingsDatabaseInterface {
    public static boolean saveServableTable(int sectionID, int tableNumber, int seats) {
        try {
            // creates prepared statement and sets its values
            String query = "INSERT INTO ServableTable (sectionID,tableNumber,seats) VALUES (?,?,?) ";
            Connection connection = ConfigBean.getConnection();
            PreparedStatement s = connection.prepareStatement(query);
            s.setInt(1, sectionID);
            s.setInt(2, tableNumber);
            s.setInt(3, seats);
            // executes the statement and closes statement and connection
            s.executeUpdate();
            s.close();
            connection.close();
            return  true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static ArrayList getTableIDsInputtedBookingID(int bookingID){
        ArrayList tableIDList = new ArrayList();
        String query = "SELECT* FROM TableBookings WHERE bookingID=?";
        try(Connection connection = ConfigBean.getConnection();){
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, bookingID);
            ResultSet result = preparedStatement.executeQuery();
            while(result.next()){
                tableIDList.add(result.getInt(2));
            }
            result.close();
            preparedStatement.close();
            connection.close();
        }
        catch(SQLException e){
            System.err.println(e.getMessage());
            System.err.println(e.getStackTrace());
        }
        return tableIDList;
    }


    public static void deleteServableTable(int tableID) {
        String query = "DELETE FROM ServableTable WHERE tableID=?";
        try (Connection connection = ConfigBean.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query); //step 2
            preparedStatement.setInt(1, tableID);
            preparedStatement.execute();
            preparedStatement.close();
            connection.close();
        } //step 1

        catch (SQLException e) {
            System.err.println(e.getMessage());
            System.err.println(e.getStackTrace());

        }
    }
    public static boolean updateSeatsNumber(int tableID, int seats) {
        try {
            // creates prepared statement and sets its values
            String query = "UPDATE ServableTable SET seats=? WHERE tableID=?";
            Connection connection = ConfigBean.getConnection();
            PreparedStatement s = connection.prepareStatement(query);
            s.setInt(1, seats);
            s.setInt(2, tableID);
            // executes the statement and closes statement and connection
            s.executeUpdate();
            s.close();
            connection.close();
            return  true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean updateMaxCapacity(int sectionID, int maxCapacity) {
        try {
            // creates prepared statement and sets its values
            String query = "UPDATE Section SET maxCapacity=? WHERE sectionID=?";
            Connection connection = ConfigBean.getConnection();
            PreparedStatement s = connection.prepareStatement(query);
            s.setInt(1, maxCapacity);
            s.setInt(2, sectionID);
            // executes the statement and closes statement and connection
            s.executeUpdate();
            s.close();
            connection.close();
            return  true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}

