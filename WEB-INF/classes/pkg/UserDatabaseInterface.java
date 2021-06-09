package pkg;
import java.sql.*;
public class UserDatabaseInterface {
    public static boolean checkUserDetails (String username, Integer password){
        String query = "SELECT* FROM Staff WHERE username=? AND password=?";
        try(Connection connection = ConfigBean.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(query); //step 2
            preparedStatement.setString(1, username);
            preparedStatement.setInt(2, password);
            ResultSet result = preparedStatement.executeQuery(); //step 3 and 4

            if(result.next()){
                return true;
            }
            result.close();
            preparedStatement.close();
            connection.close();
        } //step 1

        catch(SQLException e){
            System.err.println(e.getMessage());
            System.err.println(e.getStackTrace());
        }

        return false;
    }
    public static String getStaffName(String username, Integer password){
        String query = "SELECT name FROM Staff WHERE username =? AND password=?";
        try(Connection connection = ConfigBean.getConnection();){

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setInt(2, password);
            ResultSet result = preparedStatement.executeQuery();
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
        return null;
    }

}

