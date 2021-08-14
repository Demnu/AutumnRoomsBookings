package pkg;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class JoinedTablesDatabaseInterface {
    public static ArrayList<JoinedTables> getAllJoinedServeableTablesGivenSectionID(Integer sectionID) {
        ArrayList<JoinedTables> joinedTables = new ArrayList<>();
        String query = "SELECT* FROM JoinedTables WHERE sectionID=?";
        try(Connection connection = ConfigBean.getConnection();){
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, sectionID);
            ResultSet result = preparedStatement.executeQuery();
            while(result.next()){
                JoinedTables joinedTable = new JoinedTables();
                joinedTable.setJoinedTablesID(result.getInt(1));
                joinedTable.setNumberSeats(result.getInt(2));
                joinedTable.setJoinedTablesList(JoinedTablesQDatabaseInterface.getJoinedServableJoinedTablesGivenJoinedTablesID(result.getInt(1)));
                joinedTables.add(joinedTable);
            }
            result.close();
            preparedStatement.close();
            connection.close();
        }
        catch(SQLException e){
            System.out.println("Cannot get joined tables for SectionID=: " + sectionID);
            System.err.println(e.getMessage());
            System.err.println(e.getStackTrace());
        }
        return joinedTables;
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
}
