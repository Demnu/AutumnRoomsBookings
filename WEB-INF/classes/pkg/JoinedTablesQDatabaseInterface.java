package pkg;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class JoinedTablesQDatabaseInterface {

    public static ArrayList<ServableTable> getJoinedServableJoinedTablesGivenJoinedTablesID(int joinedTablesID) {
        ArrayList<ServableTable> servableTables = new ArrayList<>();
        String query = "SELECT* FROM JoinedTablesQ WHERE joinedTablesID=?";
        try(Connection connection = ConfigBean.getConnection();){
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, joinedTablesID);
            ResultSet result = preparedStatement.executeQuery();
            while(result.next()){
                ServableTable tempTable = ServableTableDatabaseInterface.getTableGivenID(result.getInt(2));
                servableTables.add(tempTable);
            }
            result.close();
            preparedStatement.close();
            connection.close();
        }
        catch(SQLException e){
            System.out.println("Cannot get servable tables for joinedTablesID=: " + joinedTablesID);
            System.err.println(e.getMessage());
            System.err.println(e.getStackTrace());
        }
        return servableTables;
    }
}
