package pkg;
import java.sql.*;
import java.sql.Date;
import java.util.*;
public class BookingDatabaseInterface {
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

    public static ArrayList<Booking> getAllBookingsInputtedDate(Date dateOfBooking){
        ArrayList<Booking> bookingList = new ArrayList<Booking>();
        String query = "SELECT* FROM Booking WHERE dateOfBooking=?";
        try(Connection connection = ConfigBean.getConnection();){
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setDate(1, dateOfBooking);
            ResultSet result = preparedStatement.executeQuery();
            while(result.next()){
                Booking tempBooking = new Booking();
                tempBooking.setBookingID(result.getInt(1));
                tempBooking.setStaffID(result.getInt(2));
                tempBooking.setDateBooked(result.getDate(3));
                tempBooking.setTimeBooked(result.getTime(4));
                tempBooking.setDateOfBooking(result.getDate(5));
                tempBooking.setStartTimeOfBooking(result.getTime(6));
                tempBooking.setEndTimeOfBooking(result.getTime(7));
                tempBooking.setNumberOfPeople(result.getInt(8));
                tempBooking.setConfirmed(result.getBoolean(9));
                bookingList.add(tempBooking);
            }
            result.close();
            preparedStatement.close();
            connection.close();
        }
        catch(SQLException e){
            System.err.println(e.getMessage());
            System.err.println(e.getStackTrace());
        }
        ArrayList tableIDsAssignedToFoundBooking;
        for (int i =0; i<bookingList.size();i++){
            tableIDsAssignedToFoundBooking = TableBookingsDatabaseInterface.getTableIDsInputtedBookingID(bookingList.get(i).getBookingID());
            for (int j = 0 ; j<tableIDsAssignedToFoundBooking.size();j++){
                bookingList.get(i).setAssignedTables(ServableTableDatabaseInterface.getAllServableTablesInBooking((Integer) tableIDsAssignedToFoundBooking.get(j)));
            }
        }
        return bookingList;
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

