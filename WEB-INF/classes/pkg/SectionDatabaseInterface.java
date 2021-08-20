package pkg;
import java.sql.*;
import java.sql.Date;
import java.time.LocalTime;
import java.util.*;
public class SectionDatabaseInterface {
    public static boolean saveSection(String sectionName, String description, int maxCoversSection, LocalTime maxTimeOfBooking, LocalTime timeRequiredAfterBookingIsFinished, boolean timeConstrained, LocalTime timeAllowedToStayAfterSectionIsClosed) {
        try {
            // creates prepared statement and sets its values
            String query = "INSERT INTO Section (sectionName,description,maxCapacity,maxTimeOfBooking,timeRequiredAfterBookingIsFinished,startTimeMonday,endTimeMonday,startTimeTuesday,endTimeTuesday,startTimeWednesday,endTimeWednesday,startTimeThursday,endTimeThursday,startTimeFriday,endTimeFriday,startTimeSaturday,endTimeSaturday,startTimeSunday,endTimeSunday,timeConstrained,timeAllowedToStayAfterSectionClosed) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
            Connection connection = ConfigBean.getConnection();
            PreparedStatement s = connection.prepareStatement(query);
            s.setString(1, sectionName);
            s.setString(2, description);
            s.setInt(3, maxCoversSection);
            s.setTime(4, Time.valueOf(maxTimeOfBooking));
            s.setTime(5, Time.valueOf(timeRequiredAfterBookingIsFinished));
            Venue venue = VenueDetailsDatabaseInterface.getOpenCloseTimes(1);
            int parameterIndex = 6;
            ArrayList<LocalTime> openTimes = venue.getOpenTimes();
            ArrayList<LocalTime> closeTimes = venue.getCloseTimes();

            for (LocalTime openTime : openTimes){
                s.setTime(parameterIndex, Time.valueOf(openTime));
                parameterIndex++;

            }
            for (LocalTime closeTime : closeTimes){
                s.setTime(parameterIndex, Time.valueOf(closeTime));
                parameterIndex++;

            }
            s.setBoolean(20,timeConstrained);
            s.setTime(21,Time.valueOf(timeAllowedToStayAfterSectionIsClosed) );
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
    public static Time getTimeRequiredAfterBookingIsFinishedInputtedSectionID(int sectionID) {
        try(Connection connection = ConfigBean.getConnection();){
            String query = "Select timeRequiredAfterBookingIsFinished FROM Section WHERE sectionID =?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, sectionID);
            ResultSet result = preparedStatement.executeQuery();
            while(result.next()){
                return result.getTime(1);
            }
            result.close();
            preparedStatement.close();

        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    public static Time getMaxTimeOfSectionInputtedSectionID(int sectionID){
        try(Connection connection = ConfigBean.getConnection();){
            String query = "Select maxTimeOfBooking FROM Section WHERE sectionID =?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, sectionID);
            ResultSet result = preparedStatement.executeQuery();
            while(result.next()){
                return result.getTime(1);
            }
            result.close();
            preparedStatement.close();

        } catch (Exception e) {
            System.err.println(e.getMessage());
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
                Section section = new Section();
                ArrayList<LocalTime> startTimes = new ArrayList<>();
                ArrayList<LocalTime> endTimes = new ArrayList<>();
                section.setSectionID(result.getInt(1));
                section.setName(result.getString(2));
                section.setDescription(result.getString(3));
                section.setMaxCoversSection(result.getInt(4));
                section.setMaxTimeOfBooking(result.getTime(5));
                section.setTimeRequiredAfterBookingIsFinishedTime(result.getTime(6));
                Boolean timeConstrained = result.getBoolean(21);
                section.setTimeConstrained(timeConstrained);
                section.setServableTables(ServableTableDatabaseInterface.getAllServeableTables(result.getInt(1)));
                section.setJoinedTables(JoinedTablesDatabaseInterface.getAllJoinedServeableTablesGivenSectionID(result.getInt(1)));
                section.setTimeAllowedToStayAfterSectionClosed(result.getTime(22).toLocalTime());
                startTimes.add(result.getTime(7).toLocalTime());
                endTimes.add(result.getTime(8).toLocalTime());
                startTimes.add(result.getTime(9).toLocalTime());
                endTimes.add(result.getTime(10).toLocalTime());
                startTimes.add(result.getTime(11).toLocalTime());
                endTimes.add(result.getTime(12).toLocalTime());
                startTimes.add(result.getTime(13).toLocalTime());
                endTimes.add(result.getTime(14).toLocalTime());
                startTimes.add(result.getTime(15).toLocalTime());
                endTimes.add(result.getTime(16).toLocalTime());
                startTimes.add(result.getTime(17).toLocalTime());
                endTimes.add(result.getTime(18).toLocalTime());
                startTimes.add(result.getTime(19).toLocalTime());
                endTimes.add(result.getTime(20).toLocalTime());
                section.setStartTimes(startTimes);
                section.setEndTimes(endTimes);

                sectionList.add(section);
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

    public static Section getSectionGivenSectionID(int sectionID){
        Section section= new Section();
        ArrayList<LocalTime> startTimes = new ArrayList<>();
        ArrayList<LocalTime> endTimes = new ArrayList<>();
        String query = "SELECT* FROM Section WHERE sectionID=?";
        try(Connection connection = ConfigBean.getConnection();){
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, sectionID);
            ResultSet result = preparedStatement.executeQuery();
            while(result.next()){
                section.setSectionID(result.getInt(1));
                section.setName(result.getString(2));
                section.setDescription(result.getString(3));
                section.setMaxCoversSection(result.getInt(4));
                section.setMaxTimeOfBooking(result.getTime(5));
                section.setTimeRequiredAfterBookingIsFinishedTime(result.getTime(6));
                Boolean timeConstrained = result.getBoolean(21);
                section.setTimeAllowedToStayAfterSectionClosed(result.getTime(22).toLocalTime());

                section.setTimeConstrained(timeConstrained);
                section.setServableTables(ServableTableDatabaseInterface.getAllServeableTables(section.getSectionID()));
                section.setJoinedTables(JoinedTablesDatabaseInterface.getAllJoinedServeableTablesGivenSectionID(sectionID));
                startTimes.add(result.getTime(7).toLocalTime());
                endTimes.add(result.getTime(8).toLocalTime());
                startTimes.add(result.getTime(9).toLocalTime());
                endTimes.add(result.getTime(10).toLocalTime());
                startTimes.add(result.getTime(11).toLocalTime());
                endTimes.add(result.getTime(12).toLocalTime());
                startTimes.add(result.getTime(13).toLocalTime());
                endTimes.add(result.getTime(14).toLocalTime());
                startTimes.add(result.getTime(15).toLocalTime());
                endTimes.add(result.getTime(16).toLocalTime());
                startTimes.add(result.getTime(17).toLocalTime());
                endTimes.add(result.getTime(18).toLocalTime());
                startTimes.add(result.getTime(19).toLocalTime());
                endTimes.add(result.getTime(20).toLocalTime());
                section.setStartTimes(startTimes);
                section.setEndTimes(endTimes);

            }
            result.close();
            preparedStatement.close();
            connection.close();
        }
        catch(SQLException e){
            System.err.println(e.getMessage());
            System.err.println(e.getStackTrace());
        }
        return section;
    }
    public static void deleteSection (int sectionID) {
        String query = "DELETE FROM Section WHERE sectionID=?";
        //TODO Development: Delete tables assigned to deleted section
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

    public static boolean updateMaxCoversSection(int sectionID, int maxCoversSection) {
        try {
            // creates prepared statement and sets its values
            String query = "UPDATE Section SET maxCapacity=? WHERE sectionID=?";
            Connection connection = ConfigBean.getConnection();
            PreparedStatement s = connection.prepareStatement(query);
            s.setInt(1, maxCoversSection);
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
    public static boolean updateTimeConstrained(int sectionID, boolean timeConstrained) {
        if (timeConstrained==false){
            Venue venue = VenueDetailsDatabaseInterface.getOpenCloseTimes(1);
            editStartEndTimes(sectionID, venue.getOpenTimes(),venue.getCloseTimes());
        }
        try {
            // creates prepared statement and sets its values
            String query = "UPDATE Section SET timeConstrained=? WHERE sectionID=?";
            Connection connection = ConfigBean.getConnection();
            PreparedStatement s = connection.prepareStatement(query);
            s.setBoolean(1, timeConstrained);
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

    public static void editStartEndTimes(int sectionID, Time mondayOpenTime, Time mondayCloseTime, Time tuesdayOpenTime, Time tuesdayCloseTime, Time wednesdayOpenTime, Time wednesdayCloseTime, Time thursdayOpenTime, Time thursdayCloseTime, Time fridayOpenTime, Time fridayCloseTime, Time saturdayOpenTime, Time saturdayCloseTime, Time sundayOpenTime, Time sundayCloseTime) {
        try {
            // creates prepared statement and sets its values
            String query = "UPDATE Section SET startTimeMonday=?, endTimeMonday=?, startTimeTuesday=?, endTimeTuesday=?, startTimeWednesday=?, endTimeWednesday=?, startTimeThursday=?, endTimeThursday=?, startTimeFriday=?, endTimeFriday=?, startTimeSaturday=?, endTimeSaturday=?, startTimeSunday=?, endTimeSunday=? WHERE sectionID=?";
            Connection connection = ConfigBean.getConnection();
            PreparedStatement s = connection.prepareStatement(query);
            s.setTime(1, mondayOpenTime);
            s.setTime(2, mondayCloseTime);
            s.setTime(3, tuesdayOpenTime);
            s.setTime(4, tuesdayCloseTime);
            s.setTime(5, wednesdayOpenTime);
            s.setTime(6, wednesdayCloseTime);
            s.setTime(7, thursdayOpenTime);
            s.setTime(8, thursdayCloseTime);
            s.setTime(9, fridayOpenTime);
            s.setTime(10, fridayCloseTime);
            s.setTime(11, saturdayOpenTime);
            s.setTime(12, saturdayCloseTime);
            s.setTime(13, sundayOpenTime);
            s.setTime(14, sundayCloseTime);
            s.setInt(15,sectionID);
            ArrayList<Section> sections = SectionDatabaseInterface.getAllSections();
            for (Section section : sections){
                if (section.isTimeConstrained()){

                }
            }
            // executes the statement and closes statement and connection
            s.executeUpdate();
            s.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public static void editStartEndTimes(int sectionID, ArrayList<LocalTime> startTimes, ArrayList<LocalTime> endTimes) {
        try {
            // creates prepared statement and sets its values
            String query = "UPDATE Section SET startTimeMonday=?, endTimeMonday=?, startTimeTuesday=?, endTimeTuesday=?, startTimeWednesday=?, endTimeWednesday=?, startTimeThursday=?, endTimeThursday=?, startTimeFriday=?, endTimeFriday=?, startTimeSaturday=?, endTimeSaturday=?, startTimeSunday=?, endTimeSunday=? WHERE sectionID=?";
            Connection connection = ConfigBean.getConnection();
            PreparedStatement s = connection.prepareStatement(query);
            int j = 0;

            for (int i = 1; i <14; i++){
                s.setTime(i,Time.valueOf(startTimes.get(j)));
                i++;
                s.setTime(i,Time.valueOf(endTimes.get(j)));
                j++;

            }
            s.setInt(15,sectionID);
            ArrayList<Section> sections = SectionDatabaseInterface.getAllSections();
            for (Section section : sections){
                if (section.isTimeConstrained()){

                }
            }
            // executes the statement and closes statement and connection
            s.executeUpdate();
            s.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public static void editStartEndTimesUsedByEditOpenCloseTimes(int sectionID, ArrayList<Time> startTimes, ArrayList<Time> endTimes) {
        try {
            // creates prepared statement and sets its values
            String query = "UPDATE Section SET startTimeMonday=?, endTimeMonday=?, startTimeTuesday=?, endTimeTuesday=?, startTimeWednesday=?, endTimeWednesday=?, startTimeThursday=?, endTimeThursday=?, startTimeFriday=?, endTimeFriday=?, startTimeSaturday=?, endTimeSaturday=?, startTimeSunday=?, endTimeSunday=? WHERE sectionID=?";
            Connection connection = ConfigBean.getConnection();
            PreparedStatement s = connection.prepareStatement(query);
            int j = 0;

            for (int i = 1; i <14; i++){
                s.setTime(i,(startTimes.get(j)));
                i++;
                s.setTime(i,(endTimes.get(j)));
                j++;

            }
            s.setInt(15,sectionID);
            ArrayList<Section> sections = SectionDatabaseInterface.getAllSections();
            for (Section section : sections){
                if (section.isTimeConstrained()){

                }
            }
            // executes the statement and closes statement and connection
            s.executeUpdate();
            s.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static boolean updateTimeAllowedToStayAfterSectionIsClosed(Integer sectionID, LocalTime timeAllowedToStayAfterSectionIsClosed) {
        try {
            // creates prepared statement and sets its values
            String query = "UPDATE Section SET timeAllowedToStayAfterSectionClosed=? WHERE sectionID=?";
            Connection connection = ConfigBean.getConnection();
            PreparedStatement s = connection.prepareStatement(query);
            s.setTime(1, Time.valueOf(timeAllowedToStayAfterSectionIsClosed) );
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


    public static LocalTime getTimeAllowedToStayAfterSectionClosed(int sectionID) {
        try(Connection connection = ConfigBean.getConnection();){
            String query = "Select timeAllowedToStayAfterSectionClosed FROM Section WHERE sectionID =?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, sectionID);
            ResultSet result = preparedStatement.executeQuery();
            while(result.next()){
                return result.getTime(1).toLocalTime();
            }
            result.close();
            preparedStatement.close();

        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}

