package pkg;
import java.sql.*;
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
    public static ArrayList<ServableTable> getAllServableTablesInBooking(int tableID){
        ArrayList<ServableTable> tableList = new ArrayList<ServableTable>();
        String query = "SELECT* FROM ServableTable WHERE tableID=?";
        try(Connection connection = ConfigBean.getConnection();){
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, tableID);
            ResultSet result = preparedStatement.executeQuery();
            while(result.next()){
                ServableTable tempServableTable = new ServableTable();
                tempServableTable.setTableID(result.getInt(1));
                tempServableTable.setSectionID(result.getInt(2));
                tempServableTable.setTableNumber(result.getInt(3));
                tempServableTable.setSeats(result.getInt(4));
                //get max time for seat
                tempServableTable.setMaxTimeOfBooking(SectionDatabaseInterface.getMaxTimeOfSectionInputtedSectionID(result.getInt(2)));
                //get section name for table
                tempServableTable.setSectionName(SectionDatabaseInterface.getSectionName(result.getInt(2)));
                //get timeRequiredAfterBookingIsFinished for table
                tempServableTable.setTimeRequiredAfterBookingIsFinished(SectionDatabaseInterface.getTimeRequiredAfterBookingIsFinishedInputtedSectionID(result.getInt(2)));
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
    public static ArrayList<ServableTable> getAllServableTabless(){
        ArrayList<ServableTable> tableList = new ArrayList<ServableTable>();
        String query = "SELECT* FROM ServableTable";
        try(Connection connection = ConfigBean.getConnection();){
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet result = preparedStatement.executeQuery();
            while(result.next()){
                ServableTable tempServableTable = new ServableTable();
                tempServableTable.setTableID(result.getInt(1));
                tempServableTable.setSectionID(result.getInt(2));
                tempServableTable.setTableNumber(result.getInt(3));
                tempServableTable.setSeats(result.getInt(4));
                //get max time for seat
                tempServableTable.setMaxTimeOfBooking(SectionDatabaseInterface.getMaxTimeOfSectionInputtedSectionID(result.getInt(2)));
                //get section name for table
                tempServableTable.setSectionName(SectionDatabaseInterface.getSectionName(result.getInt(2)));
                //get timeRequiredAfterBookingIsFinished for table
                tempServableTable.setTimeRequiredAfterBookingIsFinished(SectionDatabaseInterface.getTimeRequiredAfterBookingIsFinishedInputtedSectionID(result.getInt(2)));
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
    public static ServableTable getTableGivenID(int tableID){
        ServableTable tempServableTable = new ServableTable();
        String query = "SELECT* FROM ServableTable WHERE tableID=?";
        try(Connection connection = ConfigBean.getConnection();){
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, tableID);
            ResultSet result = preparedStatement.executeQuery();
            while(result.next()){
                tempServableTable.setTableID(result.getInt(1));
                tempServableTable.setSectionID(result.getInt(2));
                tempServableTable.setTableNumber(result.getInt(3));
                tempServableTable.setSeats(result.getInt(4));
                //get max time for seat
                tempServableTable.setMaxTimeOfBooking(SectionDatabaseInterface.getMaxTimeOfSectionInputtedSectionID(result.getInt(2)));
                //get section name for table
                tempServableTable.setSectionName(SectionDatabaseInterface.getSectionName(result.getInt(2)));
                //get timeRequiredAfterBookingIsFinished for table
                tempServableTable.setTimeRequiredAfterBookingIsFinished(SectionDatabaseInterface.getTimeRequiredAfterBookingIsFinishedInputtedSectionID(result.getInt(2)));
            }
            result.close();
            preparedStatement.close();
            connection.close();
        }
        catch(SQLException e){
            System.err.println(e.getMessage());
            System.err.println(e.getStackTrace());
        }
        return tempServableTable;
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

    public static boolean updateMaxCoversSection(int sectionID, int maxCoversSection) {
        try {
            // creates prepared statement and sets its values
            String query = "UPDATE Section SET maxCapacity=? WHERE sectionID=?";
            Connection connection = ConfigBean.getConnection();
            PreparedStatement s = connection.prepareStatement(query);
            s.setInt(1, maxCoversSection);
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

