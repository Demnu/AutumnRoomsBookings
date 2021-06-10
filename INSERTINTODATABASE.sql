USE AutumnRoomsBookingDB;
INSERT INTO Staff (name,username,password) VALUES ('Harry','admin',4810);


INSERT INTO Booking (staffID, dateBooked, timeBooked, dateOfBooking, startTimeOfBooking, endTimeOfBooking, numberOfPeople,confirmed)
VALUES (1,'2021-06-11','12:20','2021-06-14','10:15','10:45',4,'1');
INSERT INTO TableBookings (bookingID,tableID)
Values(1,9);
INSERT INTO TableBookings (bookingID,tableID)
Values(1,10);