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
            System.out.println(e.getMessage());
            System.err.println(e.getStackTrace());
        }
        return servableTables;
    }

    public static void addServableTablesInputtedJoinedTableID(int joinedTableID, ArrayList<Integer> servableTableIDs) {
        for (Integer servableTableID : servableTableIDs){
            try {
                // creates prepared statement and sets its values
                String query = "INSERT INTO JoinedTablesQ (joinedTablesID,tableID) VALUES (?,?) ";
                Connection connection = ConfigBean.getConnection();
                PreparedStatement s = connection.prepareStatement(query);
                s.setInt(1, joinedTableID);
                s.setInt(2, servableTableID);
                // executes the statement and closes statement and connection
                s.executeUpdate();
                s.close();
                connection.close();
            } catch (Exception e) {
                System.out.println("Failed adding table to JoinedTablesQ");
                System.out.println(e.getMessage());
                System.out.println(e.getStackTrace());
            }
        }
    }
    public static void deleteJoinedTables(Integer joinedTableID, ArrayList<ServableTable> joinedTables) {

        for (ServableTable servableTable : joinedTables){
            String query = "DELETE FROM JoinedTablesQ WHERE joinedTablesID=? AND tableID=?";
            try (Connection connection = ConfigBean.getConnection()) {
                PreparedStatement preparedStatement = connection.prepareStatement(query); //step 2
                preparedStatement.setInt(1, joinedTableID);
                preparedStatement.setInt(2, servableTable.getTableID());
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
}
