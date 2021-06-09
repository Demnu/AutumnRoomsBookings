package pkg;
import java.sql.*;
import java.sql.Date;
import java.util.*;
public class SectionDatabaseInterface {
    public static boolean saveSection(String sectionName, String description, int maxCapacity, Time maxTimeOfBooking) {
        try {
            // creates prepared statement and sets its values
            String query = "INSERT INTO Section (sectionName,description,maxCapacity,maxTimeOfBooking) VALUES (?,?,?,?) ";
            Connection connection = ConfigBean.getConnection();
            PreparedStatement s = connection.prepareStatement(query);
            s.setString(1, sectionName);
            s.setString(2, description);
            s.setInt(3, maxCapacity);
            s.setTime(4, maxTimeOfBooking);
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

    public static ArrayList<Section> getAllSections(){
        ArrayList<Section> sectionList = new ArrayList<Section>();
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


}

