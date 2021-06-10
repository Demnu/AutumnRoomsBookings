package pkg;
import java.sql.*;
import java.sql.Date;
import java.util.*;
public class ServableTableDatabaseInterface {
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

    public static ArrayList<ServableTable> getAllServeableTables(int sectionID){
        ArrayList<ServableTable> tableList = new ArrayList<ServableTable>();
        String query = "SELECT* FROM ServableTable WHERE sectionID=?";
        try(Connection connection = ConfigBean.getConnection();){
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, sectionID);
            ResultSet result = preparedStatement.executeQuery();
            while(result.next()){
                ServableTable tempServableTable = new ServableTable();
                tempServableTable.setTableID(result.getInt(1));
                tempServableTable.setSectionID(result.getInt(2));
                tempServableTable.setTableNumber(result.getInt(3));
                tempServableTable.setSeats(result.getInt(4));
                tableList.add(tempServableTable);
            }
            result.close();
            preparedStatement.close();
            connection.close();
        }
        catch(SQLException e){
            System.err.println(e.getMessage());
            System.err.println(e.getStackTrace());
        }
        return tableList;
    }
    public static void deleteSection (int sectionID) {
        String query = "DELETE FROM Section WHERE sectionID=?";
        try (Connection connection = ConfigBean.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query); //step 2
            preparedStatement.setInt(1, sectionID);
            preparedStatement.execute();
            preparedStatement.close();
            connection.close();
        } //step 1

        catch (SQLException e) {
            System.err.println(e.getMessage());
            System.err.println(e.getStackTrace());

        }
    }
    public static boolean updateMaxTimeOfBooking(int sectionID, Time maxTimeOfBooking) {
        try {
            // creates prepared statement and sets its values
            String query = "UPDATE Section SET maxTimeOfBooking=? WHERE sectionID=?";
            Connection connection = ConfigBean.getConnection();
            PreparedStatement s = connection.prepareStatement(query);
            s.setTime(1, maxTimeOfBooking);
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

