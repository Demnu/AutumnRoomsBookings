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
    public static ArrayList<JoinedTables> getAllJoinedServeableTables() {
        ArrayList<JoinedTables> joinedTables = new ArrayList<>();
        String query = "SELECT* FROM JoinedTables";
        try(Connection connection = ConfigBean.getConnection();){
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet result = preparedStatement.executeQuery();
            while(result.next()){
                JoinedTables joinedTable = new JoinedTables();
                joinedTable.setJoinedTablesID(result.getInt(1));
                joinedTable.setNumberSeats(result.getInt(2));
                joinedTable.setSectionID(result.getInt(3));
                joinedTable.setJoinedTablesList(JoinedTablesQDatabaseInterface.getJoinedServableJoinedTablesGivenJoinedTablesID(result.getInt(1)));
                joinedTable.setMaxTimeOfBooking(SectionDatabaseInterface.getMaxTimeOfSectionInputtedSectionID(joinedTable.getSectionID()));
                joinedTable.setTimeRequiredAfterBookingIsFinished(SectionDatabaseInterface.getTimeRequiredAfterBookingIsFinishedInputtedSectionID(joinedTable.getSectionID()));
                joinedTables.add(joinedTable);
            }
            result.close();
            preparedStatement.close();
            connection.close();
        }
        catch(SQLException e){
            System.err.println(e.getMessage());
            System.err.println(e.getStackTrace());
        }
        return joinedTables;
    }
    public static void createJoinedTable(Integer sectionID,Integer numberSeats, ArrayList<Integer> servableTableIDs) {
        boolean completed = false;
        try {
            // creates prepared statement and sets its values
            String query = "INSERT INTO JoinedTables (numberSeats,sectionID) VALUES (?,?) ";
            Connection connection = ConfigBean.getConnection();
            PreparedStatement s = connection.prepareStatement(query);
            s.setInt(1, numberSeats);
            s.setInt(2, sectionID);
            // executes the statement and closes statement and connection
            s.executeUpdate();
            s.close();
            connection.close();
            completed = true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (completed){
            int joinedTableID = 100000000;
            String query = "SELECT* FROM JoinedTables WHERE sectionID=?";
            try(Connection connection = ConfigBean.getConnection();){
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, sectionID);
                ResultSet result = preparedStatement.executeQuery();
                while(result.next()){
                    joinedTableID = result.getInt(1);
                }
                result.close();
                preparedStatement.close();
                connection.close();
            }
            catch(SQLException e){
                System.err.println(e.getMessage());
                System.err.println(e.getStackTrace());
            }
            JoinedTablesQDatabaseInterface.addServableTablesInputtedJoinedTableID(joinedTableID,servableTableIDs);

        }
    }
    public static void updateSeatsNumber(int joinedTablesID, int numberSeats) {
        try {
            // creates prepared statement and sets its values
            String query = "UPDATE JoinedTables SET numberSeats=? WHERE joinedTablesID=?";
            Connection connection = ConfigBean.getConnection();
            PreparedStatement s = connection.prepareStatement(query);
            s.setInt(1, numberSeats);
            s.setInt(2, joinedTablesID);

            // executes the statement and closes statement and connection
            s.executeUpdate();
            s.close();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteJoinedTable(Integer joinedTablesID, ArrayList<ServableTable>joinedTables) {
        JoinedTablesQDatabaseInterface.deleteJoinedTables(joinedTablesID,joinedTables);
        String query = "DELETE FROM JoinedTables WHERE joinedTablesID=?";
        try (Connection connection = ConfigBean.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query); //step 2
            preparedStatement.setInt(1, joinedTablesID);
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
