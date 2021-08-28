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
        ArrayList<LocalTime> timeIncrements = new ArrayList<>();
        for (LocalTime localTime : timeIncrementsLocalTime){
            timeIncrements.add(localTime);
        }
        ArrayList<LocalTime> timeIncrementsFilled = new ArrayList<>();

        for (Booking booking : servableTable.getBookingsOnDay()){
            boolean startBookingIncrement = true;
            for (BookingTime bookingTimeIncrement : booking.getBookingTimes()){
                if (bookingTimeIncrement.isStartTime()){
                    tempColumn = new Columns();
                    tempColumn.setBooking(booking);
                    tempColumn.setStartTimeOfBookingLocalTime(bookingTimeIncrement.getTimeIncrement());
                    tempColumn.setAmountOfIncrements(booking.getTimeIncrementsForBooking().size());
                    tempColumn.setTimeIncrementLocalTime(bookingTimeIncrement.getTimeIncrement());
                    tempColumn.setBooked(true);
                    tempColumn.setStartOfBooking(true);
                    columns.add(tempColumn);
                    timeIncrementsFilled.add(bookingTimeIncrement.getTimeIncrement());

                }
                else if(bookingTimeIncrement.isEndTime()){

                }
                else{
                    tempColumn = new Columns();
                    tempColumn.setBooked(true);
                    tempColumn.setTimeIncrementLocalTime(bookingTimeIncrement.getTimeIncrement());
                    columns.add(tempColumn);
                    timeIncrementsFilled.add(bookingTimeIncrement.getTimeIncrement());


                }
            }
        }
        timeIncrements.removeAll(timeIncrementsFilled);
        for (LocalTime localTime : timeIncrements){
            tempColumn = new Columns();
            tempColumn.setTimeIncrementLocalTime(localTime);
            tempColumn.setBooked(false);
            columns.add(tempColumn);
        }
        Collections.sort(columns);
//        if (!columns.isEmpty()){
//            int firstColIncrements = columns.get(0).getTimeIncrementLocalTime().getMinute()/15;
//
//        }

        boolean bool = true;
        for (Columns column : columns){


            if (column.getTimeIncrementLocalTime().getMinute()==0){
                if (bool){
                    bool = false;
                }
                else{
                    bool = true;
                }
            }
            column.setEven(bool);

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

    @Override
    public String toString() {
        String str = "";
        for (Columns columns : columns){
            str += columns;
        }
        return str;
    }
}
