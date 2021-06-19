package pkg;
import java.sql.*;
import java.sql.Date;
import java.util.*;
public class BookingDatabaseInterface {
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



    public static boolean saveBooking(Integer staffID, Date dateBooked, Time timeBooked, Date dateOfBooking, Time startTimeOfBooking, Time endTimeOfBooking, int numberOfPeople, boolean confirmed, ArrayList tableIDsBooking) {
        try {
            // creates prepared statement and sets its values
            String query = "INSERT INTO Booking (staffID,dateBooked,timeBooked,dateOfBooking,startTimeOfBooking,endTimeOfBooking,numberOfPeople,confirmed) VALUES (?,?,?,?,?,?,?,?) ";
            Connection connection = ConfigBean.getConnection();
            PreparedStatement s = connection.prepareStatement(query);
            s.setInt(1, staffID);
            s.setDate(2, dateBooked);
            s.setTime(3, timeBooked);
            s.setDate(4,dateOfBooking);
            s.setTime(5,startTimeOfBooking);
            s.setTime(6, endTimeOfBooking);
            s.setInt(7,numberOfPeople);
            s.setBoolean(8,confirmed);
            // executes the statement and closes statement and connection
            s.executeUpdate();
            s.close();
            connection.close();
            //Create entry in TableBooking
            //Get generated bookingID
            int bookingID = getBookingIDInputtedTimeBookedStaffID(staffID,timeBooked);
            if (bookingID !=-1){
                for (int i = 0; i<tableIDsBooking.size();i++){
                    TableBookingsDatabaseInterface.saveTableBooking(bookingID,tableIDsBooking.get(i));
                }
            }
            else{
                System.out.println("ERROR, Could not find bookingID");
                return false;
            }
            return  true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static int getBookingIDInputtedTimeBookedStaffID(Integer staffID, Time timeBooked){
        ArrayList<Booking> bookingList = new ArrayList<Booking>();
        String query = "SELECT* FROM Booking WHERE staffID=? AND timeBooked =?";
        try(Connection connection = ConfigBean.getConnection();){
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, staffID);
            preparedStatement.setTime(2, timeBooked);
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
        return -1;

    }


}

