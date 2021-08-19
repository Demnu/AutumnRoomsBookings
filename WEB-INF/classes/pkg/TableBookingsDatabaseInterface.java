package pkg;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;
public class TableBookingsDatabaseInterface {
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


    public static boolean saveTableBooking(int bookingID, Object tableIDObject) {
        int tableID = (int) tableIDObject;
        try {
            // creates prepared statement and sets its values
            String query = "INSERT INTO TableBookings (bookingID, tableID) VALUES (?,?) ";
            Connection connection = ConfigBean.getConnection();
            PreparedStatement s = connection.prepareStatement(query);
            s.setInt(1, bookingID);
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
//    public static ServableTable tableBookingInformationBookedOnGivenDateAvailableTable(LocalDate dateOfBooking, ServableTable availableTable){
//        ArrayList<Integer> bookingIDs = new ArrayList<>();
//        int counter = 0;
//        boolean found = false;
//        String query = "SELECT* FROM TableBooking WHERE tableID=?";
//        try(Connection connection = ConfigBean.getConnection();){
//            PreparedStatement preparedStatement = connection.prepareStatement(query);
//            preparedStatement.setInt(1, availableTable.getTableID());
//            ResultSet result = preparedStatement.executeQuery();
//            while(result.next()){
//                Booking tempBooking = BookingDatabaseInterface.getBookingInputtedDateBookingID(dateOfBooking,result.getInt(3));
//                bookingIDs.add(tempBooking.getBookingID());
//                if (tempBooking!=null){
//                    bookingIDs.add(tempBooking.getBookingID());
////                    availableTable.addBookingOnDay(tempBooking);
//                }
//
//
//            }
//            availableTable.setTimeIncrementsBookedOutForDay();
//            result.close();
//            preparedStatement.close();
//            connection.close();
//        }
//        catch(SQLException e){
//            System.err.println(e.getMessage());
//            System.err.println(e.getStackTrace());
//        }
//        return availableTable;
//    }
}

