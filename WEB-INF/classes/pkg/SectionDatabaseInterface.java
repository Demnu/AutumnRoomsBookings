package pkg;
import java.sql.*;
import java.sql.Date;
import java.util.*;
public class SectionDatabaseInterface {
    public static boolean saveSection(String sectionName, String description, int maxCapacity, Time maxTimeOfBooking, Time timeRequiredAfterBookingIsFinished) {
        try {
            // creates prepared statement and sets its values
            String query = "INSERT INTO Section (sectionName,description,maxCapacity,maxTimeOfBooking,timeRequiredAfterBookingIsFinished) VALUES (?,?,?,?,?) ";
            Connection connection = ConfigBean.getConnection();
            PreparedStatement s = connection.prepareStatement(query);
            s.setString(1, sectionName);
            s.setString(2, description);
            s.setInt(3, maxCapacity);
            s.setTime(4, maxTimeOfBooking);
            s.setTime(5, timeRequiredAfterBookingIsFinished);
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

    public static Time getMaxTimeOfSection(int sectionID){
        try{
            String query = "Select maxTimeOfBooking FROM Section WHERE sectionID =?";
            Connection connection = ConfigBean.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, sectionID);
            ResultSet result = preparedStatement.executeQuery();
            while(result.next()){
                Booking tempBooking = new Booking();
                return result.getTime(1);
            }
            result.close();
            preparedStatement.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String getSectionName(int sectionID){
        String query = "SELECT sectionName FROM Section WHERE sectionID=?";
        try(Connection connection = ConfigBean.getConnection();){
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, sectionID);
            ResultSet result = preparedStatement.executeQuery();
            System.out.println(sectionID);
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
        return "EMPTY";
    }
    public static ArrayList<Section> getAllSections(){
        ArrayList<Section> sectionList = new ArrayList<Section>();
        ArrayList<ServableTable> servableTablesList = new ArrayList<ServableTable>();

        String query = "SELECT* FROM Section";
        try(Connection connection = ConfigBean.getConnection();){
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet result = preparedStatement.executeQuery();
            while(result.next()){
                Section tempSection = new Section();
                tempSection.setSectionID(result.getInt(1));
                tempSection.setName(result.getString(2));
                tempSection.setDescription(result.getString(3));
                tempSection.setMaxCapacity(result.getInt(4));
                tempSection.setMaxTimeOfBooking(result.getTime(5));
                tempSection.setTimeRequiredAfterBookingIsFinishedTime(result.getTime(6));
                tempSection.setServableTables(ServableTableDatabaseInterface.getAllServeableTables(tempSection.getSectionID()));
                sectionList.add(tempSection);
            }
            result.close();
            preparedStatement.close();
            connection.close();
        }
        catch(SQLException e){
            System.err.println(e.getMessage());
            System.err.println(e.getStackTrace());
        }
        return sectionList;
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

    public static boolean updateTimeRequiredAfterBookingIsFinished(int sectionID, Time timeRequiredAfterBookingIsFinished) {
        try {
            // creates prepared statement and sets its values
            String query = "UPDATE Section SET timeRequiredAfterBookingIsFinished=? WHERE sectionID=?";
            Connection connection = ConfigBean.getConnection();
            PreparedStatement s = connection.prepareStatement(query);
            s.setTime(1, timeRequiredAfterBookingIsFinished);
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

