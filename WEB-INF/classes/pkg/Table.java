package pkg;

import java.util.ArrayList;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
public class Table {
    ArrayList<Rows> rows = new ArrayList<>();
    public Table(ArrayList<ServableTable> allTables, ArrayList<LocalTime>timeIncrementsLocalTime) {
        ArrayList<String> timeIncrements = new ArrayList();
        for(LocalTime timeIncrementLocalTime : timeIncrementsLocalTime){
            String timeIncrement = timeIncrementLocalTime.toString();
            timeIncrements.add(timeIncrement);
        }
        for (ServableTable servableTable : allTables){

            rows.add(new Rows(servableTable,timeIncrementsLocalTime));
        }
    }

    public ArrayList<Rows> getRows() {
        return rows;
    }

    public void setRows(ArrayList<Rows> rows) {
        this.rows = rows;
    }
}

