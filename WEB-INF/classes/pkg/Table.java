package pkg;

import java.util.ArrayList;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
public class Table {
    ArrayList<Rows> rows = new ArrayList<>();
    ArrayList<LocalTime> timeIncrementsForDay = new ArrayList<>();
    public Table(ArrayList<ServableTable> allTables, ArrayList<LocalTime>timeIncrementsLocalTime) {
        ArrayList<String> timeIncrements = new ArrayList();
        this.timeIncrementsForDay = timeIncrementsLocalTime;
        for(LocalTime timeIncrementLocalTime : timeIncrementsLocalTime){
            String timeIncrement = timeIncrementLocalTime.toString();
            timeIncrements.add(timeIncrement);
        }
        for (ServableTable servableTable : allTables){
            rows.add(new Rows(servableTable,timeIncrementsLocalTime));
        }
    }

    public ArrayList<BookingFormattedTimeIncrements> getTotalCoversForEachColumn(){
        ArrayList<BookingFormattedTimeIncrements> list = new ArrayList<>();
        for (LocalTime timeIncrement : timeIncrementsForDay){
            int totalCovers = 0;
            BookingFormattedTimeIncrements bookingFormattedTimeIncrements = new BookingFormattedTimeIncrements();
            bookingFormattedTimeIncrements.setTimeIncrement(timeIncrement);
            for (Rows row : rows){
                for (Columns column : row.getColumns()){
                    if (column.getTimeIncrementLocalTime().compareTo(timeIncrement)==0){
                        totalCovers+=column.getNumPeople();
                    }
                }
            }
            bookingFormattedTimeIncrements.setCovers(totalCovers);
            list.add(bookingFormattedTimeIncrements);
        }
        return list;
    }

    public ArrayList<Rows> getRows() {
        return rows;
    }

    public void setRows(ArrayList<Rows> rows) {
        this.rows = rows;
    }

    @Override
    public String toString() {
        String str = "";
        for (Rows row : rows){
            str +=  row.getTableNumber() + "\n" + row+"\n";
        }
        return str;
    }
}

