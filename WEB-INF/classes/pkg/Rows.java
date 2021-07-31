package pkg;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;

public class Rows {
    private int tableNumber;
    private ArrayList<Booking> bookings;
    private ArrayList<Columns> columns;

    public Rows(ServableTable servableTable,ArrayList<LocalTime> timeIncrementsLocalTime) {
        this.tableNumber = servableTable.getTableNumber();
        columns = new ArrayList<>();
        Columns tempColumn;
        ArrayList<LocalTime> timeIncrementsFilled = new ArrayList<>();
        for (Booking booking : servableTable.getBookingsOnDay()){
            boolean startBookingIncrement = true;
            for (LocalTime bookingTimeIncrement : booking.getTimeIncrementsForBooking()){
                tempColumn = new Columns();
                if (startBookingIncrement == true){
                    tempColumn.setStartOfBooking(true);
                    tempColumn.setAmountOfTimeIncrements(booking.getTimeIncrementsForBooking().size());
                    tempColumn.setEndTimeOfBooking(booking.getEndTimeOfBooking());
                    tempColumn.setStartTimeOfBooking(booking.getStartTimeOfBooking());
                    startBookingIncrement = false;
                }
                tempColumn.setTimeIncrement(bookingTimeIncrement.toString());
                tempColumn.setBooked(true);
                tempColumn.setBookingDetails("Harry Collins");
                tempColumn.setTimeIncrementLocalTime(bookingTimeIncrement);
                columns.add(tempColumn);
                timeIncrementsFilled.add(bookingTimeIncrement);
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
