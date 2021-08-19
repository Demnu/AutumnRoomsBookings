SET SQL_SAFE_UPDATES = 0;
USE AutumnRoomsBookingDB;
INSERT INTO Staff (name,username,password) VALUES ('Harry','admin',4810);


INSERT INTO Booking (staffID, dateBooked, timeBooked, dateOfBooking, startTimeOfBooking, endTimeOfBooking, numberOfPeople,confirmed)
VALUES (1,'2021-08-17','12:20','2021-08-17','10:15','10:45',4,'1');
INSERT INTO TableBookings (bookingID,tableID)
Values(1,9);
INSERT INTO TableBookings (bookingID,tableID)
Values(1,10);

UPDATE VenueDetails
SET openTimeMonday = "08:00",
    closeTimeMonday = "15:00",
    openTimeTuesday = "08:00",
    closeTimeTuesday= "15:00",
    openTimeWednesday= "08:00",
    closeTimeWednesday= "15:00",
    openTimeThursday= "08:00",
    closeTimeThursday= "15:00",
    openTimeFriday= "08:00",
    closeTimeFriday= "15:00",
    openTimeSaturday= "08:00",
    closeTimeSaturday= "15:00",
    openTimeSunday= "08:00",
    closeTimeSunday= "15:00",
    maximumCovers = 50
WHERE venueID = 1

