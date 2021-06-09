/*
Name: ConfigBean.java
Author: c3282352
Type: Model
Description: Sets up connection with database
*/

package pkg;
import javax.sql.*;
import java.sql.*;
import javax.naming.*;
public class ConfigBean {
    ConfigBean(){

    }
    //Preconditions:
    //None
    //Postconditions:
    //Connects a Datasource object to the database

    private static final DataSource dataSource = makeDataSource();

    private static DataSource makeDataSource() {
        try {
            InitialContext ctx = new InitialContext();
            return (DataSource) ctx.lookup("java:/comp/env/jdbc/BookingService");
        } catch (NamingException e) { throw new RuntimeException(e); }
    }
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
