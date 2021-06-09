package pkg;
import java.sql.*;
import java.util.*;
public class SectionDatabaseInterface {
    public static boolean saveSection(String sectionName, String description) {
        try {
            // creates prepared statement and sets its values
            String query = "INSERT INTO Section (sectionName,description) VALUES (?,?) ";
            Connection connection = ConfigBean.getConnection();
            PreparedStatement s = connection.prepareStatement(query);
            s.setString(1, sectionName);
            s.setString(2, description);
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
        String query = "SELECT* Section";
        try(Connection connection = ConfigBean.getConnection();){
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet result = preparedStatement.executeQuery();
            while(result.next()){
                Section tempSection = new Section(result.getString(1),result.getString(2));
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

}

