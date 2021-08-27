package pkg;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;

public class Rows {
    private int tableNumber;
    private ArrayList<Booking> bookings;
    private ArrayList<Columns> columns;
    private int sectionID;

    public int getSectionID() {
        return sectionID;
    }

    public void setSectionID(int sectionID) {
        this.sectionID = sectionID;
    }

    public Rows(ServableTable servableTable, ArrayList<LocalTime> timeIncrementsLocalTime) {
        this.tableNumber = servableTable.getTableNumber();
        this.sectionID = servableTable.getSectionID();
        columns = new ArrayList<>();
        Columns tempColumn;
        ArrayList<LocalTime> timeIncrementsFilled = new ArrayList<>();
        for (Booking booking : servableTable.getBookingsOnDay()){
            boolean startBookingIncrement = true;
            boolean firstIncrementSkipped = false;
            for (LocalTime bookingTimeIncrement : booking.getTimeIncrementsForBooking()){
                tempColumn = new Columns();
                if (firstIncrementSkipped == false){
                    firstIncrementSkipped = true;
                }
                else{
                    tempColumn = new Columns();
                    if (startBookingIncrement == true){
                        tempColumn.setStartOfBooking(true);
                        tempColumn.setAmountOfTimeIncrements(booking.getTimeIncrementsForBooking().size());
                        tempColumn.setEndTimeOfBooking(booking.getEndTimeOfBookingLocalTime());
                        tempColumn.setStartTimeOfBooking(booking.getStartTimeOfBooking());
                        tempColumn.setEndTimeOfBookingLocalTime(booking.getEndTimeOfBookingLocalTime());
                        tempColumn.setStartTimeOfBookingLocalTime(booking.getStartTimeOfBookingLocalTime());
                        startBookingIncrement = false;
                    }
                    tempColumn.setTimeIncrementLocalTime(bookingTimeIncrement);
                    tempColumn.setTimeIncrement(bookingTimeIncrement.toString());
                    tempColumn.setBooked(true);
                    tempColumn.setBookingDetails("Harry Collins");
                    columns.add(tempColumn);
                    timeIncrementsFilled.add(bookingTimeIncrement);
                }
            }
        }
        boolean timeIncrementIsFilled;

        for (LocalTime timeIncrement : timeIncrementsLocalTime){
            tempColumn = new Columns();
            timeIncrementIsFilled = false;
            for (LocalTime timeIncrementFilled : timeIncrementsFilled){
                if (timeIncrement.equals(timeIncrementFilled)){
                    timeIncrementIsFilled = true;
                }
            }
            if (timeIncrementIsFilled == false){
                tempColumn.setTimeIncrementLocalTime(timeIncrement);
                tempColumn.setTimeIncrement(timeIncrement.toString());
                tempColumn.setBooked(false);
                columns.add(tempColumn);
            }
        }
        Collections.sort(columns);
        if (columns.size()>1){
            boolean bool = false;
            for (int i = 0; i < columns.size();i++){
                if (columns.get(i).getTimeIncrementLocalTime().getMinute()==0){
                    if (bool == false){
                        bool = true;
                    }
                    else{
                        bool = false;
                    }
                    columns.get(i).setEven(bool);

                }
                else{
                    columns.get(i).setEven(bool);
                }
            }
        }

    }

    public int getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }


    public ArrayList<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(ArrayList<Booking> bookings) {
        this.bookings = bookings;
    }

    public ArrayList<Columns> getColumns() {
        return columns;
    }

    public void setColumns(ArrayList<Columns> columns) {
        this.columns = columns;
    }
}
