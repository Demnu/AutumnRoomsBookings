DROP DATABASE AutumnRoomsBookingDB;
CREATE DATABASE AutumnRoomsBookingDB;
USE AutumnRoomsBookingDB;
CREATE TABLE Staff(
                      staffID INT NOT NULL AUTO_INCREMENT,
                      name VARCHAR(80) NOT NULL,
                      username VARCHAR(80) NOT NULL,
                      password VARCHAR(80) NOT NULL,
                      PRIMARY KEY (staffID)
);
CREATE TABLE Section (
                         sectionID INT NOT NULL AUTO_INCREMENT,
                         sectionName VARCHAR(80) NOT NULL,
                         description VARCHAR(120) NOT NULL,
                         maxCapacity int,
                         maxTimeOfBooking TIME,
                         timeRequiredAfterBookingIsFinished TIME,
                         PRIMARY KEY (sectionID)
);
CREATE TABLE ServableTable (
                               tableID INT NOT NULL AUTO_INCREMENT,
                               sectionID int NOT NULL,
                               tableNumber VARCHAR(64) NOT NULL,
                               seats int NOT NULL,
                               PRIMARY KEY (tableID),
                               FOREIGN KEY (sectionID) REFERENCES Section(sectionID)
);
CREATE TABLE Booking(
                        bookingID INT NOT NULL AUTO_INCREMENT,
                        staffID INT NOT NULL,
                        dateBooked DATE NOT NULL,
                        timeBooked TIME NOT NULL,
                        dateOfBooking DATE NOT NULL,
                        startTimeOfBooking TIME NOT NULL,
                        endTimeOfBooking TIME NOT NULL,
                        numberOfPeople int NOT NULL,
                        confirmed boolean,
                        PRIMARY KEY (bookingID),
                        FOREIGN KEY (staffID) REFERENCES Staff(staffID)
);
CREATE TABLE TableBookings(
                              tableBookingID INT NOT NULL AUTO_INCREMENT,
                              tableID INT NOT NULL,
                              bookingID INT NOT NULL,
                              PRIMARY KEY (tableBookingID),
                              FOREIGN KEY (tableID) REFERENCES ServableTable(tableID),
                              FOREIGN KEY (bookingID) REFERENCES Booking(bookingID)
);
CREATE TABLE VenueDetails(
                             venueID INT NOT NULL auto_increment,
                             venueName VARCHAR(80) NOT NULL,
                             openTimeMonday TIME,
                             closeTimeMonday TIME,
                             openTimeTuesday TIME,
                             closeTimeTuesday TIME,
                             openTimeWednesday TIME,
                             closeTimeWednesday TIME,
                             openTimeThursday TIME,
                             closeTimeThursday TIME,
                             openTimeFriday TIME,
                             closeTimeFriday TIME,
                             openTimeSaturday TIME,
                             closeTimeSaturday TIME,
                             openTimeSunday TIME,
                             closeTimeSunday TIME,
                             maximumCovers INT,
                             primary key (venueID)

)

CREATE TABLE changedDate(
                            changedDateID INT NOT NULL auto_increment,
                            changedDate DATE NOT NULL,
                            changedOpenTime TIME NOT NULL,
                            changedCloseTime TIME NOT NULL,
                            venueID int,
                            description VARCHAR(64) NOT NULL,
                            PRIMARY KEY (changedDateID),
                            FOREIGN KEY (venueID) REFERENCES VenueDetails(venueID)
);

CREATE TABLE JoinedTables(
                             joinedTablesID INT NOT NULL auto_increment,
                             numberSeats INT NOT NULL,
                             sectionID INT NOT NULL,
                             FOREIGN KEY (sectionID) REFERENCES Section(sectionID),
                             PRIMARY KEY (joinedTablesID)
);

CREATE TABLE JoinedTablesQ(
                              joinedTablesID INT NOT NULL,
                              tableID INT NOT NULL,
                              FOREIGN KEY (joinedTablesID) REFERENCES JoinedTables(joinedTablesID),
                              FOREIGN KEY (tableID) REFERENCES ServableTable(tableID)
);